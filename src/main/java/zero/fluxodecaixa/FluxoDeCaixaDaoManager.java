package zero.fluxodecaixa;

import java.util.Map;

import zero.easymvc.ormlite.DaoManager;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

public class FluxoDeCaixaDaoManager extends DaoManager {

    public FluxoDeCaixaDaoManager() {
        super();
    }

    @Override
    protected void populateDaoInfo(Map<Class<?>, Class<?>> daoInfo) {
        daoInfo.put(ContaDao.class, Conta.class);
        daoInfo.put(TransacaoDao.class, Transacao.class);
        daoInfo.put(LancamentoDao.class, Lancamento.class);
    }

}
