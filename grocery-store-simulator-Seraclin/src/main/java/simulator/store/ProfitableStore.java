package simulator.store;

import simulator.checkout.*;
import simulator.grocery.GroceryInterface;

import java.util.LinkedList;
import java.util.List;

public class ProfitableStore extends SimpleStore {
    public List<Register> reg2;
    public List<CheckoutLineInterface> lines2;


    public ProfitableStore() {
        reg2 = new LinkedList<>();
        reg2.add(new Register());
        reg2.add(new Register());
        reg2.add(new Register());

        lines2 = new LinkedList<CheckoutLineInterface>();
        lines2.add(new NormalLine()); // line 1
        lines2.add(new ExpressLine()); // line 2, only if 15 items or less
        lines2.add(new NormalLine());

    }
    @Override
    public void tick() {  // shoppers irate 300 ticks; .15 per tick; 10 to turn on; 43200 ticks total
        for (int i = 0; i < reg2.size(); i++) {
            if (!reg2.get(i).isBusy() && !lines2.get(i).isEmpty()) {
                reg2.get(i).turnOn();
                reg2.get(i).processShopper(lines2.get(i).dequeue());
            }
            if (lines2.get(i).isEmpty() && !reg2.get(i).isBusy()) {
                reg2.get(i).turnOff();
            }
        }
      if (getAverageWaitingTime() > 300) { // open a new line when the average time equals irate time
           reg2.add(new Register());
           lines2.add(new NormalLine());
       }
    }
    @Override
    public List<CheckoutLineInterface> getLines() { // shoppers always enter shortest line they canEnter
        return lines2;
    }
    @Override
    public List<Transaction> getTransactions() {
        trans.clear();
        for (Register rt : reg2) {
            trans.addAll(rt.getTransactions());
        }
        return trans;
    }
    @Override
    public double getTotalCost() {
        double totalCost = 0;
        for (Register r : reg2) {
            totalCost += r.getRunningCost();
        }
        for (Transaction t: trans) {
            for (GroceryInterface gr : t.getReceipt().getGroceries()) {
                totalCost += gr.getCost();
            }
        }
        return totalCost;
    }


}
