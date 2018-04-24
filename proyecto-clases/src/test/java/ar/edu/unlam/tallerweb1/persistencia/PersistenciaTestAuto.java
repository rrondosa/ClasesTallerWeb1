package ar.edu.unlam.tallerweb1.persistencia;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Auto;
import ar.edu.unlam.tallerweb1.modelo.Direccion;
import ar.edu.unlam.tallerweb1.modelo.Empresa;
import ar.edu.unlam.tallerweb1.modelo.Modelo;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public class PersistenciaTestAuto extends SpringTest{
	

	
	@Test
	@Transactional @Rollback(true)
	public void testConsultaColorAuto(){

		Session s = getSession();
		
		Modelo mod1 = new Modelo("x");
		s.save(mod1);
		
		Modelo mod2 = new Modelo("y");
		s.save(mod2);
		
		Auto auto1 = new Auto("XCV1",mod1, "azul");
		s.save(auto1);
		
		Auto auto2 = new Auto("XCV2",mod2, "azul");
		s.save(auto2);
		
		Auto auto3 = new Auto("XCV1",mod1, "rojo");
		s.save(auto3);
	
		
		List<Auto> la = getSession().createCriteria(Auto.class).add(Restrictions.eq("color", "azul")).list();
		
		assertThat(la.size()).isEqualTo(2);
		
	}
	@Test
	@Transactional @Rollback(true)
	public void testConsultaporPatenteDeAuto(){
		
		Session s = getSession();
			
		Modelo mod1 = new Modelo ("modelo1");
		Modelo mod2 = new Modelo ("modelo2");
		
		s.save(mod1);
		s.save(mod2);
		
		Auto auto  = new Auto("XCV1",mod1, "azul");
		Auto auto2 = new Auto("XCV2",mod1, "azul");
		Auto auto3 = new Auto("XCH1",mod1, "azul");
		Auto auto4 = new Auto("JCV1",mod2, "azul");
		
		
		s.save(auto);
		s.save(auto2);
		s.save(auto3);
		s.save(auto4);
	
		Auto autoRecuperado = s.get(Auto.class,auto3.getId());
//		TODO: uniqueResult() solo sirve para uno o cero resultados. Sino devuelve una exception q no e sun unico resultado
		Auto autoBuscado = (Auto) getSession().createCriteria(Auto.class).add(Restrictions.like("patente", "XCH1")).uniqueResult();
		assertThat(autoBuscado.getPatente()).isEqualTo(autoRecuperado.getPatente());
	
	}
	
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testConsultaporModeloDeAuto() {
		Session s = getSession();

		// Auto auto = new Auto("XCV1","x", "azul");

		Modelo mod1 = new Modelo("modelo1");
		Modelo mod2 = new Modelo("modelo2");
		Modelo mod3 = new Modelo("modelo3");

		s.save(mod1);
		s.save(mod2);
		s.save(mod3);

		Auto auto2 = new Auto("XCV2", mod2, "azul");
		Auto auto3 = new Auto("XCH1", mod2, "azul");
		Auto auto4 = new Auto("JCV1", mod3, "azul");

		// s.save(auto);
		s.save(auto2);
		s.save(auto3);
		s.save(auto4);

		Auto autoRecuperado = s.get(Auto.class, auto3.getId());
		
		
		
		List autoBuscadoPorModelo = getSession().createCriteria(Auto.class)
												.createAlias("modelo", "modeloBuscado")
												.add(Restrictions.eq("modeloBuscado.nombre", "modelo2")).list();
		
		assertThat(autoBuscadoPorModelo.size()).isEqualTo(2);
		
	}

}
