/*
 * Copyright 2014 JaggyOrg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jaggy.bungeelogin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
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
    public Email Email = new Email();

    public Utils(BungeeLogin lplugin) {
        plugin = lplugin;
    }

    public class Email {
        public void send(String to, String subject, String message) {
            try {           
                Properties properties = new Properties();
                String server = plugin.config.getMailServer();
                String from = plugin.config.getMailFrom();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.host", server);
                properties.put("mail.smtp.port", "465");
                final String username = plugin.config.getMailUsername();
                final String password = plugin.config.getMailPassword();
                Session emailSession = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
                Message emailMessage = new MimeMessage(emailSession);
                emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                emailMessage.setFrom(new InternetAddress(from));
                emailMessage.setSubject(subject);
                emailMessage.setText(message);
                emailSession.setDebug(true);
                Transport.send(emailMessage);
            } catch (MessagingException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                    createTable(plugin.config.getMysqlTable(), struct, "id");
                } catch (SQLException ex) {
                    plugin.log.warning(ex.getMessage());
                }
            }
        }

        public ResultSet query(String str) {
            ResultSet result = null;
            try {
                Statement stmt = connection.createStatement();
                stmt.execute(str);
                result = stmt.getResultSet();
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

