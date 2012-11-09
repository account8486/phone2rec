// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CNGPTransaction.java

package com.huawei.insa2.comm.cngp;

import com.huawei.insa2.comm.*;
import com.huawei.insa2.comm.cngp.message.CNGPMessage;
import com.huawei.insa2.util.Debug;

// Referenced classes of package com.huawei.insa2.comm.cngp:
//            CNGPConnection, CNGPConstant

public class CNGPTransaction extends PLayer
{

    public CNGPTransaction(PLayer connection)
    {
        super(connection);
        sequenceId = super.id;
    }

    public synchronized void onReceive(PMessage msg)
    {
        receive = (CNGPMessage)msg;
        sequenceId = receive.getSequenceId();
        if(CNGPConstant.debug)
            Debug.dump(receive.toString());
        notifyAll();
    }

    public void send(PMessage message)
        throws PException
    {
        CNGPMessage msg = (CNGPMessage)message;
        msg.setSequenceId(sequenceId);
        super.parent.send(message);
        if(CNGPConstant.debug)
            Debug.dump(msg.toString());
    }

    public CNGPMessage getResponse()
    {
        return receive;
    }

    public synchronized void waitResponse()
    {
        if(receive == null)
            try
            {
                wait(((CNGPConnection)super.parent).getTransactionTimeout());
            }
            catch(InterruptedException interruptedexception) { }
    }

    private CNGPMessage receive;
    private int sequenceId;
}
