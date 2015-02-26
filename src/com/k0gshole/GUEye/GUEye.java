package com.k0gshole.GUEye;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/*import uskyblock.PlayerInfo;
import uskyblock.PlayerJoin;
import uskyblock.ProtectionEvents;
import uskyblock.Settings;
import uskyblock.uSkyBlock;*/

















































import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.event.EventHandler;


//import announce.App;











import com.earth2me.essentials.Essentials;

public class GUEye extends JavaPlugin{

	File file = null;

	Material matEvent = null;




	String isFalse = "false";
	String isTrue = "true";


	List worlds;



	public String pName;


	public Inventory GUIWorld;
	public Inventory GUIMain;
	public Inventory GUIMissMenu;
	public Inventory GUIMiss;
	public Inventory GUIJobsMenu;
	public Inventory GUIJobs;
	public Inventory GUISellMenu;

	public Inventory GUIInventory;


	ItemStack currentLogItem;
	File missionyml = new File("plugins/GUEye/mission.yml");
	File assasyml = new File("plugins/GUEye/assassin.yml");
	File jobyml = new File("plugins/GUEye/job.yml");
	File configyml = new File("plugins/GUEye/config.yml");
	File rankyml = new File("plugins/GUEye/ranks.yml");
	File shopyml = new File("plugins/GUEye/shop.yml");
	List lores;
	public YamlConfiguration jobs;
	public YamlConfiguration assass;
	public YamlConfiguration missions;
	public YamlConfiguration config;
	public YamlConfiguration rank;
	public YamlConfiguration shop;
	public String missEnable;
	public String assEnable;
	public String jobEnable;
	public String shopEnable;
	public String invEnable;
	public String host;
	public String port;
	public String database;
	public String userN;
	public String userP;
	public String MySQLEn;
	public List missList;
	public List jobsList;
	public ConfigurationSection lineOne;
	public static Economy economy = null;
	public static Essentials essentials = null;
	public List bWorld = new ArrayList();
	public static GUEye instance;
	private GUEye plugin;
	public MySQL MySQL = null;
	public Boolean SQLEn; 
	public static Permission perms = null;
	
	public Connection c = null;



	public GUEye(){
		file = new File("plugins/GUEye/config.yml");

		GUIMissMenu = null;
		GUIMiss = null;


		lores = new ArrayList();


		worlds = null;
		missEnable = "false";
		assEnable = "false";
		jobEnable = "false";
		shopEnable = "false";
		invEnable = "false";
		missList = null;
		jobsList = null;
		lineOne = null;
	}







	@EventHandler
	public void onEnable(){
		instance=this;
		Bukkit.getPluginManager().registerEvents(new GuiInteract(this), this);

		Bukkit.getPluginManager().registerEvents(new GListeners(this), this);


		getCommand("gui").setExecutor(new Gui());
		getCommand("brb").setExecutor(new BeRightBack());

		if(!configyml.exists()){
			getInstance().saveDefaultConfig();
			config = YamlConfiguration.loadConfiguration(configyml);
			bWorld = (List)config.getList("BlockedWorlds");
			shopEnable = config.getString("shop");
			invEnable = config.getString("inventory");
			MySQLEn = config.getString("MySQL.enabled");
			host = config.getString("MySQL.host");
			port = config.getString("MySQL.port");
			database = config.getString("MySQL.database");
			userN = config.getString("MySQL.username");
			userP = config.getString("MySQL.password");
			if(MySQLEn == "true"){
			SQLConnect();
			checkTables();
			}
		}
		else{

			config = YamlConfiguration.loadConfiguration(configyml);
			bWorld = (List)config.getList("BlockedWorlds");
			shopEnable = config.getString("shop");
			invEnable = config.getString("inventory");
			MySQLEn = config.getString("MySQL.enabled");
			host = config.getString("MySQL.host");
			port = config.getString("MySQL.port");
			database = config.getString("MySQL.database");
			userN = config.getString("MySQL.username");
			userP = config.getString("MySQL.password");
			if(MySQLEn == "true"){
			SQLConnect();
			checkTables();
			}
		}


		if(!shopyml.exists()){
			getInstance().saveResource("shop.yml", false);
			shop = YamlConfiguration.loadConfiguration(shopyml);
		}
		else{

			shop = YamlConfiguration.loadConfiguration(shopyml);
		}

		if(!rankyml.exists()){
			getInstance().saveResource("ranks.yml", false);
			rank = YamlConfiguration.loadConfiguration(rankyml);
		}
		else{

			rank = YamlConfiguration.loadConfiguration(rankyml);
		}

		if (!missionyml.exists()){
			getInstance().saveResource("mission.yml", false);
			missions = YamlConfiguration.loadConfiguration(missionyml);
			if(missions.getString("Enable") != null){
				missEnable = (String)missions.getString("Enable");
				missList = (List)missions.getList("Missions");
			}
		}
		else{

			missions = YamlConfiguration.loadConfiguration(missionyml);

			if(missions.getString("Enable") != null){
				missEnable = (String)missions.getString("Enable");
				missList = (List)missions.getList("Missions");
			}
		}
		if (!assasyml.exists()){
			getInstance().saveResource("assassin.yml", false);
			assass = YamlConfiguration.loadConfiguration(assasyml);
			if(assass.get("Enable") != null){
				assEnable = (String)assass.getString("Enable");
			}
		}
		else{

			assass = YamlConfiguration.loadConfiguration(assasyml);
			if(assass.get("Enable") != null){
				assEnable = (String)assass.getString("Enable");
			}
		}
		if (!jobyml.exists()){
			getInstance().saveResource("job.yml", false);
			jobs = YamlConfiguration.loadConfiguration(jobyml);
			if(jobs.getString("Enable") != null){
				jobEnable = (String)jobs.getString("Enable");

				jobsList = (List)jobs.getList("Jobs");
			}
		}
		else{

			jobs = YamlConfiguration.loadConfiguration(jobyml);
			if(jobs.getString("Enable") != null){
				jobEnable = (String)jobs.getString("Enable");

				jobsList = (List)jobs.getList("Jobs");
			}
		}


		setupEconomy();
		setupEssentials();
		wildCleanTask();
		setupPermissions();
	}



