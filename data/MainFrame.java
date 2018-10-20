package data;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tree.TreeEvent;
import tree.TreeListener;
import tree.TreePanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private PixelPanel pixelPanel;
	private TreePanel treePanel;
	
	public MainFrame(String title) {
		super(title);
		setLayout(new BorderLayout());
		
		pixelPanel = new PixelPanel();
		treePanel = new TreePanel();
		treePanel.addTreeListener(new TreeListener() {
			public void treeEventOccured(TreeEvent event) {
				if(event.getType() == "Edit") pixelPanel.giveCode(event.getData());
				else if(event.getType() == "Add1") {
					treePanel.pop = true;
					String s = (String) JOptionPane.showInputDialog(null, "Name", "Pixel : Add File", JOptionPane.QUESTION_MESSAGE, null, null, "");
					//If a string was returned, say so.
					if ((s != null) && (s.length() > 0)) {
					    treePanel.addNode1(s);
					}
					treePanel.pop = false;
				} else if(event.getType() == "Add2") {
					treePanel.pop = true;
					Object[] possibilities = {"FOLDER", "ONECOMMAND", "MCFUNCTION", "BLINGEDIT"};
					String s = (String) JOptionPane.showInputDialog(null, "Type", "Pixel : Add File", JOptionPane.QUESTION_MESSAGE, null, possibilities, possibilities[0]);
					//If a string was returned, say so.
					if ((s != null) && (s.length() > 0)) {
					    treePanel.addNode2(s);
					}
					treePanel.pop = false;
				} else if(event.getType() == "Error") JOptionPane.showMessageDialog(null, event.getData(), "Pixel : Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		Container c = getContentPane();
		c.add(pixelPanel, BorderLayout.CENTER);
		c.add(treePanel, BorderLayout.WEST);
	}
}
