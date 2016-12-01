package book.chapter05;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class SetData_API_Sync_Usage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk=null;
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
		}

	}

	public static void main(String[] args) throws Exception {
		String path="/zk-book";
		zk = new ZooKeeper("127.0.0.1:2181", 5000, new SetData_API_Sync_Usage());
		connectedSemaphore.await();
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.getData(path, true, null);
		Stat stat= zk.setData(path, "456".getBytes(), -1);
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
		Stat stat2= zk.setData(path, "456".getBytes(),stat.getVersion());
		System.out.println(stat2.getCzxid()+","+stat2.getMzxid()+","+stat2.getVersion());
		zk.setData(path, "456".getBytes(), -1);
		Thread.sleep(Integer.MAX_VALUE);
	}

}



















