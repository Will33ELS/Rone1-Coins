package fr.will33.rone01.coins.stockage;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.api.ISQLBridge;
import fr.will33.rone01.coins.database.MySQLDatabase;
import fr.will33.rone01.coins.models.CoinsPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CoinsStockage {

    private final ISQLBridge sqlBridge;
    private final String prefixTable;

    public CoinsStockage(){
        this.sqlBridge = CoinsPlugin.getInstance().getSqlBridge();
        if(sqlBridge instanceof MySQLDatabase mySQLDatabase)
            this.prefixTable = mySQLDatabase.getPrefixTable();
        else
            this.prefixTable = "";
    }

    /**
     * Load player data
     * @param coinsPlayer Instance of the player
     */
    public void load(CoinsPlayer coinsPlayer){
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("SELECT balance FROM " + this.prefixTable + "coinsPlayer WHERE uuid = ?");
            preparedStatement.setString(1, coinsPlayer.getPlayer().getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                coinsPlayer.setCoins(resultSet.getLong("balance"));
            } else {
                coinsPlayer.setCoins(CoinsPlugin.getInstance().getConfig().getLong("config.startBalance"));
                this.create(coinsPlayer);
            }
            resultSet.close();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * Create player data
     * @param coinsPlayer Instance of the player
     */
    private void create(CoinsPlayer coinsPlayer){
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("INSERT INTO " + this.prefixTable + "coinsPlayer (uuid, balance) VALUES (?, ?)");
            preparedStatement.setString(1, coinsPlayer.getPlayer().getUniqueId().toString());
            preparedStatement.setLong(2, coinsPlayer.getCoins());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * Save player data
     * @param coinsPlayer Instance of the player
     */
    public void save(CoinsPlayer coinsPlayer){
        try {
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("UPDATE " + this.prefixTable + "coinsPlayer SET balance = ? WHERE uuid = ?");
            preparedStatement.setLong(1, coinsPlayer.getCoins());
            preparedStatement.setString(2, coinsPlayer.getPlayer().getUniqueId().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * Add amount in the balance to offline player
     * @param uuid UUID of the offline player
     * @param amount Amount to add
     */
    public void addAmount(UUID uuid, long amount){
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("UPDATE " + this.prefixTable + "coinsPlayer SET balance += ? WHERE uuid = ?");
            preparedStatement.setLong(1, amount);
            preparedStatement.setString(2, uuid.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    /**
     * Get top
     * @return
     */
    public Map<UUID, Long> getTop(){
        Map<UUID, Long> top = new HashMap<>();
        try{
            PreparedStatement preparedStatement = this.sqlBridge.getConnection().prepareStatement("SELECT * FROM " + this.prefixTable + "coinsPlayer ORDER BY balance DESC");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                top.put(UUID.fromString(resultSet.getString("uuid")), resultSet.getLong("balance"));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException err){
            err.printStackTrace();
        }
        return top;
    }

}
