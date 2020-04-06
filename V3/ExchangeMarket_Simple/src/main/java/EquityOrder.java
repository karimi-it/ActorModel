import Enums.BuySell;
import Enums.OrderType;

public class EquityOrder extends Order {
	public EquityOrder(Long id,String instrument,OrderType orderType,
			BuySell buySell,double price,int quantity)
			{
				this.setOrderId(id);
			this.setInstrument(instrument); 
			this.setOrderType(orderType); 
			this.setBuySell(buySell); 
			this.setPrice(price); 
			this.setQuantity(quantity);
			
			}
}
