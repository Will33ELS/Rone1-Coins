package fr.will33.rone01.coins.listener;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.api.CoinsListener;
import fr.will33.rone01.coins.models.CoinsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener extends CoinsListener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        CoinsPlayer coinsPlayer = new CoinsPlayer(player);
        this.getInstance().getCoinsStockage().load(coinsPlayer);
        this.getInstance().getCoinsPlayers().put(player, coinsPlayer);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        CoinsPlayer coinsPlayer = this.getCoinsPlayer(player);
        this.getInstance().getCoinsStockage().save(coinsPlayer);
        this.getInstance().getCoinsPlayers().remove(player);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getPlayer();
        CoinsPlayer coinsPlayer = this.getCoinsPlayer(player);
        int takeOnDeath = this.getInstance().getConfig().getInt("config.takeCoinsOnDeath");
        if(coinsPlayer != null){
            if(coinsPlayer.getCoins() >= takeOnDeath){
                coinsPlayer.takeCoins(takeOnDeath);
            }
        }
    }

}
