import java.util.LinkedList;
import java.util.Arrays;
public class Insertionsort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Insertionsort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Insertionsort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = insertionsort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] insertionsort(int[] arr)
	{
		int insert;
		for(int i = 1; i<arr.length;i++)
		{
			insert = arr[i];
			for(int j = i-1;j>=0;j--)
			{
				if(arr[j]<insert){break;}
				else{arr[j+1]=arr[j];arr[j]=insert;}
				if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
			}
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