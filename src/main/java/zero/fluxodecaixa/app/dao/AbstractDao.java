package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class AbstractDao<T> extends BaseDaoImpl<T, Integer> {

    public AbstractDao(ConnectionSource connection, Class<T> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static <TT> AbstractDao<TT> getInstance(ConnectionSource connection, Class<TT> dataClass) throws SQLException {
        return DaoManager.createDao(connection, dataClass);
    }

}
