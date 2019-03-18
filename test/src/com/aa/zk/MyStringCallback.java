package com.aa.zk;

import org.apache.zookeeper.AsyncCallback.StringCallback;

public class MyStringCallback implements StringCallback{

	@Override
	public void processResult(int rc, String path, Object ctx, String name) {
		
		System.out.println("MyStringCallback processResult()");
		System.out.println("rc: "+rc);
		System.out.println("path: "+path);
		System.out.println("ctx: "+ctx);
		System.out.println("name: "+name);
	}

}
