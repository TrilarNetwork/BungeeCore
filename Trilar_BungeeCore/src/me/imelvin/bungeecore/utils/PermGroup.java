package me.imelvin.bungeecore.utils;

import static net.md_5.bungee.api.ChatColor.AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_AQUA;
import static net.md_5.bungee.api.ChatColor.DARK_GREEN;
import static net.md_5.bungee.api.ChatColor.DARK_RED;
import static net.md_5.bungee.api.ChatColor.GOLD;
import static net.md_5.bungee.api.ChatColor.GRAY;
import static net.md_5.bungee.api.ChatColor.GREEN;
import static net.md_5.bungee.api.ChatColor.RED;
import static net.md_5.bungee.api.ChatColor.YELLOW;
import static net.md_5.bungee.api.ChatColor.DARK_GRAY;
import static net.md_5.bungee.api.ChatColor.WHITE;

@Deprecated
public enum PermGroup {

	LEAD_DEVELOPER(550, GRAY + "(" + DARK_AQUA + "Lead" + GRAY + "-" + DARK_AQUA + "Developer" + GRAY + ") " + AQUA, "Lead-Developer"),
	DEVELOPER(525, GRAY + "(" + YELLOW + "Developer" + GRAY + ") " + GOLD, "Developer"),
	OWNER(500, GRAY + "(" + DARK_RED + "Owner" + GRAY + ") " + RED, "Owner"),
	SERVER_MANAGER(450, GRAY + "(" + RED + "Server" + GRAY + "-" + RED + "Manager" + GRAY + ") " + RED, "Server-Manager"),
	ADMIN(400, GRAY + "(" + DARK_RED + "Admin" + GRAY + ") " + RED, "Admin"),
	MODERATOR(300, GRAY + "(" + GOLD + "Moderator" + GRAY + ") " + YELLOW, "Moderator"),
	HELPER(200, GRAY + "(" + DARK_GREEN + "Helper" + GRAY + ") " + GREEN, "Helper"),
	HEAD_BUILDER(199, GRAY + "(" + GREEN + "Head-Builder" + GRAY + ") " + GREEN, "Head-Builder"),
	BUILDER(100, GRAY + "(" + DARK_GREEN + "Builder" + GRAY + ") " + GREEN, "Builder"),
	YOUTUBE(99, GRAY + "(" + RED + "You" + WHITE + "Tube" + GRAY + ") " + RED, "YouTube"),
	VIP4(90, GRAY + "(" + AQUA + "Vip" + GRAY + ") " + AQUA, "Vip"),
	VIP3(80, GRAY + "(" + AQUA + "Vip" + GRAY + ") " + AQUA, "Vip"),
	VIP2(60, GRAY + "(" + AQUA + "Vip" + GRAY + ") " + AQUA, "Vip"),
	VIP1(40, GRAY + "(" + AQUA + "Vip" + GRAY + ") " + AQUA, "Vip"),
	VIP(20, GRAY + "(" + AQUA + "Vip" + GRAY + ") " + AQUA, "Vip"),
	MEMBER(1, GRAY + "(" + DARK_GRAY + "Member" + GRAY + ") " + GREEN, "Member");
	
	private int group;
	private String prefix;
	private String name;

    PermGroup(int i, String p, String name) {
    	group = i;
    	prefix = p;
    	this.name = name;
    }
    
    public int getId(){
    	return group;
    }
    
    public String getPrefix(){
    	return prefix;
    }
    
    public String getName() {
    	return name;
    }
    
    public static PermGroup get(int _i) { 
        for(PermGroup s : values()) {
            if(s.group == _i){
            	return s;
            }
        }
        return null;
    }
    
    public static PermGroup get(String _s) { 
        for(PermGroup s : values()) {
            if(s.toString().replace("_", "").equalsIgnoreCase(_s.replace("_", "").replace("+", "p"))){
            	return s;
            }
        }
        return null;
    }
    
    public static int getTotal() {
    	int tot = 0;
    	for (int i = 0; i < values().length; i++) {
    		tot++;
    	}
    	return tot;
    }
}
