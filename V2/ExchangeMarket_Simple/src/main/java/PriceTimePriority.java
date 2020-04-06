import java.util.Comparator;
import java.util.Date;

import Enums.BuySell;

public class PriceTimePriority implements Comparator<Order>{
	public int CompareOrder(Order orderX,Order orderY,int sortingOrder)
	{
		//Compares the current order price with another order price
		int priceComp =Double.compare(orderX.getPrice(), orderY.getPrice()) ;
		//If both price are equal then we also need to sort according to 
		//order timestamp
		if ( priceComp == 0 ) 
		{
			//Compare the current order timestamp with another order timestamp
			int timeComp =orderX.getOrderTimeStamp().compareTo(orderY.getOrderTimeStamp());
			return timeComp;
		}
		//since the sorting order for buy and sell order book is different
		//we need to ensure that orders are arranged accordingly
		//buy order book - highest buy price occupies top position
		//sell order book - lowest sell price occupies top position
		//therefore sortingOrder helps to achieve this ranking
		//a value of -1 sorts order in descending order of price and ascending 
		//order of time 
		//similarly value of 1 sorts order in ascending order of price
		//and ascending order of time
		return priceComp * sortingOrder;
	}
	public int compare(Order o1, Order o2) {
		// TODO Auto-generated method stub
		if ( o1.getBuySell() ==  BuySell.Buy ) 
			return CompareOrder(o1,o2,-1);
		else
			//For a sell order lowest sell price occupies top position
			return CompareOrder(o1,o2,1);
	}
	
}
