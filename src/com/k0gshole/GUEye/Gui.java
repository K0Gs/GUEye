package com.k0gshole.GUEye;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;


import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;



public class Gui implements CommandExecutor{

	

	private GUEye plugin;
	private Inventory inventory;
	public Gui() {

	}











	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String arg3[]) {
		if (!(arg0 instanceof Player)) {
			return false;
		}

		Player player = (Player)arg0;
		if (arg3.length == 0){
			player.openInventory(GUEye.getInstance().displayGUI(player));
			return true;

		}
		if(arg3.length != 0){
			String newStr = "";
			for(int a = 0;a < arg3.length;a++){
				newStr = newStr + " " + arg3[a];
			}
			player .sendMessage("Player "+ arg0 + "Command " + arg1.toString() + " First String " + arg2.toString() +"Second String "+ newStr);
		
		if (arg3[0].equalsIgnoreCase("invsee")){
			if(arg3.length < 2){
				player.sendMessage("You must include the players name...");
				return false;
			}
			OfflinePlayer oPlayer = null;
			oPlayer = (OfflinePlayer) GUEye.essentials.getOfflineUser(arg3[1]).getBase();
			if (oPlayer == null){
				player.sendMessage("Player: " + arg3[1].toString() + " not found...");
				return false;
			}
			

		
			String[] pUUID = StringUtils.split(oPlayer.getUniqueId().toString(), "-");
			String pFinUUID = "";
			for(int z = 0; z < pUUID.length; z++){
				pFinUUID = pFinUUID + pUUID[z];
			}
			File oPlayeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
			YamlConfiguration oPlayerData = YamlConfiguration.loadConfiguration(oPlayeryml);
			ItemStack tInv = null;
			inventory = Bukkit.createInventory(null, 36, "\2479GuiInventory of "+oPlayer.getName());
			for(int c = 0;c < 36;c++){
			tInv = oPlayerData.getItemStack("Inventory."+c);
			inventory.setItem(c, tInv);
			}
			player.openInventory(inventory);
			return true;
		}
		}

		return false;
	}

}
/*
 * Version 1.1 
 * By K0Gs
 */