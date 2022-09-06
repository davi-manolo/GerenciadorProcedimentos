package view;

import controller.ManagerTypeController;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ProcedureTypeModel;
import utils.MoveStageAction;

public class ManagerTypeDialog implements Initializable {
    
    private ManagerTypeController controller = new ManagerTypeController(this);

    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button closeAppButton;
    @FXML
    private TextField procedureField;
    @FXML
    private TextField porcentField;
    @FXML
    private TableView<ProcedureTypeModel> procedureTypeTable;
    @FXML
    private TableColumn<ProcedureTypeModel, String> nameColumn;
    @FXML
    private TableColumn<ProcedureTypeModel, String> profitPorcentColumn;
    @FXML
    private TableColumn<ProcedureTypeModel, ProcedureTypeModel> editColumn;
    @FXML
    private HBox procedureHBox;

    public void open() {
        try {
            MoveStageAction moveAction = new MoveStageAction();
            VBox root = (VBox) FXMLLoader.load(getClass().getResource("/views/ManagerTypeDialog.fxml"));
            Scene scene = new Scene(root, 600, 450);
            scene.getStylesheets().add(getClass().getResource("/styles/manager.css").toExternalForm());
            Stage stage = new Stage();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.centerOnScreen();
            stage.setScene(scene);
            moveAction.move(root, stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addButton.disableProperty().bind(
                procedureField.textProperty().isEmpty()
                .or(porcentField.textProperty().isEmpty()));

        removeButton.disableProperty().bind(procedureTypeTable.getSelectionModel()
                .selectedItemProperty().isNull());

        procedureTypeTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        profitPorcentColumn.setCellValueFactory(obj
                -> new ReadOnlyObjectWrapper<>(
                        new DecimalFormat("#0.00")
                                .format(obj.getValue().getPorcent()) + "%"));
        editColumn.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        editColumn.setCellFactory(param -> new TableCell<ProcedureTypeModel, ProcedureTypeModel>() {

            private final Button button = new Button();

            @Override
            protected void updateItem(ProcedureTypeModel typeEditable, boolean empty) {
                super.updateItem(typeEditable, empty);

                if (typeEditable == null) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                    button.getStyleClass().add("editButtonRow");
                    button.setOnAction(event -> {
                        procedureField.setText(typeEditable.getName());
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        porcentField.setText(formatter.format(typeEditable.getPorcent()));
                        procedureTypeTable.setDisable(true);
                        Button editButton = new Button("Atualizar");
                        editButton.getStyleClass().add("buttonManager");
                        editButton.setMinWidth(100);
                        editButton.setMinHeight(28);
                        editButton.setOnAction(action -> {
                            controller.updateProcedereType(typeEditable.getId());
                            procedureTypeTable.setDisable(false);
                            procedureHBox.getChildren().remove(editButton);
                            procedureHBox.getChildren().add(addButton);
                            clearComponents();
                            loadTable();
                        });
                        procedureHBox.getChildren().remove(addButton);
                        procedureHBox.getChildren().add(editButton);

                    });
                }
            }

        });

        addButton.setOnAction(action -> {
            controller.addNewType();
            clearComponents();
            loadTable();
        });
        removeButton.setOnAction(action -> {
            controller.removeType();
            clearComponents();
            loadTable();
        });
        loadTable();
    }

    private void clearComponents() {
        procedureField.clear();
        porcentField.clear();
    }

    private void loadTable() {
        procedureTypeTable.getItems().clear();
        procedureTypeTable.getItems().addAll(controller.getTypes());
    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeAppButton.getScene().getWindow();
        stage.close();
    }

    public TextField getProcedureField() {
        return procedureField;
    }

    public TextField getPorcentField() {
        return porcentField;
    }

    public TableView<ProcedureTypeModel> getProcedureTypeTable() {
        return procedureTypeTable;
    }

}
