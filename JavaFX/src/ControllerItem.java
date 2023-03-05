import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ControllerItem {
    
    @FXML
    private Label id, name;

    @FXML
    private ImageView icon;

    @FXML
    private void handleMenuAction() {
        ControllerForm cnt = (ControllerForm) UtilsViews.getController("ViewForm");
        cnt.setEditMode(id.getText());
        UtilsViews.setView("viewForm");
    }

    public void setId(String title) {
        this.id.setText(title);
    }

    public void setName(String subtitle) {
        this.name.setText(subtitle);
    }

    public void showIcon() {
        icon.setVisible(true);
    }

    public void hideIcon() {
        icon.setVisible(false);
    }
}
