package ma.enset.projectmanagement.dao;

import ma.enset.projectmanagement.entities.Etat;
import ma.enset.projectmanagement.entities.Responsable;
import ma.enset.projectmanagement.entities.Tache;

import java.util.Date;
import java.util.List;

public interface TacheDao extends CrudDao<Tache>{
    void prolongerTache(Tache tache, Date dateFin);
    void modifierEtatTache(Tache tache, Etat etat);
    List<Tache> findAllTache(Responsable responsable);
}
