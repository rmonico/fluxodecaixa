package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Conta;
import zero.fluxodecaixa.model.Lancamento;
import zero.fluxodecaixa.model.Transacao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class TestConnectionManager extends ConnectionManager {

    public TestConnectionManager() throws SQLException {
        super();

        recreateStructure();
    }

    public String getConnectionString() {
        return "jdbc:sqlite:./dbunit/test_database";
    }

    private void recreateStructure() throws SQLException {
        ConnectionSource source = (ConnectionSource) getInstance(ConnectionSource.class);

        TableUtils.dropTable(source, Conta.class, true);
        TableUtils.dropTable(source, Transacao.class, true);
        TableUtils.dropTable(source, Lancamento.class, true);
        TableUtils.createTable(source, Conta.class);
        TableUtils.createTable(source, Transacao.class);
        TableUtils.createTable(source, Lancamento.class);
    }

}
