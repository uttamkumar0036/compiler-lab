package compiler_lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LexicalAnalysis {

	static String keywords[] = {"bool","main", "char", "int", "long", "float", "double", "void", "return", "if", "else", "while", "do", "for", "goto","break", "switch", "case", "cin", "cout"};
	
	static String operators[] = {"+", "-", "*", "/", "%", "++", "--", "=", "+=", "-=", "*=", "/=", "==", "!=", ">", "<", ">=", "<=", "&&", "||","!", "&", "|", "^", "~", "<<", ">>"};
	
	static String single_operators[] = {"+", "-", "*", "/", "%", "=", ">", "<","!", "&", "|", "^", "~", ";"};
	
	static String symbols[] = {",","(", ")", "{", "}", ";"};
	
	 static String lexems[] ;
	
	static String rawCode = "";
	
	
	static ArrayList<String> symTable = new ArrayList<String>();
	static Set<String> kes = new HashSet<String>();
	static Set<String> ops = new HashSet<String>();
	static Set<String> sym = new HashSet<String>();
	static Set<String> num = new HashSet<String>();
	private static Scanner sc;
	public static void main(String[] args) throws FileNotFoundException {
		//file input
		File fl = new File("hello.c");	
		sc = new Scanner(fl);
		
		System.out.println("\nLexems\t       Token Name   Attribute Value\tName");
		System.out.println("__________________________________________________________\n");
		while(sc.hasNextLine())
		{
			rawCode = sc.nextLine();
			
			if(rawCode.indexOf("//") != -1)
			{
				
				rawCode = rawCode.substring(0, rawCode.indexOf("//"));
			}
			lexems = split(rawCode);	
			
			
			for(String st : lexems)
			{
				
				if(isBlank(st))
					continue;
				
				if(checkSymbol(st))
				{
					sym.add(st);
					
				}else if(checkKeyword(st))
				{
					kes.add(st);
				}else if(checkOperator(st))
				{
					ops.add(st);
				}
				else if(isNumeric(st))
				{
					num.add(st);
				}
				else if(isValidJavaIdentifier(st))
				{
					if(!symTable.contains(st))
					{
						symTable.add(st);
					}
					
					System.out.println(st+ "\t\tid\t\t"+ pointer_table_entry( st));
				}
				
			}
			
		}
		
		 System.out.println("\n\n          Symbol Table");
		 System.out.println("____________________________________");
		for(int i = 0 ; i < symTable.size() ; i++)
		{
			System.out.format("%s		ID		%d		\n",symTable.get(i),  i);
		}
	
	}
	
	public static int pointer_table_entry( String str)
	{
		
		int j = symTable.size();
		for(int i = 0; i < j ; i++)
		{
			if(symTable.get(i).equals(str) )
				return i;
		}
		
		return -1;
		
	}
	
	public static boolean isNumeric(String str)  
	{  
		  try  
		  {  
		    double d = Double.parseDouble(str);
		    System.out.println(str+ "\t\tnumber\t\t\tconstant");
		    
		    	
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;		  
		 
	}
	
	public static boolean checkKeyword(String str)
	{
		for(String s : keywords)
		{
			if(s.equals(str))
			{
				System.out.println(str+"\t\tkeyword");
				return true;
			}
			
				
		}
		return false;
	}
	
	public static boolean checkOperator(String str)
	{
		for(String s : operators)
		{
			if(s.equals(str))
			{
				if(s.equals("="))
					System.out.println(str+ "\t\toperator\t\tassignment");
				else if(s.equals("+"))
					System.out.println(str+ "\t\toperator\t\taddition");
				else if(s.equals("-"))
					System.out.println(str+ "\t\toperator\t\tsubtraction");
				else if(s.equals("*"))
					System.out.println(str+ "\t\toperator\t\tmultiplication");
				else if(s.equals("/"))
					System.out.println(str+ "\t\toperator\t\tdivision");
				else if(s.equals("=="))
					System.out.println(str+ "\t\toperator\t\tequal checking");
				else if(s.equals("++"))
					System.out.println(str+ "\t\toperator\t\tincrement");
				else if(s.equals("--"))
					System.out.println(str+ "\t\toperator\t\tdecrement");
				else if(s.equals("+="))
					System.out.println(str+ "\t\toperator\t\tincrement and assign");
				else if(s.equals("-="))
					System.out.println(str+ "\t\toperator\t\tdecrement and assign");
				else if(s.equals("*="))
					System.out.println(str+ "\t\toperator\t\tmultiply and assign");
				else if(s.equals(">"))
					System.out.println(str+ "\t\toperator\t\tgreater than");
				else if(s.equals("<"))
					System.out.println(str+ "\t\toperator\t\tless than");
				else if(s.equals("&&"))
					System.out.println(str+ "\t\toperator\t\tAND");
				else if(s.equals("||"))
					System.out.println(str+ "\t\toperator\t\tOR");
				return true;
			}
				
		}
		return false;
	}
	
	public static boolean checkSymbol(String str)
	{
		for(String s : symbols)
		{
			if(s.equals(str))
			{
				if(s.equals("("))
					System.out.println(str+ "\t\tspecial symbol\t\topening braces");
				else if(s.equals(")"))
					System.out.println(str+ "\t\tspecial symbol\t\tclosing braces");
				else if(s.equals("{")) 
					System.out.println(str+ "\t\tspecial symbol\t\tleft curly braces");
				else  if(s.equals("}"))
					System.out.println(str+ "\t\tspecial symbol\t\tright curly braces");
				else if(s.equals(","))
					System.out.println(str+ "\t\tspecial symbol\t\tcomma");
				else if(s.equals(";"))
					System.out.println(str+ "\t\tspecial symbol\t\tsemi-colon");
								
				return true;
			}
				
		}
		return false;
	}
	
	public static boolean is_double_operator(String s)
	{

		if(s.equals("==") || s.equals("+=") ||s.equals("-=") ||s.equals("*=") ||s.equals("/=") ||s.equals("++") ||s.equals("--"))
		{
			
			return true;
		}
				
		return false;
	}
	
	public static boolean is_single_operator(char c)
	{
		String st = String.valueOf(c);
		for(int i = 0 ; i < single_operators.length ; i++)
			if(single_operators[i].equals(st))
				return true;
		return false;
	}
	
	public static boolean isValidJavaIdentifier(String s) {
	    if (s.isEmpty()) {
	        return false;
	    }
	    if (!Character.isJavaIdentifierStart(s.charAt(0))) {
	        return false;
	    }
	    for (int i = 1; i < s.length(); i++) {
	        if (!Character.isJavaIdentifierPart(s.charAt(i))) {
	            return false;
	        }
	    }
	    
	    return true;
	}
	
	    public static String[] split(String st){
						
		    ArrayList<Character> str=new ArrayList<Character>();
		    String myStrings[] = new String[30];
		    char ch, last_char = ' ';
		    int pointer=0;
			for(int i=0; i < st.length() ; i++){
				
				ch=st.charAt(i);
				
				if(!is_separator(ch)){
					str.add(ch);
					
				}
				else{
					
					if(!str.isEmpty()) {
						myStrings[++pointer]=getStringRepresentation(str);
						str.clear();
					}
						if( (i + 1) < st.length() )
						{
							String a = String.valueOf(ch);
							a = a.concat(String.valueOf(st.charAt(i+1)));
							
							if(is_double_operator(a))
							{	
								myStrings[++pointer]=a;
								i = i + 1;
							}else if(is_single_operator(ch))
							{
							
								myStrings[++pointer]=String.valueOf(ch);
							}
							else
							{			
								myStrings[++pointer]=String.valueOf(ch);		
							}
						}
																	
				}
				
				if(st.length() - 1 == i)
				last_char = ch;
							
			}
			if(!str.isEmpty()) {
				myStrings[++pointer]=getStringRepresentation(str);
				str.clear();
			}else
				myStrings[++pointer]=String.valueOf(last_char);
			return myStrings;
		}
		
		public static String getStringRepresentation(ArrayList<Character> list)
		{    
		    StringBuilder builder = new StringBuilder(list.size());
		    for(Character ch: list)
		    {
		        builder.append(ch);
		    }
		    return builder.toString();
		}
		
		
		public static Boolean is_separator(char ch){
			if(ch==','||ch==';'||ch=='/'||ch=='+'||ch=='-'||ch=='*' ){
				 return true;
			}
			
			else if(ch=='('||ch==')'||ch=='{'||ch=='}'||ch=='['){
				
				 return true;
			}
			else if(ch==']'||ch=='#'||ch=='\\'||ch=='"'||ch=='<'||ch=='>' || ch == '='){
				
				 return true;
			}else if(Character.isWhitespace(ch))
				return true;
			
		return false;		
		}
		
		
		public static boolean isBlank(String string) {
	        if (string == null || string.length() == 0)
	            return true;

	        int l = string.length();
	        for (int i = 0; i < l; i++) {
	            if (! Character.isWhitespace(string.charAt(i)) )
	                return false;
	        }
	        return true;
	    } 
	
		
	
}	
