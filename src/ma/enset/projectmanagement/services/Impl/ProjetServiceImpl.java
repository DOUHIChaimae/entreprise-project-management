package ma.enset.projectmanagement.services.Impl;

import ma.enset.projectmanagement.dao.ProjetDao;
import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.entities.Responsable;
import ma.enset.projectmanagement.services.ProjetService;
import java.util.List;


public class ProjetServiceImpl implements ProjetService {
    ProjetDao projetDao;

    public ProjetServiceImpl(ProjetDao projetDao) {
        this.projetDao = projetDao;
    }

    @Override
    public void addProject(Projet project) {
        projetDao.ajouter(project);
    }

    @Override
    public void deleteProject(Projet project) {
        projetDao.supprimer(project);
    }

    @Override
    public Projet update(Projet projet) {
        return projetDao.modifier(projet);
    }

    @Override
    public List<Projet> getAllProjectsByResponsable(Responsable responsable) {
        return projetDao.findAllProjectByResponsable(responsable);
    }

    @Override
    public List<Projet> chercherParTitre(String titreProjet) {
        return projetDao.chercherParTitre(titreProjet);
    }
}
