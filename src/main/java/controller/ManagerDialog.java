package controller;

import dao.ProcedureTypeDAO;
import dao.ProcedureTypeImp;
import dao.ServiceProcedureDAO;
import dao.ServiceProcedureImp;
import database.ProcedureManagerDB;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manager.SessionTestMode;
import model.ProcedureTypeModel;
import model.ServiceProcedureModel;
import tester.dao.ProcedureTypeTesterImp;
import tester.dao.ServiceProcedureTesterImpl;
import utils.GenerateExcelFile;
import utils.ServiceProcedurePeriod;

public class ManagerDialog implements Initializable {

    private ProcedureTypeDAO procedureType;
    private ServiceProcedureDAO serviceProcedure;
    
    @FXML
    private Button procedureManagerButton;
    @FXML
    private Button closeAppButton;
    @FXML
    private Button versionButton;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button exportButton;
    @FXML
    private TextField clientField;
    @FXML
    private TextField priceField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<ServiceProcedurePeriod> periodBox;
    @FXML
    private Label totalReceivedLabel;
    @FXML
    private ComboBox<ProcedureTypeModel> proceduresTypesBox;
    @FXML
    private TableView<ServiceProcedureModel> serviceProceduresTable;
    @FXML
    private TableColumn<ServiceProcedureModel, String> permormedInColumn;
    @FXML
    private TableColumn<ServiceProcedureModel, String> clientColumn;
    @FXML
    private TableColumn<ServiceProcedureModel, String> procedureTypeColumn;
    @FXML
    private TableColumn<ServiceProcedureModel, String> priceColumn;
    @FXML
    private TableColumn<ServiceProcedureModel, String> receivedColumn;
    @FXML
    private TableColumn<ServiceProcedureModel, ServiceProcedureModel> editColumn;
    @FXML
    private HBox procedureHBox;
    @FXML
    private Text testText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SessionTestMode.on(); //INICIADO COMO TESTES (SEM COMUNICAÇÃO COM BANCO DE DADOS)
        testingSession(SessionTestMode.isValue());
        serviceProceduresTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        permormedInColumn.setCellValueFactory(obj
                -> new ReadOnlyObjectWrapper<>(
                        new SimpleDateFormat("dd/MM/yyyy")
                                .format(obj.getValue().getPerformedIn())));
        clientColumn.setCellValueFactory(
                new PropertyValueFactory<>("client"));
        procedureTypeColumn.setCellValueFactory(obj
                -> new ReadOnlyObjectWrapper<>(obj.getValue().getType().getName()));
        priceColumn.setCellValueFactory(obj
                -> new ReadOnlyObjectWrapper<>(NumberFormat.getCurrencyInstance()
                        .format(obj.getValue().getPrice())));
        receivedColumn.setCellValueFactory(obj
                -> new ReadOnlyObjectWrapper<>(NumberFormat.getCurrencyInstance()
                        .format(obj.getValue().getReceived())));

