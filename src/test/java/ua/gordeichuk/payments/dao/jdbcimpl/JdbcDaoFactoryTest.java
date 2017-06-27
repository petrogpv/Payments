package ua.gordeichuk.payments.dao.jdbcimpl;

import org.junit.Test;
import ua.gordeichuk.payments.dao.DaoFactory;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Валерий on 21.06.2017.
 */
public class JdbcDaoFactoryTest {

    @Test
    public void testGetConnection() throws Exception {
        DaoFactory daoFactory = DaoFactory.getInstance();
        assertNotNull(daoFactory.getConnection());
    }
}