	@EventHandler
	public void onReload(){
		if(!configyml.exists()){
			getInstance().saveDefaultConfig();
			config = YamlConfiguration.loadConfiguration(configyml);
			bWorld = (List)config.getList("BlockedWorlds");
			shopEnable = config.getString("shop");
			invEnable = config.getString("inventory");
			MySQLEn = config.getString("MySQL.enabled");
			host = config.getString("MySQL.host");
			port = config.getString("MySQL.port");
			database = config.getString("MySQL.database");
			userN = config.getString("MySQL.username");
			userP = config.getString("MySQL.password");
		}
		else{

			config = YamlConfiguration.loadConfiguration(configyml);
			bWorld = (List)config.getList("BlockedWorlds");
			shopEnable = config.getString("shop");
			invEnable = config.getString("inventory");
			MySQLEn = config.getString("MySQL.enabled");
			host = config.getString("MySQL.host");
			port = config.getString("MySQL.port");
			database = config.getString("MySQL.database");
			userN = config.getString("MySQL.username");
			userP = config.getString("MySQL.password");
		}

		if (!missionyml.exists()){
			getInstance().saveResource("mission.yml", false);
			missions = YamlConfiguration.loadConfiguration(missionyml);
			if(missions.getString("Enable") != null){
				missEnable = (String)missions.getString("Enable");
				missList = (List)missions.getList("Missions");
			}
		}
		else{

			missions = YamlConfiguration.loadConfiguration(missionyml);

			if(missions.getString("Enable") != null){
				missEnable = (String)missions.getString("Enable");
				missList = (List)missions.getList("Missions");
			}
		}
		if (!assasyml.exists()){
			getInstance().saveResource("assassin.yml", false);
			assass = YamlConfiguration.loadConfiguration(assasyml);
			if(assass.get("Enable") != null){
				assEnable = (String)assass.getString("Enable");
			}
		}
		else{

			assass = YamlConfiguration.loadConfiguration(assasyml);
			if(assass.get("Enable") != null){
				assEnable = (String)assass.getString("Enable");
			}
		}
		if (!jobyml.exists()){
			getInstance().saveResource("job.yml", false);
			jobs = YamlConfiguration.loadConfiguration(jobyml);
			if(jobs.getString("Enable") != null){
				jobEnable = (String)jobs.getString("Enable");
			}
		}
		else{

			jobs = YamlConfiguration.loadConfiguration(jobyml);
			if(jobs.getString("Enable") != null){
				jobEnable = (String)jobs.getString("Enable");
			}
		}
		instance.getServer().broadcastMessage("[GUEye] reloaded...");
	}

	public static GUEye getInstance(){
		return instance;
	}
	@EventHandler
	public void onDisable(){
		System.out.println("Good Bye :( ");

	}

	public void errorLogger(){


	}

