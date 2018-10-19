package data;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

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
		
		Container c = getContentPane();
		c.add(pixelPanel, BorderLayout.EAST);
		c.add(treePanel, BorderLayout.WEST);
	}
}
