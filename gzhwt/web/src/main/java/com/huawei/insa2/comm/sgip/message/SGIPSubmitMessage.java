// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SGIPSubmitMessage.java

package com.huawei.insa2.comm.sgip.message;

import com.huawei.insa2.comm.sgip.SGIPConstant;
import com.huawei.insa2.util.TypeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.sgip.message:
//            SGIPMessage

public class SGIPSubmitMessage extends SGIPMessage
{

    public SGIPSubmitMessage(String SPNumber, String ChargeNumber, String UserNumber[], String CorpId, String ServiceType, int FeeType, String FeeValue, 
            String GivenValue, int AgentFlag, int MorelatetoMTFlag, int Priority, Date ExpireTime, Date ScheduleTime, int ReportFlag, 
            int TP_pid, int TP_udhi, int MessageCoding, int MessageType, int MessageLen, byte MessageContent[], String reserve)
        throws IllegalArgumentException
    {
        if(SPNumber.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":SPNumber").append(SGIPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(ChargeNumber.length() > 21)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":ChargeNumber").append(SGIPConstant.STRING_LENGTH_GREAT).append("21"))));
        if(UserNumber.length > 100)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":UserNumber").append(SGIPConstant.STRING_LENGTH_GREAT).append("100"))));
        for(int i = 0; i < UserNumber.length; i++)
            if(UserNumber[i].length() > 21)
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":UserNumber[").append(i).append("]").append(SGIPConstant.STRING_LENGTH_GREAT).append("21"))));

        if(CorpId.length() > 5)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":CorpId").append(SGIPConstant.STRING_LENGTH_GREAT).append("5"))));
        if(ServiceType.length() > 10)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":ServiceType").append(SGIPConstant.STRING_LENGTH_GREAT).append("10"))));
        if(FeeType < 0 || FeeType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeType").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(FeeValue.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeValue").append(SGIPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(GivenValue.length() > 6)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":GivenValue").append(SGIPConstant.STRING_LENGTH_GREAT).append("6"))));
        if(AgentFlag < 0 || AgentFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":AgentFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MorelatetoMTFlag < 0 || MorelatetoMTFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MorelatetoMTFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MorelatetoMTFlag < 0 || MorelatetoMTFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MorelatetoMTFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(Priority < 0 || Priority > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":Priority").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(ReportFlag < 0 || ReportFlag > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":ReportFlag").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(TP_pid < 0 || TP_pid > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":TP_pid").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(TP_udhi < 0 || TP_udhi > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":TP_udhi").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageCoding < 0 || MessageCoding > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageCoding").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageType < 0 || MessageType > 255)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageType").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageLen < 0 || MessageLen > 160)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageLen").append(SGIPConstant.INT_SCOPE_ERROR))));
        if(MessageContent.length > 160)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":MessageContent").append(SGIPConstant.STRING_LENGTH_GREAT).append("160"))));
        if(reserve.length() > 8)
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SGIPConstant.SUBMIT_INPUT_ERROR)))).append(":reserve").append(SGIPConstant.STRING_LENGTH_GREAT).append("8"))));
        int len = 143 + 21 * UserNumber.length + MessageLen;
        super.buf = new byte[len];
        TypeConvert.int2byte(len, super.buf, 0);
        TypeConvert.int2byte(3, super.buf, 4);
        System.arraycopy(SPNumber.getBytes(), 0, super.buf, 20, SPNumber.length());
        System.arraycopy(ChargeNumber.getBytes(), 0, super.buf, 41, ChargeNumber.length());
        super.buf[62] = (byte)UserNumber.length;
        int i = 0;
        for(i = 0; i < UserNumber.length; i++)
            System.arraycopy(UserNumber[i].getBytes(), 0, super.buf, 63 + i * 21, UserNumber[i].length());

        int loc = 63 + i * 21;
        System.arraycopy(CorpId.getBytes(), 0, super.buf, loc, CorpId.length());
        loc += 5;
        System.arraycopy(ServiceType.getBytes(), 0, super.buf, loc, ServiceType.length());
        loc += 10;
        super.buf[loc++] = (byte)FeeType;
        System.arraycopy(FeeValue.getBytes(), 0, super.buf, loc, FeeValue.length());
        loc += 6;
        System.arraycopy(GivenValue.getBytes(), 0, super.buf, loc, GivenValue.length());
        loc += 6;
        super.buf[loc++] = (byte)AgentFlag;
        super.buf[loc++] = (byte)MorelatetoMTFlag;
        super.buf[loc++] = (byte)Priority;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        if(ExpireTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(ExpireTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, loc, 16);
        }
        loc += 16;
        if(ScheduleTime != null)
        {
            String tmpTime = String.valueOf(String.valueOf(dateFormat.format(ScheduleTime))).concat("032+");
            System.arraycopy(tmpTime.getBytes(), 0, super.buf, loc, 16);
        }
        loc += 16;
        super.buf[loc++] = (byte)ReportFlag;
        super.buf[loc++] = (byte)TP_pid;
        super.buf[loc++] = (byte)TP_udhi;
        super.buf[loc++] = (byte)MessageCoding;
        super.buf[loc++] = (byte)MessageType;
        TypeConvert.int2byte(MessageLen, super.buf, loc);
        loc += 4;
        System.arraycopy(MessageContent, 0, super.buf, loc, MessageLen);
        loc += MessageLen;
        System.arraycopy(reserve.getBytes(), 0, super.buf, loc, reserve.length());
        outStr = ",SPNumber=".concat(String.valueOf(String.valueOf(SPNumber)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ChargeNumber=").append(ChargeNumber)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",UserCount=").append(UserNumber.length)));
        for(int t = 0; t < UserNumber.length; t++)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",UserNumber[").append(t).append("]=").append(UserNumber[t])));

        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",CorpId=").append(CorpId)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ServiceType=").append(ServiceType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",FeeType=").append(FeeType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",FeeValue=").append(FeeValue)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",GivenValue=").append(GivenValue)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",AgentFlag=").append(AgentFlag)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MorelatetoMTFlag=").append(MorelatetoMTFlag)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",Priority=").append(Priority)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ExpireTime=").append(ExpireTime)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ScheduleTime=").append(ScheduleTime)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ReportFlag=").append(ReportFlag)));
        if(ExpireTime != null)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ExpireTime=").append(dateFormat.format(ExpireTime))));
        else
            outStr = String.valueOf(String.valueOf(outStr)).concat(",ExpireTime=null");
        if(ScheduleTime != null)
            outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ScheduleTime=").append(dateFormat.format(ScheduleTime))));
        else
            outStr = String.valueOf(String.valueOf(outStr)).concat(",ScheduleTime=null");
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",ReportFlag=").append(ReportFlag)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",TP_pid=").append(TP_pid)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",TP_udhi=").append(TP_udhi)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageCoding=").append(MessageCoding)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageType=").append(MessageType)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageLength=").append(MessageLen)));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",MessageContent=").append(new String(MessageContent))));
        outStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(outStr)))).append(",reserve=").append(reserve)));
    }

    public String toString()
    {
        String tmpStr = "SGIP_Submit: ";
        tmpStr = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(tmpStr)))).append("Sequence_Id=").append(getSequenceId())));
        tmpStr = String.valueOf(tmpStr) + String.valueOf(outStr);
        return tmpStr;
    }

    public int getCommandId()
    {
        return 3;
    }

    private String outStr;
}
