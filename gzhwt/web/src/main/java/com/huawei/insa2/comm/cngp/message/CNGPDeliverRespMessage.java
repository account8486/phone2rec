// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPDeliverRespMessage.java

package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.cngp.CNGPConstant;

// Referenced classes of package com.huawei.insa2.comm.cngp.message:
//            CNGPMessage

public class CNGPDeliverRespMessage extends CNGPMessage
{

    public CNGPDeliverRespMessage(byte msgId[], int congestionState)
        throws IllegalArgumentException
    {
        if(msgId.length > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.DELIVER_REPINPUT_ERROR)))).append(":msg_Id").append(CNGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(congestionState < 0 || congestionState > 255)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.DELIVER_REPINPUT_ERROR)))).append(":congestionState ").append(CNGPConstant.INT_SCOPE_ERROR))));
        } else
        {
            int len = 31;
            super.buf = new byte[31];
            setMsgLength(len);
            setRequestId(0x80000003);
            System.arraycopy(msgId, 0, super.buf, 16, msgId.length);
            super.buf[30] = (byte)congestionState;
            strBuf = new StringBuffer(100);
            strBuf.append(",MsgId=".concat(String.valueOf(String.valueOf(msgId))));
            strBuf.append(",congestionState=".concat(String.valueOf(String.valueOf(congestionState))));
            return;
        }
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(100);
        outStr.append("CNGPDeliverRespMessage:");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(getRequestId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        outStr.append(",SequenceId=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }

    private StringBuffer strBuf;
}
