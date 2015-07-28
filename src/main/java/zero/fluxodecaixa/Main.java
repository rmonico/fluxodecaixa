package zero.fluxodecaixa;

import java.sql.SQLException;

import zero.fluxodecaixa.model.Conta;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class Main {

    private final static String DATABASE_URL = "jdbc:sqlite:./database.sqlite";

    public static void main(String[] args) throws SQLException {
        JdbcConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);

        DaoManager.createDao(connectionSource, Conta.class);

        // if you need to create the table
        TableUtils.dropTable(connectionSource, Conta.class, true);
        TableUtils.createTable(connectionSource, Conta.class);
    }
}
