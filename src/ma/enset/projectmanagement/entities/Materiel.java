package ma.enset.projectmanagement.entities;

public class Materiel {
    private String matricule;

    private String nomMateriel;

    public Materiel() {
    }

    public Materiel(String matricule, String nomMateriel) {
        this.nomMateriel = nomMateriel;
        this.matricule = matricule;

    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomMateriel() {
        return nomMateriel;
    }

    public void setNomMateriel(String nomMateriel) {
        this.nomMateriel = nomMateriel;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Matériel\n")
                .append("matricule : ")
                .append(matricule)
                .append("\nnom materiel")
                .append(nomMateriel);
        return str.toString();
    }
}
