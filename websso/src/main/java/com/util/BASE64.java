package com.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 编码解码
 *
 */
public class BASE64 {
        private static Log log = LogFactory.getLog(BASE64.class);

        public BASE64() {
        }

        /**
         * 解码
         *
         * @param str
         *            String
         * @param codeFormat
         *            String 编码方式：UTF-8
         * @return String
         */
        public static String BASE64Decoder(String str, String codeFormat) {
                BASE64Decoder dn = new BASE64Decoder();
                byte[] b = null;
                try {
                        b = dn.decodeBuffer(str);
                        str = new String(b, codeFormat);
                } catch (Exception ex) {
                        log.info("解码不成功");
                }
                return str;

        }

        /**
         * 编码
         *
         * @param str
         *            String
         * @param codeFormat
         *            String 编码方式：UTF-8
         * @return String
         */
        public static String BASE64Encoder(String str, String codeFormat) {
                BASE64Encoder en = new BASE64Encoder();
                String bss = new String();
                try {
                        bss = en.encode(str.getBytes(codeFormat));
                } catch (Exception ex) {
                        log.info("编码不成功！");
                }
                return bss;
        }
}
