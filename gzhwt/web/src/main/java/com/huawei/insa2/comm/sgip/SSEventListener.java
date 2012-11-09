// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SSEventListener.java

package com.huawei.insa2.comm.sgip;

import java.net.Socket;

public interface SSEventListener
{

    public abstract void onConnect(Socket socket);
}
