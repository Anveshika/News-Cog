import java.io.*;
import java.net.*;
import java.util.*;

class ntype {
	String category="";
	String title="";
	String url="";
	String description="";
	String openion=" ";
}

public class feed {
	public static void main(String[] args) throws Exception {
		PrintWriter out1 = new PrintWriter(new FileWriter("file.txt", true));
		ArrayList<ntype> a=new ArrayList<ntype>();
		a=readRSS("http://www.thehindu.com/news/?service=rss",a);
		Hashtable<String,ArrayList<ntype>> h=new Hashtable<String,ArrayList<ntype>>();
		for(int i=0;i<a.size();i++) {
			ntype t=a.get(i);
			ArrayList<ntype> t1=new ArrayList<ntype>();
			if(h.containsKey(t.category))  {
				t1=h.get(t.category);
				h.remove(t.category);
			}
			t1.add(t);
			h.put(t.category,t1);
		}
		int j=1;
		String[] s1=new String[h.size()+1];
		String s5="";
		for(String key: h.keySet()) {
			s1[j]=key;
			j++;
		}
		Scanner in1=new Scanner(System.in);
		while(true) {
			System.out.println("Choose the category from list below:");
			for(int i=0;i<s1.length;i++) {
				System.out.println(" "+i+" : "+s1[i]);
			}
			System.out.println("Give the category number");
			boolean v=true;
			while(true)
			String aa=in1.nextLine();
			String cat=s1[Integer.parseInt(aa)];
			s5=cat+"//"+s5;
			ArrayList<ntype> s2=h.get(cat);
			for(int i=0;i<s2.size();i++) {
				ntype s3=s2.get(i);
				System.out.println("Title:");
				System.out.println(s3.title+"\n");
				System.out.println("Description:");
				System.out.println(s3.description+"\n");			
				System.out.println("URL:");
				System.out.println(s3.url+"\n");
				System.out.print("Do you like this news item? y or n ");
				String ss1=in1.nextLine();
				//System.out.println(ss1);
					s3.openion=ss1;
				System.out.println("Type 'e' to exit from this category else press Enter to continue");
				String s4=in1.nextLine();
				if(s4.equals("e"))
					break;
			}
			System.out.println("Type 'e' to exit from  news feed else press Enter to show category");
			String s4=in1.nextLine();
			if(s4.equals("e"))
				break;
		}
		in1.close();
		Calendar cal = Calendar.getInstance();
		out1.println("\n"+cal.getTime()+"\n");
		String[] s6=s5.split("//");
		//System.out.println("s6==="+s5);
		for(int i=0;i<s6.length;i++) {
			ArrayList<ntype> s7=h.get(s6[i]);
			System.out.println(s6[i]+"size="+s6.length);
			out1.println(s6[i]+"\n");
			for(int k=0;k<s7.size();k++) {
			
				if(!(s7.get(k).openion.equals(" "))) {
					out1.println(s7.get(k).url);
					out1.println(s7.get(k).openion);
				}
			}
			
		}
		out1.close();
	}
	
	public static ntype getdata(String line) throws Exception {
		ntype a=new ntype();
		int fstp=line.indexOf("<title><![CDATA[");
		String temp=line.substring(fstp);
		temp=temp.replace("<title><![CDATA["," ");
		int lstp=temp.indexOf("]]></title>");
		a.title=temp.substring(0,lstp);
		
		fstp=line.indexOf("<category><![CDATA[");
		temp=line.substring(fstp);
		temp=temp.replace("<category><![CDATA["," ");
		lstp=temp.indexOf("]]></category>");
		a.category=temp.substring(0,lstp);
		
		fstp=line.indexOf("<link>");
		temp=line.substring(fstp);
		temp=temp.replace("<link>"," ");
		lstp=temp.indexOf("</link>");
		a.url=temp.substring(0,lstp);

		fstp=line.indexOf("<description><![CDATA[");
		temp=line.substring(fstp);
		temp=temp.replace("<description><![CDATA["," ");
		lstp=temp.indexOf("</description>");
		a.description=temp.substring(0,lstp-5);
		return a;
	}
	
	public static ArrayList<ntype>  readRSS(String urls,ArrayList<ntype> c) throws Exception {
		int p=0;
		URL rssurl= new URL(urls);
		BufferedReader in=new BufferedReader(new InputStreamReader(rssurl.openStream()));
		String sourcecode="";
		String line;
		while((line=in.readLine())!=null) {
			if(line.contains("<item>")) {
				//System.out.println(line);
				String  s="";
				line=in.readLine();
				while((line!=null)&&!(line.contains("</item>"))) {
					s+=line+"  ";
					line=in.readLine();
				}
				c.add(getdata(s));				
			}	
		}
		in.close();
		return c;
	}
}