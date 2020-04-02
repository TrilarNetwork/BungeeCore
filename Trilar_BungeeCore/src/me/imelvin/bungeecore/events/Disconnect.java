package me.imelvin.bungeecore.events;

import me.imelvin.bungeecore.handlers.IPSaver;
//import me.imelvin.bungeecore.utils.Perm;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.net.InetAddress;

public class Disconnect implements Listener {

	@EventHandler
	public void onDisconnect(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		InetAddress ip = p.getPendingConnection().getVirtualHost().getAddress();
		if (ip != null) {
			IPSaver.saveIP(p.getName(), ip.toString());
		}
		//Perm.logoutPlayer(p.getName());
	}
}
