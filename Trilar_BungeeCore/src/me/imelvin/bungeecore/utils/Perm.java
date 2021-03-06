package me.imelvin.bungeecore.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import me.imelvin.bungeecore.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Deprecated
public class Perm {
	public static Map<String, PermGroup> pPerms = new HashMap<String, PermGroup>();
	
	public static boolean hasPerm(ProxiedPlayer p, PermGroup g) {
		return hasPerm(p.getName(), g);
	}

	public static boolean hasPerm(String name, PermGroup g) {
		if (g.getId() <= getGroup(name).getId()) {
			return true;
		}
		return false;
	}
	
	public static PermGroup getGroup(ProxiedPlayer p) {
		return getGroup(p.getName());
	}
	
	public static PermGroup getGroup(String name) {
		if (pPerms.containsKey(name)) {
			return pPerms.get(name);
		} else {
			return getGroupFromDatabase(name);
		}
	}
	
	public static PermGroup getGroupFromDatabase(ProxiedPlayer p) {
		return getGroupFromDatabase(p.getName());
	}
	
	public static void updateGroup(ProxiedPlayer p, PermGroup g) {
		updateGroup(p.getName(), g);
	}
	
	public static void updateGroup(String name, PermGroup g) {
		if (pPerms.containsKey(name)) {
			pPerms.put(name, g);
		}
		try {
			Statement s = null;
			Main.sql.getReady();
			s = Main.sql.getConnection().createStatement();
			PreparedStatement pS = null;
			ResultSet rs = s.executeQuery("SELECT `rank` FROM `player_ranks` WHERE `player` LIKE '" + name + "'");
			if (!rs.next()) {
				pS = Main.sql.getConnection().prepareStatement("INSERT INTO `player_ranks` (`player`, `rank`) VALUES ('" + name + "', '" + g.toString().toLowerCase() + "')");
			} else {
				pS = Main.sql.getConnection().prepareStatement("UPDATE `player_ranks` SET `rank`='" + (g.toString().toLowerCase()) + "' WHERE `player` LIKE '" + name + "'");
			}
			pS.executeUpdate();
			pS.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static PermGroup getGroupFromDatabase(String name) {
		String str = PermGroup.MEMBER.toString();
		try {
			Statement s = null;
			Main.sql.getReady();
			s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT `rank` FROM `player_ranks` WHERE `player`='" + name + "'");
			while (rs.next()) {
				str = rs.getString("rank").toUpperCase();
			}
			return PermGroup.get(str); 
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void loginPlayer(String name) {
		pPerms.put(name, getGroup(name));
	}
	
	public static void logoutPlayer(String name)  {
		if (pPerms.containsKey(name)) {
			pPerms.remove(name);
		}
	}
}
