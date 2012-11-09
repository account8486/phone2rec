// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPSubmitMessage.java

package com.huawei.insa2.comm.cngp.message;

import com.huawei.insa2.comm.cngp.CNGPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.cngp.message:
//            CNGPMessage

public class CNGPSubmitMessage extends CNGPMessage
{

    public CNGPSubmitMessage(String SPId, int subType, int needReport, int priority, String serviceId, String feeType, int feeUserType, 
            String feeCode, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, int destTermIdCount, 
            String destTermId[], int msgLength, String msgContent, int protocolValue)
        throws IllegalArgumentException
    {
        if(SPId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":SPId ").append(CNGPConstant.STRING_NULL))));
        if(SPId.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":SPId ").append(CNGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(subType < 0 || subType > 3)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":subType ").append(CNGPConstant.INT_SCOPE_ERROR))));
        if(needReport < 0 || needReport > 1)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":needReport ").append(CNGPConstant.INT_SCOPE_ERROR))));
        if(priority < 0 || priority > 3)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":priority ").append(CNGPConstant.INT_SCOPE_ERROR))));
        if(serviceId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":serviceId ").append(CNGPConstant.STRING_NULL))));
        if(serviceId.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":serviceId ").append(CNGPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(feeType == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":feeType ").append(CNGPConstant.STRING_NULL))));
        if(feeType.length() > 2)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":feeType ").append(CNGPConstant.STRING_LENGTH_GREAT).append("2"))));
        if(feeUserType < 0 || feeUserType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":feeUserType ").append(CNGPConstant.INT_SCOPE_ERROR))));
        if(feeCode == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":feeCode ").append(CNGPConstant.STRING_NULL))));
        if(feeCode.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":feeCode ").append(CNGPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(msgFormat < 0 || msgFormat > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":msgFormat ").append(CNGPConstant.INT_SCOPE_ERROR))));
        if(srcTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":srcTermId ").append(CNGPConstant.STRING_NULL))));
        if(srcTermId.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":srcTermId ").append(CNGPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(chargeTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":chargeTermId ").append(CNGPConstant.STRING_NULL))));
        if(chargeTermId.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":chargeTermId ").append(CNGPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(destTermIdCount < 0 || destTermIdCount > 100)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":destTermIdCount ").append(CNGPConstant.INT_SCOPE_ERROR))));
        if(destTermId == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":destTermId ").append(CNGPConstant.STRING_NULL))));
        if(msgLength < 0 || msgLength > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":msgLength ").append(CNGPConstant.INT_SCOPE_ERROR))));
        if(msgContent == null)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":msgContent ").append(CNGPConstant.STRING_NULL))));
        if(msgContent.length() > 254)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":msgContent ").append(CNGPConstant.STRING_LENGTH_GREAT).append("254"))));
        if(protocolValue < 0 || protocolValue > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(CNGPConstant.SUBMIT_INPUT_ERROR)))).append(":protocolValue ").append(CNGPConstant.INT_SCOPE_ERROR))));
        byte ms[] = null;
        try
        {
            ms = msgContent.getBytes("gb2312");
        }
        catch(UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
        int len = 132 + 21 * destTermIdCount + ms.length;
        super.buf = new byte[len];
        setMsgLength(len);
        setRequestId(2);
        System.arraycopy(SPId.getBytes(), 0, super.buf, 16, SPId.length());
        super.buf[26] = (byte)subType;
        super.buf[27] = (byte)needReport;
        super.buf[28] = (byte)priority;
        System.arraycopy(serviceId.getBytes(), 0, super.buf, 29, serviceId.length());
        System.arraycopy(feeType.getBytes(), 0, super.buf, 39, feeType.length());
        super.buf[41] = (byte)feeUserType;
        System.arraycopy(feeCode.getBytes(), 0, super.buf, 42, feeCode.length());
        super.buf[48] = (byte)msgFormat;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if(validTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(validTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, 49, 16);
        }
        if(atTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(atTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, 66, 16);
        }
        System.arraycopy(srcTermId.getBytes(), 0, super.buf, 83, srcTermId.length());
        System.arraycopy(chargeTermId.getBytes(), 0, super.buf, 104, chargeTermId.length());
        super.buf[125] = (byte)destTermIdCount;
        int i = 0;
        for(i = 0; i < destTermIdCount; i++)
            System.arraycopy(destTermId[i].getBytes(), 0, super.buf, 126 + i * 21, destTermId[i].length());

        int pos = 126 + 21 * destTermIdCount;
        super.buf[pos] = (byte)ms.length;
        pos++;
        System.arraycopy(ms, 0, super.buf, pos, ms.length);
        pos += ms.length;
        TypeConvert.short2byte(256, super.buf, pos);
        pos += 2;
        TypeConvert.short2byte(1, super.buf, pos);
        pos += 2;
        super.buf[pos] = (byte)protocolValue;
        strBuf = new StringBuffer(600);
        strBuf.append(",SPId=".concat(String.valueOf(String.valueOf(SPId))));
        strBuf.append(",subType=".concat(String.valueOf(String.valueOf(subType))));
        strBuf.append(",NeedReport=".concat(String.valueOf(String.valueOf(needReport))));
        strBuf.append(",Priority=".concat(String.valueOf(String.valueOf(priority))));
        strBuf.append(",ServiceID=".concat(String.valueOf(String.valueOf(serviceId))));
        strBuf.append(",FeeType=".concat(String.valueOf(String.valueOf(feeType))));
        strBuf.append(",feeUserType=".concat(String.valueOf(String.valueOf(feeUserType))));
        strBuf.append(",FeeCode=".concat(String.valueOf(String.valueOf(feeCode))));
        strBuf.append(",msgFormat=".concat(String.valueOf(String.valueOf(msgFormat))));
        strBuf.append(",validTime=".concat(String.valueOf(String.valueOf(validTime))));
        strBuf.append(",atTime=".concat(String.valueOf(String.valueOf(atTime))));
        strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(srcTermId))));
        strBuf.append(",ChargeTermID=".concat(String.valueOf(String.valueOf(chargeTermId))));
        strBuf.append(",DestTermIDCount=".concat(String.valueOf(String.valueOf(destTermIdCount))));
        for(int t = 0; t < destTermIdCount; t++)
            strBuf.append(String.valueOf(String.valueOf((new StringBuffer(",DestTermID[")).append(t).append("]=").append(destTermId[t]))));

        strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(ms.length))));
        strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(msgContent))));
        strBuf.append(",protocolValue=".concat(String.valueOf(String.valueOf(protocolValue))));
    }

    public String toString()
    {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("CNGPSubmitMessage: ");
        outBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        outBuf.append(",RequestID=".concat(String.valueOf(String.valueOf(getRequestId()))));
        outBuf.append(",Status=".concat(String.valueOf(String.valueOf(getStatus()))));
        outBuf.append(",SequenceID=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        if(strBuf != null)
            outBuf.append(strBuf.toString());
        return outBuf.toString();
    }

    private StringBuffer strBuf;
}
