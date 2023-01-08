package ma.enset.projectmanagement.dao.Impl;

import ma.enset.projectmanagement.dao.SingletonConnexionDB;
import ma.enset.projectmanagement.dao.TacheDao;
import ma.enset.projectmanagement.entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TacheDaoImpl implements TacheDao {
    Connection connection;

    public TacheDaoImpl() {
        this.connection = SingletonConnexionDB.getConnections();
    }

    @Override
    public List<Tache> findAllTache(Responsable responsable) {
        List<Tache> taches = new ArrayList<>();
        ProjetDaoImpl projetDao = new ProjetDaoImpl();
        IntervenantDaoImpl intervenantDao=new IntervenantDaoImpl();
        List<Projet> projets = projetDao.findAllProjectByResponsable(responsable);
        for (Projet projet : projets) {
            try {
                PreparedStatement stm = connection.prepareStatement("select T.ID as IdTache,T.TITRE as titleTache,T.dateDebut as dateDebutTache,T.dateFin as dateFinTache,T.ETAT,T.DESCRIPTION as descriptionTache,T.etat,P.ID,P.titre,P.description,P.dateDebut,P.dateFin,T.intervenant  from TACHE T,PROJET P where T.PROJET_ID=P.ID");
                ResultSet rs = stm.executeQuery();
                while (rs.next()) {
                    Tache tache = new Tache();
                    tache.setId(rs.getInt("IdTache"));
                    tache.setTitre(rs.getString("titleTache"));
                    tache.setDateDebut(rs.getDate("dateDebutTache"));
                    tache.setDateFin(rs.getDate("dateFinTache"));
                    tache.setEtat(Etat.valueOf(rs.getString("etat")));
                    tache.setDescription(rs.getString("descriptionTache"));
                    projet.setId(rs.getInt("id"));
                    projet.setTitre(rs.getString("titre"));
                    projet.setDescription(rs.getString("description"));
                    projet.setDateDebut(rs.getDate("dateDebut"));
                    projet.setDateDebut(rs.getDate("dateFin"));
                    tache.setProject(projet);
                    tache.setIntervenant(intervenantDao.findByMatricule(rs.getString("intervenant")));
                    taches.add(tache);
                }
                return taches;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public List<Tache> findAll() {
        return null;
    }

    @Override
    public Tache findById(int id) {
        Connection connection = SingletonConnexionDB.getConnections();
        Tache tache = new Tache();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from TACHE where ID=?");
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                tache.setId(rs.getInt("ID"));
                tache.setTitre(rs.getString("titre"));
                tache.setDateDebut(rs.getDate("dateDebut"));
                tache.setDateFin(rs.getDate("dateFin"));
                tache.setDescription(rs.getString("description"));
                tache.setEtat(Etat.valueOf(rs.getString("etat")));
            }
            return tache;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tache> findByTitre(String titre) {
        Connection connection = SingletonConnexionDB.getConnections();
        List<Tache> taches = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from TACHE where titre like?");
            stm.setString(1, "%"+titre+"%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Tache tache=new Tache();
                tache.setId(rs.getInt("ID"));
                tache.setTitre(rs.getString("titre"));
                tache.setDateDebut(rs.getDate("dateDebut"));
                tache.setDateFin(rs.getDate("dateFin"));
                tache.setDescription(rs.getString("description"));
                tache.setEtat(Etat.valueOf(rs.getString("etat")));
                taches.add(tache);
            }
            return taches;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Tache ajouter(Tache tache) {
        try {
            PreparedStatement pstm = connection.prepareStatement("insert into TACHE(id,titre,description,dateDebut,dateFin,etat,projet_id,intervenant)" +
                    "   values(?,?,?,?,?,?,?,?)");
            pstm.setInt(1, tache.getId());
            pstm.setString(2, tache.getTitre());
            pstm.setString(3, tache.getDescription());
            pstm.setDate(4, new java.sql.Date(tache.getDateDebut().getTime()));
            pstm.setDate(5, new java.sql.Date(tache.getDateFin().getTime()));
            pstm.setString(6, tache.getEtat().toString());
            pstm.setInt(7, tache.getProject().getId());
            pstm.setString(8, tache.getIntervenant().getMatricule());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tache;
    }

    @Override
    public boolean supprimer(Tache tache) {
        try {
            PreparedStatement pstm = connection.prepareStatement("delete from TACHE where ID=?");
            pstm.setInt(1, tache.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    @Override
    public Tache modifier(Tache o) {
        try {
            PreparedStatement pstm = connection.prepareStatement("update TACHE set titre=?,description=?,dateDebut=?,dateFin=?,etat=?, projet_id=? , intervenant=? where ID=?");
            pstm.setString(1, o.getTitre());
            pstm.setString(2, o.getDescription());
            pstm.setDate(3, new java.sql.Date(o.getDateDebut().getTime()));
            pstm.setDate(4, new java.sql.Date(o.getDateFin().getTime()));
            pstm.setString(5, o.getEtat().toString());
            pstm.setInt(6, o.getProject().getId());
            pstm.setString(7, o.getIntervenant().getMatricule());
            pstm.setInt(8, o.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public List<Tache> tachesProjet(Projet projet) {
        List<Tache> taches = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from TACHE where projet_id=?");
            stm.setInt(1, projet.getId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Tache tache=new Tache();
                tache.setId(rs.getInt("ID"));
                tache.setTitre(rs.getString("titre"));
                tache.setDateDebut(rs.getDate("dateDebut"));
                tache.setDateFin(rs.getDate("dateFin"));
                tache.setDescription(rs.getString("description"));
                tache.setEtat(Etat.valueOf(rs.getString("etat")));
                taches.add(tache);
            }
            return taches;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void prolongerTache(Tache tache, Date dateFin) {
        try {
            PreparedStatement pstm = connection.prepareStatement("update TACHE set dateFin=? where ID=?");
            pstm.setDate(1, new java.sql.Date(dateFin.getTime()));
            pstm.setInt(2, tache.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierEtatTache(Tache tache) {
        try {
            PreparedStatement pstm = connection.prepareStatement("update TACHE set etat=? where ID=?");
            pstm.setString(1, tache.getEtat().toString());
            pstm.setInt(2, tache.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Tache> tachesIntervenant(Intervenant intervenant) {
        List<Tache> taches = new ArrayList<>();
        try {
            PreparedStatement stm = connection.prepareStatement("select * from TACHE where intervenant=?");
            stm.setString(1, intervenant.getMatricule());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Tache tache=new Tache();
                tache.setId(rs.getInt("ID"));
                tache.setTitre(rs.getString("titre"));
                tache.setDateDebut(rs.getDate("dateDebut"));
                tache.setDateFin(rs.getDate("dateFin"));
                tache.setDescription(rs.getString("description"));
                tache.setEtat(Etat.valueOf(rs.getString("etat")));
                taches.add(tache);
            }
            return taches;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
