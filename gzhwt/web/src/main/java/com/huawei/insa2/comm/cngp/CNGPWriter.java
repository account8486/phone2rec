// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPWriter.java

package com.huawei.insa2.comm.cngp;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PWriter;
import com.huawei.insa2.comm.cngp.message.CNGPMessage;
import java.io.IOException;
import java.io.OutputStream;

public class CNGPWriter extends PWriter
{

    public CNGPWriter(OutputStream out)
    {
        this.out = out;
    }

    public void write(PMessage message)
        throws IOException
    {
        out.write(((CNGPMessage)message).getBytes());
    }

    public void writeHeartbeat()
        throws IOException
    {
    }

    protected OutputStream out;
}
