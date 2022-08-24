/************************************************************************************************************************************
Project 1
Beverlyn Tsai
7/24/2020

This project involves writing a program that encodes and decodes messages. The program should prompt the user to select whether a message 
is to be encoded or decoded, and then prompts the user to enter the message. A blank space is used to separate each word in the message, 
and a period (.) is used to denote the end of a sentence. Separate methods must be used to encode and decode the input message. The coding 
scheme is very simple: the code equivalent for the letter A is Z, for B is Y, …, and for Z is A, as indicated in the following table:

*************************************************************************************************************************************/
import java.util.Scanner;
public class Project1
{
	public static void main( String [] args )
	{
		String inputString="";
		String outputString="";
		int choice=1;
	
		//Create a Scanner Object
		Scanner input= new Scanner(System.in);

		
		//Prompt the user choice
		System.out.print("You want to encode or decode (1=encode, 2=decode)");
		choice=input.nextInt();

		// Read the leftover new line
		input.nextLine();

		System.out.print("Please input a sentence:");

		// Read the line from user input
		inputString=input.nextLine();
		System.out.println("input String="+inputString);
		outputString=enCode(inputString);


		switch(choice){
			case 1:System.out.println("Your sentence is encoded to "+outputString);break;
			case 2:
			default: System.out.println("Your sentence is decoded to "+outputString);break;
		}

		
	}



	public static String enCode(String inputStr)
	{
		String newString="",singleChar="";
		int length=inputStr.length();

		for(int i=0;i<inputStr.length();i++){

			if(Character.isLetter(inputStr.charAt(i))){
				if(Character.isUpperCase(inputStr.charAt(i))){
					singleChar=encodeUpperLetter(inputStr.charAt(i));
				}else{
					singleChar=encodeLowerLetter(inputStr.charAt(i));
				}

			}else{
				singleChar=Character.toString(inputStr.charAt(i));
			}
			newString+=singleChar;
		}

		
		return newString;
	}


	public static String encodeUpperLetter(char inputChar)
	{

		char ch=' ';


		//if(Character.isLetter(inputChar)){
			ch=(char)(155-inputChar);	
		//}

		
		return Character.toString(ch);
	}

	public static String encodeLowerLetter(char inputChar)
	{

		char ch=' ';

		//System.out.println("inputChar  ="+Character.toString(inputChar));

		//if(Character.isLetter(inputChar)){
			ch=(char)(219-inputChar);	
		//}

		

		//System.out.println("ch="+Character.toString(ch));
		
		
		return Character.toString(ch);
	}



}