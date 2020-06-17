/**
 * 
 */
package nz.ac.vuw.swen301.a2.client;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONTokener;

/**
 * @author Claire
 */
public class LogMonitor extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = -2499690603869610469L;

	private final CloseableHttpClient httpClient;
	
	private final RequestPanel request;
	private final ResultsPanel results;
	
	/**
	 * @param title
	 * @throws HeadlessException
	 */
	public LogMonitor() throws HeadlessException {
		super("Resthome4Logs Monitor");
		httpClient = HttpClients.createSystem();
		this.setLayout(new BorderLayout());
		this.request = new RequestPanel(this, "request");
		this.results = new ResultsPanel();
		this.add(request, BorderLayout.NORTH);
		this.add(results, BorderLayout.CENTER);
		this.addWindowListener(this);
		this.setSize(600, 400);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "request":
				HttpGet request = this.request.getRequest();
				if(request != null) {
					try {
						HttpResponse resp = httpClient.execute(request);
						JSONArray array = new JSONArray(new JSONTokener(resp.getEntity().getContent()));
						request.releaseConnection();
						results.setResults(array);
					} catch(Exception ex) {
						JOptionPane.showMessageDialog(this.getParent(),
							    ex.getMessage(),
							    "Exception",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
				break;
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		try {
			httpClient.close();
		} catch (IOException e1) {}
		this.setVisible(false);
		this.dispose();
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new LogMonitor().setVisible(true);
	}
	
}
