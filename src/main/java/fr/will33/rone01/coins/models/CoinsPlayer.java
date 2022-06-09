package fr.will33.rone01.coins.models;

import org.bukkit.entity.Player;

public class CoinsPlayer {

    private final Player player;
    private long coins;

    public CoinsPlayer(Player player){
        this.player = player;
    }

    /**
     * Get {@link Player} instance
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get coins balance
     * @return
     */
    public long getCoins() {
        return coins;
    }

    /**
     * Define coins balance
     * @param coins Amount to define
     */
    public void setCoins(long coins) {
        this.coins = coins;
    }

    /**
     * Add coins in the balance
     * @param amount Amount to add
     */
    public void addCoins(long amount){
        this.coins += amount;
    }

    /**
     * Take coins in the balance
     * @param amount Amount to take
     */
    public void takeCoins(long amount){
        this.coins -= amount;
    }
}
