package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class ContaListCommand {

    @Dependency
    private ConnectionSource connection;

    @Bean
    private List<Conta> contas;

    @CommandHandler(path = { "conta", "ls" })
    public void execute() throws SQLException {
        Dao<Conta, Integer> dao = DaoManager.createDao(connection, Conta.class);

        contas = dao.queryForAll();
    }
}
