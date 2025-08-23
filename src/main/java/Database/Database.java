package Database;

import java.sql.*;
import java.util.*;

public class Database {

    private static Database instancia = null;
    private Connection conexao = null;

    public Database() {
        try {
            // Load prop file data
            Properties prop = new Properties();
            prop.load(getClass().getResourceAsStream("/properties/db.properties"));
            
            String dbdriver = prop.getProperty("db.driver");
            String dburl = prop.getProperty("db.url");
            String dbuser = prop.getProperty("db.user");
            String dbsenha = prop.getProperty("db.senha");

            // Load database driver
            Class.forName(dbdriver);

            if (!dbuser.isEmpty()) // With user and password
            {
                conexao = DriverManager.getConnection(dburl, dbuser, dbsenha);
            } else // Without user and password
            {
                conexao = DriverManager.getConnection(dburl);
            }

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /** Returns database instance
     * @return Database*/
    public static Database getInstance() {
        if (instancia == null) {
            instancia = new Database();
        }
        return instancia;
    }

    /**Returns database connection
     * @return Connection*/
    public Connection getConnection() {
        if (conexao == null) {
            throw new RuntimeException("conexao==null");
        }
        return conexao;
    }

    /**Shutdown connection*/
    public void shutdown() {
        try {
            conexao.close();
            instancia = null;
            conexao = null;
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}