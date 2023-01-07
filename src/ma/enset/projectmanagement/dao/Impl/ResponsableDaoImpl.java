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
                return responsable1;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
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
    public Responsable ajouter(Responsable o) {
        return null;
    }

    @Override
    public boolean supprimer(Responsable o) {
        return false;
    }

    @Override
    public Responsable modifier(Responsable o) {
        return null;
    }
}
