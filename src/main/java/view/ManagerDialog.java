package view;

import controller.ServiceProcedureController;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
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
import model.ProcedureTypeModel;
import model.ServiceProcedureModel;
import utils.GenerateExcelFile;
import utils.ServiceProcedurePeriod;

public class ManagerDialog implements Initializable {

    private ServiceProcedureController controller = new ServiceProcedureController(this);

    @FXML
    private Button procedureManagerButton;
    @FXML
    private Button closeAppButton;
    @FXML
    private Button sendMailButton;
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
    private Label totalCostumersLabel;
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
        controller.startSession();
        YearMonth current = YearMonth.now();
        int year = current.getYear();
        while (current.getMonthValue() >= 1 && current.getYear() == year) {
            periodBox.getItems().add(new ServiceProcedurePeriod(current));
            current = current.minusMonths(1);
        }
        periodBox.getSelectionModel().selectFirst();
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
                            controller.updateServiceProcedure(spEditable.getId());
                            serviceProceduresTable.setDisable(false);
                            procedureHBox.getChildren().remove(editButton);
                            procedureHBox.getChildren().add(addButton);
                            clearComponents();
                            loadTable();
                            controller.defineReceivedTotalValue();
                        });
                        procedureHBox.getChildren().remove(addButton);
                        procedureHBox.getChildren().add(editButton);

                    });
                    disableButtonsByPeriodProperty(button);
                }
            }

        });

        procedureManagerButton.setOnAction(action -> {
            ManagerTypeDialog typeDialog = new ManagerTypeDialog();
            typeDialog.open();
            ObservableList<ProcedureTypeModel> typesList = FXCollections.observableArrayList();
            typesList.addAll(controller.getProcedureTypeList());
            proceduresTypesBox.setItems(typesList);
        });
        versionButton.setOnAction(action -> {
            AboutAppDialog appDialog = new AboutAppDialog();
            appDialog.open();
        });
        addButton.setOnAction(action -> {
            controller.addNewServiceProcedure();
            clearComponents();
            loadTable();
            controller.defineReceivedTotalValue();
        });
        sendMailButton.setOnAction(action -> {
            EmailDialog emailDialog = new EmailDialog();
            emailDialog.open();
        });
        removeButton.setOnAction(action -> {
            controller.removeServiceProcedure(serviceProceduresTable
                    .getSelectionModel().getSelectedItems());
            clearComponents();
            loadTable();
            controller.defineReceivedTotalValue();
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
            controller.defineReceivedTotalValue();
            if(!disableButtonsByPeriodProperty(addButton, removeButton)) {
                setBindsProperty();
            }
        });

        setBindsProperty();
        
        proceduresTypesBox.getItems().addAll(controller.getProcedureTypeList());
        loadTable();
        controller.defineReceivedTotalValue();

    }

    private boolean disableButtonsByPeriodProperty(Button... buttons) {
        YearMonth periodSelected
                = periodBox.getSelectionModel().getSelectedItem().getPeriod();
        SimpleBooleanProperty binding
                = new SimpleBooleanProperty(periodSelected.isBefore(YearMonth.now()));
        for (Button button : buttons) {
            button.disableProperty().bind(binding);
        }
        return binding.get();
    }

    private void setBindsProperty() {
        addButton.disableProperty().bind(datePicker.valueProperty().isNull()
                .or(clientField.textProperty().isEmpty())
                .or(priceField.textProperty().isEmpty())
                .or(proceduresTypesBox.getSelectionModel().selectedItemProperty().isNull())
        );
        removeButton.disableProperty().bind(serviceProceduresTable.getSelectionModel()
                .selectedItemProperty().isNull());
        totalCostumersLabel.textProperty().bind(Bindings
                .size(serviceProceduresTable.getItems())
                .asString("%s Procedimentos realizados"));

        exportButton.disableProperty().bind(Bindings.isEmpty(serviceProceduresTable.getItems()));
        priceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(,(\\d)?(\\d)?)?")) {
                priceField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void loadTable() {
        serviceProceduresTable.getItems().clear();
        serviceProceduresTable.getItems().addAll(
                controller.getServiceProcedureModels(
                        periodBox.getSelectionModel()
                                .getSelectedItem().getPeriod().toString()));
    }

    private void clearComponents() {
        datePicker.setValue(null);
        clientField.clear();
        priceField.clear();
        proceduresTypesBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void closeButtonAction() {
        controller.closeConnection();
        Stage stage = (Stage) closeAppButton.getScene().getWindow();
        stage.close();
    }

    public void setTotalReceivedLabel(String value) {
        totalReceivedLabel.setText(value);
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public TextField getClientField() {
        return clientField;
    }

    public TextField getPriceField() {
        return priceField;
    }

    public ComboBox<ProcedureTypeModel> getProceduresTypesBox() {
        return proceduresTypesBox;
    }

    public TableView<ServiceProcedureModel> getServiceProceduresTable() {
        return serviceProceduresTable;
    }

    public Text getTestText() {
        return testText;
    }

}
