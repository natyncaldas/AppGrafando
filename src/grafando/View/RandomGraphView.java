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

    Stage Random_popUpStage;
    Stage primaryStage;
    GridPane random_vertexSelection;
    ComboBox<Integer> number_vertex;
    Label label_number;
    Button confirmButton;
    ArrayList quantidade;

    public RandomGraphView(Stage primaryStage, ArrayList quantidade) {
        this.primaryStage = primaryStage;
        this.quantidade = quantidade;
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
        ObservableList<Integer> options = FXCollections.observableArrayList(quantidade);
        number_vertex = new ComboBox<>(options);
        number_vertex.setBorder((new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5)))));
    }
    private void initializeLabel() {
        label_number = new Label("Quantidade de vertices:");
        label_number.setTextFill(Color.web("#ffffff"));

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
        Random_popUpStage = new Stage();
        Random_popUpStage.initModality(Modality.APPLICATION_MODAL);
        Random_popUpStage.setResizable(false);
        Random_popUpStage.setTitle("Generate a Random Graph");

        Scene Random_popUpScene = new Scene(random_vertexSelection, 250, 150);
        Random_popUpScene.setFill(Color.web("#15202b"));

        Random_popUpStage.setScene(Random_popUpScene);
    }

    private void setupGrid() {
        random_vertexSelection = new GridPane();
        random_vertexSelection.setBackground(new Background(new BackgroundFill(Color.web("#15202b"), CornerRadii.EMPTY, Insets.EMPTY)));
        random_vertexSelection.setPadding(new Insets(12,12,12,12));
        random_vertexSelection.setHgap(20);
        random_vertexSelection.setVgap(5);
        random_vertexSelection.setAlignment(Pos.CENTER);
    }
    private void positionElementsInsideGridLayout() {
        random_vertexSelection.add(label_number, 0, 0);
        random_vertexSelection.add(number_vertex, 1, 0);
        random_vertexSelection.add(confirmButton, 1, 3);
        GridPane.setHalignment(label_number, HPos.CENTER);
        GridPane.setHalignment(confirmButton, HPos.RIGHT);
    }
    public void positioningPopupInsideParentStage() {
        // Calculate the center position of the parent Stage
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        // Hide the pop-up stage before it is shown and becomes relocated
        Random_popUpStage.setOnShowing(ev -> Random_popUpStage.hide());

        // Relocate the pop-up Stage
        Random_popUpStage.setOnShown(ev -> {
            Random_popUpStage.setX(centerXPosition - Random_popUpStage.getWidth()/2d);
            Random_popUpStage.setY(centerYPosition - Random_popUpStage.getHeight()/2d);
            Random_popUpStage.show();
        });

        Random_popUpStage.showAndWait();
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Stage getRandomPopUpStage() {
        return Random_popUpStage;
    }

    public ComboBox<Integer> getNumber_vertex() {
        return number_vertex;
    }


}
