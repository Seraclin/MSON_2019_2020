package simulator.checkout;

import simulator.shopper.Shopper;
import structures.LinkedQueue;
import structures.QueueInterface;

public class NormalLine extends LinkedQueue<Shopper> implements CheckoutLineInterface {

    @Override
    public boolean canEnterLine(Shopper shopper) {
        if (shopper == null) {
            throw new NullPointerException();
        }
        return true;
    }

    @Override
    public QueueInterface<Shopper> enqueue(Shopper shopper) {
        if (shopper == null) {
            throw new NullPointerException();
        }
        return super.enqueue(shopper);
    }
}
