/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jaggy.bungeelogin.BungeeLogin;

/**
 *
 * @author Matthew
 */
public class Config implements YAMLConfig {

    /**
     * config stored in memory
     */
    public static Configuration    lconfig;
    public File                    cFile;
    private final BungeeLogin plugin;
    private static File cFolder;


    /**
     * Initialize Config Class
     * @param lplugin
     * @param file
     */
    public Config(BungeeLogin lplugin, String file) {
        plugin = lplugin;
        cFolder = plugin.getDataFolder();
        cFile = new File(cFolder.toString()+ File.separator + file);
        try {
        lconfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(cFile);
        } catch (IOException e) {
            
        }
    }
    @Override
        public void loadConfig() {
        
        if(!cFolder.exists()) {
            cFolder.mkdir();
        }
        if(!cFile.exists()) {
            try {
                cFile.createNewFile();
            } catch (IOException ex) {
                plugin.log.log(Level.SEVERE, null, ex);
            }
            
        }
        cFolder = plugin.getDataFolder();
        cFile = new File(cFolder.toString()+ File.separator + "config.yml");
        try {
        lconfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(cFile);
        } catch (IOException e) {
            
        }
   
    }

    @Override
    public void saveConfig() {
        try
        {
            FileWriter writer = new FileWriter( cFile.toString());
            ConfigurationProvider.getProvider(YamlConfiguration.class).save( lconfig, writer );
        } catch (IOException ex) {
            plugin.log.severe(cFile.toString());
        }
    }


    @Override
    public void createConfig(String file, String data) {
        cFile = new File(cFolder.toString()+ File.separator + file);
        if (!cFile.exists()) {
            try {
                cFile.createNewFile();
                 FileWriter fw = new FileWriter(cFile);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(data);
            out.close();
            fw.close();
            } catch (IOException e) {
                plugin.log.log(Level.SEVERE, null, e);
            }
        } else {
            
            try {
                FileWriter fw;
                fw = new FileWriter(cFile);
                BufferedWriter out = new BufferedWriter(fw);
                out.write(data);
                out.close();
                fw.close();
            } catch (IOException ex) {
                plugin.log.log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    public Boolean getOnlineMode() {
        boolean value;
        value = lconfig.getBoolean("onlineMode", true);
        return value;
    }

    @Override
    public String getAuthType() {
        String value;
        value = lconfig.getString("AuthType", "Mysql");
        return value;
    }

    @Override
    public String getMysqlDbName() {
        String value = lconfig.getString("MysqlDbName", "bungecord");
        return value;
    }

    @Override
    public String getMysqlUsername() {
        String value = lconfig.getString("MysqlUserName", "root");
        return value;
    }

    @Override
    public String getMysqlPassword() {
        String value = lconfig.getString("MysqlPassword", "");
        return value;
    }

    @Override
    public String getMysqlHost() {
        String value = lconfig.getString("MysqlHost", "localhost");
        return value;
    }

    @Override
    public Integer getMysqlPort() {
        int value = lconfig.getInt("MysqlPort", 3306);
        return value;
    }

    @Override
    public String getUsernameField() {
        String value = lconfig.getString("UsernameField", "username");
        return value;
    }

    @Override
    public String getPasswordField() {
        String value = lconfig.getString("PasswordField", "password");
        return value;
    }

    @Override
    public String getPasswordType() {
        String value = lconfig.getString("PasswordType", "");
        return value;
    }

    @Override
    public String getServerToken() {
        String value = lconfig.getString("ServerToken", "");
        return value;
    }

    @Override
    public String getAuthURL() {
        String value = lconfig.getString("AuthURL", "");
        return value;
    }

    @Override
    public void loadConfig(String file) {
        lconfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }
}
