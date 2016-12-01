package book.chapter05;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class AuthSample_Delete {
	final static String PATH="/zk-book-auth_test";
	final static String PATH2="/zk-book-auth_test/child";
public static void main(String[] args)  throws Exception{
	ZooKeeper zooKeeper1 = new ZooKeeper("127.0.0.1:2181", 50000, null);
	zooKeeper1.addAuthInfo("digest", "foo:true".getBytes());
	zooKeeper1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
	zooKeeper1.create(PATH2, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
	
	ZooKeeper zooKeeper2;
	try {
		zooKeeper2 = new ZooKeeper("127.0.0.1:2181", 50000, null);
		zooKeeper2.delete(PATH2, -1);
	} catch (Exception e) {
		// TODO Auto-generated catch block
//		e.printStackTrace();
		System.out.println("删除节点失败");
	}
	ZooKeeper zooKeeper3 = new ZooKeeper("127.0.0.1:2181", 50000, null);
	zooKeeper3.addAuthInfo("digest", "foo:true".getBytes());
	zooKeeper3.delete(PATH2, -1);
	System.out.println("成功删除节点"+PATH2);
	
	ZooKeeper zooKeeper4 = new ZooKeeper("127.0.0.1:2181", 50000, null);
	zooKeeper4.delete(PATH, -1);
	System.out.println("成功删除节点"+PATH);
}
}






















