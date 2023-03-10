package ma.enset.projectmanagement.dao.Impl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ma.enset.projectmanagement.dao.ResponsableDao;
import ma.enset.projectmanagement.dao.SingletonConnexionDB;
import ma.enset.projectmanagement.entities.Responsable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResponsableDaoImpl implements ResponsableDao {

    Connection connection;

    public ResponsableDaoImpl() {
        this.connection = SingletonConnexionDB.getConnections();
    }

    @Override
    public Responsable login(Responsable responsable) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT *from RESPONSABLE WHERE email=? and motDePasse=?;");
            ps.setString(1, responsable.getEmail());
            ps.setString(2, responsable.getMotDePasse());
            ResultSet set = ps.executeQuery();
            if (set.next()) {
                Responsable responsable1 = new Responsable();
                responsable1.setMatricule(set.getString("matricule"));
                responsable1.setNom(set.getString("nom"));
                responsable1.setPrenom(set.getString("prenom"));
                responsable1.setEmail(set.getString("email"));
                responsable1.setNumeroTel(set.getString("numeroTel"));
                responsable1.setMotDePasse(set.getString("motDePasse"));
                return responsable1;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    @Override
    public Responsable ajouter(Responsable o) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into RESPONSABLE(matricule,nom, prenom,numeroTel,email, motDePasse)" +
                    "values (?,?,?,?,?, ?)");
            preparedStatement.setString(1, o.getMatricule());
            preparedStatement.setString(2, o.getNom());
            preparedStatement.setString(3, o.getPrenom());
            preparedStatement.setString(4, o.getNumeroTel());
            preparedStatement.setString(5, o.getEmail());
            preparedStatement.setString(6, "123");
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return o;
    }

    @Override
    public List<Responsable> findAll() {
        return null;
    }

    @Override
    public Responsable findById(int id) {
        return null;
    }


    @Override
    public boolean supprimer(Responsable o) {
        return false;
    }

    @Override
    public Responsable modifier(Responsable responsable) {
        try{
            PreparedStatement pstm=connection.prepareStatement("update RESPONSABLE set NOM=?,PRENOM=?,NUMEROTEL=?,EMAIL=?, MOTDEPASSE=? where MATRICULE=?");
            pstm.setString(1,responsable.getNom());
            pstm.setString(2,responsable.getPrenom());
            pstm.setString(3,responsable.getNumeroTel());
            pstm.setString(4,responsable.getEmail());
            pstm.setString(5,responsable.getMotDePasse());
            pstm.setString(6,responsable.getMatricule());
            pstm.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return responsable;
    }
}
