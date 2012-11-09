// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPExitMessage.java

package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.cngp.CNGPConstant;

// Referenced classes of package com.huawei.insa2.comm.cngp.message:
//            CNGPMessage

public class CNGPExitMessage extends CNGPMessage
{

    public CNGPExitMessage()
    {
        int len = 16;
        super.buf = new byte[len];
        setMsgLength(len);
        setRequestId(6);
    }

    public CNGPExitMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[16];
        if(buf.length != 16)
        {
            throw new IllegalArgumentException(CNGPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, 16);
            return;
        }
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(100);
        strBuf.append("CNGPExitMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(getRequestId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        return strBuf.toString();
    }
}
