//import the necessary resources
import java.util.*;

/**
 * This class demonstrates the heap data structures and it methods 
 * which implement all of its functions. 
 * @author Austin Daigle
 * @since 4/22/2021
 * @version 1.1
 * @param <K> This is a class parameter for the key object parameter
 * @param <V> This is a class parameter for the value object parameter
 * 
 * Version 1.0: full working heap 
 * Version 1.1: heapify function has been implemented to be automatically called 
 * with the insert()/merge() method. 
 */
public class QHeap<K,V> {
	@SuppressWarnings("hiding")
	/*
	 * This is a nested class that serves the purpose of containing 
	 * a constructor for the creation of key-value pair objects. 
	 */
	class Item<K,V> {
		//Create the internal object fields that are required for the Item objects.
		public K key;
		public V value;
		
		/**
		 * This is the default constructor that is responsible for creating 
		 * Item objects with the Key and Value objects as parameters.
		 * @param key
		 * @param value
		 */
		public Item(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * This method returns the key and value fields as a String.
		 * @return returns a string value displaying the value of 
		 * the key and value fields.
		 */
		public String toString() {
			return "key: " + this.key + ", Value: " + this.value + "\n";
		}
	} //End of the nested class
	
	//Create the private attributes used for QHeap object and constructor. 
	private final int DEFAULT_CAPACITY = 15;
	private int count;
	private int capacity; // the maximum heap before resize is needed
	private ArrayList<Item<K,V>> storage; // array used to store the items

	/**
	 * This is the default constructor that creates heap object
	 * at a default capacity, with the count set to zero.
	 */
	public QHeap() {
		//Define count to zero as the heap is no populated with data.
		this.count = 0;
		this.capacity = DEFAULT_CAPACITY;
		//create the storage parameter
		this.storage = new ArrayList<Item<K,V>>(DEFAULT_CAPACITY);
	}

	/**
	 * This constructor creates a heap object at a specific 
	 * size.
	 * @param requestSize This is an integer that parameter that
	 * denotes the custom size of the heap object. 
	 */
	public QHeap(int requestSize) {
		//Define count to zero as the heap is no populated with data.
		this.count = 0;
		this.capacity = requestSize;
		//Create the storage parameter.
		this.storage = new ArrayList<Item<K,V>> (this.capacity);
	}

	/**
	 * This method is a copy constructor for heap objects. 
	 * @param heapToCopy This is the heap object that is copied into 
	 * new heap object.
	 */
	public QHeap(QHeap<K,V> heapToCopy) {
		//Copy all parameters into the new object in the copy constructor.
		this.count = heapToCopy.count;
		this.capacity = heapToCopy.capacity;
		this.storage = heapToCopy.storage;
	}

	/**
	 * This method merges the given heap object into
	 * the current heap .this object.
	 * @param heapToMerge This is the heap that is being added to the current
	 * heap object. 
	 * @return This returns the current count object of the merged heap object.
	 */
	public int merge(QHeap<K,V> heapToMerge) {
		/*
		 * Use the addAll() method to merge the contents from heapToMerge
		 * into the current heap object. 
		 */
		this.storage.addAll(heapToMerge.storage);
		//Update the current count fields to the current count added to heapToMerge's size.
		this.count = this.count + heapToMerge.size();
		//call heapify to reorder the heap
		heapify();
		//Return the updated count variable. 
		return this.count;
	}

	/**
	 * This method inserts a key and value pair into the heap. 
	 * The key value is used to denote the position/priority for the 
	 * key/value pair. 
	 * @param key This is the key entry to denote the position/priority of the
	 * entry into the heap object.
	 * @param value This is the value that is inserted into key/value pair.
	 * @exception Throws exception if the capacity of the heap has been exceeded.
	 */
	public void insert(K key, V value) throws Exception {
		if(this.count == this.capacity) {
			//Throw exception if the capacity has been exceeded.
			throw new Exception("Error: Heap has exceeded storage capacity.");
		}
		else if(this.count < this.capacity) {
			//update count variable to reflect the new entry.
			this.count++;
			//Add the new entry key/value pair into the heap storage field.
			this.storage.add(new Item<K,V> (key, value));
		}
		heapify();
	}

	/**
	 * This method returns the minimum value of the heap.
	 * @return returns the minimum from the heap. If the heap is 
	 * empty then return null. 
	 */
	public Item<K,V> min() {
		//If the count is greater than zero
		if(this.count > 0) {
			//return the minimum value
			return this.storage.get(0);
		}
		else {
			//If the count is zero then return null.
			return null;
		}
	}

