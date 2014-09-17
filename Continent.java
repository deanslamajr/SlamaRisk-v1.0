// SlamaRisk v1.0
// Continent class 
// code by Dean Slama Jr
// June 2014

import java.io.Serializable;
import java.util.ArrayList;

public class Continent implements Serializable
{

    private String name;
    private int bonusArmies;
    private ArrayList<Country> countries;

    public Continent( String name, int bonusArmies, ArrayList<Country> memberCountries )
    {
	this.name = name;
	this.bonusArmies = bonusArmies;
    	this.countries = memberCountries;
    }

    public String getName()
    {
	return this.name;
    }


    /**
     *  Returns the number of bonus armies a player gets per round when the player controls this
     * continent
     **/
    public int getBonusArmies()
    {
	return this.bonusArmies;
    }


    /**
     * Retuens a list of the country objects that are on this continent
     **/
    public ArrayList<Country> getMemberCountries()
    {
	return this.countries;
    }

}
