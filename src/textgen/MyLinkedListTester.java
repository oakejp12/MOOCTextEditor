/**
 *
 */
package textgen;

import static org.junit.Assert.*;


import com.sun.istack.internal.NotNull;
import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 * @author Johan Oakes
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH = 10;

	private MyLinkedList<String> shortList;
	private MyLinkedList<Integer> emptyList;
	private MyLinkedList<Integer> longerList;
	private MyLinkedList<Integer> list1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<>();
		shortList.add("A");
		shortList.add("B");

		emptyList = new MyLinkedList<>();

		longerList = new MyLinkedList<>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}

		list1 = new MyLinkedList<>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

	}


	/**
     *  Test if the get method is working correctly.
	 */
	@Test
	public void testGet() {

		// Test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		// Test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try {
			shortList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			shortList.get(2);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		// Test longer list contents
		for (int i = 0; i < LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check " + i + " element", (Integer)i, longerList.get(i));
		}

		// Test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

	}


	/**
     * Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.
     */
	@Test
	public void testRemove() {

        int a = list1.remove(0);
        assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());

        String b = shortList.remove(0);
        assertEquals("Remove: check b is correct ", "A", b);
        assertEquals("Remove: check element 0 is correct ", "B", shortList.get(0));
        assertEquals("Remove: check size is correct ", 1, shortList.size());

        try {
            list1.remove(-1);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException ex) {

        }

        try {
            list1.remove(3);
            fail("Check out of bounds");
        } catch (IndexOutOfBoundsException ex) {

        }

        int d = longerList.remove(1);
        assertEquals("Remove: check d is correct ", 1, d);
        assertEquals("Remove: check element 1 is correct ", (Integer) 2, longerList.get(1));

        try {
            emptyList.remove(0);
            fail("Check empty list");
        } catch (IndexOutOfBoundsException ex) {

        }
	}

	/**
     * Test adding an element into the end of the list, specifically
	 * public boolean add(E element)
	 */
	@Test
	public void testAddEnd() {
        boolean added = shortList.add("C");
        assertEquals("Add: check a is correct ", "C", shortList.get(shortList.size() - 1));
        assertEquals("Add: check a returns true", Boolean.TRUE, added);
        assertEquals("Add: check b is the second element", "B", shortList.get(1));

	    added = emptyList.add(1);
	    assertEquals("Add: check empty list was added to ", (Integer) 1, emptyList.get(0));
	    assertEquals("Add: check added returns true ", Boolean.TRUE, added);
	}


	/**
     *  Test the size of the list
     */
	@Test
	public void testSize() {
		// TODO: implement this test
	}



	/**
     * Test adding an element into the list at a specified index,
	 * specifically: public void add(int index, E element)
	 */
	@Test
	public void testAddAtIndex() {
        // TODO: implement this test

	}

	/**
     *  Test setting an element in the list
     */
	@Test
	public void testSet() {
	    // TODO: implement this test

        String a = shortList.set(0, "Z");
        assertEquals("Set: check element was inserted ", "Z", shortList.get(0));
        assertEquals("Set: check a is correct ", "Z", a);
        assertEquals("Set: check size ", 2, shortList.size());

        int b = longerList.set(3, 15);
        assertEquals("Set: check element was inserted ", (Integer) 15, longerList.get(3));
	}


	// TODO: Optionally add more test methods.

}
