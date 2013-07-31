public class starter <Ty extends Comparable<Ty>> extends AVlvert<Ty> implements AVLNode<Ty>>{

public starter


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

}
