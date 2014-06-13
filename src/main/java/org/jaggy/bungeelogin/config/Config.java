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
import java.util.logging.Logger;
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
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
    public void createConfig(String file) {
        cFile = new File(cFolder.toString()+ File.separator + file);
        if (!cFile.exists()) {
            try {
                cFile.createNewFile();
            } catch (IOException e) {
            }
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
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
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
        return lconfig.getString("AuthType", "xAuth");
    }

    @Override
    public String getMysqlDbName() {
        return lconfig.getString("MysqlDbName", "bungecord");
    }

    @Override
    public String getMysqlUsername() {
        return lconfig.getString("MysqlUserName", "root");
    }

    @Override
    public String getMysqlPassword() {
        return lconfig.getString("MysqlPassword", "");
    }

    @Override
    public String getMysqlHost() {
        return lconfig.getString("MysqlHost", "localhost");
    }

    @Override
    public Integer getMysqlPort() {
        return lconfig.getInt("MysqlPort", 3306);
    }

    @Override
    public String getUsernameField() {
        return lconfig.getString("UsernameField", "username");
    }

    @Override
    public String getPasswordField() {
        return lconfig.getString("PasswordField", "password");
    }

    @Override
    public String getPasswordType() {
        return lconfig.getString("PasswordType", "");
    }

    @Override
    public String getServerToken() {
        return lconfig.getString("ServerToken", "");
    }

    @Override
    public String getAuthURL() {
        return lconfig.getString("AuthURL", "");
    }

    @Override
    public void loadConfig(String file) {
        lconfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }
}
