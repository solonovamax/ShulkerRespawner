package com.github.joelgodofwar.sr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class ShulkerRespawner extends JavaPlugin implements Listener {
    public final static Logger                   logger                 = Logger.getLogger("Minecraft");
    public static       String                   daLang;
    public static       boolean                  UpdateCheck;
    private final       ScheduledExecutorService updateExecutor         = Executors.newSingleThreadScheduledExecutor();
    private             File                     langFile;
    private             boolean                  shouldAlertOfNewUpdate = false;
    private             String                   versionToUpdateTo      = "none";
    private             FileConfiguration        lang;
    private             boolean                  debug;
    
    public static String getMCVersion() {
        String strVersion = Bukkit.getVersion();
        strVersion = strVersion.substring(strVersion.indexOf("MC: "), strVersion.length());
        strVersion = strVersion.replace("MC: ", "").replace(")", "");
        
        return strVersion;
    }
    
    public FileConfiguration getLang() {
        return lang;
    }
    
    public void setLang(FileConfiguration lang) {
        this.lang = lang;
    }
    
    public boolean isDebug() {
        return debug;
    }
    
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    
    public void consoleInfo(boolean enabled) {
        PluginDescriptionFile pluginDescription = this.getDescription();
        logger.info("\u001B[32m**************************************\u001B[0m");
        logger.info("\u001B[32m" + pluginDescription.getName() + " v" + pluginDescription.getVersion() + "\u001B[0m is " +
                    (enabled ? "ENABLED" : "DISABLED"));
        logger.info("\u001B[33m**************************************\u001B[0m");
    }
    
    public void logDebug(String string) {
        if (debug) logger.info("[DEBUG] " + string);
    }
    
    public void checkForUpdate() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/JoelGodOfwar/ShulkerRespawner/master/versioncheck/1.13/version.txt");
            
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(5000);
            
            BufferedReader reader       = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String         response     = reader.readLine();
            String         localVersion = this.getDescription().getVersion();
            
            if (debug) {
                logDebug("response=" + response + ".");
                logDebug("localVersion= ." + localVersion + ".");
            }
            if (!response.equalsIgnoreCase(localVersion)) {
                versionToUpdateTo = response;
                shouldAlertOfNewUpdate = true;
            }
        } catch (Exception e) {
            logger.info("Ran into error while trying to fetch version file."); //TODO add translation for error message
            e.printStackTrace();
        }
    }
    
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && shouldAlertOfNewUpdate) {
            player.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.get("newversion." + daLang + ""));
            shouldAlertOfNewUpdate = false;
            versionToUpdateTo = "none";
        }
    }
    
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) { //onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        if (debug)
            logDebug("entity=" + entity.getType());
        if (entity instanceof Enderman) {
            if (debug)
                logDebug("biome=" + entity.getWorld().getEnvironment().toString());
            if (entity.getWorld().getEnvironment() == Environment.THE_END &&
                (entity.getLocation().getBlock().getBiome() == Biome.END_HIGHLANDS ||
                 entity.getLocation().getBlock().getBiome() == Biome.END_MIDLANDS)) {
        
                if (debug)
                    logDebug("block=" + entity.getLocation().getBlock().getType().toString());
                if (entity.getLocation().subtract(0, 1, 0).getBlock().getType().toString().contains("PURPUR") ||
                    entity.getLocation().getBlock().getType().toString().contains("PURPUR")) {
                    Location location = entity.getLocation();
                    World    world    = entity.getWorld();
                    e.setCancelled(true);
                    if (debug)
                        logDebug("Enderman tried to spawn at " + location + " and a shulker was spawned in it's place.");
                    world.spawn(location, Shulker.class);
                }
            }
        }
    }
    
    @Override // TODO: onDisable
    public void onDisable() {
        consoleInfo(false);
    }
    
    @Override // TODO: onEnable
    public void onEnable() {
        daLang = getConfig().getString("lang");
        debug = getConfig().getBoolean("debug");
        langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {                                  // checks if the yaml does not exist
            langFile.getParentFile().mkdirs();                  // creates the /plugins/<pluginName>/ directory if not found
            saveResource("lang.yml", true);
            //ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
            
        }
        lang = new YamlConfiguration();
        try {
            lang.load(langFile);
        } catch (IOException | InvalidConfigurationException e1) {
            e1.printStackTrace();
        }
        
        // DEV check
        File jarfile = this.getFile().getAbsoluteFile();
        if (jarfile.toString().toUpperCase().contains("DEV")) {
            debug = true;
            logDebug("the name ShulkerRespawner jar contains the word \"dev\", debug set to true.");
        }
        getServer().getPluginManager().registerEvents(this, this);
        consoleInfo(true);
        if (getConfig().getBoolean("debug") && !(jarfile.toString().contains("-DEV"))) {
            logDebug("Config.yml dump");
            logDebug("auto_update_check=" + getConfig().getBoolean("auto_update_check"));
            logDebug("debug=" + getConfig().getBoolean("debug"));
            logDebug("lang=" + getConfig().getString("lang"));
        }
    
        this.getCommand("sr").setExecutor(new SRCommand(this));
    
        updateExecutor.scheduleAtFixedRate(this::checkForUpdate, 0, 1, TimeUnit.DAYS);//check for updates every 24 hours
    }
}