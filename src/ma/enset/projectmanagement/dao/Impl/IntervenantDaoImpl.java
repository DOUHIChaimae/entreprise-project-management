package ma.enset.projectmanagement.dao.Impl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.enset.projectmanagement.dao.IntervenantDao;
import ma.enset.projectmanagement.dao.SingletonConnexionDB;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.entities.Responsable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntervenantDaoImpl implements IntervenantDao {
    Connection connection;
    public IntervenantDaoImpl() {
        this.connection = SingletonConnexionDB.getConnections();
    }

    @Override
    public List<Intervenant> findAll() {
        List<Intervenant> intervenants=new ArrayList<>();
        try{
            PreparedStatement stm=connection.prepareStatement("select * from INTERVENANT");
            ResultSet rs=stm.executeQuery();
            while (rs.next()){
                Intervenant intervenant=new Intervenant();
                intervenant.setMatricule(rs.getString("MATRICULE"));
                intervenant.setNom(rs.getString("NOM"));
                intervenant.setPrenom(rs.getString("PRENOM"));
                intervenant.setNumeroTel(rs.getString("NUMEROTEL"));
                intervenant.setEmail(rs.getString("EMAIL"));
                intervenant.setMotDePasse(rs.getString("MOTDEPASSE"));
                intervenants.add(intervenant);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return intervenants;
    }

    @Override
    public Intervenant login(Intervenant intervenant) {
        try {
            PreparedStatement ps2= connection.prepareStatement("SELECT *from INTERVENANT WHERE email=? and motDePasse=?;");
            ps2.setString(1,intervenant.getEmail());
            ps2.setString(2,intervenant.getMotDePasse());
            ResultSet set2 = ps2.executeQuery();
            if (set2.next()) {
                Intervenant intervenant1 = new Intervenant();
                intervenant1.setMatricule(set2.getString("matricule"));
                intervenant1.setNom(set2.getString("nom"));
                intervenant1.setPrenom(set2.getString("prenom"));
                intervenant1.setEmail(set2.getString("email"));
                intervenant1.setNumeroTel(set2.getString("numeroTel"));
               return  intervenant1;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Intervenant findById(int id) {
        Intervenant intervenant=new Intervenant();
        try{
            PreparedStatement stm=connection.prepareStatement("select * from INTERVENANT where MATRICULE=?");
            stm.setInt(1,id);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
                intervenant.setMatricule(rs.getString("MATRICULE"));
                intervenant.setNom(rs.getString("NOM"));
                intervenant.setPrenom(rs.getString("PRENOM"));
                intervenant.setNumeroTel(rs.getString("numeroTel"));
                intervenant.setEmail(rs.getString("EMAIL"));
                intervenant.setMotDePasse(rs.getString("MOTDEPASS"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return intervenant;
    }

    public Intervenant findByNom(String nom) {
        Intervenant intervenant=new Intervenant();
        try{
            PreparedStatement stm=connection.prepareStatement("select * from INTERVENANT where NOM=?");
            stm.setString(1,nom);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
                intervenant.setMatricule(rs.getString("MATRICULE"));
                intervenant.setNom(rs.getString("NOM"));
                intervenant.setPrenom(rs.getString("PRENOM"));
                intervenant.setNumeroTel(rs.getString("numeroTel"));
                intervenant.setEmail(rs.getString("EMAIL"));
                intervenant.setMotDePasse(rs.getString("MOTDEPASSE"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return intervenant;
    }

    @Override
    public Intervenant findByMatricule(String matricule) {
        Intervenant intervenant=new Intervenant();
        try{
            PreparedStatement stm=connection.prepareStatement("select * from INTERVENANT where MATRICULE=?");
            stm.setString(1,matricule);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
                intervenant.setMatricule(rs.getString("MATRICULE"));
                intervenant.setNom(rs.getString("NOM"));
                intervenant.setPrenom(rs.getString("PRENOM"));
                intervenant.setNumeroTel(rs.getString("numeroTel"));
                intervenant.setEmail(rs.getString("EMAIL"));
                intervenant.setMotDePasse(rs.getString("MOTDEPASSE"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return intervenant;
    }

    @Override
    public Intervenant ajouter(Intervenant intervenant) {
        try{
            PreparedStatement pstm=connection.prepareStatement("insert into INTERVENANT(MATRICULE,NOM,PRENOM,NUMEROTEL,EMAIL,MOTDEPASSE)" +
                    "   values(?,?,?,?,?,?)");
            pstm.setString(1,intervenant.getMatricule());
            pstm.setString(2,intervenant.getNom());
            pstm.setString(3,intervenant.getPrenom());
            pstm.setString(4,intervenant.getNumeroTel());
            pstm.setString(5,intervenant.getEmail());
            pstm.setString(6,intervenant.getMotDePasse());
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return intervenant;
    }

    @Override
    public boolean supprimer(Intervenant intervenant) {
        try {
            PreparedStatement pstm=connection.prepareStatement("delete from INTERVENANT where MATRICULE=?");
            pstm.setString(1,intervenant.getMatricule());
            pstm.executeUpdate();
        }catch (SQLException e){
            return false;
        }
        return true;
    }

    @Override
    public Intervenant modifier(Intervenant intervenant) {
        try{
            PreparedStatement pstm=connection.prepareStatement("update INTERVENANT set NOM=?,PRENOM=?,NUMEROTEL=?,EMAIL=?, MOTDEPASSE=? where MATRICULE=?");
            pstm.setString(1,intervenant.getNom());
            pstm.setString(2,intervenant.getPrenom());
            pstm.setString(3,intervenant.getNumeroTel());
            pstm.setString(4,intervenant.getEmail());
            pstm.setString(5,intervenant.getMotDePasse());
            pstm.setString(6,intervenant.getMatricule());
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return intervenant;
    }
}
