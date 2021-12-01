package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Email;
import utils.MoveStageAction;
import utils.PropertiesManager;

public class EmailDialog implements Initializable {

    @FXML
    private TextField inputEmails;
    @FXML
    private TextField inputSubject;
    @FXML
    private TextArea inputMesage;
    @FXML
    private Button closeAppButton;
    @FXML
    private Button attachmentButton;
    @FXML
    private Button sendButton;
    @FXML
    private Label descriptionAttachment;
    @FXML
    private Label labelProgress;

    private PropertiesManager props = new PropertiesManager("emailProvider.properties");
    private File attachment;

    public void open() {
        Platform.runLater(() -> {
            try {
                MoveStageAction moveAction = new MoveStageAction();
                VBox root = (VBox) FXMLLoader.load(getClass().getResource("/views/EmailDialog.fxml"));
                Scene scene = new Scene(root);
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
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sendButton.disableProperty().bind(
                inputEmails.textProperty().isEmpty()
                        .or(inputMesage.textProperty().isEmpty())
                        .or(inputSubject.textProperty().isEmpty())
                        .or(descriptionAttachment.textProperty().isEmpty()));
        labelProgress.setVisible(false);
        descriptionAttachment.setVisible(false);
        inputEmails.setText(props.getValue("email.default.recipients"));
        inputSubject.setText(props.getValue("email.default.subject"));
        inputMesage.setText(props.getValue("email.default.mesage"));
        attachmentButton.setOnAction(action -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("Microsoft Excel", "*.xls");
            fileChooser.getExtensionFilters().add(extFilter);
            attachment = fileChooser.showOpenDialog(new Stage());
            descriptionAttachment.setVisible(true);
            descriptionAttachment.setText(attachment.getName());
            Email.attachFile(attachment.getAbsolutePath(), attachment.getName());
        });

        sendButton.setOnAction(action -> {
            Email email = new Email(
                    getMails(inputEmails.getText()),
                    inputSubject.getText(),
                    inputMesage.getText());
            email.send();
            labelProgress.setVisible(true);
            labelProgress.setText("Email enviado!");
        });

    }

    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeAppButton.getScene().getWindow();
        stage.close();
    }

    private String[] getMails(String input) {
        return input.trim().split(";");
    }

}
