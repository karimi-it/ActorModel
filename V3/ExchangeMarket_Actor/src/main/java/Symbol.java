import Enums.BuySell;
import Enums.MessageSubject;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

public class Symbol extends UntypedAbstractActor {
    String name;
    OrderBook sellBook;
    OrderBook buyBook;
    Order currentOrder;
    ActorRef logger;
    Long StartTime;
    int counter;

    public Symbol(String _name, ActorRef _logger, Long time) {

        StartTime = time;
        logger = _logger;
        this.name = _name;
        sellBook = new OrderBook();
        PriceTimePriority orderPriority_sell = new PriceTimePriority();
        sellBook.setOrderPriority(orderPriority_sell);
        buyBook = new OrderBook();
        PriceTimePriority orderPriority_buy = new PriceTimePriority();
        buyBook.setOrderPriority(orderPriority_buy);

    }

    public void printOrderList() {
        System.out.println("---------------buy------------------------");
        for (Order o : buyBook.getOrderList()) {
            System.out.println("Order Id =" + o.getOrderId() + " Quantity =" + o.getQuantity() + " Price =" + o.getPrice());
        }
        System.out.println("---------------sell------------------------");
        for (Order o : sellBook.getOrderList()) {
            System.out.println("Order Id =" + o.getOrderId() + " Quantity =" + o.getQuantity() + " Price =" + o.getPrice());
        }
        System.out.println("*****************************************");
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        Message Msg = (Message) msg;
        if (Msg.subject == MessageSubject.addOrder) {
            OrderMessage orderMsg = (OrderMessage) msg;
            currentOrder = orderMsg.order;
            ProcessOrder(orderMsg);
        }
    }

    public void ProcessOrder(OrderMessage orderMsg) {
//		try
//		{
//			TimeUnit.MILLISECONDS.sleep(5);
//		}
//		catch (Exception ex)
//		{
//
//		}
        if (currentOrder.getBuySell() == BuySell.Buy) {
            // System.out.println("Call buy logic");
            MatchBuyLogic(orderMsg);
        } else {
            //  System.out.println("Call sell logic");
            MatchSellLogic(orderMsg);
        }
        if (currentOrder.getQuantity() > 0) {
            if (currentOrder.getBuySell() == BuySell.Buy) {
                buyBook.addOrder(currentOrder);
                buyBook.sort();
            } else {
                sellBook.addOrder(currentOrder);
                sellBook.sort();
            }
        }
    }

    private void MatchBuyLogic(OrderMessage orderMsg) {
        for (Order curOrder : sellBook.getOrderList()) {
			int quantity = currentOrder.getQuantity();
			double currentPrice = currentOrder.getPrice();
			int curquantity = curOrder.getQuantity();
			double curPrice = curOrder.getPrice();
            if (curPrice <=currentPrice  && quantity > 0) {
                logger.tell("Oreder Number =" + currentOrder.getOrderId() + "-Buy-" + currentPrice + " Matched with= " + "Oreder Number =" + curOrder.getOrderId() + "-Sell-" + curPrice+ "\r\n", ActorRef.noSender());
                curOrder.setQuantity(curquantity - quantity);
                currentOrder.setQuantity(curquantity - quantity);
            } else {
                break;
            }
        }

    }

    private void MatchSellLogic(OrderMessage orderMsg) {
        for (Order curOrder : buyBook.getOrderList()) {
			int quantity = curOrder.getQuantity();
			int currentQuantity = currentOrder.getQuantity();
			double currentOrderPrice = currentOrder.getPrice();
			double curOrderPrice = curOrder.getPrice();
            if (curOrderPrice >= currentOrderPrice && currentQuantity > 0) {
                logger.tell("Oreder Number =" + curOrder.getOrderId() + "-Buy-" + curOrderPrice + " Matched with= " + "Oreder Number =" + currentOrder.getOrderId() + "-Sell-" +currentOrderPrice + "\r\n", ActorRef.noSender());
                curOrder.setQuantity(quantity - currentQuantity);
                currentOrder.setQuantity(currentQuantity - quantity);
            } else {
                break;
            }

        }

    }
}
