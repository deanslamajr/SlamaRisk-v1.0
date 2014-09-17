// SlamaRisk v1.0
// Player class 
// code by Dean Slama Jr
// June 2014

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collection;

public class Player implements Serializable
{

    private String name;
    private int numberArmies;
    private HashMap<String, Country> countriesHeld;
    private HashMap<String, Continent> continentsHeld;
    private ArrayList<Card> riskCards;
    private Color playerColor;

    public Player( String name, int numberArmies )
    {
	this.name = name;
	this.numberArmies = numberArmies;
	this.countriesHeld = new HashMap<String, Country>();
	this.continentsHeld = new HashMap<String, Continent>();
	this.riskCards = new ArrayList<Card>();
    }

    /**
     * Returns array of cards representing all the cards in the player's hand
     **/
    public String[] getHandStrings()
    {
	String[] hand = new String[ getHandSize() ];
	Card currentCard;
	String cardID;

	for( int i = 0; i < getHandSize() ; i++ )
	{
		currentCard = this.riskCards.get( i );
		cardID = "";
		cardID += currentCard.getCountry().getName();
		cardID += "  ";
		cardID += currentCard.getType();
		hand[ i ] = cardID;
	}

	return hand;
    }

    /**
     * Sets color
     **/
    public void setColor( Color color )
    {
	this.playerColor = color;
    } 

    /**
     * Get color
     **/
    public Color getColor()
    {
	return this.playerColor;
    }

    /**
     * Returns number of armies
     **/
    public int getNumArmies()
    {
	return this.numberArmies;
    }

    /**
     * Returns the number of cards in the player's hand
     **/
    public int getHandSize()
    {
	return this.riskCards.size();
    }

    /**
     * Returns reference to continentsHeld hashmap
     **/
    public HashMap<String, Continent> getContinents()
    {
	return this.continentsHeld;
    }

    /**
     * Returns number of countries player currently holds
     **/
    public int getCountriesCount()
    {
	return this.countriesHeld.size();
    }

    /**
     * Returns reference to the HashMap containing the player's held countries
     **/
    public HashMap<String, Country> getCountries()
    {
	return this.countriesHeld;
    }

    /**
     * Returns ArrayList containing player's held countries
     **/
    public ArrayList<Country> getListCountries()
    {
	Collection<Country> collection = this.countriesHeld.values();
	Iterator<Country> tempIterator = collection.iterator();
	ArrayList<Country> list = new ArrayList<Country>( collection.size() );
	while( tempIterator.hasNext() )
		list.add( tempIterator.next() );
	return list;
    }

    /**
     * Returns reference to cards in players hand
     **/
    public Card getCard( int index )
    {
	return this.riskCards.get( index );
    }
    

    /**
     * Returns the player's name
     **/
    public String getName()
    {
	return this.name;
    }

    /**
     * Checks if this player holds a particular country
     **/
    public boolean hasCountry( Country country )
    {
	boolean value = false;
	if( this.countriesHeld.containsValue( country ) )
	{
		value = true;
	}
	return value;
    }
    
    /**
     * Checks if this player holds a particular continent
     **/
    public boolean hasContinent( Continent continent )
    {
	boolean value = false;
	if( this.continentsHeld.containsValue( continent ) )
	{
		value = true;
	}
	return value;
    }

    /**
     * Decreases the count of the number of armies the player has on the board
     * This count has to reflect the actual number of armies the players has on 
     * the board
     **/
    public void decrementArmies( int numArmiesLost )
    {
	this.numberArmies = this.numberArmies - numArmiesLost;
    }


    /**
     * Increases the count of the number of armies the player has on the board
     * This count has to reflect the actual number of armies the players has on 
     * the board
     **/
    public void incrementArmies( int numArmiesGained )
    {
	this.numberArmies = this.numberArmies + numArmiesGained;
    }


    /**
     * When a player conquers a country the country needs to be added to a data structure
     * that keeps track of all the countries the player occupies
     **/
    public void addCountry( Country country )
    {
	this.countriesHeld.put( country.getName(), country );
    }


    /**
     * Works like addCountry above, but can be used to add multiple countries at once
     **/
    public void addCountry( ArrayList<Country> countriesList )
    {
	for( Country cont : countriesList )
	{
		this.countriesHeld.put( cont.getName(), cont );
	}
    }


    /**
     * When a player loses a country, the country must be removed from the data structure
     * tracking which countries the player occupies
     **/
    public void removeCountry( String countryName )
    {
	this.countriesHeld.remove( countryName );
    }


    /**
     * When a player occupies all the countries on a continent the continent must be added to
     * a data structure that tracks which continents the player occupies
     **/
    public void addContinent( Continent continent )
    {
	this.continentsHeld.put( continent.getName(), continent );	
    }

    /**
     * Removes a contient from the data structure to reflect that the player nolonger controls
     * the whole continent
     **/
    public void removeContinent( String continentName )
    {
	this.continentsHeld.remove( continentName );
    }

    
    /**
     * Adds a risk card to the players hand
     **/
    public void addRiskCard( Card riskCard )
    {
	this.riskCards.add( riskCard );
    }


    /**
     * Removed a set of risk cards from the players hand to reflect risk cards being turned in
     **/
    public void removeCards( int[] cardsTurnedInIndex )
    {
	Card[] cardsToBeRemoved = new Card[ cardsTurnedInIndex.length ];

	for( int  i = 0; i < cardsTurnedInIndex.length; i++ )
	{
		cardsToBeRemoved[ i ] = this.riskCards.get( cardsTurnedInIndex[ i ] );
	}

	Iterator iter = this.riskCards.iterator();
	    	
	while( iter.hasNext() )
	{
		Card test = (Card) iter.next();
		for( Card removeThis : cardsToBeRemoved )
		{
			if( test.getType() == removeThis.getType() && 
				test.getCountry() == removeThis.getCountry() )
			{
				iter.remove();
			}
		}
	}
    }

}
