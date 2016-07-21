import java.util.LinkedList;
import java.util.Arrays;
public class Quicksort
{
	private int[] data;
	private int[] sortedData;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Quicksort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Quicksort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		sortedData = quicksort(data, data.length-1, 0);
		if(timed){time = System.currentTimeMillis()-time;}
		return sortedData;
	}

	public int[] quicksort(int[] arr, int end, int start)
	{
		//Lomuto partition quicksort
		//System.out.println(start + " " + end);
		int pivot = end;
		int swap;
		for(int i = start; i<pivot; i++)
		{
			if(arr[i] >= arr[pivot])
			{
				//swap into index pivot-1 then swap with pivot
				swap = arr[i];
				arr[i] = arr[pivot-1];
				arr[pivot-1] = arr[pivot];
				arr[pivot] = swap;
				pivot--;
				i--;
			}
		}
		//System.out.println(Arrays.toString(arr));
		if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
		if(pivot!=start){arr = quicksort(arr, pivot-1, start);} //Sort before pivot
		if(pivot!=end){arr = quicksort(arr, end, pivot+1);} //sort after pivot
		return arr;
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