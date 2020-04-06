import Enums.MessageSubject;
import Enums.MessageType;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

import java.util.List;

public class Broker extends UntypedAbstractActor {
	private String name;
	private Long brokerId;
  private ActorRef market;
	public Broker(String _name, Long _brokerId, List<EquityOrder> equityOrders, ActorRef market) {
		this.setName(_name);
		this.setBrokerId(_brokerId);
		for (EquityOrder equityOrder : equityOrders) {
			equityOrder.setOwner(getSelf());
			SendOrder(market, equityOrder);
		}
	}

	public Broker(String _name, Long _brokerId,  ActorRef _market) {
		this.setName(_name);
		this.setBrokerId(_brokerId);
		market = _market;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public void SendOrder(ActorRef market, Order order) {
		OrderMessage msg = new OrderMessage();
		msg.messageType = MessageType.order;
		msg.subject = MessageSubject.addOrder;
		msg.order = order;
		market.tell(msg, getSelf());

	}

	@Override
	public void onReceive(Object msg) throws Exception {
		Message Msg = (Message) msg;
//		if (Msg.subject == MessageSubject.failed) {
//			System.out.println(getSender().path().name() + "  :" + "  Operation Field");
//		}
//		else
			if(Msg.subject == MessageSubject.addOrder)
		{

			OrderMessage orderMsg = (OrderMessage )msg;

			market.tell(orderMsg, getSelf());
		}

	}

}
