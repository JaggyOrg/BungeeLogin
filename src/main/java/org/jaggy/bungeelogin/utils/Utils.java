/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.utils;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jaggy.bungeelogin.BungeeLogin;

/**
 *
 * @author Matthew
 */
public class Utils {
    public final BungeeLogin plugin;
    public Messages Messages = new Messages();
    public Database DB = new Database();

    public Utils(BungeeLogin lplugin) {
        plugin = lplugin;
    }
    public class Messages {

        public void sendNotLoginMsg(ProxiedPlayer player) {
            if (plugin.config.getOnlineMode()) {
                if (plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                    plugin.sendError(player, "You must /register to be able to use our service.");
                } else {
                    plugin.sendError(player, "This server is linked to a website and you must /register to be able to use our service.");
                }
            } else {
                if (plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                    plugin.sendError(player, "You must /login or /register to be able to use our service.");
                } else {
                    plugin.sendError(player, "You must /login or /register to be able to use our service.");
                }
            }
        }
    }
    public class Database {
        Connection connection;
        public void connect() {
            if(plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                String connectionstr = "jdbc:mysql://"+plugin.config.getMysqlHost()+":"+plugin.config.getMysqlPort()+"/"+
                        plugin.config.getMysqlDbName()+"?" + "user="+plugin.config.getMysqlUsername()+"&password="+plugin.config.getMysqlPassword();
                try {
                    connection = DriverManager.getConnection(connectionstr);
                } catch (SQLException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        public ResultSet query(String str) {
            ResultSet result = null;
            try {
                Statement stmt = connection.createStatement();
                result = stmt.executeQuery(str);
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;    
        }
        public void closeConnection() {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void createTable(String table, TableStruct struct) {
            Statement stmt;
            try {
                
                stmt = connection.createStatement();
                stmt.execute("CREATE TABLE "+table+" ");
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public class Field {
            String name;
            String type;
            int size;
            /**
             * Defines a field to use later.
             * 
             * @param namestr name of the field
             * @param typestr type of mysql field
             * @param sizeint int size of the field
             */
            Field(String namestr, String typestr, int sizeint) {
                name = namestr;
                type = typestr;
                size = sizeint;
            }
        }

        public class TableStruct {
            Array fields;
            /**
             * TableStruct datatype is an array object in general recommended use
             * with Field datatype.
             * @param objList 
             */
            TableStruct(Object objList) {
                fields = (Array) objList;
            }
        }
    }
}

