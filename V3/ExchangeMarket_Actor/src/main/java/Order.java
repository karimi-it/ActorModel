import Enums.BuySell;
import Enums.OrderType;
import akka.actor.ActorRef;

import java.util.Date;
import java.util.UUID;

public abstract class Order {
	private String instrument;
	private BuySell buySell;
	private OrderType orderType;
	private double price;
	private int quantity;
	static String globalOrderId;
	private long orderId;
	private Date orderTimeStamp;
    private ActorRef owner;
	

	public Order() {
		globalOrderId = UUID.randomUUID().toString();
		orderTimeStamp = new Date();
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public BuySell getBuySell() {
		return buySell;
	}

	public void setBuySell(BuySell buySell) {
		this.buySell = buySell;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		if (quantity < 0)
			this.quantity = 0;
		else
			this.quantity = quantity;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderTimeStamp() {
		return orderTimeStamp;
	}
	public ActorRef getOwner() {
		return owner;
	}
	public void setOwner(ActorRef owner) {
		this.owner = owner;
	}

}
