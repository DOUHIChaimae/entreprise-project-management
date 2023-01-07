package ma.enset.projectmanagement.services;

import ma.enset.projectmanagement.entities.Responsable;

public interface ResponsableService {
    Responsable login(Responsable responsable);
    Responsable update(Responsable responsable);
}
