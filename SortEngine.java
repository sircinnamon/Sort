import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Random;
import java.util.LinkedList;
import java.util.Arrays;
public class SortEngine
{
	//engine for comparing sorting algorithms on different datasets
	public static boolean verbose = false;
	public static boolean random = false;
	public static boolean time = false;
	public static boolean getFile = false;
	public static String filename;
	public static boolean getSize = false;
	public static int dataSize = 10;
	public static LinkedList<String> modes = new LinkedList<String>();

	public static void main(String[] args)
	{
		for(String s : args)
		{
			if(getFile){filename = s;getFile = false;}
			else if(getSize){dataSize = Integer.parseInt(s);getSize=false;}
			else if(s.equals("-v")){verbose = true;}
			else if(s.equals("-r")){random = true;}
			else if(s.equals("-t")){time = true;}
			else if(s.equals("-f")){getFile = true;}
			else if(s.equals("-s")){getSize = true;}
			else{modes.add(s.toLowerCase());}
		}
		if(modes.size()==0){modes.add("quicksort");}
		int[] data = new int[dataSize];
		if(filename != null)
		{
			try{data = readData(filename);}catch(IOException e){e.printStackTrace();}
		}
		else if(random)
		{
			//fill data with random ints from 0 to (10xdataSize)
			Random r = new Random();
			for(int i = 0; i<data.length;i++)
			{
				data[i] = r.nextInt(10*dataSize);
			}
		}
		else
		{
			for(int i = 0; i<data.length;i++)
			{
				data[i] = dataSize-i;
			}
		}
		if(verbose){System.out.println("Input = "+Arrays.toString(data));}
		int[][] sequence = new int[0][0];
		int[] sortedData = new int[0];
		long t = 0;
		for(String s : modes)
		{
			if(s.equals("quicksort")||s.equals("q"))
			{
				Quicksort q = new Quicksort(Arrays.copyOf(data,data.length),verbose,time);
				sortedData = q.sort();
				sequence = q.getSequence();
				if(time){t = q.getTime();}
			}
			if(s.equals("mergesort")||s.equals("m"))
			{
				Mergesort m = new Mergesort(Arrays.copyOf(data,data.length),verbose,time);
				sortedData = m.sort();
				sequence = m.getSequence();
				if(time){t = m.getTime();}
			}
			if(verbose){printSeq(sequence);}
			System.out.println((verbose?"Output = ":"")+Arrays.toString(sortedData));
			if(time){System.out.println("Time: "+t+"ms");}

		}

	}

	public static int[] readData(String file) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		LinkedList<String> list = new LinkedList<String>();
		String line = br.readLine();
		while(line != null)
		{
			list.add(line);
			line = br.readLine();
		}
		int[] arr = new int[list.size()];
		for(int i = 0; i<list.size();i++)
		{
			arr[i] = Integer.parseInt(list.get(i));
		}
		return arr;
	}

	public static void printSeq(int[][] seq)
	{
		for(int i = 0; i<seq.length;i++)
		{
			System.out.println(i+". "+Arrays.toString(seq[i]));
		}
	}
}