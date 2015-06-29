package org.jobscheduler.dashboard.ocab;

import java.io.*;

public class LineCount
{
	public static void main(String[] args) throws Exception
     {
 
//Change the directory where the count files are existing
File di   = new File("D:/resultat/");
File fl[] = di.listFiles();
int j;
int count=0;
String str="";
String totalinfo="";

for (j=0; j < fl.length; j++)
{
System.out.println(""+fl[j]);
FileInputStream fis = new FileInputStream(fl[j].getCanonicalFile());
LineNumberReader l = new LineNumberReader(new BufferedReader(new InputStreamReader(fis)));
while ((str=l.readLine())!=null)
{
count = l.getLineNumber();
}
System.out.println(count);
 
String filename = fl[j].getName().toString();
totalinfo = filename+"-------------"+count;
 
FileOutputStream file = new FileOutputStream("D:/test.txt",true); //append
PrintStream printtofile = new PrintStream(file);
printtofile.println(totalinfo);
printtofile.close();
file.close();
}
}
}