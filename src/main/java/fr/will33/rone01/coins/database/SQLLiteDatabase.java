package fr.will33.rone01.coins.database;

import fr.will33.rone01.coins.CoinsPlugin;
import fr.will33.rone01.coins.api.ISQLBridge;
import fr.will33.rone01.coins.models.CoinsPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLLiteDatabase implements ISQLBridge {

    private final Path sqlFile;
    private Connection connection;
    public SQLLiteDatabase(String fileName){
        this.sqlFile = Paths.get(CoinsPlugin.getInstance().getDataFolder().toString(), fileName);

        try{
            if(!Files.exists(this.sqlFile))
                Files.createFile(this.sqlFile);
        }catch (IOException err){
            err.printStackTrace();
        }
        this.setup();
    }

    /**
     * Connect to database
     */
    private void setup(){
        if(!this.isConnected()){
            try{
                this.connection = DriverManager.getConnection("jdbc:sqlite:" + sqlFile.toAbsolutePath());
                this.onCreateTable();
            }catch (SQLException err){
                err.printStackTrace();
            }
        }
    }

    /**
     * Create table if not exists
     */
    private void onCreateTable(){
        try{
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS coinsPlayer (" +
                    "`uuid` VARCHAR(255) PRIMARY KEY," +
                    "`balance` INTEGER" +
                    ")");
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
        try{
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("INSERT INTO " + this.prefixTable + "coinsPlayer (uuid, balance) VALUES (?, ?) ON CONFLICT DO UPDATE SET balance = ?");
            preparedStatement.setString(1, coinsPlayer.getPlayer().getUniqueId().toString());
            preparedStatement.setLong(2, coinsPlayer.getCoins());
            preparedStatement.setLong(3, coinsPlayer.getCoins());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException err){
            err.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        if (!this.isConnected()) {
            try {
                this.shutdownDataSource();
            }catch (Exception err){
                err.printStackTrace();
            }
            this.setup();
            return this.connection;
        }

        return this.connection;
    }

    /**
     * Close connection
     * @throws Exception
     */
    private void shutdownDataSource() throws Exception{
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new Exception("Erreur: "+e.getMessage());
        }
    }

    /**
     * Check if database is valid
     * @return
     */
    private boolean isConnected() {
        try {
            return (this.connection != null) && (!this.connection.isClosed()) && this.connection.isValid(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
