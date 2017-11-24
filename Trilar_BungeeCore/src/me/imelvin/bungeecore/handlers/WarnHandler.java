package me.imelvin.bungeecore.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.imelvin.bungeecore.Main;

public class WarnHandler {
	
	public static void warnPlayer(String name, String reason) {
		PreparedStatement s = null;
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			s = Main.sql.getConnection().prepareStatement("INSERT INTO player_warns (player, reason, label) VALUES ('" + name + "', '" + reason + "', '" 
					+ (getWarns(name) + 1) +"')");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static int getWarns(String name) {
		int warns = 0;
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_warns WHERE player='" + name + "'");
			int iters = 0;
			while (rs.next()) {
				iters++;
			}
			warns = iters;
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return warns;
	}
	
	public static boolean exists(String name, int label) {
		boolean exists = false;
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_warns WHERE (player='" + name + "' AND label='" + label + "')");
			if (rs.next()) {
				exists = true;
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return exists;
	}
	
	public static void deleteWarn(String name, int label) {
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			PreparedStatement s = Main.sql.getConnection().prepareStatement("DELETE FROM player_warns WHERE (player='" + name + "' AND label='" + label + "')");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static String getWarnReason(String name, int label) {
		String reason = "";
		try {
			if (!Main.sql.hasConnection()) {
				Main.sql.openConnection();
			}
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_warns WHERE (player='" + name + "' AND label='" + label + "')");
			if (rs.next()) {
				reason = rs.getString("reason");
			}
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return reason;
	}
}
