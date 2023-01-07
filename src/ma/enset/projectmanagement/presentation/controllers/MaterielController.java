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
import ma.enset.projectmanagement.dao.Impl.MaterielDaoImpl;
import ma.enset.projectmanagement.entities.Materiel;
import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.services.Impl.MaterielServiceImpl;
import ma.enset.projectmanagement.services.MaterielService;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MaterielController implements Initializable {
    @FXML
    private TextField search;
    @FXML
    private TextField matriculeTextField;
    @FXML
    private TextField nomTextField;

    @FXML
    public TableView<Materiel> materielsTableView;
    public MaterielService materielService;
    public ObservableList<Materiel> materielObservable = FXCollections.observableArrayList();
    public Alert alert;

    public MaterielController() {
        this.materielService = new MaterielServiceImpl(new MaterielDaoImpl());
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
        materielsTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("matricule"));
        materielsTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("nomMateriel"));
        populateMaterielTableView();
        materielsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Materiel>() {
            @Override
            public void changed(ObservableValue<? extends Materiel> observable, Materiel oldValue, Materiel newValue) {
                if (materielsTableView.getSelectionModel().getSelectedItem() != null) {
                    matriculeTextField.setText(materielsTableView.getSelectionModel().getSelectedItem().getMatricule().trim());
                    nomTextField.setText(materielsTableView.getSelectionModel().getSelectedItem().getNomMateriel().trim());

                }
            }
        });
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                materielObservable.clear();
                materielObservable.addAll(materielService.chercherParNomMateriel(search.getText()));
            }
        });

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

    public void ajouterMateriel() {
        try {
            if (isOneOfTheseTextFieldsEmpty(matriculeTextField,nomTextField)) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                String matricule = matriculeTextField.getText().trim();
                String nom = nomTextField.getText().trim();
                materielService.addMateriel(new Materiel(matricule, nom));
                clear();
                populateMaterielTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void modifierMateriel() {
        try {
            if (isOneOfTheseTextFieldsEmpty(matriculeTextField,nomTextField)) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                String matricule = matriculeTextField.getText().trim();
                String nom = nomTextField.getText().trim();
                Materiel materiel = new Materiel(matricule,nom);
                 materielService.updateMateriel(materiel);
                clear();
                populateMaterielTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void supprimerMateriel(){
        Materiel m = materielsTableView.getSelectionModel().getSelectedItem();
        if (m != null) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            setAlertContent("Êtes-vous sûr de vouloir supprimer ce matériel ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    materielService.deleteMateriel(m);
                    populateMaterielTableView();
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
    public void clear(){
        clearTextFields(matriculeTextField,nomTextField);
    }
    public void populateMaterielTableView() {
        try {
            materielsTableView.getItems().clear();
            materielObservable.addAll(materielService.getAllMateriel());
            materielsTableView.setItems(materielObservable);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
