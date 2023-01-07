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
import ma.enset.projectmanagement.dao.SingletonConnexionDB;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.entities.Responsable;

import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final Connection con;
    static Responsable responsable= new Responsable();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }
    public LoginController(){
        this.con= SingletonConnexionDB.getConnections();
    }
    @FXML
    public void login() throws  Exception{
        if(this.isValidated()){
            try {
                // login responsable
                PreparedStatement ps= con.prepareStatement("SELECT *from RESPONSABLE WHERE email=? and motDePasse=?;");
                ps.setString(1,username.getText());
                ps.setString(2, password.getText());
                ResultSet set = ps.executeQuery();
                if(set.next()){
                    responsable.setMatricule(set.getString("matricule"));
                    responsable.setNom(set.getString("nom"));
                    responsable.setPrenom(set.getString("prenom"));
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                    Stage stage1 = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("../views/menuResponsableView.fxml"));
                    Scene scene = new Scene(root);
                    stage1.setScene(scene);
                    stage1.initStyle(StageStyle.UNDECORATED);
                    stage1.getIcons().add(new javafx.scene.image.Image("ma/enset/projectmanagement/presentation/ressources/icon.png"));
                    stage1.show();
                }  else {
                    // login Intervenant
                    PreparedStatement ps2= con.prepareStatement("SELECT *from INTERVENANT WHERE email=? and motDePasse=?;");
                    ps2.setString(1,username.getText());
                    ps2.setString(2, password.getText());
                    ResultSet set2 = ps2.executeQuery();
                    if (set2.next()) {
                        intervenant.setMatricule(set2.getString("matricule"));
                        intervenant.setNom(set2.getString("nom"));
                        intervenant.setPrenom(set2.getString("prenom"));
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.close();
                        Stage stage1 = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("../views/menuIntervenantView.fxml"));
                        Scene scene = new Scene(root);
                        stage1.setScene(scene);
                        stage1.initStyle(StageStyle.UNDECORATED);
                        stage1.getIcons().add(new Image("ma/enset/projectmanagement/ressources/icon.png"));
                        stage1.show();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.WARNING,"Erreur de Email ou mot de Passe !", ButtonType.OK);
                        alert.show();
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

        }
    }

    private boolean isValidated(){
        Alert alert;
        if (username.getText().equals("")) {
            alert= new Alert(Alert.AlertType.ERROR,
                    "Vous devez saisir votre email",ButtonType.OK);
            alert.show();
            username.requestFocus();
        } else if (username.getText().length() < 12 || username.getText().length() > 40) {
            alert= new Alert(Alert.AlertType.ERROR,
                    "Email  ne pas etre moins de 12 ou plus de 30 caracteres ",ButtonType.OK);
            alert.show();
            username.requestFocus();
        } else if (password.getText().equals("")) {
            alert= new Alert(Alert.AlertType.ERROR,
                    " Vous devez saisir votre mot de passe.",ButtonType.OK);
            alert.show();
            password.requestFocus();
        } else if (password.getText().length() < 2 || password.getText().length() > 10) {
            alert= new Alert(Alert.AlertType.ERROR,
                    "Le mot de pass ne pas etre moins de 2 ou plus de 10 caracteres ",ButtonType.OK);
            alert.show();
            password.requestFocus();
        } else {
            return true;
        }
        return false;

    }
}
