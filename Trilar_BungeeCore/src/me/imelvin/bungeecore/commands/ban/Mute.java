package me.imelvin.bungeecore.commands.ban;

import java.util.concurrent.TimeUnit;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.Chat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Mute extends Command {

	public Mute() {
		super("mute");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (args.length == 0) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /mute <player> <time>"));
			} else if (args.length == 1) {
				String name = args[0];
				if (BanHandler.isMuted(name)) {
					BanHandler.unmutePlayer(name);
					Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been unmuted by " + ChatColor.GOLD
							+ sender.getName() + ChatColor.YELLOW + "!");
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "That player is not muted!"));
				}
			} else {
				ProxiedPlayer tp = ProxyServer.getInstance().getPlayer(args[0]);
				if (tp.isConnected()) {
					String toParse = "";
					String type = "";
					if (args[1].endsWith("s")) {
						type = "s";
						toParse = args[1].substring(0, args[1].length() - 1);
					} else if (args[1].endsWith("m")) {
						type = "m";
						toParse = args[1].substring(0, args[1].length() - 1);
					} else if (args[1].endsWith("h")) {
						type = "h";
						toParse = args[1].substring(0, args[1].length() - 1);
					} else if (args[1].endsWith("d")) {
						type = "d";
						toParse = args[1].substring(0, args[1].length() - 1);
					} else {
						sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /mute <player> <time> [reason]"));
						sender.sendMessage(new TextComponent(ChatColor.RED + "Time info: d = day, h = hour, m = minute, s = second"));
					}
					int time = 0;
					try {
						time = Integer.parseInt(toParse);
					} catch (NumberFormatException e) {
						sender.sendMessage(new TextComponent(ChatColor.RED + "You must enter a number!"));
					}
					long t = 0;
					String ty = "";
					TimeUnit tu = null;
					switch (type) {
						case "s":
							t = time;
							ty = "second(s)";
							tu = TimeUnit.SECONDS;
							break;
						case "m":
							t = time * 60;
							ty = "minute(s)";
							tu = TimeUnit.MINUTES;
							break;
						case "h":
							t = time * 3600;
							ty = "hour(s)";
							tu = TimeUnit.HOURS;
							break;
						case "d":
							t = time * 86400;
							ty = "day(s)";
							tu = TimeUnit.DAYS;
							break;
					}
					t = t / 1000;
					long ist = System.currentTimeMillis() + t;
					BanHandler.mutePlayer(tp.getName(), time);
					Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + tp.getName() + ChatColor.YELLOW + " has been muted for " + ChatColor.GOLD + time
							+ ChatColor.YELLOW + " " + ty + " by " + ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + "!");
					sender.sendMessage(new TextComponent(Main.PREFIX + "You have muted " + ChatColor.GOLD + tp.getName() + ChatColor.YELLOW + " for " + ChatColor.GOLD
							+ time + ChatColor.YELLOW + " " + ty + "!"));
					tp.sendMessage(new TextComponent(Main.PREFIX + "You have been muted by " + ChatColor.GOLD + tp.getName() + ChatColor.YELLOW + " for "
							+ ChatColor.GOLD + time + ChatColor.YELLOW + " " + ty + "!"));
					ProxyServer.getInstance().getScheduler().schedule(Main.p, () -> {
						if (System.currentTimeMillis() == ist) {
							if (BanHandler.isMuted(tp.getName())) {
								BanHandler.unmutePlayer(tp.getName());
								Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + tp.getName() + ChatColor.YELLOW + " has been unmuted! (Time exceeded)");
							}
						}
					}, t, tu);
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "That player is not online!"));
				}
			}
	}
}
