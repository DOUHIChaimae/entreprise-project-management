package ma.enset.projectmanagement;

import ma.enset.projectmanagement.dao.Impl.MaterielDaoImpl;
import ma.enset.projectmanagement.dao.Impl.ProjetDaoImpl;
import ma.enset.projectmanagement.dao.MaterielDao;
import ma.enset.projectmanagement.entities.Materiel;
import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.entities.Responsable;
import ma.enset.projectmanagement.services.Impl.MaterielServiceImpl;
import ma.enset.projectmanagement.services.Impl.ProjetServiceImpl;
import ma.enset.projectmanagement.services.MaterielService;
import ma.enset.projectmanagement.services.ProjetService;

import java.util.Date;
import java.util.List;

public class ProjectManagementConsole {
    public static void main(String[] args) {
        //Project
        ProjetDaoImpl projectDao = new ProjetDaoImpl();
        ProjetService projetService = new ProjetServiceImpl(projectDao);
        Responsable responsable1 = new Responsable("m6072345", "daabal", "sokaina", "0604230472", "sokainadaabal@gmail.com", "sokaina");
        Projet project = new Projet();
        project.setTitre("1 er projet");
        project.setDateDebut(new Date(10 / 2 / 2022));
        project.setDateFin(new Date(18 / 3 / 2023));
        project.setDescription("Ceci est un projet dédié au la gestion des projets de l'entreprise");
        project.setResponsable(responsable1);
        //Add a project
        //projetService.addProject(project);
        //delete a project
        //projetService.deleteProject(projectDao.findById(4));
        //update a project
        //projetService.update(projectDao.findById(3));
        //retourner la liste des projets
        //List<Projet> projects = projetService.getAllProjectsByResponsable(responsable1);
        //for (Projet projet : projects) {
        //System.out.println(projet);
        //}
        //cherché un projet par matricule
        //System.out.println("Le projet recherché " + projetService.chercherParTitre("1er projet"));
        //Materiel
        //Ajouter un matériel
        MaterielDao materielDao = new MaterielDaoImpl();
        Materiel materiel = new Materiel();
        materiel.setNomMateriel("materielNew");
        materiel.setMatricule("matriculeMatériel");
        MaterielService materielService = new MaterielServiceImpl(materielDao);
        //materielService.addMateriel(materiel);
        //supprimer un materiel
        //materielService.deleteMateriel(materiel);
        List<Materiel> materiels = materielService.getAllMateriel();
        for (Materiel m:materiels){
            System.out.println(m);
        }
        //rechercher materiel
        System.out.println("le materiel cherche est " + materielDao.chercherParNomMateriel("materielNew"));
    }
}
