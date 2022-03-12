import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueueTest {
	private RandomizedQueue<String> list;

	@Before
	public void setUp() throws Exception {
		StdRandom.setSeed(10);
		list = new RandomizedQueue<String>();
	}

	@Test
	public void testIsEmpty() {
		assertEquals("List should be empty", 0, list.size());
		assertTrue("Should be empty", list.isEmpty());

		list.enqueue("A");
		assertFalse("Should not be empty", list.isEmpty());

		list.dequeue();
		assertTrue("Should be empty", list.isEmpty());
	}

	@Test
	public void testSize() {
		assertEquals("Should have 0 elements", 0, list.size());
		list.enqueue("A");
		assertEquals("Should have 1 elements", 1, list.size());
		list.enqueue("B");
		assertEquals("Should have 2 elements", 2, list.size());

		list.dequeue();
		assertEquals("Should have 1 elements", 1, list.size());
		list.dequeue();
		assertEquals("Should have 0 elements", 0, list.size());
	}

	@Test
	public void testEnqueue() {
		ArrayList<String> expectedRes = new ArrayList<String>();
		assertEquals("Should have 0 elements", 0, list.size());

		expectedRes.add("Hello");
		list.enqueue("Hello");
		assertEquals("Should have 1 element", 1, list.size());

		String actualResult = list.dequeue();
		assertEquals("Should have 0 elements", 0, list.size());
		assertTrue("Should contain " + actualResult, expectedRes.contains(actualResult));
		expectedRes.remove(actualResult);

		expectedRes.add("World");
		list.enqueue("World");
		actualResult = list.dequeue();
		assertTrue("Should contain other word" + actualResult, expectedRes.contains(actualResult));
		assertEquals("Should have 0 elements", 0, list.size());

	}

	@Test
	public void testDequeue() {
		ArrayList<String> expectedRes = new ArrayList<String>();
		expectedRes.add("Hello");
		expectedRes.add("World");
		expectedRes.add("Gecko");

		list.enqueue("Hello");
		list.enqueue("World");
		list.enqueue("Gecko");

		assertEquals("Should have 3 elements", 3, list.size());
		String actualResult = list.dequeue();
		assertTrue("Should contain" + actualResult, expectedRes.contains(actualResult));
		expectedRes.remove(actualResult);

		assertEquals("Should have 2 elements", 2, list.size());
		actualResult = list.dequeue();
		assertTrue("Should contain other word " + actualResult, expectedRes.contains(actualResult));
		expectedRes.remove(actualResult);

		assertEquals("Should have 1 elements", 1, list.size());
		actualResult = list.dequeue();
		assertEquals("Should have 0 elements", 0, list.size());
		assertTrue("Should contain other word " + actualResult, expectedRes.contains(actualResult));
		expectedRes.remove(actualResult);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testSample() {
		ArrayList<String> expectedRes = new ArrayList<String>();
		expectedRes.add("Hello");
		expectedRes.add("World");
		expectedRes.add("Gecko");

		list.enqueue("Hello");
		list.enqueue("World");
		list.enqueue("Gecko");
		assertTrue("Sample from randomized queue", expectedRes.contains(list.sample()));
	}

	@Test
	public void testIterator() {
		ArrayList<String> expectedRes = new ArrayList<String>();
		expectedRes.add("Hello");
		expectedRes.add("World");
		expectedRes.add("Gecko");

		list.enqueue("Hello");
		list.enqueue("World");
		list.enqueue("Gecko");

		Iterator<String> iter = list.iterator();

		assertTrue(iter.hasNext());
		String current = iter.next();
		assertTrue("Should get a random word", expectedRes.contains(current));
		expectedRes.remove(current);

		assertTrue(iter.hasNext());
		String current2 = iter.next();
		assertTrue("Should get a random word", expectedRes.contains(current2));
		expectedRes.remove(current2);

		assertTrue(iter.hasNext());
		String current3 = iter.next();
		assertTrue("Should get a random word", expectedRes.contains(current3));
		expectedRes.remove(current3);
	}

	@Test (timeout = 500, expected = IllegalArgumentException.class)
	public void testExceptionOnEnqueueNull() {
		list.enqueue(null);
	}
	@Test (timeout = 500, expected = NoSuchElementException.class)
	public void testExceptionOnEmptySample() {
		list.sample();
	}
	@Test (timeout = 500, expected = NoSuchElementException.class)
	public void testExceptionOnEmptyDequeue() {
		list.dequeue();
	}
	@Test (timeout = 500, expected = UnsupportedOperationException.class)
	public void testExceptionOnRemoveIterator() {
		list.iterator().remove();
	}

}
