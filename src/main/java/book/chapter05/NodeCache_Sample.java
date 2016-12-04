package book.chapter05;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class NodeCache_Sample {
	static String path = "/zk-book";
	static CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
	
	public static void main(String[] args) throws Exception{
		client.start();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, "init".getBytes());
		final NodeCache cache = new NodeCache(client, path,false);
		cache.start(true);
		cache.getListenable().addListener(new NodeCacheListener() {
			
			public void nodeChanged() throws Exception {
				System.out.println("Node data update, new data: "+new String(cache.getCurrentData().getData()));
			}
		});
		client.setData().forPath(path, "u".getBytes());
		Thread.sleep(1000);
		client.setData().forPath(path, "u2".getBytes());
//		Thread.sleep(10000);
		client.delete().deletingChildrenIfNeeded().forPath(path);
//		Thread.sleep(Integer.MAX_VALUE);
	}

}



























