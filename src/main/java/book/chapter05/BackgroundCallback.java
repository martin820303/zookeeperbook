package book.chapter05;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;

public interface BackgroundCallback {
	/**
	 * Called when the async background operation  completes
	 * @param client the client
	 * @param event operation result details
	 * @throws Exception errors
	 */
	public void processResult(CuratorFramework client,CuratorEvent event) throws Exception;
}
