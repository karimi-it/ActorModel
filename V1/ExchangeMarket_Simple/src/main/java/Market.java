import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Market  {
    String name;
    String[] symbolNames;
    List<Symbol> symbols;
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getSymbols() {
		return symbolNames;
	}

	public void setSymbols(String[] symbols) {
		this.symbolNames = symbols;
	}

	public Market(String marketName,String[] _symbolNames )
	{
    	name = marketName;
    	symbolNames= _symbolNames;
		symbols = new ArrayList<Symbol>();
		Start();
	}

    public void Start() 
    {
    	for (String symbol : symbolNames) {
    		Symbol s = new Symbol(symbol);
    	    symbols.add(s);
    	}
    }
	int transactionReportCunt=0;
    public void AddOrder(Order order) throws IOException {
		transactionReportCunt++;
    	for (Symbol symbol : symbols) {
    		if( symbol.name.equals(order.getInstrument())  ) {
    			symbol.ProcessOrder(order);
    		}
    	}
		if (transactionReportCunt>=5000)
		{
			FileWriter writer = new FileWriter("Simple-Result.txt", true);
			writer.write(Symbol.result);
			writer.close();
			transactionReportCunt=0;
			Symbol.result="";
		}
    }
    public void AddSymbol(String symbolName)
    {
    	 
    }
    public void RemoveSymbol(String symbolName)
    {
    	
    }

}
