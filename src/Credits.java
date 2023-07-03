
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Credits {

	private int circumnavigationCounter;
	private String name;
	private int[] expendedMP = new int[100];
	private String[] Areas = new String[100];
	private String[] AreaTypes = new String[100];
	
	private int transactionCounter;
	private int circumNaviCounter;
	private int chanceCounter;
	private int communityCounter;
	private String fileName; 
// Record MP transactions

//Record Areas saved.

//Record Globe circumnavigations.

// Record 
	public Credits(String name)
	{
		this.name = name;
		circumnavigationCounter = 0;
		transactionCounter = 0;
		chanceCounter = 0;
		communityCounter = 0;
		fileName = name + "credits.txt";
		
	}
	
	public void adjChanceCardCount()
	{
		chanceCounter++;
	}
	private ArrayList<String> savedAreas = new ArrayList<String>();
	
	public void adjCircumnavigation()
	{
		circumnavigationCounter++;
	}

	//add transaction
	public void addTransaction(int MP, String Area, String AreaType )
	{
	
		expendedMP[transactionCounter] = MP;
		Areas[transactionCounter] = Area;
		AreaTypes[transactionCounter] = AreaType;
		
		transactionCounter++;
	}
	//import all transactions to CSV File
	public void StoreCSV() 
	{	
		
		try
		{
			//Pseudocode for csv file to store credits for each player, printed out upon victory.
			FileWriter Credits = new FileWriter(fileName, true);
			
	 		// Makes sure arrays are maintained during the writing procedure.
			BufferedWriter buffer = new BufferedWriter(Credits);
			// Accesses java syntax.
			PrintWriter print = new PrintWriter(buffer);
			
			for (int i = 0; i < transactionCounter; i++)
			{
				print.println(expendedMP[i] + "," + Areas[i] + "," + AreaTypes[i] + ",");
			}
				
			print.flush();
			print.close(); 
			
			System.out.println("Credits saved.");
		}
		catch (Exception e)
		{
			System.out.println("Credits not saved");
		}
		
	}
	
	//Retrieve CSV file
	public void creditRetrieve() throws FileNotFoundException
	{
		
		Scanner sc = new Scanner(new File(fileName));
		sc.useDelimiter(",");
	
		int counterRecord = 0;
		
		/*
		 * System.out.printf("Congratulations %s, you won! \n",name);
		 * while(sc.hasNext()) { if (counterRecord % 3 == 0)
		 * System.out.printf("A total of %s \n", (sc.next().toString())); else if
		 * (counterRecord % 1 == 0)
		 * System.out.printf("men and women to help preserve the %s \n", sc.next());
		 * else if (counterRecord % 2 == 0) System.out.printf("in the %d \n",
		 * sc.next());
		 * 
		 * counterRecord++; }
		 */
		
		sc.close();
		// String for circumnavigations
		System.out.printf("You circumnavigated the globe %d times! \n",circumnavigationCounter);
		System.out.printf("You got a total of %d chance cards. \n", chanceCounter);
		System.out.printf("You got a total of %d community cards. \n\n",communityCounter);
		
	}
	
}
