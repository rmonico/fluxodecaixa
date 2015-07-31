package zero.fluxodecaixa.app;

import java.sql.SQLException;

import zero.easymvc.ArgumentsBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.support.ConnectionSource;

public class ContaCreateCommand {

    @Dependency
    ConnectionSource connection;

    @ArgumentsBean
    private ContaCreateArguments arguments;

    @Bean
    private Conta conta;

    @CommandHandler(path = { "conta", "add" })
    public void execute() throws SQLException {
        conta = new Conta();

        conta.setNome(arguments.getNome());

        conta.setContabilizavel(arguments.isContabilizavel());

        ContaDao dao = ContaDao.getInstance(connection);

        // TODO Verificar se já existe uma conta com o mesmo nome...

        dao.create(conta);
    }
}
