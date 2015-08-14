package zero.fluxodecaixa.app.dao;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class LancamentoDao extends AbstractDao<Lancamento> {

    public LancamentoDao(ConnectionSource connection, Class<Lancamento> dataClass) throws SQLException {
        super(connection, dataClass);
    }

    public static LancamentoDao getInstance(ConnectionSource connection) throws SQLException {
        return (LancamentoDao) AbstractDao.getInstance(connection, Lancamento.class);
    }

    public PreparedQuery<Lancamento> createQuerySortedByTransacaoData(TransacaoDao transacaoDao) throws SQLException {
        QueryBuilder<Transacao, Integer> transacaoBuilder = transacaoDao.queryBuilder();

        transacaoBuilder.orderBy(Transacao.DATA_FIELD_NAME, true);

        QueryBuilder<Lancamento, Integer> builder = queryBuilder();

        builder.join(transacaoBuilder);

        PreparedQuery<Lancamento> query = builder.prepare();

        return query;
    }

}
