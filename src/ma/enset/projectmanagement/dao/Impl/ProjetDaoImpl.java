package ma.enset.projectmanagement.dao.Impl;

import ma.enset.projectmanagement.dao.ProjetDao;
import ma.enset.projectmanagement.dao.SingletonConnexionDB;
import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.entities.Responsable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetDaoImpl implements ProjetDao {
    Connection connection;

    public ProjetDaoImpl() {
        this.connection = SingletonConnexionDB.getConnections();
    }

    @Override
    public List<Projet> findAll() {
        return null;
    }

    @Override
    public Projet findById(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from PROJET where ID = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Projet project = new Projet();
            if (rs.next()) {
                project.setId(rs.getInt("id"));
                project.setTitre(rs.getString("titre"));
                project.setDescription(rs.getString("description"));
                project.setDateDebut(rs.getDate("datedebut"));
                project.setDateFin(rs.getDate("dateFin"));
                return project;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }


    @Override
    public Projet ajouter(Projet project) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into PROJET(titre,description,dateDebut,dateFin,responsable_id)" +
                    "values (?,?,?,?,?)");
            preparedStatement.setString(1, project.getTitre());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setDate(3, new Date(project.getDateDebut().getTime()));
            preparedStatement.setDate(4, new Date(project.getDateFin().getTime()));
            preparedStatement.setString(5, project.getResponsable().getMatricule());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return project;
    }

    @Override
    public boolean supprimer(Projet project) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from PROJET where id =?");
            preparedStatement.setLong(1,project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Projet modifier(Projet project) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update PROJET set titre =?,description=?,datedebut=?, dateFin=?" + " where id =?");
            preparedStatement.setString(1, project.getTitre());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setDate(3, new Date(project.getDateDebut().getTime()));
            preparedStatement.setDate(4, new Date(project.getDateFin().getTime()));
            preparedStatement.setLong(5, project.getId());
            preparedStatement.executeUpdate();
            return project;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Projet> chercherParTitre(String titreProjet) {
        List<Projet> projects = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * from PROJET,RESPONSABLE" +
                    " where titre like ? and PROJET.responsable_id=RESPONSABLE.matricule");
            pstm.setString(1, "%" + titreProjet + "%");
            ResultSet res = pstm.executeQuery();
            while (res.next()) {
                Projet project = new Projet();
                project.setId(res.getInt("id"));
                project.setTitre(res.getString("titre"));
                project.setDescription(res.getString("description"));
                project.setDateDebut(res.getDate("dateDebut"));
                project.setDateFin(res.getDate("dateFin"));
                Responsable responsable = new Responsable();
                responsable.setMatricule(res.getString("matricule"));
                responsable.setNom(res.getString("nom"));
                responsable.setPrenom(res.getString("prenom"));
                responsable.setEmail(res.getString("email"));
                project.setTitre(res.getString("titre"));
                project.setDescription(res.getString("description"));
                project.setDateDebut(res.getDate("datedebut"));
                project.setDateFin(res.getDate("dateFin"));
                project.setResponsable(responsable);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public List<Projet> findAllProjectByResponsable(Responsable responsable) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from PROJET P, RESPONSABLE R where P.responsable_id = R.matricule");
            ResultSet rs = statement.executeQuery();
            List<Projet> projects = new ArrayList<>();
            while (rs.next()) {
                Projet project = new Projet();
                project.setId(rs.getInt("id"));
                project.setTitre(rs.getString("titre"));
                project.setDescription(rs.getString("description"));
                project.setDateDebut(rs.getDate("datedebut"));
                project.setDateFin(rs.getDate("dateFin"));
                projects.add(project);
            }
            return projects;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}
