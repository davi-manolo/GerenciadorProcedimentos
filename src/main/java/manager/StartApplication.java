package manager;

import utils.SessionMode;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.MoveStageAction;

public class StartApplication extends Application {
    
    public static final Locale REGION = new Locale("pt", "BR");

    @Override
    public void start(Stage principalStage) throws Exception {
        MoveStageAction moveAction = new MoveStageAction();
        VBox root = (VBox) FXMLLoader.load(getClass().getResource("/views/ManagerDialog.fxml"));
        Scene scene = new Scene(root, 910, 590);
        scene.getStylesheets().add(getClass().getResource("/styles/manager.css").toExternalForm());
        principalStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png"))); 
        scene.setFill(Color.TRANSPARENT);
        principalStage.initStyle(StageStyle.TRANSPARENT);
        principalStage.centerOnScreen();
        principalStage.setScene(scene);
        principalStage.show();
        moveAction.move(root, principalStage);
    }
    
    public static void main(String[] args) {
        Locale.setDefault(REGION);
        SessionMode.demo();//Modo de início de sessão (demo/full)
        launch(args);
    }

}
