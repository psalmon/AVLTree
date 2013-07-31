/* gui to insert/delete from avl tree of integers  - algebraic version*/

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class avlgui extends JFrame implements ActionListener
{
    public JTextField ifd, dfd;    // single line editible area
    public JLabel il, dl;
    public int width = 1000;       // window width 
    public int height = 700;       // and height
    AVLNode<Integer> Tree; 
    treegraph TG;

    public avlgui(AVLNode<Integer> tr)
    {
	Tree = tr;
        ifd = new JTextField(4);
        dfd = new JTextField(4);
	il = new JLabel("Insert:");
	dl = new JLabel("Delete:");
	Container pane = this.getContentPane();
	pane.setLayout(new FlowLayout());
	pane.add(il);  pane.add(ifd);
	pane.add(dl);  pane.add(dfd);
	ifd.addActionListener(this);
	dfd.addActionListener(this);
	this.setBounds(width,height,120,100);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
      	TG = new treegraph(width,height);
	TG.drawtree(Tree);
	this.ifd.requestFocus();
    } // constructor

    public void actionPerformed(ActionEvent e) 
    { 
	try {
	    if (e.getSource() == ifd)  // insert
	    {
		int x = Integer.parseInt(ifd.getText());
		Tree = Tree.insert(x);
		TG.drawtree(Tree);
		ifd.setText("");

	    }
	else if (e.getSource() == dfd)  // delete
	    {  
		int x = Integer.parseInt(dfd.getText());
		Tree = Tree.delete(x);
		TG.drawtree(Tree);
		dfd.setText("");
	    }
	} catch (Exception ee) {}
    } // actionPerformed.


    public static void main(String[] args)
    {
	AVLNode<Integer> T = new avlnil<Integer>();
	for(int i = 0; i<args.length;i++)
	    T = T.insert(Integer.parseInt(args[i]));
	avlgui W = new avlgui(T);
    }

} // avlgui
