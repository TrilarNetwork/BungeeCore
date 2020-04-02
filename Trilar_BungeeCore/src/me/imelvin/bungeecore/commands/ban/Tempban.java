package me.imelvin.bungeecore.commands.ban;

import java.util.concurrent.TimeUnit;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.Chat;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Tempban extends Command {
	
	public Tempban() {
		super("tempban");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (args.length < 2) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /tempban <player> <time> [reason]"));
			} else if (args.length == 2) {
				String name = args[0];
				if (BanHandler.isBanned(name)) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This player has already been banned!"));
				} else {
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
					}
					int time = 0;
					try {
						time = Integer.parseInt(toParse);
					} catch (NumberFormatException e) {
						sender.sendMessage(new TextComponent(ChatColor.RED + "You must enter a number!"));
					}
					long t = 0;
					TimeUnit tu = null;
					switch (type) {
						case "s":
							t = time;
							tu = TimeUnit.SECONDS;
							break;
						case "m":
							t = time * 60;
							tu = TimeUnit.MINUTES;
							break;
						case "h":
							t = (time * 60) * 60;
							tu = TimeUnit.HOURS;
							break;
						case "d":
							t = ((time * 60) * 60) * 24;
							tu = TimeUnit.DAYS;
							break;
					}
					t = t * 1000;
					long ist = System.currentTimeMillis() + t;
					BanHandler.setTempBanned(name, null, ist);
					ProxyServer.getInstance().getScheduler().schedule(Main.p, () -> {
						if (System.currentTimeMillis() == ist) {
							if (BanHandler.isBanned(name)) {
								BanHandler.unbanPlayer(name);
								Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been unbanned! (Time exceeded)");
							}
						}
					}, t, tu);
				}
			} else {
				String name = args[0];
				if (BanHandler.isBanned(name)) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This player has already been banned!"));
				} else {
					StringBuilder reason = new StringBuilder();
					for (int i = 2; i < args.length; i++) {
						reason.append(args[i]).append(" ");
					}
					reason = new StringBuilder(reason.toString().trim());
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
					}
					int time = 0;
					try {
						time = Integer.parseInt(toParse);
					} catch (NumberFormatException e) {
						sender.sendMessage(new TextComponent(ChatColor.RED + "You must enter a number!"));
					}
					long t = 0;
					TimeUnit tu = null;
					switch (type) {
						case "s":
							t = time;
							tu = TimeUnit.SECONDS;
							break;
						case "m":
							t = time * 60;
							tu = TimeUnit.MINUTES;
							break;
						case "h":
							t = (time * 60) * 60;
							tu = TimeUnit.HOURS;
							break;
						case "d":
							t = ((time * 60) * 60) * 24;
							tu = TimeUnit.DAYS;
							break;
					}
					t = t * 1000;
					long ist = System.currentTimeMillis() + t;
					BanHandler.setTempBanned(name, reason.toString(), ist);
					ProxyServer.getInstance().getScheduler().schedule(Main.p, () -> {
						if (System.currentTimeMillis() == ist) {
							if (BanHandler.isBanned(name)) {
								BanHandler.unbanPlayer(name);
								Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + name + ChatColor.YELLOW + " has been unbanned! (Time exceeded)");
							}
						}
					}, t, tu);
				}
			}
	}
}
