// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SGIPWriter.java

package com.huawei.insa2.comm.sgip;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PWriter;
import com.huawei.insa2.comm.sgip.message.SGIPMessage;
import java.io.IOException;
import java.io.OutputStream;

public class SGIPWriter extends PWriter
{

    public SGIPWriter(OutputStream out)
    {
        this.out = out;
    }

    public void write(PMessage message)
        throws IOException
    {
        SGIPMessage msg = (SGIPMessage)message;
        out.write(msg.getBytes());
    }

    public void writeHeartbeat()
        throws IOException
    {
    }

    protected OutputStream out;
}
