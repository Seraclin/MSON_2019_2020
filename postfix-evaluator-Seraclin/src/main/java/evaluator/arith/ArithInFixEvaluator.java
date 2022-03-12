package evaluator.arith;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import language.Operand;
import language.Operator;
import parser.arith.ArithPostFixParser;
import stack.LinkedStack;
import stack.StackInterface;
import stack.StackUnderflowException;


/**
 * An {@link ArithInFixEvaluator} is an in fix evaluator over simple arithmetic expressions in order of precedence.
 *
 */
public class ArithInFixEvaluator implements PostFixEvaluator<Integer> {

    private final StackInterface<Operand<Integer>> stack;
    private final StackInterface<Operator> stack2;

    /**
     * Constructs an {@link ArithInFixEvaluator}.
     */
    public ArithInFixEvaluator() {
        this.stack = new LinkedStack<Operand<Integer>>();
        this.stack2 = new LinkedStack<Operator>();
    }

    /**
     * Evaluates a Infix expression.
     * @return the result
     */
    @Override
    public Integer evaluate(String expr) {
        ArithPostFixParser parser = new ArithPostFixParser(expr);
        while (parser.hasNext()) {
            switch (parser.nextType()) {
                case OPERAND: // the numbers
                    stack.push(parser.nextOperand());
                    break;
                case OPERATOR: // precedence from (hi to lo): ^ , */, +- )(
                    Operator operator1 = parser.nextOperator();
                    int numArgs1 = operator1.getNumberOfArguments();
                    if (numArgs1 == -1) { // for left (
                        stack2.push(operator1);
                    } else if (numArgs1 == -2) { // for right )
                        while (stack2.top().getNumberOfArguments() != -1) {
                            Operator next = stack2.pop();
                            performArg(next);
                        }
                        stack2.pop(); // pop off the (
                        break;
                    } else if (stack2.isEmpty() || (operator1.getPrecedence() > stack2.top().getPrecedence())) { // if higher or equal precedence add to stack
                        stack2.push(operator1);
                    } else if (!stack2.isEmpty() && operator1.getPrecedence() <= stack2.top().getPrecedence()) { // urOp is lower or equal the previous operator
                        while (!stack2.isEmpty() && !stack.isEmpty() && operator1.getPrecedence() <= stack2.top().getPrecedence()) {
                            Operator op0 = stack2.pop();
                            performArg(op0);
                        }
                        stack2.push(operator1);
                    }
                    break;
                default:
                    if (stack.size() > 1) {
                        throw new IllegalPostFixExpressionException("This infix expression is not valid");
                    }
                    System.out.println("Why are we here? You did something wrong!");
            }
        }
        while (stack.size() >= 1 && !stack2.isEmpty()) {
            Operator p1 = stack2.pop();
            performArg(p1);
        }
        if (stack.size() > 1) {
            throw new IllegalPostFixExpressionException("No more operators!");
        }
        if (stack.isEmpty()) {
            throw new IllegalPostFixExpressionException("Invalid infix expression: nothing left");
        }
        if (!stack2.isEmpty()) {
            throw new StackUnderflowException("Invalid infix expression: no more operands");
        }

        return stack.pop().getValue();
    }

    /**
     *Performs the operation for the specified operator.
     * @param op Operator to perform operation with.
     */
    public void performArg(Operator op) {
        int numArgs = op.getNumberOfArguments();
        if (numArgs == 1) {
            Operand<Integer> op0 = stack.pop();
            op.setOperand(0, op0);
            Operand<Integer> result = op.performOperation();
            stack.push(result);
        } else if (numArgs == 2) {
            Operand<Integer> op1 = stack.pop(); // remember this is a stack= last in, first out
            Operand<Integer> op0 = stack.pop();
            op.setOperand(1, op1);
            op.setOperand(0, op0);
            Operand<Integer> result2 = op.performOperation();
            stack.push(result2);
        }
    }
}
