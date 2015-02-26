package com.k0gshole.GUEye;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GListeners implements Listener{

	int stackSize = 0;
	String isFalse = "false";
	String isTrue = "true";
	public String missionListItem;
	//public File pFile = null;
	File playeryml = null;
	File missionyml = new File("plugins/GUEye/mission.yml");
	File rankyml = new File("plugins/GUEye/ranks.yml");
	File jobyml = new File("plugins/GUEye/job.yml");
	public YamlConfiguration playerData = null;
	public YamlConfiguration rank = YamlConfiguration.loadConfiguration(rankyml);
	public YamlConfiguration missions = null;
	public YamlConfiguration jobs = null;
	String activity = null;


	private GUEye plugin;
	public Player player;
	public OfflinePlayer oPlayer;

	List matList;

	public GListeners(GUEye plugin){
		this.plugin = plugin;
		player = null;

	}


	@EventHandler
	public void onPlayerCollect(PlayerPickupItemEvent event){
		activity = "collect";

		player = event.getPlayer();
		oPlayer = player;
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}


		String theFile = pFinUUID + ".yml";
		String confPath = "plugins/GUEye/PlayerData/";



		File confFile = new File(confPath + theFile);

		playeryml = confFile;
		jobs = YamlConfiguration.loadConfiguration(jobyml);
		missions = YamlConfiguration.loadConfiguration(missionyml);
		playerData = YamlConfiguration.loadConfiguration(playeryml);
		String inMiss = playerData.getString("inMission");
		String curMiss = playerData.getString("currentMission");
		int coll = playerData.getInt("collect");
		String type = missions.getString(curMiss+".Detail.type");
		String material = missions.getString(curMiss+".Detail.material");
		int amount = missions.getInt(curMiss+".Detail.amount");
		String inJob = playerData.getString("inJob");
		String curJob = playerData.getString("currentJob");
		int jobColl = playerData.getInt("collect");
		int earned = playerData.getInt("earned");
		String jobType = jobs.getString(curJob+".Detail.type");
		String jobMaterial = jobs.getString(curJob+".Detail.material");
		Material matEvent = null;
		int jobAmount = jobs.getInt(curJob+".Detail.amount");
		int reward = jobs.getInt(curJob+".Detail.reward");

			
			stackSize = event.getItem().getItemStack().getAmount();
			ItemStack give = event.getItem().getItemStack();
			ItemMeta gMeta = event.getItem().getItemStack().getItemMeta();
			matEvent = give.getType();
			//player.sendMessage(event.getItem().getItemStack().getType().name());
			//player.sendMessage(event.getItem().getCustomName());
			
			//player.sendMessage(event.getItem().getName());
			
			//player.sendMessage(event.getItem().getItemStack().getItemMeta().toString());
			//player.sendMessage(event.getItem().getItemStack().getItemMeta().getDisplayName());
			//player.sendMessage(event.getItem().getItemStack().getData().getItemType().name());
			//player.sendMessage(event.getItem().getItemStack().getType().name());
			//if (give.getType().name().equals("STONE")){
			//	String[] stoneString = StringUtils.split(event.getItem().getName(), ".");
			//	//player.sendMessage(stoneString[3].toUpperCase());
			//	gMeta.setDisplayName(stoneString[3].toUpperCase());
			//}
			event.setCancelled(true);
			event.getItem().remove();
			BigDecimal curWorth = null;
			curWorth = plugin.essentials.getWorth().getPrice(give);
			List lores = new ArrayList();
			if(curWorth != null){
			lores.add("$");
			lores.add(Double.toString(curWorth.doubleValue()));
			}
			gMeta.setLore(lores);
			
			give.setAmount(stackSize);
			give.setItemMeta(gMeta);
			player.getInventory().addItem(new ItemStack[]{
					give});

			lores.clear();
			give = null;
			stackSize = 0;


	}


	@EventHandler
	public void onPLayerKill(EntityDeathEvent event){
		activity = "kill";
		if(event.getEntity().getKiller() != null){
			Player player = event.getEntity().getKiller();
			oPlayer = player;

			String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
			String pFinUUID = "";
			for(int z = 0; z < pUUID.length; z++){
				pFinUUID = pFinUUID + pUUID[z];
			}

			//player.sendMessage("Entity Event");


			String theFile = pFinUUID + ".yml";
			String confPath = "plugins/GUEye/PlayerData/";



			File confFile = new File(confPath + theFile);

			playeryml = confFile;

			jobs = YamlConfiguration.loadConfiguration(jobyml);
			missions = YamlConfiguration.loadConfiguration(missionyml);
			playerData = YamlConfiguration.loadConfiguration(playeryml);
			String inMiss = playerData.getString("inMission");
			String curMiss = playerData.getString("currentMission");
			int kills = playerData.getInt("kill");
			String type = missions.getString(curMiss+".Detail.type");
			String entity = missions.getString(curMiss+".Detail.entity");
			int amount = missions.getInt(curMiss+".Detail.amount");
			int earned = playerData.getInt("earned");
			jobs = YamlConfiguration.loadConfiguration(jobyml);
			playerData = YamlConfiguration.loadConfiguration(playeryml);
			String inJob = playerData.getString("inJob");
			String curJob = playerData.getString("currentJob");
			int jobKills = playerData.getInt("kill");
			String jobType = jobs.getString(curJob+".Detail.type");
			String jobEntity = jobs.getString(curJob+".Detail.entity");
			int jobAmount = jobs.getInt(curJob+".Detail.amount");

			int reward = jobs.getInt(curJob+".Detail.reward");

			//player.sendMessage(playerData.getString("inMission") + " " + playerData.getString("currentMission") + " " + playerData.getString("kill"));
			//player.sendMessage(inMiss + " " +  curMiss + " " +  kills + " " + entity + " " + type); 
			//player.sendMessage(inJob + " " +  curJob + " " +  jobKills + " " + jobEntity + " " + jobType); 

			if(inMiss == isTrue || inMiss.equals(isTrue)){
				//player.sendMessage("In a mission");

				//player.sendMessage(event.getItem().getMetadata("Lore").toString());
				if (type.equalsIgnoreCase("kill") || type == "kill"){
					//String kill = "kill";

					//player.sendMessage(event.getEntity().getType().name());
					if(event.getEntity().getType().name().equalsIgnoreCase(entity) || event.getEntity().getType().name() == entity){

						player.sendMessage(event.getEntity().getType().name()+" killed");
						playerData.set("kill", kills+1);
						try {
							playerData.save(playeryml);
							//player.sendMessage("save");
						} catch (IOException e) {
							e.printStackTrace();
							//player.sendMessage(" failed");
						}
						//player.sendMessage(" successful");
					}
				}

				if (kills > amount){

					rank = YamlConfiguration.loadConfiguration(rankyml);

					if((int)playerData.getInt("rank") < rank.getList("ranks").size()){
						playerData.set("rank", playerData.getInt("rank")+1);
					}
					player.sendMessage("___________________________");
					player.sendMessage("___Mission______Complete___");
					player.sendMessage("    Rank:" + rank.getString("rank."+playerData.getString("rank")) );
					player.sendMessage("___________________________");
					playerData.set("kill", 0);
					playerData.set("inMission", isFalse);
					playerData.set("currentMission", "none");
					try {
						playerData.save(playeryml);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}





			}

			//player.sendMessage(playerData.getString("inJob") + " " + playerData.getString("currentJob") + " " + playerData.getString("kill"));
			//player.sendMessage(inMiss + " " +  curMiss + " " +  kills + " " + entity + " " + type); 
			if(inJob == isTrue || inJob.equals(isTrue)){
				//player.sendMessage("In a job");

				//player.sendMessage(event.getItem().getMetadata("Lore").toString());
				if (jobType.equalsIgnoreCase("kill") || jobType == "kill"){
					//String kill = "kill";

					//player.sendMessage(event.getEntity().getType().name());
					if(event.getEntity().getType().name().equalsIgnoreCase(jobEntity) || event.getEntity().getType().name() == jobEntity){

						player.sendMessage(event.getEntity().getType().name()+" killed");
						playerData.set("kill", jobKills+1);
						try {
							playerData.save(playeryml);
							//player.sendMessage("save");
						} catch (IOException e) {
							e.printStackTrace();
							//player.sendMessage(" failed");
						}
						//player.sendMessage(" successful");
					}
				}

				if (jobKills > jobAmount){


					plugin.economy.depositPlayer(oPlayer, reward);
					player.sendMessage("___________________________");
					player.sendMessage("_____Job________Complete___");
					player.sendMessage("     Reward: $" + reward);
					player.sendMessage("___________________________");
					playerData.set("kill", 0);
					playerData.set("inJob", isFalse);
					playerData.set("currentJob", "none");
					playerData.set("earned", reward + earned);
					try {
						playerData.save(playeryml);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}


	}

	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent event){
		activity = "ate";

		Player player = event.getPlayer();
		oPlayer = player;
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}

		//player.sendMessage("Entity Event");


		String theFile = pFinUUID.toString() + ".yml";
		String confPath = "plugins/GUEye/PlayerData/";



		File confFile = new File(confPath + theFile);

		playeryml = confFile;
		jobs = YamlConfiguration.loadConfiguration(jobyml);
		missions = YamlConfiguration.loadConfiguration(missionyml);
		playerData = YamlConfiguration.loadConfiguration(playeryml);
		String inMiss = playerData.getString("inMission");
		String curMiss = playerData.getString("currentMission");
		int ate = playerData.getInt("ate");
		String type = missions.getString(curMiss+".Detail.type");
		String material = missions.getString(curMiss+".Detail.material");
		int amount = missions.getInt(curMiss+".Detail.amount");
		String inJob = playerData.getString("inJob");
		String curJob = playerData.getString("currentJob");
		int jobAte = playerData.getInt("ate");
		String jobType = jobs.getString(curJob+".Detail.type");
		String jobMaterial = jobs.getString(curJob+".Detail.material");
		int jobAmount = jobs.getInt(curJob+".Detail.amount");
		int earned = playerData.getInt("earned");
		int reward = jobs.getInt(curJob+".Detail.reward");
		if(inMiss == isTrue || inMiss.equals(isTrue)){
			//player.sendMessage("In a mission");

			player.sendMessage(missions.getString(curMiss+".Detail.type"));
			if (type.equalsIgnoreCase("ate") || type == "ate"){


				//player.sendMessage(missions.getString((String)playerData.getString("currentMission")+".Details.material") + " " + missions.getString(playerData.getString("currentMission")+".Details.material"));
				if(event.getItem().getType().name().equalsIgnoreCase(material) || event.getItem().getType().name() == material){

					player.sendMessage(event.getItem().getType().name()+" Eaten");
					playerData.set("ate", ate+1);
					try {
						playerData.save(playeryml);
						//player.sendMessage("save");
					} catch (IOException e) {
						e.printStackTrace();
						//player.sendMessage(" failed");
					}
					//player.sendMessage(" successful");
				}
			}

			if (ate > amount){

				rank = YamlConfiguration.loadConfiguration(rankyml);

				if((int)playerData.getInt("rank") < rank.getList("ranks").size()){
					playerData.set("rank", playerData.getInt("rank")+1);
				}
				player.sendMessage("___________________________");
				player.sendMessage("___Mission______Complete___");
				player.sendMessage("    Rank:" + rank.getString("rank."+playerData.getString("rank")) );
				player.sendMessage("___________________________");
				playerData.set("ate", 0);
				playerData.set("inMission", isFalse);
				playerData.set("currentMission", "none");
				try {
					playerData.save(playeryml);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		if(inJob == isTrue || inJob.equals(isTrue)){
			//player.sendMessage("In a Job");

			//player.sendMessage(jobs.getString(curMiss+".Detail.type"));
			if (jobType.equalsIgnoreCase("ate") || jobType == "ate"){


				//player.sendMessage(jobs.getString((String)playerData.getString("currentJob")+".Details.material") + " " + jobs.getString(playerData.getString("currentJob")+".Details.material"));
				if(event.getItem().getType().name().equalsIgnoreCase(jobMaterial) || event.getItem().getType().name() == jobMaterial){

					player.sendMessage(event.getItem().getType().name()+" Eaten");
					playerData.set("ate", jobAte+1);
					try {
						playerData.save(playeryml);
						//player.sendMessage("save");
					} catch (IOException e) {
						e.printStackTrace();
						//player.sendMessage(" failed");
					}
					//player.sendMessage(" successful");
				}
			}

			if (jobAte > jobAmount){


				plugin.economy.depositPlayer(oPlayer, reward);
				player.sendMessage("___________________________");
				player.sendMessage("_____Job________Complete___");
				player.sendMessage("     Reward: $" + reward);
				player.sendMessage("___________________________");
				playerData.set("ate", 0);
				playerData.set("inJob", isFalse);
				playerData.set("currentJob", "none");
				playerData.set("earned", reward + earned);
				try {
					playerData.save(playeryml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}





	}


}
/*
 * Version 1.1 
 * By K0Gs
 */
