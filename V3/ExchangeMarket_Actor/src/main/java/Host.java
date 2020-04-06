import Enums.BuySell;
import Enums.MessageType;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static Enums.MessageSubject.addOrder;

public class Host {

	public static List<ActorRef>  brokers = new ArrayList<>();
	public static void main(String[] args) {
		File myConfigFile = new File("src/main/resources/application.conf");
		Config fileConfig = ConfigFactory.parseFile(myConfigFile);
		Config config;
		config = ConfigFactory.load(fileConfig);
		int nrOfInstances = 5;
		/*	ActorSystem system = ActorSystem.create("ExchangeMarket", config);*/
		ActorSystem system =ActorSystem.create("ExchangeMarket",config);
		String transactionCount="0";
		long startTime = 0;
		try {

			//test for  git server
			File result = new File("ActorModel-Result.txt");
			result.delete();
			File file = new File("D:\\DataSet.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));

			List<String> dataSet = new ArrayList<String>();
			String st;
			while ((st = br.readLine()) != null) {
				dataSet.add(st);
			}
			br.close();
			//get Transaction Cunt from data Set
			transactionCount= dataSet.get(3);


			// ActorRef router = system.actorOf(
			//		 Props.create(Router_test.class, "Market_1", dataSet.get(0).split(" "), logger, startTime).withDispatcher("my-dispatcher"), "router");
			//ActorRef market = system.actorOf(
			//		Props.create(Market.class, "Market_1", dataSet.get(0).split(" "), logger, startTime).withDispatcher("my-dispatcher"), "Market_1");
			int i = 0;
			String[] symbols =dataSet.get(0).split(" ");
			String[] markets = dataSet.get(1).split(" ");

			List<ActorRef> market_symbols = new ArrayList<>();
			startTime = System.nanoTime();
			ActorRef logger = system.actorOf(Props.create(Logger.class, startTime, Integer.parseInt(transactionCount)).withDispatcher("my-dispatcher"), "Logger");
			for(String s :symbols )
			{
				for(String m: markets)
				{

					ActorRef actor = system.actorOf(Props.create(Symbol.class , m+s , logger,startTime).withDispatcher("my-dispatcher"),m+s);
					market_symbols.add(actor);
				}
			}
//			System.out.println((double)( System.nanoTime()-startTime) / 0x1.dcd65p29);
			for (String row : dataSet) {
				String[] info = row.split(" ");
				if (i == 0) {

				}
				else if (i == 1) {

				}
				else if (i == 2)
				{
//					for (int b = 1; b <= Integer.parseInt(info[0]); b++) {
//						brokers.add(system.actorOf(
//								Props.create(Broker.class, "broker_" + String.valueOf(b), (long) b).withDispatcher("my-dispatcher"),
//								"broker_" + b));
//
//					}
				}
				else if (i == 3)
				{
					//ignore this line because  Transaction Cunt is in this line
				}
				else {

					/*System.out.println("Create Order With Id : "+ i+" " +info[0]+" " +info[1]+" " +info[2]+" " +info[3]+" " +info[4]+" " +info[5]);*/
					EquityOrder order = new EquityOrder((long) i,info[1], Enums.OrderType.valueOf(info[2]), BuySell.valueOf(info[3]),
							Double.valueOf(info[4]), Integer.valueOf(info[5]));
//					System.out.println(Integer.parseInt( info[6]));
					//ActorRef b = brokers.get(Integer.parseInt( info[6]) - 1);
					String  s = info[0]+info[1];
					OrderMessage msg = new OrderMessage();
					msg.order = order;
					msg.subject= addOrder;

					msg.messageType = MessageType.order;
					boolean flag = true;
					for(ActorRef a : market_symbols) {
						if(a.path().name().equals(s)) {
							a.tell(msg,ActorRef.noSender());
							break;
						}
					}
				}
				i++;

			}



		} catch (IOException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//	catch (InterruptedException e) {
		//		e.printStackTrace();
		//	}
	}
}
