package br.com.dimo.ediwsboot.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPostgresJDBC implements ConexaoJDBC {

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "postgres";
	private Connection connection = null;
	
	
	public ConnectionPostgresJDBC() {
		
		try {
			Class.forName("org.postgresql.Driver");
			
			this.connection = DriverManager.getConnection(this.URL, this.USUARIO, this.SENHA);
			
			this.connection.setAutoCommit(false);
			
			System.out.print("conexão realizada com sucesso!");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Connection getConnection() {
		return this.connection;
	}
	
	@Override
	public void closeConnection() {
		if (this.connection != null) {
			
			try {
				this.connection.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}			
		}		
	}
	
	@Override
	public void commit() throws SQLException {
		this.connection.commit();
		this.closeConnection();
	}
	
	@Override
	public void rollback() {
		if (this.connection != null) {
			try {
				this.connection.rollback();				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.closeConnection();
			}			
		}
	}
}
