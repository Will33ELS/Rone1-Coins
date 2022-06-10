package fr.will33.rone01.coins.task;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.models.CoinsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeDetectTask extends BukkitRunnable {
    @Override
    public void run() {
        World world = Bukkit.getWorlds().get(0);
        if(world.getTime() == 0){
            for(CoinsPlayer coinsPlayer : CoinsPlugin.getInstance().getCoinsPlayers().values()){
                coinsPlayer.addCoins(CoinsPlugin.getInstance().getConfig().getInt("config.newDay"));
                CoinsPlugin.getInstance().getCoinsStockage().save(coinsPlayer);
            }
        }
    }
}
