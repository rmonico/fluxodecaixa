package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.model.Lancamento;

import com.j256.ormlite.stmt.PreparedQuery;

public class LancamentoListCommand {

    @Dependency
    private LancamentoDao dao;

    @Dependency
    private TransacaoDao transacaoDao;

    @Bean
    public List<Lancamento> lancamentos;

    @CommandHandler(path = { "lanc", "ls" })
    public void run() throws ParseException, SQLException {
        PreparedQuery<Lancamento> query = dao.createQuerySortedByTransacaoData(transacaoDao);

        lancamentos = dao.query(query);
    }

}
