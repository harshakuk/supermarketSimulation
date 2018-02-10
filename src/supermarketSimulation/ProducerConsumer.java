package supermarketSimulation;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ProducerConsumer {
	
	//static ArrayList<LinkedList<Customer>> lines=new ArrayList<LinkedList<Customer>>();
	static int cashier;
	static ArrayList <BlockingQueue<Customer>> lines=new ArrayList<BlockingQueue<Customer>>();
	//Queue<Customer> list = new LinkedList<>();
     
	    public static void main(String[] args)
	                        throws InterruptedException
	    {
	        // Object of a class that has both produce()
	        // and consume() methods
	    	Scanner sc=new Scanner(System.in);
	    	System.out.println("please enter the no of cashiers");
			cashier=sc.nextInt();
			sc.close();
	    	for(int i=0;i<8;i++)
	    	{
	    		//lines.add(new LinkedList<Customer>());
	    		//lines[i]=new LinkedBlockingQueue();
	    		lines.add(new LinkedBlockingQueue());
	    	}
	        
	        Producer producer=new Producer(lines,cashier);
	        Thread t1=new Thread(producer);
	        
	        
	        
	        SupermarketUI graphicsObject=new SupermarketUI(lines,cashier);
	         graphicsObject.setVisible(true);
	         t1.start();
	         for(int i=0;i<cashier;i++)
		        {
		        	Consumer c=new Consumer(lines.get(i),i,cashier);
		        	Thread t=new Thread(c);
		        	t.start();
		        }
	 
	        
	        
	        
	    
}
	 
	   

	    	   
	    	}

class Producer extends Thread
{
	private long start = System.currentTimeMillis();
	Calculation cal;
	Random random = new Random();
    ArrayList<BlockingQueue<Customer>> lines;
    int cashier;
    public Producer(ArrayList<BlockingQueue<Customer>> lines2, int cashier)
    {
    	this.lines=lines2;
    	this.cashier=cashier;
    	cal=new Calculation(cashier);
    }
	 public void run() {
	while ((System.currentTimeMillis()-start)<=60000L)
    {
        try
        {
            // producer thread waits while list
            // is full
        	 int waitTime= random.nextInt(60);
        	//int randomNumber = random.nextInt(5);
        	BlockingQueue<Customer> queue=min(lines); 
        	int items=random.nextInt(199)+1;
        	if(queue.size()==6)
            {
                cal.setLostCustomer();
                Thread.sleep(waitTime);
               
            }
        	else
        	{
        		/* while (queue.size()==capacity)
     				wait();

                 System.out.println("Producer produced-"
                                               + items+" and added to queue " +Integer.toString(randomNumber));
                 queue.add(new Customer(items));*/
        		if(queue==lines.get(0))
                {
                    if (items<10)
                    {         
                    		Customer customer=new Customer();
                    		customer.setItems(items);
                    		customer.setStartingTime(System.currentTimeMillis());
                            queue.put(customer);
                            //notify();
                            System.out.println("add: "+items);
                            Thread.sleep(waitTime+100);
                            
                     
                    }
                    
                    else{
                        
                        System.out.println("10 or less queue");
                        /*int randomNumber = random.nextInt(7)+1;
                        lines[randomNumber].put(new Customer(items));
                        Thread.sleep(waitTime+100);
                        System.out.println("add: "+items);*/
                    }
        	}
        		else
        		{
        			

                     /*System.out.println("Producer produced-"
                                                   + items+" and added to queue " +Integer.toString(randomNumber));*/
        			Customer customer=new Customer();
            		customer.setItems(items);
            		customer.setStartingTime(System.currentTimeMillis());
                    queue.put(customer);
                     System.out.println("add: "+items);
                     Thread.sleep(waitTime+100);
                     
                     
        		}
           
            //value++;

            // to insert the jobs in the list
            //list.add(value);

            // notifies the consumer thread that
            // now it can start consuming
            
            
        	
        }
        }
        catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
	
	
}
	 public BlockingQueue<Customer> min(ArrayList<BlockingQueue<Customer>> lines2)
	 {
		 ArrayList<BlockingQueue<Customer>> lines1=lines2;
		 BlockingQueue<Customer> min=lines2.get(0);
		 for(int i=0;i<lines1.size();i++)
		 {
			 if(lines1.get(i).size()<min.size())
			 {
				 min=lines1.get(i);
			 }
		 }
		 return min;
	 }
}

class Consumer extends Thread
{
	private long start = System.currentTimeMillis();
	BlockingQueue<Customer> line;
	Calculation cal;
	Random random = new Random();
	int rand,time;
	long totalTimeTaken;
	int cashier;
	int totalCashier;
	public Consumer(BlockingQueue<Customer> lines, int cashier, int totalCashier)
	{
		this.line=lines;
		this.cashier=cashier;
		this.totalCashier=totalCashier;
		cal=new Calculation(totalCashier);
	}
	public void run() {
	while ((System.currentTimeMillis()-start)<=60000L)
    {
        try
        {
			rand=random.nextInt(5)+1;
       
        	
        	Customer val=line.take();
        	time=rand*(val.getItems());
        	
        	System.out.println("Consumer "+ String.valueOf(cashier)+ " consumed-"
                    + val.getItems());
        	totalTimeTaken=System.currentTimeMillis()-val.getStartingTime();
        	System.out.println("time taken "+time);
        	cal.setUtilization(cashier);
        	cal.setTotalWaitingTime(totalTimeTaken);
        	cal.setTotalCusServed();
        	cal.setProductsProcessed(val.getItems());
            // and sleep
        	Thread.sleep(time*10 +40);
            
        }
        catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
	}
}


	

