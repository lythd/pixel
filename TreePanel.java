package tree;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private DefaultMutableTreeNode selectedNode;
	
	public TreePanel() {
		Dimension size = getPreferredSize();
		size.width = 300;
		size.height = 350;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("File Searcher"));

	    DefaultMutableTreeNode files = new DefaultMutableTreeNode("Files"); 
	    BuffNode buffFiles = new BuffNode(new File("files"), "FOLDER", null, files);
	    
	    appendFilesFromDir(files, buffFiles, "files");
	    
	    tree = new JTree(files);  
	    
	    tree.setEditable(true);
	    tree.setComponentPopupMenu(getPopUpMenu(buffFiles));
	    tree.addMouseListener(getMouseListener());
	    
	    add(tree);
	}
	
	public static void appendFilesFromDir(DefaultMutableTreeNode node, BuffNode bNode, String dir) {
		File folder = new File(dir);
	    appendFilesForFolder(node, bNode, folder);
	}
	
	public static void appendFilesForFolder(DefaultMutableTreeNode node, BuffNode bNode, File folder) {
		File [] files = folder.listFiles();
		File file = null;
		String temp;
		int count;
		for (int i = 0; i < folder.listFiles().length; i++) {
		    count = 0;
		    for (File f : files) {
		    	if (count == i) file = f;
		    	count++;
		    }
		    if (file.isDirectory()) {
		    	DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(file.getName());
		    	node.add(newNode);
		    	BuffNode newbNode = new BuffNode(file, "FOLDER", null, newNode, bNode);
		    	appendFilesForFolder(newNode, newbNode, file);
		    } else {
		    	if (file.isFile()) {
			    	temp = file.getName();
			    	DefaultMutableTreeNode newNode = null;
			    	@SuppressWarnings("unused")
					BuffNode newbNode = null;
			    	if (temp.contains(".be.mcfunction.pixel")) {
			    		newNode = new DefaultMutableTreeNode(file.getName());
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "BLINGEDIT_PIXEL", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".mcfunction.pixel")) {
			    		newNode = new DefaultMutableTreeNode(file.getName());
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "MCFUNCTION_PIXEL", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".onecommand.pixel")) {
			    		newNode = new DefaultMutableTreeNode(file.getName());
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "ONECOMMAND_PIXEL", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".be.mcfunction")) {
			    		newNode = new DefaultMutableTreeNode(file.getName());
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "BLINGEDIT_EXPORTED", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".mcfunction")) {
			    		newNode = new DefaultMutableTreeNode(file.getName());
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "MCFUNCTION_EXPORTED", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".onecommand")) {
			    		newNode = new DefaultMutableTreeNode(file.getName());
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "ONECOMMAND_EXPORTED", readFile(file), newNode, bNode);
			    	}
		    	}
		    }
		}
	}
	
	public static String readFile (File file) {
		String string = "";
		String line = null;
		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine())!= null) string += line + "\n";
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return string;
	}

	private MouseListener getMouseListener() {
	    return new MouseAdapter() {
	
	        @Override
	        public void mousePressed(MouseEvent arg0) {
	            if(arg0.getButton() == MouseEvent.BUTTON3){
	                TreePath pathForLocation = tree.getPathForLocation(arg0.getPoint().x, arg0.getPoint().y);
	                if(pathForLocation != null){
	                    selectedNode = (DefaultMutableTreeNode) pathForLocation.getLastPathComponent();
	                } else{
	                    selectedNode = null;
	                }
	
	            }
	            super.mousePressed(arg0);
	        }
	    };
	}
	
	private JPopupMenu getPopUpMenu(BuffNode bNode) {
	    JPopupMenu menu = new JPopupMenu();
	    JMenuItem itemAdd = new JMenuItem("add");
	    itemAdd.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            if(selectedNode != null){
	                System.out.println("Add " + selectedNode);
	                DefaultMutableTreeNode n = new DefaultMutableTreeNode("added");
	                selectedNode.add(n);
	                tree.repaint();
	                tree.updateUI();
	            }
	        }
	    });
	    menu.add(itemAdd);
	
	    JMenuItem itemEdit = new JMenuItem("edit");
	    itemEdit.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            if(selectedNode != null){
	                System.out.println("Edit " + selectedNode);
	            }
	        }
	    });
	    menu.add(itemEdit);
	
	    JMenuItem itemExport = new JMenuItem("export");
	    itemExport.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            if(selectedNode != null){
	                System.out.println("Export " + selectedNode);
	                String name = crop(selectedNode.getUserObject().toString());
	                DefaultMutableTreeNode n = new DefaultMutableTreeNode(name);
	                selectedNode.add(n);
	                tree.repaint();
	                tree.updateUI();
	            }
	        }
	    });
	    menu.add(itemExport);
	
	    return menu;
	}
	
	public String crop(String str) {
		return str.substring(0,str.lastIndexOf("."));
	}
}
