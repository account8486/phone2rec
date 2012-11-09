// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SGIPConnection.java

package com.huawei.insa2.comm.sgip;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.sgip.message.SGIPBindMessage;
import com.huawei.insa2.comm.sgip.message.SGIPBindRepMessage;
import com.huawei.insa2.comm.sgip.message.SGIPMessage;
import com.huawei.insa2.comm.sgip.message.SGIPUnbindMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Resource;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

// Referenced classes of package com.huawei.insa2.comm.sgip:
//            SGIPSocketConnection, SGIPWriter, SGIPReader, SGIPTransaction, 
//            SGIPConstant

public class SGIPConnection extends SGIPSocketConnection
{

    public SGIPConnection(Args args, boolean ifasClient, HashMap connmap)
    {
        degree = 0;
        hbnoResponseOut = 3;
        source_addr = null;
        hbnoResponseOut = args.get("heartbeat-noresponseout", 3);
        source_addr = args.get("source-addr", "huawei");
        version = args.get("version", 1);
        shared_secret = args.get("shared-secret", "");
        SGIPConstant.debug = args.get("debug", false);
        login_name = args.get("login-name", "");
        login_pass = args.get("login-pass", "");
        src_nodeid = args.get("source-addr", 0);
        this.connmap = connmap;
        SGIPConstant.initConstant(getResource());
        asClient = ifasClient;
        if(asClient)
            init(args);
    }

    public synchronized void attach(Args args, Socket socket)
    {
        if(asClient)
        {
            throw new UnsupportedOperationException("Client socket can not accept connection");
        } else
        {
            init(args, socket);
            ipaddr = socket.getInetAddress().getHostAddress();
            port = socket.getPort();
            return;
        }
    }

    protected void onReadTimeOut()
    {
        close();
    }

    protected PWriter getWriter(OutputStream out)
    {
        return new SGIPWriter(out);
    }

    protected PReader getReader(InputStream in)
    {
        return new SGIPReader(in);
    }

    public int getChildId(PMessage message)
    {
        SGIPMessage mes = (SGIPMessage)message;
        int sequenceId = mes.getSequenceId();
        if(!asClient);
        if(mes.getCommandId() == 4 || mes.getCommandId() == 5 || mes.getCommandId() == 17 || mes.getCommandId() == 1 || mes.getCommandId() == 2)
            return -1;
        else
            return sequenceId;
    }

    public PLayer createChild()
    {
        return new SGIPTransaction(this);
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
            if(getError() == SGIPSocketConnection.NOT_INIT)
                wait(super.transactionTimeout);
        }
        catch(InterruptedException interruptedexception) { }
    }

    public void close()
    {
        SGIPTransaction t = (SGIPTransaction)createChild();
        t.setSPNumber(src_nodeid);
        Date nowtime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
        String tmpTime = dateFormat.format(nowtime);
        Integer timestamp = new Integer(tmpTime);
        t.setTimestamp(timestamp.intValue());
        try
        {
            SGIPUnbindMessage msg = new SGIPUnbindMessage();
            t.send(msg);
            t.waitResponse();
            SGIPMessage sgipmessage = t.getResponse();
        }
        catch(PException pexception) { }
        finally
        {
            t.close();
        }
        super.close();
        if(!asClient && connmap != null)
            connmap.remove(new String(String.valueOf(ipaddr) + String.valueOf(port)));
    }

    protected void heartbeat()
        throws IOException
    {
    }

    protected synchronized void connect()
    {
        super.connect();
        if(!available())
            return;
        SGIPBindMessage request = null;
        SGIPBindRepMessage rsp = null;
        try
        {
            request = new SGIPBindMessage(1, login_name, login_pass);
        }
        catch(IllegalArgumentException e)
        {
            e.printStackTrace();
            close();
            setError(SGIPConstant.CONNECT_INPUT_ERROR);
        }
        SGIPTransaction t = (SGIPTransaction)createChild();
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
            setError(String.valueOf(SGIPConstant.LOGIN_ERROR) + String.valueOf(explain(e)));
        }
        rsp = (SGIPBindRepMessage)t.getResponse();
        if(rsp == null)
        {
            close();
            setError(SGIPConstant.CONNECT_TIMEOUT);
        }
        t.close();
        if(rsp != null && rsp.getResult() != 0)
        {
            close();
            if(rsp.getResult() == 1)
                setError(SGIPConstant.NONLICETSP_LOGNAME);
            else
                setError(SGIPConstant.OTHER_ERROR);
        }
        notifyAll();
    }

    private int degree;
    private int hbnoResponseOut;
    private String source_addr;
    private int version;
    private String shared_secret;
    private boolean asClient;
    private String login_name;
    private String login_pass;
    private int src_nodeid;
    private String ipaddr;
    private int port;
    private HashMap connmap;
}
