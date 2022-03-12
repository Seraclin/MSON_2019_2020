package language.arith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import language.Operand;
import language.Operator;

import org.junit.Before;
import org.junit.Test;

public class ExponentOperatorTest {
    private Operator<Integer> operator;

    /**
     * Runs before each test.
     */
    @Before
    public void setup() {
        operator = new ExponentOperator();

    }

    @Test (timeout = 5000)
    public void testPerformOperation() {
        operator.setOperand(0, new Operand<Integer>(2));
        operator.setOperand(1, new Operand<Integer>(3));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to 2 and 3 should produce 8.", 8,  value);
    }

    @Test (timeout = 5000)
    public void testPerformOperationNegative() {
        operator.setOperand(0, new Operand<Integer>(-2));
        operator.setOperand(1, new Operand<Integer>(3));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to -2 and 3 should produce -8.", -8,  value);
    }

    @Test (timeout = 5000)
    public void testPerformOperationNegativeEven() {
        operator.setOperand(0, new Operand<Integer>(-4));
        operator.setOperand(1, new Operand<Integer>(2));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to -4 and 2 should produce 16.", 16,  value);
    }

    @Test (timeout = 5000)
    public void testPerformOperationZero() {
        operator.setOperand(0, new Operand<Integer>(6));
        operator.setOperand(1, new Operand<Integer>(0));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to 6 and 0 should produce 1.", 1,  value);
    }

    @Test (timeout = 5000)
    public void testPerformOperationNegativeDegree() {
        operator.setOperand(0, new Operand<Integer>(6));
        operator.setOperand(1, new Operand<Integer>(-1));

        Operand<Integer> result = operator.performOperation();
        int value = result.getValue();
        assertEquals("Operator applied to 6 and -1 should produce 0.", 0,  value);
    }


    @Test (timeout = 5000)
    public void testGetNumberOfArguments() {
        assertEquals("Binary operator should have 2 arguments.", operator.getNumberOfArguments(), 2);
    }

    @Test (timeout = 5000, expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {
        operator.setOperand(2, new Operand<Integer>(2));
        fail("Binary operator should not accept input to position 2");
    }

    @Test (timeout = 5000, expected = NullPointerException.class)
    public void testNullArgumentException() {
        operator.setOperand(0, null);
        fail("Operator should not allow null arguments");
    }

    @Test (timeout = 5000, expected = IllegalStateException.class)
    public void testIllegalStateException() {
        operator.setOperand(0, new Operand<Integer>(2));
        operator.setOperand(0, new Operand<Integer>(2));

        fail("Operator should not allow position 0 to be set more than once");
    }

    @Test (timeout = 5000, expected = IllegalStateException.class)
    public void testIllegalStateExceptionPerform() {
        operator.performOperation();
        fail("Operator should not compute when all arguments have not been set.");
    }



}
