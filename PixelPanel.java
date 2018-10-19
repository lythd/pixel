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
import javax.swing.JTextField;

import functions.StandardLibrary;

public class PixelPanel extends JPanel {


	private static final long serialVersionUID = 1L;
	
	public PixelPanel() {
		Dimension size = getPreferredSize();
		size.width = 450;
		size.height = 350;
		setPreferredSize(size);
		setBorder(BorderFactory.createTitledBorder("Pixel Code"));
		
		final JTextArea codeArea = new JTextArea();
		final JTextField codeField = new JTextField(30);
		codeArea.setRows(15);
		codeArea.setColumns(20);
		
		JButton conBtn = new JButton("Convert");
		conBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				codeField.setText(StandardLibrary.mTell.convert(codeArea.getText()));
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
		
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 2;
		add(codeField, gc);
	}
}
