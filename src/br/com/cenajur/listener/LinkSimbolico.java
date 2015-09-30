package br.com.cenajur.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class LinkSimbolico
 *
 */
@WebListener
public class LinkSimbolico implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public LinkSimbolico() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println("INICIALIZANDO O LISTENER - TOPSYS");
         try {
			Runtime.getRuntime().exec("ln -s /home/cenajurc/arquivos/ /home/cenajurc/appservers/apache-tomcat-7x/webapps/cenajur/arquivos");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
