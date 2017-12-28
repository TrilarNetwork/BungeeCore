package me.imelvin.bungeecore.commands.warnings;

import me.imelvin.bungeecore.handlers.WarnHandler;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Warnings extends Command {

	public Warnings() {
		super("warnings", "", "warns");
	}
	public String b = ChatColor.GOLD + "[" + ChatColor.YELLOW;
	public String e = ChatColor.GOLD + "] ";
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
			if (args.length != 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /delwarn <player> <warninglabel>"));
			} else {
				String name = args[0];
				int warns = WarnHandler.getWarns(name);
				if (warns == 0) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "This player has not been warned yet!"));
				} else {
					int i = 0;
					sender.sendMessage(new TextComponent(ChatColor.DARK_AQUA + "--=[ " + ChatColor.AQUA + "Warns" + ChatColor.DARK_AQUA + " ]=--"));
					while (i < warns) {
						i++;
						sender.sendMessage(new TextComponent(b + i + e + name + ChatColor.YELLOW + WarnHandler.getWarnReason(name, i)));
					}
				}
			}
	}
}
