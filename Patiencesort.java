import java.util.LinkedList;
import java.util.Arrays;
public class Patiencesort
{
	private int[] data;
	private LinkedList<int[]> sequence = new LinkedList<int[]>();
	private boolean verbose = false;
	private boolean timed = false;
	private long time;

	public Patiencesort(int[] d, boolean v, boolean t)
	{
		data = d;
		verbose = v;
		timed = t;
	}
	public Patiencesort(int[] d)
	{
		data = d;
	}

	public int[] sort()
	{
		if(verbose){sequence.add(Arrays.copyOf(data,data.length));}
		if(timed){time = System.currentTimeMillis();}
		data = patiencesort(data);
		if(timed){time = System.currentTimeMillis()-time;}
		return data;
	}

	private int[] patiencesort(int[] arr)
	{
		int insert;
		LinkedList<LinkedList<Integer>> piles = new LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> pile = new LinkedList<Integer>();
		pile.add(arr[0]);
		piles.add(pile);
		for(int i = 1; i<arr.length;i++)
		{
			for(int j = 0;j<piles.size();j++)
			{
				pile = piles.get(j);
				if(pile.get(pile.size()-1) >= arr[i])
				{
					pile.add(arr[i]);
					//System.out.println("ADDED TO PILE: "+arr[i] + " TO "+j);
					break;
				}
				else if(j==piles.size()-1)
				{
					//reached end of piles, make new pile
					pile = new LinkedList<Integer>();
					pile.add(arr[i]);
					piles.add(pile);
					//System.out.println("NEW PILE: "+arr[i]);
					break;
				}
			}
		}
		//merge the piles
		for(int i = 0; i<arr.length;i++)
		{
			int min = Integer.MAX_VALUE;
			int minPile = -1;
			for(int j = 0;j<piles.size();j++)
			{
				pile = piles.get(j);
				if(pile.size()==0){piles.remove(j);j--;}
				else if(pile.get(pile.size()-1) < min)
				{
					min = pile.get(pile.size()-1);
					minPile = j;
				}
			}
			arr[i]=min;
			piles.get(minPile).removeLast();
		}
		if(verbose){sequence.add(Arrays.copyOf(arr,arr.length));}
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