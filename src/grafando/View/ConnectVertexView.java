package grafando.View;

import javafx.collections.*;
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


public class ConnectVertexView {

    Stage popUpStage;
    Stage primaryStage;
    GridPane vertexSelection;
    ComboBox startingVertex, finalVertex;
    Label labelVertexA, labelVertexB;
    Button confirmButton;

    public ConnectVertexView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupElements();
        positionElementsInsideView();
    }

    private void setupElements() {
        setupGrid();
        setupPopUpStage();
        initializeComboboxes();
        initializeLabels();
        initializeButton();
    }

    private void positionElementsInsideView() {
        positionElementsInsideGridLayout();
        //positioningPopupInsideParentStage();
    }

    private void initializeComboboxes() {
        //TODO: pegar array de vertices do controller depois
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
        startingVertex = new ComboBox(options);
        finalVertex = new ComboBox(options);
    }

    private void initializeLabels() {
        labelVertexA = new Label("Vértice A");
        labelVertexB = new Label("Vértice B");
        labelVertexA.setTextFill(Color.web("#ffffff"));
        labelVertexB.setTextFill(Color.web("#ffffff"));
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
        popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setResizable(false);
        popUpStage.setTitle("Connect");

        Scene popUpScene = new Scene(vertexSelection, 250, 150);
        popUpScene.setFill(Color.web("#15202b"));

        popUpStage.setScene(popUpScene);
    }

    private void setupGrid() {
        vertexSelection = new GridPane();
        vertexSelection.setBackground(new Background(new BackgroundFill(Color.web("#15202b"), CornerRadii.EMPTY, Insets.EMPTY)));
        vertexSelection.setPadding(new Insets(12,12,12,12));
        vertexSelection.setHgap(20);
        vertexSelection.setVgap(5);
        vertexSelection.setAlignment(Pos.CENTER);
    }

    private void positionElementsInsideGridLayout() {
        vertexSelection.add(labelVertexA, 0, 0);
        vertexSelection.add(labelVertexB, 1, 0);
        vertexSelection.add(startingVertex, 0, 1);
        vertexSelection.add(finalVertex, 1, 1);
        vertexSelection.add(confirmButton, 1, 3);
        GridPane.setHalignment(labelVertexA, HPos.CENTER);
        GridPane.setHalignment(labelVertexB, HPos.CENTER);
        GridPane.setHalignment(confirmButton, HPos.RIGHT);
    }

    public void positioningPopupInsideParentStage() {
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

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }

    public ComboBox getStartingVertex() {
        return startingVertex;
    }

    public ComboBox getFinalVertex() {
        return finalVertex;
    }
}
