package ua.gordeichuk.payments.service;

import org.apache.log4j.Logger;
import ua.gordeichuk.payments.dao.Dao;
import ua.gordeichuk.payments.dao.DaoConnection;
import ua.gordeichuk.payments.dao.DaoFactory;
import ua.gordeichuk.payments.entity.Entity;
import ua.gordeichuk.payments.exception.ServiceException;
import ua.gordeichuk.payments.util.ExceptionMessage;

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
                String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.CREATE_FAILED) + dao.getEntityName();
                LOGGER.error(logMessage);
                String message = ExceptionMessage.getMessage(ExceptionMessage.CREATE_FAILED) + dao.getEntityName();
                throw new ServiceException(message);
            }
            connection.commit();

        }
    }

    public void update(T entity) throws ServiceException {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
            if (!dao.update(entity)) {
                String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.UPDATE_FAILED) + dao.getEntityName();
                LOGGER.error(logMessage);
                String message = ExceptionMessage.getMessage(ExceptionMessage.UPDATE_FAILED) + dao.getEntityName();
                throw new ServiceException(message);
            }
            connection.commit();
        }
    }

    public void delete(T entity) throws ServiceException {

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
            if (!dao.delete(entity.getId())) {
                String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.DELETE_FAILED) + dao.getEntityName();
                LOGGER.error(logMessage);
                String message = ExceptionMessage.getMessage(ExceptionMessage.DELETE_FAILED) + dao.getEntityName();
                throw new ServiceException(message);
            }
            connection.commit();
        }
    }

    public Optional<T> find(Long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
//            Optional<T> optional = dao.find(id);
//            if (!optional.isPresent()) {
//                String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.FIND_ITEMS_FAILED) + dao.getEntityName() +
//                        ExceptionMessage.getLogMessage(ExceptionMessage.ITEM_ID) + id;
//                LOGGER.error(logMessage);
//                String message = ExceptionMessage.getMessage(ExceptionMessage.FIND_ITEMS_FAILED) + dao.getEntityName() +
//                        ExceptionMessage.getMessage(ExceptionMessage.ITEM_ID) + id;
//                throw new ServiceException(message);
//            }
//            return optional.get();
            Optional<T> entity =  dao.find(id);
            connection.commit();
            return entity;


        }
    }

    public List<T> findAll(){
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.begin();
            Dao <T> dao = daoFactory.createDao(entityName, connection);
//            List<T> itemList = dao.findAll();
//            if (itemList.size() == 0) {
//                String logMessage = ExceptionMessage.getLogMessage(ExceptionMessage.FIND_ITEMS_FAILED) +
//                        dao.getEntityName();
//                LOGGER.error(logMessage);
//                String message = ExceptionMessage.getMessage(ExceptionMessage.FIND_ITEMS_FAILED) +
//                        dao.getEntityName();
//                throw new ServiceException(message);
//            }
            List<T> list = dao.findAll();
            connection.commit();
            return list;
        }

    }


}
