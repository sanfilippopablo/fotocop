package jobs.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.Statement;

import jobs.entities.Job;
import common.DBConnection;
import users.data.UsersService;
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
		Job job = new Job();
		Connection connection = DBConnection.getConnection();
		
		job.setCreationDate(new Date());
		job.setLastModifiedDate(new Date());
		job.setUser(user);
		job.setStatus("Abierto");
		
		String sql = "INSERT INTO jobs (user, creationDate, lastModifiedDate, status) VALUES ((SELECT id FROM users WHERE username = ?), ?, ?,?);";
		PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, user.getUsername());
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		ps.setDate(2, date);
		ps.setDate(3, date);
		ps.setString(4, job.getStatus());
		ps.executeUpdate(sql);
		ResultSet rs = ps.getGeneratedKeys();
		
		if (rs.rowInserted() == false){
			throw new AuthException("No se pudo agregrar el trabajo a la DB");
		}
		job.setId(rs.getInt("id"));
		return job;

	}
	
	/**
	 * Ubica un Job a partir de su id y lo devuelve.
	 * 
	 * @param id
	 * @throws SQLException 
	 * @throws AuthException 
	 */ 
	public Job getJobById(int id) throws SQLException, AuthException{
		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM jobs WHERE id = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		
		if (!resultSet.next()) {
			throw new AuthException("Trabajo inexistente.");
		}
		else {
			Job job = new Job();
			UsersService us = new UsersService();
			job.setId(id);
			job.setCreationDate(resultSet.getDate("creationDate"));
			job.setLastModifiedDate(resultSet.getDate("lastModifiedDate"));
			job.setUser(us.getUserById(resultSet.getInt("id")));
			return job;
			}
	
	}
}
