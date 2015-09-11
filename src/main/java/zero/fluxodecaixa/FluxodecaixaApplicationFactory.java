package zero.fluxodecaixa;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import zero.easymvc.ormlite.DatabaseVersion;
import zero.easymvc.ormlite.factory.ApplicationFactory;
import zero.fluxodecaixa.app.ContaCreateCommand;
import zero.fluxodecaixa.app.ContaListCommand;
import zero.fluxodecaixa.app.ContaRemoveCommand;
import zero.fluxodecaixa.app.HelpCommand;
import zero.fluxodecaixa.app.LancamentoCreateCommand;
import zero.fluxodecaixa.app.LancamentoListCommand;
import zero.fluxodecaixa.app.LancamentoListRenderer;
import zero.fluxodecaixa.app.SaldoCommand;
import zero.fluxodecaixa.app.VersionCommand;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.database.DatabaseVersion_1;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;
import zero.fluxodecaixa.renderer.ContaCreateRenderer;
import zero.fluxodecaixa.renderer.ContaListRenderer;
import zero.fluxodecaixa.renderer.ContaRemoveRenderer;
import zero.fluxodecaixa.renderer.HelpRenderer;
import zero.fluxodecaixa.renderer.LancamentoCreateRenderer;
import zero.fluxodecaixa.renderer.SaldoRenderer;

public class FluxodecaixaApplicationFactory extends ApplicationFactory {

    public FluxodecaixaApplicationFactory(Properties props) {
        super(props);
    }

    @Override
    public void populateDaoInfo(Map<Class<?>, Class<?>> daoInfo) {
        super.populateDaoInfo(daoInfo);

        daoInfo.put(ContaDao.class, Conta.class);
        daoInfo.put(TransacaoDao.class, Transacao.class);
        daoInfo.put(LancamentoDao.class, Lancamento.class);
    }

    @Override
    public void createCommandList(List<Class<?>> commands) {
        super.createCommandList(commands);

        commands.add(ContaCreateCommand.class);
        commands.add(ContaListCommand.class);
        commands.add(ContaRemoveCommand.class);
        commands.add(HelpCommand.class);
        commands.add(LancamentoCreateCommand.class);
        commands.add(LancamentoListCommand.class);
        commands.add(SaldoCommand.class);
        commands.add(VersionCommand.class);
    }

    @Override
    public void createRendererList(List<Class<?>> renderers) {
        super.createRendererList(renderers);

        renderers.add(ContaCreateRenderer.class);
        renderers.add(ContaListRenderer.class);
        renderers.add(ContaRemoveRenderer.class);
        renderers.add(HelpRenderer.class);
        renderers.add(LancamentoCreateRenderer.class);
        renderers.add(LancamentoListRenderer.class);
        renderers.add(SaldoRenderer.class);
        renderers.add(VersionCommand.class);
    }

    @Override
    public DatabaseVersion createDatabaseVersion() throws Exception {
        return new DatabaseVersion_1(connectionManager.getConnection());
    }

}
