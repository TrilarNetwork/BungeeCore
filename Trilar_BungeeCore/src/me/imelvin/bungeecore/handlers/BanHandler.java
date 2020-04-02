package me.imelvin.bungeecore.handlers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import me.imelvin.bungeecore.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BanHandler {
	public static HashMap<String, Long> muted = new HashMap<>();
	public enum BanType {
		TEMP, PERM, IP
	}
	
	public static boolean isBanned(String name) {
		return isPermBanned(name) || isTempBanned(name) || isIPBanned(name, false);
	}
	
	public static BanType getBanType(String name) {
		BanType t = null;
		if (isPermBanned(name)) {
			t = BanType.PERM;
		} else if (isTempBanned(name)) {
			t = BanType.TEMP;
		} else if (isIPBanned(name, false)) {
			t = BanType.IP;
		}
		return t;
	}
	
	public static void banPlayer(String name, String reason) {
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		if (reason == null || reason.isEmpty()) {
			reason = ChatColor.DARK_RED + "The Ban Hammer has spoken!";
		}
		try {
			PreparedStatement s = Main.sql.getConnection().prepareStatement("INSERT INTO player_bans (player, reason) VALUES ('" + name + "', '" + reason + "')");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(name);
		if (tp != null && tp.isConnected()) {
			tp.disconnect(new TextComponent(ChatColor.RED + "You have been banned for: \n" + ChatColor.translateAlternateColorCodes('&', reason)));
		}
		Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been banned for: ");
		Chat.msgAllOps(ChatColor.translateAlternateColorCodes('&', reason));
	}
	
	public static boolean isPermBanned(String name) {
		boolean banned = false;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_bans WHERE player='" + name + "'");
			if (rs.next()) {
				banned = true;
			}
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return banned;
	}
	
	public static String getBanReason(String name) {
		String reason = "";
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_bans WHERE player='" + name + "'");
			if (rs.next()) {
				reason = rs.getString("reason");
			}
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return reason;
	}
	
	public static void kickPlayer(String name, String reason) {
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		if (reason == null) {
			reason = "You have been kicked from the server!";
		}
		try {
			PreparedStatement s;
			if (getKicks(name) != 0) {
				s = Main.sql.getConnection().prepareStatement("UPDATE player_kicks SET (kicks='" + (getKicks(name) + 1) + "' AND reason='" 
						+ reason + "') WHERE player='" + name + "'");
			} else {
				s = Main.sql.getConnection().prepareStatement("INSERT INTO player_kicks (player, kicks, reason) VALUES ('" + name + "', '1','" + reason + "')");
			}
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(name);
		if (tp != null && tp.isConnected()) {
			tp.disconnect(new TextComponent(ChatColor.RED + "You have been kicked for: \n" + ChatColor.translateAlternateColorCodes('&', reason)));
		}
		Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been kicked for:");
		Chat.msgAllOps(ChatColor.translateAlternateColorCodes('&', reason));
	}
	
	public static int getKicks(String name) {
		int kicks = 0;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try { 
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_kicks WHERE player='" + name + "'");
			if (rs.next()) {
				kicks = rs.getInt("kicks");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return kicks;
	}
	
	public static String getKickReason(String name) {
		String reason = null;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try { 
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_kicks WHERE player='" + name + "'");
			if (rs.next()) {
				reason = rs.getString("reason");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return reason;
	}
	
	public static void setTempBanned(String name, String reason, long time) {
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		if (reason == null || reason.isEmpty()) {
			reason = "The Ban Hammer has spoken!";
		}
		try {
			PreparedStatement s = Main.sql.getConnection().prepareStatement("INSERT INTO player_tempbans (player, reason, time) VALUES ('" + name + "', '" 
					+ reason + "', '" + time + "')");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(name);
		if (tp != null && tp.isConnected()) {
			tp.disconnect(new TextComponent(ChatColor.RED + "You have been banned for: \n" + ChatColor.translateAlternateColorCodes('&', reason) + 
					ChatColor.RED + "\nYou will be unbanned in " + (time/1000) + " seconds."));
		}
		Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been banned for: ");
		Chat.msgAllOps(ChatColor.translateAlternateColorCodes('&', reason));
		Chat.msgAllOps(Main.PREFIX + "They will be unbanned in " + (time/1000) + " seconds.");
	}
	
	public static String getTempBanReason(String name) {
		String reason = "";
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_tempbans WHERE player='" + name + "'");
			if (rs.next()) {
				reason = rs.getString("reason");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return reason;
	}
	
	public static long getTempBanTime(String name) {
		long time = 0;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_tempbans WHERE player='" + name + "'");
			if (rs.next()) {
				time = rs.getLong("time");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return time;
	}
	
	public static boolean isTempBanned(String name) {
		boolean tempbanned = false;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_tempbans WHERE player='" + name + "'");
			if (rs.next()) {
				tempbanned = true;
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return tempbanned;
	}
	
	public static void unbanPlayer(String name) {
		if (!isBanned(name)) return;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			PreparedStatement s = null;
			if (getBanType(name).equals(BanType.TEMP)) {
				s = Main.sql.getConnection().prepareStatement("DELETE FROM player_tempbans WHERE player='" + name + "'");
			} else if (getBanType(name).equals(BanType.PERM)) {
				s = Main.sql.getConnection().prepareStatement("DELETE FROM player_bans WHERE player='" + name + "'");
			} else if (getBanType(name).equals(BanType.IP)) {
				s = Main.sql.getConnection().prepareStatement("DELETE FROM player_ipbans WHERE player='" + name + "'");
			}
			assert s != null;
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static void mutePlayer(String name, long time) {
		int mutes = getMutes(name);
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			PreparedStatement s;
			if (mutes == 0) {
				s = Main.sql.getConnection().prepareStatement("INSERT INTO player_mutes (player, mutes, time) VALUES ('" + name + "', '1', '" + time + "')");
			} else {
				s = Main.sql.getConnection().prepareStatement("UPDATE player_mutes SET mutes='" + (mutes + 1) + "' WHERE player='" + name + "'");
			}
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		muted.put(name, time);
	}
	
	public static void unmutePlayer(String name) {
		muted.remove(name);
	}
	
	public static boolean isMuted(String name) {
		return muted.containsKey(name);
	}
	
	public static int getMutes(String name) {
		int mutes = 0;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_mutes WHERE player='" + name + "'");
			if (rs.next()) {
				mutes = rs.getInt("mutes");
			}
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return mutes;
	}
	
	public static long getMuteTime(String name) {
		long times = 0;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM player_mutes WHERE player='" + name + "'");
			if (rs.next()) {
				times = rs.getLong("time");
			}
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return times;
	}
	
	public static void setIPBanned(String name, String reason, String ip) {
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		if (reason == null) {
			reason = "The Ban Hammer has spoken!";
		}
		if (name == null) {
			if (IPSaver.getNameFromIp(ip) != null) {
				name = IPSaver.getNameFromIp(ip);
			}
		}
		try {
			PreparedStatement s = Main.sql.getConnection().prepareStatement("INSERT INTO player_ipbans (player, reason, ip) VALUES ('" + name + "', '" 
					+ reason + "', '" + ip + "')");
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(name);
		if (tp != null && tp.isConnected()) {
			tp.disconnect(new TextComponent(ChatColor.RED + "You have been banned for: \n" + ChatColor.translateAlternateColorCodes('&', reason)));
		}
		Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been banned for: ");
		Chat.msgAllOps(ChatColor.translateAlternateColorCodes('&', reason));
	}
	
	public static String getIPBanReason(String name, boolean ip) {
		String reason = null;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs;
			if (ip) {
				rs = s.executeQuery("SELECT * FROM player_ipbans WHERE ip='" + name + "'");
			} else {
				rs = s.executeQuery("SELECT * FROM player_ipbans WHERE player='" + name + "'");
			}
			if (rs.next()) {
				reason = rs.getString("reason");
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return reason;
	}
	
	public static void unbanIP(String name, boolean ip) {
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			PreparedStatement s;
			if (ip) {
				s = Main.sql.getConnection().prepareStatement("DELETE FROM player_ipbans WHERE ip='" + name + "'");
			} else {
				s = Main.sql.getConnection().prepareStatement("DELETE FROM player_ipbans WHERE player='" + name + "'");
			}
			s.executeUpdate();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
	}
	
	public static boolean isIPBanned(String name, boolean ip) {
		boolean banned = false;
		if (!Main.sql.hasConnection()) {
			Main.sql.openConnection();
		}
		try {
			Statement s = Main.sql.getConnection().createStatement();
			ResultSet rs;
			if (ip) {
				rs = s.executeQuery("SELECT * FROM player_ipbans WHERE ip='" + name + "'");
			} else {
				rs = s.executeQuery("SELECT * FROM player_ipbans WHERE player='" + name + "'");
			}
			if (rs.next()) {
				banned = true;
			}
			rs.close();
			s.close();
		} catch (SQLException e) {
			Main.p.getLogger().info("Trilar Cor > Error:" + e);
		}
		return banned;
	}
}
