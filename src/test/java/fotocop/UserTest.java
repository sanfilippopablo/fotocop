package fotocop;

import static org.junit.Assert.*;

import org.junit.Test;

import common.ValidationManager;
import users.entities.User;

public class UserTest {

	@Test
	public void testPassword() {
		User user = new User();
		
		user.setPassword("asd123");
		
		assertFalse("Contraseña incorrecta debe dar falso", user.checkPassword("asdasdasd"));
		assertTrue("Contraseña correcta debe dar true", user.checkPassword("asd123"));
		
		user.setPassword("123asd");
		
		assertTrue("Cambio de contraseña correcta", user.checkPassword("123asd"));
		
	}

}
