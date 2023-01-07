package ma.enset.projectmanagement.services;

import ma.enset.projectmanagement.entities.Etat;
import ma.enset.projectmanagement.entities.Tache;

import java.util.Date;
import java.util.List;

public interface TacheService {

    List<Tache> getAllTache();
    Tache getTacheParId(int id);
    void addTache(Tache tache);
    void deleteTache(Tache tache);
    void updateTache(Tache tache);
    void prolongerTache(Tache o, Date dateFin);
    void modifierEtatTache(Tache o, Etat etat);
}
