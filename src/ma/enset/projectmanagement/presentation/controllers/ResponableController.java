package ma.enset.projectmanagement.presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import ma.enset.projectmanagement.dao.Impl.ResponsableDaoImpl;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.entities.Responsable;
import ma.enset.projectmanagement.services.Impl.ResponsableServiceImpl;
import ma.enset.projectmanagement.services.ResponsableService;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ResponableController  implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private TextField matriculeTextField;
    @FXML
    private TextField nomTextField;
    @FXML
    private TextField prenomTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField telTextField;

    @FXML
    private TextField passwordTextField;
    public Alert alert;
    ResponsableService responsableService;
    Responsable responsable2;

    public ResponableController() {
        this.responsableService = new ResponsableServiceImpl(new ResponsableDaoImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert = new Alert(Alert.AlertType.WARNING, "Gestion des Projets", ButtonType.OK);
        alert.setTitle("Gestion des projets");
        alert.setContentText("");
        alert.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../ressources/styles/dialog.css")).toExternalForm());
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ok");
        matriculeTextField.setText(LoginController.responsable.getMatricule());
        nomTextField.setText(LoginController.responsable.getNom());
        prenomTextField.setText(LoginController.responsable.getPrenom());
        emailTextField.setText(LoginController.responsable.getEmail());
        telTextField.setText(LoginController.responsable.getNumeroTel());
        passwordTextField.setText(LoginController.responsable.getMotDePasse());
    }
    public void modifierResponasable(){
        try {
            if (isOneOfTheseTextFieldsEmpty(matriculeTextField,nomTextField)) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                String matricule = matriculeTextField.getText().trim();
                String nom = nomTextField.getText().trim();
                String prenom = prenomTextField.getText().trim();
                String email = emailTextField.getText().trim();
                String tel = telTextField.getText().trim();
                String password = passwordTextField.getText().trim();
                responsable2=responsableService.update(new Responsable(matricule, nom,prenom,tel,email,password));
                LoginController.responsable= responsable2;
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public boolean isOneOfTheseTextFieldsEmpty(TextField... textFields) {
        for (TextField textField : textFields) {
            if (textField.getText().trim().isEmpty()) return true;
        }
        return false;
    }
    public void setAlertContent(String content) {
        Label label = new Label(content);
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
    }

}
