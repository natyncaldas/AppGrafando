package grafando.View;

import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ConnectVertexView {

    Stage popUpStage;
    Stage primaryStage;
    BorderPane root;
    GridPane vertexSelection;
    ComboBox startingVertex, finalVertex;

    public ConnectVertexView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        setupViewElements();
        positionElementsInsideView();
    }

    private void setupViewElements() {
        setupRootPane();
        setupPopUpStage();
        setupGrid();
        initializeComboboxes();
    }

    private void positionElementsInsideView() {
        positionElementsInsideGridLayout();
        positioningPopup();
    }

    private void initializeComboboxes() {
        //change to array from controller after
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
        startingVertex = new ComboBox(options);
        finalVertex = new ComboBox(options);
    }

    private void setupPopUpStage() {
        popUpStage = new Stage();
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.setResizable(false);

        Scene popUpScene = new Scene(root, 300, 100);
        popUpScene.setFill(Color.web("#15202b"));

        popUpStage.setScene(popUpScene);
    }

    private void setupRootPane() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Color.web("#15202b"), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setPadding(new Insets(12,12,12,12));
    }

    private void setupGrid() {
        vertexSelection = new GridPane();
        Pane pane = new Pane();
        vertexSelection.setHgap(20);
        vertexSelection.setVgap(5);
        vertexSelection.setAlignment(Pos.CENTER);
        root.setBottom(vertexSelection);
    }

    private void positionElementsInsideGridLayout() {
        vertexSelection.addColumn(0, startingVertex);
        vertexSelection.addColumn(1, finalVertex);
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
