package grafando.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;


public class RandomGraphView {

    Stage randomPopUpStage;
    Stage primaryStage;
    GridPane randomVertexSelection;
    ComboBox<Integer> numberVertex;
    Label labelNumber;
    Button confirmButton;
    ArrayList quantity;

    public RandomGraphView(Stage primaryStage, ArrayList quantity) {
        this.primaryStage = primaryStage;
        this.quantity = quantity;
        setupElements();
        positionElementsInsideView();
    }

    private void setupElements() {
        setupGrid();
        setupPopUpStage();
        initializeCombobox();
        initializeLabel();
        initializeButton();
    }
    private void positionElementsInsideView() {
        positionElementsInsideGridLayout();
    }

    private void initializeCombobox() {
        ObservableList<Integer> options = FXCollections.observableArrayList(quantity);
        numberVertex = new ComboBox<>(options);
        numberVertex.setBorder((new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5)))));
    }
    private void initializeLabel() {
        labelNumber = new Label("Number of vertexes:");
        labelNumber.setTextFill(Color.web("#ffffff"));

    }
    private void initializeButton() {
        confirmButton = new Button("OK");
        confirmButton.setTextFill(Color.DARKSLATEGRAY);
        confirmButton.setFont(Font.loadFont("file:resources/fonts/OpenSans-Regular.ttf", 12));
        confirmButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(50), Insets.EMPTY)));

        confirmButton.setOnMousePressed(new EventHandler<>() {
            final Color bg = confirmButton.getGraphic() == null ? Color.SPRINGGREEN : Color.SLATEGRAY;

            @Override
            public void handle(MouseEvent mouseEvent) {
                confirmButton.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
            }
        }) ;
        confirmButton.setOnMouseReleased(new EventHandler<>() {
            final Color bg = confirmButton.getGraphic() == null ? Color.LIGHTGRAY : null;

            @Override
            public void handle(MouseEvent mouseEvent) {
                confirmButton.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
            }
        });
    }
    private void setupPopUpStage() {
        randomPopUpStage = new Stage();
        randomPopUpStage.initModality(Modality.APPLICATION_MODAL);
        randomPopUpStage.setResizable(false);
        randomPopUpStage.setTitle("Generate a Random Graph");

        Scene Random_popUpScene = new Scene(randomVertexSelection, 250, 150);
        Random_popUpScene.setFill(Color.web("#15202b"));

        randomPopUpStage.setScene(Random_popUpScene);
    }

    private void setupGrid() {
        randomVertexSelection = new GridPane();
        randomVertexSelection.setBackground(new Background(new BackgroundFill(Color.web("#15202b"), CornerRadii.EMPTY, Insets.EMPTY)));
        randomVertexSelection.setPadding(new Insets(12,12,12,12));
        randomVertexSelection.setHgap(20);
        randomVertexSelection.setVgap(5);
        randomVertexSelection.setAlignment(Pos.CENTER);
    }
    private void positionElementsInsideGridLayout() {
        randomVertexSelection.add(labelNumber, 0, 0);
        randomVertexSelection.add(numberVertex, 1, 0);
        randomVertexSelection.add(confirmButton, 1, 3);
        GridPane.setHalignment(labelNumber, HPos.CENTER);
        GridPane.setHalignment(confirmButton, HPos.RIGHT);
    }
    public void positioningPopupInsideParentStage() {
        // Calculate the center position of the parent Stage
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        randomPopUpStage.setOnShowing(ev -> randomPopUpStage.hide());

        // Relocate the pop-up Stage
        randomPopUpStage.setOnShown(ev -> {
            randomPopUpStage.setX(centerXPosition - randomPopUpStage.getWidth()/2d);
            randomPopUpStage.setY(centerYPosition - randomPopUpStage.getHeight()/2d);
            randomPopUpStage.show();
        });

        randomPopUpStage.showAndWait();
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Stage getRandomPopUpStage() {
        return randomPopUpStage;
    }

    public ComboBox<Integer> getNumberVertex() {
        return numberVertex;
    }


}
