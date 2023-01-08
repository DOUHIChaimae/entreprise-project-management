package ma.enset.projectmanagement.services.Impl;

import ma.enset.projectmanagement.dao.TacheDao;
import ma.enset.projectmanagement.entities.*;
import ma.enset.projectmanagement.services.TacheService;

import java.util.Date;
import java.util.List;

public class TacheServiceImpl implements TacheService {

    private TacheDao tacheDao;

    public TacheServiceImpl(TacheDao tacheDao) {
        this.tacheDao = tacheDao;
    }

    @Override
    public List<Tache> getAllTache() {
        return tacheDao.findAll();
    }

    @Override
    public Tache getTacheParId(int id) {
        return tacheDao.findById(id);
    }

    @Override
    public void addTache(Tache tache) {
        tacheDao.ajouter(tache);
    }
    @Override
    public void deleteTache(Tache tache) {
        tacheDao.supprimer(tache);
    }

    @Override
    public void updateTache(Tache tache) {
        tacheDao.modifier(tache);
    }
    @Override
    public void prolongerTache(Tache tache, Date dateFin) {
        tacheDao.prolongerTache(tache,dateFin);
    }

    @Override
    public void modifierEtatTache(Tache tache) {
        tacheDao.modifierEtatTache(tache);
    }

    @Override
    public List<Tache> getTacheProjetRespo(Responsable responsable) {
        return tacheDao.findAllTache(responsable);
    }

    @Override
    public List<Tache> rechercherParMot(String titre) {
        return tacheDao.findByTitre(titre);
    }

    @Override
    public List<Tache> tacheProjet(Projet projet) {
        return tacheDao.tachesProjet(projet);
    }

    @Override
    public List<Tache> tacheProjetIntervenant(Intervenant intervenant) {
        return tacheDao.tachesIntervenant(intervenant);
    }

}
