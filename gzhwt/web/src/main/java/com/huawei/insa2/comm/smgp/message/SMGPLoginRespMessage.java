// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SMGPLoginRespMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPLoginRespMessage extends SMGPMessage
{

    public SMGPLoginRespMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[25];
        if(buf.length != 25)
        {
            throw new IllegalArgumentException(SMGPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
            return;
        }
    }

    public int getStatus()
    {
        return TypeConvert.byte2int(super.buf, 4);
    }

    public byte[] getAuthenticatorServer()
    {
        byte tmpbuf[] = new byte[16];
        System.arraycopy(super.buf, 8, tmpbuf, 0, 16);
        return tmpbuf;
    }

    public byte getVersion()
    {
        return super.buf[24];
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(300);
        strBuf.append("SMGPLoginRespMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(super.buf.length))));
        strBuf.append(",RequestID=-2147483647");
        strBuf.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",AuthenticatorServer=".concat(String.valueOf(String.valueOf(new String(getAuthenticatorServer())))));
        strBuf.append(",Version=".concat(String.valueOf(String.valueOf(getVersion()))));
        return strBuf.toString();
    }

    public int getRequestId()
    {
        return 0x80000001;
    }
}
