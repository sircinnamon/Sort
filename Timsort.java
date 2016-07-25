import java.util.LinkedList;
import java.util.Arrays;
public class Timsort
{
	//Timsort is very complex and this only represents my own understanding of it
	//probably no galloping mode

	//This is an abysmal, oversimplified implementation which scales terribly
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;
	private int minrun = 64;

	public Timsort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Timsort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = timsort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] timsort(int[] arr)
	{
		if(arr.length<64){return insertionsort(arr,0,arr.length,true);}
		int minrun = chooseMinrun(arr.length);
		//System.out.println(minrun);
		LinkedList<int[]> runs = findRuns(arr, minrun);
		//System.out.println("Found " + runs.size() + " runs.");
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		//arr = mergeruns(runs, arr);
		//System.out.println("Merged runs.");
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		return arr;
	}
	
	private int[] insertionsort(int[] arr, int start, int end, boolean ascending)
	{
		int insert;
		if(end>arr.length){end=arr.length;}
		if(ascending)
		{
			for(int i = start+1; i<end;i++)
			{
				insert = arr[i];
				for(int j = i-1;j>=start;j--)
				{
					if(arr[j]<insert){break;}
					else{arr[j+1]=arr[j];arr[j]=insert;}
				}
			}
		}
		else
		{
			for(int i = start+1; i<end;i++)
			{
				insert = arr[i];
				for(int j = i-1;j>=start;j--)
				{
					if(arr[j]>insert){break;}
					else{arr[j+1]=arr[j];arr[j]=insert;}
				}
			}
		}
		return arr;
	}

	private LinkedList<int[]> findRuns(int[] arr, int minrun)
	{
		//a run is an int[] = {start, length, ascending}
		int[] currentRun = new int[]{0,1,1};
		LinkedList<int[]> runs = new LinkedList<int[]>();
		for(int i = 1; i<arr.length; i++)
		{
			//Determine new run direction
			if(currentRun[1] == 1 && arr[i]>arr[i-1]){currentRun[2]=1;}
			else if(currentRun[1] == 1 && arr[i]<arr[i-1]){currentRun[2]=0;}

			//Run continues on
			if(currentRun[2]==1 && arr[i]>=arr[i-1]){currentRun[1]++;}
			else if(currentRun[2]==0 && arr[i]<=arr[i-1]){currentRun[1]++;}
			//run breaks. If longer than minrun, leave it. if not, insertionsort
			else if(i-currentRun[0]>=minrun)
			{
				currentRun[1] = i-currentRun[0];
				//System.out.println(((currentRun[2]==1)?"+":"-")+" Run from " + currentRun[0] + " to " + (i-1));
				runs.push(Arrays.copyOf(currentRun,currentRun.length));
				currentRun = new int[]{i,1,1};
			}
			else
			{
				int runlength = minrun;
				if(currentRun[0]+runlength >= arr.length){runlength = arr.length-currentRun[0];}
				arr=insertionsort(arr,currentRun[0],currentRun[0]+runlength,(currentRun[2]==1));
				currentRun[1] = runlength;
				i = currentRun[0]+runlength;
				//System.out.println(((currentRun[2]==1)?"+":"-")+" Run from " + currentRun[0] + " to " + (i-1));
				//System.out.println("RUN FROM "+currentRun[0]);
				//System.out.println(Arrays.toString(Arrays.copyOfRange(arr,currentRun[0],currentRun[0]+currentRun[1])));
				runs.push(Arrays.copyOf(currentRun,currentRun.length));
				currentRun = new int[]{i,1,1};
			}
			if(runs.size()>=3){mergeruns(runs, arr);}
		}
		if(currentRun[0]<arr.length)
		{
			currentRun[1] = arr.length-currentRun[0];
			//System.out.println(((currentRun[2]==1)?"+":"-")+" Run from " + currentRun[0] + " to " + (currentRun[0]+currentRun[1]));
			runs.push(Arrays.copyOf(currentRun,currentRun.length));
		}
		mergeruns(runs, arr);
		return runs;
	}

	private int[] mergeruns(LinkedList<int[]> runs, int[] arr)
	{
		//using extra memory here
		int[] runx, runy, runz;
		while(runs.size() >=3)
		{
			//System.out.println("Remaining runs: "+runs.size());
			runx = runs.get(0);
			runy = runs.get(1);
			runz = runs.get(2);
			//System.out.println("X: "+runx[1]);
			//System.out.println("Y: "+runy[1]);
			//System.out.println("Z: "+runz[1] + "\n");
			if(runx[1] <= runy[1]+runz[1])
			{
				if(runx[1]<runz[1])
				{
					//merge Y and Z
					runx = runs.pop();
					runy = runs.pop();
					runz = runs.pop();
					runs.push(merge(runy,runz,arr));
					runs.push(runx);
				}
				else
				{
					runx = runs.pop();
					runy = runs.pop();
					runs.push(merge(runx,runy,arr));
					//merge X and Y
				}
			}
			else
			{
				//merge Y and Z
				runx = runs.pop();
				runy = runs.pop();
				runz = runs.pop();
				runs.push(merge(runy,runz,arr));
				runs.push(runx);
			}
		}
		if(runs.size()==2)
		{
			runx = runs.pop();
			runy = runs.pop();
			runs.push(merge(runx,runy,arr));
		}
		if(runs.size()==1 && runs.get(0)[2]==0)
		{
			runx = runs.pop();
			runx[2] = 1;
			runs.push(runx);
			arr = reverse(arr);
		}
		return arr;
	}

	private int[] merge(int[] a, int[] b, int[] arr)
	{
		//returns the run created
		int[] newrun = new int[]{Math.min(a[0],b[0]),a[1]+b[1],1};
		int[] adata = Arrays.copyOfRange(arr,a[0],a[0]+a[1]);
		int[] bdata = Arrays.copyOfRange(arr,b[0],b[0]+b[1]);
		//System.out.println(Arrays.toString(adata));
		//System.out.println("MERGE");
		//System.out.println(Arrays.toString(bdata));

		int ia = a[0];
		int ib = b[0];
		int adir = (-1)+(a[2]*2); //if a[2]==1, adir=1, if a[2]==0, adir = -1;
		int bdir = (-1)+(b[2]*2);
		if(a[2]==0){ia=a[0]+a[1]-1;}
		if(b[2]==0){ib=b[0]+b[1]-1;}
		for(int i = newrun[0]; i<newrun[0]+newrun[1];i++)
		{
			//System.out.println("a :" + ia + " " + adir + "("+a[0]+","+(a[0]+a[1])+")");
			//System.out.println("b :" + ib + " " + bdir + "("+b[0]+","+(b[0]+b[1])+")");
			//System.out.println(":: "+adata[ia-a[0]] + " vs "+bdata[ib-b[0]]);
			if(ia>=a[0]+a[1]||ia<a[0]){arr[i]=bdata[ib-b[0]];ib+=bdir;}
			else if(ib>=b[0]+b[1]||ib<b[0]){arr[i]=adata[ia-a[0]];ia+=adir;}
			else if(adata[ia-a[0]]<=bdata[ib-b[0]]){arr[i]=adata[ia-a[0]];ia+=adir;}
			else{arr[i]=bdata[ib-b[0]];ib+=bdir;}
			//System.out.println("CHOSE " + arr[i]);
		}
		//System.out.println(Arrays.toString(Arrays.copyOfRange(arr,newrun[0],newrun[0]+newrun[1])));
		return newrun;
	}

	private int[] reverse(int[] arr)
	{
		int[] rev = new int[arr.length];
		for(int i = 0; i<arr.length;i++)
		{
			rev[i]=arr[(arr.length-1)-i];
		}
		return rev;
	}

	private int chooseMinrun(int length)
	{
		String bin = Integer.toBinaryString(length);
		int addVal = 0;
		int minrun = 0;
		if(bin.substring(6).contains("1")){addVal=1;}
		//System.out.println(bin);
		for(int i = 0; i<6;i++)
		{
			if(bin.charAt(i)=='1')
			{
				minrun = minrun + (int)Math.pow(2,(5-i));
			}
		}
		minrun = minrun + addVal;
		return minrun;
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