package language.arith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import language.Operand;
import language.Operator;

import org.junit.Before;
import org.junit.Test;

public class SubOperatorTest {
  Operator<Integer> operator;
  Operand<Integer> op0;
  Operand<Integer> op1;

  Operand<Integer> op2;
  Operand<Integer> op3;

  /**
   * Runs before each test.
   */
  @Before
  public void setup() {
    operator = new SubOperator();
    op0 = new Operand<Integer>(5);
    op1 = new Operand<Integer>(7);
    op2 = new Operand<Integer>(-6);
    op3 = new Operand<Integer>(-10);


  }

  @Test (timeout = 5000)
  public void testPerformOperation() {
    operator.setOperand(0, op0);
    operator.setOperand(1, op1);

    Operand<Integer> result = operator.performOperation();
    int value = result.getValue();
    assertEquals("Operator applied to 5 and 7 should produce -2.", 5 - 7,  value);
  }

  @Test (timeout = 5000)
  public void testPerformOperationPos(){
    operator.setOperand(0, op1);
    operator.setOperand(1, op0);

    Operand<Integer> result = operator.performOperation();
    int value = result.getValue();
    assertEquals("Operator applied to 7 and 5 should produce 2.", 7 - 5,  value);
  }

  @Test (timeout = 5000)
  public void testPerformOperationZero(){
    operator.setOperand(0, op0);
    operator.setOperand(1, op0);

    Operand<Integer> result = operator.performOperation();
    int value = result.getValue();
    assertEquals("Operator applied to 5 and 5 should produce 0", 0,  value);
  }

  @Test (timeout = 5000)
  public void testPerformOperationNegatives(){
    operator.setOperand(0, op2);
    operator.setOperand(1, op3);

    Operand<Integer> result = operator.performOperation();
    int value = result.getValue();
    assertEquals("Operator applied to -6 and -10 should produce 4", 4,  value);
  }


  @Test (timeout = 5000, expected = IllegalArgumentException.class)
  public void testIllegalArgumentException() {
    operator.setOperand(2, op0);
    fail("Binary operator should not except input to position 2");
  }

  @Test (timeout = 5000, expected = NullPointerException.class)
  public void testNullArgumentException() {
    operator.setOperand(0, null);
    fail("Operator should not allow null arguments");
  }

  @Test (timeout = 5000, expected = IllegalStateException.class)
  public void testIllegalStateException() {
    operator.setOperand(0, op0);
    operator.setOperand(0, op0);

    fail("Operator should not allow position 0 to be set more than once");
  }

  @Test (timeout = 5000, expected = IllegalStateException.class)
  public void testIllegalStateExceptionPerform() {
    operator.performOperation();
    fail("Operator should not compute when all arguments have not been set.");
  }

}
