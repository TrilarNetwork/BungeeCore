package me.imelvin.bungeecore.commands;

import java.util.HashMap;

import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Reply extends Command {

	public Reply() {
		super("reply", "", "r");
	}
	public static HashMap<String, String> reply = new HashMap<>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer pl = (ProxiedPlayer) sender;
			if (args.length < 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /reply <text>"));
			} else {
				if (reply.containsValue(sender.getName())) {
					String name = "";
					for (String n : reply.keySet()) {
						if (reply.get(n).equals(sender.getName())) {
							name = n;
						}
					}
					ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(name);
					if (tp != null) {
						if (tp.isConnected()) {
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String msg = "";
							if (sender.hasPermission("trilar.chatcolors.use")) {
								msg = ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
							} else {
								msg = sb.toString().trim();
							}
							sender.sendMessage(new TextComponent(Msg.sendSenderPrefix(tp.getName()) + msg));
							tp.sendMessage(new TextComponent(Msg.sendReceiverPrefix(sender.getName()) + msg));
							for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
								if (Msg.socialspy.contains(p.getName())) {
									p.sendMessage(new TextComponent(Msg.sendPrefix(sender.getName(), tp.getName()) + msg));
								}
							}
							reply.remove(tp.getName());
							reply.put(sender.getName(), tp.getName());
							ProxyServer.getInstance().getLogger().info("Messages >> " + sender.getName() + " > " + tp.getName() + " " + msg);
						} else {
							sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Player " + ChatColor.GRAY + tp.getName() + ChatColor.DARK_GRAY 
									+ " has not been found!"));
							reply.remove(tp.getName());
						}
					} else {
						sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Player " + ChatColor.GRAY + args[0] + ChatColor.DARK_GRAY 
								+ " has not been found!"));
					}
				} else if (reply.containsKey(sender.getName())) {
					ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(reply.get(sender.getName()));
					if (tp != null) {
						if (tp.isConnected()) {
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < args.length; i++) {
								sb.append(args[i]).append(" ");
							}
							String msg = "";
							if (sender.hasPermission("trilar.chatcolors.use")) {
								msg = ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
							} else {
								msg = sb.toString().trim();
							}
							sender.sendMessage(new TextComponent(Msg.sendSenderPrefix(tp.getName()) + msg));
							tp.sendMessage(new TextComponent(Msg.sendReceiverPrefix(sender.getName()) + msg));
							for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
								if (Msg.socialspy.contains(p.getName())) {
									p.sendMessage(new TextComponent(Msg.sendPrefix(sender.getName(), tp.getName()) + msg));
								}
							}
							reply.remove(sender.getName());
							reply.put(sender.getName(), tp.getName());
							ProxyServer.getInstance().getLogger().info("Messages >> " + sender.getName() + " > " + tp.getName() + " " + msg);
						} else {
							sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Player " + ChatColor.GRAY + tp.getName() + ChatColor.DARK_GRAY 
									+ " has not been found!"));
							reply.remove(sender.getName());
						}
					} else {
						sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Player " + ChatColor.GRAY + args[0] + ChatColor.DARK_GRAY 
								+ " has not been found!"));
					}
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "You have no one to reply to!"));
				}
			}
	}
}
