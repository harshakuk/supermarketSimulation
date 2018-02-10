package supermarketSimulation;

public class Customer {
	int items;
	long start;
	/*Customer(int items)
	{
		this.items=items;
	}*/
	public void setItems(int items)
	{
		this.items=items;
	}
	public int getItems(){
		return items;
	}
	
	public void setStartingTime(long start)
	{
		this.start=start;
	}
	public long getStartingTime()
	{
		return start;
	}

}
