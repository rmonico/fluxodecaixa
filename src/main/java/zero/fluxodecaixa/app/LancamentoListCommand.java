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
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class LancamentoListCommand {

    @Dependency
    private LancamentoDao dao;

    @Dependency
    private TransacaoDao transacaoDao;

    @Bean
    public List<Lancamento> lancamentos;

    @CommandHandler(path = { "lanc", "ls" })
    public void run() throws ParseException, SQLException {
        PreparedQuery<Lancamento> query = createQuery();

        lancamentos = dao.query(query);
    }

    private PreparedQuery<Lancamento> createQuery() throws SQLException {
        QueryBuilder<Transacao, Integer> transacaoBuilder = transacaoDao.queryBuilder();

        transacaoBuilder.orderBy(Transacao.DATA_FIELD_NAME, true);

        QueryBuilder<Lancamento, Integer> builder = dao.queryBuilder();

        builder.join(transacaoBuilder);

        PreparedQuery<Lancamento> query = builder.prepare();

        return query;
    }

}
