package nz.ac.vuw.swen301.a2.client;

import javax.swing.JComboBox;

import org.apache.log4j.Level;

/**
 * @author Claire
 */
public class LevelSelect extends JComboBox<Level> {

	private static final long serialVersionUID = 2330037521682914727L;

	public LevelSelect() {
		this.addItem(Level.ALL);
		this.addItem(Level.TRACE);
		this.addItem(Level.DEBUG);
		this.addItem(Level.INFO);
		this.addItem(Level.WARN);
		this.addItem(Level.ERROR);
		this.addItem(Level.FATAL);
		this.addItem(Level.OFF);
	}
	
	public Level getSelected()
	{
		return (Level) this.getSelectedItem();
	}
	
}
