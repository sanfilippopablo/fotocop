package testutils;

import static org.junit.Assert.*;

import org.junit.Before;

public class IntegrationBase {

	@Before
	public void resetDB() {
		try {
			Runtime rt = Runtime.getRuntime();
			Process process = rt.exec("/bin/bash /vagrant/scripts/setup_db.sh");
			process.waitFor();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
