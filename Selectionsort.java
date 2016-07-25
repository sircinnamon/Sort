import java.util.LinkedList;
import java.util.Arrays;
public class Selectionsort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Selectionsort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Selectionsort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = selectionsort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] selectionsort(int[] arr)
	{
		//Unstable selection sort with swaps
		int swap;
		int min;
		for(int i = 0; i<arr.length;i++)
		{
			min = i;
			for(int j = i+1; j<arr.length;j++)
			{
				if(arr[min]>arr[j]){min=j;}
			}	
			if(min!=i)
			{
				swap = arr[i];
				arr[i]=arr[min];
				arr[min]=swap;
			}
			if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		}
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