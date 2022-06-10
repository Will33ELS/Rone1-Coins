package fr.will33.rone01.coins.api;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.models.CoinsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CoinsListener implements Listener {

    /**
     * Get plugin instance
     * @return
     */
    protected CoinsPlugin getInstance(){
        return CoinsPlugin.getInstance();
    }

    /**
     * Get {@link CoinsPlayer} instance
     * @param player Instance of the player
     * @return
     */
    protected CoinsPlayer getCoinsPlayer(Player player){
        return this.getInstance().getCoinsPlayers().get(player);
    }

}
