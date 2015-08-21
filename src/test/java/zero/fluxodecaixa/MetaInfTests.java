package zero.fluxodecaixa;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import zero.fluxodecaixa.app.dao.MetaInfDao;

import com.j256.ormlite.support.ConnectionSource;

public class MetaInfTests extends FluxodecaixaTest {

    public MetaInfTests() {
        super("dbunit/metainf_dataset.xml");
    }

    @Test
    public void should_get_a_value_from_meta_inf_table() throws SQLException {
        ConnectionSource connection = controllerFactory.connectionManager.getConnection();
        MetaInfDao dao = MetaInfDao.getInstance(connection);

        assertEquals("some random value", dao.getValue("some random key"));
    }
}
