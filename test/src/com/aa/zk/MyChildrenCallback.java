package com.aa.zk;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;

public class MyChildrenCallback implements ChildrenCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, List<String> children) {
		System.out.println("MyChildrenCallback processResult()");
		System.out.println("rc: "+rc);
		System.out.println("path: "+path);
		System.out.println("ctx: "+ctx);
		System.out.println("children: ");
		int i = 0;
		for (String child : children) {
			System.out.println("child"+i+": "+child);
		}
	}

}
