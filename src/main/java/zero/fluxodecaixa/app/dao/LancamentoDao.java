package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Lancamento;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class LancamentoDao extends BaseDaoImpl<Lancamento, Integer> {

    public LancamentoDao(ConnectionSource connection, Class<Lancamento> dataClass) throws SQLException {
        super(connection, dataClass);
    }

}
