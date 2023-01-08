package ma.enset.projectmanagement.dao;

import ma.enset.projectmanagement.entities.*;

import java.util.Date;
import java.util.List;

public interface TacheDao extends CrudDao<Tache>{
    void prolongerTache(Tache tache, Date dateFin);
    void modifierEtatTache(Tache tache);
    List<Tache> findAllTache(Responsable responsable);
    List<Tache> findByTitre(String titre);

    List<Tache> tachesProjet(Projet projet);
    List<Tache> tachesIntervenant(Intervenant intervenant);
    public List<Tache> getTasksOfProject(int projectId);

}
