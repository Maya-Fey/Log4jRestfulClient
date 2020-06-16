package nz.ac.vuw.swen301.a2.client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.json.JSONObject;

/**
 * A JSON Layout for Log4j.
 * 
 * Turns log events into JS objects.
 * 
 * @author Claire
 */
public class JSONLayout extends Layout {
	
	public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
	
	static { format.setTimeZone(TimeZone.getTimeZone("UTC")); }
	

	public void activateOptions() {}
	
	@Override
	public String format(LoggingEvent event) 
	{	
		return this.toJSON(event).toString();
	}

	@Override
	public boolean ignoresThrowable() 
	{
		return false;
	}
	
	/**
	 * @param event The event being converted to JSON
	 * @return A Json representation of the event
	 */
	public JSONObject toJSON(LoggingEvent event) 
	{
		JSONObject obj = new JSONObject();
		obj.accumulate("id", UUID.randomUUID().toString());
		obj.accumulate("message", event.getMessage() == null ? null : event.getMessage().toString());
		obj.accumulate("timestamp", format.format(new Date(event.getTimeStamp())));
		obj.accumulate("thread", event.getThreadName());
		obj.accumulate("logger", event.getLoggerName());
		obj.accumulate("level", event.getLevel().toString());
		if(event.getThrowableInformation() != null) {
			StringBuilder builder = new StringBuilder();
			for(String s : event.getThrowableInformation().getThrowableStrRep()) builder.append(s + '\n');
			obj.accumulate("errorDetails", builder.substring(0, builder.length() - 1));
		}
		return obj;
	}

	

}
