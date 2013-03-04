package br.com.cenajur.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.engine.spi.SessionFactoryImplementor;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSHibernateUtil;
// DEUS PAI...deleta isso

public final class ConnectionFactory {


    private ConnectionFactory() {
    }

    public static Connection getConnection()  {
    	try {
			return ((SessionFactoryImplementor) TSHibernateUtil.getSessionFactory()).getConnectionProvider().getConnection();
		} catch (SQLException e) {
			throw new TSSystemException(e);
		} 
       
    }
    
    public static void closeConnection(Connection con) {
        try {
        
            con.close();
        
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }
        
    }

}
