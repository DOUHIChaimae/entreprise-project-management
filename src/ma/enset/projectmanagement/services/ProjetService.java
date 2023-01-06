package ma.enset.projectmanagement.services;

import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.entities.Responsable;

import java.util.List;

public interface ProjetService {
    void addProject(Projet project);
    void deleteProject(Projet project);
    Projet update(Projet projet);
    List<Projet> getAllProjectsByResponsable(Responsable responsable);
    List<Projet> chercherParTitre(String titreProjet);
}
