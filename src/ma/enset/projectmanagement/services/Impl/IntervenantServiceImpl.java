package ma.enset.projectmanagement.services.Impl;

import ma.enset.projectmanagement.dao.IntervenantDao;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.services.IntervenantService;

import java.util.List;

public class IntervenantServiceImpl implements IntervenantService {

    private IntervenantDao intervenantDao;

    public IntervenantServiceImpl(IntervenantDao intervenantDao) {
        this.intervenantDao = intervenantDao;
    }

    @Override
    public List<Intervenant> getAllIntervenants() {
        return intervenantDao.findAll();
    }

    @Override
    public Intervenant getIntervenantsParMatricule(String matricule) {
        return intervenantDao.findByMatricule(matricule);
    }

    @Override
    public Intervenant getIntervenantParId(int id) {
        return intervenantDao.findById(id);
    }


    @Override
    public Intervenant getIntervenantsParNom(String nom) {
        return intervenantDao.findByNom(nom);
    }

    @Override
    public void addIntervenants(Intervenant intervenant) {
        intervenantDao.ajouter(intervenant);
    }

    @Override
    public void deleteIntervenants(Intervenant intervenant) {
        intervenantDao.supprimer(intervenant);
    }

    @Override
    public void updateIntervenants(Intervenant intervenant) {
        intervenantDao.modifier(intervenant);
    }

    @Override
    public Intervenant login(Intervenant intervenant) {
        return intervenantDao.login(intervenant);
    }
}
