/* AVL Binary trees and search trees  - Algebraic style, needs algetree.java*/
import java.awt.*;

interface AVLNode<Ty extends Comparable<Ty>> extends Node<Ty>
{
    public int height(); // returns static value -- for avl trees
    public int calcheight(); // sets height value and return relative balance
    public void balance();  // balance node - also sets height
    public AVLNode<Ty> insert(Ty x); // insert, returns pointer to root
    public AVLNode<Ty> delete(Ty x); // delete, returns pointer to root
}


// class for empty tree - not using "null"
class avlnil<Ty extends Comparable<Ty>> extends nil<Ty> implements AVLNode<Ty>
{
    public int height() { return 0; }
    public int calcheight() { return 0; }
    public void balance() {}
    public AVLNode<Ty> insert(Ty x) { return new AVLvert<Ty>(x); }
    public AVLNode<Ty> delete(Ty x) { return this; } // still nil!
}

// class for a non-nil vertex or node, with a head, left and right:
class AVLvert<Ty extends Comparable<Ty>> extends vertex<Ty> 
                                         implements AVLNode<Ty>
{
    //    Ty head;               // inherited
    //    Node<Ty> left;
    //    Node<Ty> right;
    int height; // for avl trees

    // constructors
    public AVLvert(Ty h, AVLNode<Ty> l, AVLNode<Ty> r) 
	{super(h,l,r); calcheight(); }
    public AVLvert(Ty h) 
	{ super(h); 
	  left = new avlnil<Ty>(); right = new avlnil<Ty>(); 
	  height=1;
        }

    // accessors  -- includes type casting (important!)
    //    public Ty head() {return head;}  // inherited
    public AVLNode<Ty> leftnode() { return (AVLNode<Ty>)left; }
    public AVLNode<Ty> rightnode() { return (AVLNode<Ty>)right; }
    public AVLvert<Ty> leftvert() { return (AVLvert<Ty>)left; }
    public AVLvert<Ty> rightvert() { return (AVLvert<Ty>)right; }

    public int height()     { return height; }
    public int calcheight() 
    {
	int lh = leftnode().height();
	int rh = rightnode().height();
	if (lh>rh) height = lh+1; else height=rh+1;
	return rh-lh;  // return negative left-heavy, positive if right-heavy
    }

    // Basic rotations:  double rotations will be composed of single rotations
    protected void LL()
    {
	if (leftnode().empty()) return; // nothing to rotate
	AVLvert<Ty> lvert = leftvert();  // left vertex
	right = new AVLvert<Ty>(head,lvert.rightnode(),rightnode());
	rightnode().calcheight(); // missing from first implementation!
	head = lvert.head;  // copy left.head to this.head
	left = lvert.leftnode();  // type doesn't matter, just set pointer.
	calcheight(); // reset height values
    }
    
    protected void RR()
    {
	if (rightnode().empty()) return; // nothing to do
	AVLvert<Ty> rvert = rightvert();
	left = new AVLvert<Ty>(head,leftnode(),rvert.leftnode());
	leftnode().calcheight();
	head = rvert.head;
	right = rvert.rightnode();
	calcheight();
    }
    
    public void balance() 
    {
	int bal = calcheight();  // also sets height
	if (bal<-1) // left side too heavy
	    {
		// determine if single or double rotation needed
		int lbal = leftnode().calcheight();
		if (lbal<=0) // left heavier, single rotation needed
		    LL();
		else { leftvert().RR(); LL(); } // LR double rotation
	    }
	else if (bal>1) // right side too heavy
	    {
		int rbal = rightnode().calcheight();
		if (rbal>=0) // single rotation
		    RR();
		else { rightvert().LL(); RR(); } // RL double rotation
	    }
    }//balance


    // function to insert a new element into the tree:
    public AVLNode<Ty> insert(Ty x)
    {
	if (x.compareTo(head)<0) // go left
	    left = left.insert(x);
	else if (x.compareTo(head)>0) right = right.insert(x);
	balance();  // only thing different from regular insert
	return this; // root vertex has not changed.
    }//insert
    // Sample usage: AVLNode<Integer> T = new avlnil<Integer>();
    //               T = T.insert(5);


    ////////////  functions for delete   //////////////

    // remove and return the largest element, from parent node
    protected AVLvert<Ty> largest()
    {
	if (rightnode().empty()) return this;
	else if (rightvert().rightnode().empty())
	    {
		AVLvert<Ty> answer = rightvert();
		right = rightvert().leftnode();
		balance();
		return answer;
	    }
	else
	    {
		AVLvert<Ty> answer = rightvert().largest();
		balance();
		return answer;
	    }
    }// largest

    protected AVLvert<Ty> smallest() // find and delete smallest
    {
	if (leftnode().empty()) return this;
	else if (leftvert().leftnode().empty())
	    {
		AVLvert<Ty> answer = leftvert();
		left = leftvert().rightnode();
		balance();
		return answer;
	    }
	else
	    {
		AVLvert<Ty> answer = leftvert().smallest();
		balance();
		return answer;
	    }
    }// smallest
    
    // AVL delete operation
    public AVLNode<Ty> delete(Ty x)
    {
	// first, find node to delete:
	if (x.compareTo(head)<0) // go left
	    { left = leftnode().delete(x); balance(); return this; }
	else if (x.compareTo(head)>0) // go right
	    { right = rightnode().delete(x); balance(); return this; }
	else // found x at head
	    {
		// determine what to substitute for deleted element
		if (leftnode().empty() && rightnode().empty()) // leaf node
		    return new avlnil<Ty>();
		if (!leftnode().empty()) // use largest element on left subtree
		    {
			AVLvert<Ty> sub = leftvert().largest();
			head = sub.head; // replace
			// special case where left is largest
			if (sub==leftvert())
			    {
				left = leftvert().leftnode();
				leftnode().balance();
			    }
		    }
		else if (!rightnode().empty()) // use smallest on right subtree
		    {
			AVLvert<Ty> subr = rightvert().smallest();
			head = subr.head;
			if (subr==rightvert())
			    {
				right = rightvert().rightnode();
				rightnode().balance();
			    }
		    }
		balance();
		return this;
	    }// found x at head
	
    }//delete
    
}// AVLvertex
