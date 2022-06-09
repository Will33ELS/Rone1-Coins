package fr.will33.rone01.coins.database;

import fr.will33.rone01.coins.api.ISQLBridge;
import fr.will33.rone01.coins.models.CoinsPlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLDatabase implements ISQLBridge {

    private final String host,
            database,
            username,
            password,
            prefixTable;

    private Connection connection;

    public MySQLDatabase(String host, String database, String username, String password, String prefixTable){
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.prefixTable = prefixTable;
    }

    /**
     * Connect to database
     */
    private void setup() {
        if (!isConnected()) {
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database + "?autoreconnect=true&useUnicode=true&characterEncoding=utf-8&allowPublicKeyRetrieval=true&useSSL=false", username, password);
                this.onCreateTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create table if not exists
     */
    private void onCreateTable(){
        try{
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + this.prefixTable + "coinsPlayer (" +
                    "`uuid` VARCHAR(255) PRIMARY KEY," +
                    "`balance` LONG" +
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
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("INSERT INTO " + this.prefixTable + "coinsPlayer (uuid, balance) VALUES (?, ?) ON DUPLICATE KEY UPDATE balance = ?");
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
        if (!isConnected()) {
            try {
                shutdownDataSource();
            }catch (Exception err){
                err.printStackTrace();
            }
            setup();
        }
        return connection;
    }

    /**
     * Close connection
     * @throws Exception
     */
    public void shutdownDataSource() throws Exception{
        if(connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            throw new Exception("Erreur: "+e.getMessage());
        }
    }

    /**
     * Check if connection is valid
     * @return
     */
    private boolean isConnected() {
        try {
            return (connection != null) && (!connection.isClosed()) && connection.isValid(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get prefix table
     * @return
     */
    public String getPrefixTable() {
        return prefixTable;
    }
}
