import java.util.LinkedList;
import java.util.Arrays;
public class BinTreesort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public BinTreesort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public BinTreesort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = binTreesort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] binTreesort(int[] arr)
	{
		BinTree root = new BinTree(arr[0]);
		BinTree leaf;
		for(int i = 1; i<arr.length;i++)
		{
			//System.out.println(Arrays.toString(root.inOrder()));
			if(verbose){sequence.add(Arrays.copyOf(root.inOrder(),root.inOrder().length));}
			leaf = root.insert(arr[i]);
			while(!leaf.isRoot())
			{
				leaf = leaf.parent;
				if(leaf.balance() == 2)
				{
					//Right-heavy. If rchild is left-heavy (-1), r-rotate it. Then l-rotate current
					if(leaf.rchild.balance() == -1){leaf.rchild.rightRotate();}
					leaf.leftRotate();

				}
				else if(leaf.balance() == -2)
				{
					//Left-heavy. If lchild is right-heavy (1), l-rotate it. Then r-rotate current
					if(leaf.lchild.balance() == 1){leaf.lchild.leftRotate();}
					leaf.rightRotate();
				}
				while(!root.isRoot()){root=root.parent;}
			}
		}
		//System.out.println(Arrays.toString(root.inOrder()));
		if(verbose){sequence.add(Arrays.copyOf(root.inOrder(),root.inOrder().length));}
		return root.inOrder();
	}
	

	public int[][] getSequence()
	{
		return sequence.toArray(new int[sequence.size()][data.length]);
	}

	public long getTime()
	{
		return time;
	}
}