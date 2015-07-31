package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.support.ConnectionSource;

public class ContaDao extends AbstractDao<Conta> {

    public ContaDao(ConnectionSource connection, Class<Conta> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static ContaDao getInstance(ConnectionSource connection) throws SQLException {
        return (ContaDao) AbstractDao.getInstance(connection, Conta.class);
    }
}
