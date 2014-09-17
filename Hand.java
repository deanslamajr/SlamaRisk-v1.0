// SlamaRisk v1.0
// Hand class 
// code by Dean Slama Jr
// June 2014

import java.util.ArrayList;

public class Hand
{

	private ArrayList<Card> hand;

	/**
	* No-arg constructor. Instantiate Hand.
	**/
	public Hand()
	{
		this.hand = new ArrayList<Card>( 5 );
	}

	/**
	* Adds the card to the hand 
	**/
	public void add(Card card)
	{
		this.hand.add( card );
	}
	/**
	* Removes the cards at the given indices from the hand
	**/
	public ArrayList<Card> removeCardsFromHand(int index1, int index2, int index3)
	{
		ArrayList<Card> cardsToBeRemoved = new ArrayList<Card>( 3 );

		for( int i = 0; i > 3; i++ )
		{
			cardsToBeRemoved.add( hand.get( i ) );
		}

		return cardsToBeRemoved;
	}

	/**
	* returns true if the player can turn in cards
	**/
	public boolean canTurnInCards()
	{
		boolean value = false;

		if( hand.size() > 4 )
			value = true;

		else
		{
			int infantry = 0;
			int cavalry = 0;
			int artillery = 0;

			for( Card card : hand )
			{
				String tempType = card.getType();
				
				if( tempType == "Infantry" )
					infantry++;
				else if( tempType == "Cavalry" )
					cavalry++;
				else if( tempType == "Artillery" )
					artillery++;
			}

			if( infantry >= 3 || cavalry >= 3 || artillery >= 3 )
				value = true;

			if( infantry >= 1 && cavalry >= 1 && artillery >= 1 )
				value = true;
			
		}
		return value;
	}

	/**
	* Returns true if the player must turn in cards
	**/
	public boolean mustTurnInCards()
	{
		boolean value = false;

		if( hand.size() >= 5 )
			value = true;

		return value;
	}

	/**
	* Returns the hand
	**/
	public ArrayList<Card> getHand()
	{
		return hand;
	}
}
