package com.aa.zk;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.Stat;

import com.google.gson.Gson;

public class MyStatCallback implements StatCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		System.out.println("MyStatCallback processResult()");
		System.out.println("rc: "+rc);
		System.out.println("path: "+path);
		System.out.println("ctx: "+ctx);
		System.out.println("stat: "+new Gson().toJson(stat));
	}

}
