import akka.actor.ActorRef;

public class OrderMessage extends Message {
      public Order order;
      public ActorRef Receiver;
}
