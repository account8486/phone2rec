// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Env.java

package com.huawei.opencmpp.hw;

import com.huawei.insa2.util.Cfg;
import com.huawei.insa2.util.Resource;
import java.io.IOException;

public class Env
{

    public Env()
    {
    }

    public static Cfg getConfig()
    {
        if(config == null)
            try
            {
                config = new Cfg("app.xml");
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        return config;
    }

    public static Resource getResource()
    {
        if(resource == null)
            try
            {
                resource = new Resource("resource");
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        return resource;
    }

    static Cfg config;
    static Resource resource;
}
