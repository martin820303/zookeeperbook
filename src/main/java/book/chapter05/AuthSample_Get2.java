package book.chapter05;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class AuthSample_Get2 {
	final static String PATH="/zk-book-auth_test";
	public static void main(String[] args)  throws Exception{
		ZooKeeper zooKeeper1 = new ZooKeeper("127.0.0.1:2181", 50000, null);
		zooKeeper1.addAuthInfo("digest", "foo:true".getBytes());
		zooKeeper1.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
		ZooKeeper zooKeeper2 = new ZooKeeper("127.0.0.1:2181", 50000, null);
		zooKeeper2.addAuthInfo("digest", "foo:true".getBytes());
		System.out.println(zooKeeper2.getData(PATH, false	, null));
		ZooKeeper zooKeeper3 = new ZooKeeper("127.0.0.1:2181", 50000, null);
		zooKeeper3.addAuthInfo("digest", "foo:false".getBytes());
		System.out.println(zooKeeper3.getData(PATH, false	, null));
	}
}
