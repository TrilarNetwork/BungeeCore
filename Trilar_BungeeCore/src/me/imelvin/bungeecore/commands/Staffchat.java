package me.imelvin.bungeecore.commands;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Staffchat extends Command {

	public Staffchat() {
		super("staffchat", "", "s", "sc", "staff", "schat", "staffc");
	}
	public static ArrayList<String> global = new ArrayList<>();
	public static ArrayList<String> helpers = new ArrayList<>();
	public static ArrayList<String> moderator = new ArrayList<>();
	public static ArrayList<String> admins = new ArrayList<>();
	public static ArrayList<String> owners = new ArrayList<>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (args.length == 0) {
				if (global.contains(sender.getName())) {
					global.remove(sender.getName());
					sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA + " Staff-Chat!"));
				} else if (helpers.contains(sender.getName())) {
					helpers.remove(sender.getName());
					sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA + " Staff-Chat!"));
				} else if (moderator.contains(sender.getName())) {
					moderator.remove(sender.getName());
					sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA + " Staff-Chat!"));
				} else if (admins.contains(sender.getName())) {
					admins.remove(sender.getName());
					sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA + " Staff-Chat!"));
				} else if (owners.contains(sender.getName())) {
					owners.remove(sender.getName());
					sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA + " Staff-Chat!"));
				} else {
					global.add(sender.getName());
					sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "enabled" + ChatColor.DARK_AQUA + " Staff-Chat!"));
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("helper") || args[0].equalsIgnoreCase("h")) {
					if (helpers.contains(sender.getName())) {
						helpers.remove(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA	+ " Helper Staff-Chat!"));
					} else {
						helpers.add(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "enabled" + ChatColor.DARK_AQUA + " Helper Staff-Chat!"));
					}
				} else if (args[0].equalsIgnoreCase("moderator") || args[0].equalsIgnoreCase("mods") || args[0].equalsIgnoreCase("m")) {
					if (moderator.contains(sender.getName())) {
						moderator.remove(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA	+ " Moderator Staff-Chat!"));
					} else {
						moderator.add(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "enabled" + ChatColor.DARK_AQUA + " Moderator Staff-Chat!"));
					}
				} else if (args[0].equalsIgnoreCase("admin") || args[0].equalsIgnoreCase("a")) {
					if (admins.contains(sender.getName())) {
						admins.remove(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA + " Admin Staff-Chat!"));
					} else {
						admins.add(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "enabled" + ChatColor.DARK_AQUA + " Admin Staff-Chat!"));
					}
				} else if (args[0].equalsIgnoreCase("owner") || args[0].equalsIgnoreCase("o") || args[0].equalsIgnoreCase("developer") 
						|| args[0].equalsIgnoreCase("dev") || args[0].equalsIgnoreCase("d")) {
					if (owners.contains(sender.getName())) {
						owners.remove(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "disabled" + ChatColor.DARK_AQUA 
								+ " Owner/Developer Staff-Chat!"));
					} else {
						owners.add(sender.getName());
						sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "You have " + ChatColor.AQUA +  "enabled" + ChatColor.DARK_AQUA 
								+ " Owner/Developer Staff-Chat!"));
					}
				} else {
					sender.sendMessage(new TextComponent(ChatColor.DARK_RED + "Invalid usage! Use: /staffchat [helper|moderator|admin|owner|dev]"));
				}
			}
	}
}
