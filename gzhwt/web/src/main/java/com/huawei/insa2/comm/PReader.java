package com.huawei.insa2.comm;

import java.io.IOException;

public abstract class PReader {
	public abstract PMessage read() throws IOException;
}
