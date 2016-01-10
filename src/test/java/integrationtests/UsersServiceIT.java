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
	public void testAuthenticateWithCorrectData() throws Exception {
		
		User user = null;
		user = usersService.authenticate("pablo", "123");
		
		assertNotNull("User should exist", user);
		assertEquals("User should be the right one", user.getUsername(), "pablo");
		
	}
	
	@Test(expected=AuthException.class)
	public void testAuthenticateWithWrongPassword() throws AuthException, SQLException {
		
		usersService.authenticate("pablo", "1dfwe23");
		
	}

	@Test
	public void testGetUserByUsername() throws AuthException, SQLException {
		User user = usersService.getUserByUsername("pablo");
		
		assertNotNull("User should exist", user);
		assertEquals("User should be the right one", user.getUsername(), "pablo");
	}
	@Test
	public void testCreateUser() throws AuthException, SQLException {
		
		User user = new User();
		user.setUsername("chalo");
		user.setEmail("gperfar@gmail.com");
		user.setPassword("40222RTYU");
		
		user = usersService.createUser(user);
		user = null;
		user = usersService.getUserByUsername("chalo");
		
		assertNotNull("User should have been registered", user);		
	}

	@Test(expected=AuthException.class)
	public void testCreateUserSameUsername() throws AuthException, SQLException {
		
		User user = new User();
		user.setUsername("pablo");
		user.setEmail("gperfar@gmail.com");
		user.setPassword("40222RTYU");
	
		user = usersService.createUser(user);
				
	}

	@Test(expected=AuthException.class)
	public void testCreateUserSameEmail() throws AuthException, SQLException {
		
		User user = new User();
		user.setUsername("chalo");
		user.setEmail("pablo@gmail.com");
		user.setPassword("40222RTYU");
	
		user = usersService.createUser(user);
	}
}
