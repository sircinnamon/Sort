import java.util.LinkedList;
import java.util.Arrays;
public class Mergesort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Mergesort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Mergesort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = mergesort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] mergesort(int[] arr)
	{
		if(arr.length>1)
		{
			if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
			int mid = (int)Math.floor(arr.length/2);
			int[] left = new int[mid];
			left  = mergesort(arrCopy(arr, 0,mid));
			int[] right = new int[arr.length-mid];
			right = mergesort(arrCopy(arr,mid,arr.length));
			arr = merge(left, right);
		}
		//System.out.println(Arrays.toString(arr));
		if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
		return arr;
	}

	private int[] merge(int[] l, int[] r)
	{
		int[] m = new int[l.length+r.length];
		int lindex = 0;
		int rindex = 0;
		for(int i = 0; i<m.length;i++)
		{
			if(lindex==l.length){m[i]=r[rindex];rindex++;}
			else if(rindex==r.length){m[i]=l[lindex];lindex++;}
			else if(l[lindex]<r[rindex]){m[i]=l[lindex];lindex++;}
			else{m[i]=r[rindex];rindex++;}
		}
		return m;
	}
	private int[] arrCopy(int[] source, int start, int end)
	{
		int[] dest = new int[end-start];
		for(int i = start; i<end;i++){dest[i-start]=source[i];}
		return dest;
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