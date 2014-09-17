// SlamaRisk v1.0
// Board class 
// code by Dean Slama Jr
// June 2014

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.regex.Pattern;

public class Board implements Serializable
{
    private HashMap<String, Country>  countries;
    private HashMap<String, Continent>  continents;
    private ArrayList< Integer > bonusArmies;
    private File fileNames;
    private transient Scanner input;
    private ArrayList<Country> adjacents; 
    
    public Board()
    {
	this.countries = new HashMap<String, Country>();
	this.continents = new HashMap<String, Continent>();
	this.bonusArmies = new ArrayList< Integer >();
    }


/**
  * Loads the information needed to contruct the board and constructs the country and continent obects
  * needed for the board from three files.  The first file lists all the countries.  The second file lists 
  * all of the continents and which countries are on a given continent.  The third file lists the adjacencies 
  * for each country.
  **/
    public boolean loadBoard( String countriesFile, String continentsFile, String adjacenciesFile ) throws FileNotFoundException
    {
	this.fileNames = new File( countriesFile );
	input = new Scanner( this.fileNames );
	Scanner secondInput;
	boolean hasLoaded = true;

	try
	{

	// Load this object's countries array from countries txt file
		while( input.hasNextLine() )
		{	
			Scanner skim = new Scanner( input.nextLine() );
			String tempCountString = "";
			do	
			{
				tempCountString += skim.next();
				if( skim.hasNext() )
					tempCountString += " ";
			} while( skim.hasNext() );		
			Country tempCount = new Country( tempCountString );
			this.countries.put( tempCountString, tempCount );		
		}
	
	//loads this object's adjacencies array with data from text file
		this.fileNames = new File( adjacenciesFile );
		input = new Scanner( this.fileNames );

		Country rootCountry;
		while( input.hasNextLine() )
		{
			secondInput = new Scanner( input.nextLine() );
			secondInput.useDelimiter( "[,\n]" );
			String whichCountry = secondInput.next();
			whichCountry = whichCountry.trim();
			adjacents = new ArrayList<Country>();
		
			while( secondInput.hasNext() )
			{
				String nextCountry = secondInput.next();
				adjacents.add( this.countries.get( nextCountry ) ); 
			}	

			rootCountry = this.countries.get( whichCountry );
			rootCountry.addAdjacencies( adjacents );
		}


		this.fileNames = new File ( continentsFile );
		input = new Scanner( this.fileNames );
		Continent tempCont;
		String tempContName;
		int tempContBonus;
		ArrayList<Country> members;
	

	//loads this object's continents array with data from text file
		while( input.hasNextLine() )
		{
			secondInput = new Scanner( input.nextLine() );
			secondInput.useDelimiter("[,\n]");
			tempContName = secondInput.next();
			tempContBonus = secondInput.nextInt();
			members = new ArrayList<Country>();
			while( secondInput.hasNext() )
			{
				members.add( this.countries.get( secondInput.next() ) );
			}

			tempCont = new Continent( tempContName, tempContBonus, members );
			this.continents.put( tempContName, tempCont ); 
		}
	}
    	catch( FileNotFoundException e )
	{
		hasLoaded = false;
		System.out.println( "trouble finding file" );
		System.out.println( e );		
	}

	return hasLoaded;
    }


/**
  * Returns a list containing the continent objects the board has
  **/
    public ArrayList<Continent> getContinents()
    {
	ArrayList<Continent> tempArrayList = new ArrayList<Continent>( continents.values() );
	return tempArrayList;
    }


/**
  * Returns the continent object whose name is the string continentName
  **/
    public Continent getContinentByName( String continentName )
    {
	Continent tempContinent = continents.get( continentName );
	return tempContinent;
    }


/**
  * Returns the number of bonus armies awarded to a player for controlling all the countries in
  * the continent whose name is the string continentName
  **/
    public int getBonusAmrines( String continentName )
    {
	Continent tempCont = this.continents.get( continentName );
	int bonusArmies = tempCont.getBonusArmies();
	return bonusArmies;
    }


/**
  * Returns a list of the country objects that are in the continent specified 
  * by the string continentName
  **/
    public ArrayList<Country> getMemeberCountries( String continentName )
    {
	Continent tempCount = this.continents.get( continentName );
	ArrayList<Country> tempCountryList = tempCount.getMemberCountries();
	return tempCountryList;
    }


/**
  * Returns a list of all of the country objects in the board
  **/
    public ArrayList<Country> getCountries()
    {
    	ArrayList<Country> tempCountriesList = new ArrayList<Country>( this.countries.values() );	
    	return tempCountriesList;
    }


/**
  * Returns the country object for the country secified by the string
  * countryName
  **/
    public Country getCountryByName( String countryName )
    {
	Country tempCountry = this.countries.get( countryName );
	return tempCountry;
    }


/**
  * Sets the occupant of the country object specified by the string countryName
  * to be the player object supplied as an argument.
  **/
    public void setOccupant( String countryName,  Player occupant )
    {
	Country tempCountry = this.countries.get( countryName );
	tempCountry.setOccupant( occupant );
    }


/**
  * Returns the player object that currently occupies the country specufied by
  * string countryName
  **/
    public Player getOccupant( String countryName )
    {
	Country tempCountry = this.countries.get( countryName );
	Player tempPlayer = tempCountry.getOccupant();
	return tempPlayer;

    }


/**
  * Sets the number of armies currently in the country specified by the string
  * countryName to the integer supplied as an argument
  **/
    public void setNumberArmies( String countryName, int numberArmies )
    {
	Country tempCountry = this.countries.get( countryName );
	tempCountry.setNumArmies( numberArmies );
    }


/**
  * Returns the number of armies currently in the country specified by the string
  * countryName
  **/
    public int getNumberArmies( String countryName )
    {
	Country tempCountry = this.countries.get( countryName );
	int numArmies = tempCountry.getNumArmies();
	return numArmies;
    }


/**
  * Returns a list of the country objects that are the countries adjacent to the country
  * specified by the string countryName on the board
  **/
    public ArrayList<Country> getAdjacencies( String countryName )
    {
	Country tempCountry = this.countries.get( countryName );
	ArrayList<Country> tempList = tempCountry.getAdjacencies();
	return tempList;
    }

}
