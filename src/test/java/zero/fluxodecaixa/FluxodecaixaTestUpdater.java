package zero.fluxodecaixa;

import zero.easymvc.ormlite.AbstractDatabaseVersion;
import zero.easymvc.ormlite.DatabaseVersion;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class FluxodecaixaTestUpdater extends AbstractDatabaseVersion {

    public FluxodecaixaTestUpdater(ConnectionSource connection, DatabaseVersion previousVersion) {
        super(connection, previousVersion);
    }

    @Override
    public void update() throws Exception {
        TableUtils.dropTable(connection, Lancamento.class, true);
        TableUtils.dropTable(connection, Conta.class, true);
        TableUtils.dropTable(connection, Transacao.class, true);

        super.update();
    }

    @Override
    public int getCurrentVersion() {
        return 0;
    }

}
