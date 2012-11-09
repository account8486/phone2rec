package com.huawei.insa2.util;

public abstract class WatchThread extends Thread {

	public WatchThread(String name) {
		super(tg, name);
		alive = true;
		state = "unknown";
		setDaemon(true);
	}

	public void kill() {
		alive = false;
	}

	public final void run() {
		while (alive)
			try {
				task();
			} catch (Exception ex) {
				ex.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			}
	}

	protected void setState(String newState) {
		state = newState;
	}

	public String getWatchState() {
		return state;
	}

	protected abstract void task();

	private boolean alive;

	private String state;

	public static final ThreadGroup tg = new ThreadGroup("watch-thread");

}
