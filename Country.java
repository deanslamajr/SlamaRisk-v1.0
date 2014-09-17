// SlamaRisk v1.0
// Country class 
// code by Dean Slama Jr
// June 2014

import java.io.Serializable;
import java.util.ArrayList;


public class Country implements Serializable
{

    private String name;
    private Player occupant;
    private int numOfArmies;
    private ArrayList<Country> adjacencies;

    public Country( String name )
    {
	this.name = name;
	this.adjacencies = new ArrayList<>();
    }

    
    /**
     * Used only when contstructing the country object, it should not be called after
     * the board is initialized
     **/
    public void addAdjacencies( ArrayList<Country> adjacencies )
    {
	this.adjacencies.addAll( adjacencies );
    }

   
    public String getName()
    {
	return this.name;
    }


    /**
     * When a player conquers a country the player object is set as the occupant of the country
     **/
    public void setOccupant( Player occupant )
    {
	this.occupant = occupant;
    }


    /**
     * Returns the player object who currently occupies the country
     **/
    public Player getOccupant()
    {
	return this.occupant;
    }


    /**
     * Used to set the number of armies currently stationed in this country
     **/
    public void setNumArmies( int numArmies )
    {
	this.numOfArmies = numArmies;
    }

    /**
     * Increments or decrements the number of armies currently stationed in 
     * this country
     **/
    public void changeNumArmies( int change )
    {
	this.numOfArmies += change;
	if( this.numOfArmies < 0 )
		this.numOfArmies = 0;
    }

    /**
     * Returns the number of armies currently stationed in this country
     **/
    public int getNumArmies()
    {
	return numOfArmies;
    }


    /**
     *  Returns a list of the country objects that are adjacent to this country on the baord
     **/
    public ArrayList<Country> getAdjacencies()
    {
	return adjacencies;
    }
}
