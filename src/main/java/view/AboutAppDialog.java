package view;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import utils.MoveStageAction;

public class AboutAppDialog implements Initializable {
    
    @FXML
    private Button closeAppButton;
    @FXML
    private Label versionLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    
    public void open() {
        Platform.runLater(() -> {
            try {
                MoveStageAction moveAction = new MoveStageAction();
                VBox root = (VBox) FXMLLoader.load(getClass().getResource("/views/AboutAppDialog.fxml"));
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
        try {
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileReader("pom.xml"));
            nameLabel.setText(model.getName());
            versionLabel.setText(model.getVersion());
            descriptionLabel.setText(model.getDescription());
        } catch (IOException ex) {
            Logger.getLogger(AboutAppDialog.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XmlPullParserException ex) {
            Logger.getLogger(AboutAppDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void closeButtonAction() {
        Stage stage = (Stage) closeAppButton.getScene().getWindow();
        stage.close();
    }
    
}
