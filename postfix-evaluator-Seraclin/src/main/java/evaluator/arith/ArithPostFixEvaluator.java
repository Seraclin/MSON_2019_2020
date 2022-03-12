package evaluator.arith;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import language.Operand;
import language.Operator;
import parser.arith.ArithPostFixParser;
import stack.LinkedStack;
import stack.StackInterface;


/**
 * An {@link ArithPostFixEvaluator} is a post fix evaluator over simple arithmetic expressions.
 *
 */
public class ArithPostFixEvaluator implements PostFixEvaluator<Integer> {

  private final StackInterface<Operand<Integer>> stack;

  /**
   * Constructs an {@link ArithPostFixEvaluator}.
   */
  public ArithPostFixEvaluator() {
    this.stack = new LinkedStack<Operand<Integer>>();
  }

  /**
   * Evaluates a postfix expression.
   * @return the result
   */
  @Override
  public Integer evaluate(String expr) {
    // create the algorithm to do post fix evaluation

    ArithPostFixParser parser = new ArithPostFixParser(expr);
    while (parser.hasNext()) {
      switch (parser.nextType()) {
        case OPERAND: // the numbers
          stack.push(parser.nextOperand());
          break;
        case OPERATOR: // the +-*/! ; works for unary operations
          Operator operator1 = parser.nextOperator();
          int numArgs = operator1.getNumberOfArguments();
          if (stack.isEmpty() || (numArgs > 1 && stack.size() == 1)) {
            throw new IllegalPostFixExpressionException("Invalid postfix expression: no more operands");
          }

          if (numArgs == 1) {
            Operand<Integer> op0 = stack.pop();
            operator1.setOperand(0, op0);
            Operand<Integer> result = operator1.performOperation();
            stack.push(result);
          } else if (numArgs == 2) {
            Operand<Integer> op1 = stack.pop(); // remember this is a stack= last in, first out
            Operand<Integer> op0 = stack.pop();
            operator1.setOperand(1, op1);
            operator1.setOperand(0, op0);
            Operand<Integer> result2 = operator1.performOperation();
            stack.push(result2);
          }

          break;
        default:
          if (stack.size() > 1) {
            throw new IllegalPostFixExpressionException("This postfix expression is not valid");
          }
          System.out.println("Why are we here? You did something wrong!");
      }
    }
    if (stack.size() > 1) {
      throw new IllegalPostFixExpressionException("Invalid postfix expression: no more operators");
    }

    return stack.pop().getValue();
  }

}
