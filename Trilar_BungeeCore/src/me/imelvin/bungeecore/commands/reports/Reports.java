package me.imelvin.bungeecore.commands.reports;

import me.imelvin.bungeecore.handlers.ReportHandler;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Reports extends Command {

	public Reports() {
		super("reports");
	}	
	public String b = ChatColor.GOLD + "[" + ChatColor.YELLOW;
	public String e = ChatColor.GOLD + "] ";
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (Perm.hasPerm(p, PermGroup.HELPER)) {
			if (args.length > 0) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid usage! Use: /reports"));
			} else {
				for (int counter : ReportHandler.getOpenReports()) {
					String desc = "";
					if (ReportHandler.getDesc(counter).length() >= 43) {
						desc = ReportHandler.getDesc(counter).substring(0, 40);
					}
					desc = desc + "...";
					sender.sendMessage(new TextComponent(b + counter + e + ReportHandler.getOwner(counter) + ": " + ChatColor.YELLOW + desc));
				}
			}
		} else {
			p.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to perform this command!"));
		}
	}
}
