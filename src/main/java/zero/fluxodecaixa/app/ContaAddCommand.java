package zero.fluxodecaixa.app;

import java.sql.SQLException;

import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class ContaAddCommand {

    @Dependency
    ConnectionSource connection;

    @CommandHandler(path = { "conta", "add" })
    public void execute(ContaAddBean bean) throws SQLException {
        Conta conta = new Conta();

        conta.setNome(bean.getNome());

        conta.setContabilizavel(bean.isContabilizavel());

        Dao<Conta, Integer> dao = DaoManager.createDao(connection, Conta.class);

        dao.create(conta);

        bean.setConta(conta);
    }
}
