import java.util.Scanner;

/**
 * This is a compression/decompression program for project 1
 * @author Joey Schauer
 */
public class proj1 {
	/** Initial state constant of the process FSM */
	private final static int stateInitial = 0;
	/** Letter state constant of the process FSM */
	private final static int stateIsLetter = 1;
	/** Digit state constant of the process FSM */
	private final static int stateIsDigit = 2;
	/** Special state constant of the process FSM */
	private final static int stateIsSpecial = 3;
	/** Gets the state of the process FSM */
	private static int state;
	
    /**
     * main method of the program that accepts user input
     * @param args is an array of user input strings
     */
	public static void main(String[] args) {
		/** creates a scanner for system input */
		Scanner input = new Scanner(System.in);
		process(input);
	}
	
	/**
	 * This method takes input from the system and processes it line by line
	 * outputting a compressed version of the input using a move to front heuristic
	 * and linked list algorithm
	 * @param scanner is the system input scanner
	 */
	public static void process(Scanner scanner) {
		LinkedList list = new LinkedList();		
		int compressed = 0;
		int uncompressed = 0;
		boolean compress = false;
		boolean processing = false;
		if(scanner.hasNextInt()) {
			compress = false;
		} else {
			System.out.print("0 ");
			compress = true;
		}
        /** uses the scanner to process the file line by line */
		while(scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			String word = "";
			
			uncompressed += nextLine.length();
			
			state = stateInitial;
			
			int charIndex = 0;
			
			char c;

            if (processing && nextLine.length() != 0 && Character.getNumericValue(nextLine.charAt(0)) == 0) {
                break;
            } else if (processing) {
                System.out.print("\n"); 
            }
			
            /** this is a finite state machine to process each line character by character */
			while(charIndex < nextLine.length()) {
				c = nextLine.charAt(charIndex);
				
				switch(state) {
					case stateInitial:
						if(Character.isLetter(c)) {
							word += c;
							state = stateIsLetter;
						} else if(Character.isDigit(c)) {
							if (Character.getNumericValue(c) != 0) {
								word += c;
								state = stateIsDigit;
							}
						} else {
							if(compress) {
								System.out.print(c);
								compressed++;
							}
							state = stateIsSpecial;
						}
						break;
						
					case stateIsLetter:
						if(Character.isLetter(c)) {
							word += c;
							if(charIndex == nextLine.length() - 1) {
								if(list.contains(word)) {
									int index = list.indexOf(word);
									if(compress) {
										System.out.print(index + 1);
										String moveToFront = (String) list.remove(index);
										list.add(moveToFront);
									}
									compressed += String.valueOf(index).length();
									word = "";
								} else {
									System.out.print(word);
									list.add(word);
									compressed += word.length();
									word = "";
								}
							}
							state = stateIsLetter;
						} else if(Character.isDigit(c)) {
							state = stateIsDigit;
						} else {
							if(list.contains(word)) {
								int index = list.indexOf(word);
								if(compress) {
									System.out.print(index + 1);
									String moveToFront = (String) list.remove(index);
									list.add(moveToFront);
								} else {
									System.out.print(word);
									String moveToFront = (String) list.remove(index);
									list.add(moveToFront);
								}
								compressed += String.valueOf(index).length();
								word = "";
							} else {
								System.out.print(word);
								list.add(word);
								compressed += word.length();
								word = "";
							}
							System.out.print(c);
							compressed++;
							state = stateIsSpecial;
						}
						break;
						
					case stateIsDigit:
						if(Character.isLetter(c)) {
							state = stateIsLetter;
						} else if(Character.isDigit(c)) {
							word += c;
							if(charIndex == nextLine.length() - 1) {
								int index = Integer.parseInt(word);
								word = (String) list.remove(index - 1);
								System.out.print(word);
								list.add(word);
								word = "";
							}
							state = stateIsDigit;
						} else {
							if(!compress) {
								int index = Integer.parseInt(word);
								word = (String) list.remove(index - 1);
								System.out.print(word);
								list.add(word);
								word = "";
							}
							System.out.print(c);
							state = stateIsSpecial;
						}
						break;
						
					case stateIsSpecial:
						if(Character.isLetter(c)) {
							word += c;
							state = stateIsLetter;
						} else if(Character.isDigit(c)) {
							word += c;
							if(charIndex == nextLine.length() - 1) {
								int index = Integer.parseInt(word);
								word = (String) list.remove(index - 1);
								System.out.print(word);
								list.add(word);
								word = "";
							}
							state = stateIsDigit;
						} else {
							System.out.print(c);
							compressed++;
							state = stateIsSpecial;
						}
						break;
					default:
						break;
				}
				charIndex++;
				processing = true;
			}               		
		}	
		if (compress) {
            System.out.println();
			System.out.print("0 ");
			System.out.print("Uncompressed: " + uncompressed + " bytes;  ");
			System.out.print("Compressed: " + compressed + "bytes");
		}
	}
	
