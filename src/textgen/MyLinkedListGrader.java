package textgen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MyLinkedListGrader {

	PrintWriter out;

	public String printListForwards(MyLinkedList<Integer> lst)
	{
		LLNode<Integer> curr;
		String ret = "";
		if (lst.head.data == null)
			curr = lst.head.next;
		else
			curr = lst.head;

		while (curr != null && curr.data != null)
		{
			ret += curr.data;
			curr = curr.next;
		}
		return ret;
	}

	public String printListBackwards(MyLinkedList<Integer> lst) {
		LLNode<Integer> curr;
		String ret = "";
		if (lst.tail.data == null)
			curr = lst.tail.prev;
		else
			curr = lst.tail;
		while (curr != null && curr.data != null)
		{
			ret += curr.data;
			curr = curr.prev;
		}
		return ret;
	}

	public void doTest() {
		int incorrect = 0;
		int tests = 0;

		String feedback = "";

		try {
			out = new PrintWriter("grader_output/module3.part1.out", "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		MyLinkedList<Integer> lst = new MyLinkedList<>();
		int nums[] = {1, 2, 3, 4, 5};

		feedback += "** Test #1: Adding to end of list...\n";
		for (int i : nums) {
			lst.add(i);
		}

		feedback += "Got " + printListForwards(lst) + ". \n";


		feedback += "** Test #2: Getting from the middle...\n";
		feedback += "Third element was " + lst.get(2) + ". \n";


		feedback += "** Test #3: Adding to the middle...\n";
		lst.add(2, 6);
		feedback += "Got " + printListForwards(lst) + ". \n";

		feedback += "** Test #4: Testing 'prev' pointers by going through the list backwards...\n";
		feedback += "Got " + printListBackwards(lst) + ". \n";

		feedback += "** Test #5: Testing list size...\n";
		feedback += "Got " + lst.size() + ". \n";

		lst.remove(2);
		feedback += "** Test #6: Removing from the middle...\n";
		feedback += "Got " + printListForwards(lst) + ". \n";

		feedback += "** Test #7: Testing 'prev' pointers on remove by going through the list backwards...\n";
		feedback += "Got " + printListBackwards(lst) + ". \n";

		feedback += "** Test #8: Testing size after remove...\n";
		feedback += "Got " + lst.size() + ". \n";

		feedback += "** Test #9: Testing add, remove, and add on new list...\n";
		lst = new MyLinkedList<>();
		lst.add(0, 1);
		feedback += "List - Add: " + printListForwards(lst) + " \n";
		lst.remove(0);
		feedback += "List - Remove: " + printListForwards(lst) + "\n";
		lst.add(0, 1);
		feedback += "Got " + printListForwards(lst) + ". \n";

		feedback += "** Test 10: Checking size after previous test...\n";
		feedback += "List size is " + lst.size() + ". \n";

		feedback += "** Tests 11-20: Testing method bounds...\n";

		out.println(feedback + "Tests complete. Check that everything is as expected.\n");
		out.close();

	}

	public static void main(String args[])
	{
		MyLinkedListGrader grader = new MyLinkedListGrader();
		grader.doTest();
	}


}
