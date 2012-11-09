/**
 * 
 */
package com.wondertek.meeting.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * @author rain
 *
 */
public class Configurations {
	private static PropertiesConfiguration config;

    static {
        config = null;
        try {
            config = new PropertiesConfiguration("sys-config.properties");
            config.setReloadingStrategy(new FileChangedReloadingStrategy());
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("apkSaveFileName=" + Configurations.getString("apkSaveFileName"));
	}
	
	public static String getString(final String propertyName) {
		return config == null ? "" : config.getString(propertyName, "");
	}
}
