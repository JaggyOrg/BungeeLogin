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

package org.jaggy.bungeelogin.config;

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
    public void loadConfig();
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
    /**
     * Gets Online mode
     *  @return OnlineMode
     */
    public Boolean getOnlineMode();
    /**
     * Gets Auth Type: Mysql, xAuth
     * @return AuthType
     */
    public String getAuthType();
    /**
     * Gets Mysql DB Name
     * @return MysqlDbName
     */
    public String getMysqlDbName();
    /**
     * Gets Mysql Username
     * @return MysqlUsername
     */
    public String getMysqlUsername();
    /**
     * Gets Mysql Username
     * @return MysqlPassword
     */
    public String getMysqlPassword();
    /**
     * Gets Mysql Host
     * @return MysqlHost
     */
    public String getMysqlHost();
    /**
     * Gets Mysql Port
     * @return MysqlPort
     */
    public Integer getMysqlPort();
    /**
     * Gets Username Field to query
     * @return UsernameField
     */
    public String getUsernameField();
    /**
     * Gets Password Field to query
     * @return PasswordField
     */
    public String getPasswordField();
    /**
     * Gets Password Hash Type
     * @return PasswordType
     */
    public String getPasswordType();
    /**
     * Gets Server Token used in authentication like in xAuth
     * @return ServerToken
     */
    public String getServerToken();
    /**
     * Gets Auth URL used in authentication like in xAuth
     * @return PasswordType
     */
    public String getAuthURL();
    /**
     * Gets the mail send from info from config.yml
     * @return 
     */
    public String getMailFrom();
    /**
     * Gets the mail server to send from in the config.yml
     * @return 
     */
    public String getMailServer();
    /**
     * Gets the mail Usernane from config.yml
     * @return 
     */
    public String getMailUsername();
    /**
     * Gets the mail password from config.yml
     * @return 
     */
    public String getMailPassword();
}
