package supermarketSimulation;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class SimulationUI extends JPanel {
	final private static Border blackline = BorderFactory.createLineBorder(Color.black);
	final private static ImageIcon trollyGIF = new ImageIcon("trolly.gif");
	final private JLabel cust = new JLabel("", trollyGIF, SwingConstants.CENTER);
	int c;
	/**
	 * Create the panel.
	 */
	public SimulationUI() {
		setBackground(Color.white);
        
		setLayout(new BorderLayout());
                JLabel cust = new JLabel("Shopper",trollyGIF,SwingConstants.CENTER);
		setBorder(blackline);
	}
	public void addToPanel() {
		removeAll(); // Clear panel
		if (c != 0) {
			add(cust);
			JLabel products = new JLabel(c+ "");
			add(products, BorderLayout.EAST);
		} 
	}
	
	public void setCustomer(int items){
		c=items;
		addToPanel();
	}


	public int getNumberOfItems() {
		return c;
	}

}
