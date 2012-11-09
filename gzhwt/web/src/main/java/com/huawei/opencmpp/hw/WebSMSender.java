// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WebSMSender.java

package com.huawei.opencmpp.hw;

import com.huawei.insa2.comm.cmpp.message.*;
import com.huawei.insa2.util.*;
import com.huawei.smproxy.SMProxy;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;

// Referenced classes of package demo:
//            Env

public class WebSMSender extends SMProxy
{

    public static WebSMSender getInstance()
    {
        if(instance == null)
            instance = new WebSMSender();
        return instance;
    }

    protected WebSMSender()
    {
        super(arg);
    }

    public void OnTerminate()
    {
        System.out.println("Connection have been breaked! ");
    }

    public CMPPMessage onDeliver(CMPPDeliverMessage msg)
    {
        byte msgId[] = msg.getMsgId();
        if(msg.getRegisteredDeliver() == 1)
        {
            if(String.valueOf(msg.getStat()).equalsIgnoreCase("DELIVRD"))
            {
                System.out.println(String.valueOf(String.valueOf((new StringBuffer("\t\treceived DELIVRD message msgid=[")).append(msg.getMsgId()).append("]"))));
                long submitMsgId = TypeConvert.byte2long(msg.getStatusMsgId());
                PreparedStatement stat = null;
                try
                {
                    CMPPDeliverRepMessage cmppdeliverrepmessage = new CMPPDeliverRepMessage(msgId, 0);
                    return cmppdeliverrepmessage;
                }
                catch(Exception ex)
                {
                    CMPPDeliverRepMessage cmppdeliverrepmessage1 = new CMPPDeliverRepMessage(msgId, 9);
                    return cmppdeliverrepmessage1;
                }
            } else
            {
                return new CMPPDeliverRepMessage(msgId, 9);
            }
        } else
        {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer("\t\treceived non DELIVRD message msgid=[")).append(msg.getMsgId()).append("]"))));
            return new CMPPDeliverRepMessage(msgId, 9);
        }
    }

    public boolean send(CMPPSubmitMessage msg)
    {
        if(msg == null)
            return false;
        CMPPSubmitRepMessage reportMsg = null;
        PreparedStatement stat = null;
        try
        {
            reportMsg = (CMPPSubmitRepMessage)super.send(msg);
            
            if (reportMsg == null) {
            	return false;
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            boolean flag = false;
            return flag;
        }
        return true;
    }

    private static Args arg = Env.getConfig().getArgs("CMPPConnect");
    public static final String service_Id = Env.getConfig().get("CMPPSubmitMessage/service_Id", "WebSM");
    public static final String msg_Src = Env.getConfig().get("CMPPSubmitMessage/msg_Src", "WebSMS");
    public static final String connectCode = Env.getConfig().get("CMPPSubmitMessage/src_Terminal_Id", "");
    private static List msgs = new LinkedList();
    private static WebSMSender instance;

}
