package jobs.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

import jobs.entities.File;
import jobs.entities.Job;
import jobs.entities.JobLine;
import common.DBConnection;
import common.exceptions.NotFoundException;
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
	public Job createJobForUser(User user) throws SQLException {
		Job job = new Job();
		job.setCreationDate(new Date());
		job.setLastModifiedDate(new Date());
		job.setUser(user);
		job.setStatus("Abierto");
		
		try(Connection connection = DBConnection.getConnection()){
			String sql = "INSERT INTO jobs(`user`, `creationDate`, `lastModifiedDate`, `status`) VALUES (?, ?, ?, ?);";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, user.getId());
			ps.setTimestamp(2, new java.sql.Timestamp(job.getCreationDate().getTime()));
			ps.setTimestamp(3, new java.sql.Timestamp(job.getLastModifiedDate().getTime()));
			ps.setString(4, job.getStatus());
			
			try{
				ps.executeUpdate();
				} finally{}
			
			try(ResultSet rs = ps.getGeneratedKeys()){
				rs.next();
				job.setId(rs.getInt(1));
				return job;	
			}
	
		}

	}
	/**
	 * Ubica un Job a partir de su id y lo devuelve.
	 * 
	 * @param id
	 * @throws SQLException 
	 * @throws NotFoundException 
	 * @throws AuthException 
	 */ 
	public Job getJobById(int id) throws SQLException, NotFoundException, AuthException{
		try(Connection connection = DBConnection.getConnection()){
			String sql = "SELECT * FROM jobs WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			try(ResultSet resultSet = statement.executeQuery()){
				if (!resultSet.next()) {
					throw new NotFoundException("Trabajo inexistente.");
				}
				else {
					//Si el trabajo existe, lo creamos y cargamos
					Job job = new Job();
					UsersService us = new UsersService();
					job.setId(id);
					job.setCreationDate(resultSet.getDate("creationDate"));
					job.setLastModifiedDate(resultSet.getDate("lastModifiedDate"));
					try {
						//Cargamos el user según su id
						job.setUser(us.getUserById(resultSet.getInt("id")));
					} catch (AuthException e) {
						e.printStackTrace();
					}
					ArrayList<JobLine> jobLines = new ArrayList<JobLine>();
					try(Connection connection2 = DBConnection.getConnection()){
						//Para cargar el arreglo de JobLines, necesitamos otra SQL sentence
						sql = "SELECT * FROM joblines WHERE job = ?;";
						statement = connection2.prepareStatement(sql);
						statement.setInt(1, id);
						try(ResultSet resultSet2 = statement.executeQuery()){
							//Si hay joblines para ese job, se las cargamos
							FilesService fs = new FilesService();
							while(resultSet2.next()){
								JobLine jl = new JobLine();
								Integer aux = resultSet2.getInt("abrochado");
								jl.setAbrochado(aux.equals(1));
								aux = resultSet2.getInt("anillado");
								jl.setAnillado(aux.equals(1));
								aux = resultSet2.getInt("dobleFaz");
								jl.setDobleFaz(aux.equals(1));
								File file = fs.getFileById(resultSet2.getInt("file"));
								jl.setFile(file);
								jl.setQuantity(resultSet2.getInt("quantity"));
								jl.setJob(job);
								//Una vez cargada la JobLine, se la agregamos al arreglo de JobLines y dicho arreglo al Job
								jobLines.add(jl);
							}
						}
					}
					job.setJobLines(jobLines);
					return job;
				}
			}
		}
	}
	/**
	 * Ubica los trabajos pendientes de un usuario dado y los devuelve en un ArrayList.
	 * 
	 * @param u
	 * @throws SQLException 
	 * @throws AuthException 
	 * @throws NotFoundException 
	 */ 
	public ArrayList<Job> getPendingJobsForUser(User u) throws SQLException, NotFoundException, AuthException{
		ArrayList<Job> pendingJobs = new ArrayList<Job>();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "SELECT * FROM jobs WHERE user = ? AND (status <> 'Retirado');";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, u.getId());
			try(ResultSet resultSet = statement.executeQuery()){
				while (resultSet.next()) {
					pendingJobs.add(getJobById(resultSet.getInt("id")));
				}
				return pendingJobs;
			}
		}
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
			try(Connection connection = DBConnection.getConnection()){
				String sql = "INSERT INTO joblines(`file`, `quantity`, `abrochado`, `anillado`, `dobleFaz`, `job`) VALUES (?, ?, ?, ?, ?, ?);";
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, jl.getFile().getId());
				ps.setInt(2, jl.getQuantity());
				ps.setBoolean(3, jl.isAbrochado());
				ps.setBoolean(4, jl.isAnillado());
				ps.setBoolean(5, jl.isDobleFaz());
				ps.setInt(6, j.getId());
				try{
					ps.executeUpdate();
				}finally{}
			}

	}
	/**
	 * Cambia el estado del Job a Enviado, calcula y setea un ETA, y registra en la DB.
	 * 
	 * @param j
	 * @throws SQLException 
	 */ 
	public void submitjob(Job j) throws SQLException{
		JobsService js = new JobsService();
		j.setStatus("Enviado");
		j.setLastModifiedDate(new Date());
		Date eta = new Date();
		//Calculamos eta siendo igual al momento actual + lo que tarde en imprimirse el actual trabajo + lo que tardan en imprimirse los trabajos enviados
		long readyToPickupTime = j.getPrintingTime() + js.getSentJobsTime(); 
		eta.setTime(eta.getTime() + readyToPickupTime);
		j.setEta(eta);
		//Guardamos en la DB
		try(Connection connection = DBConnection.getConnection()){
			String sql = "UPDATE jobs SET status = ?, eta = ?, lastModifiedDate = ?  WHERE id = ? ";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, j.getStatus());
			ps.setTimestamp(2, new java.sql.Timestamp(j.getEta().getTime()));
			ps.setTimestamp(3, new java.sql.Timestamp(j.getLastModifiedDate().getTime()));
			ps.setInt(4, j.getId());
			try{
				ps.executeUpdate();
			}finally{
				
			}
		}
	}
	/**
	 * Calcula y devuelve el tiempo que se tarda en imprimir los trabajos enviados.
	 * 
	 * @throws SQLException 
	 */ 
	public long getSentJobsTime() throws SQLException{
		long pendingJobsTime = 0;
		try(Connection connection = DBConnection.getConnection()){
			String sql = "SELECT eta FROM jobs WHERE status = 'Enviado';";
			PreparedStatement statement = connection.prepareStatement(sql);
				try(ResultSet resultSet = statement.executeQuery()){
					while (resultSet.next()) {
						pendingJobsTime += resultSet.getLong("eta");
					}
					return pendingJobsTime;
				}

		}

	}
	/**
	 * Ubica un job en la DB y lo actualiza.
	 * 
	 * @param j
	 * @throws SQLException 
	 */ 
	public void updateJob(Job j) throws SQLException{
		//Actualizamos en la DB
		try(Connection connection = DBConnection.getConnection()){
			String sql = "UPDATE jobs SET status = ?, eta = ?, lastModifiedDate = ?, user = ?, creationDate = ?  WHERE id = ? ";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, j.getStatus());
			ps.setTimestamp(2, new java.sql.Timestamp(j.getEta().getTime()));
			ps.setTimestamp(3, new java.sql.Timestamp(j.getLastModifiedDate().getTime()));
			ps.setInt(4, j.getUser().getId());
			ps.setTimestamp(5, new java.sql.Timestamp(j.getCreationDate().getTime()));
			ps.setInt(6, j.getId());
			try{
				ps.executeUpdate();
			}finally{
				
			}
		}
	}
	/**
	 * Ubica los trabajos pendientes de impresión y los devuelve en un ArrayList.
	 * 
	 * 
	 * @throws SQLException 
	 * @throws AuthException 
	 */ 
	public ArrayList<Job> getPendingJobs() throws SQLException, AuthException{
		ArrayList<Job> pendingJobs = new ArrayList<Job>();
		UsersService us = new UsersService();
		try(Connection connection = DBConnection.getConnection()){
			String sql = "SELECT id FROM jobs WHERE status = 'Enviado';";
			PreparedStatement statement = connection.prepareStatement(sql);
			try(ResultSet resultSet = statement.executeQuery()){
				//Por cada uno, creamos un Job acá, lo cargamos con la data, y lo agregamos al array de resultados
				while (resultSet.next()) {
					try {
						pendingJobs.add(getJobById(resultSet.getInt("id")));
					} catch (NotFoundException e) {
						// This won't happen but Java doesn't know that
						e.printStackTrace();
					}
				}
				return pendingJobs;
			}
		}
	}
}
