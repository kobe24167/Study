package com.aa.zk;

import org.apache.zookeeper.AsyncCallback.VoidCallback;

public class MyVoidCallback implements VoidCallback {

	@Override
	public void processResult(int rc, String path, Object ctx) {
		System.out.println("MyVoidCallback processResult()");
		System.out.println("rc: "+rc);
		System.out.println("path: "+path);
		System.out.println("ctx: "+ctx);
	}

}
