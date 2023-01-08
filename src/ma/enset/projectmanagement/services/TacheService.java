package ma.enset.projectmanagement.services;

import ma.enset.projectmanagement.entities.*;

import java.util.Date;
import java.util.List;

public interface TacheService {

    List<Tache> getAllTache();
    Tache getTacheParId(int id);
    void addTache(Tache tache);
    void deleteTache(Tache tache);
    void updateTache(Tache tache);
    void prolongerTache(Tache o, Date dateFin);
    void modifierEtatTache(Tache o);
    List<Tache> getTacheProjetRespo(Responsable responsable);

    List<Tache> rechercherParMot(String titre);

    List<Tache> tacheProjet(Projet projet);

    List<Tache> tacheProjetIntervenant(Intervenant intervenant);


}
