package grafando.View;

import javafx.collections.*;


public class ConnectVertexView {
    ConnectVertexView() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Option 1",
                        "Option 2",
                        "Option 3"
                );
    }
}
