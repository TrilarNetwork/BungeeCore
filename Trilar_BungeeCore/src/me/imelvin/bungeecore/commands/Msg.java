package me.imelvin.bungeecore.commands;

import java.util.ArrayList;

import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Msg extends Command {

	public Msg() {
		super("msg", "", new String[] {"tell", "m", "whisper", "w"});
	}
	public static ArrayList<String> socialspy = new ArrayList<String>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer pl = (ProxiedPlayer) sender;
		if (Perm.hasPerm(pl, PermGroup.MEMBER)) {
			if (args.length < 2) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /msg <player> <text>"));
			} else {
				ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(args[0]);
				if (tp != null) {
					if (tp.isConnected()) {
						StringBuilder sb = new StringBuilder();
						for (int i = 1; i < args.length; i++) {
							sb.append(args[i]).append(" ");
						}
						String msg = "";
						if (sender.hasPermission("trilar.chatcolors.use")) {
							msg = ChatColor.translateAlternateColorCodes('&', sb.toString().trim());
						} else {
							msg = sb.toString().trim();
						}
						sender.sendMessage(new TextComponent(sendSenderPrefix(tp.getName()) + msg));
						tp.sendMessage(new TextComponent(sendReceiverPrefix(sender.getName()) + msg));
						for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
							if (socialspy.contains(p.getName())) {
								p.sendMessage(new TextComponent(sendPrefix(sender.getName(), tp.getName()) + msg));
							}
						}
						Reply.reply.put(sender.getName(), tp.getName());
						ProxyServer.getInstance().getLogger().info("Messages >> " + sender.getName() + " > " + tp.getName() + " " + msg);
					} else {
						sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Player " + ChatColor.GRAY + tp.getName() + ChatColor.DARK_GRAY 
								+ " has not been found!"));
					}
				} else {
					sender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + "Player " + ChatColor.GRAY + args[0] + ChatColor.DARK_GRAY 
							+ " has not been found!"));
				}
			}
		} else {
			pl.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to perform this command!"));
		}
	}
	
	public static String sendSenderPrefix(String targetName) {
		String s = ChatColor.GOLD + "You" + ChatColor.YELLOW + " > " + ChatColor.GOLD + targetName + ChatColor.RESET + ": ";
		return s;
	}
	
	public static String sendReceiverPrefix(String targetName) {
		String s = ChatColor.GOLD + targetName + ChatColor.YELLOW + " > " + ChatColor.GOLD + "You" + ChatColor.RESET + ": ";
		return s;
	}
	
	public static String sendPrefix(String sender, String target) {
		String s = ChatColor.GOLD + sender + ChatColor.YELLOW + " > " + ChatColor.GOLD + target + ChatColor.RESET + ": ";
		return s;
	}
}
