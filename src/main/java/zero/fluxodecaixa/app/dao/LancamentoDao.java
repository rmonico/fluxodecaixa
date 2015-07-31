package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Lancamento;

import com.j256.ormlite.support.ConnectionSource;

public class LancamentoDao extends AbstractDao<Lancamento> {

    public LancamentoDao(ConnectionSource connection, Class<Lancamento> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static LancamentoDao getInstance(ConnectionSource connection) throws SQLException {
        return (LancamentoDao) AbstractDao.getInstance(connection, Lancamento.class);
    }
}
