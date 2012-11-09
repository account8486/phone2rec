package com.huawei.opencmpp.util;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import java.util.Properties;

/**
 * ConfigParser
 *
 * @author RaymondXiu
 * @version 1.0, 2006-6-24
 * @see
 */
public class ConfigParser extends DefaultHandler {

    private Properties props;

    private String currentSet;
    private String currentName;
    private StringBuffer currentValue = new StringBuffer();

    public ConfigParser() {
        this.props = new Properties();
    }

    public Properties getProps() {
        return this.props;
    }

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        currentValue.delete(0, currentValue.length());
        this.currentName = qName;
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        currentValue.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        props.put(qName.toLowerCase(), currentValue.toString().trim());
    }

}
