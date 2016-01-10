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
import jobs.data.JobsService;
import jobs.entities.Job;

public class JobsServiceIT {
	JobsService jobsService;
	
	@Before
	public void setUp() {
		this.jobsService = new JobsService();
	}
	
	@Test
	public void testCreateJobForUser() {
		Job job = null;
		User user = new User();
		user.setUsername("sebabaskov");
		
		try {
			job = jobsService.createJobForUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
