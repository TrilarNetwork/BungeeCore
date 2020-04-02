package me.imelvin.bungeecore.commands.warnings;

import me.imelvin.bungeecore.Main;
import me.imelvin.bungeecore.handlers.Chat;
import me.imelvin.bungeecore.handlers.WarnHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Delwarn extends Command {

	public Delwarn() {
		super("delwarn", "", "deletewarn", "deletewarning", "removewarn", "removewarning");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (args.length != 2) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /delwarn <player> <warninglabel>"));
			} else {
				String name = args[0];
				int label = 0;
				try {
					label = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "The warning label must be a number!"));
				}
				if (label == 0) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This person does not have a warn!"));
				}
				if (WarnHandler.exists(name, label)) {
					WarnHandler.deleteWarn(name, label);
					Chat.msgAllOps(Main.PREFIX + "Player " + ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + " has removed a warn from "
							+ ChatColor.GOLD + name + ChatColor.YELLOW + "!");
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "Warning with label " + label + " does not exists for player " + name + "!"));
				}
			}
	}
}
