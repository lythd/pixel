package data;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PIBoot {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new MainFrame("Pixel IDE");
				frame.setSize(768,  384);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);		
			}
		});
	}
}
