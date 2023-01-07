package ma.enset.projectmanagement.services.Impl;

import ma.enset.projectmanagement.dao.Impl.MaterielDaoImpl;
import ma.enset.projectmanagement.dao.MaterielDao;
import ma.enset.projectmanagement.entities.Materiel;
import ma.enset.projectmanagement.services.MaterielService;

import java.util.List;

public class MaterielServiceImpl implements MaterielService {

    // TODO : the instantiation is redundant, delete the instantiation or add a default constructor
    MaterielDao materielDao = new MaterielDaoImpl();

    public MaterielServiceImpl(MaterielDao materielDao) {
        this.materielDao = materielDao;
    }

    @Override
    public void addMateriel(Materiel materiel) {
        materielDao.ajouter(materiel);
    }

    @Override
    public void deleteMateriel(Materiel materiel) {
        materielDao.supprimer(materiel);
    }

    @Override
    public List<Materiel> getAllMateriel() {
        return materielDao.findAll();
    }

    @Override
    public List<Materiel> chercherParNomMateriel(String nomMateriel) {
        return materielDao.chercherParNomMateriel(nomMateriel);
    }

    @Override
    public void updateMateriel(Materiel materiel) {
        materielDao.modifier(materiel);
    }
}
