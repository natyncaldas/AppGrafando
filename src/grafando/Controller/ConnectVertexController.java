package grafando.Controller;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ConnectVertexController {

    Stage primaryStage;
    Stage popUpStage;
    BorderPane root;

    ConnectVertexController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        popUpStage = new Stage();
        popUpStage.setResizable(false);
        this.root = new BorderPane();
        Scene popUpScene = new Scene(root, 300, 100);
        popUpScene.setFill(Color.web("#15202b"));
        root.setBackground(new Background(new BackgroundFill(Color.web("#15202b"), CornerRadii.EMPTY, Insets.EMPTY)));
        popUpStage.setScene(popUpScene);
        positioningPopup();
    }

    private void positioningPopup() {
        // Calculate the center position of the parent Stage
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        popUpStage.setOnShowing(ev -> popUpStage.hide());

        // Relocate the pop-up Stage
        popUpStage.setOnShown(ev -> {
            popUpStage.setX(centerXPosition - popUpStage.getWidth()/2d);
            popUpStage.setY(centerYPosition - popUpStage.getHeight()/2d);
            popUpStage.show();
        });
        popUpStage.showAndWait();
    }

}
