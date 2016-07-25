import java.util.LinkedList;
import java.util.Arrays;
public class Shellsort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Shellsort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Shellsort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = shellsort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] shellsort(int[] arr)
	{
		int[] gaps = chooseGaps(0,arr.length);
		int swap;
		for(int gap : gaps)
		{
			for(int i = gap; i < arr.length; i++)
			{
				swap = arr[i];
				int j = i;
				for(j = i; j>= gap && arr[j-gap]>swap;j=j-gap)
				{
					arr[j]= arr[j-gap];
					if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
				}
				arr[j]=swap;
				if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
			}
		}
		return arr;
	}
	
	private int[] chooseGaps(int choice, int n)
	{
		if(choice == 0){return new int[]{701,301,132,57,23,10,4,1};} //Ciura Gap Sequence
		else if(choice == 1)
		{
			//Shell's Seq
			return genShellSeq(n);
		}
		return new int[]{1};
	}

	private int[] genShellSeq(int n)
	{
		LinkedList<Integer> seq = new LinkedList<Integer>();
		int x = n;
		int k = 1;
		while(x!=1)
		{
			x = (int)Math.floor(n/(int)Math.pow(2,k));
			k++;
			seq.add(x);
		}
		int[] arr = new int[seq.size()];
		for(int i = 0; i < arr.length; i++)
		{
			arr[i]=(int)seq.get(i);
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