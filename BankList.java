/**
 * File:BankList.java
 * Author: Li Jingwei
 * Date: October 21, 2015
 * 
 * Description: This class specifies data in the bank list.
 */

/**
 * This is the BankList class for storing data in the list.
 */
public class BankList
{
	// Define the variables.
	private int arrTime;
	private String service;
	private String role;
	private String name;
	private int time;
	private int lan;
	private String[] nameLan;
	private int worktime;
	private BankList object = null;
	public boolean free = true;

	/**
	 * This is the only constructor for this class. This constructor initializes
	 * the bank list and store data into it.
	 */
	public BankList( int A, String S, String R, String N, int T, int L, String[] li)
	{
		// Store data into local variables.
		arrTime = A;
		service = S;
		role = R;
		name = N;
		time = T;
		lan = L;
		nameLan = new String [lan];
		
		// Loop through input string and store it into local string.
		for ( int i = 0; i < L; i++)
		{
			nameLan[i] = li[i];
		}
	}
	
	// Return the arrive time.
	public int arriveTime()
	{
		return arrTime;
	}
	
	// Return the service string.
	public String service()
	{
		return service;
	}
	
	// Return the role string.
	public String role()
	{
		return role;
	}
	
	// Return the name string.
	public String name()
	{
		return name;
	}
	
	/**
	 *  Return the number of minutes a client needs for service 
	 *  or the length of a tell's shift.
	 */
	public int time()
	{
		return time;
	}
	
	// Return the string array of the names of language the person speaks.
	public String[] nameLanguage()
	{
		return nameLan;
	}
	
	// Update the time.
	public void timeUpdate()
	{
		if ( this.time != 0 )
		{
			this.time--;
		}
	}
	
	// Initialize the work time when a new service begins.
	public void workBegin()
	{
		worktime = 0;
	}
	
	// Update the work time.
	public void worktimeUpdate()
	{
		worktime++;
	}
	
	// Return the work time
	public int worktime()
	{
		return worktime;
	}
	
	// Set the object when work begins.
	public void objectSet( BankList ob )
	{
		object = ob;
	}
	
	// Remove the object when work ends.
	public void objectGo()
	{
		object = null;
	}
	
	// Return the object.
	public BankList object()
	{
		return object;
	}
	
	// Return true if the two people share one common language.
	public boolean shareLanguage( String[] inputLan )
	{
		for ( int i1 = 0; i1 < lan; i1++)
			for ( int i2 = 0; i2 < inputLan.length; i2++)
			{
				if ( nameLan[i1].equals(inputLan[i2]) )
				{
					return true;
				}
			}
		
		return false;
	}
	
	// The service time increments if no common language.
	public void clientTimeAdd()
	{
		this.time += 5;
	}
}
