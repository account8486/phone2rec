// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SGIPReader.java

package com.huawei.insa2.comm.sgip;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PReader;
import com.huawei.insa2.comm.sgip.message.SGIPBindMessage;
import com.huawei.insa2.comm.sgip.message.SGIPBindRepMessage;
import com.huawei.insa2.comm.sgip.message.SGIPDeliverMessage;
import com.huawei.insa2.comm.sgip.message.SGIPReportMessage;
import com.huawei.insa2.comm.sgip.message.SGIPSubmitRepMessage;
import com.huawei.insa2.comm.sgip.message.SGIPUnbindMessage;
import com.huawei.insa2.comm.sgip.message.SGIPUnbindRepMessage;
import com.huawei.insa2.comm.sgip.message.SGIPUserReportMessage;
import java.io.*;

public class SGIPReader extends PReader
{

    public SGIPReader(InputStream is)
    {
        in = new DataInputStream(is);
    }

    public PMessage read()
        throws IOException
    {
        int total_Length = in.readInt();
        int command_Id = in.readInt();
        byte buf[] = new byte[total_Length - 8];
        in.readFully(buf);
        if(command_Id == 0x80000001)
            return new SGIPBindRepMessage(buf);
        if(command_Id == 1)
            return new SGIPBindMessage(buf);
        if(command_Id == 4)
            return new SGIPDeliverMessage(buf);
        if(command_Id == 0x80000003)
            return new SGIPSubmitRepMessage(buf);
        if(command_Id == 5)
            return new SGIPReportMessage(buf);
        if(command_Id == 17)
            return new SGIPUserReportMessage(buf);
        if(command_Id == 2)
            return new SGIPUnbindMessage(buf);
        if(command_Id == 0x80000002)
            return new SGIPUnbindRepMessage(buf);
        else
            return null;
    }

    protected DataInputStream in;
}
