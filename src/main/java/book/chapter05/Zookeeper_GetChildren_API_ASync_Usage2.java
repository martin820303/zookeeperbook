package book.chapter05;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.omg.CosNaming.IstringHelper;

public class Zookeeper_GetChildren_API_ASync_Usage2 implements Watcher {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
	private static ZooKeeper zk=null;
	
	public void process(WatchedEvent event)  {
		System.out.println("Receive watched event: " + event);
		if (KeeperState.SyncConnected == event.getState()) {
			if(EventType.None==event.getType()&&null==event.getPath()){
			connectedSemaphore.countDown();
			}else if(event.getType()==EventType.NodeChildrenChanged){
				try {
					System.out.println("ReGet Child: "+zk.getChildren(event.getPath(),true));
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

	public static void main(String[] args)  throws Exception{
		String path="/zk-book";
		zk = new ZooKeeper("127.0.0.1:2181", 5000, new Zookeeper_GetChildren_API_ASync_Usage2());
		connectedSemaphore.await();
		zk.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.create(path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.getChildren(path, true,new IChildren2Callback(),null);
		zk.create(path+"/c2", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		Thread.sleep(Integer.MAX_VALUE);
	}

}

class IChildren2Callback implements AsyncCallback.Children2Callback{

	public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
		
		System.out.println("Get Children znode result: [response code: "+rc+", param path: "+",ctx: "+ctx+",children list: "+children+",stat: "+stat);
	}
	
}
























