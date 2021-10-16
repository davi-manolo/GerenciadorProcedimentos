package utils;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MoveStageAction {

    private double x;
    private double y;

    public void move(Pane pane, Stage stage) {
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                x = stage.getX() - mouseEvent.getScreenX();
                y = stage.getY() - mouseEvent.getScreenY();
            }
        });
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() + x);
                stage.setY(mouseEvent.getScreenY() + y);
            }
        });
    }

}
