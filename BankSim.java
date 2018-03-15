/**
 * File:BankSim.java
 * Author: Li Jingwei
 * Date: October 21, 2015
 * 
 * Description: This class designs and implements a simple model of a bank,
 * to solve a very common problem in the business world.
 */
import java.util.Scanner;
import java.util.ArrayList;

import org.omg.CORBA.FREE_MEM;

/**
 * This is the main class of the program and contains some program code.
 * The program starts running in the main() of this class.
 */
public class BankSim {

	/**
	 * This method is called when the program starts running.
	 * It simulates the bank and outputs hourly (every 60 minutes) statistics 
	 * on the average number of clients in the two lines (personal banking and
	 * business banking). 
	 */
	public static void main(String[] args)
	{
		// Instantiate new scanner to read from the console.
		Scanner keyboard = new Scanner(System.in);

		// Get the input and store it into variable.
		int A = keyboard.nextInt();
		
		// Define the other variables.
		String S;
		String R;
		String N;
		int T;
		int L;
		String[] Li;
		ArrayList<BankList> clientsList = new ArrayList<BankList>();
		ArrayList<BankList> tellersList = new ArrayList<BankList>();
		ArrayList<BankList> personLine = new ArrayList<BankList>();
		ArrayList<BankList> businessLine = new ArrayList<BankList>();
		ArrayList<BankList> tellerLine = new ArrayList<BankList>();
		ArrayList<Integer> numPerClients = new ArrayList<Integer>();
		ArrayList<Integer> numBusClients = new ArrayList<Integer>();
		ArrayList<Integer> resPerClients = new ArrayList<Integer>();
		ArrayList<Integer> resBusClients = new ArrayList<Integer>();
		BankList bank;
		
		// Judge if the input is -1.
		while ( A != -1 )
		{
			// Get the input and store it into specific variable.
			S = keyboard.next();
			R = keyboard.next();
			N = keyboard.next();
			T = keyboard.nextInt();
			L = keyboard.nextInt();
			Li = new String[L];
			
			// Store the languages into the string.
			for ( int i = 0; i < L; i++)
			{
				Li[i] = keyboard.next();
			}
			
			// Store all data into BankList.
			bank = new BankList( A, S, R, N, T, L, Li);
			
			// Judge if the role of the person is client.
			if ( bank.role().equals("client") )
			{
				clientsList.add(bank);
			}
			
			// Judge if the role of the person is teller.
			if ( bank.role().equals("teller") )
			{
				tellersList.add(bank);
			}
			
			// Get the integer indicating arriving time.
			A = keyboard.nextInt();
		}
		
		// Define the counter.
		int counter = 0;
		
		// Loop the simulation.
		while ( tellersList.size() != 0 || tellerLine.size() != 0 )
		{
			// Increment the clock counter.
			counter++;
			
			// The amount of time each teller has spent with their client is updated.
			if ( tellerLine.size() != 0 )
			{
				for ( int i = 0; i < tellerLine.size(); i++)
				{
					tellerLine.get(i).timeUpdate();
					
					// Judge if the teller is free.
					if ( !(tellerLine.get(i).free) )
					{
						// Increment the work time when the service is going on.
						tellerLine.get(i).worktimeUpdate();
					}
				}
			}
						
			/**
			 *  All persons whose arrival time is equal to the current time enter the bank.
			 *  The tellers are added to the teller line (in order of arrival), and the clients
			 *  are added to the personal or business banking lines. 
			 */
			// Loop through the list when the arrive time is not larger than the counter.
			for ( int i = 0; i < clientsList.size();)
			{
				// Assign the person into the variable.
				bank = clientsList.get(i);
				
				// Judge if the arrive time is the counter.
				if ( bank.arriveTime() == counter )
				{
					// Print the information when the person arrives.
					System.out.println( counter + " : " + bank.role() + " " + bank.name() + " arrives");
					
					// Add the client into the specific array list and remove the person from the array list.
					if ( bank.service().equals("personal") )
					{
						personLine.add(bank);
						clientsList.remove(i);
					}
					else if ( bank.service().equals("business") )
					{
						businessLine.add(bank);
						clientsList.remove(i);
					}
					else 
					{
						// Increment the loop number when no element is deleted.
						i++;
					}
				}
				else 
				{
					// Increment the loop number when no element is deleted.
					i++;
				}
			}
				
			// Loop through the list when the arrive time is not larger than the counter.
			for ( int i = 0; i < tellersList.size();)
			{
				// Assign the person into the variable.
				bank = tellersList.get(i);
				
				// Judge if the arrive time is the counter.
				if ( bank.arriveTime() == counter )
				{
					// Print the information when the person arrives.
					System.out.println( counter + " : " + bank.role() + " " + bank.name() + " arrives");
					
					// Add the teller into the specific array list and remove the person from the array list.
					tellerLine.add(bank);
					tellersList.remove(i);
				}
				else 
				{
					// Increment the loop number when no element is deleted.
					i++;
				}
			}
			
			// Loop through the teller line.
			for ( int i = 0; i < tellerLine.size(); i++)
			{
				// Judge if the teller is working.
				if ( !(tellerLine.get(i).free) )
				{
					// Judge if the service time equals to the work time.
					if ( tellerLine.get(i).worktime() == tellerLine.get(i).object().time() )
					{
						// Print the information when the person leaves.
						System.out.println( counter + " : " + tellerLine.get(i).object().role() + " " +
								tellerLine.get(i).object().name() + " leaves" );
						
						// The teller becomes free.
						tellerLine.get(i).free = true;
						
						// The object leaves.
						tellerLine.get(i).objectGo();
					}
				}
			}
			
			// Loop through the teller line.
			for ( int i = 0; i < tellerLine.size();)
			{
				// Judge if the teller is free.
				if ( tellerLine.get(i).free )
				{
					// Judge if the shift time comes.
					if ( tellerLine.get(i).time() == 0 )
					{
						// Print the information when the person leaves.
						System.out.println( counter + " : " + tellerLine.get(i).role() + " " + 
						tellerLine.get(i).name() + " leaves");
						
						// Remove the teller from the array list.
						tellerLine.remove(i);
					}
					else 
					{
						// Increment the loop number.
						i++;
					}
				}
				else 
				{
					// Increment the loop number.
					i++;
				}
			}
			
			// Loop through the teller line.
			for ( int i = 0; i < tellerLine.size(); i++)
			{
				// Judge if the teller is free.
				if ( tellerLine.get(i).free )
				{
					// Judge the service type of the teller.
					if ( tellerLine.get(i).service().equals("personal") )
					{
						// Judge if there is a person in the personal client line.
						if ( personLine.size() != 0 )
						{
							// Service the first person in the personal client line.
							tellerLine.get(i).objectSet(personLine.get(0));
							
							// The teller becomes busy.
							tellerLine.get(i).free = false;
							
							// Judge if the client and the teller share the same language.
							if ( !(tellerLine.get(i).shareLanguage(personLine.get(0).nameLanguage())) )
							{
								personLine.get(0).clientTimeAdd();
							}
							
							// Service time counts from zero.
							tellerLine.get(i).workBegin();
							
							// Remove the person from the waiting line.
							personLine.remove(0);
							
							// Print the information when the person is served.
							System.out.println( counter + " : " + tellerLine.get(i).object().role() + " " +
								tellerLine.get(i).object().name() + " is served by teller " + tellerLine.get(i).name() );
						}
					}
					else if ( tellerLine.get(i).service().equals("business") )
					{
						// Judge if there is a person in the business banking line.
						if ( businessLine.size() != 0 )
						{
							// Service the first person in the business banking line.
							tellerLine.get(i).objectSet(businessLine.get(0));
							
							// The teller becomes busy.
							tellerLine.get(i).free = false;
							
							// Judge if the client and the teller share the same language.
							if ( !(tellerLine.get(i).shareLanguage(businessLine.get(0).nameLanguage())) )
							{
								businessLine.get(0).clientTimeAdd();
							}
							
							// Service time counts from zero.
							tellerLine.get(i).workBegin();
							
							// Remove the person from the waiting line.
							businessLine.remove(0);
							
							// Print the information when the person is served.
							System.out.println( counter + " : " + tellerLine.get(i).object().role() + " " +
								tellerLine.get(i).object().name() + " is served by teller " + tellerLine.get(i).name() );
						}
						// Judge if there is a person in the personal banking line.
						else if ( personLine.size() != 0 )
						{
							// Service the first person in the personal client line.
							tellerLine.get(i).objectSet(personLine.get(0));
							
							// The teller becomes busy.
							tellerLine.get(i).free = false;
							
							// Judge if the client and the teller share the same language.
							if ( !(tellerLine.get(i).shareLanguage(personLine.get(0).nameLanguage())) )
							{
								personLine.get(0).clientTimeAdd();
							}
							
							// Service time counts from zero.
							tellerLine.get(i).workBegin();
							
							// Remove the person from the waiting line.
							personLine.remove(0);
							
							// Print the information when the person is served.
							System.out.println( counter + " : " + tellerLine.get(i).object().role() + " " +
								tellerLine.get(i).object().name() + " is served by teller " + tellerLine.get(i).name() );
						}
					}
				}
			}
			
			// Record the numbers in the specific array list.
			numPerClients.add(personLine.size());
			numBusClients.add(businessLine.size());
			
			// Define the number for computing.
			int num = 0;
			
			// Compute the average number of clients in each line in the past hour.
			if ( counter % 60 == 0 )
			{
				// Loop through the personal line.
				for ( int i = 0; i < numPerClients.size(); i++)
				{
					num += numPerClients.get(i);
				}
					
				// Get the average number.
				num /= numPerClients.size();
				
				// Add the result into the array list.
				resPerClients.add(num);
				
				// Initialize the number for computing.
				num = 0;
				
				// Loop through the personal line.
				for ( int i = 0; i < numBusClients.size(); i++)
				{
					num += numBusClients.get(i);
				}
					
				// Get the average number.
				num /= numBusClients.size();
				
				// Add the result into the array list.
				resBusClients.add(num);
				
				// Clear the array lists.
				numPerClients.clear();
				numBusClients.clear();
			}
		}
		
		// Print the title.
		System.out.println("Hourly Average Line-Ups");
		
		// Print numbers for each hour simulation.
		for ( int i = 0; i < resPerClients.size(); i++)
		{
			System.out.println( "hour " + ( i + 1 ) + " : personal " + resPerClients.get(i) +
					", business " + resBusClients.get(i) );
		}
	}
}
