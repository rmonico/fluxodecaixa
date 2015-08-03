package zero.fluxodecaixa.app;

import java.sql.SQLException;

import zero.easymvc.ArgumentsBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.model.Conta;

public class ContaRemoveCommand {

    @Dependency
    private ContaDao dao;

    @ArgumentsBean
    private ContaRemoveArguments arguments;

    @Bean
    private Conta removedConta;

    @CommandHandler(path = { "conta", "rm" })
    public void execute() throws SQLException {
        removedConta = dao.getContaByNome(arguments.getNome());

        if (removedConta == null)
            removedConta = null;
        else {
            dao.delete(removedConta);
        }
    }

}
