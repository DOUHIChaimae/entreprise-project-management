package ma.enset.projectmanagement.entities;

import ma.enset.projectmanagement.utils.StringUtils;
import ma.enset.projectmanagement.utils.files.FileMappable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Intervenant implements FileMappable<Intervenant> {
    public static final String INTERVENANT = "INTERVENANT";

    private String matricule;
    private String nom;
    private String prenom;
    private String numeroTel;
    private String email;
    private String motDePasse;

    private List<Projet> projetList = new ArrayList<>();

    public Intervenant() {
    }

    public Intervenant(String matricule, String nom, String prenom, String numeroTel, String email, String motDePasse) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTel = numeroTel;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Intervenant(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Projet> getProjectList() {
        return projetList;
    }

    public void setProjectList(List<Projet> projetList) {
        this.projetList = projetList;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder().append(matricule);
        return str.toString();
    }
    @Override
    public String mapperToLine() {
        return String.join(StringUtils.SEMI_COLON,
                Arrays.asList(
                        INTERVENANT, matricule, nom, prenom, numeroTel, email
                ));
    }

    public static Intervenant mapperInfosFromLine(String line) {
        final Intervenant intervenant = new Intervenant();
        String[] infos = line.split(StringUtils.SEMI_COLON.toString());
        intervenant.setMatricule(infos[1]);
        intervenant.setNom(infos[2]);
        intervenant.setPrenom(infos[3]);
        intervenant.setNumeroTel(infos[4]);
        intervenant.setEmail(infos[5]);
        return intervenant;
    }
}
