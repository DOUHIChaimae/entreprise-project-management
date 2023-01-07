package ma.enset.projectmanagement.entities;

import java.util.Date;

public class Tache {
    private int id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private String description;
    private Projet projet;
    private Intervenant intervenant;
    private Etat etat;

    public Tache() {
    }

    public Tache(Date dateDebut, Date dateFin, String description) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
    }

    public Tache(int id, Date dateDebut, Date dateFin, String description, Projet projet, Etat etat) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.projet = projet;
        this.etat = etat;
    }

    public Tache(String titre, Date dateDebut, Date dateFin, String description, Projet projet, Intervenant intervenant, Etat etat) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.projet = projet;
        this.intervenant = intervenant;
        this.etat = etat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(java.sql.Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Projet getProject() {
        return projet;
    }

    public void setProject(Projet projet) {
        this.projet = projet;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Tache d'id" + id +
                "\ntitre : " + titre +
                "\ndateDebut : " + dateDebut +
                "\ndateFin : " + dateFin +
                "\ndescription :" + description +
                "\nCette tache est afféctée aux projets " + projet +
                "intervenant" + intervenant +
                "\netat : " + etat;
    }
}
