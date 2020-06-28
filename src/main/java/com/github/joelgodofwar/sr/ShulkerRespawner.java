package com.github.joelgodofwar.sr;

import com.github.joelgodofwar.sr.commands.DebugCommand;
import com.github.joelgodofwar.sr.commands.DistanceCommand;
import com.github.joelgodofwar.sr.commands.HelpCommand;
import com.github.joelgodofwar.sr.commands.LangCommand;
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
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;


public class ShulkerRespawner extends JavaPlugin implements Listener {
    public static boolean           UpdateCheck;
    private final Logger            logger                  = this.getLogger();
    private       String            daLang;
    private       boolean           shouldAlertOfNewUpdate  = false;
    private       String            versionToUpdateTo       = "none";
    private       FileConfiguration lang;
    private       boolean           debug;
    private       double            distanceBetweenShulkers = 10;
    
    public static String getMCVersion() {
        String strVersion = Bukkit.getVersion();
        strVersion = strVersion.substring(strVersion.indexOf("MC: "), strVersion.length());
        strVersion = strVersion.replace("MC: ", "").replace(")", "");
        
        return strVersion;
    }
    
    public double getDistanceBetweenShulkers() {
        return distanceBetweenShulkers;
    }
    
    public void setDistanceBetweenShulkers(double distanceBetweenShulkers) {
        this.distanceBetweenShulkers = distanceBetweenShulkers;
        getConfig().set("distanceBetweenShulkers", distanceBetweenShulkers);
        System.out.println("set distance between shulkers.");
        saveConfig();
    }
    
    public String getDaLang() {
        return daLang;
    }
    
    public void setDaLang(String daLang) {
        this.daLang = daLang;
        getConfig().set("lang", daLang);
        saveConfig();
    }
    
    public FileConfiguration getLang() {
        return lang;
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
            URL url = new URL("https://raw.githubusercontent.com/solonovamax/ShulkerRespawner/master/versioncheck/1.13/version.txt");
            
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
            player.sendMessage(ChatColor.YELLOW + this.getName() + ChatColor.RED + " " + lang.getString("newversion." + daLang));
            shouldAlertOfNewUpdate = false;
            versionToUpdateTo = "none";
        }
    }
    
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) { //onEntitySpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        if (debug)
            logDebug("entity=" + entity.getType());
        if (entity instanceof Enderman) {
            if (debug)
                logDebug("biome=" + entity.getWorld().getEnvironment().toString());
            if (entity.getWorld().getEnvironment() == Environment.THE_END &&
                (entity.getLocation().getBlock().getBiome() == Biome.END_HIGHLANDS ||
                 entity.getLocation().getBlock().getBiome() == Biome.END_MIDLANDS)) {
                
                if (debug) {
                    logDebug("block=" + entity.getLocation().getBlock().getType().toString());
                }
                if (entity.getLocation().subtract(0, 1, 0).getBlock().getType().toString().contains("PURPUR") ||
                    entity.getLocation().getBlock().getType().toString().contains("PURPUR")) {
                    Location location = entity.getLocation();
                    World    world    = entity.getWorld();
                    
                    for (Entity en : e.getEntity().getNearbyEntities(distanceBetweenShulkers / 2, distanceBetweenShulkers / 2,
                                                                     distanceBetweenShulkers / 2)) { /* only allow a shulker to spawn if
                                                                                                           there are no other shulkers
                                                                                                           within a 10 block radius. */
                        if (en instanceof Shulker) {
                            return;
                        }
                    }
                    
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
        saveDefaultConfig();
        
        daLang = getConfig().getString("lang");
        debug = getConfig().getBoolean("debug");
        distanceBetweenShulkers = getConfig().getDouble("distanceBetweenShulkers");
        
        File langFile = new File(getDataFolder(), "lang.yml");
        if (!langFile.exists()) {                                  // checks if the yaml does not exist
            langFile.getParentFile().mkdirs();                  // creates the /plugins/<pluginName>/ directory if not found
            saveResource("lang.yml", true);
            //ConfigAPI.copy(getResource("lang.yml"), langFile); // copies the yaml from your jar to the folder /plugin/<pluginName>
        }
        
        lang = new YamlConfiguration();
        try {
            lang.load(langFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        
        
        getServer().getPluginManager().registerEvents(this, this);
        consoleInfo(true);
        if (getConfig().getBoolean("debug")) {
            logDebug("Config.yml dump");
            logDebug("auto_update_check=" + getConfig().getBoolean("auto_update_check"));
            logDebug("debug=" + getConfig().getBoolean("debug"));
            logDebug("lang=" + getConfig().getString("lang"));
        }
        
        getCommand("sr-help").setExecutor(new HelpCommand(this));
        getCommand("sr-lang").setExecutor(new LangCommand(this));
        getCommand("sr-debug").setExecutor(new DebugCommand(this));
        getCommand("sr-distance").setExecutor(new DistanceCommand(this));
        
        //updateExecutor.scheduleAtFixedRate(this::checkForUpdate, 0, 1, TimeUnit.DAYS);//check for updates every 24 hours
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::checkForUpdate, 0, 1728000);
    }
}