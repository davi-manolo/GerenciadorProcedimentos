package view;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Email;
import utils.MoveStageAction;

public class EmailDialog implements Initializable {
    
    @FXML
    private Button closeAppButton;
    
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
        List<String> list = new ArrayList<>();
        list.add("davi.manolo@gmail.com");
        
        Email email = new Email("davi.manolo@gmail.com",list,"Teste Assunto","Descrição da mensagem");
        email.send();
    }
    
    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeAppButton.getScene().getWindow();
        stage.close();
    }
    
}
