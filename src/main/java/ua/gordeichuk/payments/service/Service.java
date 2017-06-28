package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.entity.Entity;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.ExceptionMessages;

import java.util.List;
import java.util.Optional;

/**
 * Created by Валерий on 25.06.2017.
 */
public abstract class Service<T extends Entity> {
    protected static DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger LOGGER = Logger.getLogger(Service.class);
    protected String entityName;

    protected Service(String entityName) {
        this.entityName = entityName;
    }

    public void create(T entity) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
            if (!dao.create(entity)) {
                String logMessage = ExceptionMessages.getLogMessage(ExceptionMessages.CREATE_FAILED) + dao.getEntityName();
                LOGGER.error(logMessage);
                String message = ExceptionMessages.getMessage(ExceptionMessages.CREATE_FAILED) + dao.getEntityName();
                throw new ServiceException(message);
            }
        }
    }

    public void update(T entity) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
            if (!dao.update(entity)) {
                String logMessage = ExceptionMessages.getLogMessage(ExceptionMessages.UPDATE_FAILED) + dao.getEntityName();
                LOGGER.error(logMessage);
                String message = ExceptionMessages.getMessage(ExceptionMessages.UPDATE_FAILED) + dao.getEntityName();
                throw new ServiceException(message);
            }
        }
    }

    public void delete(T entity) throws ServiceException {

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
            if (!dao.delete(entity.getId())) {
                String logMessage = ExceptionMessages.getLogMessage(ExceptionMessages.DELETE_FAILED) + dao.getEntityName();
                LOGGER.error(logMessage);
                String message = ExceptionMessages.getMessage(ExceptionMessages.DELETE_FAILED) + dao.getEntityName();
                throw new ServiceException(message);
            }
        }
    }

    public Optional<T> find(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
//            Optional<T> optional = dao.find(id);
//            if (!optional.isPresent()) {
//                String logMessage = ExceptionMessages.getLogMessage(ExceptionMessages.FIND_ITEMS_FAILED) + dao.getEntityName() +
//                        ExceptionMessages.getLogMessage(ExceptionMessages.ITEM_ID) + id;
//                LOGGER.error(logMessage);
//                String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEMS_FAILED) + dao.getEntityName() +
//                        ExceptionMessages.getMessage(ExceptionMessages.ITEM_ID) + id;
//                throw new ServiceException(message);
//            }
//            return optional.get();
            return dao.find(id);
        }
    }

    public List<T> findAll(){
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
//            List<T> itemList = dao.findAll();
//            if (itemList.size() == 0) {
//                String logMessage = ExceptionMessages.getLogMessage(ExceptionMessages.FIND_ITEMS_FAILED) +
//                        dao.getEntityName();
//                LOGGER.error(logMessage);
//                String message = ExceptionMessages.getMessage(ExceptionMessages.FIND_ITEMS_FAILED) +
//                        dao.getEntityName();
//                throw new ServiceException(message);
//            }
            return dao.findAll();
        }

    }


}
