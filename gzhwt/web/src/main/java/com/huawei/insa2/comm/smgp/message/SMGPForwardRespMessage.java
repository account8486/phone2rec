// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SMGPForwardRespMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPForwardRespMessage extends SMGPMessage
{

    public SMGPForwardRespMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[18];
        if(buf.length != 18)
        {
            throw new IllegalArgumentException(SMGPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, 18);
            super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
            return;
        }
    }

    public byte[] getMsgId()
    {
        byte tmpMsgId[] = new byte[10];
        System.arraycopy(super.buf, 4, tmpMsgId, 0, 10);
        return tmpMsgId;
    }

    public int getStatus()
    {
        return TypeConvert.byte2int(super.buf, 14);
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(200);
        strBuf.append("SMGPForwardRespMessage: ");
        strBuf.append("Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        strBuf.append(",MsgId=".concat(String.valueOf(String.valueOf(new String(getMsgId())))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        return strBuf.toString();
    }

    public int getRequestId()
    {
        return 0x80000005;
    }
}
