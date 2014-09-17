// SlamaRisk v1.0
// Deck class 
// code by Dean Slama Jr
// June 2014

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collections;

public class Deck implements Serializable
{

	private ArrayList<Card> deck;
	private File countriesSource; 	
	private transient Scanner fileInput;

	/**
	* Creates all 42 cards, one for each territory. Each card has either 
	* a type of Infantry, Cavalry, or Artillery. Ensure that the number of
	* Ifantry, Cavalry, and Artillery are the same
	**/
	public Deck( String countriesFileName ) 
	{
	
		try
		{
			this.countriesSource = new File( countriesFileName );
			this.fileInput = new Scanner( countriesSource );
			this.deck = new ArrayList<Card>( 42 ); 

			while( fileInput.hasNextLine() )
			{
				String temp = fileInput.nextLine();
				Country tempCountry = new Country( temp );
				Card inf = new Card( "Infantry", tempCountry );
				Card cav = new Card( "Cavalry", tempCountry );
				Card art = new Card( "Artillery", tempCountry );
				deck.add( inf );
				deck.add( cav );
				deck.add( art );
			}
		}

		catch( FileNotFoundException e )
		{
		
		}
	
	}

	/**
	* Removes a card from the deck and return it
	**/
	public Card draw()
	{
		Card drawnCard = this.deck.get( 0 );
		this.deck.remove( 0 );
		return drawnCard;
	}

	/**
	* Add a card to the deck
	**/
	public void add( Card addedCard )
	{
		this.deck.add( addedCard );
	}

	/**
	* Shuffle the deck of cards
	**/
	public void shuffle()
	{
		Collections.shuffle( this.deck );
	}

}
