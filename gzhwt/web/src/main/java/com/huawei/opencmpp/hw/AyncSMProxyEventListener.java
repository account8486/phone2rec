package com.huawei.opencmpp.hw;

import com.huawei.insa2.comm.cmpp.message.*;

public interface AyncSMProxyEventListener
{

    public abstract void onReceive(CMPPDeliverMessage cmppdelivermessage);

    public abstract void onSubmitSuccess(CMPPSubmitMessage cmppsubmitmessage, CMPPSubmitRepMessage cmppsubmitrepmessage);

    public abstract void onSubmitFailed(CMPPSubmitMessage cmppsubmitmessage, CMPPSubmitRepMessage cmppsubmitrepmessage);
}
