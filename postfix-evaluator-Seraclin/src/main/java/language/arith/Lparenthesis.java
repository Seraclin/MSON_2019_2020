package language.arith;

import language.Operand;

/**
 * The {@code Lparenthesis} is an operator that signals the start of a parenthetical statement
 * @author Seraclin
 *
 */
public class Lparenthesis extends unaryOperator<Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Operand<Integer> performOperation() { // parenthesis '(' never uses performOperation()
        return null;
    }

    @Override
    public int getPrecedence() { // precedence should be low
        int precedence = -10;
        return precedence;
    }

    @Override
    public int getNumberOfArguments() {
        return -1;
    } // parenthesis don't perform operations



}