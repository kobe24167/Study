package com.aa.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZooKeeperFileTester {
	public static void main(String[] args) {
		try {
			test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void test() throws IOException, KeeperException, InterruptedException {
//		ZooKeeper zk = new ZooKeeper("116.196.97.97:2181,116.196.97.97:2182,116.196.97.97:2183", 5000, null, false);
		ZooKeeper zk = new ZooKeeper("116.196.97.97:2181,116.196.97.97:2182,116.196.97.97:2183", 5000, new MyWatcher(), false);
		
		System.out.println(zk);
		if (zk.exists("/event", null) != null) {
			//-1表示当前版本
			zk.delete("/event", -1, new MyVoidCallback(), "obj4");
			deleteAll(zk, "/event");
		}
		
		zk.create("/event", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new MyStringCallback(), "obj1");
//		zk.getChildren("/event", new MyWatcher(), new MyChildrenCallback(), "obj4");
		zk.getChildren("/event", new MyWatcher(), null);
		
		//true 表示使用zk的watcher
		zk.getChildren("/event", true);
//		zk.setData("/event", "root".getBytes(), -1, new MyStatCallback(), "root");
//		zk.getData("/event", new MyWatcher(), new MyDataCallback(), "obj2");
//		System.out.println(new String(zk.getData("/event", new MyWatcher(), null)));
//		zk.exists("/event", new MyWatcher(), new MyStatCallback(), "obj3");
		
		zk.create("/event/event002", "message002".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/event/event001", "message002".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/event/event001/m1", "message002".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/event/event001/m2", "message002".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		
		
		//持续监听
		zk.create("/event/event003", "message003".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.getChildren("/event/event003", new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				try {
					System.out.println("持续监听: "+event.getPath());
					zk.getChildren(event.getPath(), this);
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		zk.create("/event/event003/m1", "message003".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/event/event003/m2", "message003".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/event/event003/m3", "message003".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create("/event/event003/m4", "message003".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.delete("/event/event003/m4", -1);
	}

	private static void deleteAll(ZooKeeper zk, String path) throws KeeperException, InterruptedException {
		List<String> children = zk.getChildren(path, new MyWatcher(), null);
		if (children != null && children.size() > 0) {
			for (String child : children) {
				String childPath = path + "/" + child;
				if (zk.exists(childPath, null) != null) {
					deleteAll(zk, childPath);
				}
			}
		}
		zk.delete(path, -1);
		System.out.println("ZNode deleted: "+path);
	}

}
