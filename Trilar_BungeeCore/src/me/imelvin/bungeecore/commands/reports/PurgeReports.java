package me.imelvin.bungeecore.commands.reports;

import java.util.ArrayList;

import me.imelvin.bungeecore.handlers.ReportHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class PurgeReports extends Command {

	public PurgeReports() {
		super("purgereports");
	}
	ArrayList<String> confirm = new ArrayList<>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
			if (confirm.contains(sender.getName())) {
				sender.sendMessage(new TextComponent(ChatColor.GOLD + "Here goes..."));
				ReportHandler.purgeOldTickets();
				sender.sendMessage(new TextComponent(ChatColor.GOLD + "All done!"));
			} else {
				sender.sendMessage(new TextComponent(ChatColor.GOLD + "Are you sure you want to delete all CLOSED reports? Type /purgereports if so."));
			}
	}
}
