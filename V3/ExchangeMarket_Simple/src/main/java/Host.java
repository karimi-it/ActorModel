import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import Enums.BuySell;
import Enums.OrderType;

public class Host {
    public static  int brokerCont ;
    public static int markertCount;
    public static int transactionCount;
    public static int symbolsCont;
    // public  List<EquityOrder> orders = new ArrayList();
    public static List<Broker> brokers =  new ArrayList();

    public static void fillDataSet() {

        try {
            BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter Transaction Count :");
            transactionCount = Integer.parseInt(reader.readLine());
            System.out.println("Enter Brocker Count :");
             brokerCont = Integer.parseInt(reader.readLine());
            System.out.println("Enter Market Count :");
            markertCount =Integer.parseInt(reader.readLine());
            System.out.println("Enter Symbol Count :");
            symbolsCont =Integer.parseInt(reader.readLine());


            FileWriter writer = new FileWriter("D:\\DataSet.txt", false);
            ArrayList<String> symbols =new  ArrayList<String>();
            for(int i=0 ; i< symbolsCont ; i++) {
                symbols.add("sym_"+i);
            }
            ArrayList<String> markets =new  ArrayList<String>();
            for(int i=0 ; i < markertCount ; i++)
            {
                markets.add("market_"+i);
            }
            int broker = brokerCont;

            for(String i : symbols) {
                writer.write(i+" ");
            }
            writer.write("\r\n");
            for(String i : markets) {
                writer.write(i+" ");
            }
            writer.write("\r\n");
            writer.write(String.valueOf(broker) );
            writer.write("\r\n");
            writer.write(String.valueOf(transactionCount) );
            writer.write("\r\n");
            String[] types = new String[]{"regular"};
            String[] buysells = new String[]{"Sell","Buy"};
            int[] prices = new int[] {100,10,50,150,200,300,180,30};
            int[] quantities = new int[] {100,10,50,150,200,300,180,30};
            for(int i=0 ; i< transactionCount ; i++) {
                Random rand = new Random();
                String type = types[rand.nextInt(types.length)];
                String buysell = buysells[rand.nextInt(buysells.length)];
                String symbol =symbols.get( rand.nextInt(symbols.size()));
                String market =markets.get( rand.nextInt(markets.size()));
                int price = prices[rand.nextInt(prices.length)];
                int quantity= quantities[rand.nextInt(quantities.length)];
                writer.write( market+" "+symbol+" "+type+" "+buysell+" "+price+" "+quantity+" "+(rand.nextInt(broker ) + 1));
                writer.write("\r\n");
            }
            writer.close();
        }
        catch(Exception ex) {

        }
    }
    public static void RunSimpleExchangeMarket() throws IOException {
        String ResultTitle="Simple-Result.txt";
        System.out.println("Generate Report Title");
        System.out.println("Enter 1 For Default");
        System.out.println("Enter 2 For Generate Custom !");
        BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
        int programType = Integer.parseInt(reader.readLine());
        if(programType==1) {
            ResultTitle="Simple-Result.txt";
        }
        else if(programType==2){
            ResultTitle= transactionCount+"-"+markertCount+"M-"+brokerCont+"B-"+symbolsCont+"S-"+ "Simple-Result.txt";
        }
        Market market;
        List<Market> markets = new ArrayList<>();
        long startTime=0;
        String transactionCount="";
        try {
            File result = new File(ResultTitle);
            result.delete();
            File file = new File("D:\\DataSet.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            startTime = System.nanoTime();
            String st;
            int i = 0;
            List<String> dataSet = new ArrayList<String>();
            while ((st = br.readLine()) != null)
            {
                dataSet.add(st);
            }
            br.close();
            String[] symbols= {} ;
            for(String row : dataSet)
            {
                String[] info = row.split(" ");
                if (i == 0) {
                    symbols = info;
                }
                else if (i == 1)
                {
                    for (String m : info) {
                        market = new Market(m,symbols);
                        markets.add(market);
                    }

                }
                else if (i == 2)
                {
                    for(int b= 1; b <= Integer.parseInt(info[0])  ; b++)
                    {
                        brokers.add(new Broker("broker_"+b,(long)b));

                    }
                }
                else if(i == 3)
                {
                    transactionCount= info[0];
                }
                else {
                    EquityOrder order = new EquityOrder((long) i,info[1], OrderType.valueOf(info[2]), BuySell.valueOf(info[3]),
                            Double.valueOf(info[4]), Integer.valueOf(info[5]));
                    Broker b = brokers.get(Integer.parseInt( info[6]) - 1);
                    for(Market m : markets)
                    {
                        if(m.getName().equals(info[0])  )
                        {
                            b.SendOrder(m,order);
                            break;
                        }
                    }

                }
                i++;
            }
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            double seconds = (double)totalTime / 0x1.dcd65p29;
            FileWriter writer = new FileWriter(ResultTitle, true);

            Symbol.result=Symbol.result.concat("Transaction Count : "+transactionCount+" , ");
            Symbol.result=Symbol.result.concat("Total Time : "+seconds);
            writer.write(Symbol.result);
            writer.close();
            System.out.println(seconds);
        } catch (IOException  e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Select one of the item :");
        System.out.println("Enter 1 For Generate DataSet !");
        System.out.println("Enter 2 For Run Simple Exchange  Market !");
        System.out.println("Enter 3 For  Generate DataSet & Run Simple Exchange  Market !");
        BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
        int programType = Integer.parseInt(reader.readLine());
        if(programType==1) {
            fillDataSet();
        }
        else if(programType==2){
            RunSimpleExchangeMarket();
        }
        else if(programType==3){
            fillDataSet();
            RunSimpleExchangeMarket();
        }
        else {
            System.out.println("you should Entered 1 Or 2");
        }
        makeMeAwareTaskCompleted();
    }
    public static void  makeMeAwareTaskCompleted()
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


