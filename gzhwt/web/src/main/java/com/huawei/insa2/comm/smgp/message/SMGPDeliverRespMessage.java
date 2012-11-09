// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SMGPDeliverRespMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPDeliverRespMessage extends SMGPMessage
{

    public SMGPDeliverRespMessage(byte msg_Id[], int status)
        throws IllegalArgumentException
    {
        if(msg_Id.length > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.DELIVER_REPINPUT_ERROR)))).append(":msg_Id").append(SMGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(status < 0)
        {
            throw new IllegalArgumentException(String.valueOf(String.valueOf(SMGPConstant.DELIVER_REPINPUT_ERROR)).concat(":result"));
        } else
        {
            int len = 26;
            super.buf = new byte[len];
            TypeConvert.int2byte(len, super.buf, 0);
            TypeConvert.int2byte(0x80000003, super.buf, 4);
            System.arraycopy(msg_Id, 0, super.buf, 12, msg_Id.length);
            TypeConvert.int2byte(status, super.buf, 22);
            strBuf = new StringBuffer(100);
            strBuf.append(",status=".concat(String.valueOf(String.valueOf(status))));
            return;
        }
    }

    public String toString()
    {
        StringBuffer outStr = new StringBuffer(100);
        outStr.append("SMGPDeliverRespMessage:");
        outStr.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outStr.append(strBuf.toString());
        return outStr.toString();
    }

    public int getRequestId()
    {
        return 0x80000003;
    }

    private StringBuffer strBuf;
}
