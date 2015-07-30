package zero.fluxodecaixa.app;

import java.sql.SQLException;

import zero.easymvc.ArgumentBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class ContaCreateCommand {

    @Dependency
    ConnectionSource connection;

    @ArgumentBean
    private ContaCreateArguments arguments;

    @Bean
    private Conta conta;

    @CommandHandler(path = { "conta", "add" })
    public void execute() throws SQLException {
        conta = new Conta();

        conta.setNome(arguments.getNome());

        conta.setContabilizavel(arguments.isContabilizavel());

        Dao<Conta, Integer> dao = DaoManager.createDao(connection, Conta.class);

        // TODO Verificar se já existe uma conta com o mesmo nome...

        dao.create(conta);
    }
}
