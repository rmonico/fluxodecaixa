package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Calendar;
import java.util.GregorianCalendar;

import zero.easymvc.ArgumentsBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.CommandLineParsingException;
import zero.easymvc.Dependency;
import zero.easymvc.EasyMVCException;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

public class LancamentoCreateCommand {

    @Dependency
    private ConnectionSource source;

    @Dependency
    private LancamentoDao lancamentoDao;

    @Dependency
    private TransacaoDao transacaoDao;

    @Dependency
    private ContaDao contaDao;

    @ArgumentsBean
    private LancamentoCreateArguments args;

    @Bean
    private Lancamento lancamento;

    @CommandHandler(path = { "lanc", "add" })
    public void run() throws CommandLineParsingException, SQLException, EasyMVCException {
        checkCommandLine();

        final DatabaseConnection connection = source.getReadWriteConnection();

        connection.setAutoCommit(false);

        final Savepoint savePoint = connection.setSavePoint("lancamento create");

        try {
            Transacao transacao = createOrGetTransacao();

            lancamento = new Lancamento();

            lancamento.setTransacao(transacao);

            String nomeOrigem = args.getNomeOrigem();
            Conta origem = contaDao.getContaByNome(nomeOrigem);

            if (origem == null)
                throw new EasyMVCException(String.format("Conta origem \"%s\" must exist!", nomeOrigem));

            lancamento.setOrigem(origem);

            String nomeDestino = args.getNomeDestino();
            Conta destino = contaDao.getContaByNome(nomeDestino);

            if (destino == null)
                throw new EasyMVCException(String.format("Conta destino \"%s\" must exist!", nomeDestino));

            lancamento.setDestino(destino);

            lancamento.setValor(args.getValor());

            lancamento.setObservacao(args.getObservacao());

            lancamentoDao.create(lancamento);

            connection.commit(savePoint);
        } catch (SQLException e) {
            connection.rollback(savePoint);

            throw e;

        }
    }

    private void checkCommandLine() throws CommandLineParsingException {
        if ((args.getData() != null) && (args.getTransacaoId() != null)) {
            throw new CommandLineParsingException("Os parâmetros --data e --transacao-id não podem ser informados simultâneamente.");
        }
    }

    private Transacao createOrGetTransacao() throws SQLException {
        Integer transactionId = args.getTransacaoId();

        if (transactionId == null)
            return createTransacao();
        else {
            return transacaoDao.queryForId(transactionId);
        }
    }

    private Transacao createTransacao() throws SQLException {
        Transacao transacao = new Transacao();

        Calendar data = args.getData();

        if (data == null)
            data = GregorianCalendar.getInstance();

        transacao.setData(data);

        transacaoDao.create(transacao);

        return transacao;
    }
}