	/**
	 * An implementation of the List interface with a data structure of linked Nodes
	 * @author Joey Schauer
	 */
	private static class LinkedList {
		/** the head node for the linked list */
		private Node head;

		/**
		 * The null constructor for LinkedList
		 */
		public LinkedList() {
			head = null;
		}
		
		/**
		 * Returns the size of the linked list
		 * @return the size of the linked list
		 */
		public int size() {
			int size = 0;
			for (Node p = head; p != null; p = p.next) {
				size++;
			}
			return size;
		}

		/**
		 * Returns true if the linked list contains the object
		 * @param o is the object to check for in the linked list
		 * @return true if the linked list contains the object
		 */
		public boolean contains(Object o) {
			if (o == null) {
				return false;
			}
			for (Node p = head; p != null; p = p.next) {
				if (p.value.equals(o)) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Adds the given object to the fron of the linked list
		 * @param o is the object to add to the linked list
		 * @return true if the object is successfully added to the linked list
		 */
		public boolean add(Object o) {
			if (o == null) {
				throw new NullPointerException();
			}
			if (contains(o)) {
				throw new IllegalArgumentException();
			}
			head = new Node(o, head);
			return true;
		}

		/**
		 * Returns the object at the given index in the linked list
		 * @param index is the index to get the object from
		 * @return the object at the given index in the linked list
		 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to size
		 */
		public Object get(int index) {
			if (index < 0 || index >= size()) {
				throw new IndexOutOfBoundsException();
			}
			
			Node current = head;
			
			while (current != null && index > 0) {
				current = current.next;
				index--;
			}
			if (current == null) {
				return null;
			}
			return current.value;
		}

		/**
		 * Removes the element at the given index from the linked list
		 * @param index is the index to remove the element from
		 * @return the object that was removed
		 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than or equal to size
		 */
		public Object remove(int index) {
			if (index < 0 || index >= size()) {
				throw new IndexOutOfBoundsException();
			}		
			Node current = head;
			Node previous = null;		
			while (current != null && index > 0) {
				previous = current;
				current = current.next;
				index--;
			}		
			if (current != null) {
				if (current == head) {
					head = head.next;
				} else {
					previous.next = current.next;
				}
				return current.value;
			}		
			return null;
		}

		/**
		 * Returns the index of the given object
		 * @return the index of the given object or -1 if it isn't in the list
		 */
		public int indexOf(Object o) {
			if (o == null) {
				return -1;
			}
			int index = 0;
			for (Node p = head; p != null; p = p.next) {
				if (p.value.equals(o)) {
					return index;
				}
				index++;
			}
			return -1;
		}

		/**
		 * Contains an Object and a reference to the next Node in the List
		 * @author Joey Schauer
		 */
		private class Node {
			/** the object value for the Node */
			Object value;
			/** the next Node in the link */
			Node next;
			
			/**
			 * The null constructor for Node
			 * @param value the object in the node
			 * @param next the next Node in the link
			 */
			public Node(Object value, Node next) {
				this.value = value;
				this.next = next;
			}
		}
	}
}