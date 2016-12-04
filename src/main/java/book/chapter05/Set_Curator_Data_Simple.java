package book.chapter05;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class Set_Curator_Data_Simple {
	static String path = "/zk-book";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
	public static void main(String[] args) throws Exception {
		client.start();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path,"init".getBytes());
		Stat stat = new Stat();
		client.getData().storingStatIn(stat).forPath(path);
		System.out.println(new String(client.getData().forPath(path)));
		System.out.println(stat.getVersion());
		System.out.println("Success set node for: "+path+" , new version: "+client.setData().withVersion(stat.getVersion()).forPath(path,"abc".getBytes()).getVersion());
		System.out.println(new String(client.getData().forPath(path)));
//		client.setData().withVersion(stat.getVersion()).forPath(path);
		Thread.sleep(Integer.MAX_VALUE);
	}

}














