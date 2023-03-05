import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class ControllerForm implements Initializable {
    @FXML
    Label user, label_msg;

    @FXML
    ScrollPane scroll;

    @FXML
    Button button_confirm, button_form, button_list;

    @FXML
    TextField name, lastName, email, phone, address, city;

    String mode = "create";
    String userId;

    @FXML
    private ProgressIndicator loading;
    private int loadingCounter = 0;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        button_form.setDisable(true);
        hideError();
    }

    @FXML
    public void changeViewList() {
        ControllerList cnt = (ControllerList) UtilsViews.getController("listView");
        cnt.loadUsersList();
    }

    private void showError() {
        // Show the error
        label_msg.setVisible(true);
    }

    private void hideError() {
        label_msg.setVisible(false);
    }

    private void showLoading() {
        loadingCounter++;
        loading.setVisible(true);
    }

    private void hideLoading() {
        loadingCounter--;
        if (loadingCounter <= 0) {
            loadingCounter = 0;
            loading.setVisible(false);
        }
    }

    public void setEditMode(String userId) {
        mode = "edit";
        this.userId = userId;
        button_confirm.setVisible(false);
    }

    public void setCreatemode(String userId) {
        mode = "create";
        name.setText("");
        lastName.setText("");
        email.setText("");
        phone.setText("");
        address.setText("");
        city.setText("");
        button_confirm.setVisible(true);
    }

    @FXML
    private void createUser(String brand) {

        // Load list of consoles for this brand
        JSONObject obj = new JSONObject("{}");
        obj.put("type", "addUser");
        obj.put("name", name.getText());
        obj.put("lastName", lastName.getText());
        obj.put("email", email.getText());
        obj.put("phone", phone.getText());
        obj.put("address", address.getText());
        obj.put("city", city.getText());

        // Ask for data
        showLoading();
        UtilsHTTP.sendPOST(Main.protocol + "://" + Main.host + ":" + Main.port + "/dades", obj.toString(),
                (response) -> {
                    loadUsersListCallback(response);
                    hideLoading();
                });
    }

    private void loadUsersListCallback(String response) {

        JSONObject objResponse = new JSONObject(response);

        if (objResponse.getString("status").equals("OK")) {

            JSONArray JSONlist = objResponse.getJSONArray("result");

        } else {
            showError();
        }
    }

}
