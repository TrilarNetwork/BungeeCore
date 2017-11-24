 package me.imelvin.bungeecore.events;

import me.imelvin.bungeecore.ConfigManager;
import me.imelvin.bungeecore.commands.Maintenance;
import me.imelvin.bungeecore.handlers.BanHandler;
import me.imelvin.bungeecore.handlers.BanHandler.BanType;
import me.imelvin.bungeecore.handlers.IPSaver;
import me.imelvin.bungeecore.utils.Perm;
import me.imelvin.bungeecore.utils.PermGroup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLogin implements Listener {

	@EventHandler
	public void onPost(PostLoginEvent e) {
		ProxiedPlayer p = e.getPlayer();
		IPSaver.saveIP(p.getName(), p.getAddress().toString());
		if (BanHandler.isBanned(p.getName())) {
			String reason = "";
			long tbantime = 0;
			if (BanHandler.getBanType(p.getName()).equals(BanType.PERM)) {
				reason = BanHandler.getBanReason(p.getName());
			} else if (BanHandler.getBanType(p.getName()).equals(BanType.TEMP)) {
				reason = BanHandler.getTempBanReason(p.getName());
				tbantime = BanHandler.getTempBanTime(p.getName());
			} else if (BanHandler.getBanType(p.getName()).equals(BanType.IP)) {
				reason = BanHandler.getIPBanReason(p.getName(), false);
			} else {
				reason = ChatColor.RED + "Something went wrong, please contact the Staff Team.";
			}
			if ((tbantime != 0) && (System.currentTimeMillis() <= tbantime)) {
				BanHandler.unbanPlayer(p.getName());
			}
			String msg = ConfigManager.getInstance().getConfig().getString("settings.banMessage").trim();
			msg = msg.replace("%reason%", reason);
			msg = ChatColor.translateAlternateColorCodes('&', msg).trim();
			p.disconnect(new TextComponent(msg));
		} else {
			if (Perm.getGroup(p) == null) {
				Perm.updateGroup(p, PermGroup.MEMBER);
			} 
			Perm.loginPlayer(p.getName());
		}
		if (Maintenance.m) {
			if (!Perm.hasPerm(p, PermGroup.BUILDER)) {
				p.disconnect(new TextComponent(ChatColor.DARK_AQUA + "Trilar -> Onderhoud\nJe bent gekicked omdat de server in onderhoud is. \nProbeer het later opnieuw."));
			}
		}
	}
}
