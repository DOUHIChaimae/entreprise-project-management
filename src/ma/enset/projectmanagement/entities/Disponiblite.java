package ma.enset.projectmanagement.entities;

import java.util.Date;

public class Disponiblite {
    private int id;
    private Date dureeDisponiblite;
    private Materiel materiel;
    private Tache tache;

    public Disponiblite() {
    }

    public Disponiblite(Date dureeDisponiblite, Materiel materiel, Tache tache) {
        this.dureeDisponiblite = dureeDisponiblite;
        this.materiel = materiel;
        this.tache = tache;
    }

    public Disponiblite(int id, Date dureeDisoiniplite, Materiel materiel, Tache tache) {
        this.id = id;
        this.dureeDisponiblite = dureeDisoiniplite;
        this.materiel = materiel;
        this.tache = tache;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDureeDisponiblite() {
        return dureeDisponiblite;
    }

    public void setDureeDisponiblite(Date dureeDisponiblite) {
        this.dureeDisponiblite = dureeDisponiblite;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    @Override
    public String toString() {
        return "Desponiblite{" +
                "id=" + id +
                ", dureeDisoiniplite=" + dureeDisponiblite +
                ", materiel=" + materiel +
                ", tache=" + tache +
                '}';
    }
}
