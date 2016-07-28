import java.util.LinkedList;
import java.util.Arrays;
public class Cyclesort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	//This sort is not intended to ptimize speed but number of writes
	public Cyclesort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Cyclesort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = cyclesort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] cyclesort(int[] arr)
	{
		int curr;
		int pos;
		int swap;
		for(int i = 0; i<arr.length-1;i++)
		{
			curr = arr[i];
			pos = i;
			for(int j = i+1;j<arr.length;j++)
			{
				if(arr[j]<curr){pos++;}
			}
			if(pos!=i)
			{
				while(curr==arr[pos]){pos++;}
				swap = curr;
				curr = arr[pos];
				arr[pos]=swap;

				while(pos!=i)
				{
					if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
					pos=i;
					for(int j = i+1;j<arr.length;j++)
					{
						if(arr[j]<curr){pos++;}
					}
					while(curr==arr[pos]){pos++;}
					swap = curr;
					curr = arr[pos];
					arr[pos]=swap;
				}
			}
			if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
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