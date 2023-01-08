package ma.enset.projectmanagement.services.Impl;

import ma.enset.projectmanagement.dao.Impl.IntervenantDaoImpl;
import ma.enset.projectmanagement.dao.Impl.ProjetDaoImpl;
import ma.enset.projectmanagement.dao.Impl.ResponsableDaoImpl;
import ma.enset.projectmanagement.dao.Impl.TacheDaoImpl;
import ma.enset.projectmanagement.dao.IntervenantDao;
import ma.enset.projectmanagement.dao.ProjetDao;
import ma.enset.projectmanagement.dao.ResponsableDao;
import ma.enset.projectmanagement.dao.TacheDao;
import ma.enset.projectmanagement.entities.Intervenant;
import ma.enset.projectmanagement.entities.Projet;
import ma.enset.projectmanagement.entities.Responsable;
import ma.enset.projectmanagement.entities.Tache;
import ma.enset.projectmanagement.services.CompositeServices;
import ma.enset.projectmanagement.services.ProjetService;
import ma.enset.projectmanagement.utils.files.FileUtils;

import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Queue;

public class CompositeServicesImpl implements CompositeServices {

    ProjetDao projetDao = new ProjetDaoImpl();
    TacheDao tacheDao = new TacheDaoImpl();
    IntervenantDao intervenantDao = new IntervenantDaoImpl();
    ResponsableDao responsableDao = new ResponsableDaoImpl();
    ProjetService projetService = new ProjetServiceImpl(projetDao);


    @Override
    public String exportProject(int projectId) {
        final Projet projet = this.projetDao.getProjetWithResposableByProjectId(projectId);
        final Queue<String> lines = mapperProjectToLines(projet);
        return FileUtils.mapperToFIle(lines, projet.getTitre());
    }

    private Queue<String> mapperProjectToLines(Projet projet) {
        final Queue<String> lines = new ArrayDeque<>();
        lines.add(projet.mapperToLine());
        lines.add(projet.getResponsable().mapperToLine());
        this.tacheDao.getTasksOfProject(projet.getId()).forEach(task -> {
            lines.add(task.mapperToLine());
            lines.add(task.getIntervenant().mapperToLine());
        });
        return lines;
    }

    @Override
    public Projet importerProject(String pathName) throws ParseException {
        final Projet projet = new Projet();
        Queue<String> lines = FileUtils.mapperFromFile(pathName);
        mapperProjectInfos(projet, lines);
        this.responsableDao.ajouter(projet.getResponsable());
        this.projetDao.ajouter(projet);
        int projectId = this.projetDao.findProjectIdByTitre(projet.getTitre());
        projet.setId(projectId);
        savetasksAndIntervenants(projet);
        return projet;
    }

    private void savetasksAndIntervenants(Projet projet) {
        projet.getIntervenants()
                        .forEach(intervenant -> this.intervenantDao.ajouter(intervenant));
        projet.getTaches().forEach(tache -> {
            tache.setProject(projet);
            this.tacheDao.ajouter(tache);
        });
    }

    private static void mapperProjectInfos(Projet projet, Queue<String> lines) throws ParseException {
        for (String line : lines) {
            if (line.startsWith(Projet.PROJECT))
                projet.mapperInfosFromLine(line);
            else if (line.startsWith(Responsable.RESPONSABLE))
                projet.setResponsable(Responsable.mapperInfosFromLine(line));
            else if (line.startsWith(Tache.TACHE))
                projet.addTache(Tache.mapperInfosFromLine(line));
            else if (line.startsWith(Intervenant.INTERVENANT))
                projet.addIntervenant(Intervenant.mapperInfosFromLine(line));
        }
    }

}
