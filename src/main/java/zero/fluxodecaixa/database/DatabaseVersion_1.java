package zero.fluxodecaixa.database;

import zero.easymvc.ormlite.AbstractDatabaseVersion;
import zero.easymvc.ormlite.MetaInfUpdater;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseVersion_1 extends AbstractDatabaseVersion {

    private static final int THIS_VERSION = 1;

    public DatabaseVersion_1(ConnectionSource connection) {
        super(connection, new MetaInfUpdater(connection));
    }

    @Override
    public void update() throws Exception {
        if (THIS_VERSION > getOldVersion()) {
            TableUtils.createTableIfNotExists(connection, Conta.class);
            TableUtils.createTableIfNotExists(connection, Transacao.class);
            TableUtils.createTableIfNotExists(connection, Lancamento.class);
        }
    }

    @Override
    public int getCurrentVersion() {
        return THIS_VERSION;
    }

}
