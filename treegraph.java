/* graphical representation of binary trees

   To use, this file and "algetree.java" must be in same directory:
  
   Everytime drawtree is called, the background is cleared, so you
   don't have to create a new treegraph object to draw a new tree.

   vertex<T extends Comparable<T>> class expected to include
   method names head(), left(), and right().  
   head is of type T and left, right are of type node<T>.

   See "main" at end of file for sample usage
*/


import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;

public class treegraph extends JFrame
{
    public int XDIM, YDIM;
    public Graphics display;
    public Node<?> currenttree;

    public void paint(Graphics g) {drawtree(currenttree);} // override method

    // constructor sets window dimensions
    public treegraph(int x, int y)
    {
	XDIM = x;  YDIM = y;
	this.setBounds(0,0,XDIM,YDIM);
	this.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	display = this.getGraphics();
	// draw static background as a black rectangle
	display.setColor(Color.black);
	display.fillRect(0,0,x,y);
        display.setColor(Color.red);
	try{Thread.sleep(700);} catch(Exception e) {} // Synch with system
    }  // drawingwindow


    // internal vars used by drawtree routines:
    public int bheight = 50; // default branch height
    public int yoff = 30;  // static y-offset

    // l is level, lb,rb are the bounds (position of left and right child)

    public void drawtree(Node<?> T)
    {
        if (T==null) return;
	currenttree = T;
	int d = T.depth();
	display.setColor(Color.white);
	display.fillRect(0,0,XDIM,YDIM);  // clear background
	if (d<1) return;
	bheight = (YDIM/d);
	T.accept(this,0,0,XDIM);
	//        T.draw(this,0,0,XDIM);
    }

    // double-dispatch versions of draw
    public void draw(nil<?> N ,int l, int lb, int rb) {}
    public void draw(vertex<?> N, int l, int lb, int rb)
    {
	try{Thread.sleep(10);} catch(Exception e) {} // slow down
        display.setColor(Color.green);
	display.fillOval(((lb+rb)/2)-10,yoff+(l*bheight),20,20);
	display.setColor(Color.red);
	display.drawString(N.head()+"",((lb+rb)/2)-5,yoff+15+(l*bheight));
	display.setColor(Color.blue); // draw branches
        if (!N.left().empty())
	    {
   	       display.drawLine((lb+rb)/2,yoff+10+(l*bheight),
			((3*lb+rb)/4),yoff+(l*bheight+bheight));
               N.left().accept(this,l+1,lb,(lb+rb)/2);
		   //draw(N.left,l+1,lb,(lb+rb)/2);
	    }
        if (!N.right().empty())
	    {
               display.drawLine((lb+rb)/2,yoff+10+(l*bheight),
			((3*rb+lb)/4),yoff+(l*bheight+bheight));
               N.right().accept(this,l+1,(lb+rb)/2,rb);
	       //               draw(N.right,l+1,(lb+rb)/2,rb);
	    }
    } // draw


    /* sample use:  (put this in another file) ************** 
    public static void main(String[] args)
    {
      treegraph W = new treegraph(1024,768);
      nil<Integer> NIL = new nil<Integer>(); // shared empty tree instance
      Node<Integer> T = 
       new vertex<Integer>(9,
         new vertex<Integer>(5,NIL,new vertex<Integer>(10)),
         new vertex<Integer>(15,
		  NIL, 
		  new vertex<Integer>(18,new vertex<Integer>(3),NIL)));
      W.drawtree(T);
      W.display.drawString("Do you like my tree?",20,W.YDIM-50);
      try{Thread.sleep(5000);} catch(Exception e) {} // 5 sec delay
    }  // main
    ********************/

} // treegraph

