package me.imelvin.bungeecore.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.imelvin.bungeecore.Main;

public class IPSaver {
	
	public static void saveIP(String name, String ip) {
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			PreparedStatement s = null;
			if (getIP(name) != null) {
				s = Main.sql.getConnection().prepareStatement("UPDATE player_ips SET ip='" + ip + "' WHERE player='" + name + "'");
			} else {
				s = Main.sql.getConnection().prepareStatement("INSERT INTO player_ips (player, ip) VALUES ('" + name + "', '" + ip + "')");
			}
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static String getIP(String name) {
		String ip = null;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_ips WHERE player='" + name + "'");
			if (rs.next()) {
				ip = rs.getString("ip");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return ip;
	}
	
	public static String getNameFromIp(String ip) {
		String name = null;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_ips WHERE ip='" + ip + "'");
			if (rs.next()) {
				name = rs.getString("player");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return name;
	}
}
