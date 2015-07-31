package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class ContaDao extends BaseDaoImpl<Conta, Integer> {

    public ContaDao(ConnectionSource connection, Class<Conta> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public Conta getContaByNome(String nomeOrigem) {
        return null;
    }

}
