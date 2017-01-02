package com.meiyou_tec.util;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;

import com.meiyou_tec.IF.HttpMsg;
import com.meiyou_tec.IF.ServiceIF;

public class MsgAsynSortObserver implements Observer {
	private Logger logger = Logger.getLogger(getClass());
	private TaskExecutor taskExecutor;
	private Queue<HttpMsg> queue;
	private Map<String, ServiceIF> serviceMap;

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setQueue(Queue<HttpMsg> queue) {
		this.queue = queue;
	}

	public void setServiceMap(Map<String, ServiceIF> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public void update(Observable arg0, Object arg1) {
		this.taskExecutor.execute(new Runnable() {
			public void run() {
				HttpMsg obj = null;
				while ((obj = (HttpMsg) queue.poll()) != null) {
					String queueKey = obj.getClass().getName();
					if (serviceMap.containsKey(queueKey)) {
						try {
//							((ServiceIF) serviceMap.get(queueKey)).doProcess(obj);
						} catch (Throwable e) {
							logger.error(e, e);
						}
					} else {
						logger.warn("Err Msg Data:" + obj.getClass().getName() + ":" + obj.toString());
					}
				}
			}
		});
	}
}