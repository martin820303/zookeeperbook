package book.chapter05;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class Get_Curator_Data_Sample {
	static String path = "/zk-book";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
	public static void main(String[] args) throws Exception {
		client.start();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path,"init".getBytes());
		Stat stat = new Stat();
		System.out.println(stat.getDataLength());
		;
		System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));
		System.out.println(stat.getDataLength());
		Thread.sleep(Integer.MAX_VALUE);
	}

}
