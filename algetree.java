/* Binary trees and search trees  - Algebraic style */
import java.awt.*;

interface Node<Ty extends Comparable<Ty>>
{
    public vertex<Ty> find(Ty x);
    public vertex<Ty> bsearch(Ty x);
    public Node<Ty> insert(Ty x);
    public boolean empty();
    public int size();   
    public int depth();  // dynamic computation
    public boolean isbst(Ty min, Ty max); // determine if binary search tree

    public void accept(treegraph TG, int l, int lb, int rb);
}


// class for empty tree - not using "null"
class nil<Ty extends Comparable<Ty>> implements Node<Ty>
{
    public int size() { return 0; }
    public vertex<Ty> find(Ty x) { return null; }
    public vertex<Ty> bsearch(Ty x) {return null;}
    public int depth() {return 0;}
    public Node<Ty> insert(Ty x) { return new vertex<Ty>(x); }
    public boolean empty() { return true; }
    public boolean isbst(Ty min, Ty max) { return true; }
    public void accept(treegraph TG, int l, int lb, int rb)
       {TG.draw(this,l,lb,rb);}
}

// class for a non-nil vertex or node, with a head, left and right:
class vertex<Ty extends Comparable<Ty>> implements Node<Ty>
{
    protected Ty head;
    protected Node<Ty> left;
    protected Node<Ty> right;

    // constructors
    public vertex(Ty h, Node<Ty> l, Node<Ty> r) 
	{head=h; left=l; right=r; }
    public vertex(Ty h) 
	{head=h; left = new nil<Ty>(); right = new nil<Ty>(); }

    // accessors
    public Ty head() {return head;}
    public Node<Ty> left() {return left;}
    public Node<Ty> right() {return right;}

    // function to compute total number of vertexs:
    public int size()
    {
	return left.size() + right.size() + 1;
    }

    public boolean empty() { return false; }

    // function to determine if an element is in the tree, NOT assuming
    // that the tree is a binary-search tree.  It returns a pointer to
    // the vertex containing the element, or null if non exists
    public vertex<Ty> find(Ty x)
    {
	if (head.equals(x)) return this; // this is the vertex with x
	vertex<Ty> l = left.find(x);
	if (l!=null) return l; 
	else return right.find(x);
    }
    

    // function to find an element, assuming a BST structure, where
    // all elements on the left subtree are <= to the root element.

    public vertex<Ty> bsearch(Ty x)
    {
	if (head.equals(x)) return this;
	if (x.compareTo(head)<0) // go left
		 return left.bsearch(x);
	else // go right
	         return right.bsearch(x); 
    }

    // function to insert a new element into the tree
    public Node<Ty> insert(Ty x)
    {
	if (x.compareTo(head)<=0) // go left
	    left = left.insert(x);
	else right = right.insert(x);
	return this; // root vertex has not changed.
    }//insert
 

    // function to find the maximum depth of the tree.  The depth of a 
    // tree is one plus the depth of the deeper subtree.  That's recursion
    // for you!
    public int depth()
    {
	int dl = left.depth();
	int dr = right.depth();
	if (dl > dr) return dl+1;  else return dr+1;
    }

    public boolean isbst(Ty min, Ty max) // test for search tree property
    {
	boolean leftOK = (min==null || min.compareTo(head)<0);
	boolean rightOK = (max==null || max.compareTo(head)>=0);
	return leftOK && rightOK && 
	    left.isbst(min,head) && right.isbst(head,max);
    }

    // for drawing treegraph
    public void accept(treegraph TG, int l, int lb, int rb)
       {TG.draw(this,l,lb,rb);}

}// vertex
