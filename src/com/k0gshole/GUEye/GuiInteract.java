package com.k0gshole.GUEye;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardEndHandler;

import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.earth2me.essentials.Worth;


public class GuiInteract implements Listener{

	private GUEye plugin;
	private Player player;
	private SkullMeta meta;
	private World world;
	private Location loc;
	public String missionListItem;
	public String jobListItem;

	String isFalse = "false";
	String isTrue = "true";
	File playeryml = null;
	File missionyml = new File("plugins/GUEye/mission.yml");
	File configyml = new File("plugins/GUEye/config.yml");
	File jobyml = new File("plugins/GUEye/job.yml");
	public YamlConfiguration playerData = null;
	public YamlConfiguration missions = YamlConfiguration.loadConfiguration(missionyml);
	public YamlConfiguration config = YamlConfiguration.loadConfiguration(configyml);
	public YamlConfiguration jobs = YamlConfiguration.loadConfiguration(jobyml);

	public List missList = (List)missions.getList("Missions");
	public List jobsList = (List)jobs.getList("Jobs");
	public List cleanWorld = (List)config.getList("CleanupWorlds");
	public List cleanBlock = (List)config.getList("CleanupBlocks");
	String error = "";
	Statement statement = null;
	Connection c = null;
	

	public GuiInteract(GUEye plugin){
		this.plugin = plugin;
		player = null;
		meta = null;
		world = null;
		loc = null;
		missionListItem = null;
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) throws ClassNotFoundException, SQLException{
		Player player = event.getPlayer();
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}
		player.sendMessage("Welcome to this GUEye server!");
		//System.out.print("Welcome to this gEye server!");
		
