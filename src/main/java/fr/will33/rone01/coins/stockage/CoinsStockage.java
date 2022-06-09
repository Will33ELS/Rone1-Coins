package fr.will33.rone01.coins.stockage;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.api.ISQLBridge;
import fr.will33.rone01.coins.database.MySQLDatabase;
import fr.will33.rone01.coins.database.SQLLiteDatabase;
import fr.will33.rone01.coins.models.CoinsPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            }
            resultSet.close();
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
        if(this.sqlBridge instanceof SQLLiteDatabase sqlLiteDatabase)
            sqlLiteDatabase.save(coinsPlayer);
        else if(this.sqlBridge instanceof MySQLDatabase mySQLDatabase)
            mySQLDatabase.save(coinsPlayer);
    }

}
