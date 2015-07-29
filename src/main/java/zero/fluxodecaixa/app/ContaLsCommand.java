package zero.fluxodecaixa.app;

import java.sql.SQLException;

import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class ContaLsCommand {

    @Dependency
    private ConnectionSource connection;

    @CommandHandler(path = { "conta", "ls" })
    public void execute(ContaLsBean bean) throws SQLException {
        Dao<Conta, Integer> dao = DaoManager.createDao(connection, Conta.class);

        bean.setContas(dao.queryForAll());
    }
}
