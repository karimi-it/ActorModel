import akka.actor.ReceiveTimeout;
import akka.actor.UntypedAbstractActor;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class Logger extends UntypedAbstractActor {

	Long StartTime;
	int Counter;
	int Count;
	String result = "";
	long endTime;
	long totalTime;
	double seconds;
	public Logger(Long startTime , int count)
	{
		Count = count;
		Counter = 0;
		StartTime = startTime;
		// To set an initial delay
		getContext().setReceiveTimeout(Duration.ofSeconds(5));

	}

	private Logger onPostStop() {
		System.out.println("logger stoped");
		return this;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		/*getContext().setReceiveTimeout(Duration.ofSeconds(20));*/
		if(msg instanceof ReceiveTimeout) {

			try {
				this.totalTime = this.endTime - this.StartTime;
				this.seconds = (double)totalTime / 0x1.dcd65p29;
				this.result=this.result.concat("Transaction Count : "+Count+" ,  Total Time : "+seconds);
				FileWriter writer = new FileWriter("ActorModel-Result.txt", true);
				writer.write(this.result);
				writer.close();
				System.out.println(seconds);
				getContext().stop(getSelf());
				makeMeAwareTaskCompleted();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}


		}   else {
			Counter++;
			this.endTime   = System.nanoTime();
			result=result.concat(msg.toString());
			if(Counter >= 5000) {
				FileWriter writer = new FileWriter("ActorModel-Result.txt", true);
				writer.write(this.result);
				writer.close();
				Counter = 0;
				this.result = "";
			}
		}
	}

	public  void  makeMeAwareTaskCompleted()
	{
		for (int t=0;t<=1000;t++)
		{
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			java.awt.Toolkit.getDefaultToolkit().beep();
		}
	}
}
