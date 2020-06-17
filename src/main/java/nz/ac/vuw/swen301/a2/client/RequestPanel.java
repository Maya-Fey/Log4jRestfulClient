/**
 * 
 */
package nz.ac.vuw.swen301.a2.client;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

/**
 * @author Claire
 *
 */
public class RequestPanel extends JPanel {

	private static final long serialVersionUID = -8620739064967092682L;

	private final JButton submit;
	private final JTextField limit = new JTextField();
	private final LevelSelect level = new LevelSelect();
	
	private String logServiceURL = "http://localhost:8080/resthome4logs/logs";
	
	public RequestPanel(ActionListener listener, String actionCommand) {
		submit = new JButton("Request");
		submit.setActionCommand(actionCommand);
		submit.addActionListener(listener);
		this.add(new JLabel("Log Level: "));
		this.add(level);
		this.add(new JLabel("Logs to return: "));
		this.add(limit);
		this.add(submit);
		this.limit.setColumns(10);
	}
	
	public HttpGet getRequest()
	{
		boolean err = false;
		try {
			if(Integer.parseInt(limit.getText()) < 0)
				err = true;
		} catch(NumberFormatException e) {
			err = true;
		}
		if(err) {
			JOptionPane.showMessageDialog(this.getParent(),
				    "Limit must be a number above zero",
				    "Input error",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
        try {
        	HttpGet get = new HttpGet(new URIBuilder().setScheme("http").setHost("localhost:8080").setPath("/resthome4logs/logs").addParameter("limit", limit.getText()).addParameter("level", level.getSelected().toString()).build());
    		get.setHeader("accepts", "application/json");
			return get;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.getParent(),
				    "Your system does not support UrlEncodedFormEntity.",
				    "Encoding error",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
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
