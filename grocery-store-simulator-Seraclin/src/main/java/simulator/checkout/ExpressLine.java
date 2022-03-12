package simulator.checkout;

import simulator.shopper.Shopper;
import structures.QueueInterface;

public class ExpressLine extends NormalLine {
    @Override
    public boolean canEnterLine(Shopper shopper) {
        if (shopper == null) {
            throw new NullPointerException();
        }
        if (shopper.getShoppingList().size() > 15) { // only 15 items or less for express; 16 or more is too much
            return false;
        }
        return true;
    }

    @Override
    public QueueInterface<Shopper> enqueue(Shopper shopper) {
        if (shopper == null) {
            throw new NullPointerException();
        }
        if (shopper.getShoppingList().size() > 15) {
            throw new IllegalArgumentException();
        }
        return super.enqueue(shopper);
    }

}
