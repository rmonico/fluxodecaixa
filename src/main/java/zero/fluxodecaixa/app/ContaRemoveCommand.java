package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zero.easymvc.ArgumentsBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class ContaRemoveCommand {

    @Dependency
    ConnectionSource connection;

    @ArgumentsBean
    private ContaRemoveArguments arguments;

    @Bean
    private Conta removedConta;

    @CommandHandler(path = { "conta", "rm" })
    public void execute() throws SQLException {
        Dao<Conta, Integer> dao = DaoManager.createDao(connection, Conta.class);

        Map<String, Object> map = new HashMap<>();

        map.put(Conta.NOME_FIELD_NAME, arguments.getNome());

        List<Conta> queryForFieldValues = dao.queryForFieldValues(map);

        if (queryForFieldValues.size() == 0) {
            removedConta = null;
        } else {
            removedConta = queryForFieldValues.get(0);

            dao.delete(removedConta);
        }
    }

}
