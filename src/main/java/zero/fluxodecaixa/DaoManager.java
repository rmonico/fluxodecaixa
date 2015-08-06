package zero.fluxodecaixa;

import java.util.HashMap;
import java.util.Map;

import zero.easymvc.DependencyManager;
import zero.fluxodecaixa.app.dao.ContaDao;
import zero.fluxodecaixa.app.dao.LancamentoDao;
import zero.fluxodecaixa.app.dao.TransacaoDao;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;

public class DaoManager implements DependencyManager {

    private ConnectionSource connection;
    private Map<Class<?>, Class<?>> daoInfo;

    public DaoManager(ConnectionSource connection) {
        this.connection = connection;

        daoInfo = new HashMap<>();

        daoInfo.put(ContaDao.class, Conta.class);
        daoInfo.put(TransacaoDao.class, Transacao.class);
        daoInfo.put(LancamentoDao.class, Lancamento.class);
    }

    @Override
    public Class<?>[] managedClasses() {
        return daoInfo.keySet().toArray(new Class<?>[] {});
    }

    @Override
    public Object getInstance(Class<?> daoClass) throws Exception {
        Class<?> dataClass = daoInfo.get(daoClass);

        return com.j256.ormlite.dao.DaoManager.createDao(connection, dataClass);
    }

    @Override
    public void afterUse(Class<?> daoClass) throws Exception {
        connection.close();
    }

}
