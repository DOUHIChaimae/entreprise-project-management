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
import ma.enset.projectmanagement.dao.Impl.ProjetDaoImpl;
import ma.enset.projectmanagement.dao.Impl.TacheDaoImpl;
import ma.enset.projectmanagement.entities.Etat;
import ma.enset.projectmanagement.entities.Tache;
import ma.enset.projectmanagement.services.Impl.IntervenantServiceImpl;
import ma.enset.projectmanagement.services.Impl.ProjetServiceImpl;
import ma.enset.projectmanagement.services.Impl.TacheServiceImpl;
import ma.enset.projectmanagement.services.IntervenantService;
import ma.enset.projectmanagement.services.ProjetService;
import ma.enset.projectmanagement.services.TacheService;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class IntervenantTacheController implements Initializable {

    @FXML
    private TextField titreTextField;
    @FXML
    ComboBox<Etat> etatBox = new ComboBox();

    @FXML
    private TextField search;
    TacheService tacheService;
    ProjetService projetService;
    IntervenantService intervenantService;

    @FXML
    private TableView<Tache> tachesTableView;
    Alert alert;

    ObservableList<Etat> list
            = FXCollections.observableArrayList(Etat.A_FAIRE,Etat.EN_DEVELOPMENT, Etat.TERMINER);

    ObservableList<Tache> tacheObservableList = FXCollections.observableArrayList();

    public IntervenantTacheController() {
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
        etatBox.setItems(list);
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
                    etatBox.setValue(tachesTableView.getSelectionModel().getSelectedItem().getEtat());
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


    public void modifierTache() {
        try {
            if (isOneOfTheseTextFieldsEmpty(titreTextField)) {
                alert.setAlertType(Alert.AlertType.WARNING);
                setAlertContent("Veuillez remplir tous les champs correctement !");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) alert.close();
            } else {
                tacheService.modifierEtatTache(new Tache(1,Etat.valueOf(etatBox.getSelectionModel().getSelectedItem().toString())));
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



    public void populateTacheTableView() {
        try {
            tachesTableView.getItems().clear();
            tacheObservableList.addAll(tacheService.tacheProjetIntervenant(LoginController.intervenant));
            tachesTableView.setItems(tacheObservableList);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
