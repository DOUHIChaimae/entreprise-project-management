package ma.enset.projectmanagement.dao;

import ma.enset.projectmanagement.entities.Materiel;

import java.util.List;

public interface MaterielDao extends CrudDao<Materiel>{
    List<Materiel> chercherParNomMateriel(String nomMateriel);
}
