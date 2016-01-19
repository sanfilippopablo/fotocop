package jobs.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import common.DBConnection;
import jobs.entities.File;

/**
 * Manejo de todo lo que es archivos en la DB.
 *
 */
public class FilesService {
	
	int BUFFER_LENGTH = 4096;

	/**
	 * Dados un InputStream y un name, crea un File.
	 * Guarda el archivo en el FS y la metadata en la DB.
	 * 
	 * @param is el InputStream con la data.
	 * @param name nombre del archivo.
	 * @return el objeto File, con la metadata y todo.
	 * @throws SQLException
	 * @throws IOException 
	 */
	public File createFileFromInputStream(InputStream is, String name) throws SQLException, IOException {
		
		File file = new File();
		file.setName(name);
		
		try(Connection connection = DBConnection.getConnection()) {
			
			String sql = "INSERT INTO files(name) VALUES (?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, file.getName());
			
			ps.executeUpdate();
			
			try (ResultSet rs = ps.getGeneratedKeys()) {
				rs.next();
				file.setId(rs.getInt(1));
				
			}
		}

		FileOutputStream os = new FileOutputStream(System.getenv("OPENSHIFT_DATA_DIR") + Integer.toString(file.getId()));
        byte[] bytes = new byte[BUFFER_LENGTH];
        int read = 0;
        while ((read = is.read(bytes, 0, BUFFER_LENGTH)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        is.close();
        os.close();
		
		return file;
	}

	/**
	 * Devuelve un InputStream con los datos de el File. Además le inyecta
	 * el valor de length al File.
	 * 
	 * @param file
	 * @return InputStream
	 * @throws FileNotFoundException 
	 */
	public InputStream getInputStreamForFile(File file) throws FileNotFoundException {
		java.io.File inFilesystemFile = new java.io.File(System.getenv("OPENSHIFT_DATA_DIR") + Integer.toString(file.getId()));
	    InputStream input = new FileInputStream(inFilesystemFile);
	    
	    file.setLength(inFilesystemFile.length());
	    
	    return input;
	
	}
}
