import java.util.LinkedList;
import java.util.Arrays;
public class Heapsort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	//Following spec on wikipedia
	//Array simulates a heap:
	//iParent = floor((i-1)/2)
	//iLeftChild = 2*i + 1
	//iRightChild = 2*i + 2
	public Heapsort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Heapsort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = heapsort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] heapsort(int[] arr)
	{
		heapify(arr);
		int end = arr.length-1;
		int swap;
		while(end>0)
		{
			swap = arr[end];
			arr[end] = arr[0];
			arr[0] = swap;
			end--;
			arr = sift(arr,0,end);
			if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
		}
		//System.out.println(Arrays.toString(arr));
		return arr;
	}

	private int[] heapify(int[] arr)
	{
		int parent = getParent(arr.length-1); //Get final parent
		while(parent>=0)
		{
			arr = sift(arr,parent,arr.length-1);
			if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
			parent--;
		}
		return arr;
	}

	private int[] sift(int[] heap, int start, int end)
	{
		int root = start;
		int swap;
		int swapVal;
		int child;
		while (getLeft(root) <= end)
		{
			swap = root;
			child = getLeft(root);
			//System.out.println("root: " + root + "-"+heap[root]);
			//System.out.println("lchild: " + child + "-"+heap[child]);
			//if(child+1<=end){System.out.println("rchild: " + (child+1) + "-"+heap[child+1]);}
			if(heap[swap]<heap[child]){swap = child;}
			if(child+1<=end && heap[swap]<heap[child+1]){swap=child+1;}
			if(swap == root){return heap;} //no sifting needed
			else
			{
				//System.out.println("swapping " + heap[swap] + " with " + heap[root]);
				swapVal=heap[swap];
				heap[swap]=heap[root];
				heap[root]=swapVal;
				root = swap;
			}
		}
		return heap;
	}

	private int getParent(int i)
	{
		return (int)Math.floor((i-1)/2);
	}
	private int getLeft(int i)
	{
		return (2*i) + 1;
	}
	private int getRight(int i)
	{
		return (2*i) + 2;
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