package ma.enset.projectmanagement.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Projet {
    private int id;
    private String titre;
    private String description;
    private Date dateDebut;
    private Date dateFin;
    private Responsable responsable;
    private List<Tache> taches = new ArrayList<>();
    private List<Intervenant> intervenants = new ArrayList<>();

    public Projet() {
    }

    public Projet(String titre, String description, Date dateDebut, Date dateFin) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Projet(String titre, String description, Date dateDebut, Date dateFin, Responsable responsable) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.responsable = responsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public List<Tache> getTaches() {
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Intervenant> getIntervenants() {
        return intervenants;
    }

    public void setIntervenants(List<Intervenant> intervenants) {
        this.intervenants = intervenants;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder().append(titre);

        return str.toString();

    }
}
