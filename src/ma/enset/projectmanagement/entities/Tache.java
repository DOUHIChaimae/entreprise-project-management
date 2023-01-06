package ma.enset.projectmanagement.entities;

import java.util.Date;

public class Tache {
    private int id;
    private Date dateDebut;
    private Date dateFin;
    private String description;
    private Projet project;
    private Etat etat;
    public Tache() {
    }

    public Tache(Date dateDebut, Date dateFin, String description) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
    }

    public Tache(Date dateDebut, Date dateFin, String description, Projet project, Etat etat) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.description = description;
        this.project = project;
        this.etat = etat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
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

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Projet getProject() {
        return project;
    }

    public void setProject(Projet project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", descriptionTache='" + description + '\'' +
                ", project=" + project +
                ", etat=" + etat +
                '}';
    }
}