	private Boolean SQLConnect(){
		if (MySQLEn.equalsIgnoreCase("true")){
			String error = "";
		MySQL = new MySQL(plugin, host, port, database, userN, userP);
		try {
			c = MySQL.openConnection();
		} catch (ClassNotFoundException e) {
			error = e.getMessage();

			return false;
		} catch (SQLException e) {
			error = e.getMessage();

			return false;
		}
		if (error == ""){

			System.out.println("[GUEye] MySQL Connectivity Completed...");

			SQLEn = true;

			return true;
		}
		}
		
		else{

			System.out.println("[GUEye] MySQL intergation disabled in config...");
			SQLEn = false;
			return false;
			
		}
		return false;
		
	}
	
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        System.out.println("[GUEye] Permissions Enabled...");
        return perms != null;
    }
	
	private Boolean checkTables(){
		PreparedStatement ps = null;
		String error = "";
		Statement statement = null;
		try {
			statement = c.createStatement();
		} catch (SQLException e) {
			error = e.getMessage();

			return false;
		}
		if (error == ""){
		try {
			    statement.executeUpdate("CREATE TABLE IF NOT EXISTS playerdata (UUID VARCHAR(64), playername VARCHAR(64), inmission varchar(64), curmission varchar(64), inassassin varchar(64), curassassinate varchar(64), injob varchar(64), curjob varchar(64), collect int, kills int, rankP int, targetP varchar(64), assassinlevel int, earned int, brbmessage varchar(16));");

			    statement.close();
			    statement = c.createStatement();
			    statement.executeUpdate("CREATE TABLE IF NOT EXISTS inventory (UUID varchar(64), slot1 BLOB, slot2 BLOB, slot3 BLOB, slot4 BLOB, slot5 BLOB, slot6 BLOB, slot7 BLOB, slot8 BLOB, slot9 BLOB, slot10 BLOB, slot11 BLOB, slot12 BLOB, slot13 BLOB, slot14 BLOB, slot15 BLOB, slot16 BLOB, slot17 BLOB, slot18 BLOB, slot19 BLOB, slot20 BLOB, slot21 BLOB, slot22 BLOB, slot23 BLOB, slot24 BLOB, slot25 BLOB, slot26 BLOB, slot27 BLOB, slot28 BLOB, slot29 BLOB, slot30 BLOB, slot31 BLOB, slot32 BLOB, slot33 BLOB, slot34 BLOB, slot35 BLOB);");

			    statement.close();
			    statement = c.createStatement();
			    statement.executeUpdate("CREATE TABLE IF NOT EXISTS  wilderness (locx int, locy int, locz int, material BLOB, world varchar(64), blockdata BLOB);");

			    
			    statement.close();
			    statement = c.createStatement();
			    statement.executeUpdate("CREATE TABLE IF NOT EXISTS chunk (id_chunk int, world varchar(64), x int, y int, owner varchar(64), fire int, explode int, piston int, door int, store int, redstone int, animal int, cartdestroy int, cartdrive int, liquid int, checkc int, name varchar(64), text varchar(64));");
			    
			    PreparedStatement stmt = null;
			    ResultSet res = null;
				stmt = c.prepareStatement("SELECT * FROM chunk");
				stmt.executeQuery();
				res = stmt.getResultSet();
				res.last();
				int size = res.getRow();
				res.first();
				
				if(isMyResultSetEmpty(res)){
			    ps = null;
				ps = c.prepareStatement("INSERT INTO chunk VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setInt(1, 0);
				ps.setString(2, "none");
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.setString(5, "none");
				ps.setInt(6, 0);
				ps.setInt(7, 0);
				ps.setInt(8, 0);
				ps.setInt(9, 0);
				ps.setInt(10, 0);
				ps.setInt(11, 0);
				ps.setInt(12, 0);
				ps.setInt(13, 0);
				ps.setInt(14, 0);
				ps.setInt(15, 0);
				ps.setInt(16, 0);
				ps.setString(17, "none");
				ps.setString(18, "none");
				
				
				ps.executeUpdate();
				ps.close();
				
				}
			    
		} catch (SQLException e) {
			e.printStackTrace();
			error = e.getMessage();
			return false;
		}
		System.out.println("[GUEye] MySQL tables loaded...");
		return true;
		}
	else{

		System.out.println(error);
		return false;
	}
	
	}
	
	public ResultSet getData(String pFinUUID, String database) throws SQLException, ClassNotFoundException{
		c.close();
		c = MySQL.openConnection();
		String error = "";
		Statement statement = null;
		try {
			statement = c.createStatement();
		} catch (SQLException e) {
			error = e.getMessage();
			return null;
		}
		if (error == ""){
		ResultSet res = statement.executeQuery("SELECT * FROM "+database+" WHERE UUID = '" + pFinUUID + "';");/* */
		
		res.first();
		return res;
		}
		return null;
	}
	
	public static boolean isMyResultSetEmpty(ResultSet rs) throws SQLException {
	    return (!rs.isBeforeFirst() && rs.getRow() == 0);
	}
	
	public void upPData(String database, String list1, String list2) throws SQLException, ClassNotFoundException{
		c.close();
		c = MySQL.openConnection();
		String error = "";
		Statement statement = null;
		try {
			statement = c.createStatement();
		} catch (SQLException e) {
			error = e.getMessage();
		}
		if (error == ""){
			
			statement.executeUpdate("INSERT INTO "+database+" "+list1+" VALUES "+list2+";");
			
		}
		
	}
	
	public void updateInv(String pFinUUID, ItemStack[] myObject, int newplayer) {
		Statement statement0 = null;
		PreparedStatement ps = null;
		ItemStack curItem;
		try {
			c.createStatement();
				c = MySQL.openConnection();

		c = MySQL.openConnection();

		if(newplayer == 1){
			statement0.executeUpdate("INSERT INTO inventory (UUID) VALUES ('"+pFinUUID+"');");
		}
		statement0 = null;
		statement0 = c.createStatement();
		statement0.execute("SET SQL_SAFE_UPDATES=0;");
		statement0.executeUpdate(" DELETE FROM minecraft.inventory WHERE UUID='"+pFinUUID+"';");
		//System.out.println("Delete Inventory");
		statement0.execute("SET SQL_SAFE_UPDATES=1;");
		ps = null;
		ps = c.prepareStatement("INSERT INTO inventory VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");


		
		for(int i = 0; i < 36; i++){
			
			//System.out.println((i+1)+"for loop i + 1");
			if( i < 2){

				if  (i == 0){
					
						ps.setString(i+1, pFinUUID);

					//System.out.println("insert UUID into comumn " + (i+1));
					
				}
				
				if(i == 1){
					ps.setNull(i+1, java.sql.Types.BLOB);
					//System.out.println("skip slot0");
				}
			}
			else{
			if(myObject[i-1] == null){


					ps.setNull(i+1, java.sql.Types.BLOB);

				//System.out.println("insert null in slot"+(i+1));
			}
			else	
			{

				//System.out.println("insert item in slot"+(i+1));
				ItemStack tempStack = new ItemStack(myObject[i-1].getType());
				tempStack.setAmount(myObject[i-1].getAmount());
		Map<String, Object> myMap = tempStack.serialize();
		//System.out.println("myMap: "+myMap.toString());
		


		    ByteArrayOutputStream b = new ByteArrayOutputStream();
		    ObjectOutputStream output = new ObjectOutputStream(b);
		    output.writeObject(myMap);
		   //System.out.println(b.toByteArray().toString());

		    

		    ps.setBinaryStream(i+1, new ByteArrayInputStream(b.toByteArray()));
		    
		    //System.out.println("uploaded");


		
			}
		}
			
		}
		
			ps.executeUpdate();
		ps.close();
	    c.close();
		} catch(IOException ioEx) {
		    ioEx.printStackTrace();
		} catch(SQLException sqlEx) {
		    sqlEx.printStackTrace();
		} 			
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {

		}
		
	}
	
	public void updateWild(int x, int y, int z, ItemStack myObject, String world, Byte tByte) throws ClassNotFoundException, SQLException, IOException {

		PreparedStatement ps = null;
		ItemStack curItem;


		c = MySQL.openConnection();
		ps = null;
		ps = c.prepareStatement("INSERT INTO wilderness VALUES ( ?, ?, ?, ?, ?, ?)");
		ps.setInt(1, x);
		ps.setInt(2, y);
		ps.setInt(3, z);
		ps.setString(5, world);
		ps.setByte(6, tByte);

		Map<String, Object> myMap = myObject.serialize();
		//System.out.println("myMap: "+myMap.toString());
		


		    ByteArrayOutputStream b = new ByteArrayOutputStream();
		    ObjectOutputStream output = new ObjectOutputStream(b);
		    output.writeObject(myMap);
		   //System.out.println(b.toByteArray().toString());

		    

		    ps.setBinaryStream(4, new ByteArrayInputStream(b.toByteArray()));
		    
		    //System.out.println("uploaded");



			ps.executeUpdate();
		ps.close();


		
	}
	
	public Inventory returnInv(String pFinUUID, Player player) throws ClassNotFoundException, SQLException, IOException{

		ItemStack[] tempStack = new ItemStack[]{new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG), new ItemStack(Material.LOG)};
		//System.out.println(tempStack.toString());
		Inventory newInv = Bukkit.createInventory(null, 36, "temp");
		c = MySQL.openConnection();
		String error = "";
		PreparedStatement statement = null;
		ResultSet res;
		ByteArrayInputStream inStream;
		ObjectInputStream input;
		Object obj;
		Blob bl = null;
		try {
			statement = c.prepareStatement("Select * from inventory WHERE  UUID LIKE '"+pFinUUID+"'");
			
		} catch (SQLException e) {
			error = e.getMessage();
			return null;
		}
		if (error == ""){
		statement.executeQuery();
		
		ItemStack is = null;
		
		res = statement.getResultSet();
		
		res.last();
		
		for(int i = 1; i < 35; i++){
		bl = null;
		//System.out.println(res.getBlob("slot"+i));
		bl = res.getBlob("slot"+i);
		
		if(bl != null){
			//System.out.println(bl.toString());
		ObjectInputStream in = new ObjectInputStream(bl.getBinaryStream());

		Map<String, Object> data2 = (Map<String, Object>) in.readObject();

		
		
		//System.out.println(data2.toString());
		
		is = ItemStack.deserialize(data2);
		if(is != null){
		//System.out.println(is.toString());
		}
		newInv.setItem(i-1, is);
		}
		}
		return newInv;
		}
		return null;	
		
	}
	
	public void wildClean() {
		Statement statement = null;
		Statement statement2 = null;
		ResultSet res = null;
		Blob bl = null;
		Block bk = null;
		ItemStack tStack = null;
		org.bukkit.World wld = null;
		String sWld = null;
		Byte tByte = null;
		List ls = new ArrayList();
		List ls1 = new ArrayList();
		List ls2 = new ArrayList();
		
		try {

			c = MySQL.openConnection();
		statement = c.createStatement();

			statement2 = c.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {

		
		res = statement.executeQuery("SELECT * FROM wilderness");
		res.last();
		int size = res.getRow();
		res.first();
		
		if(!isMyResultSetEmpty(res)){
			//System.out.println("RES NOT EMPTY");
		for(int i = 0; i < size;i++){
				
				ls1 = new ArrayList();
			
				ls1.add(res.getInt(1));

				ls1.add(res.getInt(2));

				ls1.add(res.getInt(3));

				ls1.add(res.getBlob(4));

				ls1.add(res.getString(5));

				ls1.add(res.getByte(6));

			ls2.add(ls1);
			res.next();
			
		}
			statement2.executeUpdate("DELETE FROM wilderness");
			
			
			for(int k = 0; k < ls2.size();k++){
			ls1 = (List) ls2.get(k);
			sWld = null;
			tByte = null;
			wld = null;
			bk = null;
			bl = null;
			tStack = null;
			int x = (int) ls1.get(0);
			int y = (int) ls1.get(1);
			int z = (int) ls1.get(2);
			bl = (Blob) ls1.get(3);
			sWld = (String) ls1.get(4);
			tByte = (Byte) ls1.get(5);
			if(bl != null){
				//System.out.println("BLOB NOT NULL");
				//System.out.println(bl.toString());
			ObjectInputStream in = new ObjectInputStream(bl.getBinaryStream());

			Map<String, Object> data2 = (Map<String, Object>) in.readObject();
			tStack = ItemStack.deserialize(data2);

			wld = GUEye.getInstance().getServer().getWorld(sWld);
			bk = wld.getBlockAt(x, y, z);
			if(bk.getType().equals(Material.AIR) || bk.getType() == Material.AIR){
				//System.out.println("BLOCK IS AIR");
			bk.setType(tStack.getType());
			bk.setData(tByte);
			}else{
				//System.out.println("BLOCK NOT AIR");
			}
			
			}
			
			
		}

		instance.getServer().broadcastMessage("Wilderness Cleanup");


		}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    public void wildCleanTask() {

    	
    	
    	
    	int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() { public void run() {GUEye.instance.wildClean(); } }, 1,60 * 20);


    }
	
	private boolean setupEconomy()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
			System.out.println("[GUEye] Vault Economy Integration Enabled...");
		}

		return (economy != null);
	}


	private boolean setupEssentials()
	{
		final PluginManager pluginManager = instance.getServer().getPluginManager();
		//System.out.println(pluginManager.toString());
		final Plugin GMplugin = pluginManager.getPlugin("Essentials");

		//System.out.println(GMplugin.toString());
 
		if (GMplugin != null && GMplugin.isEnabled())
		{
			essentials = (Essentials)GMplugin;

			//System.out.println(essentials.toString());
			System.out.println("[GUEye] Essentials Integration Enabled...");
		}
		
		return (essentials != null);
	}


	public Inventory displayWorldGUI(Player player){
		worlds = getServer().getWorlds();
		int mSize = 9;
		if(worlds.size() > 7){
			mSize = 18;
		}
		GUIWorld = Bukkit.createInventory(null,  mSize, "\2479World Menu");

		ItemMeta meta4 = null;
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lExit Worlds");
		lores.add("\247eClick here to return to");
		lores.add("\247eMain Menu.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIWorld.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();

		bWorld = (List)config.getList("BlockedWorlds");
		for(int i = 0; i < worlds.size();i++){
			currentLogItem = new ItemStack(Material.SIGN, 1);
			meta4 = currentLogItem.getItemMeta();
			String worldSplit1 = worlds.get(i).toString();
			String delim = "[\\{\\=\\}]+";
			String[] worldSplit2 = worldSplit1.split(delim);
			if(!worldSplit2[2].equals(player.getWorld().getName())){
				if(!bWorld.contains(worldSplit2[2])){

				meta4.setDisplayName(worldSplit2[2]);
				lores.add("\247eClick here to teleport");
				lores.add("\247eto " + worldSplit2[2] + ".");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIWorld.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();
			}}}
		return GUIWorld;

	}

	public Inventory displayGUI(Player player) {
		GUIMain = Bukkit.createInventory(null, 9, "\2479Main Menu");
		ItemMeta meta4 = null;
		currentLogItem = new ItemStack(Material.SIGN, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lWorlds");
		lores.add("\247eClick here to select a");
		lores.add("\247eworld for teleport.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIMain.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		if(shopEnable.toLowerCase().equals("true")){
		meta4 = null;
		currentLogItem = new ItemStack(Material.CHEST, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lShop");
		lores.add("\247eClick here to open");
		lores.add("\247ethe shop menu.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIMain.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		}
		
		if(invEnable.toLowerCase().equals("true")){
		meta4 = null;
		currentLogItem = new ItemStack(Material.ENDER_CHEST, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\2479GUEye Inventory");
		lores.add("\247eClick here to open");
		lores.add("\247eyour Inventory.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIMain.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		}
		if(missEnable.toLowerCase().equals("true")){
			currentLogItem = new ItemStack(Material.BOOK_AND_QUILL, 1);
			meta4 = currentLogItem.getItemMeta();
			meta4.setDisplayName("\247e\247lMissions");


			lores.add("\247eClick here to reveiw");
			lores.add("\247ecurrent mission setings.");


			meta4.setLore(lores);
			currentLogItem.setItemMeta(meta4);
			GUIMain.addItem(new ItemStack[] {currentLogItem

			});
			lores.clear();
		}
		if(assEnable.toLowerCase().equals("true")){
			currentLogItem = new ItemStack(Material.BOW, 1);
			meta4 = currentLogItem.getItemMeta();
			meta4.setDisplayName("\247e\247lAssasinations");


			lores.add("\247eClick here to reveiw");
			lores.add("\247ecurrent assassin setings.");


			meta4.setLore(lores);
			currentLogItem.setItemMeta(meta4);
			GUIMain.addItem(new ItemStack[] {currentLogItem

			});
			lores.clear();
		}
		if(jobEnable.toLowerCase().equals("true")){
			currentLogItem = new ItemStack(Material.STONE_SPADE, 1);
			meta4 = currentLogItem.getItemMeta();
			meta4.setDisplayName("\247e\247lJobs");


			lores.add("\247eClick here to reveiw");
			lores.add("\247ecurrent job setings.");


			meta4.setLore(lores);
			currentLogItem.setItemMeta(meta4);
			GUIMain.addItem(new ItemStack[] {currentLogItem

			});
			lores.clear();
		}


		meta4 = null;
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lExit GUEye");
		lores.add("\247eClick here exit");
		lores.add("\247eGUEye....");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIMain.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		return GUIMain;
	}

	public Inventory displayMissionMenuGUI(Player player) throws ClassNotFoundException, SQLException{
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}
		File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playeryml);
		worlds = getServer().getWorlds();
		int mSize = 9;
		if(missList.size() > 7){
			mSize = 18;
		}
		GUIMissMenu = Bukkit.createInventory(null,  mSize, "\2479Mission Menu");

		ItemMeta meta4 = null;
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lExit Missions");
		lores.add("\247eClick here to return to");
		lores.add("\247eMain Menu.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIMissMenu.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();


		for(int i = 0; i < missList.size();i++){
			if(SQLEn != false){
			if ((String)playerData.getString("inMission") == "true" || playerData.getString("inMission").equals("true")){
				currentLogItem = new ItemStack(Material.SIGN, 1);
				meta4 = currentLogItem.getItemMeta();

				meta4.setDisplayName((String)playerData.getString("currentMission"));
				lores.add("\247e"+missions.get((String)playerData.getString("currentMission")+".Detail.lineOne"));
				lores.add("\247eClick here to open mission");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIMissMenu.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();
			}
			}else
				if(GUEye.instance.SQLEn = true){
					ResultSet res = getData(pFinUUID, "playerdata");
					res.first();
					if (res.getString("inmission") == "true" || res.getString("inmission").equals("true")){
						currentLogItem = new ItemStack(Material.SIGN, 1);
						meta4 = currentLogItem.getItemMeta();

						meta4.setDisplayName((String)res.getString("currentmission"));
						lores.add("\247e"+missions.get((String)res.getString("currentmission")+".Detail.lineOne"));
						lores.add("\247eClick here to open mission");
						meta4.setLore(lores);
						currentLogItem.setItemMeta(meta4);
						GUIMissMenu.addItem(new ItemStack[] {
								currentLogItem
						});
						lores.clear();
					}
					
				}
			else
			{
				currentLogItem = new ItemStack(Material.SIGN, 1);
				meta4 = currentLogItem.getItemMeta();

				meta4.setDisplayName(missList.get(i).toString());
				lores.add("\247e"+missions.get(missList.get(i).toString()+".Detail.lineOne"));
				lores.add("\247eClick here to open mission");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIMissMenu.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();	
			}
		}
		return GUIMissMenu;

	}

	public Inventory displayMissionGUI(Player player, String clicked) throws ClassNotFoundException, SQLException{

		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}
		worlds = getServer().getWorlds();
		File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playeryml);
		int mSize = 9;
		if(missList.size() > 7){
			mSize = 18;
		}
		GUIMiss = Bukkit.createInventory(null,  mSize, clicked);

		ItemMeta meta4 = null;
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lExit");
		lores.add("\247eClick here to return to");
		lores.add("\247eMissions Menu.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIMiss.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();



		currentLogItem = new ItemStack(Material.SIGN, 1);
		meta4 = currentLogItem.getItemMeta();

		if(GUEye.instance.SQLEn = false){
		meta4.setDisplayName("Detail");
		lores.add("\247e"+(String)missions.get(clicked+".Detail.lineOne"));
		lores.add("\247e"+(String)missions.get(clicked+".Detail.lineTwo"));
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIMiss.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		if((String)playerData.getString("inMission") == isFalse || playerData.getString("inMission").equals(isFalse)){
			currentLogItem = new ItemStack(Material.EMERALD_BLOCK, 1);
			meta4 = currentLogItem.getItemMeta();


			meta4.setDisplayName("\247lStart");
			lores.add("\247eStart mission");
			meta4.setLore(lores);
			currentLogItem.setItemMeta(meta4);
			GUIMiss.addItem(new ItemStack[] {
					currentLogItem
			});
			lores.clear();
		}
		else
			if((String)playerData.getString("inMission") == isTrue || playerData.getString("inMission").equals(isTrue)){
				currentLogItem = new ItemStack(Material.REDSTONE_BLOCK, 1);
				meta4 = currentLogItem.getItemMeta();


				meta4.setDisplayName("\247lCancel");
				lores.add("\247eCancel mission");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIMiss.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();

			}
	
		}else
		if(GUEye.instance.SQLEn = true){
			ResultSet res = getData(pFinUUID, "playerdata");
			res.first();
			meta4.setDisplayName("Detail");
			lores.add("\247e"+(String)missions.get(clicked+".Detail.lineOne"));
			lores.add("\247e"+(String)missions.get(clicked+".Detail.lineTwo"));
			meta4.setLore(lores);
			currentLogItem.setItemMeta(meta4);
			GUIMiss.addItem(new ItemStack[] {
					currentLogItem
			});
			lores.clear();
			if((String)res.getString("inmission") == isFalse || res.getString("inmission").equals(isFalse)){
				currentLogItem = new ItemStack(Material.EMERALD_BLOCK, 1);
				meta4 = currentLogItem.getItemMeta();


				meta4.setDisplayName("\247lStart");
				lores.add("\247eStart mission");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIMiss.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();
			}
			else
				if((String)res.getString("inmission") == isTrue || res.getString("inmission").equals(isTrue)){
					currentLogItem = new ItemStack(Material.REDSTONE_BLOCK, 1);
					meta4 = currentLogItem.getItemMeta();


					meta4.setDisplayName("\247lCancel");
					lores.add("\247eCancel mission");
					meta4.setLore(lores);
					currentLogItem.setItemMeta(meta4);
					GUIMiss.addItem(new ItemStack[] {
							currentLogItem
					});
					lores.clear();

				}
			
		}
		return GUIMiss;
	
	}

	public Inventory displayJobsGUI(Player player, String clicked) throws ClassNotFoundException, SQLException{

		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}
		worlds = getServer().getWorlds();
		File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playeryml);
		int mSize = 9;
		if(jobsList.size() > 7){
			mSize = 18;
		}
		GUIJobs = Bukkit.createInventory(null,  mSize, clicked);

		ItemMeta meta4 = null;
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lExit");
		lores.add("\247eClick here to return to");
		lores.add("\247eJobs Menu.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIJobs.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();



		currentLogItem = new ItemStack(Material.SIGN, 1);
		meta4 = currentLogItem.getItemMeta();

		if(GUEye.instance.SQLEn = false){
		meta4.setDisplayName("Detail");
		lores.add("\247e"+(String)jobs.get(clicked+".Detail.lineOne"));
		lores.add("\247e"+(String)jobs.get(clicked+".Detail.lineTwo"));
		lores.add("\247eReward: $"+jobs.get(clicked+".Detail.reward").toString());
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIJobs.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		if((String)playerData.getString("inJob") == isFalse || playerData.getString("inJob").equals(isFalse)){
			currentLogItem = new ItemStack(Material.EMERALD_BLOCK, 1);
			meta4 = currentLogItem.getItemMeta();


			meta4.setDisplayName("\247lStart");
			lores.add("\247eStart job");
			meta4.setLore(lores);
			currentLogItem.setItemMeta(meta4);
			GUIJobs.addItem(new ItemStack[] {
					currentLogItem
			});
			lores.clear();
		}
		else
			if((String)playerData.getString("inJob") == isTrue || playerData.getString("inJob").equals(isTrue)){
				currentLogItem = new ItemStack(Material.REDSTONE_BLOCK, 1);
				meta4 = currentLogItem.getItemMeta();


				meta4.setDisplayName("\247lCancel");
				lores.add("\247eCancel job");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIJobs.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();

			}
		}
		if(GUEye.instance.SQLEn = true){
			ResultSet res = getData(pFinUUID, "playerdata");
			res.first();
		meta4.setDisplayName("Detail");
		lores.add("\247e"+(String)jobs.get(clicked+".Detail.lineOne"));
		lores.add("\247e"+(String)jobs.get(clicked+".Detail.lineTwo"));
		lores.add("\247eReward: $"+jobs.get(clicked+".Detail.reward").toString());
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIJobs.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		if((String)res.getString("injob") == isFalse || res.getString("injob").equals(isFalse)){
			currentLogItem = new ItemStack(Material.EMERALD_BLOCK, 1);
			meta4 = currentLogItem.getItemMeta();


			meta4.setDisplayName("\247lStart");
			lores.add("\247eStart job");
			meta4.setLore(lores);
			currentLogItem.setItemMeta(meta4);
			GUIJobs.addItem(new ItemStack[] {
					currentLogItem
			});
			lores.clear();
		}
		else
			if((String)res.getString("injob") == isTrue || res.getString("injob").equals(isTrue)){
				currentLogItem = new ItemStack(Material.REDSTONE_BLOCK, 1);
				meta4 = currentLogItem.getItemMeta();


				meta4.setDisplayName("\247lCancel");
				lores.add("\247eCancel job");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIJobs.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();

			}
		}
		return GUIJobs;

	}

	public Inventory displayJobsMenuGUI(Player player) throws ClassNotFoundException, SQLException{
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}
		File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playeryml);
		worlds = getServer().getWorlds();
		int mSize = 9;
		if(jobsList.size() > 7){
			mSize = 18;
		}
		GUIJobsMenu = Bukkit.createInventory(null,  mSize, "\2479Job Menu");

		ItemMeta meta4 = null;
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247lExit Jobs");
		lores.add("\247eClick here to return to");
		lores.add("\247eMain Menu.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIJobsMenu.addItem(new ItemStack[] {
				currentLogItem
		});
		
		lores.clear();


		for(int i = 0; i < jobsList.size();i++){
			if(GUEye.instance.SQLEn = false){
			if ((String)playerData.getString("inJob") == "true" || playerData.getString("inJob").equals("true")){
				currentLogItem = new ItemStack(Material.SIGN, 1);
				meta4 = currentLogItem.getItemMeta();

				meta4.setDisplayName((String)playerData.getString("currentJob"));
				lores.add("\247e"+jobs.get((String)playerData.getString("currentJob")+".Detail.lineOne"));
				lores.add("\247eClick here to open job");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIJobsMenu.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();
			}
			else
			{
				currentLogItem = new ItemStack(Material.SIGN, 1);
				meta4 = currentLogItem.getItemMeta();

				meta4.setDisplayName(jobsList.get(i).toString());
				lores.add("\247e"+jobs.get(jobsList.get(i).toString()+".Detail.lineOne"));
				lores.add("\247eReward: $"+jobs.get(jobsList.get(i).toString()+".Detail.reward").toString());
				lores.add("\247eClick here to open job");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIJobsMenu.addItem(new ItemStack[] {
						currentLogItem
				});
				lores.clear();	
			}
			}
			
			if(GUEye.instance.SQLEn = true){
				ResultSet res = getData(pFinUUID, "playerdata");
				res.first();
				
				if ((String)res.getString("injob") == "true" || res.getString("injob").equals("true")){
					currentLogItem = new ItemStack(Material.SIGN, 1);
					meta4 = currentLogItem.getItemMeta();

					meta4.setDisplayName((String)res.getString("currentjob"));
					lores.add("\247e"+jobs.get((String)res.getString("currentjob")+".Detail.lineOne"));
					lores.add("\247eClick here to open job");
					meta4.setLore(lores);
					currentLogItem.setItemMeta(meta4);
					GUIJobsMenu.addItem(new ItemStack[] {
							currentLogItem
					});
					lores.clear();
				}
				else
				{
					currentLogItem = new ItemStack(Material.SIGN, 1);
					meta4 = currentLogItem.getItemMeta();

					meta4.setDisplayName(jobsList.get(i).toString());
					lores.add("\247e"+jobs.get(jobsList.get(i).toString()+".Detail.lineOne"));
					lores.add("\247eReward: $"+jobs.get(jobsList.get(i).toString()+".Detail.reward").toString());
					lores.add("\247eClick here to open job");
					meta4.setLore(lores);
					currentLogItem.setItemMeta(meta4);
					GUIJobsMenu.addItem(new ItemStack[] {
							currentLogItem
					});
					lores.clear();	
				}
				}
		}
		return GUIJobsMenu;

	}
	
	public Inventory displaySellGUI(Player player){
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}
		File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playeryml);
		worlds = getServer().getWorlds();
		shop = YamlConfiguration.loadConfiguration(shopyml);
		int mSize = 36;
		GUISellMenu = Bukkit.createInventory(null, mSize, "\2479Shop");
		
		ItemMeta meta4 = null;
		


		lores.clear();
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();

		meta4.setDisplayName("\247lExit Shop");
		lores.add("\247eClick here to");
		lores.add("\247eexit the Shop.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUISellMenu.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		
		BigDecimal curWorth = new BigDecimal(0);
		
		
		List aList = shop.getList("Shop");
		for(int u = 0; u < aList.size();u++){
			curWorth = new BigDecimal(0);
		meta4 = null;
		currentLogItem = (ItemStack)aList.get(u);
		curWorth = new BigDecimal(0);
		//player.sendMessage(currentLogItem.toString());
		//player.sendMessage("Bad Item: "+currentLogItem.getType().name());
		curWorth = plugin.essentials.getWorth().getPrice(currentLogItem);
		//player.sendMessage(curWorth.toString());
		curWorth = curWorth.multiply(new BigDecimal(2.5));
		//player.sendMessage(curWorth.toString());
		meta4 = currentLogItem.getItemMeta();
		meta4.setDisplayName("\247l"+currentLogItem.getType().name());
		lores.add("$");
		lores.add(Double.toString(curWorth.doubleValue()));
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUISellMenu.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		}	

		return GUISellMenu;

	}

	public Inventory displayInventoryGUI(Player player) throws ClassNotFoundException, SQLException, IOException{
		String[] pUUID = StringUtils.split(player.getUniqueId().toString(), "-");
		String pFinUUID = "";
		for(int z = 0; z < pUUID.length; z++){
			pFinUUID = pFinUUID + pUUID[z];
		}
		File playeryml = new File("plugins/GUEye/PlayerData/"+pFinUUID+".yml");
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playeryml);
		worlds = getServer().getWorlds();
		shop = YamlConfiguration.loadConfiguration(shopyml);
		int mSize = 36;

		
		
		
		ItemStack cIStack = null;

		GUIInventory = Bukkit.createInventory(null, mSize, "\2479GUEye Inventory");
		
		ItemMeta meta4 = null;
		


		lores.clear();
		currentLogItem = new ItemStack(Material.PORK, 1);
		meta4 = currentLogItem.getItemMeta();

		meta4.setDisplayName("\247lExit Inventory");
		lores.add("\247eClick here to");
		lores.add("\247eexit your Inventory.");
		meta4.setLore(lores);
		currentLogItem.setItemMeta(meta4);
		GUIInventory.addItem(new ItemStack[] {
				currentLogItem
		});
		lores.clear();
		
		if(GUEye.instance.SQLEn = false){
		for(int b = 1;b < 36;b++){
		cIStack = playerData.getItemStack("Inventory."+b);
		GUIInventory.setItem(b, cIStack);
		}
		}else
		if(GUEye.instance.SQLEn = true){
			GUIInventory = Bukkit.createInventory(null, mSize, "\2479GUEye Inventory");
			GUIInventory.setContents(returnInv(pFinUUID, player).getContents());

				meta4 = null;
				


				lores.clear();
				currentLogItem = new ItemStack(Material.PORK, 1);
				meta4 = currentLogItem.getItemMeta();

				meta4.setDisplayName("\247lExit Inventory");
				lores.add("\247eClick here to");
				lores.add("\247eexit your Inventory.");
				meta4.setLore(lores);
				currentLogItem.setItemMeta(meta4);
				GUIInventory.setItem(0, currentLogItem);
				lores.clear();
			

		}

		
		return GUIInventory;

	}
	
}
/*
 * Version 1.1 
 * By K0Gs
 */