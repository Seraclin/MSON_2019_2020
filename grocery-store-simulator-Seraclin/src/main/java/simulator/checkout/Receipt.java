package simulator.checkout;

import simulator.grocery.Grocery;
import simulator.grocery.GroceryInterface;

import java.util.List;

public class Receipt extends AbstractReceipt {
    private double total;
    /**
     * Creates a {@link AbstractReceipt} with the specified grocery list, and
     * discount. The discount given to a Shopper is a value in the range
     * [0, 1] where 0 is no discount and 1 is a 100% discount.
     *
     * @param groceries the {@link List} of {@link GroceryInterface} items purchased
     * @param discount  any discount given to the Shopper
     * @throws NullPointerException     if {@code groceries} is {@code null}
     * @throws IllegalArgumentException if discount is less than 0 or greater than 1.
     */
    public Receipt(List<GroceryInterface> groceries, double discount) {
        super(groceries, discount);
    }

    @Override
    public double getSubtotal() {
        total = 0;
        for (GroceryInterface g: super.getGroceries()) {
            total += g.getPrice();
        }
        return total;
    }

    @Override
    public double getSaleValue() {
        return total - (total * super.getDiscount());
    }
}
