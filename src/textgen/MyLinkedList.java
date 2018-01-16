package textgen;

import org.reactfx.util.LL;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	private int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		this.size = 0;
		this.head = new LLNode<>(null);
		this.tail = new LLNode<>(null);

		// Set pointers
		this.head.next = this.tail;
		this.head.prev = null;

		this.tail.prev = this.head;
		this.tail.next = null;

		this.head.index = -1;
		this.tail.index = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) {

		// Create a new Node to hold the element
		LLNode<E> node = new LLNode<>(element);

		// Assign appropriate links
		node.next = tail; // Point to last sentinel node
		node.prev = tail.prev; // Point to what was tail's prev before node was added

		LLNode<E> prevNode = tail.prev;
		prevNode.next = node; // The last element before insertion points to element created
		tail.prev = node;

		node.index = this.size; // Since we're adding to the back, increment from prev's index

		this.size++; // Increment the size of the list

		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) throws IndexOutOfBoundsException {
		if (index > this.size || index < 0 || this.size == 0)
			throw new IndexOutOfBoundsException("ERROR: Can't access element on that index.");

		// Since we are working with sentinel node, start at the first actual data element
		LLNode<E> current = this.head.next;

		while (current != tail) {
			if (current.index == index) {
				return current.data;
			}
			current = current.next;
		}

		throw new IndexOutOfBoundsException("ERROR: Unable to get element at index " + index);
	}

	/**
	 * Add an element to the list at the specified index
	 * @param index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) {
		LLNode<E> current = this.head; // Object used to iterate over the list

		boolean added = false; // Keep track of whether we've added the node in order to increment all other indices

		// If the list of the size is 0 or if the index is specified at the end
		// then pass it off to the simple append method
		if ((current.next == tail && this.size == 0) || index == size) {
			add(element);
			return;
		}

		while (current.next != null) {
			LLNode<E> next = current.next;
			if (next.index == index) {
				LLNode<E> nodeToAdd = new LLNode<>(index, element); // Create empty object representing the node to add

				nodeToAdd.next 	= next;
				current.next 	= nodeToAdd;

				nodeToAdd.prev	= current;
				next.prev 		= nodeToAdd;

				added = true;

				this.size++;
			}

			// Loop through the rest and increment their indices
			if (added) {
				next.index++;
			}

			current = current.next;
		}

	}


	/** Return the size of the list */
	public int size() {
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("ERROR: Unable to access element from index " + index);

		LLNode<E> current = this.head;

		E data = null;

		boolean removed = false;

		while (current.next != null) {
			LLNode<E> next = current.next;
			if (next.index == index) {
				LLNode<E> nextToNext = next.next;

				nextToNext.prev	= current; // WARNING: THIS MAY BE CAUSING NULL POINTER
				current.next 	= nextToNext;

				next.next = null;
				next.prev = null;

				data = next.data;

				removed = true;

				this.size--;
			}

			current = current.next;

			if (removed) {
				next.index--;
			}
		}

		return data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		if (index < 0 || index > this.size)
			throw new IndexOutOfBoundsException("Error: Index was either too low or greater than size of list.");

		// TODO: Implement this method
		LLNode<E> newElement = new LLNode<>(element);

		LLNode<E> current = this.head; // Object used to iterate threw

		while (current.next != this.tail) {
			LLNode<E> next = current.next;

			if (next.index == index) {
				// Replace element
				current.next = newElement;
				next.next.prev = newElement;

				newElement.next = next.next;
				newElement.prev = next.prev;
				newElement.index = next.index;

				return newElement.data;
			}

			current = next;
		}

		throw new IndexOutOfBoundsException("Error: Unable to replace element at index " + index);
	}   
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;
	int index;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
		this.index = 0;
	}

	public LLNode(int index, E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
		this.index = index;
	}

	// Get index of element added
	public int getIndex(LLNode<E> e) {
		return e.index;
	}
}
