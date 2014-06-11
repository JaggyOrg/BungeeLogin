/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.config;

import java.io.File;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

/**
 *
 * @author Matthew
 */
public interface YAMLConfig {
    /**
     * Loads a local config file
     * @param file 
     */
    public void loadConfig(String file);

    /**
     * Creates an empty config file.
     * @param file
     */
    public void createConfig(String file);
    /**
     * Creates a config file with data.
     * @param file
     * @param data 
     */
    public void createConfig(String file, String data);
    /**
     * Saves the loaded config file
     */
    public void saveConfig();
}
