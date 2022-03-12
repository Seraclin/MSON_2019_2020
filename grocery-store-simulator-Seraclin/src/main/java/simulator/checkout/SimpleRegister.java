package simulator.checkout;

import simulator.shopper.Shopper;

public class SimpleRegister extends AbstractRegister {
    @Override
    public Transaction createTransaction(Shopper s) {
        int time = 0;
        if (s.getShoppingList().size() == 0) {
            time = 1;
        } else {
            time = s.getShoppingList().size() * 4;
        }
        return new Transaction(new Receipt(s.getShoppingList(), 0), s, time);
    }
}
