package integrationtests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;



//import testutils.IntegrationBase;
import users.data.UsersService;
import users.entities.User;
import jobs.data.JobsService;
import jobs.entities.Job;

public class JobsServiceIT {
	JobsService jobsService;
	
	@Before
	public void setUp() {
		this.jobsService = new JobsService();
	}
	
	@Test
	public void testCreateJobForUser() throws Exception{
		UsersService usersService = new UsersService();
		Job job = null;
		
		User user = usersService.getUserByUsername("pablo");
		job = jobsService.createJobForUser(user);
			
		assertNotNull("Job should have been created", job);
		assertTrue("Job should have a valid id", job.getId() > 0);

	}
	
	@Test
	public void testGetJobById() throws Exception{
		Job job = null;
		JobsService js = new JobsService();
		job = js.getJobById(1);
		assertNotNull("Job should exist", job);
		assertEquals("Job should be the right one", job.getId(), 1);
	}
	
	@Test
	public void testGetPendingJobsForUser() throws Exception{
		ArrayList<Job> pendingJobs = null;
		UsersService us = new UsersService();
		JobsService js = new JobsService();
		User user = us.getUserById(1);
		pendingJobs = js.getPendingJobsForUser(user);
		assertNotNull("Pending jobs array should have been created", pendingJobs);
	}
}
