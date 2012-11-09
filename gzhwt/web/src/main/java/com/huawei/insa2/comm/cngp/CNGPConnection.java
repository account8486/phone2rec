// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPConnection.java

package com.huawei.insa2.comm.cngp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cngp.message.CNGPActiveTestMessage;
import com.huawei.insa2.comm.cngp.message.CNGPActiveTestRespMessage;
import com.huawei.insa2.comm.cngp.message.CNGPExitMessage;
import com.huawei.insa2.comm.cngp.message.CNGPLoginMessage;
import com.huawei.insa2.comm.cngp.message.CNGPLoginRespMessage;
import com.huawei.insa2.comm.cngp.message.CNGPMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Resource;
import java.io.*;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.cngp:
//            CNGPWriter, CNGPReader, CNGPTransaction, CNGPConstant

public class CNGPConnection extends PSocketConnection
{

    public CNGPConnection(Args args)
    {
        degree = 0;
        hbnoResponseOut = 3;
        clientid = null;
        loginMode = 0;
        hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        clientid = args.get("clientid", "huawei");
        loginMode = args.get("loginmode", 0);
        version = args.get("version", 1);
        shared_secret = args.get("shared-secret", "");
        CNGPConstant.debug = args.get("debug", false);
        CNGPConstant.initConstant(getResource());
        init(args);
    }

    protected PWriter getWriter(OutputStream out)
    {
        return new CNGPWriter(out);
    }

    protected PReader getReader(InputStream in)
    {
        return new CNGPReader(in);
    }

    public int getChildId(PMessage message)
    {
        CNGPMessage mes = (CNGPMessage)message;
        int sequenceId = mes.getSequenceId();
        if(mes.getRequestId() == 3)
            return -1;
        else
            return sequenceId;
    }

    public PLayer createChild()
    {
        return new CNGPTransaction(this);
    }

    public int getTransactionTimeout()
    {
        return super.transactionTimeout;
    }

    public Resource getResource()
    {
        try
        {
            Resource resource = new Resource(getClass(), "resource");
            return resource;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        Resource resource1 = null;
        return resource1;
    }

    public synchronized void waitAvailable()
    {
        try
        {
            if(getError() == PSocketConnection.NOT_INIT)
                wait(super.transactionTimeout);
        }
        catch(InterruptedException interruptedexception) { }
    }

    public void close()
    {
        try
        {
            CNGPExitMessage msg = new CNGPExitMessage();
            send(msg);
        }
        catch(PException pexception) { }
        super.close();
    }

    protected void heartbeat()
        throws IOException
    {
        CNGPTransaction t = (CNGPTransaction)createChild();
        CNGPActiveTestMessage hbmes = new CNGPActiveTestMessage();
        t.send(hbmes);
        t.waitResponse();
        CNGPActiveTestRespMessage rsp = (CNGPActiveTestRespMessage)t.getResponse();
        if(rsp == null)
        {
            degree++;
            if(degree == hbnoResponseOut)
            {
                degree = 0;
                throw new IOException(CNGPConstant.HEARTBEAT_ABNORMITY);
            }
        } else
        {
            degree = 0;
        }
        t.close();
    }

    protected synchronized void connect()
    {
        super.connect();
        if(!available())
            return;
        CNGPLoginMessage request = null;
        CNGPLoginRespMessage rsp = null;
        try
        {
            request = new CNGPLoginMessage(clientid, shared_secret, 0, new Date(), version);
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
            close();
            setError(CNGPConstant.CONNECT_INPUT_ERROR);
        }
        CNGPTransaction t = (CNGPTransaction)createChild();
        try
        {
            t.send(request);
            PMessage m = super.in.read();
            onReceive(m);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            close();
            setError(String.valueOf(CNGPConstant.LOGIN_ERROR) + String.valueOf(explain(e)));
        }
        rsp = (CNGPLoginRespMessage)t.getResponse();
        if(rsp == null)
        {
            close();
            setError(CNGPConstant.CONNECT_TIMEOUT);
        }
        t.close();
        if(rsp != null && rsp.getStatus() != 0)
        {
            close();
            setError("Fail to login,the status code id ".concat(String.valueOf(String.valueOf(rsp.getStatus()))));
        }
        notifyAll();
    }

    private int degree;
    private int hbnoResponseOut;
    private String clientid;
    private int loginMode;
    private int version;
    private String shared_secret;
}
