package com.aprismatic.prismadb;

import java.sql.*;

public final class App {
    public static void main(String[] args) {
        try {
            MySqlPrismaDbConn conn = new MySqlPrismaDbConn("localhost:4000", "testdb", "root", "Qwer!234");

            // Drop old table
            try {
                conn.executeQuery("DROP TABLE test_table;");
            } catch (SQLException e) {
            }

            // Create a new table
            conn.executeQuery("CREATE TABLE test_table ("
                    + "a INT ENCRYPTED FOR (MULTIPLICATION, ADDITION, RANGE), "
                    + "b INT ENCRYPTED FOR (MULTIPLICATION, ADDITION, RANGE), "
                    + "c INT, "
                    + "d VARCHAR(30) ENCRYPTED FOR (STORE, SEARCH, WILDCARD), "
                    + "e VARCHAR(30));");

            // Insert data
            conn.executeQuery("INSERT INTO test_table (a, b, c, d, e) VALUES "
                    + "(1, 2, 3, 'Hello', 'Prisma/DB'), "
                    + "(12, 0, 7, 'Test', 'data'), "
                    + "( 0, 2, 123, 'This is encrypted', 'And this is not'), "
                    + "(71, 67, 13, 'Last', 'row');");

            // Select data
            ResultSet columnRes = conn
                    .executeQuery("SELECT a, b, a + b, b * a, c, d, e FROM test_table WHERE b = 2 OR d LIKE 'Te%';");
            while (columnRes.next()) {
                System.out.println(columnRes.getString(1) + ", " + columnRes.getString(2) + ", "
                        + columnRes.getString(3) + ", " + columnRes.getString(4) + ", " + columnRes.getString(5) + ", "
                        + columnRes.getString(6) + ", " + columnRes.getString(7));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
