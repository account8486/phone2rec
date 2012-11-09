// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPDeliverMessage.java

package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.util.Calendar;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.cngp.message:
//            CNGPMessage

public class CNGPDeliverMessage extends CNGPMessage
{

    public CNGPDeliverMessage(byte buf[])
        throws IllegalArgumentException
    {
        int len = 90 + (buf[84] & 0xff);
        if(buf.length != len)
        {
            throw new IllegalArgumentException(CNGPConstant.SMC_MESSAGE_ERROR);
        } else
        {
            super.buf = new byte[len];
            System.arraycopy(buf, 0, super.buf, 0, buf.length);
            return;
        }
    }

    public byte[] getMsgId()
    {
        byte msgId[] = new byte[10];
        System.arraycopy(super.buf, 16, msgId, 0, 10);
        return msgId;
    }

    public int getIsReport()
    {
        return super.buf[26];
    }

    public int getMsgFormat()
    {
        return super.buf[27];
    }

    public Date getRecvTime()
    {
        Date date;
        try
        {
            int tmpYear = TypeConvert.byte2int(super.buf, 27);
            byte tmpbyte[] = new byte[2];
            System.arraycopy(super.buf, 31, tmpbyte, 0, 2);
            String tmpstr = new String(tmpbyte);
            int tmpMonth = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 33, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 35, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 37, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMinute = Integer.parseInt(tmpstr);
            System.arraycopy(super.buf, 39, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpSecond = Integer.parseInt(tmpstr);
            Calendar calendar = Calendar.getInstance();
            calendar.set(tmpYear, tmpMonth, tmpDay, tmpHour, tmpMinute, tmpSecond);
            Date date1 = calendar.getTime();
            return date1;
        }
        catch(Exception e)
        {
            date = null;
        }
        return date;
    }

    public String getSrcTermID()
    {
        byte srcTermId[] = new byte[21];
        System.arraycopy(super.buf, 41, srcTermId, 0, 21);
        return (new String(srcTermId)).trim();
    }

    public String getDestTermID()
    {
        byte destTermId[] = new byte[21];
        System.arraycopy(super.buf, 62, destTermId, 0, 21);
        return (new String(destTermId)).trim();
    }

    public int getMsgLength()
    {
        return super.buf[84] & 0xff;
    }

    public String getMsgContent()
    {
        int len = super.buf[84] & 0xff;
        byte content[] = new byte[len];
        System.arraycopy(super.buf, 85, content, 0, len);
        return (new String(content)).trim();
    }

    public int getCongestionState()
    {
        int pos = 89 + (super.buf[84] & 0xff);
        return super.buf[pos];
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(600);
        strBuf.append("CNGPDeliverMessage: ");
        strBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(getRequestId()))));
        strBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        strBuf.append(",Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        strBuf.append(",MsgID=".concat(String.valueOf(String.valueOf(new String(getMsgId())))));
        strBuf.append(",IsReport=".concat(String.valueOf(String.valueOf(getIsReport()))));
        strBuf.append(",MsgFormat=".concat(String.valueOf(String.valueOf(getMsgFormat()))));
        strBuf.append(",RecvTime=".concat(String.valueOf(String.valueOf(getRecvTime()))));
        strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(getSrcTermID()))));
        strBuf.append(",DestTermID=".concat(String.valueOf(String.valueOf(getDestTermID()))));
        strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(new String(getMsgContent())))));
        strBuf.append(",CongestionState=".concat(String.valueOf(String.valueOf(getCongestionState()))));
        return strBuf.toString();
    }
}
