// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPActiveTestMessage.java

package com.huawei.insa2.comm.cngp.message;


// Referenced classes of package com.huawei.insa2.comm.cngp.message:
//            CNGPMessage

public class CNGPActiveTestMessage extends CNGPMessage
{

    public CNGPActiveTestMessage()
    {
        int len = 16;
        super.buf = new byte[len];
        setMsgLength(len);
        setRequestId(4);
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(100);
        strBuf.append("CNGPActiveTestMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(getRequestId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        return strBuf.toString();
    }
}
