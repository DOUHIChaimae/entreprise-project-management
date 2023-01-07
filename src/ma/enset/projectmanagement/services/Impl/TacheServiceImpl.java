package ma.enset.projectmanagement.services.Impl;

import ma.enset.projectmanagement.dao.TacheDao;
import ma.enset.projectmanagement.entities.Etat;
import ma.enset.projectmanagement.entities.Tache;
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
    public void modifierEtatTache(Tache tache, Etat etat) {
        tacheDao.modifierEtatTache(tache,etat);
    }
}
