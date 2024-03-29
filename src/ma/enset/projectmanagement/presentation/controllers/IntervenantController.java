package ma.enset.projectmanagement.presentation.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import ma.enset.projectmanagement.dao.Impl.IntervenantDaoImpl;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.services.Impl.IntervenantServiceImpl;
import ma.enset.projectmanagement.services.IntervenantService;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class IntervenantController implements Initializable {
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
    public TableView<Intervenant> intervenantTableView;
    public IntervenantService intervenantService;
    public ObservableList<Intervenant> intervenantObservableList = FXCollections.observableArrayList();
    public Alert alert;

    public IntervenantController() {
        this.intervenantService= new IntervenantServiceImpl(new IntervenantDaoImpl());
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
        intervenantTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("matricule"));
        intervenantTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nom"));
        intervenantTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        intervenantTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        intervenantTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("numeroTel"));
        populateIntervenantTableView();
        intervenantTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Intervenant>() {
            @Override
            public void changed(ObservableValue<? extends Intervenant> observable, Intervenant oldValue, Intervenant newValue) {
                if (intervenantTableView.getSelectionModel().getSelectedItem() != null) {
                    matriculeTextField.setText(intervenantTableView.getSelectionModel().getSelectedItem().getMatricule().trim());
                    nomTextField.setText(intervenantTableView.getSelectionModel().getSelectedItem().getNom().trim());
                    prenomTextField.setText(intervenantTableView.getSelectionModel().getSelectedItem().getPrenom().trim());
                    emailTextField.setText(intervenantTableView.getSelectionModel().getSelectedItem().getEmail().trim());
                    telTextField.setText(intervenantTableView.getSelectionModel().getSelectedItem().getNumeroTel().trim());

                }
            }
        });
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                intervenantObservableList.clear();
                intervenantObservableList.addAll(intervenantService.getIntervenantsParNom(search.getText()));
            }
        });

    }
    public  void ajouterIntervenant(){
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
                intervenantService.addIntervenants(new Intervenant(matricule, nom,prenom,tel,email,"12345678"));
                clear();
                populateIntervenantTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void  modifierInterevenant(){
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
                intervenantService.updateIntervenants(new Intervenant(matricule, nom,prenom,tel,email,"12345678"));
                clear();
                populateIntervenantTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void supprimerIntervenant(){
        Intervenant intervenant = intervenantTableView.getSelectionModel().getSelectedItem();
        if (intervenant != null) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            setAlertContent("Êtes-vous sûr de vouloir supprimer ce intervenant ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    intervenantService.deleteIntervenants(intervenant);
                    populateIntervenantTableView();
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    setAlertContent("L'opération s'est terminée avec succès !");
                    alert.showAndWait();
                    clear();
                    if (alert.getResult() == ButtonType.OK) alert.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            alert.setAlertType(Alert.AlertType.WARNING);
            setAlertContent("Veuillez sélectionner un élément pour effectuer cette opération !");
            alert.show();
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

    public void clearTextFields(TextField... textFields) {

        for (TextField textField : textFields) textField.clear();
    }
    public void clear(){
        clearTextFields(matriculeTextField,nomTextField,prenomTextField,emailTextField,telTextField);
    }
    public void populateIntervenantTableView() {
        try {
            intervenantTableView.getItems().clear();
            intervenantObservableList.addAll(intervenantService.getAllIntervenants());
            intervenantTableView.setItems(intervenantObservableList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
