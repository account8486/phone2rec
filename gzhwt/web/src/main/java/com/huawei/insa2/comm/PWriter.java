package com.huawei.insa2.comm;

import java.io.IOException;

public abstract class PWriter {
	public abstract void write(PMessage pmessage) throws IOException;
}
