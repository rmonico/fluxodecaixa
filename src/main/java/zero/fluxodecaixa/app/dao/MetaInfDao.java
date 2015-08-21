package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;
import java.util.List;

import zero.fluxodecaixa.model.MetaInf;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

public class MetaInfDao extends AbstractDao<MetaInf> {

    public MetaInfDao(ConnectionSource connection, Class<MetaInf> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static MetaInfDao getInstance(ConnectionSource connection) throws SQLException {
        return (MetaInfDao) AbstractDao.getInstance(connection, MetaInf.class);
    }

    public String getValue(String key) throws SQLException {
        QueryBuilder<MetaInf, Integer> builder = queryBuilder();

        Where<MetaInf, Integer> where = builder.where();

        where.eq(MetaInf.KEY_FIELD_NAME, key);

        PreparedQuery<MetaInf> query = builder.prepare();

        List<MetaInf> keys = query(query);

        if (keys.isEmpty())
            return null;

        return keys.get(0).getValue();
    }
}
