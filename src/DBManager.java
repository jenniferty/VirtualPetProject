/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Modified code from Tutorial 8. A class used to establish connection to an
 * embedded database.
 * @author Jennifer Ty 15903786
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBManager {

    private static final String USER_NAME = "jennifer"; //your DB username
    private static final String PASSWORD = "15903786"; //your DB password
    private static final String URL = "jdbc:derby://localhost:1527/VirtualPetDB";  //url of the DB host
    private Connection conn;

    /**
     * constructor for DBManager class
     */
    public DBManager() {
        establishConnection();
    }

    /**
     * 
     * @return conn
     */
    public Connection getConnection() {
        return this.getConn();
    }

    //Establish connection
    public void establishConnection() {
        if (this.getConn() == null) {
            try {
                setConn(DriverManager.getConnection(URL, USER_NAME, PASSWORD));
                //System.out.println(URL + " Get Connected Successfully ....");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }       
    }

    /**
     * to close connection to database
     */
    public void closeConnections() {
        if (getConn() != null) {
            try {
                getConn().close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
