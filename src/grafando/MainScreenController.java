package grafando;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainScreenController {
    //Declaração da View
    private MainScreenView view;

    public MainScreenController() throws FileNotFoundException {
        //Instanciação da View
        this.setView(new MainScreenView());
        //Chamada de métodos com eventos para Nodes específicos
        this.colorPressedButton(this.view.getRunBFS(), this.view.getRandom(), this.view.getAddE(), this.view.getClear());
        this.drawGraph(this.view.getDrawGraph(), this.view.getToggleAddDel(), this.view.getNumbers(), this.view.getVertexes());
        this.clearGraph(this.view.getDrawGraph(), this.view.getClear(), this.view.getVertexes());
    }
    //Getter e Setter para View
    public MainScreenView getView() {
        return view;
    }

    public void setView(MainScreenView view) {
        this.view = view;
    }

    //Métodos para adcionar eventos em Nodes
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

    public void drawGraph(Pane pane, ToggleGroup group, ArrayList<Integer>num, ArrayList<StackPane> stack){
        EventHandler<MouseEvent> eventHandler = e -> {
            Circle circle = new Circle();
            circle.setCenterX(e.getX());
            circle.setCenterY(e.getY());
            circle.setRadius(13);
            circle.setFill(Color.LIGHTCORAL);
            circle.setStroke(Color.DARKSLATEGRAY);

            if(pane.contains(circle.getCenterX()+35, circle.getCenterY()+35)
                    && !(e.getTarget() instanceof StackPane) && !(e.getTarget() instanceof Circle)
                    && !(e.getTarget() instanceof Text)){

                Text txt = new Text();
                StackPane vertex = new StackPane(circle, txt);
                stack.add(vertex);
                num.add(stack.indexOf(vertex));
                txt.setText(""+stack.indexOf(vertex));
                vertex.setLayoutX(circle.getCenterX());
                vertex.setLayoutY(circle.getCenterY());
                pane.getChildren().addAll(vertex);
            }

        };
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle().getUserData() == "AddV") {
                pane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }else{
                pane.removeEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
        });
    }

    public void clearGraph(Pane pane, Button b, ArrayList<StackPane> stack){
        EventHandler<MouseEvent> eventHandler = e ->{
            pane.getChildren().removeAll(stack);
            stack.clear();
        };
        b.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}

