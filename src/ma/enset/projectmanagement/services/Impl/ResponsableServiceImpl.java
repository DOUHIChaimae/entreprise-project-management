package ma.enset.projectmanagement.services.Impl;

import ma.enset.projectmanagement.dao.ResponsableDao;
import ma.enset.projectmanagement.entities.Responsable;
import ma.enset.projectmanagement.services.ResponsableService;

public class ResponsableServiceImpl implements ResponsableService {
    private ResponsableDao responsableDao;

    public ResponsableServiceImpl(ResponsableDao responsableDao) {
        this.responsableDao = responsableDao;
    }

    @Override
    public Responsable login(Responsable responsable) {
        return responsableDao.login(responsable);
    }
}
