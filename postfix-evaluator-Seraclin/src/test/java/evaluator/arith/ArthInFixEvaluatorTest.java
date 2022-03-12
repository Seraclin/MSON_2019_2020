package evaluator.arith;

import static org.junit.Assert.assertEquals;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;

import org.junit.Before;
import org.junit.Test;
import stack.StackUnderflowException;

public class ArthInFixEvaluatorTest {

    private ArithInFixEvaluator evaluator;

    @Before
    public void setup() {
        evaluator = new ArithInFixEvaluator();
    }

    @Test (timeout = 5000)
    public void testEvaluateAll() {
        Integer result = evaluator.evaluate("3 * 4 ^ 2 + 5");
        assertEquals(new Integer(53), result);
    }

    @Test (timeout = 5000)
    public void testEvaluatePlus() {
        Integer result = evaluator.evaluate("1 + 2");
        assertEquals(new Integer(3), result);

        result = evaluator.evaluate("1 + 2 + 3");
        assertEquals(new Integer(6), result);

    }

    @Test (timeout = 5000)
    public void testEvaluateSub() {
        Integer result = evaluator.evaluate("1 - 2");
        assertEquals(new Integer(-1), result);

        result = evaluator.evaluate("1 - 2 - 3 - 4");
        assertEquals(new Integer(-8), result);

        result = evaluator.evaluate("1 + 2 - 3");
        assertEquals(new Integer(0), result);
    }

    @Test (timeout = 5000)
    public void testEvaluateMult() {
        Integer result = evaluator.evaluate("3 * 2");
        assertEquals(new Integer(6), result);

        result = evaluator.evaluate("3 * 2 + 4");
        assertEquals(new Integer(10), result);

        result = evaluator.evaluate("3 + 2 * 4");
        assertEquals(new Integer(11), result);
    }

    @Test (timeout = 5000)
    public void testEvaluateDiv() {
        Integer result = evaluator.evaluate("6 / 3");
        assertEquals(new Integer(2), result);

        result = evaluator.evaluate("72 / 9 * 7");
        assertEquals(new Integer(56), result);

        result = evaluator.evaluate("40 + 7 - 70 / 10");
        assertEquals(new Integer(40), result);

        result = evaluator.evaluate("-4 * 20 / 10 + 5 * 7");
        assertEquals(new Integer(27), result);
    }

    @Test (timeout = 5000)
    public void testEvaluateExponent() {
        Integer result = evaluator.evaluate("2 ^ 3");
        assertEquals(new Integer(8), result);

        result = evaluator.evaluate("72 / 9 * 7 + 2 ^ 2");
        assertEquals(new Integer(60), result);

        result = evaluator.evaluate("40 + 7 ^ 2 - 90 / 10");
        assertEquals(new Integer(80), result);

        result = evaluator.evaluate("-4 ^ 3 / 4 + 5 * 7");
        assertEquals(new Integer(19), result);
    }

    @Test (timeout = 5000) // negate is the same as multiplying by -1
    public void testEvaluateNegate() {
        Integer result = evaluator.evaluate("! 1");
        assertEquals(new Integer(-1), result);

        result = evaluator.evaluate("! 2 !");
        assertEquals(new Integer(2), result);

        result = evaluator.evaluate("-15 !");
        assertEquals(new Integer(15), result);

        result = evaluator.evaluate("-15 ! !");
        assertEquals(new Integer(-15), result);

        result = evaluator.evaluate("4 ! ^ 2");
        assertEquals(new Integer(-16), result);

        result = evaluator.evaluate("! 4 * 3");
        assertEquals(new Integer(-12), result);

        result = evaluator.evaluate("1 + 2 ! ^ 3");
        assertEquals(new Integer(-7), result);
    }

   @Test (timeout = 5000) // ( )
    public void testEvaluateParenthesis() {
        Integer result = evaluator.evaluate("( 1 + 2 ) * 3");
        assertEquals(new Integer(9), result);

        result = evaluator.evaluate("( 1 + 2 )");
        assertEquals(new Integer(3), result);

        result = evaluator.evaluate("2 * ( 4 * ( 1 + 2 ) )");
        assertEquals(new Integer(24), result);

        result = evaluator.evaluate("( 4 - 16 ) / 4");
        assertEquals(new Integer(-3), result);

        result = evaluator.evaluate("( ! 2 ) ^ 3");
        assertEquals(new Integer(-8), result);

        result = evaluator.evaluate("4 ^ 2 + 9 * ( ! 8 - 2 )");
        assertEquals(new Integer(-74), result);

       result = evaluator.evaluate("( 8 - 9 * 3 ^ 2 ) + ( 2 * 3 - 1 )");
       assertEquals(new Integer(-68), result);

    }

    @Test (timeout = 5000, expected = IllegalPostFixExpressionException.class)
    public void testInvalidExpression() {
        evaluator.evaluate("1 2");
    }
    @Test (timeout = 5000, expected = IllegalPostFixExpressionException.class)
    public void testInvalidExpressionOperators() {
        evaluator.evaluate("+ -");
    }

    @Test (timeout = 5000, expected = IllegalPostFixExpressionException.class)
    public void testInvalidExpressionOperatorsOperands() {
        evaluator.evaluate("5 + 5 * 6 7 8");
    }

    @Test (timeout = 5000, expected = StackUnderflowException.class)
    public void testInvalidExpressionEmpty() {
        evaluator.evaluate("1 + 7 * 9 /");
    }


}
