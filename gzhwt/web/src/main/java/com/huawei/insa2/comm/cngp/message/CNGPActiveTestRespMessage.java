// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPActiveTestRespMessage.java

package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.cngp.CNGPConstant;

// Referenced classes of package com.huawei.insa2.comm.cngp.message:
//            CNGPMessage

public class CNGPActiveTestRespMessage extends CNGPMessage
{

    public CNGPActiveTestRespMessage(byte buf[])
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
        strBuf.append("CNGPActiveTestRespMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(getRequestId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        return strBuf.toString();
    }
}
