package br.com.cenajur.util;

import java.util.Date;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;

import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Usuario;

public class Main {

	private static Session session;  
	  
	public static void main(String[] args) {
		
		Audiencia audiencia = new Audiencia();
		
//		usuario.setNome("FFFF");
//		usuario.setLogin("FFFF");
//		usuario.setSenha("123");
//		
//		Grupo grupo = new Grupo();
//		grupo.setId(1L);
//		
//		usuario.setGrupo(grupo);
//		usuario.setEscola(new Escola());
//		
//		session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//        session.save(usuario);  
//        session.getTransaction().commit();
        
        
		List<Audiencia> coll = null;
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query queryObject = null;
			
			session.setFlushMode(FlushMode.NEVER);
			queryObject = session.createQuery("  from Audiencia a " );
					//" inner join a.audienciasAdvogados aa");

			coll = queryObject.list();
			
			for (Audiencia audiencia2 : coll) {
				System.out.println(audiencia2.getId());
			}
			
			System.out.println(coll.size());
		
	}

}
