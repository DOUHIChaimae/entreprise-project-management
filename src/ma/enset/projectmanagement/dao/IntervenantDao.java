package ma.enset.projectmanagement.dao;

import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.entities.Responsable;

public interface IntervenantDao extends CrudDao<Intervenant>{
    Intervenant findByNom(String nom);
    Intervenant findByMatricule(String matricule);
    Intervenant login(Intervenant intervenant);

}