        editColumn.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        editColumn.setCellFactory(param -> new TableCell<ServiceProcedureModel, ServiceProcedureModel>() {

            private final Button button = new Button();

            @Override
            protected void updateItem(ServiceProcedureModel spEditable, boolean empty) {
                super.updateItem(spEditable, empty);

                if (spEditable == null) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                    button.getStyleClass().add("editButtonRow");
                    button.setOnAction(event -> {

                        LocalDate localDate = Instant.ofEpochMilli(
                                spEditable.getPerformedIn().getTime())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        datePicker.setValue(localDate);
                        clientField.setText(spEditable.getClient());
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        priceField.setText(formatter.format(spEditable.getPrice()));
                        proceduresTypesBox.getSelectionModel().select(spEditable.getType());
                        serviceProceduresTable.setDisable(true);
                        Button editButton = new Button("Atualizar");
                        editButton.getStyleClass().add("buttonManager");
                        editButton.setMinWidth(100);
                        editButton.setMinHeight(28);
                        editButton.setOnAction(action -> {
                            ServiceProcedureModel spEdited = createServiceProcedure();
                            spEdited.setId(spEditable.getId());
                            serviceProcedure.updateServiceProcedure(spEdited);
                            serviceProceduresTable.setDisable(false);
                            procedureHBox.getChildren().remove(editButton);
                            procedureHBox.getChildren().add(addButton);
                            clearComponents();
                            loadTable();
                            defineReceivedTotalValue();
                        });
                        procedureHBox.getChildren().remove(addButton);
                        procedureHBox.getChildren().add(editButton);

                    });
                }
            }

        });

        procedureManagerButton.setOnAction(action -> {
            ManageTypeDialog typeDialog = new ManageTypeDialog();
            typeDialog.open();
            reloadProceduresType();
            
        });
        versionButton.setOnAction(action -> {
            AboutAppDialog appDialog = new AboutAppDialog();
            appDialog.open();
        });
        addButton.setOnAction(action -> {
            serviceProcedure.addNewServiceProcedure(createServiceProcedure());
            clearComponents();
            loadTable();
            defineReceivedTotalValue();
        });
        removeButton.setOnAction(action -> {
            serviceProcedure.removeServiceProcedure(serviceProceduresTable.getSelectionModel().getSelectedItems());
            clearComponents();
            loadTable();
            defineReceivedTotalValue();
        });
        exportButton.setOnAction(action -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("Microsoft Excel", "*.xls");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(new Stage());
            GenerateExcelFile.createFile(file, serviceProceduresTable.getItems());
        });
        periodBox.setOnAction(action -> {
            loadTable();
            defineReceivedTotalValue();
        });

        addButton.disableProperty().bind(datePicker.valueProperty().isNull()
                .or(clientField.textProperty().isEmpty())
                .or(priceField.textProperty().isEmpty())
                .or(proceduresTypesBox.getSelectionModel().selectedItemProperty().isNull())
        );
        removeButton.disableProperty().bind(serviceProceduresTable.getSelectionModel()
                .selectedItemProperty().isNull());
        exportButton.disableProperty().bind(Bindings.isEmpty(serviceProceduresTable.getItems()));
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(,(\\d)?(\\d)?)?")) {
                priceField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        YearMonth current = YearMonth.now();
        int year = current.getYear();
        while (current.getMonthValue() >= 1 && current.getYear() == year) {
            periodBox.getItems().add(new ServiceProcedurePeriod(current));
            current = current.minusMonths(1);
        }

        periodBox.getSelectionModel().selectFirst();
        proceduresTypesBox.getItems().addAll(procedureType.getTypes());
        loadTable();
        defineReceivedTotalValue();

    }

    private void reloadProceduresType() {
        ObservableList<ProcedureTypeModel> typesList = FXCollections.observableArrayList();
        typesList.addAll(procedureType.getTypes());
        proceduresTypesBox.setItems(typesList);
    }
    
    private void loadTable() {
        serviceProceduresTable.getItems().clear();
        serviceProceduresTable.getItems().addAll(
                serviceProcedure.getServiceProceduresByPeriod(
                        periodBox.getSelectionModel().getSelectedItem().getPeriod().toString()));
    }

    private void defineReceivedTotalValue() {
        double value = 0;
        List<ServiceProcedureModel> spList = serviceProceduresTable.getItems();
        for (ServiceProcedureModel serviceProcedureModel : spList) {
            value += serviceProcedureModel.getReceived();
        }
        totalReceivedLabel.setText(NumberFormat.getCurrencyInstance().format(value));
    }

    private void clearComponents() {
        datePicker.setValue(null);
        clientField.clear();
        priceField.clear();
        proceduresTypesBox.getSelectionModel().clearSelection();
    }

    private ServiceProcedureModel createServiceProcedure() {
        ServiceProcedureModel sp = new ServiceProcedureModel();
        sp.setPerformedIn(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        sp.setClient(clientField.getText());
        sp.setPrice(Double.valueOf(priceField.getText().replace(",", ".")));
        sp.setType(proceduresTypesBox.getSelectionModel().getSelectedItem());
        sp.setReceived((sp.getPrice() / 100) * sp.getType().getPorcent());
        if (SessionTestMode.isValue()) {
            sp.setId(new Date().hashCode());
            sp.setRegisteredDate(new Date());
        }
        return sp;
    }

    private void testingSession(boolean value) {
        if (!value) {
            testText.setVisible(false);
            //ProcedureManagerDB.connect(); //Conexão padrão.
            ProcedureManagerDB.connect( //Conexão customizada (banco de dados, usuário e senha).
                    "jdbc:mysql://localhost:3306/procedures_db",
                    "procedure_user",
                    "procedure");
            procedureType = new ProcedureTypeImp();
            serviceProcedure = new ServiceProcedureImp();
            return;
        }
        testText.setVisible(true);
        procedureType = new ProcedureTypeTesterImp();
        serviceProcedure = new ServiceProcedureTesterImpl();
    }

    @FXML
    private void closeButtonAction() {
        if (!SessionTestMode.isValue()) {
            ProcedureManagerDB.closeConnection();
        }
        Stage stage = (Stage) closeAppButton.getScene().getWindow();
        stage.close();

    }

}
