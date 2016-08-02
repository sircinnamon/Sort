import java.util.LinkedList;
import java.util.Arrays;
public class Librarysort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Librarysort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Librarysort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = librarysort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] librarysort(int[] arr)
	{
		int length = arr.length;
		int[] sorted = new int[1];
		Arrays.fill(sorted,-1);
		sorted[0]=arr[0];
		int swap;
		for(int i = 0; i<= (int)Math.floor(Math.log(length)/Math.log(2));i++)
		{
			//System.out.println(i+" =i: "+Arrays.toString(sorted).replace("-1","_"));
			sorted = makeGaps(sorted);
			for(int j = (int)Math.pow(2,i);j<Math.pow(2,i+1);j++)
			{
				if(j>=arr.length){break;}
				//System.out.println(j+" : "+Arrays.toString(sorted).replace("-1","_"));
				int index = binSearch(arr[j],sorted,0);
				do
				{
					swap = arr[j];
					if(sorted[index]>arr[j])
					{
						swap = sorted[index];
						sorted[index]=arr[j];
						arr[j]=swap;
					}
					index++;
					if(index == sorted.length && swap!=-1)
					{
						//bring spaces forward
						sorted = shiftBack(sorted);
						sorted[index-1]=swap;
						//System.out.println("SWAP: "+swap);
						swap = -1;
						//System.out.println("AFTERPUSH:"+Arrays.toString(sorted).replace("-1","_"));
					}
					if(verbose){sequence.add(Arrays.copyOf(sorted,sorted.length));}
				}while(swap!=-1);

			}
		}
		return removeSpaces(sorted, length);
	}

	private int binSearch(int x, int[] arr, int skip)
	{
		int index = skip;
		int min = index;
		int max = arr.length-1;
		//while(arr[max]==-1){max--;}
		index = (int)Math.floor((min+max)/2);
		while(true)//(leftVal(arr,min,x)>x || leftVal(arr,max,x)<=x)//(max-min>1)
		{
			index = (int)Math.floor((min+max)/2);
			//System.out.println("MAX: "+max+" MIN:"+min+" INDEX:"+index + " VAL:"+x);
			//System.out.println(Arrays.toString(arr).replace("-1","_"));
			if(arr[index]!=-1)
			{
				if(arr[index]>x){max=index;}
				else if(arr[index]<=x){min=index+1;}
			}
			else
			{
				if(leftVal(arr,index,x)>x){max=index-1;}
				if(leftVal(arr,index,x)<=x){min=index+1;}
			}
			if(max==0){index = 0;break;}
			if(min==arr.length){index=min;break;}
			if(max==min)
			{
				if(arr[min-1]==-1){index=min-1;break;}
				else{index=min;break;}
			}
		}
		//index=min;
		//System.out.println("MAX: "+max+" MIN:"+min+" INDEX:"+index);
		return index;
	}

	private int[] makeGaps(int[] arr)
	{
		int r = arr.length;
		int w = r*2;
		int[] a = new int[w];
		r--;
		w=w-2;
		while(r>=0)
		{
			a[w+1]=-1;
			a[w]=arr[r];
			r--;
			w=w-2;
		}
		return a;
	}	

	private int rightVal(int[] arr, int i, int val)
	{
		while(i<arr.length && arr[i]==-1)
		{
			i++;
		}
		if(i==arr.length){return val+1;}
		else{return arr[i];}
	}
	private int leftVal(int[] arr, int i, int val)
	{
		while(i>=0 && arr[i]==-1)
		{
			i--;
		}
		if(i==-1){return val-1;}
		else{return arr[i];}
	}

	private int[] shiftBack(int[] arr)
	{
		//System.out.println("TRIGGERED PUSHBACK\n\n");
		int swap = arr[arr.length-1];
		int i = arr.length-2;
		while(swap!=-1)
		{
			arr[arr.length-1]=arr[i];
			arr[i]=swap;
			swap = arr[arr.length-1];
			i--;
		}
		arr[arr.length-1]=-1;
		//System.out.println("PUSHED:"+Arrays.toString(arr).replace("-1","_"));
		return arr;
	}
	private int[] removeSpaces(int[] arr, int length)
	{
		int[] a = new int[length];
		int i = 0;
		int j = 0;
		while(i < a.length && j < arr.length)
		{
			if(arr[j]==-1){j++;}
			else
			{
				a[i]=arr[j];
				i++;
				j++;
			}
		}
		return a;
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