		if(GUEye.instance.SQLEn = true){
			String list1 = null;
			String list2 = null;
			c = GUEye.instance.MySQL.openConnection();



					Statement s = GUEye.instance.MySQL.openConnection().createStatement();
					statement = c.createStatement();



				ResultSet res = GUEye.instance.getData( pFinUUID, "playerdata");

				if (GUEye.instance.isMyResultSetEmpty(res)){

					list1 = new String("( `UUID`, `playername`, `inmission`, `curmission`, `inassassin`, `curassassinate`, `injob`, `curjob`, `collect`, `kills`, `rankP`, `targetP`, `assassinlevel`, `earned`, `brbmessage` )");
					list2 = new String("( '"+pFinUUID+"', '"+player.getName()+"', 'false', 'none', 'false', 'none', 'false', 'none', '0', '0', '0', 'none', '0', '0', 'BRB, gotta P' )"); 	
					GUEye.instance.upPData("playerdata", list1, list2);
				}

			ResultSet res2 = GUEye.instance.getData(pFinUUID, "inventory");

			
			
			//System.out.println(res2.getMetaData().getColumnCount());
				if (GUEye.instance.isMyResultSetEmpty(res2)){

					ItemStack[] is = new ItemStack[] { new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)};

					list1 = new String(" (`UUID`, `slot1`, `slot2`, `slot3`, `slot4`, `slot5`, `slot6`, `slot7`, `slot8`, `slot9`, `slot10`, `slot11`, `slot12`, `slot13`, `slot14`, `slot15`, `slot16`, `slot17`, `slot18`, `slot19`, `slot20`, `slot21`, `slot22`, `slot23`, `slot24`, `slot25`, `slot26`, `slot27`, `slot28`, `slot29`, `slot30`, `slot31`, `slot32`, `slot33`, `slot34`, `slot35`) ");
					list2 = new String(" ('"+pFinUUID+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"', '"+is+"') "); 	
					GUEye.instance.updateInv(pFinUUID, is, 0);
			//System.out.println("Instert into inventory");
			}

		}
			
		if (GUEye.instance.SQLEn = false)
		{
		String thePath = "./plugins/GUEye/PlayerData/";
		String theFile = pFinUUID + ".yml";
		String confPath = "plugins/GUEye/PlayerData/";


		File myPath = new File(thePath);
		File myFile = new File(thePath + theFile);
		File confFile = new File(confPath + theFile);

		if(myPath.exists()){
			//System.out.println("");
		}else{
			boolean wasDirecotyMade = myPath.mkdirs();
			if(wasDirecotyMade)System.out.println("");
			else System.out.println("");
		}
		if(!myFile.exists()){
			try {
				myFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			playeryml = confFile;
			AlphaWriteFile data = new AlphaWriteFile(myFile.getPath(), true);
			try {
				data.writeToFile("inMission: " + isFalse);
				data.writeToFile("currentMission: 'none'");
				data.writeToFile("inAssassin: " + isFalse);
				data.writeToFile("currentAssassination: 'none'");
				data.writeToFile("inJob: " + isFalse);
				data.writeToFile("currentJob: 'none'");
				data.writeToFile("collect: 0");
				data.writeToFile("kill: 0");
				data.writeToFile("ate: 0");
				data.writeToFile("rank: 0");
				data.writeToFile("taret: none");
				data.writeToFile("assassinLevel: 0");
				data.writeToFile("earned: 0");
				data.writeToFile("Inventory: ");
				for (int a = 1;a < 36;a++){
				data.writeToFile("  "+a+": ");	
				}
				data.writeToFile("SellMat: ");
			} catch (IOException e) {
				e.printStackTrace();
			}


		}
		}
	}
	

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		plugin.getServer().broadcastMessage("___________________________");
		plugin.getServer().broadcastMessage("So long " + event.getPlayer().getDisplayName());
		plugin.getServer().broadcastMessage("___________________________");
	}
	
	
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) throws ClassNotFoundException, SQLException{
		
		config = YamlConfiguration.loadConfiguration(configyml);
		
		cleanWorld = (List)config.getList("CleanupWorlds");
		cleanBlock = (List)config.getList("CleanupBlocks");
		int worldMatch = 0;
		int blockMatch = 0;
		c = GUEye.instance.MySQL.openConnection();
		PreparedStatement stmt = null;
		ResultSet res = null;
		Player player = e.getPlayer();
		int claim = 0;
		//player.sendMessage(e.getBlock().getType().toString());
		int x = player.getLocation().getChunk().getX();
		int z = player.getLocation().getChunk().getZ();
		org.bukkit.World wld = player.getWorld();
		
		Byte tByte = null;
		
		
		
		if(GUEye.instance.SQLEn = true){
			stmt = c.prepareStatement("SELECT * FROM chunk");
			stmt.executeQuery();
			res = stmt.getResultSet();
			res.last();
			int size = res.getRow();
			res.first();
			//player.sendMessage(""+size);
			if(!GUEye.instance.isMyResultSetEmpty(res)){
				//if(worldMatch != 0){
					//player.sendMessage("worldMatch");
					//if(blockMatch != 0){
						//player.sendMessage("blockMatch");
					//if(e.getBlock().getType() == Material.LOG || e.getBlock().getType() == Material.LOG_2 ||e.getBlock().getType() == Material.GRASS ||e.getBlock().getType() == Material.DIRT ||e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2 || e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.COAL_ORE){
				//player.sendMessage("Not Empty");
				for(int i = 0; i < size; i++){
				//player.sendMessage("Loop");
				//player.sendMessage("x: "+x+" z: "+z+" world: "+wld.getName()+" Ax: "+res.getInt(3)+" AZ: "+res.getInt(4)+" AWorld: "+res.getString(2));
					if (res.getInt(3) != x && res.getInt(4) != z && wld.getName().equalsIgnoreCase(res.getString(2))){	
						//player.sendMessage("This is not an a ARG Claim");
						//player.sendMessage("x: "+x+" z: "+z+" world: "+wld.getName()+" Ax: "+res.getInt(3)+" AZ: "+res.getInt(4)+" AWorld: "+res.getString(2));
						//return;
						//claim;
					}					
					if (res.getInt(3) == x && res.getInt(4) == z && wld.getName().equalsIgnoreCase(res.getString(2))){	
						//player.sendMessage("This is an a ARG Claim");
						//player.sendMessage("x: "+x+" z: "+z+" world: "+wld.getName()+" Ax: "+res.getInt(3)+" AZ: "+res.getInt(4)+" AWorld: "+res.getString(2));
						//return;
						claim = claim +1;
					}
					res.next();
				}
		
			}
		}
		if(GUEye.perms.has(player, "GUEye.drops")){
		//player.sendMessage("true");}
			return;
		}
		else{
		if(claim == 0){
			e.setCancelled(true);
			return;
		}else{
			return;
		}
		}

		
	}
	

	
	@SuppressWarnings("static-access")
	@EventHandler
	public void guiClick(InventoryClickEvent event) throws ClassNotFoundException, SQLException, IOException{
		Player player = (Player) event.getWhoClicked();
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}


		String theFile = pFinUUID + ".yml";
		String confPath = "plugins/GUEye/PlayerData/";


		File confFile = new File(confPath + theFile);

		playeryml = confFile;
		playerData = YamlConfiguration.loadConfiguration(playeryml);
		List menuNameList = new ArrayList();
		menuNameList.add("null");
		String delim2 = "\\s+";
		String[] wordSplit = event.getInventory().getName().split(delim2);
		if(wordSplit.length > 0){
		for(int e = 0 ;e < wordSplit.length;e++){
		menuNameList.add(wordSplit[e]);
		}
		}
		if (event.getInventory().getName().equalsIgnoreCase("\2479Main Menu")) {
			event.setCancelled(true);
			player = (Player)event.getWhoClicked();
			if (event.getSlot() < 0 || event.getSlot() > 35) {
				return;
			}
			if(event.getCurrentItem().getAmount() != 0){



				if(event.getCurrentItem().hasItemMeta()){
				if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lWorlds")) {

					player.closeInventory();
					player.openInventory(GUEye.getInstance().displayWorldGUI(player));

				} else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247e\247lMissions")) {
					player.closeInventory();

					player.openInventory(GUEye.getInstance().displayMissionMenuGUI(player));
				}else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247e\247lJobs")) {
					player.closeInventory();

					player.openInventory(GUEye.getInstance().displayJobsMenuGUI(player));
					
				}
			else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lShop")) {
				
				if(player.getGameMode() != GameMode.CREATIVE){
				player.closeInventory();

				player.openInventory(GUEye.getInstance().displaySellGUI(player));
				}
				else{
					player.sendMessage("You cannot Use Shop in CREATIVE mode");
				}
			}
			else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\2479GUEye Inventory")) {
				
				if(player.getGameMode() != GameMode.CREATIVE){
				player.closeInventory();

				player.openInventory(GUEye.getInstance().displayInventoryGUI(player));
				
				}
				else{
					player.sendMessage("You cannot Use Inventory in CREATIVE mode");
				}
			}
			else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit GUEye")) {
				
				
				player.closeInventory();

				
				
			}
			}

			}


		}else 

			if (event.getInventory().getName().equalsIgnoreCase("\2479World Menu")) {
				event.setCancelled(true);
				player = (Player)event.getWhoClicked();
				if (event.getSlot() < 0 || event.getSlot() > 35) {
					return;
				}
				if(event.getCurrentItem().getAmount() != 0){





					if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit Worlds"))


					{
						player.closeInventory();
						player.openInventory(GUEye.getInstance().displayGUI(player));


					} 		
					else if(!event.getCurrentItem().getItemMeta().getDisplayName().isEmpty()){
						loc = GUEye.instance.getServer().getWorld(event.getCurrentItem().getItemMeta().getDisplayName()).getSpawnLocation();
						player.teleport(loc);

					}
				}


			}else 

				if (event.getInventory().getName().equalsIgnoreCase("\2479Mission Menu")) {
					event.setCancelled(true);
					player = (Player)event.getWhoClicked();
					if (event.getSlot() < 0 || event.getSlot() > 35) {
						return;
					}
					if(event.getCurrentItem().getAmount() != 0){





						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit Missions"))


						{
							player.closeInventory();
							player.openInventory(GUEye.getInstance().displayGUI(player));


						} 		
						else 
							if(!event.getCurrentItem().getItemMeta().getDisplayName().isEmpty()){

								player.openInventory(GUEye.getInstance().displayMissionGUI(player, event.getCurrentItem().getItemMeta().getDisplayName().toString()));
							}
					}


				}else 

					if (event.getInventory().getName().equalsIgnoreCase("\2479Job Menu")) {
						event.setCancelled(true);
						player = (Player)event.getWhoClicked();
						if (event.getSlot() < 0 || event.getSlot() > 35) {
							return;
						}
						if(event.getCurrentItem().getAmount() != 0){





							if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit Jobs"))


							{
								player.closeInventory();
								player.openInventory(GUEye.getInstance().displayGUI(player));
	

							} 		
							else 
								if(!event.getCurrentItem().getItemMeta().getDisplayName().isEmpty()){

									player.openInventory(GUEye.getInstance().displayJobsGUI(player, event.getCurrentItem().getItemMeta().getDisplayName().toString()));
								}
						}


					}else 

						if (event.getInventory().getName().equalsIgnoreCase("\2479Island Menu")) {
							event.setCancelled(true);
							player = (Player)event.getWhoClicked();
							if (event.getSlot() < 0 || event.getSlot() > 35) {
								return;
							}



						}else 

						if (event.getInventory().getName().equalsIgnoreCase("\2479Shop")) {
							event.setCancelled(true);
							player = (Player)event.getWhoClicked();
							if (event.getSlot() < 0 || event.getSlot() > 35) {
								return;
							}
							if(event.getCurrentItem().getAmount() != 0){

								if(!event.getSlotType().name().equals("QUICKBAR")){
								if(!event.getClickedInventory().getName().equals("container.inventory")){
								//player.sendMessage(event.getCurrentItem().getData().getItemType().getData().getSimpleName());	
							if(!event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit Shop")){

								ItemMeta meta4 = null;
								List lores = new ArrayList();
								if(event.getCurrentItem().hasItemMeta()){
									//if(event.getCurrentItem().getItemMeta().getLore().contains("$")){
										//player.sendMessage(event.getSlotType().name());
										//player.sendMessage(event.getClickedInventory().getName());
										//if(!event.getSlotType().name().equals("CRAFTING")){
											//player.closeInventory();
								String curWorld = player.getWorld().getName();
								OfflinePlayer oPlayer = player;
								int curSlot = event.getSlot();		
								//player.sendMessage(event.getCurrentItem().getData().toString());
								//+ " " +event.getCurrentItem().getType().toString()+" "+event.getCurrentItem().getClass().toString()
								BigDecimal curWorth = plugin.essentials.getWorth().getPrice(event.getCurrentItem());
								BigDecimal baseWorth = plugin.essentials.getWorth().getPrice(event.getCurrentItem());
								Double playerBal = plugin.economy.getBalance(oPlayer);
								
								int curAmount = event.getCurrentItem().getAmount();
								curWorth = curWorth.multiply(new BigDecimal(2.5));
								ItemStack curItemStack = event.getCurrentItem();
								Inventory tInv = player.getInventory();

								

								Boolean cIsNull = false;

								for(int w = 0;w < tInv.getSize();w++){

									if(tInv.getItem(w) == null){
										cIsNull = true;
									}
								}
								if(cIsNull == true){
								if(playerBal > curWorth.doubleValue()){
								meta4 = null;
								meta4 = new ItemStack(curItemStack.getType()).getItemMeta();

								lores.add("$");
								lores.add(Double.toString(baseWorth.doubleValue()));
								meta4.setLore(lores);
								ItemStack give = new ItemStack(curItemStack.getType());
								give.setItemMeta(meta4);
								player.getInventory().addItem(give);
								plugin.economy.withdrawPlayer(oPlayer, curWorld, curWorth.doubleValue());
								player.sendMessage("___________________________");
								player.sendMessage("     Purchased "+curAmount+" "+curItemStack.getType().name()+" for $"+Double.toString(curWorth.doubleValue()));
								player.sendMessage("_____Purchase___Completed__");
								//player.sendMessage("    Rank:" + rank.getString("ranks."+playerData.getString("rank")) );
								player.sendMessage("___________________________");
									
									
								}
								}
								if(cIsNull == false){
									player.sendMessage("You do not have any room to purchase items.");
								}
								if(playerBal < curWorth.doubleValue()){
									player.sendMessage("You do not have the required money" );
									player.sendMessage("to purchase " + curItemStack.getType().name());
								
								}
								
								}		


							}else
								if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit Shop")){
								
									


									player.closeInventory();
									player.openInventory(GUEye.instance.displayGUI(player));
								}
								
								
							}
								
							}
							}
							//}


						}else 

							if (event.getInventory().getName().equalsIgnoreCase("\2479GUEye Inventory")) 
							{

								player = (Player)event.getWhoClicked();

								if(event.getCurrentItem().getAmount() != 0){
									if(event.getCurrentItem().hasItemMeta()){
									if(!event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit Inventory"))
									{

									}else
									if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit Inventory")){
										event.setCancelled(true);
										Inventory tInv = event.getInventory();

										File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
										playerData = YamlConfiguration.loadConfiguration(playeryml);
										//player.sendMessage("Data loaded...");
										for(int x = 1; x < 36;x++){
											
											if(tInv.getItem(x) == null){
												//player.sendMessage("null");
											}
											if(tInv.getItem(x) != null){
											//player.sendMessage(tInv.getItem(x).toString());
											}
										playerData.set("Inventory."+x, tInv.getItem(x));
										}
										try {
											playerData.save(playeryml);
										} catch (IOException e) {
											e.printStackTrace();
										}
										player.closeInventory();
										player.openInventory(GUEye.instance.displayGUI(player));
									}
									
								}
							}


							}else

						if(event.isRightClick()){
							menuNameList = new ArrayList();
							wordSplit = event.getInventory().getName().split(delim2);
							//player.sendMessage(event.getSlotType() + " " + event.getSlot());
						if(wordSplit.length > 0){
						for(int e = 0 ;e < wordSplit.length;e++){
						menuNameList.add(wordSplit[e]);
						}
						}
							if (wordSplit[0].equals("\2479Sell")) {
								event.setCancelled(true);
								return;
								
							}else
							if(plugin.essentials.getWorth().getPrice(event.getCurrentItem()) != null){
							if(event.getCurrentItem().hasItemMeta()){
							if(event.getCurrentItem().getItemMeta().getLore().contains("$")){
								//player.sendMessage(event.getSlotType().name());
								//player.sendMessage(event.getClickedInventory().getName());
								//player.sendMessage(event.getClickedInventory().getTitle());
								//player.sendMessage(player.getOpenInventory().getTopInventory().getName());
							    //player.sendMessage(player.getOpenInventory().getTopInventory().getTitle());
								//player.sendMessage(player.getOpenInventory().getTopInventory().getType().getDefaultTitle());
								if(!event.getSlotType().name().equals("CRAFTING")){
								if(event.getClickedInventory().getName().equals("container.inventory")){
								if(!player.getOpenInventory().getTopInventory().getTitle().equals("container.chest")){
								if(!player.getOpenInventory().getTopInventory().getType().getDefaultTitle().equals("Chest")){
									if(!player.getOpenInventory().getTopInventory().getType().getDefaultTitle().equals("Ender Chest")){

						String curWorld = player.getWorld().getName();
						OfflinePlayer oPlayer = player;
						int curSlot = event.getSlot();		
						//player.sendMessage(event.getCurrentItem().getData().toString());
						//+ " " +event.getCurrentItem().getType().toString()+" "+event.getCurrentItem().getClass().toString()
						BigDecimal curWorth = plugin.essentials.getWorth().getPrice(event.getCurrentItem());
						int curAmount = event.getCurrentItem().getAmount();
						curWorth = curWorth.multiply(new BigDecimal(curAmount));
						ItemStack curItemStack = event.getCurrentItem();
						if(event.getClickedInventory().getName().equalsIgnoreCase("\2479GuiInventory")){
					//		player.sendMessage("GuiInventory purchase from slot "+curSlot+"...");
						event.getInventory().clear(curSlot);
						}
						if(!event.getClickedInventory().getName().equals("\2479GuiInventory")){

				//			player.sendMessage("Inventory purchase from slot "+curSlot+"...");
							player.getInventory().clear(curSlot);
						}
						

						plugin.economy.depositPlayer(oPlayer, curWorld, curWorth.doubleValue());
						player.sendMessage("___________________________");
						player.sendMessage("     Sold "+curAmount+" "+curItemStack.getType().name()+" for $"+Double.toString(curWorth.doubleValue()));
						player.sendMessage("_____Sell_______Completed__");
						//player.sendMessage("    Rank:" + rank.getString("ranks."+playerData.getString("rank")) );
						player.sendMessage("___________________________");
							
							
							}
							}
							}
							}
							}
							}
							}
							}

						}
						


						for(int i = 0; i < missList.size(); i++){

							missionListItem = missList.get(i).toString();
							//player.sendMessage(missionListItem);
							//player.sendMessage(event.getInventory().getName());
							if (event.getInventory().getName().equalsIgnoreCase(missionListItem)){
								//player.sendMessage("loop");
								event.setCancelled(true);
								player = (Player)event.getWhoClicked();
								if (event.getSlot() < 0 || event.getSlot() > 35) {
									return;
								}
								if(event.getCurrentItem().getAmount() != 0){




									if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit"))


									{
										player.closeInventory();
										player.openInventory(GUEye.getInstance().displayMissionMenuGUI(player));


									} 		
									else 

										if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lStart")){
											playerData.set("inMission", isTrue);
											playerData.set("currentMission", event.getInventory().getName().toString());
											try {
												playerData.save(playeryml);
											} catch (IOException e) {
												e.printStackTrace();
											}
											player.sendMessage("___________________________");
											player.sendMessage("___Mission______Started____");
											//player.sendMessage("    Rank:" + rank.getString("ranks."+playerData.getString("rank")) );
											player.sendMessage("___________________________");
											player.closeInventory();
										}else 

											if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lCancel")){
												playerData.set("collect", 0);
												playerData.set("kill", 0);
												playerData.set("ate", 0);
												playerData.set("inMission", isFalse);
												playerData.set("currentMission", "none");
												try {
													playerData.save(playeryml);
												} catch (IOException e) {
													e.printStackTrace();
												}	
												//player.sendMessage("Mission canceled...");
												player.sendMessage("___________________________");
												player.sendMessage("___Mission______Cancelled___");
												//player.sendMessage("    Rank:" + rank.getString("ranks."+playerData.getString("rank")) );
												player.sendMessage("___________________________");


												player.closeInventory();
											}
								}


							}
						}




		for(int i = 0; i < jobsList.size(); i++){

			jobListItem = jobsList.get(i).toString();
			//player.sendMessage(missionListItem);
			//player.sendMessage(event.getInventory().getName());
			if (event.getInventory().getName().equalsIgnoreCase(jobListItem)){
				//player.sendMessage("loop");
				event.setCancelled(true);
				player = (Player)event.getWhoClicked();
				if (event.getSlot() < 0 || event.getSlot() > 35) {
					return;
				}
				if(event.getCurrentItem().getAmount() != 0){




					if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lExit"))


					{
						player.closeInventory();
						player.openInventory(GUEye.getInstance().displayJobsMenuGUI(player));


					} 		
					else 

						if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lStart")){

							playerData.set("inJob", isTrue);
							playerData.set("currentJob", event.getInventory().getName().toString());
							try {
								playerData.save(playeryml);
							} catch (IOException e) {
								e.printStackTrace();
							}
							player.sendMessage("___________________________");
							player.sendMessage("_____Job________Started____");
							//player.sendMessage("    Rank:" + rank.getString("ranks."+playerData.getString("rank")) );
							player.sendMessage("___________________________");
							player.closeInventory();
						}else 

							if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("\247lCancel")){

								playerData.set("collect", 0);
								playerData.set("kill", 0);
								playerData.set("ate", 0);
								playerData.set("inJob", isFalse);
								playerData.set("currentJob", "none");
								try {
									playerData.save(playeryml);
								} catch (IOException e) {
									e.printStackTrace();
								}	
								//player.sendMessage("Mission canceled...");
								player.sendMessage("___________________________");
								player.sendMessage("_____Job________Cancelled___");
								//player.sendMessage("    Rank:" + rank.getString("ranks."+playerData.getString("rank")) );
								player.sendMessage("___________________________");


								player.closeInventory();
							}
				}


			}
		}


	}
	@EventHandler
	public void onGuiClose(InventoryCloseEvent event) throws ClassNotFoundException, SQLException{

		Player player = (Player)event.getPlayer();
		
		//player.sendMessage("Inventory closed...");
		if(GUEye.instance.SQLEn = false){ 
			//System.out.println("SQL Enabled");
		}
		if(event.getInventory().getName().equalsIgnoreCase("\2479GUEye Inventory")){
			//player.sendMessage("GuiInventory closed...");
			Inventory tInv = event.getInventory();
			String[] pUUID = StringUtils.split(event.getPlayer().getUniqueId().toString(), "-");
			String pFinUUID = "";
			for(int z = 0; z < pUUID.length; z++){
				pFinUUID = pFinUUID + pUUID[z];
			}
			File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
			playerData = YamlConfiguration.loadConfiguration(playeryml);
			//player.sendMessage("Data loaded...");
			if(GUEye.instance.SQLEn = false){
			for(int x = 1; x < 36;x++){
				
				if(tInv.getItem(x) == null){
					//player.sendMessage("null");
				}
				if(tInv.getItem(x) != null){
				//player.sendMessage(tInv.getItem(x).toString());
				}
			playerData.set("Inventory."+x, tInv.getItem(x));
			}
			try {
				playerData.save(playeryml);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//player.sendMessage("Data saved...");
		}
			
			if(GUEye.instance.SQLEn = true){
				
			
				//System.out.println("send to SQL");

				GUEye.instance.updateInv(pFinUUID, tInv.getContents(), 0);
			
			}
		}
		
	}
	@EventHandler
	public void onItemBreak(BlockBreakEvent e) throws ClassNotFoundException, SQLException, IOException{

		config = YamlConfiguration.loadConfiguration(configyml);
		
		cleanWorld = (List)config.getList("CleanupWorlds");
		cleanBlock = (List)config.getList("CleanupBlocks");
		int worldMatch = 0;
		int blockMatch = 0;
		c = GUEye.instance.MySQL.openConnection();
		PreparedStatement stmt = null;
		ResultSet res = null;
		Player player = e.getPlayer();
		//player.sendMessage("Block: "+e.getBlock().getType().toString()+", World: "+e.getBlock().getWorld().getName().toLowerCase()+" Blocks: "+cleanBlock.toString()+", Worlds: "+cleanWorld.toString());
		int claim = 0;
		//player.sendMessage(e.getBlock().getType().toString());
		int x = e.getBlock().getChunk().getX();
		int z = e.getBlock().getChunk().getZ();
		org.bukkit.World wld = e.getBlock().getWorld();
		
		Byte tByte = null;
		for(int p = 0; p < cleanWorld.size();p++){
			//player.sendMessage(cleanWorld.get(p).toString());
			if(cleanWorld.get(p).toString().equals(e.getBlock().getWorld().getName().toLowerCase()) || cleanWorld.get(p).toString() == e.getBlock().getWorld().getName().toLowerCase()){
				worldMatch = worldMatch + 1;
			}
		}
		
		for(int q = 0; q < cleanBlock.size();q++){
			//player.sendMessage(cleanBlock.get(q).toString());
			if(cleanBlock.get(q).toString().equals(e.getBlock().getType().toString()) || cleanBlock.get(q).toString() == e.getBlock().getType().toString()){
				blockMatch = blockMatch + 1;
			}
		}
		//player.sendMessage("WorldMatch: "+worldMatch+" BlockMatch: "+blockMatch);
		

		if(GUEye.instance.SQLEn = true){
			stmt = c.prepareStatement("SELECT * FROM chunk");
			stmt.executeQuery();
			res = stmt.getResultSet();
			res.last();
			int size = res.getRow();
			res.first();
			//player.sendMessage(""+size);
			if(!GUEye.instance.isMyResultSetEmpty(res)){
				if(worldMatch != 0){
					//player.sendMessage("worldMatch");
					if(blockMatch != 0){
						//player.sendMessage("blockMatch");
					//if(e.getBlock().getType() == Material.LOG || e.getBlock().getType() == Material.LOG_2 ||e.getBlock().getType() == Material.GRASS ||e.getBlock().getType() == Material.DIRT ||e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2 || e.getBlock().getType() == Material.STONE || e.getBlock().getType() == Material.COAL_ORE){
				//player.sendMessage("Not Empty");
				for(int i = 0; i < size; i++){
				//player.sendMessage("Loop");
				//player.sendMessage("x: "+x+" z: "+z+" world: "+wld.getName()+" Ax: "+res.getInt(3)+" AZ: "+res.getInt(4)+" AWorld: "+res.getString(2));
					if (res.getInt(3) != x && res.getInt(4) != z && wld.getName().equalsIgnoreCase(res.getString(2))){	
						//player.sendMessage("This is not an a ARG Claim");
						//player.sendMessage("x: "+x+" z: "+z+" world: "+wld.getName()+" Ax: "+res.getInt(3)+" AZ: "+res.getInt(4)+" AWorld: "+res.getString(2));
						//return;
						//claim;
					}					
					if (res.getInt(3) == x && res.getInt(4) == z && wld.getName().equalsIgnoreCase(res.getString(2))){	
						//player.sendMessage("This is an a ARG Claim");
						//player.sendMessage("x: "+x+" z: "+z+" world: "+wld.getName()+" Ax: "+res.getInt(3)+" AZ: "+res.getInt(4)+" AWorld: "+res.getString(2));
						//return;
						claim = claim +1;
					}
					res.next();
				}
					
					
				if(GUEye.perms.has(player, "GUEye.cleanupexempt")){
				//player.sendMessage("true");}
				}
				else{
				//player.sendMessage("false");
			if(claim == 0){
			tByte = e.getBlock().getData();
			String list1 = new String("( `locx`, `locy`, `locz`, `material`, `world`, `blockdata` )");
			String list2 = new String("( '"+e.getBlock().getX()+"', '"+e.getBlock().getY()+"', '"+e.getBlock().getZ()+"', '"+new ItemStack(e.getBlock().getType())+"', '"+e.getBlock().getWorld().toString()+"', '"+tByte+"' )");
			GUEye.instance.updateWild(e.getBlock().getX(), e.getBlock().getY(), e.getBlock().getZ(), new ItemStack(e.getBlock().getType()), e.getBlock().getWorld().getName(), e.getBlock().getData());
			//player.sendMessage("Block Uploaded");
			}
					}
		
			
		
		
			}
			}
		}
			stmt.close();
	
		}
		if(GUEye.instance.SQLEn = false){
			
		}
		
		
	}
}
/*
 * Version 1.1 
 * By K0Gs
 */
