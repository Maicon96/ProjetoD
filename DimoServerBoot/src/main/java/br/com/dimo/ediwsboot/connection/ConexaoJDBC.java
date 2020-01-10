package br.com.dimo.ediwsboot.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConexaoJDBC {

	
	public Connection getConnection();
	
	public void closeConnection();
	
	public void commit() throws SQLException;
	
	public void rollback();
	
}
