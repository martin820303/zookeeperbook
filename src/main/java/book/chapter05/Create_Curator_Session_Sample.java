package book.chapter05;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Create_Curator_Session_Sample {

	public static void main(String[] args) throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		
		CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 5000,3000,retryPolicy);
		client.start();
		Thread.sleep(Integer.MAX_VALUE);
	}

}
