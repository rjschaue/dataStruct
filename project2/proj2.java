import java.io.File;
import java.io.FileNotFoundException;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class proj2 {
        /** global array for the preorder traversal characters and it's size */
        private static char[] preTrav = new char[256];
        private static int preTravSize = 0;
        /** global array for the postorder traversal characters and it's size */
        private static char[] postTrav = new char[256];
        private static int postTravSize = 0;
    
	/**
     * The main method to run the program
     * @param args command line arguments
     */
	public static void main(String[] args) {
        /** creates a scanner for system input */
		Scanner input = new Scanner(System.in);
        /** prompts for input file name and stores it's value */
		System.out.print("Enter input file name: ");
		String inputFileName = input.nextLine();
        /** prompts for output file name and stores it's value */
		System.out.print("Enter output file name: ");
		String outputFileName = input.nextLine();    
        
        
        try {
            /** scanner for input file */
			Scanner fileReader = new Scanner(new File(inputFileName));
            /** creates output file to send values to */
            PrintStream outputFile = new PrintStream(new File(outputFileName));            
		
            /** gets the preorder and postorder strings from the input file */
			String preorder = fileReader.nextLine();						
			String postorder = fileReader.nextLine();			
			
            /** traverses the preorder string and stores the relevant values in preTrav */
			for(int i = 0; i < preorder.length(); i++) {
				char character = preorder.charAt(i);
				if (character != ' ' && character != '<' && character != '>' && character != ',' && character != '.' && character != '?') {
					preTrav[preTravSize] = character;
					preTravSize++;
				}
			}
            
            /** traverses the postoreder array and stores the relevant values in preTrav */
            for(int i = 0; i < postorder.length(); i++) {
                char character = postorder.charAt(i);
                if (character != ' ' && character != '<' && character != '>' && character != ',' && character != '.' && character != '?') {
					postTrav[postTravSize] = character;
					postTravSize++;
				}
            }
            
            /** calls buildTree method and stores given node */
            TreeNode<String> root = buildTree(preTravSize, 0, 0);
            
            /** takes the above node and creates a tree out of it */
            Tree<String> tree = new proj2().new Tree<String>(root);
            
            /** calls levelOrder to get a string of the tree's level order traversal */
            String levelOrder = tree.levelOrder();
            
            /** traverses the rest of the file and makes calls to relationship, storing the given answers in the output file */
            while(fileReader.hasNextLine()) {
                String query = fileReader.nextLine();
                String a = "";
                String b = "";
                for(int i = 0; i < query.length(); i++) {
                    char character = query.charAt(i);
                    if (character != ' ' && character != '<' && character != '>' && character != ',' && character != '.' && character != '?') {
                        if (a.isEmpty()) {
                            a = "" + character;
                        } else {
                            b = "" + character;
                        }
                        
                    }
                }
                outputFile.println(relationship(tree, a, b));
            }
            /** stores the levelOrder string in the output file */
            outputFile.print(levelOrder);
   
			fileReader.close();                       
		} catch (FileNotFoundException e) {
			System.out.println("File does not exist.");
		}                  
		input.close();
	}	
	
    /**
     * The recursive algorithm takes a given size, prestart and poststart position and builds a tree from
     * the global arrays using these values
     * @param size is the size of the array to be processed
     * @param prestart is the starting position in the preorder array
     * @param poststart is the starting positon in the postorder array
     * @return the root node of the tree
     */
	public static TreeNode<String> buildTree(int size, int prestart, int poststart) {
		if(size == 1) {
            TreeNode<String> leaf = new proj2().new TreeNode<String>("" + preTrav[prestart]);
            return leaf;
        } else {
            int newSize;
            int totalSize = 0;
            int newPoststart = poststart;
            TreeNode<String> root = new proj2().new TreeNode<String>("" + preTrav[prestart]);
            for(int i = prestart + 1; i < (size + prestart); i += newSize) {
                newSize = 0;
                
                while(i < preTravSize && totalSize < size && preTrav[i] != postTrav[poststart + totalSize]) {
                    newSize++;
                    totalSize++;
                }
                totalSize++;
                newSize++;
                
                TreeNode<String> child = buildTree(newSize, i, newPoststart);
                child.setParent(root);
                root.addChild(child);
                newPoststart += newSize;
            }
            return root;
        }
	}
    
    /**
     * Takes the given tree and determines the familial relationship between strings a and back
     * @param tree is the tree to check for relationships
     * @param a is the first family member check
     * @param b is the second family member to check
     * @return a string stating the relationship between a and b
     */
    public static String relationship(Tree<String> tree, String a, String b) {
        TreeNode<String> nodeA = tree.lookup(a);
        LinkedList<TreeNode> listA = new LinkedList<TreeNode>();
        TreeNode<String> newNodeA = nodeA;
        while(newNodeA != null && newNodeA.getParent() != null) {            
            newNodeA.markNode();
            listA.add(newNodeA);
            newNodeA = newNodeA.getParent();
            
        }
        if (newNodeA != null) {
            newNodeA.markNode();
            listA.add(newNodeA);
        }        
        TreeNode<String> nodeB = tree.lookup(b);
        int countB = 0;
        TreeNode<String> newNodeB = nodeB;
        while(newNodeB != null && newNodeB.getMark() != 1) {   
            countB++;
            newNodeB = newNodeB.getParent();
        }
        int countA = listA.indexOf(newNodeB);      
        for(int i = 0; i < listA.size(); i++) {
            listA.get(i).clearMark();
        }
        if(countA == 0) {
            if(countB == 0) {
                return a + " is " + b;
            } else if(countB == 1) {
                return a + " is " + b + "'s parent.";
            } else if(countB == 2) {
                return a + " is " + b + "'s grandparent.";
            } else if(countB >= 3) {
                String great = "";
                for(int i = 3; i <= countB; i++) {
                    great += "great-";
                }
                return a + " is " + b + "'s " + great + "grandparent.";
            }
        } else if(countA == 1) {
            if(countB == 0) {
                return a + " is " + b + "'s child.";
            } else if(countB == 1) {
                return a + " is " + b + "'s sibling.";
            } else if(countB == 2) {
                return a + " is " + b + "'s aunt/uncle.";
            } else if(countB >= 3) {
                String great = "";
                for(int i = 3; i <= countB; i++) {
                    great += "great-";
                }
                return a + " is " + b + "'s " + great + "aunt/uncle.";
            }
        } else if(countA >= 2) {
            if(countB == 0) {
                String great = "";
                for(int i = 3; i <= countB; i++) {
                    great += "great-";
                }
                return a + " is " + b + "'s " + great + "grandchild.";
            } else if(countB == 1) {
                String great = "";
                for(int i = 3; i <= countB; i++) {
                    great += "great-";
                }
                return a + " is " + b + "'s " + great + "niece/nephew.";
            } else if(countB >= 2) {
                int th = Math.min(countB, countA) - 1;
                return a + " is " + b + "'s " + th + "th cousin " + Math.abs((countA - countB)) + " times removed.";
            }
        } 
        return "";
    }

    /**
     * A generic tree node class
     * @author Joey Schauer
     */
    private class TreeNode<E> {
        /** the value of the tree node */
        private E value;
        /** used to mark the node for relationship comparison */
        private int mark;
        /** the parent of the node */
        private TreeNode<E> parent;
        /** a list of children for the node */
        private List<TreeNode<E>> children;
        
        /**
         * Node constructor with 3 parameters
         * @param value is the value of the node
         * @param parent is the parent of the node
         * @param children is the array of children for the node
         */
        public TreeNode(E value, TreeNode<E> parent, List<TreeNode<E>> children) {
            setValue(value);
            clearMark();
            setParent(parent);
            setChildren(children);
        }
        
        /**
         * The null constructor for tree node
         */
        public TreeNode() {
            this(null, null, new ArrayList<TreeNode<E>>());
        }
        
        /**
         * Constructor with just a value parameter
         * @param value is the value of the node
         */
        public TreeNode(E value) {
            this(value, null, new ArrayList<TreeNode<E>>());
        }
        
        /**
         * Node constructor with value and parent
         * @param value is the value of the node
         * @param parent is the parent of the node
         */
        public TreeNode(E value, TreeNode<E> parent) {
            this(value,parent, new ArrayList<TreeNode<E>>());
        }
        
        /**
         * @return value of the node
         */
        public Object getValue() {
            return value;
        }
        
        /**
         * Sets the value of the node
         * @param value is the value of the node
         */
        public void setValue(E value) {
            this.value = value;
        }
        
        /**
         * @return the mark integer of the node
         */
        public int getMark() {
            return mark;
        }
        
        /**
         * Marks the node with a 1
         */
        public void markNode() {
            mark = 1;
        }
        
        /**
         * Sets the node mark to 0
         */
        public void clearMark() {
            mark = 0;
        }
        
        /**
         * @return the parent of the node
         */
        public TreeNode<E> getParent() {
            return parent;
        }
        
        /**
         * Sets the parent of the node
         * @param parent is the parent of the node
         */
        public void setParent(TreeNode<E> parent) {
            this.parent = parent;
        }
        
        /**
         * @return the array of children of the node
         */
        public List<TreeNode<E>> getChildren() {
            return children;
        }
        
        /**
         * @return the number of children of the node
         */
        public int numberOfChildren() {
            return children.size();
        }
        
        /**
         * Sets the array of children of the node
         * @param children is an array of children to set to the node
         */
        public void setChildren(List<TreeNode<E>> children) {
            this.children = children;
        }
        
        /**
         * Adds a given child to the node's array of children
         * @param child is the child to add to the node's array of children
         */
        public void addChild(TreeNode<E> child) {
            children.add(child);
        }
    }
    
    /**
     * A generic tree class
     * @author Joey Schauer
     */
    public class Tree<E> {
        /** the root node of the tree */
        private TreeNode<E> root;
        
        /** 
         * constructor with root node
         * @param root is the root of the tree 
         */
        public Tree(TreeNode<E> root) {
            setRoot(root);
        }
        
        /**
         * null constructor for tree
         */
        public Tree() {
            this(null);
        }
        
        /**
         * Sets the root of the tree
         * @param root is the root to set the tree to
         */
        public void setRoot(TreeNode<E> root) {
            this.root = root;
        }
        
        /**
         * Gives the level order traversal of the tree
         * @return a string of the level order of the tree
         */
        public String levelOrder() {
            LinkedQueue<TreeNode<E>> queue = new LinkedQueue<TreeNode<E>>();
            String levelOrder = "";
            if(root == null) {
                return levelOrder;
            }
            queue.enqueue(root);
            while(!queue.isEmpty()) {
                TreeNode<E> node = queue.dequeue();
                levelOrder += "" + node.getValue();
                List<TreeNode<E>> children = node.getChildren();
                for (int i = 0; i < children.size(); i++) {
                    TreeNode<E> child = children.get(i);
                    queue.enqueue(child);
                }
                if(!queue.isEmpty()) {
                    levelOrder += ", ";
                }
            }
            levelOrder += ".";
            return levelOrder;
        }
        
        /**
         * Looks up a given node in the tree
         * @param value is the value to search for
         * @return the node with the given value or null if not found
         */
        public TreeNode<E> lookup(E value) {
            LinkedQueue<TreeNode<E>> queue = new LinkedQueue<TreeNode<E>>();
            if(root.getValue().equals(value)) {
                return root;
            } else {
                queue.enqueue(root);
                while(!queue.isEmpty()) {
                    TreeNode<E> node = queue.dequeue();
                    if (node.getValue().equals(value)) {
                        return node;
                    }
                    List<TreeNode<E>> children = node.getChildren();
                    for (int i = 0; i < children.size(); i++) {
                        TreeNode<E> child = children.get(i);
                        queue.enqueue(child);
                    }
                }
            }
            return null;
        }
    }
    
    /**
     * Stores values as a linked queue
     * @param <E> is the element type for the queue
     * @author Joey Schauer
     */
    public class LinkedQueue<E> {
        LinkedList<E> linkedList;

        /**
         * Constructor for LinkedQueue
         * @param capacity the capacity for the queue
         */
        public LinkedQueue() {
            linkedList = new LinkedList<E>();
        }
        
        /**
         * Adds the element to the back of the queue
         */
        public void enqueue(E element) {
            linkedList.add(element);		
        }

        /**
         * Removes and returns the element at the front of the queue
         * @return the element at the front of the queue
         * @throws NoSuchElementException if the queue is empty
         */
        public E dequeue() {
            if (isEmpty()) {
                return null;
            }
            return linkedList.removeFirst();
        }

        /**
         * Returns true if the queue is empty
         * @return true if the queue is empty
         */
        public boolean isEmpty() {
            return linkedList.size() == 0;
        }

        /**
         * Returns the number of elements in the queue
         * @return the number of elements in the queue
         */
        public int size() {
            return linkedList.size();
        }
    }
}




