package stack;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class stackTest {

    private maxStackInterface<Integer> stack;


    @Before
    public void setup() {
        stack = new LinkedStack<Integer>();
    }

    @Test (timeout = 5000)
    public void testStack() {
        assertTrue("Stack should be empty after being constructed.", stack.isEmpty());
        assertEquals("Stack should contain zero elements after being constructed.", 0, stack.size());

        stack.push(5);
        assertFalse("Stack should not be empty.", stack.isEmpty());
        assertEquals(5,stack.max());
        stack.push(7);
    }


}
