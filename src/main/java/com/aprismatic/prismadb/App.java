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
            for (int i=0; i<200; i++) {
                conn.executeQuery("INSERT INTO test_table (a, b, c, d, e) VALUES "
                        + "(1, 2, 3, 'Hello', 'Prisma/DB'), "
                        + "(12, 0, 7, 'Test', 'data'), "
                        + "( 0, 2, 123, 'This is encrypted', 'And this is not'), "
                        + "(71, 67, 13, 'Last', 'row');");
            }

            // Encrypt column
            conn.executeQuery("PRISMADB ENCRYPT test_table.c FOR (MULTIPLICATION, ADDITION, SEARCH);");

            // Check for encrypt status
            while (true) {
                // Prisma/DB requires a very small amount of time to start the encryption,
                // therefore there is a short sleep before checking the status.
                // This sleep is in the loop to not flood the console with status prints.
                Thread.sleep(100);
                ResultSet statusRes = conn
                        .executeQuery("PRISMADB ENCRYPT test_table.c FOR (MULTIPLICATION, ADDITION, SEARCH) STATUS;");
                statusRes.next();
                String status = statusRes.getString(2);
                System.out.println(status);
                if (!status.equals("In Progress")) {
                    break;
                }
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
