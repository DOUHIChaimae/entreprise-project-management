package ma.enset.projectmanagement.presentation.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import ma.enset.projectmanagement.dao.Impl.IntervenantDaoImpl;
import ma.enset.projectmanagement.dao.Impl.ProjetDaoImpl;
import ma.enset.projectmanagement.dao.Impl.TacheDaoImpl;
import ma.enset.projectmanagement.entities.*;
import ma.enset.projectmanagement.services.Impl.IntervenantServiceImpl;
import ma.enset.projectmanagement.services.Impl.ProjetServiceImpl;
import ma.enset.projectmanagement.services.Impl.TacheServiceImpl;
import ma.enset.projectmanagement.services.IntervenantService;
import ma.enset.projectmanagement.services.ProjetService;
import ma.enset.projectmanagement.services.TacheService;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class tacheController implements Initializable {
    @FXML
    private TextField titreTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private DatePicker dateDebutDatePicker;
    @FXML
    private DatePicker dateFinDatePicker;
    @FXML
    private ComboBox projetComboBox;
    @FXML
    private ComboBox intervenantCombox;
    @FXML
    private ComboBox projetComboBox2;

    @FXML
    private TextField search;
    TacheService tacheService;
    ProjetService projetService;
    IntervenantService intervenantService;

    @FXML
    private TableView<Tache> tachesTableView;
    Alert alert;

    ObservableList<Tache> tacheObservableList = FXCollections.observableArrayList();

    public tacheController() {
        this.projetService = new ProjetServiceImpl(new ProjetDaoImpl());
        this.tacheService = new TacheServiceImpl(new TacheDaoImpl());
        this.intervenantService = new IntervenantServiceImpl(new IntervenantDaoImpl());
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

        projetComboBox.getItems().addAll(projetService.getAllProjectsByResponsable(LoginController.responsable));
        intervenantCombox.getItems().addAll(intervenantService.getAllIntervenants());
        projetComboBox2.getItems().addAll(projetService.getAllProjectsByResponsable(LoginController.responsable));

        tachesTableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tachesTableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("titre"));
        tachesTableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
        tachesTableView.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        tachesTableView.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        populateTacheTableView();
        tachesTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tache>() {
            @Override
            public void changed(ObservableValue<? extends Tache> observable, Tache oldValue, Tache newValue) {
                if (tachesTableView.getSelectionModel().getSelectedItem() != null) {
                    titreTextField.setText(tachesTableView.getSelectionModel().getSelectedItem().getTitre().trim());
                    descriptionTextField.setText(tachesTableView.getSelectionModel().getSelectedItem().getDescription().trim());
                    dateDebutDatePicker.setValue(LocalDate.parse(tachesTableView.getSelectionModel().getSelectedItem().getDateDebut().toString()));
                    dateFinDatePicker.setValue(LocalDate.parse(tachesTableView.getSelectionModel().getSelectedItem().getDateFin().toString()));
                    projetComboBox.setValue(tachesTableView.getSelectionModel().getSelectedItem().getProjet());
                    intervenantCombox.setValue(tachesTableView.getSelectionModel().getSelectedItem().getIntervenant());

                }
            }
        });
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tacheObservableList.clear();
                tacheObservableList.addAll(tacheService.rechercherParMot(search.getText()));
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

    public void clear() {
        clearTextFields(titreTextField);
        descriptionTextField.clear();
        dateDebutDatePicker.setValue(null);
        dateFinDatePicker.setValue(null);
        projetComboBox.getItems().clear();
        intervenantCombox.getItems().clear();
        projetComboBox.getItems().addAll(projetService.getAllProjectsByResponsable(LoginController.responsable));
        intervenantCombox.getItems().addAll(intervenantService.getAllIntervenants());
    }

    public void ajouterTache() {
        try {
            if (isOneOfTheseTextFieldsEmpty(titreTextField)) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                String titre = titreTextField.getText().trim();
                String description = descriptionTextField.getText().trim();
                Date dateDebut = java.sql.Date.valueOf(dateDebutDatePicker.getValue());
                Date dateFin = java.sql.Date.valueOf(dateFinDatePicker.getValue());
                Projet projet = (Projet) projetComboBox.getSelectionModel().getSelectedItem();
                Intervenant intervenant = (Intervenant) intervenantCombox.getSelectionModel().getSelectedItem();
                tacheService.addTache(new Tache(titre, dateDebut, dateFin, description, projet, intervenant, Etat.A_FAIRE));
                clear();
                populateTacheTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    public void modifierTache() {
        try {
            if (isOneOfTheseTextFieldsEmpty(titreTextField)) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                String titre = titreTextField.getText().trim();
                String description = descriptionTextField.getText().trim();
                Date dateDebut = java.sql.Date.valueOf(dateDebutDatePicker.getValue());
                Date dateFin = java.sql.Date.valueOf(dateFinDatePicker.getValue());
                Projet projet = (Projet) projetComboBox.getSelectionModel().getSelectedItem();
                Intervenant intervenant = (Intervenant) intervenantCombox.getSelectionModel().getSelectedItem();
                tacheService.updateTache(new Tache(tachesTableView.getSelectionModel().getSelectedItem().getId(),titre, dateDebut, dateFin, description, projet, intervenant, tachesTableView.getSelectionModel().getSelectedItem().getEtat()));
                clear();
                populateTacheTableView();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                setAlertContent("L'opération s'est terminée avec succès !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void supprimerTache(){
        Tache p = tachesTableView.getSelectionModel().getSelectedItem();
        if (p != null) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            setAlertContent("Êtes-vous sûr de vouloir supprimer ce projet ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    tacheService.deleteTache(p);
                    populateTacheTableView();
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

    public void searchTacheController(){
        tacheObservableList.clear();
        tacheObservableList.addAll(tacheService.tacheProjet((Projet)projetComboBox2.getSelectionModel().getSelectedItem()));
    }
    public void populateTacheTableView() {
        try {
            tachesTableView.getItems().clear();
            tacheObservableList.addAll(tacheService.getTacheProjetRespo(LoginController.responsable));
            tachesTableView.setItems(tacheObservableList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
