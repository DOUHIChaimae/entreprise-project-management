package ma.enset.projectmanagement.services;

import ma.enset.projectmanagement.entities.Intervenant;

import java.util.List;

public interface IntervenantService {

    List<Intervenant> getAllIntervenants();
    Intervenant getIntervenantsParMatricule(String matricule);
    Intervenant getIntervenantParId(int id);
    Intervenant getIntervenantsParNom(String nom);
    void  addIntervenants(Intervenant i);
    void deleteIntervenants(Intervenant  i);
    void updateIntervenants(Intervenant i);
}
