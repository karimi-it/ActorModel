import java.io.FileWriter;
import java.io.IOException;

import Enums.BuySell;


public  class  Symbol {
	String name;
	OrderBook sellBook;
	OrderBook buyBook;
	public  static  String result = "";
	public Symbol(String _name )
	{

		this.name = _name;
		sellBook = new OrderBook();
		PriceTimePriority orderPriority_sell= new PriceTimePriority();
		sellBook.setOrderPriority(orderPriority_sell);
		buyBook = new OrderBook();
		PriceTimePriority orderPriority_buy= new PriceTimePriority();
		buyBook.setOrderPriority(orderPriority_buy);

	}

	public void ProcessOrder(Order order ) throws IOException {
		if ( order.getBuySell() == BuySell.Buy )
			MatchBuyLogic(order);
		else
			MatchSellLogic(order);
		if ( order.getQuantity() > 0 )
		{
			if ( order.getBuySell() == BuySell.Buy )
			{
				buyBook.addOrder(order);
				buyBook.sort();
			}
			else
			{
				sellBook.addOrder(order);
				sellBook.sort();
			}
		}

	}
	private void MatchBuyLogic(Order currentOrder )
	{
		for (Order curOrder : sellBook.getOrderList()) {
			int quantity = currentOrder.getQuantity();
			double currentPrice = currentOrder.getPrice();
			int curquantity = curOrder.getQuantity();
			double curPrice = curOrder.getPrice();
			if (curPrice <= currentPrice && quantity > 0) {
				try {
					result = result.concat("Oreder Number =" + currentOrder.getOrderId() + "-Buy-" + currentPrice + " Matched with= " + "Oreder Number =" + curOrder.getOrderId() + "-Sell-" + curPrice);
					result = result.concat("\r\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				curOrder.setQuantity(curquantity - quantity);
				currentOrder.setQuantity(curquantity - quantity);
			} else {
				break;
			}
		}
//		for(Order curOrder : sellBook.getOrderList())
//		{
//			if ( curOrder.getPrice() <= currentOrder.getPrice() && currentOrder.getQuantity() > 0 )
//			{
//				try {
//					result=	result.concat("Oreder Number ="+curOrder.getOrderId()+"-"+curOrder.getBuySell()+"-"+curOrder.getPrice()+" Matched with= "+"Oreder Number ="+currentOrder.getOrderId()+"-"+currentOrder.getBuySell()+"-"+currentOrder.getPrice());
//					result= result.concat("\r\n");
//		           // FileWriter writer = new FileWriter("Result.txt", true);
//		         // writer.write("Oreder Number ="+curOrder.getOrderId()+"-"+curOrder.getBuySell()+"-"+curOrder.getPrice()+" Matched with= "+"Oreder Number ="+currentOrder.getOrderId()+"-"+currentOrder.getBuySell()+"-"+currentOrder.getPrice() );
//		          //  writer.write("\r\n");
//		          //  writer.close();
//		        } catch (Exception e) {
//		            e.printStackTrace();
//		        }
//				int quantity = currentOrder.getQuantity();
//				curOrder.setQuantity(curOrder.getQuantity() - currentOrder.getQuantity());
//				currentOrder.setQuantity(currentOrder.getQuantity() - quantity);
//			}
//			else
//			{
//				break;
//			}
//		}
	}

	private void MatchSellLogic(Order currentOrder )
	{
		for (Order curOrder : buyBook.getOrderList()) {
			int quantity = currentOrder.getQuantity();
			double currentPrice = currentOrder.getPrice();
			int curquantity = curOrder.getQuantity();
			double curPrice = curOrder.getPrice();
			if (curPrice >=currentPrice  && quantity > 0) {
				try {
					result=	result.concat("Oreder Number =" + currentOrder.getOrderId() + "-Buy-" + currentPrice + " Matched with= " + "Oreder Number =" + curOrder.getOrderId() + "-Sell-" + curPrice);
					result= result.concat("\r\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				curOrder.setQuantity(curquantity - quantity);
				currentOrder.setQuantity( quantity-curquantity);
			} else {
				break;
			}
		}
//		for(Order curOrder : buyBook.getOrderList())
//		{
//			if ( curOrder.getPrice() >= currentOrder.getPrice() && currentOrder.getQuantity() > 0 )
//			{
//				try {
//					result= result.concat("Oreder Number ="+curOrder.getOrderId()+"-"+curOrder.getBuySell()+"-"+curOrder.getPrice()+" Matched with= "+"Oreder Number ="+currentOrder.getOrderId()+"-"+currentOrder.getBuySell()+"-"+currentOrder.getPrice());
//					result= result.concat("\r\n");
//		           // FileWriter writer = new FileWriter("Result.txt", true);
//		           // writer.write("Oreder Number ="+curOrder.getOrderId()+"-"+curOrder.getBuySell()+"-"+curOrder.getPrice()+" Matched with= "+"Oreder Number ="+currentOrder.getOrderId()+"-"+currentOrder.getBuySell()+"-"+currentOrder.getPrice());
//		           // writer.write("\r\n");
//		           // writer.close();
//		        } catch (Exception e) {
//		            e.printStackTrace();
//		        }
//				int quantity = curOrder.getQuantity();
//				curOrder.setQuantity(curOrder.getQuantity() - currentOrder.getQuantity());
//				currentOrder.setQuantity(currentOrder.getQuantity() - quantity);
//			}
//			else
//			{
//				break;
//			}
//		}
	}
}

