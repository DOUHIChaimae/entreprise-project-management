package ma.enset.projectmanagement.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.entities.Responsable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuIntervenantController implements Initializable {
    @FXML
    private ImageView exit;
    @FXML
    private Text matricule;
    @FXML
    private Button Buttonlogout;
    @FXML
    private  Button buttomProjet;

    @FXML
    private AnchorPane pane2;
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matricule.setText(LoginController.intervenant.getMatricule());
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

    }
    @FXML
    private void loadFXML(String fileName) {
        Parent parent;
        try {
            parent = FXMLLoader.load(getClass().getResource("../views/" + fileName + ".fxml"));
            borderPane.setCenter(parent);
        } catch (IOException ex) {
            Logger.getLogger(MenuResponsableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void loadTacheView(ActionEvent e) {
        loadFXML("tacheIntervenant");
    }

    @FXML
    private void loadSettingView(ActionEvent e) {
        loadFXML("intervenantSettingView");
    }

    @FXML
    public  void logout() throws IOException {
        LoginController.intervenant=new Intervenant();
        Stage stage = ((Stage) Buttonlogout.getScene().getWindow());
        stage.close();
        Stage stage1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/loginView.fxml"));
        Scene scene = new Scene(root);
        stage1.setScene(scene);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.getIcons().add(new Image("ma/enset/projectmanagement/presentation/ressources/icon.png"));
        stage1.show();
    }
    @FXML
    public void handClick() throws  Exception{

    }
}
