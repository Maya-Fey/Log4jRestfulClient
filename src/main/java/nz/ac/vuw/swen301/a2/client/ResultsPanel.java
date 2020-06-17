/**
 * 
 */
package nz.ac.vuw.swen301.a2.client;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Claire
 */
public class ResultsPanel extends JPanel {

	private static final long serialVersionUID = 7716909741774748816L;
	
	private final ResultsModel model = this.new ResultsModel();
	
	public ResultsPanel() {
		super();
		this.setLayout(new BorderLayout());
		JTable table = new JTable();
		table.setModel(this.model);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setResults(JSONArray json) {
		List<JSONObject> objs = new ArrayList<>();
		json.forEach((obj) -> { objs.add((JSONObject) obj); });
		model.setList(objs);
		model.fireTableDataChanged();
	}
	
	private static final String[] cols = new String[] { "timestamp", "level", "logger", "thread", "message" };
	
	public class ResultsModel extends AbstractTableModel {

		private static final long serialVersionUID = -7986997224732863366L;

		public List<JSONObject> objs = new ArrayList<>();;
		
		public void setList(List<JSONObject> objs)
		{
			this.objs = objs;
		}
		
		@Override 
		public String getColumnName(int columnIndex)
		{
			return cols[columnIndex];
		}
		
		@Override
		public int getRowCount() {
			return objs.size();
		}

		@Override
		public int getColumnCount() {
			return 5;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return objs.get(rowIndex).get(cols[columnIndex]);
		}
		
	}
}
