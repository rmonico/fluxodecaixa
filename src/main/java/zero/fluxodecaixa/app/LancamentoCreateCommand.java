package zero.fluxodecaixa.app;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import zero.easymvc.ArgumentsBean;
import zero.easymvc.Bean;
import zero.easymvc.CommandHandler;
import zero.easymvc.Dependency;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class LancamentoCreateCommand {

    @Dependency
    ConnectionSource connection;

    @ArgumentsBean
    private LancamentoCreateArguments args;

    @Bean
    private Lancamento lancamento;

    @CommandHandler(path = { "lanc", "add" })
    public void run() throws SQLException {
        Transacao transacao = createTransacao();

        lancamento = new Lancamento();

        lancamento.setTransacao(transacao);

        ContaDao contaDao = DaoManager.createDao(connection, Conta.class);

        Conta origem = contaDao.getContaByNome(args.getNomeOrigem());

        lancamento.setOrigem(origem);

        Conta destino = contaDao.getContaByNome(args.getNomeDestino());

        lancamento.setDestino(destino);

        lancamento.setValor(args.getValor());

        lancamento.setObservacao(args.getObservacao());

        LancamentoDao dao = LancamentoDao.getInstance(connection);

        dao.create(lancamento);
    }

    private Transacao createTransacao() throws SQLException {
        Transacao transacao = new Transacao();

        Calendar data = args.getData();

        if (data == null)
            data = GregorianCalendar.getInstance();

        transacao.setData(data);

        TransacaoDao dao = TransacaoDao.getInstance(connection);

        dao.create(transacao);

        return transacao;

    }
}
