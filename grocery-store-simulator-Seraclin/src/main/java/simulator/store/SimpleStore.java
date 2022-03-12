package simulator.store;

import simulator.checkout.*;
import simulator.grocery.Groceries;
import simulator.grocery.GroceryInterface;

import java.util.LinkedList;
import java.util.List;

public class SimpleStore extends AbstractGroceryStore {
    public List<AbstractRegister> reg;
    public List<CheckoutLineInterface> lines;
    public List<Transaction> trans;
    public SimpleStore() {
        reg = new LinkedList<>();
        reg.add(new SimpleRegister());
        reg.add(new SimpleRegister());
        lines = new LinkedList<CheckoutLineInterface>();
        trans = new LinkedList<>();

        lines.add(new NormalLine()); // line 1
        lines.add(new ExpressLine()); // line 2, only if 15 items or less
    }
    @Override
    public void tick() {
        for (int i = 0; i < reg.size(); i++) {
            if (!reg.get(i).isBusy()) {
                if (!lines.get(i).isEmpty()) {
                    reg.get(i).turnOn();
                    reg.get(i).processShopper(lines.get(i).dequeue());
                }
            }
        }
    }

    @Override
    public List<CheckoutLineInterface> getLines() {
        return lines;
    }

    @Override
    public List<Transaction> getTransactions() {
        trans.clear();
        for (AbstractRegister rt : reg) {
            trans.addAll(rt.getTransactions());
        }
        return trans;
    }

    @Override
    public double getAverageWaitingTime() {
        double totalWaitingTime = 0;
        int numShoppers = getTransactions().size();
        for (Transaction time:getTransactions()) {
            totalWaitingTime += time.getShopper().getWaitingTime();
        }
        return totalWaitingTime / numShoppers;
    }

    @Override
    public double getTotalSales() {
        double totalMoney = 0;
        for (Transaction s: trans) {
            totalMoney += s.getReceipt().getSubtotal();
        }
        return totalMoney;
    }

    @Override
    public double getTotalCost() {
        double totalCost = 0;
        for (AbstractRegister r : reg) {
            totalCost += r.getRunningCost();
        }
        for (Transaction t: trans) {
            for (GroceryInterface gr : t.getReceipt().getGroceries()) {
                totalCost += gr.getCost();
            }
        }
        return totalCost;
    }

    @Override
    public double getTotalProfit() {
        return getTotalSales() - getTotalCost();
    }

    @Override
    public int getNumberOfShoppers() {
        getTransactions();
        return trans.size();
    }

    @Override
    public int getNumberOfIrateShoppers() {
        int count = 0;
        for (Transaction t: getTransactions()) {
            if (t.getShopper().isIrate()) {
                count++;
            }
        }
        return count;
    }
}
