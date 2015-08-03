package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import zero.easymvc.ArgumentsBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.CommandLineParsingException;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

public class LancamentoCreateCommand {

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
    public void run() throws SQLException, CommandLineParsingException {
        checkCommandLine();

        Transacao transacao = createOrGetTransacao();

        lancamento = new Lancamento();

        lancamento.setTransacao(transacao);

        Conta origem = contaDao.getContaByNome(args.getNomeOrigem());

        lancamento.setOrigem(origem);

        Conta destino = contaDao.getContaByNome(args.getNomeDestino());

        lancamento.setDestino(destino);

        lancamento.setValor(args.getValor());

        lancamento.setObservacao(args.getObservacao());

        lancamentoDao.create(lancamento);
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
