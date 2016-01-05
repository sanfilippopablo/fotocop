package integrationtests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import testutils.IntegrationBase;
//import testutils.IntegrationBase;
import users.data.UsersService;
import users.entities.User;
import users.exceptions.AuthException;

public class UsersServiceIT extends IntegrationBase {
	
	UsersService usersService;

	@Before
	public void setUp() {
		this.usersService = new UsersService();
	}
	
	@Test
	public void testAuthenticateWithCorrectData() {
		
		User user = null;
		try {
			user = usersService.authenticate("pablo", "123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull("User should exist", user);
		assertEquals("User should be the right one", user.getUsername(), "pablo");
		
	}
	
	@Test
	public void testAuthenticateWithWrongPassword(){
		try {
			try {
				User user = usersService.authenticate("pablo", "1dfwe23");
				fail("La línea anterior debe tirar error");
			} catch (AuthException e) {
			}
		} catch (Exception e) {
		}
	}

	@Test
	public void testGetUserByUsername() {
		User user = null;
		try {
			user = usersService.getUserByUsername("pablo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull("User should exist", user);
		assertEquals("User should be the right one", user.getUsername(), "pablo");
	}

}
