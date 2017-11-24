package me.imelvin.bungeecore.commands.warnings;

import java.sql.SQLException;
import java.sql.Statement;

import me.imelvin.bungeecore.ConfigManager;
import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.Chat;
import me.imelvin.bungeecore.handlers.WarnHandler;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Warn extends Command {

	public Warn() {
		super("warn", "w", "warning");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (Perm.hasPerm(p, PermGroup.HELPER)) {
			if (args.length < 2) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /warn <player> <reason>"));
			} else {
				String name = args[0];
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i]).append(" ");
					
				}
				String reason = ChatColor.translateAlternateColorCodes('&', sb.toString()).trim();
				WarnHandler.warnPlayer(name, reason);
				ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(name);
				if (tp.isConnected() && ConfigManager.getInstance().getConfig().getBoolean("settings.kickOnWarn")) {
					tp.disconnect(new TextComponent(ChatColor.DARK_AQUA + "You have been warned: \n" + reason));
					Chat.msgAllOps(Main.prefix + "Player " + ChatColor.GOLD + tp.getName() + ChatColor.YELLOW + " has been warned (and auto-kicked) by " 
							+ ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + " for: "); 
					Chat.msgAllOps(reason);
					reason = reason + ChatColor.RED + "(Warn)";
					try {
						if (!Main.sql.hasConnection()) {
							Main.sql.openConnection();
						}
						Statement s = Main.sql.getConnection().createStatement();
						if (BanHandler.getKicks(name) != 0) {
							s.executeUpdate("UPDATE player_kicks SET (kicks='" + (BanHandler.getKicks(name) + 1) + "' AND reason='" 
									+ reason + "') WHERE player='" + name + "'");
						} else {
							s.executeUpdate("INSERT INTO player_kicks (player, kicks, reason) VALUES ('" + name + "', '1','" + reason + "')");
						}
					} catch (SQLException e) {
						Main.p.getLogger().info("Yor Cor > Error:" + e);
					}	
				} else {
					Chat.msgAllOps(Main.prefix + "Player " + ChatColor.GOLD + tp.getName() + ChatColor.YELLOW + " has been warned by " 
							+ ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + " for: "); 
					Chat.msgAllOps(reason);
				}
			}
		} else {
			p.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to perform this command!"));
		}
	}
}
