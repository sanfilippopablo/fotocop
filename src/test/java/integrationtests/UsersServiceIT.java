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
	@Test
	public void testCreateUser() {
		
		User user = new User();
		user.setUsername("chalo");
		user.setEmail("gperfar@gmail.com");
		user.setPassword("40222RTYU");
		
		try {
			user = usersService.createUser(user);
			user = null;
			user = usersService.getUserByUsername("chalo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull("User should have been registered", user);		
	}

	@Test
	public void testCreateUserSameUsername() {
		
		User user = new User();
		user.setUsername("pablo");
		user.setEmail("gperfar@gmail.com");
		user.setPassword("40222RTYU");
	
		try {	
			try {
				user = usersService.createUser(user);
				fail("La línea anterior debe tirar error");
			} catch (AuthException e) {
				e.printStackTrace();
			}
		}
		catch (Exception e){
		}
	}

	@Test
	public void testCreateUserSameEmail() {
		
		User user = new User();
		user.setUsername("chalo");
		user.setEmail("pablo@gmail.com");
		user.setPassword("40222RTYU");
	
		try {	
			try {
				user = usersService.createUser(user);
				fail("La línea anterior debe tirar error");
			} catch (AuthException e) {
				e.printStackTrace();
			}
		}
		catch (Exception e){
		}
	}
}
