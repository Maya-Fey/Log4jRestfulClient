/**
 * 
 */
package nz.ac.vuw.swen301.a2.client;

import java.util.Random;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * @author Claire
 */
public class CreateRandomLogs {
	
	private final Resthome4LogsAppender appender = new Resthome4LogsAppender();
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final Random rand = new Random();
	
	public CreateRandomLogs()
	{
		appender.setLayout(new JSONLayout());
		logger.addAppender(appender);
	}
	
	public void appendRandomlog()
	{
		logger.log(this.randomPriority(), this.randomMessage("This message is here to inform you of a randomly-generated log event"), rand.nextBoolean() ? new Error(this.randomMessage("This message is here to inform you of a random nonthrown exception: ")) : null);
	}
	
	private String randomMessage(String prefix)
	{
		return prefix + rand.nextInt(100000);
	}
	
	private static final Priority[] priorities = { Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE };
	private Priority randomPriority() {
		return priorities[rand.nextInt(priorities.length)];
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CreateRandomLogs creator = new CreateRandomLogs();
		while(true) {
			synchronized(creator) {
				creator.appendRandomlog();
				try {
					creator.wait(1000);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	

}
