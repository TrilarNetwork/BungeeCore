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

public enum PermGroup {

	LEAD_DEVELOPER(510, GRAY + "(" + DARK_AQUA + "Lead" + GRAY + "-" + DARK_AQUA + "Developer" + GRAY + ") " + AQUA, "Lead-Developer"),
	DEVELOPER(500, GRAY + "(" + YELLOW + "Developer" + GRAY + ") " + GOLD, "Developer"),
	OWNER(500, GRAY + "(" + DARK_RED + "Owner" + GRAY + ") ", "Owner"),
	STAFF_MANAGER(450, GRAY + "(" + RED + "Staff" + GRAY + "-" + RED + "Manager" + GRAY + ") " + RED, "Staff-Manager"),
	ADMIN(400, GRAY + "(" + DARK_AQUA + "Admin" + GRAY + ") ", "Admin"),
	MODERATOR(300, GRAY + "(" + GOLD + "Moderator" + GRAY + ") ", "Moderator"),
	HELPER(200, GRAY + "(" + DARK_GREEN + "Helper" + GRAY + ") ", "Helper"),
	BUILDER(100, GRAY + "(" + AQUA + "Builder" + GRAY + ") ", "Builder"),
	MEMBER(1, GRAY + "(" + GREEN + "Member" + GRAY + ") ", "Member");
	
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
