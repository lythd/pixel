package tree;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

public class TreePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private DefaultMutableTreeNode selectedNode;
	private BuffNode selectedbNode;
    private DefaultMutableTreeNode files = new DefaultMutableTreeNode("Pixel"); 
    private BuffNode buffFiles = new BuffNode(new File("Pixel"), "IDE", null, files);
    private DefaultMutableTreeNode n;
	private BuffNode b;
	private EventListenerList listenerList = new EventListenerList();
	public boolean pop;
	
	public TreePanel() {
		Dimension size = getPreferredSize();
		size.width = 300;
		size.height = 350;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("File Searcher"));
	    
	    appendFilesFromDir(files, buffFiles, "Pixel");
	    
	    tree = new JTree(files);  
	    
	    tree.setEditable(true);
	    tree.setComponentPopupMenu(getPopUpMenu(buffFiles));
	    tree.addMouseListener(getMouseListener());

	    ImageIcon pixelIDEIcon = createImageIcon("images/pixel_ide.png");
	    ImageIcon pixelIcon = createImageIcon("images/pixel.png");
	    ImageIcon treeIcon = createImageIcon("images/folder.png");
	    ImageIcon functionIcon = createImageIcon("images/function.png");
	    ImageIcon commandIcon = createImageIcon("images/command.png");
	    ImageIcon nullIcon = createImageIcon("images/null.png");
	    if (pixelIDEIcon != null || pixelIcon != null || treeIcon != null || functionIcon != null || commandIcon != null || nullIcon != null) {
	      tree.setCellRenderer(new MyRenderer(pixelIDEIcon, pixelIcon, treeIcon, functionIcon, commandIcon, nullIcon));
	    } else {
	      System.err.println("Icon missing; using default.");
	    }
	    
	    add(tree);
	}

	protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = TreePanel.class.getResource(path);
	    if (imgURL != null) {
	    	return new ImageIcon(imgURL);
	    } else {
	    	System.err.println("Couldn't find file: " + path);
	    	return null;
	    }
	  }
	
	private class MyRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 1L;
		Icon pixelIDEIcon, pixelIcon, treeIcon, functionIcon, commandIcon, nullIcon;

	    public MyRenderer(Icon pixelIDEIcon, Icon pixelIcon, Icon treeIcon, Icon functionIcon, Icon commandIcon, Icon nullIcon) {
	    	this.pixelIDEIcon = pixelIDEIcon;
	    	this.pixelIcon = pixelIcon;
	    	this.treeIcon = treeIcon;
	    	this.functionIcon = functionIcon;
	    	this.commandIcon = commandIcon;
	    	this.nullIcon = nullIcon;
	    }
	    
	    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
	    	super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
	    	if (leaf) {
	    		setIcon(nullIcon);
	    		String name = "";
	    		name = buffFiles.childNode(((DefaultMutableTreeNode) value).getUserObject().toString(), true).name();
	    		if(name.lastIndexOf(".") > -1 && name.substring(name.lastIndexOf(".")).contains("pixel")) setIcon(pixelIcon);
	    		else if(name.lastIndexOf(".") > -1 && name.substring(name.lastIndexOf(".")).contains("mcfunction")) setIcon(functionIcon);
	    		else if(name.lastIndexOf(".") > -1 && name.substring(name.lastIndexOf(".")).contains("onecommand")) setIcon(commandIcon);
	    	} else {
	    		if(((DefaultMutableTreeNode) value) == files) setIcon(pixelIDEIcon);
	    		else setIcon(treeIcon);
	    	}
	    	return this;
	    }
	}
	
	public static void appendFilesFromDir(DefaultMutableTreeNode node, BuffNode bNode, String dir) {
		File folder = new File(dir);
	    appendFilesForFolder(node, bNode, folder);
	}
	
	public static void appendFilesForFolder(DefaultMutableTreeNode node, BuffNode bNode, File folder) {
		File [] files = folder.listFiles();
		File file = null;
		String temp;
		for (int i = 0; i < files.length; i++) {
		    file = files[i];
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
			    	if (temp.contains(".mcfunction.pixel")) {
			    		String name = file.getName();
			    		newNode = new DefaultMutableTreeNode(name.substring(0,name.indexOf(".")));
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "MCFUNCTION", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".onecommand.pixel")) {
			    		String name = file.getName();
			    		newNode = new DefaultMutableTreeNode(name.substring(0,name.indexOf(".")));
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "ONECOMMAND", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".mcfunction")) {
			    		String name = file.getName();
			    		newNode = new DefaultMutableTreeNode(name.substring(0,name.indexOf(".")));
			    		node.add(newNode);
			    		newbNode = new BuffNode(file, "MCFUNCTION_EXPORTED", readFile(file), newNode, bNode);
			    	} else if (temp.contains(".onecommand")) {
			    		String name = file.getName();
			    		newNode = new DefaultMutableTreeNode(name.substring(0,name.indexOf(".")));
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
	                if (pathForLocation != null && !pop) {
	                    selectedNode = (DefaultMutableTreeNode) pathForLocation.getLastPathComponent();
	                    selectedbNode = buffFiles.childNode(selectedNode.getUserObject().toString(), true);
	                } else {
	                    selectedNode = null;
	                    selectedbNode = null;
	                }
	
	            }
	            super.mousePressed(arg0);
	        }
	    };
	}
	
	private JPopupMenu getPopUpMenu(BuffNode bNode) {
	    JPopupMenu menu = new JPopupMenu();
	    JMenuItem itemAdd = new JMenuItem("Add");
	    itemAdd.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            if (selectedNode != null && selectedbNode != null) {
	            	if (selectedbNode.type() == "FOLDER") fireTreeEvent(new TreeEvent(this, "Name", "Add1"));
	            	else fireTreeEvent(new TreeEvent(this, selectedbNode.nodeName() + " is not a folder and can not have a file added onto it.", "Error"));
	            }
	            else if(pop) fireTreeEvent(new TreeEvent(this, "There is a popup open.", "Error"));
	            else fireTreeEvent(new TreeEvent(this, "File not found.", "Error"));
	        }
	    });
	    menu.add(itemAdd);
	
	    JMenuItem itemEdit = new JMenuItem("Edit");
	    itemEdit.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            if (selectedNode != null && selectedbNode != null) {
	            	if (!(selectedbNode.type() == "FOLDER")) fireTreeEvent(new TreeEvent(this, selectedbNode.data(), "Edit"));
	            	else fireTreeEvent(new TreeEvent(this, selectedbNode.nodeName() + " is a folder and can not be edited.", "Error"));
	            }
	            else if(pop) fireTreeEvent(new TreeEvent(this, "There is a popup open.", "Error"));
	            else fireTreeEvent(new TreeEvent(this, "File not found.", "Error"));
	        }
	    });
	    menu.add(itemEdit);
	
	    JMenuItem itemExport = new JMenuItem("Export");
	    itemExport.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            if (selectedNode != null && selectedbNode != null) {
	            	if (!selectedbNode.type().contains("_EXPORTED") && !(selectedbNode.type() == "FOLDER")){
		        		selectedbNode.file().renameTo(new File(crop(selectedbNode.file().getAbsolutePath())));
		                selectedbNode.withType(selectedbNode.type() + "_EXPORTED").exportData();
		                tree.repaint();
		                tree.updateUI();
		            } else if (selectedbNode.type().contains("_EXPORTED")) fireTreeEvent(new TreeEvent(this, selectedbNode.nodeName() + " is already exported.", "Error"));
		            else if (selectedbNode.type() == "FOLDER") fireTreeEvent(new TreeEvent(this, selectedbNode.nodeName() + " is a folder and can not be exported.", "Error"));
	            }
	            else if(pop) fireTreeEvent(new TreeEvent(this, "There is a popup open.", "Error"));
	            else fireTreeEvent(new TreeEvent(this, "File not found.", "Error"));
	       }
	    });
	    menu.add(itemExport);
	    
	    JMenuItem itemDelete = new JMenuItem("Delete");
	    itemDelete.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            if (selectedNode != null && selectedbNode != null) {
	            	delete(selectedbNode);
		            tree.repaint();
		            tree.updateUI();
	            }
	            else if(pop) fireTreeEvent(new TreeEvent(this, "There is a popup open.", "Error"));
	            else fireTreeEvent(new TreeEvent(this, "File not found.", "Error"));
	       }
	    });
	    menu.add(itemDelete);
	
	    return menu;
	}
	
	public void delete(BuffNode bn) {
		for(BuffNode bc : bn.children()) delete(bc);
		delete(bn.node());
		bn.withoutChildren().withoutParent().nullify();
	}
	
	public void delete(DefaultMutableTreeNode dn) {
		dn.removeAllChildren();
		dn.removeFromParent();
		dn.equals(null);
	}
	
	public String crop(String str) {
		return str.substring(0,str.lastIndexOf("."));
	}
	
	public void addNode1(String name) {
		b = new BuffNode().withParent(selectedbNode).withType(name);
        tree.repaint();
        tree.updateUI();
		fireTreeEvent(new TreeEvent(this, "Type", "Add2"));
		pop = false;
	}
	
	public void addNode2(String type) {
		File file = null;
		String ending = "";
        n = new DefaultMutableTreeNode(b.type());
        selectedNode.add(n);
		try {
			if(type == "BLINGEDIT") {
				b.setData("import McFunction\nimport BlingEdit\n");
				ending = ".mcfunction.pixel";			
			}
			else if(type == "MCFUNCTION") {
				b.setData("import McFunction\n");
				ending = ".mcfunction.pixel";
			}
			else if(type == "ONECOMMAND") {
				b.setData("import OneCommand\n");
				ending = ".onecommand.pixel";
			}
			file = new File("pixel/files/" + b.type() + ending);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write("");
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		b.withFile(file, type, "").withNode(n).withParent(selectedbNode);
		pop = false;
	}
	
	public void fireTreeEvent(TreeEvent event) {
		Object[] listeners = listenerList.getListenerList();
		for(int i = 0; i < listeners.length; i += 2) {
			if(listeners[i] == TreeListener.class) {
				((TreeListener) listeners[i+1]).treeEventOccured(event);
			}
		}
	}
	
	public void addTreeListener(TreeListener treeListener) {
		listenerList.add(TreeListener.class, treeListener);
	}
	
	public void removeTreeListener(TreeListener listener) {
		listenerList.remove(TreeListener.class, listener);
	}
}
