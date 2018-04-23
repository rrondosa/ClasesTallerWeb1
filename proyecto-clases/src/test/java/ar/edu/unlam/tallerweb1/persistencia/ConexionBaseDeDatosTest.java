package ar.edu.unlam.tallerweb1.persistencia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Clase que prueba la conexion a la base de datos. Hereda de SpringTest por lo que corre dentro del contexto
// de spring
public class ConexionBaseDeDatosTest extends SpringTest {

	@Test
	@Transactional
	@Rollback(true)
	public void pruebaConexion() {
		assertThat(getSession().isConnected()).isTrue();
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testQueInsertaUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail("uno@gmail.com");
		usuario.setPassword("123");
		usuario.setRol("pepe");

		getSession().save(usuario);

		Usuario usuarioNuevo = getSession().get(Usuario.class, 1L);

		assertThat(usuarioNuevo.getId()).isEqualTo(1L);
	}

}
