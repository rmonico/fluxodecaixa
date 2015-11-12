package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.easymvc.ormlite.DatabaseUpdater;
import zero.easymvc.ormlite.MetaInfUpdater;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class FluxodecaixaTestUpdater extends MetaInfUpdater {

    public FluxodecaixaTestUpdater(ConnectionSource connection, DatabaseUpdater previousVersion) {
        super(connection);

        setUpdaterVersion(1);
    }

    @Override
    public void updateStructure() throws SQLException {
        super.updateStructure();

        TableUtils.dropTable(connection, Lancamento.class, true);
        TableUtils.dropTable(connection, Conta.class, true);
        TableUtils.dropTable(connection, Transacao.class, true);
    }

}
