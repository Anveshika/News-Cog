import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

class Preprocessing
{
	public static void main(String[] args)
	{
		Preprocessing obj=new Preprocessing();
		Hashtable<String, String> swords=obj.getStopWords();
		String art1=obj.getFileData("Article 1.txt");
		String art2=obj.getFileData("Article 2.txt");
		art1=obj.doPreprocessing(art1, swords);
		art2=obj.doPreprocessing(art2, swords);
	}
	public String doPreprocessing(String art,Hashtable<String, String> swords)
	{
		String result="";
		String arr[]=art.split(" ");
		for(int i=0;i<arr.length;i++)
		{
			if(arr[i].length()>1)
			{
				int ascii=(int)arr[i].charAt(arr[i].length()-1);
				while(!((ascii>=48 && ascii<=57) || (ascii>=65 && ascii<=90) || (ascii>=97 && ascii<=122)))
				{
					arr[i]=arr[i].substring(0,arr[i].length()-1);
					ascii=(int)arr[i].charAt(arr[i].length()-1);
				}
				String c=arr[i].toLowerCase();
				if((int)arr[i].charAt(0)==8220)
					arr[i]=arr[i].substring(1);
				if(!swords.containsKey(c))
				{
					result=result+" "+arr[i];
				}
			}
		}
		return result;
	}
	public String getFileData(String name)
	{
		String data="";
		try 
		{
			Scanner in=new Scanner(new File(name));
			while(in.hasNext())
			{
				data=data+" "+in.next();
			}
			in.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return data;
	}
	public Hashtable<String, String> getStopWords()
	{
		Hashtable<String, String> swords=new Hashtable<>();
		try 
		{
			Scanner in=new Scanner(new File("stopwords.txt"));
			while(in.hasNextLine())
			{
				swords.put(in.nextLine(), "");
			}
			in.close();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		return swords;
	}
}