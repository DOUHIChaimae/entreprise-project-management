package ma.enset.projectmanagement.dao;

import ma.enset.projectmanagement.entities.Intervenant;

public interface IntervenantDao extends CrudDao<Intervenant>{
    Intervenant findByNom(String nom);
    Intervenant findByMatricule(String matricule);

}
