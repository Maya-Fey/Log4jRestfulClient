/**
 * 
 */
package nz.ac.vuw.swen301.a2.client;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Claire
 *
 */
public class Resthome4LogsAppender extends AppenderSkeleton {

	
	public void close() {
		// TODO Auto-generated method stub

	}

	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		
	}
	
	@Override
	public void setLayout(Layout layout) {
		if(!(layout instanceof JSONLayout)) {
			System.err.println("Using this appender without the companion layout is not recommended.");
		}
		super.setLayout(layout);
	}

}
