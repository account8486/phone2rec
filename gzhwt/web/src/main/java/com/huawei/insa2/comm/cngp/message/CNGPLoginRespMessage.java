// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPLoginRespMessage.java

package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.cngp.CNGPConstant;

// Referenced classes of package com.huawei.insa2.comm.cngp.message:
//            CNGPMessage

public class CNGPLoginRespMessage extends CNGPMessage
{

    public CNGPLoginRespMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[33];
        if(buf.length != 33)
        {
            throw new IllegalArgumentException(CNGPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            return;
        }
    }

    public byte[] getAuthenticatorServer()
    {
        byte tmpbuf[] = new byte[16];
        System.arraycopy(super.buf, 16, tmpbuf, 0, 16);
        return tmpbuf;
    }

    public byte getVersion()
    {
        return super.buf[32];
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(300);
        strBuf.append("CNGPLoginRespMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(super.buf.length))));
        strBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(getRequestId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        strBuf.append(",AuthenticatorServer=".concat(String.valueOf(String.valueOf(new String(getAuthenticatorServer())))));
        strBuf.append(",Version=".concat(String.valueOf(String.valueOf(getVersion()))));
        return strBuf.toString();
    }
}
