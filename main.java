package laba1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public class main {
	
	public static String BRtoStr(BufferedReader buf) {
		 String line = null;
		 String message = new String();
		try {
		FileInputStream fstream = new FileInputStream("D:\\G.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader (fstream));
	   
	    final StringBuffer buffer = new StringBuffer();
	    while ((line = br.readLine()) != null) {
	        // buffer.append(line);
	        message += line;
	    }
	    System.out.println(message);
	}catch(IOException e) {
			System.out.println("Ошибка");
	
		}
	    
	    return message;
	}

	public static void SortStack(Map<Character,Integer>mas, Stack<Character> stack) {
		
		for(int k=0; k<=32; k++){
			 int min = Integer.MAX_VALUE;
			 char save = 0;
			 for(char j='а'; j<='я'; j++){
				 if(mas.get(j)<min) {
					 min=mas.get(j);
					 save=j;
				 } 
			 }
			 stack.push(save);
			 mas.put(save, Integer.MAX_VALUE);
		 }
		 				
	}

	public static void CreateTable (Map<Character,Character> table, Map<Character,Integer> mas) {
		
	
		
		for(char j='а'; j<='я';j++) {
			table.put(j, j);
			   
		   }
		Map<Character,Integer> codedMas = new HashMap<Character,Integer>();
		analyseShifered(codedMas);
		for(char j='а'; j<='я';j++)
		 {
		 int value = mas.get(j);
		 
		 System.out.println(j + ":" + value);
		 }
		Stack<Character> norm = new Stack<Character>();
		Stack<Character> coded = new Stack<Character>();
		SortStack(mas, norm);
		SortStack(codedMas, coded);
		while(!coded.isEmpty()){
			table.put(coded.pop(),norm.pop() );
		}
	}
	
	
	
	public static void main(String[] args) {
		
		Map<Character, Integer> mas = new HashMap<Character, Integer>();
		Map<Character, Integer> SH = new HashMap<Character, Integer>();
		Map<Character,Character> Table = new HashMap<Character,Character>();
		Map<String, Integer> Bimmas = new HashMap<String,Integer>();
		String Original = "D:\\G.txt";
		String Coded = "D:\\a.txt";
		String New = "D:\\new.txt";
		int step=5;
		System.out.println("Анализ данных... ");
		 analyseOriginal(mas,Original);
		 BIM_analyse_Origin(Bimmas);
			System.out.println("Анализ данных...ОК!!! ");

			System.out.println("Шифрование данных... ");

		 try {
			
			FileInputStream fstream = new FileInputStream("D:\\G.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader (fstream));
		   
			//Запись в Файл зашифрованной главы
			String str = encode(br,5);
            File newTextFile = new File("D:\\a.txt");
            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();
		} catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
        }
		
	//	analyseShifered(mas);	
		System.out.println("Шифрование данных...ОК!!! ");
		System.out.println("Дэшифрация данных... ");

		CreateTable(Table,mas);
		//System.out.println(Table);
		modifyTable(Table,Original,Coded);
		Decoding(Table,Original,New);
		
		System.out.println("Анализ данных...ОК!!! ");

		int counter=0;
		for(Entry<Character, Character> i : Table.entrySet()){
			System.out.println(i.getKey()+" "+i.getValue());
			if(i.getKey()==(char)(i.getValue()+step)) counter++ ;
		 }
		System.out.println(((double)counter/33.0)*100.0+"% correct");
		
	
		
		
		//for (Map.Entry<Character, Integer> pair : SH.entrySet()) {
			//char Key = pair.getKey();
			//int Value = pair.getValue();
			//double Freq = (double)Value/i*100;
			//System.out.println(Key + ":" + Value + "  Частота :  " +  Freq + "%");
			
			
	}
		
	static void decodeBin(Map<String, Integer> mas, String codedWay, String newWay){
		Map<String,String> charmap = new HashMap<String,String>();
		for(char j='а'; j<='я';j++) {
			for(char m='а'; m<='я';m++){
				charmap.put(Character.toString(j)+(Character.toString(m)), Character.toString(j)+(Character.toString(m)));
			}   
		   }
		Map<String,Integer> codedMas = new HashMap<String,Integer>();
		BIM_analyse_Coded(codedMas);

		Stack<String> norm = new Stack<String>();
		Stack<String> coded = new Stack<String>();
		BIM_Stack(mas, norm);
		BIM_Stack(codedMas, coded);
		for(int i=0; i<5; i++){
			System.out.println(coded.pop()+" "+norm.pop());
		}
		while(!coded.isEmpty()){
			charmap.put(coded.pop(),norm.pop() );
		}
	}
	static void Decoding(Map<Character,Character> charmap, String codedWay, String newWay){
		try(FileWriter writer = new FileWriter(newWay, false))
        {	
		   FileInputStream fstream = new FileInputStream(codedWay);
		   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		   String strLine;
		   writer.write("");
		   while ((strLine = br.readLine()) != null){
				 for(int i=0;i<strLine.length();i++) {
					   char letter = strLine.charAt(i);
					   if(letter>='А'&&letter<='Я'){
						   for(char j='А'; j<='Я'; j++) {
							   if (letter==j) {
								   writer.append((char) (charmap.get((char)(letter+32))-32));
							   }
						   }
					   } else if(letter>='а'&&letter<='я'){
						   writer.append((char) (charmap.get(letter)));
					   } else writer.append(letter);
					   
				 }
		   }
            
             
            writer.flush();
            br.close();
        }
        catch(IOException e){
             
            System.out.println("Error");
        }
	}
		/*public static void Replace() {
		try {
			FileInputStream fstream = new FileInputStream("D:/thetextfile.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader (fstream));
			String strLine;
			char letter; 
			String s;
			while((strLine = br.readLine())!= null) {
				for(int i=0; i<strLine.length();i++) {
					letter = strLine.charAt(i);
					if (letter=="a") {
						
					}
				}
			}
		}
			
		catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();}
		
	}*/
		
	public static String MapToString(Map<Character, Integer> mas) {
		String string = mas.toString();
		return string;
		//int value = mas.
		
	}
	
	public static String decode(String enc, int offset) {
	        return decode(enc, 33-offset);
	    }
	 	
	public static String encode( BufferedReader mas , int offset) {
	       
		offset = offset % 32 + 32;
	        String str = BRtoStr(mas);
	        StringBuilder encoded = new StringBuilder();
	        for (char i : str.toCharArray()) {
	            if (Character.isLetter(i)) {
	                if (Character.isUpperCase(i)) {
	                    encoded.append((char) ('А' + (i - 'А' + offset) % 32 ));
	                } else {
	                    encoded.append((char) ('а' + (i - 'а' + offset) % 32 ));
	                }
	            } else {
	                encoded.append(i);
	            }
	        }
	        return encoded.toString();
	    }
	
	public static void analyseOriginal(Map<Character, Integer> mas,String Origin) {

		try {
			  FileInputStream fstream = new FileInputStream(Origin);
			   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			   String strLine;
			   char letter;
			   String s;
			   for(char j='а'; j<='я';j++) {
					  mas.put(j, 0);
					   
				   }
			   while ((strLine = br.readLine()) != null){
				   for(int i=0;i<strLine.length();i++) {
					   letter = strLine.charAt(i);
					   boolean ll = false;
					   for(char j='А'; j<='Я'; j++) {
						   if (letter==j) {
							   letter=(char)((int)j+32);
							   ll=true;
						   }
					   }
					  for(char j='а'; j<='я';j++) {
						  if (letter==j) ll=true;
						   
					   }
					  if (ll==false) continue;
						   mas.put(letter, mas.get(letter)+1);
				   	}
				   
			   }
			   br.close();
			}catch (IOException e){
			   System.out.println("Error");
			}
		
	}
	
	public static void analyseShifered(Map<Character, Integer> SH) {

		try {
			  FileInputStream fstream = new FileInputStream("D:\\a.txt");
			   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			   String strLine;
			   char letter;
			   String s;
			   for(char j='а'; j<='я';j++) {
					  SH.put(j, 0);
					   
				   }
			   while ((strLine = br.readLine()) != null){
				   for(int i=0;i<strLine.length();i++) {
					   letter = strLine.charAt(i);
					   boolean ll = false;
					   for(char j='А'; j<='Я'; j++) {
						   if (letter==j) {
							   letter=(char)((int)j+32);
							   ll=true;
						   }
					   }
					  for(char j='а'; j<='я';j++) {
						  if (letter==j) ll=true;
						   
					   }
					  if (ll==false) continue;
						   SH.put(letter, SH.get(letter)+1);
				   	}
				   
			   }
			   br.close();
			}catch (IOException e){
			   System.out.println("Error");
			}
	
		
		
		/*for (Map.Entry<Character, Integer> pair : mas.entrySet()) {
			char key = pair.getKey();
			int value = pair.getValue();
			double freq = (double)value/kol*100;
				Key = pair.getKey();
				Value = pair.getValue();
			//System.out.println(key + ":" + value + "  Частота :  " +  Freq + "%");
		}
		
		//System.out.println("Всего букв :" + kol);
	//	Map<Character, Integer> Mas = new HashMap<Character, Integer>();
		//Mas.put(Value,Key);
		 * 
		 */
		//return kol ;	
	}
	
	static void BIM_Stack(Map<String, Integer> mas, Stack<String> s){
		 for(Map.Entry<String, Integer> k : mas.entrySet()){
			 int min = Integer.MAX_VALUE;
			 String save = "";
			 for(Map.Entry<String, Integer> j : mas.entrySet()){
				 if(j.getValue()<min) {
					 min=j.getValue();
					 save=j.getKey();
				 } 
			 }
			 s.push(save);
			 mas.put(save, Integer.MAX_VALUE);
		 }
	 }
	
	static void BIM_analyse_Origin(Map<String,Integer> mas) {
			try{
				   FileInputStream fstream = new FileInputStream("D:\\G.txt");
				   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				   String strLine;
				   String s;
				   for(char j='а'; j<='я';j++) {
						for(char m='а'; m<='я';m++){
							mas.put(Character.toString(j)+(Character.toString(m)), 0);
						}
						  
						   
					   }
				   while ((strLine = br.readLine()) != null){
					   for(int i=0;i<strLine.length()-1;i++) {
						   char A=strLine.charAt(i);
						   char B=strLine.charAt(i+1);
						   for(char j='А'; j<='Я'; j++) {
							   if (A==j) {
								   B=(char)((int)j+32);
							   }
							   if (B==j) {
								   B=(char)((int)j+32);
							   }
						   }
						   s = Character.toString(A).concat(Character.toString(B));
						   for(char j='а'; j<='я';j++) {
								for(char k='a'; k<='я';k++){
									String key = Character.toString(j).concat(Character.toString(k));
									if(s.equals(key)){
										int t=0;
										try{
											t = mas.get(key);
										}
										catch(NullPointerException e){
											continue;
										}
										mas.put(key, t+1);
									}
									
								}
								  
								   
							   }
					   }
				   }
				   br.close();

				}catch (IOException e){
				   System.out.println("Error");
				}
			
		}

	static void BIM_analyse_Coded(Map<String,Integer> mas) {

			try{
				   FileInputStream fstream = new FileInputStream("D:\\a.txt");
				   BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
				   String strLine;
				   String s;
				   for(char j='а'; j<='я';j++) {
						for(char m='а'; m<='я';m++){
							mas.put(Character.toString(j)+(Character.toString(m)), 0);
						}
						  
						   
					   }
				   while ((strLine = br.readLine()) != null){
					   for(int i=0;i<strLine.length()-1;i++) {
						   char A=strLine.charAt(i);
						   char B=strLine.charAt(i+1);
						   for(char j='А'; j<='Я'; j++) {
							   if (A==j) {
								   B=(char)((int)j+32);
							   }
							   if (B==j) {
								   B=(char)((int)j+32);
							   }
						   }
						   s = Character.toString(A).concat(Character.toString(B));
						   for(char j='а'; j<='я';j++) {
								for(char k='a'; k<='я';k++){
									String key = Character.toString(j).concat(Character.toString(k));
									if(s.equals(key)){
										int t=0;
										try{
											t = mas.get(key);
										}
										catch(NullPointerException e){
											continue;
										}
										mas.put(key, t+1);
									}
									
								}
								  
								   
							   }
					   }
				   }
				   br.close();

				}catch (IOException e){
				   System.out.println("Error");
				}
			
		}
	 	
	static void modifyTable(Map<Character,Character> charmap, String decodedWay, String codedWay){
		 	Map<String, Integer> mas = new HashMap<String,Integer>();
		 	BIM_analyse_Origin(mas);
		 	Map<String,String> bincharmap = new HashMap<String,String>();
			Map<String,Integer> codedMas = new HashMap<String,Integer>();
			BIM_analyse_Coded(codedMas);
			//////////////
			Stack<String> norm = new Stack<String>();
			Stack<String> coded = new Stack<String>();
			BIM_Stack(mas, norm);
			BIM_Stack(codedMas, coded);
			for(int i=0; i<5; i++){
				bincharmap.put(coded.pop(),norm.pop());
			}
			 for(Entry<String, String> i : bincharmap.entrySet()){
				 charmap.put((i.getValue().charAt(0)),i.getKey().charAt(0));
				 charmap.put((i.getValue().charAt(1)),i.getKey().charAt(1));
			 } 
	 }
}

