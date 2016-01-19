package integrationtests;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;


//import testutils.IntegrationBase;
import users.data.UsersService;
import users.entities.User;
import jobs.data.FilesService;
import jobs.data.JobsService;
import jobs.entities.File;
import jobs.entities.Job;
import testutils.IntegrationBase;

public class FilesServiceIT extends IntegrationBase {
	FilesService filesService;
	
	@Before
	public void setUp() {
		this.filesService = new FilesService();
	}
	
	@Test
	public void testCreateFileFromInputStreamAndGetInputStreamForFile() throws SQLException, IOException {
		String testString = "El alto TP";
		String name = "alto_tp.txt";
		InputStream stream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));

		File file = filesService.createFileFromInputStream(stream, name);
		
		assertNotNull("Debería haber un file", file);
		assertNotNull("Debería tener un id", file.getId());
		
		String theString;
		try (InputStream is = filesService.getInputStreamForFile(file)) {
			theString = IOUtils.toString(is, StandardCharsets.UTF_8);
		}
		
		assertEquals("El contendio debe haberse guardado y volver correctamente", testString, theString);
		
	}

}
