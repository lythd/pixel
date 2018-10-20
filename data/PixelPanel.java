package data;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PixelPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	private JButton conBtn;
	private JTextArea codeArea = new JTextArea();

	public PixelPanel() {
		Dimension size = getPreferredSize();
		size.width = 450;
		size.height = 350;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Pixel Code"));
		
		codeArea.setRows(15);
		codeArea.setColumns(20);
		
		conBtn = new JButton("Save");
		conBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
 		});
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 10;
		gc.weighty = 10;
		gc.gridx = 0;
		gc.gridy = 0;
		add(codeArea, gc);
		
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 1;
		add(conBtn, gc);
	}
	
	public void giveCode(String code) {
		codeArea.setText(code);
	}
}
