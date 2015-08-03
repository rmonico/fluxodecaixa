package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.model.Conta;

public class ContaListCommand {

    @Dependency
    private ContaDao dao;

    @Bean
    private List<Conta> contas;

    @CommandHandler(path = { "conta", "ls" })
    public void execute() throws SQLException {
        contas = dao.queryForAll();
    }
}
