import java.util.LinkedList;
import java.util.Arrays;
public class Bubblesort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Bubblesort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Bubblesort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = bubblesort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] bubblesort(int[] arr)
	{
		int swap;
		boolean swapped = false;
		do
		{
			swapped=false;
			for(int i = 1; i<arr.length;i++)
			{
				if(arr[i-1]>arr[i])
				{
					swap=arr[i];
					arr[i]=arr[i-1];
					arr[i-1]=swap;
					swapped=true;
					if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
				}
			}
		}while(swapped);
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