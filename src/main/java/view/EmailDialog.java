package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private Button sendButton;
    
    private PropertiesManager props = new PropertiesManager("emailProvider.properties");

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
        inputEmails.setText(props.getValue("email.default.recipients"));
        inputSubject.setText(props.getValue("email.default.subject"));
        inputMesage.setText(props.getValue("email.default.mesage"));
        sendButton.setOnAction(action -> {
            Email email = new Email(
                    getMails(inputEmails.getText()),
                    inputSubject.getText(),
                    inputMesage.getText());
            email.send();
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
