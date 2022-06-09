package fr.will33.rone01.coins.api;

import java.sql.Connection;

public interface ISQLBridge {

    /**
     * Get database connection
     * @return
     */
    Connection getConnection();
}
