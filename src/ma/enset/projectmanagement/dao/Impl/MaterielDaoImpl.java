package ma.enset.projectmanagement.dao.Impl;

import ma.enset.projectmanagement.dao.MaterielDao;
import ma.enset.projectmanagement.dao.SingletonConnexionDB;
import ma.enset.projectmanagement.entities.Materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterielDaoImpl implements MaterielDao {
    Connection connection;

    public MaterielDaoImpl() {
        this.connection = SingletonConnexionDB.getConnections();
    }

    @Override
    public List<Materiel> findAll() {
        List<Materiel> materiels = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select * FROM MATERIEL");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Materiel materiel = new Materiel();
                materiel.setMatricule(rs.getString("matricule"));
                materiel.setNomMateriel(rs.getString("nomMateriel"));
                materiels.add(materiel);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return materiels;
    }

    @Override
    public Materiel findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from MATERIEL where ID = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            Materiel materiel = new Materiel();
            if (rs.next()) {
                materiel.setMatricule(rs.getString("id"));
                materiel.setNomMateriel(rs.getString("nomMateriel"));
                return materiel;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }


    @Override
    public Materiel ajouter(Materiel materiel) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into MATERIEL(matricule,nomMateriel)" + "values (?,?)");
            preparedStatement.setString(1, materiel.getMatricule());
            preparedStatement.setString(2, materiel.getNomMateriel());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return materiel;
    }

    @Override
    public boolean supprimer(Materiel materiel) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from MATERIEL where matricule =?");
            preparedStatement.setString(1, materiel.getMatricule());
            preparedStatement.executeUpdate();
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Materiel modifier(Materiel materiel) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update MATERIEL set nomMateriel = ?" + " where matricule =?");
            preparedStatement.setString(1, materiel.getNomMateriel());
            preparedStatement.setString(2, materiel.getMatricule());
            preparedStatement.executeUpdate();
            return materiel;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Materiel> chercherParNomMateriel(String nomMateriel) {
        List<Materiel> materiels = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * from MATERIEL" + " where nomMateriel like ?");
            pstm.setString(1, "%" + nomMateriel + "%");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Materiel materiel = new Materiel();
                materiel.setMatricule(res.getString("matricule"));
                materiel.setNomMateriel(res.getString("nomMateriel"));
                materiels.add(materiel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materiels;
    }
}
