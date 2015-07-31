package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.support.ConnectionSource;

public class ContaDao extends AbstractDao<Conta> {

    public ContaDao(ConnectionSource connection, Class<Conta> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static ContaDao getInstance(ConnectionSource connection) throws SQLException {
        return (ContaDao) AbstractDao.getInstance(connection, Conta.class);
    }

    public Conta getContaByNome(String nomeConta) throws SQLException {
        Map<String, Object> map = new HashMap<>();

        map.put(Conta.NOME_FIELD_NAME, nomeConta);

        List<Conta> contas = queryForFieldValues(map);

        if (contas.isEmpty())
            return null;
        else
            return contas.get(0);
    }
}
