/**
 * 
 */
package nz.ac.vuw.swen301.a2.client;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Claire
 *
 */
public class Resthome4LogsAppender extends AppenderSkeleton {

	private final CloseableHttpClient httpClient;
	
	private String logServiceURL = "http://localhost:8080/resthome4logs/logs";
	
	public Resthome4LogsAppender() {
		super();
		httpClient = HttpClients.createDefault();
		this.setLayout(new JSONLayout());
	}
	
	public void close() {
		try {
			httpClient.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		
		try {
			String toWrite = this.getLayout().format(event);
			
			HttpPost post = new HttpPost(logServiceURL);
			post.setHeader("Content-Type", "application/json");
			StringEntity jsonEntity = new StringEntity(toWrite);
			post.setEntity(jsonEntity);
			
			HttpResponse resp = httpClient.execute(post);
			
			post.releaseConnection();
			
			if(resp.getStatusLine().getStatusCode() == 400) {
				throw new Error("The layout given is not suitable for this server");
			} else if(resp.getStatusLine().getStatusCode() == 409) {
				System.err.println("Received 409... writing the same log from elsewhere?");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			System.err.println("Exception writing to server: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Exception writing to server: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setLayout(Layout layout) {
		if(!(layout instanceof JSONLayout)) {
			System.err.println("Using this appender without the companion layout is not recommended.");
		}
		super.setLayout(layout);
	}

	/**
	 * @return the logServiceURL
	 */
	public String getLogServiceURL() {
		return logServiceURL;
	}

	/**
	 * @param logServiceURL the logServiceURL to set
	 */
	public void setLogServiceURL(String logServiceURL) {
		this.logServiceURL = logServiceURL;
	}

}
