package grafando.Controller;

import grafando.View.*;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainScreenController {
    //Declaração da View
    public Stage primaryStage;
    private MainScreenView view;

    public MainScreenController(Stage primaryStage) throws FileNotFoundException {
        //Salva referência ao stage principal
        this.primaryStage = primaryStage;

        //Instanciação da View
        this.setView(new MainScreenView());

        //Chamada de métodos com eventos para Nodes específicos
        this.colorPressedButton(this.view.getRunDFS(), this.view.getRandom(), this.view.getAddE(), this.view.getClear());
        this.drawVertex(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes());
        this.clearGraph(this.view.getDrawGraph(), this.view.getClear(), this.view.getVertexes());
        this.deleteElements(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getVertexes());

        //testando
        openConnectVertexScreen();
    }
    //Getter e Setter para View
    public MainScreenView getView() {
        return view;
    }

    public void setView(MainScreenView view) {
        this.view = view;
    }

    //Métodos para adcionar eventos em Nodes

    //*Completa
    public void colorPressedButton(Button... b){
        for (Button btn:b) {
            btn.setOnMousePressed(new EventHandler<>() {

                final Color bg = btn.getGraphic() == null ? Color.SPRINGGREEN : Color.SLATEGRAY;

                @Override
                public void handle(MouseEvent mouseEvent) {
                    btn.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
                }
            }) ;
            btn.setOnMouseReleased(new EventHandler<>() {
                final Color bg = btn.getGraphic() == null ? Color.LIGHTGRAY : null;

                @Override
                public void handle(MouseEvent mouseEvent) {
                    btn.setBackground(new Background(new BackgroundFill(bg, new CornerRadii(50), Insets.EMPTY)));
                }
            });
        }
    }

    //*Completa
    //*Revisar quando o model for feito
    public void drawVertex(Pane pane, ToggleGroup group, ArrayList<Vertex> vertexArray){
        EventHandler<MouseEvent> eventHandler = e -> {

            if(pane.contains(e.getX() + 30, e.getY() + 30)
                    && !(e.getTarget() instanceof StackPane) && !(e.getTarget() instanceof Circle)
                    && !(e.getTarget() instanceof Text)){

                Vertex vertex = new Vertex();

                Circle circle = new Circle();
                circle.setCenterX(e.getX());
                circle.setCenterY(e.getY());
                MainScreenView.styleVertexShape(circle);

                Text txt = new Text(""+vertexArray.size());
                MainScreenView.styleVertexText(txt);

                vertex.setVertex(new StackPane(), circle, txt);
                vertexArray.add(vertex);
                vertex.getVertex().setLayoutX(e.getX()-13);
                vertex.getVertex().setLayoutY(e.getY()-13);

                pane.getChildren().addAll(vertex.getVertex());
            }
        };
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle().getUserData() == "AddV") {
                pane.setCursor(Cursor.HAND);
                pane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }else{
                pane.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        });
    }

    //*Completa
    public void clearGraph(Pane pane, Button b, ArrayList<Vertex> vertexArray){
        EventHandler<MouseEvent> eventHandler = e ->{
            pane.getChildren().clear();
            vertexArray.clear();
        };
        b.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    //*Incompleta!!
    //*Deleta apenas os vértices
    //*Completar com o controller da tela popup
    public void deleteElements(Pane pane, ToggleGroup group, ArrayList<Vertex> vertexArray){
        EventHandler<MouseEvent> eventHandler = e ->{
            for (Vertex v:vertexArray) {
                if(v.getVertex().getChildren().contains(e.getTarget())) {
                    v.getVertex().getChildren().removeAll();
                    pane.getChildren().remove(v.getVertex());
                    v.delete();
                    break;
                }
            }
        };

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle().getUserData() == "Del") {
                pane.setCursor(Cursor.CROSSHAIR);
                pane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }else{
                pane.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        });
    }

    //adiciona funcionalidade de criar a popup
    public void openConnectVertexScreen() {
        view.getAddE().setOnAction(e ->{
            ConnectVertexController popupController = new ConnectVertexController(this.primaryStage, this);
        });
    }

    //cria aresta ligando dois nós
    public void callDrawEdgeOnView(int initialVertexIndex, int finalVertexIndex) {
        view.drawEdge(initialVertexIndex, finalVertexIndex);
    }

}

