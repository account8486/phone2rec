// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CMPP30SubmitRepMessage.java

package com.huawei.insa2.comm.cmpp30.message;

import com.huawei.insa2.comm.cmpp.CMPPConstant;
import com.huawei.insa2.comm.cmpp.message.CMPPMessage;
import com.huawei.insa2.util.TypeConvert;

public class CMPP30SubmitRepMessage extends CMPPMessage
{

    public CMPP30SubmitRepMessage(byte buf[])
        throws IllegalArgumentException
    {
        super.buf = new byte[16];
        if(buf.length != 16)
        {
            throw new IllegalArgumentException(CMPPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            System.arraycopy(buf, 0, super.buf, 0, 16);
            super.sequence_Id = TypeConvert.byte2int(super.buf, 0);
            return;
        }
    }

    public byte[] getMsgId()
    {
        byte tmpMsgId[] = new byte[8];
        System.arraycopy(super.buf, 4, tmpMsgId, 0, 8);
        return tmpMsgId;
    }

    public int getResult()
    {
        return TypeConvert.byte2int(super.buf, 12);
    }

    public String toString()
    {
        String tmpStr = "CMPP_Submit_REP: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",MsgId=").append(new String(getMsgId()))));
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append(",Result=").append(getResult())));
        return tmpStr;
    }

    public int getCommandId()
    {
        return 0x80000004;
    }
}
