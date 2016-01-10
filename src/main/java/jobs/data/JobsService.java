package jobs.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import jobs.entities.Job;
import common.DBConnection;
import users.entities.User;
import users.exceptions.AuthException;

public class JobsService {
	
	/**
	 * Crea el Job en la DB (con el user asignado) y lo devuelve.
	 * 
	 * @param user
	 * @throws SQLException 
	 * @throws AuthException 
	 */ 
	public Job createJobForUser(User user) throws SQLException, AuthException{
		Job job = null;
		Connection connection = DBConnection.getConnection();
		
		job.setCreationDate(new Date());
		job.setLastModifiedDate(new Date());
		job.setUser(user);
		job.setStatus("Abierto");
		
		String sql = "INSERT INTO jobs (user, creationDate, lastModifiedDate, status) VALUES ('?', '?', '?','?);";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, user.getUsername());
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		statement.setDate(2, date);
		statement.setDate(3, date);
		statement.setString(4, job.getStatus());
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.rowInserted() == false){
			throw new AuthException("No se pudo agregrar el trabajo a la DB");
		}
		job.setId(resultSet.getInt("id"));
		return job;
	}
	
	
	
	
}
