/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.aut.dms;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class DataBase {
    
    private static final String CREATE_LOCATION_TABLE = "CREATE TABLE LOCATIONS (LAT VARCHAR(100), LON VARCHAR(100))";
    
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String URL = "jdbc:derby://localhost:1527/ChickenFarm/;create=true";
        private String userName = "chicken";
        private String password = "work";
	private Connection conn;
	private PreparedStatement psInsert;
	private Statement s;

	public DataBase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName(driver);
		conn = DriverManager.getConnection(URL, userName, password);
		psInsert = null;
		s = conn.createStatement();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				shutDownDatabase();
			}
		});
                
            //Initializing Tables
            if(!tableExists("LOCATIONS")){
                execute(CREATE_LOCATION_TABLE);
            }
           
	}

	public boolean execute(String exc) throws SQLException {
		return s.execute(exc);
	}

	public void shutDownDatabase() {
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
			//DriverManager.getConnection("jdbc:derbDataBasey:" + dbName + ";shutdown=true");
		} catch (SQLException se) {
		} finally {
			//Connection
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException sqle) {
			}
		}
	}

	/**
	 * <pre>
	 * <code>
	 * psInsert = conn.prepareStatement("insert into location values (?, ?)");
	 * psInsert.setString(1, "user");
	 * psInsert.setString(2, "Webster St.");
	 * ...
	 * psInsert.setString(pair.left, pair.right);
	 * psInsert.executeUpdate();
	 * </code>
	 * @param ins
	 * @param pairs
	 * @throws SQLException
	 */
	public void insert(String ins, Collection<LoginPair<Integer, String>> pairs) throws SQLException {
		psInsert = conn.prepareStatement(ins);
		if (pairs != null) {
			for (LoginPair<Integer, String> pair : pairs) {
				psInsert.setString(pair.identifier, pair.value);
			}
		}
		psInsert.executeUpdate();
	}

	public void update(String upd, Collection<LoginPair<Integer, String>> pairs) throws SQLException {
		insert(upd, pairs);
	}

	public ResultSet query(String query) throws SQLException {
		ResultSet result = s.executeQuery(query);
		return result;
	}

	public void commit() throws SQLException {
		conn.commit();
	}

	public boolean tableExists(String tableName) throws SQLException {
		DatabaseMetaData dbm = conn.getMetaData();
		ResultSet tables = dbm.getTables(null, null, tableName, null);
		boolean exists = tables.next();
		tables.close();
		return exists;
	}

	/**
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public List<List<String>> queryAsListOfRecords(String query) throws SQLException {
		ResultSet rs = query(query);
                ResultSetMetaData metaData = rs.getMetaData();
		int columns = metaData.getColumnCount();

		List<List<String>> al = new ArrayList<>();

		while (rs.next()) {
			ArrayList<String> record = new ArrayList<>();

			for (int i = 1; i <= columns; i++) {
				String value = rs.getString(i);
				record.add(value);
			}
			al.add(record);
		}
		rs.close();
		return al;
	}
        
        public static class LoginPair<T, V> {
		T identifier;
		V value;

		public LoginPair(T identifier, V value) {
			this.identifier = identifier;
			this.value = value;
		}

		public T getLeft() {
			return this.identifier;
		}

		public V getRight() {
			return this.value;
		}

		@Override
		public String toString() {
			return this.identifier.toString() + " " + this.value.toString();
		}
	}

}
