package simulator.checkout;

import simulator.grocery.Groceries;
import simulator.grocery.GroceryInterface;
import simulator.shopper.Shopper;

public class Register extends SimpleRegister {
    @Override
    public Transaction createTransaction(Shopper s) { // how fast to process a shopper
        int time = 0;
        if (s.getShoppingList().size() == 0) {
            time = 1;
        } else {
            time = (int) getTotalHandlingRate(s) * 12; // what to do here? make fast/slow; 4 seems to work well
        } // s.getShoppingList().size() * 4; vs. (int) getTotalHandlingRate(s) * 12
        return new Transaction(new Receipt(s.getShoppingList(), 0), s, time);
    }

    public double getTotalHandlingRate(Shopper s1) {
        double total = 0;
        for (GroceryInterface g : s1.getShoppingList()) {
            total += g.getHandlingRating();
        }
        return total;
    }
}
