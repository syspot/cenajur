package br.com.cenajur.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static final String URL = "jdbc:postgresql://localhost/cenajur";
    private static final String LOGIN_BANCO_CENAJUR = "postgres";
    private static final String SENHA_BANCO_CENAJUR = "15rt01";

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        
        Class.forName("org.postgresql.Driver");
        
        return DriverManager.getConnection(URL, LOGIN_BANCO_CENAJUR, SENHA_BANCO_CENAJUR);
    }
    
    public static void closeConnection(Connection con) {
        try {
        
            con.close();
        
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }
        
    }

}
