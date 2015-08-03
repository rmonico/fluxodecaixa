package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.model.Lancamento;

public class LancamentoListCommand {

    @Dependency
    private LancamentoDao dao;

    @Bean
    public List<Lancamento> lancamentos;

    @CommandHandler(path = { "lanc", "ls" })
    public void run() throws ParseException, SQLException {
        lancamentos = dao.queryForAll();
    }

}
