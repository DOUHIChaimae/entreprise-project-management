package ma.enset.projectmanagement.presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.enset.projectmanagement.dao.Impl.IntervenantDaoImpl;
import ma.enset.projectmanagement.dao.Impl.ResponsableDaoImpl;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.entities.Responsable;
import ma.enset.projectmanagement.services.Impl.IntervenantServiceImpl;
import ma.enset.projectmanagement.services.Impl.ResponsableServiceImpl;
import ma.enset.projectmanagement.services.IntervenantService;
import ma.enset.projectmanagement.services.ResponsableService;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    static Responsable responsable = new Responsable();
    static Intervenant intervenant = new Intervenant();
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    Window window;
    @FXML
    private ImageView exit;

    private ResponsableService responsableService;
    private IntervenantService intervenantService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public LoginController() {
        this.responsableService = new ResponsableServiceImpl(new ResponsableDaoImpl());
        this.intervenantService = new IntervenantServiceImpl(new IntervenantDaoImpl());
    }

    @FXML
    public void login() throws Exception {
        if (this.isValidated()) {
            Responsable responsable1 = responsableService.login(new Responsable(username.getText(), password.getText()));
            if (responsable1 != null) {
                responsable = responsable1;
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                Stage stage1 = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../views/menuResponsableView.fxml"));
                Scene scene = new Scene(root);
                stage1.setScene(scene);
                stage1.initStyle(StageStyle.UNDECORATED);
                stage1.getIcons().add(new javafx.scene.image.Image("ma/enset/projectmanagement/presentation/ressources/icon.png"));
                stage1.show();
            } else {
                Intervenant intervenant1 = intervenantService.login(new Intervenant(username.getText(), password.getText()));
                if (intervenant1 != null) {
                    intervenant = intervenant1;
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                    Stage stage1 = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("../views/menuIntervenantView.fxml"));
                    Scene scene = new Scene(root);
                    stage1.setScene(scene);
                    stage1.initStyle(StageStyle.UNDECORATED);
                    stage1.getIcons().add(new Image("ma/enset/projectmanagement/presentation/ressources/icon.png"));
                    stage1.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Erreur de Email ou mot de Passe !", ButtonType.OK);
                    alert.show();
                }
            }
        }
    }

    private boolean isValidated() {
        Alert alert;
        if (username.getText().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR,
                    "Vous devez saisir votre email", ButtonType.OK);
            alert.show();
            username.requestFocus();
        } else if (username.getText().length() < 12 || username.getText().length() > 40) {
            alert = new Alert(Alert.AlertType.ERROR,
                    "Email  ne pas etre moins de 12 ou plus de 30 caracteres ", ButtonType.OK);
            alert.show();
            username.requestFocus();
        } else if (password.getText().equals("")) {
            alert = new Alert(Alert.AlertType.ERROR,
                    " Vous devez saisir votre mot de passe.", ButtonType.OK);
            alert.show();
            password.requestFocus();
        } else if (password.getText().length() < 2 || password.getText().length() > 10) {
            alert = new Alert(Alert.AlertType.ERROR,
                    "Le mot de pass ne pas etre moins de 2 ou plus de 10 caracteres ", ButtonType.OK);
            alert.show();
            password.requestFocus();
        } else {
            return true;
        }
        return false;

    }
}
