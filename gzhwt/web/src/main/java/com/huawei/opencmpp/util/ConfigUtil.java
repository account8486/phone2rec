package com.huawei.opencmpp.util;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * ConfigUtil
 *
 * @author RaymondXiu
 * @version 1.0, 2006-6-24
 * @see
 */
public class ConfigUtil {
    private static Properties props = null;
    
    static{
        if(props == null){
            
            try {
                parse("open-cmpp.xml");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Properties getProps() {
        return this.props;
    }
    
    private static void parse(String filename) throws Exception {
        ConfigParser handler = new ConfigParser();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setValidating(false);

        SAXParser parser = factory.newSAXParser();

        File file = new File(filename);

        URL confURL = file.toURL();
        try {
            parser.parse(confURL.toString(), handler);
            props = handler.getProps();
        } finally {
            factory = null;
            parser = null;
            handler = null;
        }

    }

    public static String getPort(){
        return (String)props.get("port");
    }
    
    public static String getTimeout() {
        return (String)props.get("timeout");
    }
    
    public static String getIsmgId(){
        return (String) props.get("ismg_id");
    }
    
    public static String getMiscId(){
        return (String)props.get("misc_id");
    }
}
