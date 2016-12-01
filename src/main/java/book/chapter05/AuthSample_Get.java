package book.chapter05;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class AuthSample_Get {
	final static String PATH="/zk-book-auth_test";
	public static void main(String[] args)  throws Exception{
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 50000, null);
		zooKeeper.addAuthInfo("digest", "foo:true".getBytes());
		zooKeeper.create(PATH, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
	}
}
