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
    private ConnectionSource connectionSource;

    @CommandHandler(path = { "conta", "ls" })
    public void execute(ContaLsBean bean) throws SQLException {
        Dao<Conta, Integer> contaDao = DaoManager.createDao(connectionSource, Conta.class);

        bean.setContas(contaDao.queryForAll());
    }
}
