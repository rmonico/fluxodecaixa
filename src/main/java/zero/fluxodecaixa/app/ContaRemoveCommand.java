package zero.fluxodecaixa.app;

import java.sql.SQLException;

import zero.easymvc.ArgumentsBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.model.Conta;

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
        ContaDao dao = ContaDao.getInstance(connection);

        removedConta = dao.getContaByNome(arguments.getNome());

        if (removedConta == null)
            removedConta = null;
        else {
            dao.delete(removedConta);
        }
    }

}
