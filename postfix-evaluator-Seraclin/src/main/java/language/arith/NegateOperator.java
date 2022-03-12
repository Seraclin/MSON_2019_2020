package language.arith;

import language.Operand;
import language.Operator;

/**
 * The {@code NegateOperator} is an operator that performs negation on a single integer
 * @author jcollard, jddevaug
 *
 */
public class NegateOperator extends unaryOperator<Integer> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Operand<Integer> performOperation() {
    Operand<Integer> op0 = this.getOp0();
    if (op0 == null) {
      throw new IllegalStateException("Could not perform operation prior to operands being set.");
    }
    Integer result = op0.getValue() * -1;
    return new Operand<Integer>(result);
  }

  @Override
  public int getPrecedence() { // precedence is like multiplication
    int precedence = 2;
    return precedence;
  }


  }




