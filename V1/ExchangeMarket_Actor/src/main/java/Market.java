import Enums.MessageSubject;
import Enums.MessageType;
import akka.actor.*;
import akka.actor.UntypedAbstractActor;
import akka.routing.BalancingPool;
import akka.routing.BroadcastPool;
import akka.routing.RoundRobinPool;

import java.util.ArrayList;
import java.util.List;

public class Market extends UntypedAbstractActor {
    String name;
    String[] symbols;
    List<ActorRef> symbolActors;
    ActorRef logger;
    Long StartTime ;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getSymbols() {
		return symbols;
	}

	public void setSymbols(String[] symbols) {
		this.symbols = symbols;
	}

	public Market(String marketName,String[] symbolNames , ActorRef _logger,Long time)
	{
		StartTime = time;
    	name = marketName;
		symbols= symbolNames;
		symbolActors = new ArrayList<ActorRef>();
		logger = _logger;
		Start();
	}

    public void Start() 
    {
    	for (String symbol : symbols) {
//    		System.out.println(this.name +" -> "+ symbol +" start");
    		ActorRef a = getContext().actorOf(Props.create(Symbol.class ,symbol , logger,StartTime),symbol);
    	    symbolActors.add(a);
    	}
    }
    public void AddSymbol(String symbolName)
    {
    	
    }
    public void RemoveSymbol(String symbolName)
    {
    	
    }
	@Override
	public void onReceive(Object msg) throws Exception {
		//System.out.println(this.name +"   "+ this.getSelf());
    	//System.out.println("actor "+this.sender().path().name() );
	    Message Msg = (Message)msg;
	    if(Msg.messageType == MessageType.order)
	    {
	     OrderMessage orderMessage = (OrderMessage)msg;
	     orderMessage.subject = MessageSubject.addOrder;
	     ActorRef actor = null;
	     for(ActorRef a : symbolActors)
	     {
	    	if(a.path().name().equals(orderMessage.order.getInstrument()) )
	    	{
	    		actor = a;
	    	}
	     }
	     if(actor != null)
	     {
//			 System.out.println(this.name+" tell to symbol actor " +actor.path().name() +" OrderId "+orderMessage.order.getOrderId());
	         actor.tell(orderMessage, getSender());
	     }
	/*     else {
	    	 Message m = new Message();
	    	 m.messageType = MessageType.simple;
	    	 m.subject = MessageSubject.failed;
	    	 getSender().tell(m, getSelf());
	     }*/
	    
	    }
		
	}

}
