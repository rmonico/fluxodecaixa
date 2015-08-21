package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.MetaInf;

import com.j256.ormlite.support.ConnectionSource;

public class MetaInfDao extends AbstractDao<MetaInf> {

    public MetaInfDao(ConnectionSource connection, Class<MetaInf> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static MetaInfDao getInstance(ConnectionSource connection) throws SQLException {
        return (MetaInfDao) AbstractDao.getInstance(connection, MetaInf.class);
    }

    public String getValue(String key) {
        return null;
    }
}
