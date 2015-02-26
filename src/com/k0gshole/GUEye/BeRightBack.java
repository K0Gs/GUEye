package com.k0gshole.GUEye;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.User;

public class BeRightBack implements CommandExecutor{

	//public GEye plugin;
	
	public BeRightBack(){
	}
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String arg3[]){
		
		if (!(arg0 instanceof Player)) {
			return false;
		}
		Player player = (Player)arg0;
		if (arg3.length == 0){
			OfflinePlayer oPlayer = player;
			//player.sendMessage(player.getName());
			User user = GUEye.essentials.getOfflineUser(player.getName());
			//player.sendMessage(user.toString());
			user._setAfk(true);
			GUEye.instance.getServer().broadcastMessage("[ALERT]: "+player.getName()+" is now AFK");
			GUEye.instance.getServer().broadcastMessage("["+player.getName()+"]: BRB.... gotta P");
			return true;
		}
		if(arg3.length != 0){
			String newStr = "";
			for(int a = 0;a < arg3.length;a++){
				newStr = newStr + " " + arg3[a];
			}
			
			OfflinePlayer oPlayer = player;
			//player.sendMessage(player.getName());
			User user = GUEye.essentials.getOfflineUser(player.getName());
			//player.sendMessage(user.toString());
			user._setAfk(true);
			GUEye.instance.getServer().broadcastMessage("[ALERT]: "+player.getName()+" is now AFK");
			GUEye.instance.getServer().broadcastMessage("["+player.getName()+"]: "+newStr);
			return true;
		

		}
			
		return false;
		
	}

}
/*
 * Version 1.1 
 * By K0Gs
 */
