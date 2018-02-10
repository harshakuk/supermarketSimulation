package supermarketSimulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import supermarketSimulation.Customer;
import java.awt.Dimension;

public class SupermarketUI extends JFrame {
	ArrayList<BlockingQueue<Customer>> lines;
	Calculation cal;
	public int cashier;
	public final static int maxQueueSize = 7;
	JPanel queue[] ;
	JPanel queueLines[][];
	JPanel calculation=new JPanel();
	SimulationUI cv[][];
	private JPanel clerkPanel[];
	private static JPanel checkOutsPanel;
	final JLabel calculationLabel = new JLabel(" ");
	final JLabel calculationLabel1 = new JLabel(" ");
	final JLabel averageWaitingTime=new JLabel(" ");
	final JLabel totalProcessedItems=new JLabel(" ");
	final JLabel utilization[];
	private Timer timer;
public SupermarketUI(ArrayList<BlockingQueue<Customer>> lines2,int cashier) {
        this.lines=lines2;
        this.cashier=cashier;
        queue = new JPanel[cashier];
        cal=new Calculation(cashier);
    	 queueLines = new JPanel[cashier][maxQueueSize];
    	cv=new SimulationUI[cashier][maxQueueSize];
    	utilization=new JLabel[cashier];
        createFrame();
        createView();
        timer = new Timer(50, new TimerListener());
		timer.start();

        //


}

	//private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	

	/**
	 * Create the frame.
	 */
	public void createFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 700);
	}
	public void createView()
	{
		checkOutsPanel = new JPanel();
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		sp.setResizeWeight(0.7);
		checkOutsPanel.setLayout(new GridLayout(cashier + 1, 1, 1, 1));
		clerkPanel = new JPanel[cashier];
		for (int count = 0; count < cashier; count++) {
			clerkPanel[count] = new JPanel();
			clerkPanel[count].setBorder(BorderFactory.createLineBorder(Color.black));
			clerkPanel[count].add(new JLabel("Checkout " + (count + 1)));
			queue[count] = new JPanel();
			queue[count].setLayout(new GridLayout(1, maxQueueSize + 1, 2, 1));
			queue[count].setBorder(BorderFactory.createLineBorder(Color.black));
			queue[count].add(clerkPanel[count]);

			for (int i = 0; i < maxQueueSize; i++) {
				queueLines[count][i] = new JPanel();
				queueLines[count][i].setBorder(BorderFactory.createLineBorder(Color.black));
				queue[count].add(queueLines[count][i]);
			}

			checkOutsPanel.add(queue[count]);
		}
		
		
		calculation.setLayout(new BoxLayout(calculation, BoxLayout.Y_AXIS));
		
		sp.add(checkOutsPanel);
		sp.add(calculation);
		getContentPane().add(sp, BorderLayout.CENTER);
		calculation.add(calculationLabel);
		calculation.add(Box.createVerticalStrut(20));
		calculation.add(calculationLabel1);
		calculation.add(Box.createVerticalStrut(20));
		calculation.add(totalProcessedItems);
		for(int i=0;i<cashier;i++)
		{
			utilization[i]=new JLabel();
			calculation.add(Box.createVerticalStrut(20));
		calculation.add(utilization[i]);
		}
		calculation.add(Box.createVerticalStrut(20));
		calculation.add(averageWaitingTime);
		for (int count = 0; count < cashier; count++) {
			for (int i = 0; i < maxQueueSize; i++) {
				cv[count][i] = new SimulationUI();
				queueLines[count][i].add(cv[count][i]);
			}
		}
		
		
	}
public void showCustomer() {
	Object [] arr = null;
		for (int count = 0; count < cashier; count++) {
			for (int i = 0; i < maxQueueSize; i++) {
				try {
					if (lines.get(count)!=null)
					{
						arr =  lines.get(count).toArray();
						cv[count][i].setCustomer((((Customer) arr[i]).getItems()));
					}
				} catch (IndexOutOfBoundsException e) {
					cv[count][i].setCustomer(0);
				}
			}
		}
	}

public void showCalculation()
{
	calculationLabel1.setText("Total lost customers " + (cal.getLostCustomer()));
	calculationLabel.setText("Total customers served " + (cal.getTotalCusServed()));
	totalProcessedItems.setText("Total products processed "+(cal.getProductsProcessed()));
	int[] a=cal.getUtilization();
	for(int i=0;i<cashier;i++)
	{
		
		utilization[i].setText("Utilization of cashier " +String.valueOf(i+1)+" is " +a[i]);
	}
	if(cal.getTotalCusServed()>0)
	averageWaitingTime.setText("average customer waiting time "+(cal.averageTotalWaitingTime()));
}

	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showCustomer();
			showCalculation();
			checkOutsPanel.revalidate();
			checkOutsPanel.repaint();
		}
	}

}

