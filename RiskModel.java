// SlamaRisk v1.0
// RiskModel class 
// code by Dean Slama Jr
// June 2014

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.lang.ClassNotFoundException;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;


public class RiskModel extends Observable implements Serializable
{
	private int playersCount;
	private String[] hand; // For GUI testing purposes. 
	private ArrayList<Player> players;	
	private ArrayList<Integer> removedPlayers;
	private int whosTurn = 0;
	private Deck deck;
	private Board board;
	private int turnedInCount;
	private int stageOfTurn;
	private boolean loaded;
	private String confirmButtonString;
	private transient BufferedImage map;
	private transient ObjectOutputStream objectWriter;
	private transient ObjectInputStream objectReader;

	public RiskModel( String[] hand )
	{
		this.hand = hand;  // For GUI testing purposes
		this.turnedInCount = 0;
		this.stageOfTurn = 0;
		this.loaded = false;
		this.removedPlayers = new ArrayList<Integer>();
		loadMap();
	}
/**
  * After the user inputs all the variable game information via the GUI
  * this method is called by GUI to utilize that information
  **/

	public void setUpGame( ArrayList<String> names )
	{
		int initArmies = 0;
		ArrayList<Player> tempPlayerList = new ArrayList<Player>( this.playersCount );

		if( this.playersCount == 2)
			initArmies = 60;
		else if( this.playersCount == 3)
			initArmies = 35;
		else if( this.playersCount == 4)
			initArmies = 30;	
		else if( this.playersCount == 5)
			initArmies = 25;
		else if( this.playersCount == 6)
			initArmies = 20;
		
		for( int i = 0; i < this.playersCount ; i++ )
		{
			Player tempPlayer = new Player( names.get( i ), initArmies ); 
			tempPlayerList.add( tempPlayer );
		}
		
		this.players = tempPlayerList;

		this.deck = new Deck( "countries.txt" );
		this.deck.shuffle();
		this.board = new Board();
		try
		{
			this.board.loadBoard( "countries.txt", "continents.txt", "adjacencies.txt" );
		}
		catch( FileNotFoundException e)
		{
			System.err.println( e );
		
		}
	}

/**
  * Loads map image
  **/
	public void loadMap()
	{
		try
		{
			this.map = ImageIO.read( new File("map2.jpeg") );
		}
		catch( IOException e)
		{
			System.out.println("Image map2.jpeg not loaded");
			System.out.println( e );
		}	
	}

/**
  * Loades model from file
  **/
	public void load( String fileName )
	{
		RiskModel loadedModel = null;

		try
		{
			objectReader = new ObjectInputStream( new FileInputStream( fileName ) );
			loadedModel = (RiskModel) objectReader.readObject();
			objectReader.close();
		}
		catch( IOException e )
		{
			System.err.println( e );
		}
		catch( ClassNotFoundException e )
		{
			System.err.println( e );
		}
		
		//copy over loadedModel values to this model
		this.playersCount = loadedModel.playersCount;
		this.hand = loadedModel.hand;
		this.players = loadedModel.players;
		this.whosTurn = loadedModel.whosTurn;
		this.deck = loadedModel.deck;
		this.board = loadedModel.board;
		this.turnedInCount = loadedModel.turnedInCount;
		this.stageOfTurn = loadedModel.stageOfTurn;

		this.loaded = true;

		if( this.stageOfTurn == 1 )
		{
			confirmButtonString = "Goto attack stage";
		}
	}

/**
  * Sets loaded value
  **/
	public void setLoaded( boolean value )
	{
		this.loaded = value;
	}

/**
  * Gets loaded value
  **/
	public boolean getLoaded()
	{
		boolean value = this.loaded;
		return value;
	}
/**
  * Saves this model to disk
  **/
	public void save( String fileName )
	{
		try
		{
			objectWriter = new ObjectOutputStream( new FileOutputStream( fileName + ".ser" ) );
			objectWriter.writeObject( this );
			objectWriter.close();
		}
		catch( IOException e )
		{
			System.err.println( e );
		}
	}
/**
  * sets the stageOfTurn to a specific value
  * used to set a loaded files stage
  **/
        public void setStageOfTurn( int stage )
        {
                this.stageOfTurn = stage;
        }

/**
  * gets the stageOfTurn to a specific value
  * used to get a loaded files stage
  **/
        public int getStageOfTurn()
        {
                int stage = this.stageOfTurn;
                return stage;
        }

/**
  * Returns a reference to the map
  **/
	public BufferedImage getMap()
	{
		return this.map;
	}

/**
  * Returns a reference to the deck
  **/
	public Deck getDeck()
	{
		return this.deck;
	}

/**
  * Returns a reference to the Board object
  */
	public Board getBoard()
	{
		return this.board;
	}
/**
  * Set player's color
  **/
	public void setPlayersColor( int playersNumber, String color )
	{
		Color thisColor = null;
		if( color == "Red" )
			thisColor = Color.RED;
		else if( color == "Green" )
			thisColor = Color.GREEN;
		else if( color == "Yellow" )
			thisColor = Color.YELLOW;
		else if( color == "Pink" )
			thisColor = Color.PINK;
		else if( color == "Blue" )
			thisColor = Color.BLUE;
		else if( color == "Orange" )
			thisColor = Color.ORANGE;
		else
			thisColor = Color.BLACK;

		this.players.get( playersNumber ).setColor( thisColor );
		System.out.println( thisColor);
	}

/**
  * Get player's color
  **/
	public Color getPlayersColor( int playersNumber )
	{
		return this.players.get( playersNumber ).getColor();
	}

/**
  * Changes ownership of a continent to a player
  **/
	public void givePlayerContinent( Player newOwner, Continent continent )
	{
		newOwner.addContinent( continent );
	}

/**
  * Removes the ownership of a continent from a player
  **/
	public void removePlayerContinent( Player oldOwner, Continent continent )
	{
		oldOwner.removeContinent( continent.getName() );
	}

/**
  * Changes ownership of country to player
  **/
	public void givePlayerCountry( Player newOwner, Country country )
	{
		newOwner.addCountry( country );
		
		// if this isn't the initial choosing of territories
		// at beginning of game, the former occupant must be
		// accounted for
		if( country.getOccupant() != null )
		{
			Player oldOccupant = country.getOccupant();
			oldOccupant.removeCountry( country.getName() );
		}
		country.setOccupant( newOwner );
	}

/**
  * Changes number of armies occupying a country
  **/
	public void changeArmyCount( Country country, int change )
	{
		country.changeNumArmies( change );
		setChanged();
		notifyObservers();
	}

/**
  * Increments the whosTurn variable
  * This signifies the changing of players turns
  **/
	public void nextTurn()
	{
		if( whosTurn == this.playersCount - 1 )
			whosTurn = 0;
		else whosTurn++;
		
	// If this new turn value corresponds to the player indicee 
	// of a defeated player who has been removed from the game,
	// skip this turn value
		
		while( removedPlayers.contains( whosTurn ) )
		{
			whosTurn++;
		}
	}

/**
  * Returns the whosTurn value
  **/
	public int getTurn()
	{
		return whosTurn;
	}

/**
  * Sets the value of the variable that signifies the number of players playing
  **/
	public void setPlayersCount( int players )
	{
		playersCount = players;
	}

/**
  * Returns the value of the playersCount variable
  * This is a way for the view to ask the model for the current player
  **/ 
	public int getPlayersCount()
	{
		return playersCount;
	}

/**
  * Removes Player from game queue
  * Called after a player is defeated
  **/
  	public void removePlayer( Player defeated )
	{
		for( int i = 0; i < this.players.size(); i++ )
		{
			if( defeated == this.players.get( i ) )
			{
				this.removedPlayers.add( i );
				break;
			}
		}
	}

/**
  * Checks if there is only one player left in the game
  **/
  	public boolean isDownToLastPlayer()
	{
		boolean isLast = false;
		
		int playersLeft = this.players.size() - this.removedPlayers.size();

		if( playersLeft == 1 )
		{
			isLast = true;
		}

		return isLast;
	}

/**
  * for GUI testing purposes only
  **/
	public String[] getHand()
	{
		return hand;
	}

/**
  * Returns the value of a given players name variable
  **/
	public String getPlayerName( int index )
	{
		return this.players.get( index ).getName();
	}


/**
  * Returns reference to a particular player 
  **/
	public Player getPlayer( int index )
	{
		return this.players.get( index );
	}

/**
  * This method allows a GUI to turn in three cards via an int[]
  * These Card objects are removed from the player's hand
  * The proper number of armies is awarded to the player
  **/
	public int turnInCards( int[] cardsTurnedInIndex , Player player )
	{
		int awardedArmies = 0;
		this.turnedInCount++;
		if( this.turnedInCount < 6 )
			awardedArmies = 4 + ( turnedInCount - 1 ) * 2;
		if( this.turnedInCount == 6 )
			awardedArmies = 15;
		if( this.turnedInCount > 6 )
			awardedArmies = (turnedInCount - 3 ) * 5;
		
		if( player.hasCountry( player.getCard( cardsTurnedInIndex[0] ).getCountry() ) || 
		    player.hasCountry( player.getCard( cardsTurnedInIndex[1] ).getCountry() ) || 
		    player.hasCountry( player.getCard( cardsTurnedInIndex[2] ).getCountry() ) )
		{
			awardedArmies += 2;
		}
		player.incrementArmies( awardedArmies );
		player.removeCards( cardsTurnedInIndex );
	
		return awardedArmies;
	}

/**
  * Calculates how many armies to be awarded to a given player
  * at the beginning of a turn
  * This does not include armies awarded from turning in cards
  * A Separate method handles turning in cards (see method turnInCards)
  **/
	public void calculateArmies( Player player )
	{
		int awardedArmies = 0;
		
		if( player.getCountriesCount() <= 11 )
		{
			awardedArmies = 3;
		}
		else
		{
			awardedArmies = player.getCountriesCount() / 3;
		}

		ArrayList<Continent> continentsList = new ArrayList<Continent>( player.getContinents().values() );		

		if( continentsList.size() > 0 )
		{
			for( int i = 0; i < continentsList.size(); i++ )
			{
				int award = continentsList.get( i ).getBonusArmies();
				awardedArmies += award;
			}
		}

		player.incrementArmies( awardedArmies );
	}

/**
  * Allows player to place armies onto owned countries
  **/
	public void reinforce( Country country, int numArmies, Player player )
	{
		country.changeNumArmies( numArmies );	
		player.decrementArmies( numArmies );
	}

/**
  * Conducts an attack from one country on another country
  * Changes army counts of each country
  **/
	public int[] attack( Country attackingCountry, Country defendingCountry, int numAttack, int numDefend )
	{
		int[] results = { 0, 0 };
		Dice dice = new Dice();
		int[] attackRoll = dice.rollDice( numAttack );

		ArrayList<Integer> aRollList = new ArrayList<Integer>( attackRoll.length );
		for( int i = 0; i < attackRoll.length; i++ )
		{
			aRollList.add( attackRoll[ i ] );
		}

		
		int[] defendRoll = dice.rollDice( numDefend );

		ArrayList<Integer> dRollList = new ArrayList<Integer>( defendRoll.length );
		for( int i = 0; i < defendRoll.length; i++ )
			dRollList.add( defendRoll[ i ] );
		for( int i = 0 ; i < defendRoll.length ; i++ )
		{
			int aHighest = getHighest( aRollList );
			aRollList.remove( new Integer( aHighest ) );
			int dHighest = getHighest( dRollList );
			dRollList.remove( new Integer( dHighest ) );

			if( aHighest > dHighest )
			{	
				defendingCountry.changeNumArmies( -1 );
				results[0]++;
			}
			else
			{
				attackingCountry.changeNumArmies( -1 );
				results[1]++;
			}
		}
		return results;
	}

/**
  * Checks if a country has any armies
  * If country has no armies, it has been defeated by an attack
  * Returns a boolean value
  **/
	public boolean isDefeated( Country country )
	{
		boolean value = false;
		if( country.getNumArmies() == 0 )
			value = true;
		return value;
	}

/**
  * Private helper method that runs simple algorithm to determine 
  * highest valued int in a passed Integer ArrayList
  **/
	private int getHighest( ArrayList<Integer> rollList )
	{
		int highest = rollList.get( 0 );
		for( int i = 1; i < rollList.size() ; i++ )
		{
			if( rollList.get( i ) > highest )
				highest = rollList.get( i );
		}
		return highest;
	}

/**
  * Moves armies from one territory to another
  **/
	public void fortify( Country fromCountry, Country toCountry, int numArmies )
	{
		fromCountry.changeNumArmies( numArmies * -1 );
		toCountry.changeNumArmies( numArmies );
	}
}
