package language.arith;
import language.Operand;
import language.Operator;

/**
 * A {@link unaryOperator} is an {@link Operator} that performs an
 * operation on one argument.
 * @author Samantha Lin
 *
 * @param <T> they type of the {@link Operand} being evaluated
 */
public abstract class unaryOperator<T> implements Operator<T> {

    private Operand<T> op0;

    /**
     * Returns the number of arguments.
     * @ return 1
     */
    @Override
    public int getNumberOfArguments() {
        return 1;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setOperand(int i, Operand<T> operand) {
        if (operand == null) {
            throw new NullPointerException("Could not set null operand.");
        }
        if (i != 0) {
            throw new IllegalArgumentException("Binary operator only accepts operands 0"
                    + "but received " + i + ".");
        }
        if (i == 0) {
            if (op0 != null) {
                throw new IllegalStateException("Position " + i + " has been previously set.");
            }
            op0 = operand;
        }
    }

    /**
     * Returns the first operand.
     * @return the first operand
     */
    public Operand<T> getOp0() {
        return op0;
    }


}
