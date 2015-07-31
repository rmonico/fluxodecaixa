package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class TransacaoDao extends BaseDaoImpl<Transacao, Integer> {

    public TransacaoDao(ConnectionSource connection, Class<Transacao> dataClass) throws SQLException {
        super(connection, dataClass);
    }

}
