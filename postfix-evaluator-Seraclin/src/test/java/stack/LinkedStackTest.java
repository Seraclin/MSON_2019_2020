package stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LinkedStackTest {

  private StackInterface<Integer> stack;
  private StackInterface<String> stack2;


  @Before
  public void setup() {
    stack = new LinkedStack<Integer>();
    stack2 = new LinkedStack<String>();

  }

  @Test (timeout = 5000)
  public void testStack() {
    assertTrue("Stack should be empty after being constructed.", stack.isEmpty());
    assertEquals("Stack should contain zero elements after being constructed.", 0, stack.size());

    stack.push(5);
    assertFalse("Stack should not be empty.", stack.isEmpty());
    assertEquals("The top element should be 5", new Integer(5), stack.top());
    assertEquals("The stack should contain one element.", 1, stack.size());

    stack.push(4);
    assertEquals("The top element should be 4", new Integer(4), stack.top());
    assertEquals("The stack should contain two elements.", 2, stack.size());

    Integer t = stack.pop();
    assertEquals("The popped element should be 4", new Integer(4), t);
    assertEquals("The top element should be 5", new Integer(5), stack.top());
    assertEquals("The stack should contain one element.", 1, stack.size());
    assertFalse("The stack should not be empty.", stack.isEmpty());

    t = stack.pop();
    assertEquals("The popped element should be 5", new Integer(5), t);
    assertTrue("The stack should be empty.", stack.isEmpty());
  }

  @Test (timeout = 5000, expected = StackUnderflowException.class)
  public void testStackUnderflowPop() {
    stack.pop();
  }

  @Test (timeout = 5000, expected = StackUnderflowException.class)
  public void testStackUnderflowTop() {
    stack.top();
  }

  @Test (timeout = 5000)
  public void testStackString() {
    assertTrue("Stack should be empty after being constructed.", stack2.isEmpty());
    stack2.push("ABC");
    assertFalse("Stack should not be empty.", stack2.isEmpty());
    assertEquals("The top element should be ABC", new String("ABC"), stack2.top());
    String t= stack2.pop();
    assertEquals("The popped element should be ABC", new String("ABC"), t);
    assertTrue("The stack should be empty.", stack2.isEmpty());


  }


}
