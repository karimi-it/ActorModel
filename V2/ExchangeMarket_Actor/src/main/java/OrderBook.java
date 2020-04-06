import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderBook {

	private  List<Order> orderList;
	private  Comparator<Order> OrderPriority;
	public OrderBook()
	{
	    orderList = new ArrayList<Order>();
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	public void setOrderPriority(Comparator<Order> orderPriority) {
		OrderPriority = orderPriority;
	}
	public void addOrder(Order newOrder) 
	{
		orderList.add(newOrder);
	}
	public void sort() 
	{
		orderList.sort(OrderPriority);
	}
}
