package me.imelvin.bungeecore.commands.reports;

import me.imelvin.bungeecore.handlers.Numbers;
import me.imelvin.bungeecore.handlers.ReportHandler;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CheckReport extends Command {

	public CheckReport() {
		super("checkreport");
	}
	public String b = ChatColor.GOLD + "[" + ChatColor.YELLOW;
	public String e = ChatColor.GOLD + "] ";
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
			if (args.length != 1) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /checkreport <id>"));
			} else {
				if (Numbers.isNumber(args[0])) {
					int key = Numbers.getInt(args[0]);
					if (ReportHandler.getOwner(key) != null) {
						sender.sendMessage(new TextComponent(ChatColor.GOLD + "--=[ " + ChatColor.YELLOW + "Report #" + key + ChatColor.GOLD + " ]=--"));
						sender.sendMessage(new TextComponent(ChatColor.GOLD + "Owner: " + ChatColor.YELLOW + ReportHandler.getOwner(key)));
						sender.sendMessage(new TextComponent(ChatColor.GOLD + "Status: " + ChatColor.translateAlternateColorCodes('&', ReportHandler.getStatus(key))));
						sender.sendMessage(new TextComponent(ChatColor.GOLD + "Description: " + ChatColor.translateAlternateColorCodes('&', ReportHandler.getDesc(key))));
					} else {
						sender.sendMessage(new TextComponent(ChatColor.RED + "That report does not exist!"));
					}
				} else {
					sender.sendMessage(new TextComponent(ChatColor.RED + "You must use a number! (id)"));
				}
			}
	}
}
