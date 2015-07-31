package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;

public class TransacaoDao extends AbstractDao<Transacao> {

    public TransacaoDao(ConnectionSource connection, Class<Transacao> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static TransacaoDao getInstance(ConnectionSource connection) throws SQLException {
        return (TransacaoDao) AbstractDao.getInstance(connection, Transacao.class);
    }
}
