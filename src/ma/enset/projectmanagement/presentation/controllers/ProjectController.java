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
import javafx.util.Callback;
import ma.enset.projectmanagement.dao.Impl.ProjetDaoImpl;
import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.services.Impl.ProjetServiceImpl;
import ma.enset.projectmanagement.services.ProjetService;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {
    @FXML
    private  TextField search;
    @FXML
    private TextField titreTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private DatePicker dateDebutDatePicker;
    @FXML
    private DatePicker dateFinDatePicker;
    @FXML
    public TableView<Projet> projetsTableView;
    public ProjetService projetService;
    public ObservableList<Projet> projetObservableList = FXCollections.observableArrayList();
    public Alert alert;

    public ProjectController() {
        this.projetService = new ProjetServiceImpl(new ProjetDaoImpl());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert = new Alert(Alert.AlertType.WARNING, "Gestion des Projets", ButtonType.OK);
        alert.setTitle("Gestion des Projets");
        alert.setContentText("");
        alert.initModality(Modality.APPLICATION_MODAL);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../ressources/styles/dialog.css")).toExternalForm());
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Ok");
        projetsTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        projetsTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("titre"));
        projetsTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
        projetsTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        projetsTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        populateProjectTableView();
        projetsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Projet>() {
            @Override
            public void changed(ObservableValue<? extends Projet> observable, Projet oldValue, Projet newValue) {
               if(projetsTableView.getSelectionModel().getSelectedItem()!=null)
               {
                   titreTextField.setText(projetsTableView.getSelectionModel().getSelectedItem().getTitre().trim());
                   descriptionTextField.setText(projetsTableView.getSelectionModel().getSelectedItem().getDescription().trim());
                   dateDebutDatePicker.setValue(LocalDate.parse(projetsTableView.getSelectionModel().getSelectedItem().getDateDebut().toString().trim()));
                   dateFinDatePicker.setValue(LocalDate.parse(projetsTableView.getSelectionModel().getSelectedItem().getDateFin().toString().trim()));

               }
            }
        });
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                projetObservableList.clear();
                projetObservableList.addAll(projetService.chercherParTitre(search.getText()));
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

    public void ajouterProjet() {
        try {
            if (isOneOfTheseTextFieldsEmpty(titreTextField) && descriptionTextField.getText().isEmpty()) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                String titre = titreTextField.getText().trim();
                String description = descriptionTextField.getText().trim();
                Date dateDebut = java.sql.Date.valueOf(dateDebutDatePicker.getValue());
                Date dateFin = java.sql.Date.valueOf(dateFinDatePicker.getValue());
                projetService.addProject(new Projet(titre, description, dateDebut, dateFin, LoginController.responsable));
                clear();
                populateProjectTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void modifierProjet() {
        try {
            if (isOneOfTheseTextFieldsEmpty(titreTextField) && descriptionTextField.getText().isEmpty()) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                int id = projetsTableView.getSelectionModel().getSelectedItem().getId();
                String titre = titreTextField.getText().trim();
                String description = descriptionTextField.getText().trim();
                Date dateDebut = java.sql.Date.valueOf(dateDebutDatePicker.getValue());
                Date dateFin = java.sql.Date.valueOf(dateFinDatePicker.getValue());
                Projet projet = new Projet(titre, description, dateDebut, dateFin, LoginController.responsable);
                projet.setId(id);
                projetService.update(projet);
                clear();
                populateProjectTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public void supprimerProjet(){
        Projet p = projetsTableView.getSelectionModel().getSelectedItem();
        if (p != null) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            setAlertContent("Êtes-vous sûr de vouloir supprimer ce projet ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    projetService.deleteProject(p);
                    populateProjectTableView();
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
        clearTextFields(titreTextField);
        descriptionTextField.clear();
        dateFinDatePicker.setValue(null);
        dateDebutDatePicker.setValue(null);
    }
    public void populateProjectTableView() {
        try {
            projetsTableView.getItems().clear();
            projetObservableList.addAll(projetService.getAllProjectsByResponsable(LoginController.responsable));
            projetsTableView.setItems(projetObservableList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
