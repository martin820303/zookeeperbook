package book.chapter05;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class GetData_API_Sync_Usage implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk=null;
	private static Stat stat=new Stat();
	public void process(WatchedEvent event) {
		if (KeeperState.SyncConnected == event.getState()) {
			if(EventType.None==event.getType()&&null==event.getPath()){
			connectedSemaphore.countDown();
			}else if(event.getType()==EventType.NodeDataChanged){
				try {
					System.out.println("ReGet Child: "+zk.getChildren(event.getPath(),true));
					System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
				} catch (KeeperException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws Exception {
		String path="/zk-book";
		zk = new ZooKeeper("127.0.0.1:2181", 5000, new GetData_API_Sync_Usage());
		connectedSemaphore.await();
//		zk.delete(path,2);
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println(new String(zk.getData(path, true, stat)));
		System.out.println(stat.getCzxid()+","+stat.getMzxid()+","+stat.getVersion());
		zk.setData(path, "123".getBytes(), -1);
		Thread.sleep(Integer.MAX_VALUE);
	}

}




















