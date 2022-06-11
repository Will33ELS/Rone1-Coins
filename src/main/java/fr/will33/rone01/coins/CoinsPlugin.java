package fr.will33.rone01.coins;

import fr.will33.rone01.coins.api.ISQLBridge;
import fr.will33.rone01.coins.commands.BaltopCommand;
import fr.will33.rone01.coins.commands.CoinsCommand;
import fr.will33.rone01.coins.database.MySQLDatabase;
import fr.will33.rone01.coins.database.SQLLiteDatabase;
import fr.will33.rone01.coins.listener.PlayerListener;
import fr.will33.rone01.coins.models.CoinsPlayer;
import fr.will33.rone01.coins.stockage.CoinsStockage;
import fr.will33.rone01.coins.task.TimeDetectTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;


public class CoinsPlugin extends JavaPlugin {

    private ISQLBridge sqlBridge;
    private final Map<Player, CoinsPlayer> coinsPlayers = new HashMap<>();
    private CoinsStockage coinsStockage;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        if(this.getConfig().getBoolean("database.sqllite.enable")){
            this.sqlBridge = new SQLLiteDatabase(this.getConfig().getString("database.sqllite.filename"));
        }else if(this.getConfig().getBoolean("database.mysql.enable")){
            this.sqlBridge = new MySQLDatabase(
                    this.getConfig().getString("database.mysql.host"),
                    this.getConfig().getString("database.mysql.database"),
                    this.getConfig().getString("database.mysql.username"),
                    this.getConfig().getString("database.mysql.password"),
                    this.getConfig().getString("database.mysql.tablePrefix")
                    );
        }

        if(this.sqlBridge == null){
            Bukkit.getPluginManager().disablePlugin(this);
            throw new RuntimeException("No database is configured!");
        }

        this.coinsStockage = new CoinsStockage();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        this.getCommand("baltop").setExecutor(new BaltopCommand());
        this.getCommand("coins").setExecutor(new CoinsCommand());
        new TimeDetectTask().runTaskTimer(this, 0, 1);
    }

    /**
     * Get {@link CoinsStockage} instance
     * @return
     */
    public CoinsStockage getCoinsStockage() {
        return coinsStockage;
    }

    /**
     * Get {@link CoinsPlayer} instances
     * @return
     */
    public Map<Player, CoinsPlayer> getCoinsPlayers() {
        return coinsPlayers;
    }

    /**
     * Get {@link ISQLBridge} instance
     * @return
     */
    public ISQLBridge getSqlBridge() {
        return sqlBridge;
    }

    /**
     * Get plugin instance
     * @return
     */
    public static CoinsPlugin getInstance(){
        return CoinsPlugin.getPlugin(CoinsPlugin.class);
    }
}
