package supermarketSimulation;

public class Calculation {

	static int lostCustomers=0,time,total,products,sum=0;
	static int a[];
	int cashier;
	static long average;
	Calculation(int cashier)
	{
		this.cashier=cashier;
		 a=new int[cashier];
	}
	
	public synchronized void setLostCustomer()
	{
		lostCustomers++;
	}
	
	public int getLostCustomer()
	{
		return lostCustomers;
	}
	public synchronized void setUtilization(int ind)
	{
	    a[ind]+=1;
	    
	}

	public int[] getUtilization()
	{
	    /*for(int i=0;i<8;i++)
	    {
	    System.out.println("utilization of queue"+i+1 +": " +a[i]);
	}*/
	    return a;
	}
	
	public synchronized void setTotalWaitingTime(long tim)
	{
	    time+=tim;
	}

	public int getTotalWaitingTime()
	{
	    return time;
	}
	
	public synchronized long averageTotalWaitingTime()
	{
	    average=time/total;
	    return average;
	    
	}
	
	public synchronized void setTotalCusServed(){
	    
        total++;
       
    
    }
 public int getTotalCusServed(){
 
        return total;
 }
 
 public synchronized void setProductsProcessed(int a){
	    
     products+=a;
    
 
 }
 
public int getProductsProcessed()
{
 return products;
}

public void average()
{
    for(int i=0;i<cashier;i++)
    {
     sum=sum+a[i];
    }
    sum=sum/cashier;
        System.out.println("average utilization:"+sum);
}

}
