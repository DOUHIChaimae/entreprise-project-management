package ma.enset.projectmanagement.services;

import ma.enset.projectmanagement.entities.Materiel;

import java.util.List;

public interface MaterielService {
    void addMateriel(Materiel materiel);
    void deleteMateriel(Materiel materiel);
    List<Materiel> getAllMateriel();
    List<Materiel> chercherParNomMateriel(String nomMateriel);
    void updateMateriel(Materiel materiel);
}
