/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jaggy.bungeelogin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
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
            if (plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                String connectionstr = "jdbc:mysql://" + plugin.config.getMysqlHost() + ":" + plugin.config.getMysqlPort() + "/"
                        + plugin.config.getMysqlDbName() + "?" + "user=" + plugin.config.getMysqlUsername() + "&password=" + plugin.config.getMysqlPassword();
                try {
                    connection = DriverManager.getConnection(connectionstr);
                    TableStruct struct;
                    Field[] fields = {new Field("id", "INT", 11, ""),
                                      new Field("username", "VARCHAR", 32, "unsigned NOT NULL auto_increment"),
                                      new Field("password", "TEXT", 0, ""),
                                      new Field("email", "VARCHAR", 255, ""),
                                      new Field("UUID", "VARCHAR", 255, "")
                                     };
                    struct = new TableStruct(fields);
                    createTable("users", struct, "id");
                } catch (SQLException ex) {
                    plugin.log.warning(ex.getMessage());
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
                plugin.log.warning(ex.getMessage());
            }
            return result;
        }

        public void closeConnection() {
            try {
                connection.close();
            } catch (SQLException ex) {
                plugin.log.warning(ex.getMessage());
            }
        }

        public void createTable(String table, TableStruct struct, String primary) {
            Statement stmt;
            try {
                stmt = connection.createStatement();
               stmt.execute("CREATE TABLE IF NOT EXISTS " + table + " " + fparse(struct) + ", PRIMARY KEY (" + primary + ") ) ENGINE=MyISAM  DEFAULT CHARSET=utf8");
                stmt.close();
            } catch (SQLException ex) {
                plugin.log.warning(ex.getMessage());
            }
        }
            /**
             *
             * @param struct
             * @return String of
             */
        private String fparse(TableStruct struct) {
            String str = "(";
            boolean first = true;
            for (Field f : struct.getFields()) {
                f.getName();
                if(first == true) {
                    first = false;
                    str = str + f.getName()+" "+f.getType()+" "+f.getPrams();
                    if(f.getSize() > 0) {
                        str = str +"("+f.getSize()+")";
                    }
                } else {
                    str = str + ", "+ f.getName()+" "+f.getType();
                    if(f.getSize() > 0) {
                        str = str +"("+f.getSize()+")";
                    }
                }
            }
            return str;

        }
    }

    public class Field {

        private final String name;
        private final String type;
        private final int size;
        private final String params;

        /**
         * Defines a field to use later.
         *
         * @param namestr name of the field
         * @param typestr type of mysql field
         * @param sizeint int size of the field
         */
        Field(String namestr, String typestr, int sizeint, String fparams) {
            name = namestr;
            type = typestr;
            size = sizeint;
            params = fparams;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public int getSize() {
            return size;
        }

        private String getPrams() {
            return params;
        }
    }

    public class TableStruct {

        private Field[] fields;

        /**
         * TableStruct datatype is an array object in general recommended use
         * with Field datatype.
         *
         * @param objList
         */
        TableStruct(Field[] objList) {
            fields = objList;
        }

        public Field[] getFields() {
            return fields;
        }
    }
}

