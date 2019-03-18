package com.aa.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class MyWatcher implements Watcher {

	@Override
	public void process(WatchedEvent event) {
		System.out.println("MyWatcher process(WatchedEvent event)");
		System.out.println("event.getPath: "+event.getPath());
		System.out.println("event.getState: "+event.getState());
		System.out.println("event.getType: "+event.getType());
	}

}
