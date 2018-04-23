package ar.edu.unlam.tallerweb1.persistencia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Direccion;
import ar.edu.unlam.tallerweb1.modelo.Empresa;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public class PersistenciaTest extends SpringTest{
	

	
	@Test
	@Transactional @Rollback(true)
	public void testMapeoRelacionesOneToOne(){
		
		Direccion direccion = new Direccion();
		direccion.setCalle("calle");
		direccion.setNumero(4545);
		
		getSession().save(direccion);
		
		Empresa empresa = new Empresa();
		empresa.setNombre("Sancor");
		empresa.setDir(direccion);
		
		getSession().save(empresa);
		
		Empresa empresarecuperada = getSession().get(Empresa.class,1);
		
		assertThat(empresarecuperada.getNombre()).isEqualTo(empresa.getNombre());
		assertThat(empresarecuperada.getId()).isEqualTo(empresa.getId());
		assertThat(empresarecuperada.getDireccion().getCalle()).isEqualTo(empresa.getDireccion().getCalle());
		assertThat(empresarecuperada.getDireccion().getNumero()).isEqualTo(empresa.getDireccion().getNumero());
		assertThat(empresarecuperada.getDireccion().getId()).isEqualTo(empresa.getDireccion().getId());
	}
}
