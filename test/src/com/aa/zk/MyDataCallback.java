package com.aa.zk;

import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.data.Stat;

import com.google.gson.Gson;

public class MyDataCallback implements DataCallback {

	@Override
	public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
		System.out.println("MyDataCallback processResult()");
		System.out.println("rc: "+rc);
		System.out.println("path: "+path);
		System.out.println("ctx: "+ctx);
		System.out.println("data: "+data.toString());
		System.out.println("stat: "+new Gson().toJson(stat));
	}

}
