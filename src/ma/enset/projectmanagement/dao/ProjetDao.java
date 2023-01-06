package ma.enset.projectmanagement.dao;

import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.entities.Responsable;

import java.util.List;

public interface ProjetDao extends CrudDao<Projet> {
    List<Projet> chercherParTitre(String titreProjet);
    List<Projet> findAllProjectByResponsable(Responsable responsable);

}
