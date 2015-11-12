package zero.fluxodecaixa.database;

import java.sql.SQLException;

import zero.easymvc.ormlite.MetaInfUpdater;
import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseVersion_1 extends MetaInfUpdater {

    public DatabaseVersion_1(ConnectionSource connection) {
        super(connection);

        setUpdaterVersion(1);
    }

    @Override
    public void updateStructure() throws SQLException {
        super.updateStructure();

        TableUtils.createTableIfNotExists(connection, Conta.class);
        TableUtils.createTableIfNotExists(connection, Transacao.class);
        TableUtils.createTableIfNotExists(connection, Lancamento.class);
    }

}
