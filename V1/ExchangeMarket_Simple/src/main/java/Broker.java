import java.io.IOException;
import java.util.List;

public class Broker {
	private String name;
	private Long brokerId;

	public Broker(String _name, Long _brokerId, List<EquityOrder> equityOrders, Market market) throws InterruptedException, IOException {
		this.setName(_name);
		this.setBrokerId(_brokerId);
		for (EquityOrder equityOrder : equityOrders) {
			SendOrder(market, equityOrder);
		}
	}
	public Broker(String _name, Long _brokerId, Market market) {
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

	public void SendOrder(Market market, Order order) throws InterruptedException, IOException {
		market.AddOrder(order);

	}

}
