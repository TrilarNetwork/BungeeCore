package me.imelvin.bungeecore.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import me.imelvin.bungeecore.Main;

public class ReportHandler {
	
	public static void makeReport(String owner, String desc) {
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			PreparedStatement s = Main.sql.getConnection().prepareStatement("INSERT INTO player_reports (owner, status, description) VALUES "
					+ "('" + owner + "', '" + "&cOPEN" + "', '" + desc + "')");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static void closeReport(int key) {
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			PreparedStatement s = Main.sql.getConnection().prepareStatement("UPDATE player_reports SET status='&cCLOSED' WHERE id='" + key + "'");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static void openReport(int key) {
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			PreparedStatement s = Main.sql.getConnection().prepareStatement("UPDATE player_reports SET status='&aOPEN' WHERE id='" + key + "'");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static String getOwner(int key) {
		String owner = "";
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_reports WHERE id='" + key + "'");
			if (rs.next()) {
				owner = rs.getString("owner");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return owner;
	}
	
	public static String getDesc(int key) {
		String desc = "";
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_reports WHERE id='" + key + "'");
			if (rs.next()) {
				desc = rs.getString("description");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return desc;
	}
	
	public static String getStatus(int key) {
		String status = "";
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_reports WHERE id='" + key + "'");
			if (rs.next()) {
				status = rs.getString("status");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return status;
	}
	
	public static void purgeOldTickets() {
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			PreparedStatement s = Main.sql.getConnection().prepareStatement("DELETE FROM player_reports WHERE status='&cCLOSED'");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static ArrayList<Integer> getOpenReports() {
		ArrayList<Integer> keys = new ArrayList<Integer>();
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_reports WHERE status='&aOPEN'");
			while (rs.next()) {
				keys.add(rs.getInt("id"));
			}
			Collections.sort(keys);
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return keys;
	}
}
