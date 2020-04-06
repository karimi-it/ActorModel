import Enums.MessageSubject;
import Enums.MessageType;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedAbstractActor;
import akka.util.Timeout;

import java.util.List;

public class Broker extends UntypedAbstractActor {
    private String name;
    private Long brokerId;

    public Broker(String _name, Long _brokerId) {
        this.setName(_name);
        this.setBrokerId(_brokerId);
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
        if (Msg.subject == MessageSubject.addOrder) {
            OrderMessage orderMsg = (OrderMessage) msg;
            orderMsg.Receiver.tell(orderMsg, getSelf());
        }

    }

}
