import java.util.LinkedList;
import java.util.Arrays;
public class Introsort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Introsort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Introsort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = introsort(data, (int)(Math.floor(Math.log(data.length))*2));
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] introsort(int[] arr, int depth)
	{
		if(arr.length == 1){}//do nothing
		else if(depth == 0)
		{
			heapsort(arr);
		}
		else
		{
			//select a pivot index
			//arr = pivotMOT(arr);
			//int pivot = arr[(int)Math.floor(arr.length/2)];
			//arr = partition(arr);
			if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
			//pivot = firstIndex(arr,pivot);
			int pivot = partition(arr);
			int[] sub;
			if(pivot > 0)
			{	
				sub = introsort(Arrays.copyOfRange(arr,0,pivot),depth-1);
				for(int i = 0; i < sub.length;i++){arr[i]=sub[i];}
			}
			if(pivot<arr.length-1)
			{
				sub = introsort(Arrays.copyOfRange(arr,pivot+1,arr.length),depth-1);
				for(int i = 0; i < sub.length;i++){arr[i+pivot+1]=sub[i];}
			}
		}
		if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
		return arr;
	}

	private int partition(int[] arr)
	{
		//Median-of-3 partitioning
		//Input SHOULD be sanitized with pivotMOT but if not it just un-optimizes pivot selection
		//arr = pivotMOT(arr);
		int pivot =  pivotMOT(arr);
		int swap;
		for(int i = 0; i<pivot; i++)
		{
			if(i<pivot && arr[i] >= arr[pivot])
			{
				//swap into index pivot-1 then swap with pivot
				swap = arr[i];
				arr[i] = arr[pivot-1];
				arr[pivot-1] = arr[pivot];
				arr[pivot] = swap;
				pivot--;
				i--;
			}
			else if(i>pivot && arr[i] <= arr[pivot])
			{
				//swap into index pivot-1 then swap with pivot
				swap = arr[i];
				arr[i] = arr[pivot+1];
				arr[pivot+1] = arr[pivot];
				arr[pivot] = swap;
				pivot++;
				i--;
			}
		}
		return pivot;
	}

	private int pivotMOT(int[] arr)
	{
		//median-of-3 pivot selection
		if(arr.length<3){return 0;}
		int hi = arr.length-1;
		int lo = 0;
		int mid = (int)Math.floor(arr.length/2);
		int swap;
		if(arr[lo]>arr[hi]){swap = arr[lo];arr[lo]=arr[hi];arr[hi]=swap;}
		if(arr[mid] > arr[hi]){swap = arr[hi];arr[hi]=arr[mid];arr[mid] = swap;}
		if(arr[mid] < arr[lo]){swap = arr[lo];arr[lo] = arr[mid];arr[mid] = swap;}
		return mid;
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
			arr = heapsift(arr,0,end);
		}
		//System.out.println(Arrays.toString(arr));
		return arr;
	}

	private int[] heapify(int[] arr)
	{
		int parent = getParent(arr.length-1); //Get final parent
		while(parent>=0)
		{
			arr = heapsift(arr,parent,arr.length-1);
			parent--;
		}
		return arr;
	}

	private int[] heapsift(int[] heap, int start, int end)
	{
		int root = start;
		int swap;
		int swapVal;
		int child;
		while (getLeft(root) <= end)
		{
			swap = root;
			child = getLeft(root);
			if(heap[swap]<heap[child]){swap = child;}
			if(child+1<=end && heap[swap]<heap[child+1]){swap=child+1;}
			if(swap == root){return heap;} //no sifting needed
			else
			{
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

	private int firstIndex(int[] arr, int x)
	{
		for(int i = 0; i<arr.length;i++)
		{
			if(arr[i]==x){return i;}
		}
		return -1;
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