	/**
	 * This removes the minimum from the heap.
	 * @return returns the root node. 
	 */
	public Item<K,V> removeMin() {
		//Create an Item object of the minimum root entry value. 
		Item<K,V> minItemToRemove = min();
		//Create an integer variable to store the index value of the minimum entry.
		int indexOfMinEntry = this.storage.indexOf(minItemToRemove);
		//If indexOfMinEntry is greater than or equal to zero then
		if(indexOfMinEntry >= 0) {
			//Remove the entry at the value of indexOfMinEntry in heap's storage object.
			this.storage.remove(indexOfMinEntry);
			/* update the count variable to reflect the
			 * deduction of one to reflect the removed value
			 */
			this.count--;
			//return the value of the root node
			return minItemToRemove;
		}
		//This null is returned if the heap is empty.
		return null;
	}

	/**
	 * This method returns a string value of the value of the 
	 * heap in the in order traversal format. 
	 * @return Returns a string with all of the values in order. 
	 */
	public String inOrderTraversal() {
		//Create a string variable to process and return the final result. 
		String result = "";
		//if storage is null or the count is less than or equal to zero
		if (this.storage == null || this.count <= 0) {
			//return "null"
			return "null";
		}
		else {
			/*
			 * Create an iterator object and point the 
			 * reference to the storage object of the heap.
			 */
			Iterator<Item<K,V>> iterationObject = this.storage.iterator();
			//Iterate through all of the entries in the heap
			while (iterationObject.hasNext()) {
				//update result the current next iteration entry.
				result = result + iterationObject.next();
			}
			//return the result from the iteration for the inOrderTraversal. 
			return result;
		}
	}

	/**
	 * This method returns the backwards order of the heap as 
	 * a string
	 * @return returns the backwards order of the heap as a string value.
	 */
	public String inBackWardsOrder() {
		//Create a string variable to process and return the final result. 
		String result = "";
		//If the count is less than or equal to zero then return "null"
		if(this.count <= 0) {
			return "null";
		}
		else {
			//For every entry in the heap (from the highest value to the lowest value)
			for(int i = this.count; i >= 1; i--) {
				//update the result value with the next lower iteration entry.
				result = result + this.storage.get(i-1);
			}
			//return the result from the iteration for the inBackWardsOrder
			return result;
		}
	}
	
	/**
	 * This method deletes all of the data found in heap object.
	 * This method was included for debug purposes. 
	 */
	public void clear() {
		//set count to zero
		this.count = 0;
		//set the storage object to null
		this.storage = null;
	}
	
	/**
	 * This is an equals method that returns a boolean value 
	 * if the given heap and the current heap are both equal to 
	 * each other.
	 * @param compareTo This is the heap that is compared to the current heap.
	 * @return returns a boolean value indicating the equality status of both heaps.
	 * This method was included for the sake of debugging and formality.
	 */
	public boolean equals(QHeap<K,V> compareTo) {
		//If the count variables are not equal return false
		if(this.count != compareTo.count) {
			return false;
		}
		//if the capacity variables are not equal return false. 
		else if(this.capacity != compareTo.capacity) {
			return false;
		}
		else {
			//create a boolean variable to store the result of the entry by entry analysis
			boolean storageObjectCheck = true;
			//for every element in the heap(s)
			for(int i = 0; i < count; i++) {
				/* 
				 * If the entries from the given and the current heaps are not equal
				 * then set storageObjectCheck to false.
				 */
				if(this.storage.get(i) != (compareTo.storage.get(i))) {
					storageObjectCheck = false;
				}
			}
			//return the final result of the attribute-by-attribute check.
			return storageObjectCheck;
		}
	}

	/**
	 * This method returns the number of element in the
	 * heap.
	 * @return Returns the number of element in the heap.
	 */
	public int size() {
		//return the integer value of the count.
		return this.count;
	}

	/**
	 * This method returns a boolean value if the heap is empty or not. 
	 * @return returns a boolean value indicating the emptiness of the heap.
	 */
	public boolean isEmpty() {
		//returns the boolean value of the empty-status of the heap.
		return this.count <= 0;
	}

