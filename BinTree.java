import java.util.Arrays;
public class BinTree
{
	public int val;
	public BinTree parent;
	public BinTree lchild;
	public BinTree rchild;
	public int multiplicity = 1;

	public BinTree(int v)
	{
		val = v;
	}
	public BinTree(int v,BinTree p)
	{
		val = v;
		parent = p;
	}

	public boolean isRoot()
	{
		return (parent==null);
	}

	public boolean full()
	{
		return (lchild!=null&&rchild!=null);
	}

	public boolean empty()
	{
		return (lchild==null&&rchild==null);
	}

	public int height()
	{
		if(this.isRoot()){return 0;}
		else{return parent.height()+1;}
	}

	public BinTree insert(int v)
	{
		if(v > val)
		{
			if(rchild==null)
			{
				rchild = new BinTree(v, this);
				//System.out.println("ADDED " + v + " AS RCHILD TO "+this.val);
				return rchild;
			}
			else
			{
				return rchild.insert(v);
			}
		}
		else if(v == val)
		{
			multiplicity++;	
			return this;
		}
		else
		{
			if(lchild==null)
			{
				lchild = new BinTree(v, this);
				//System.out.println("ADDED " + v + " AS LCHILD TO "+this.val);
				return lchild;
			}
			else
			{
				return lchild.insert(v);
			}
		}
	}

	public int balance()
	{
		if(empty()){return 0;}
		if(full()){return (rchild.depth()+1)-(lchild.depth()+1);}
		else if(lchild != null){return 0-lchild.depth();}
		else{return rchild.depth();}
	}

	public int depth()
	{
		if(empty()){return 1;}
		else if(full()){return Math.max(lchild.depth(),rchild.depth())+1;}
		else if(lchild != null){return lchild.depth()+1;}
		else{return rchild.depth()+1;}
	}

	public BinTree rightRotate()
	{
		//System.out.println("R-ROTATE AT " + this.val);
		BinTree pivot = this.lchild;
		this.lchild = null;
		pivot.parent = this.parent;
		if(pivot.parent!=null)
		{
			if(this.val > this.parent.val){this.parent.rchild = pivot;}
			else{this.parent.lchild = pivot;}
		}
		if(pivot.rchild != null)
		{
			this.lchild = pivot.rchild;
			this.lchild.parent = this;
		}
		this.parent = pivot;
		pivot.rchild = this;
		return this;
	}

	public BinTree leftRotate()
	{
		//System.out.println("L-ROTATE AT " + this.val);
		BinTree pivot = this.rchild;
		this.rchild = null;
		pivot.parent = this.parent;
		if(pivot.parent!=null)
		{
			if(this.val > this.parent.val){this.parent.rchild = pivot;}
			else{this.parent.lchild = pivot;}
		}
		if(pivot.lchild!= null)
		{
			this.rchild = pivot.lchild;
			this.rchild.parent = this;
		}
		this.parent = pivot;
		pivot.lchild = this;
		return this;
	}

	public int[] inOrder()
	{
		int[] left = null;
		int[] right = null;
		int[] node = null;
		if(lchild != null && rchild==null)
		{
			left = lchild.inOrder();
			node = new int[left.length+multiplicity];
			for(int i = 0; i<left.length;i++)
			{
				node[i]=left[i];
			}
			for(int i = left.length; i<node.length;i++)
			{
				node[i]=this.val;
			}
			return node;
		}
		else if(rchild!=null && lchild == null)
		{
			right = rchild.inOrder();
			node = new int[right.length+multiplicity];
			for(int i = 0; i<multiplicity;i++)
			{
				node[i]=this.val;
			}
			for(int i = multiplicity; i<node.length;i++)
			{
				node[i]=right[i-multiplicity];
			}
			return node;
		}
		else if(lchild == null && rchild == null)
		{
			node = new int[multiplicity];
			for(int i = 0; i<node.length;i++)
			{
				node[i]=this.val;
			}
			return node;
		}
		else
		{
			left = lchild.inOrder();
			right = rchild.inOrder();
			node = new int[left.length+right.length+multiplicity];

			for(int i = 0; i<left.length;i++)
			{
				node[i]=left[i];
			}
			for(int i = left.length; i<left.length+multiplicity;i++)
			{
				node[i]=this.val;
			}
			for(int i = left.length+multiplicity; i<node.length;i++)
			{
				node[i]=right[(i-left.length)-multiplicity];
			}
			return node;
		}
	}
}