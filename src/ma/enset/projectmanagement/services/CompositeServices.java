package ma.enset.projectmanagement.services;

import ma.enset.projectmanagement.entities.Projet;

import java.text.ParseException;

public interface CompositeServices {

    String exportProject(int projectId);

    Projet importerProject(String pathName) throws ParseException;

}
