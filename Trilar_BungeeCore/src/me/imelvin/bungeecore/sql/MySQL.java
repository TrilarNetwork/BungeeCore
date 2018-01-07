package me.imelvin.bungeecore.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import me.imelvin.bungeecore.Main;

/**
 * Connects to and uses a MySQL database
 * 
 * @author -_Husky_-
 * @author tips48
 */
public class MySQL {
	private String user; 
	private String database;
	private String password;
	private int port;
	private String hostname;

	private Connection conn;
	/**
	 * Creates a new MySQL instance
	 *
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	/*@Deprecated
	public MySQL(String hostname, String port, String username,
			String password) {
		this(hostname, port, null, username, password);
	}

	 * Creates a new MySQL instance for a specific database
	 *
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param database
	 *            Database name
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 *
	@Deprecated
	public MySQL(String hostname, String port, String database,
			String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.user = username;
		this.password = password;
	} */
	
	/**
	 * Creates a new MySQL instance for a specific database
	 *
	 * @param hostname
	 *            Name of the host
	 * @param port
	 *            Port number
	 * @param database
	 *            Database name
	 * @param username
	 *            Username
	 * @param password
	 *            Password
	 */
	public MySQL(String hostname, int port, String db, String user, String pw) {
		this.hostname = hostname;
		this.port = port;
		this.database = db;
		this.user = user;
		this.password = pw;
		this.conn = openConnection();
	}

	public Connection openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database 
					+ "?connectTimeout=0&socketTimeout=0&autoReconnect=true", this.user, this.password);
			this.conn = conn;
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this.conn;
	}
	
	public Connection openConnection(String hostname, int port, String database, String user, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + database 
				+ "?connectTimeout=0&socketTimeout=0&autoReconnect=true", user, password);
			this.conn = conn;
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return this.conn;
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public boolean hasConnection() {
		return (conn != null);
	}
	
	public void closeConnection() {
		try {
			if (hasConnection()) {
				this.conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.conn = null;
		}
	}
	
	public void createTable() {
		try {
			Statement s = this.conn.createStatement();
			Statement s1 = this.conn.createStatement();
			Statement s2= this.conn.createStatement();
			Statement s3 = this.conn.createStatement();
			Statement s4 = this.conn.createStatement();
			Statement s5 = this.conn.createStatement();
			Statement s6 = this.conn.createStatement();
			Statement s7 = this.conn.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS player_bans (player varchar(32), reason text)");
			s1.executeUpdate("CREATE TABLE IF NOT EXISTS player_tempbans (player varchar(32), reason text, time bigint)");
			s2.executeUpdate("CREATE TABLE IF NOT EXISTS player_kicks (player varchar(32), kicks int, reason text)");
			s3.executeUpdate("CREATE TABLE IF NOT EXISTS player_ipbans (player varchar(32), reason text, ip varchar(50))");
			s4.executeUpdate("CREATE TABLE IF NOT EXISTS player_mutes (player varchar(32), mutes int, time bigint)");
			s5.executeUpdate("CREATE TABLE IF NOT EXISTS player_ips (player varchar(32), ip varchar(50))");
			s6.executeUpdate("CREATE TABLE IF NOT EXISTS player_warns (player varchar(32), reason text, label int)");
			s7.executeUpdate("CREATE TABLE IF NOT EXISTS player_reports (id INTEGER AUTO_INCREMENT PRIMARY KEY, owner varchar(32), status varchar(32), "
					+ "description varchar(128))");
			s.close();
			s1.close();
			s2.close();
			s3.close();
			s4.close();
			s5.close();
			s6.close();
			s7.close();
		} catch (Exception e) {
			Main.p.getLogger().info("Trilar Core > Error: " + e);
		}
 	}
	
	public void getReady() {
		if (!hasConnection()) {
			openConnection();
		}
	}
}
