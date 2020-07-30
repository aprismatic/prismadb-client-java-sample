package com.aprismatic.prismadb;

import java.sql.*;

public class MySqlPrismaDbConn {
    private Connection conn;

    public MySqlPrismaDbConn(String host, String database, String username, String password) throws SQLException {
        init(host, database, username, password);
        conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);
    }

    private void init(String host, String database, String username, String password) throws SQLException {
        // As the MySQL variant of PrismaDB has a credentials limitation,
        // we will need to perform a PRISMADB REGISTER USER query:
        // https://prismadb.readthedocs.io/en/latest/native-commands/#register-user-mysql-only

        // Login as init/init can only be executed once, and will be disabled once a user is registered,
        // therefore a try/catch is used to prevent failing of this program:
        // https://prismadb.readthedocs.io/en/latest/getting-started-proxies/#21-if-you-are-running-the-mysql-version-of-prismadb-proxy

        try {
            Connection initConn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, "init", "init");
            Statement stmt = initConn.createStatement();
            stmt.executeQuery("PRISMADB REGISTER USER '" + username + "' PASSWORD '" + password + "'");
            initConn.close();
        } catch (SQLException ex) {
            if (!ex.getMessage().contains("User 'init' not registered.")) {
                throw ex;
            }
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return conn.createStatement().executeQuery(sql);
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
