package jobs.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

import jobs.entities.Job;
import jobs.entities.JobLine;
import common.DBConnection;
import users.data.UsersService;
import users.entities.User;
import users.exceptions.AuthException;

public class JobsService {
	
	/**
	 * Crea el Job en la DB (con el user asignado) y lo devuelve
	 * con el ID asignado por la DB.
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
		
		String sql = "INSERT INTO jobs(`user`, `creationDate`, `lastModifiedDate`, `status`) VALUES (?, ?, ?, ?);";
		PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		ps.setInt(1, user.getId());
		ps.setTimestamp(2, new java.sql.Timestamp(job.getCreationDate().getTime()));
		ps.setTimestamp(3, new java.sql.Timestamp(job.getLastModifiedDate().getTime()));
		ps.setString(4, job.getStatus());
		
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		job.setId(rs.getInt(1));
		
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
	/**
	 * Ubica los trabajos pendientes de un usuario dado y los devuelve en un ArrayList.
	 * 
	 * @param u
	 * @throws SQLException 
	 */ 
	public ArrayList<Job> getPendingJobsForUser(User u) throws SQLException{
		ArrayList<Job> pendingJobs = new ArrayList<Job>();
		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM jobs WHERE user = ? AND (status <> 'Retirado');";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, u.getId());
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			Job auxJob = new Job();
			auxJob.setId(resultSet.getInt("id"));
			auxJob.setStatus(resultSet.getString("status"));
			auxJob.setUser(u);
			auxJob.setCreationDate(resultSet.getTimestamp("creationDate"));
			auxJob.setEta(resultSet.getTimestamp("eta"));
			auxJob.setLastModifiedDate(resultSet.getTimestamp("lastModifiedDate"));
		
			pendingJobs.add(auxJob);
		}
		return pendingJobs;
	}
	
	/**
	 * Agrega una JobLine dada a un Job dado y lo registra en la DB.
	 * 
	 * @param j, jl
	 * @throws SQLException 
	 */ 
	public void addJobLineToJob(Job j, JobLine jl) throws SQLException{
		//Lo agregamos al job
		j.addJobLine(jl);
		//Lo agregamos a la DB
				Connection connection = DBConnection.getConnection();
				String sql = "INSERT INTO joblines(`file`, `quantity`, `abrochado`, `anillado`, `dobleFaz`) VALUES (?, ?, ?, ?, ?);";
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, jl.getFile().getId());
				ps.setInt(2, jl.getQuantity());
				ps.setBoolean(3, jl.isAbrochado());
				ps.setBoolean(4, jl.isAnillado());
				ps.setBoolean(5, jl.isDobleFaz());
				ps.executeUpdate();
	}
}
