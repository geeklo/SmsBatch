package com.meiyou_tec.smsBatch_plat.cache;

import java.util.TimerTask;

/**
 * 缓存定时任务
 * @author liugeng
 */
public class DataTask extends TimerTask {
	
	private Runnable[] tasks;
	
	@Override
	public void run() {
		for (Runnable r : tasks){
			r.run();
		}
	}

	public Runnable[] getTasks() {
		return tasks;
	}

	public void setTasks(Runnable[] tasks) {
		this.tasks = tasks;
	}
}