	/**
	 * This is a toString() method that returns the contents of the
	 * heap as a string.
	 */
	public String toString() {
		//If the heap is empty then return null
		if (this.storage == null || this.count <= 0) {
			return "null";
		}
		else {
			//Create String object for storing the result
			String output = "";
			//Create iterator object that is referenced to the storage object of the heap.
			Iterator<Item<K,V>> iterationObject = this.storage.iterator();
			//Iterate through all of the entries in the heap
			while (iterationObject.hasNext()) {
				//update result the current next iteration entry.
				output = output + iterationObject.next();
			}
			//return the contents of the heap as a String. 
			return output;
		}
	}

	/**
	 * This method is the heapify function that guarantees the
	 * integrity of the given heap. This method could be 
	 * directly invoked by the user or automatically called
	 * by the insert()/merge() function. 
	 */
	public void heapify() {
		//Create an integer variable to store the size of the heap
		int heapLength = this.size();
		/* 
		 * Create Item object references for storing the Left/Right object references
		 * for the sake of comparing values for the heapify operations.
		 */
		Item<K,V> left;
		Item<K,V> right;
		//For the length of the heap minus one (the offset is for the comparison pairs)
		for(int i = 0; i < heapLength-1; i++) {
			//Update the left/right references to the updated references per each iteration. 
			left = this.storage.get(i);
			right = this.storage.get(i+1);
			//If the left reference is greater than the right reference then perform the swap.
			if(((String) left.value).compareTo((String) right.value) > 0) {
				/* swap the left comparison reference to the value on the
				 * right reference on the heap's storage object.
				 */
				this.storage.set(i, right);
				/* swap the right comparison reference to the value 
				 * on the left reference on the heap's storage object.
				 */
				this.storage.set(i+1, left);
			}
		}
	}
	  

	public static void main(String[] args) throws Exception {
		//create heap1 and populated it with data.
		QHeap<Integer, String> heap1 = new QHeap<Integer,String>(20);
		heap1.insert(1, "data 1");
		heap1.insert(2, "data 2");
		heap1.insert(3, "data 3");
		heap1.insert(7, "data 6");
		heap1.insert(4, "data 4");
		heap1.insert(5, "data 5");

		System.out.println("Heap1 Size: " + heap1.size());
		System.out.println("Printing Heap1: \n" + heap1);
		System.out.println("Printing Heap1 in backwards Order:\n"+heap1.inBackWardsOrder());
		System.out.println("Printing out Minimum Item in heap1:\n" + heap1.min());
		System.out.println("Removing Minimum Item in heap1.");
		heap1.removeMin();
		System.out.println("Printing Heap1: \n" + heap1);
		System.out.println("InOrder Transversal: \n" + heap1.inOrderTraversal());
		
		//create second new heap
		QHeap<Integer, String> heap2 = new QHeap<Integer,String>(10);
		heap2.insert(8, "data 7");
		heap2.insert(9, "data 8");
		heap2.insert(10, "data 9");
		
		System.out.println("heap1 equals heap1: "+heap1.equals(heap1));
		System.out.println("heap1 equals heap2: "+heap1.equals(heap2));
		System.out.println("");
		//Printout heap2
		System.out.println("Printing out heap2: \n"+heap2);
		//merge the heap
		System.out.println("Merging heap2 into heap1.");
		heap1.merge(heap2);
		//print the merged heap
		System.out.println("Printing out heap1:");
		System.out.println(heap1);
		System.out.println("Removing Minimum Item in heap1.\n");
		heap1.removeMin();
		System.out.println("Removing Minimum Item in heap2.\n");
		heap2.removeMin();
		System.out.println("Heap1 Size: " + heap1.size());
		//print the merged heap
		System.out.println("Printing out heap1:");
		System.out.println(heap1); 
		System.out.println("Heap2 Size: " + heap2.size());
		//print the heap
		System.out.println("Printing out heap2:");
		System.out.println(heap2); 

		//create a new third heap by copying an existing one
		QHeap<Integer,String> heap3 = new QHeap <Integer,String>(heap2);
		System.out.println("Printing out heap3:");
		System.out.println(heap3.size());
		System.out.println("Printing out heap3:");
		System.out.println(heap3);
		System.out.println("Printing out Minimum Item in heap3.\n");
		System.out.println(heap3.min());
		System.out.println("Removing Minimum Item in heap1.");
		heap1.removeMin();
		System.out.println("Printing out heap3:");
		System.out.println(heap3); 
		
		System.out.println("Deleting all data in heap1");
		heap1.clear();
		System.out.println("Printing out heap1");
		System.out.println(heap1); 

	} //end of driver method
} // end of the class
