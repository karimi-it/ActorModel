
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.routing.*;

import java.util.ArrayList;
import java.util.List;

public class Router_test  extends UntypedAbstractActor {
    public  String[] symbol  ;
    ActorRef logger ;
    Long time ;
    Router router;

    Router_test(String marketName,String[] symbolNames , ActorRef _logger,Long _time){
    symbol = symbolNames;
    logger = _logger;
    time = _time;
        List<Routee> routees = new ArrayList<Routee>();
        for (int i = 0; i < 1; i++) {
            ActorRef r = getContext().actorOf(Props.create(Market.class,"market"+i, symbol,logger,time));
            getContext().watch(r);
            routees.add(new ActorRefRoutee(r));
        }
        router = new Router(new RoundRobinRoutingLogic(), routees);
    }

    @Override
    public void onReceive( Object message) throws Throwable, Throwable {
        router.route(message, getSender());
    }
}
