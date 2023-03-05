import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControllerConnection {
    @FXML
    Label label_server, label_port, label_msg;

    @FXML
    TextField field_server, field_port;

    @FXML
    Button button_connect;

    @FXML
    private void connect_action(ActionEvent event) {
        event.consume();
        Main.host = field_server.getText();
        Main.port = Integer.parseInt(field_port.getText());
        UtilsViews.setViewAnimating("ViewForm");
    }
    /* 

    private void checkConnection() {
        JSONObject obj = new JSONObject("{}");
        obj.put("type", "test");
        //showLoading();
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + ":" + Main.port + "/dades", obj.toString(), (response) -> {
            checkConnectionCallback(response);
            //hideLoading();
        });
    }

    private void checkConnectionCallback(String response) {
        JSONObject objResponse = new JSONObject(response);
        if (objResponse.getString("status").equals("OK")) {
            UtilsViews.setViewAnimating("ViewFilterType");
        }
    }
    */
}
