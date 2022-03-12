package language.arith;

import language.Operand;

/**
 * The {@code Rparenthesis} is an operator that signals the end of a parenthetical statement
 * @author Seraclin
 *
 */
public class Rparenthesis extends unaryOperator<Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Operand<Integer> performOperation() { // parenthesis ')' never uses performOperation()
        return null;
    }

    @Override
    public int getPrecedence() { // precedence should be low
        int precedence = -10;
        return precedence;
    }

    @Override
    public int getNumberOfArguments() {
        return -2;
    }


}