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

import java.util.HashSet;


public class ConnectVertexView {

    Stage popUpStage;
    Stage primaryStage;
    GridPane vertexSelection;
    ComboBox<Integer> startingVertex, finalVertex;
    Label labelVertexA, labelVertexB;
    Button confirmButton;
    HashSet<Integer> vertexesCurrentlyOnScreen;

    // Precisa de uma referencia ao stage inicial para seu posicionamento
    // Uma referencia dos vertices na tela para as comboboxes se preencherem
    public ConnectVertexView(Stage primaryStage, HashSet<Integer> vertexesCurrentlyOnScreen) {
        this.primaryStage = primaryStage;
        this.vertexesCurrentlyOnScreen = vertexesCurrentlyOnScreen;
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
    }

    // Pega os vertices na tela e preenche as caixas
    private void initializeComboboxes() {
        ObservableList<Integer> options = FXCollections.observableArrayList(vertexesCurrentlyOnScreen);
        startingVertex = new ComboBox<>(options);
        finalVertex = new ComboBox<>(options);
        startingVertex.setBorder((new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5)))));
        finalVertex.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(1.5))));
    }

    // Inicializa as caixas de texto e cores
    private void initializeLabels() {
        labelVertexA = new Label("Vertex A");
        labelVertexB = new Label("Vertex B");
        labelVertexA.setTextFill(Color.web("#ffffff"));
        labelVertexB.setTextFill(Color.web("#ffffff"));
    }

    // Botão de confirmação da conexao de vertices
    // Muda de cor ao ser pressionado
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


    // Cria o grid de posiçoes
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
        // Calcula o centro da posiçao do stage pai
        double centerXPosition = primaryStage.getX() + primaryStage.getWidth()/2d;
        double centerYPosition = primaryStage.getY() + primaryStage.getHeight()/2d;

        // Esconde o pop up enquanto ele não é alocado no local certo
        popUpStage.setOnShowing(ev -> popUpStage.hide());

        // Após termos o centro, o mostramos
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

    public ComboBox<Integer> getStartingVertex() {
        return startingVertex;
    }

    public ComboBox<Integer> getFinalVertex() {
        return finalVertex;
    }
}
