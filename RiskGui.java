// SlamaRisk v1.0
// Main GUI class 
// code by Dean Slama Jr
// June 2014

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Dimension;
import java.lang.NumberFormatException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Observer;
import java.util.Observable;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class RiskGui extends JFrame implements Observer
{
	private RiskModel model;
	private ImageIcon map;
	private JFileChooser chooser;
	private MapComponent mainMap;
	private FileNameExtensionFilter serExtensionFilter;
	private JButton newJButton;
	private JButton loadJButton;
	private JButton quitJButton;
	private JButton confirmPlayerCountJButton;
	private JButton backToMainJButton;
	private JButton backToCountJButton;
	private JButton startGameJButton;
	private JButton quitGameJButton;
	private JButton saveGameJButton;
	private JButton beginGameJButton;
	private JButton skipCardsSubmitJButton;
	private JButton turnInCardsJButton;
	private JButton turnInCardsJButton2;
	private JButton reinJButton1;
	private JButton attackJButton1;
	private JButton attackJButton2;
	private JButton fortifyJButton1;
	private JButton fortifyJButton2;
	private JButton confirmSelection; 
	private JComboBox<String> playersCountJCombo = new JComboBox<>( numbers );	
	@SuppressWarnings("unchecked")
	private JComboBox[] playersColorsJComboBox;
	private JComboBox<String> reinJComboBox1; 
	private JComboBox<String> attackJComboBox1; 
	private JComboBox<Integer> attackJComboBox2;
	private JComboBox<String> attackJComboBox3;
	private JComboBox<String> fortifyJComboBox1;
	private JComboBox<Integer> fortifyJComboBox2;
	private JComboBox<String> fortifyJComboBox3;
	private JComboBox<String> countriesBox; 
	private JTextField[] playersNameJTextField;
	private JLabel playersCountJLabel;
	private JLabel[] playersNameJLabel;
	private JLabel[] playersHandJLabel = new JLabel[ 6 ];	
	private JLabel reinJLabel1 = new JLabel( "Place Reinforcements" );
	private JLabel reinJLabel2 = new JLabel( "To where?" );
	private JLabel reinJLabel3;
	private JLabel attackJLabel1 = new JLabel( "Attack                                    ! " );
	private JLabel attackJLabel2 = new JLabel( "From where?" );
	private JLabel attackJLabel3 = new JLabel( "With how many armies?" );
	private JLabel attackJLabel4 = new JLabel( "Attack whom?" );
	private JLabel fortifyJLabel1 = new JLabel( "Fortify                                    ! " );
	private JLabel fortifyJLabel2 = new JLabel( "From where?" );
	private JLabel fortifyJLabel3 = new JLabel( "With how many armies?" );
	private JLabel fortifyJLabel4 = new JLabel( "To where?" );
	private JLabel blankJLabel = new JLabel();
	private JLabel choosePrompt; 
	private JLabel choosePrompt2;
	private JLabel choosePrompt3;
	private JLabel spacerJLabel;
	private JPanel playersCountJPanel;
	private JPanel[] playersNameJPanel;
	private JPanel mainMenuJPanel = new JPanel();
	private JPanel mainEastJPanel = new JPanel();
	private JPanel handJPanel = new JPanel();
	private JPanel generalJPanel = new JPanel();
	private JPanel subMenu1JPanel = new JPanel();
	private JPanel subMenu2JPanel;
	private JPanel mapJPanel = new JPanel();
	private JList<String> cardsJList = new JList<>();	
	private String[] countryStrings;
	private ArrayList< ArrayList<String> > toAddStrings;
	private ArrayList<String> countriesTempList;
	private HashMap<String, Dimension> mapCoorHash;
	private static final String[] numbers = 
		{"3", "4", "5", "6"};
	private static final String[] countries = {"West United States", "East United States", "Canada" };  //to be removed, filler
	private static final String[] colors = { "Red", "Green", "Yellow", "Pink", "Blue", "Orange", "Black" };
	private int goThrough = 0;	
	private int goThroughSetup = 0;
	private int playersWithArmiesLeft;
	private boolean awardCard;

	public RiskGui( RiskModel model )
	{
		super( "Slama Risk v1.0");
		
	// Initialize fields
		this.model = model;
		this.serExtensionFilter = new FileNameExtensionFilter( "SlamaRisk save file", "ser" );
		this.map = new ImageIcon( getClass().getResource( "map2.jpeg" ) );
		this.chooser = new JFileChooser();
		chooser.setFileFilter( serExtensionFilter );	

		Screen1Handler handler1 = new Screen1Handler();
		FortifyListener1 handler3 = new FortifyListener1();

	// Initialize GUI content
		this.saveGameJButton = new JButton( "Save Game" );
		saveGameJButton.addActionListener( handler1 );

		this.quitGameJButton = new JButton( "Quit Game" );
		quitGameJButton.addActionListener( handler1 );
		
		this.turnInCardsJButton = new JButton( "Submit Selected Cards" );
		turnInCardsJButton.addActionListener( handler1 );
		
		this.turnInCardsJButton2 = new JButton( "Submit Cards" );
		turnInCardsJButton2.addActionListener( handler1 );
		
		this.confirmPlayerCountJButton = new JButton( "OK" );
		confirmPlayerCountJButton.addActionListener( handler1 );				
		
		this.backToMainJButton = new JButton( "Go Back" );
		backToMainJButton.addActionListener( handler1 );				

		this.backToCountJButton = new JButton( "Back" );
		backToCountJButton.addActionListener( handler1 );
				
		this.startGameJButton = new JButton( "Start Game" );
		startGameJButton.addActionListener( handler1 );
				
		this.confirmSelection = new JButton( "Confirm" );
		confirmSelection.addActionListener( handler1 );

		this.beginGameJButton = new JButton( "Begin Game" );
		beginGameJButton.addActionListener( handler1 );
	
		this.skipCardsSubmitJButton = new JButton( "I Don't Want to Turn in Cards This Round" );
		skipCardsSubmitJButton.addActionListener( handler1 );
	
		this.reinJButton1 = new JButton( "Place Reinforcements" );
		reinJButton1.addActionListener( handler1 );
		
		this.attackJButton1 = new JButton( "Commence Attack" );
		attackJButton1.addActionListener( handler1 );
	
		this.attackJButton2 = new JButton( "Finished attacking" );
		attackJButton2.addActionListener( handler1 );

		this.fortifyJButton1 = new JButton( "Fortify Positions" );
		fortifyJButton1.addActionListener( handler1 );
		
		this.fortifyJButton2 = new JButton( "Skip Fortifying" );
		fortifyJButton2.addActionListener( handler1 );

		this.attackJComboBox2 = new JComboBox<>();
	
		this.attackJComboBox3 = new JComboBox<>();
	
		this.fortifyJComboBox1 = new JComboBox<>();
		fortifyJComboBox1.addActionListener( handler3 );			
	
		this.fortifyJComboBox2 = new JComboBox<>();
	
		this.fortifyJComboBox3 = new JComboBox<>();

		this.awardCard = false;

		this.spacerJLabel = new JLabel("                                                                         ");

		subMenu2JPanel = new JPanel();
		subMenu2JPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
		subMenu2JPanel.add( quitGameJButton );

		
	// Build first GUI view
		
		setLayout(new FlowLayout() );
		
		newJButton = new JButton( "New" );
		add( newJButton );
		newJButton.addActionListener( handler1 );

		loadJButton = new JButton( "Load" );
		add( loadJButton );
		loadJButton.addActionListener( handler1 );

		quitJButton = new JButton( "Quit" );
		add( quitJButton );
		quitJButton.addActionListener( handler1 );

	//Load Country Coordinates for Main Map display

		File coorFile = new File( "Coordinates.txt" );
		mapCoorHash = new HashMap<String, Dimension>();

		try
		{
			Scanner input = new Scanner( coorFile );

			while( input.hasNext() )
			{
				String countName = input.next();
			//check if next token is string or int
			//Some countries have multiple worded names ex. East United States
				int xCoor = 0;
				boolean multipleWordedCountryName = false;
				do
				{
					String nextToken = input.next();
					try
					{
						xCoor = Integer.parseInt( nextToken );
						multipleWordedCountryName = false;
					} catch( NumberFormatException e )
					{
						countName += " ";
						countName += nextToken;
						multipleWordedCountryName = true;
					}
				} while( multipleWordedCountryName );
				int yCoor = input.nextInt();
				Dimension dim = new Dimension( xCoor, yCoor );
				mapCoorHash.put( countName, dim );
			}
		}
		catch( FileNotFoundException e )
		{
			System.err.println( e );
		}
	}

	public void update( Observable obs, Object obj )
	{
		repaint();
	}

	private class MapComponent extends JComponent	
	{
		private BufferedImage image;
		private Font font;

		public MapComponent()
		{
			this.image = model.getMap();
			this.setOpaque( true );
			this.font = new Font( Font.SANS_SERIF, Font.BOLD, 30 );
			repaint();
		}

		public void paintComponent( Graphics g )
		{
			g.drawImage( image, 0 , 0 , 1283, 762, this );
			g.setFont( font );
	

			for( int i = 0; i < model.getPlayersCount(); i++)
			{
				HashMap< String, Country> tempHash = model.getPlayer( i ).getCountries();
				ArrayList<Country> tempList = new ArrayList<Country>( tempHash.values() );

				for( int j = 0; j < tempList.size() ; j++ )
				{
					Country tempCountry = tempList.get( j );
					Double tempX =  mapCoorHash.get( tempCountry.getName() ).getWidth();
					Double tempY =  mapCoorHash.get( tempCountry.getName() ).getHeight();
					int x = tempX.intValue();
					int y = tempY.intValue();
					Integer numberArmies = tempCountry.getNumArmies();
					g.setColor( model.getPlayersColor( i ) );
					g.drawString( numberArmies.toString() , x , y );
				}
			}
		}


	}
	private class FortifyListener1 implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			@SuppressWarnings("unchecked")
			JComboBox<String> fillerBox = (JComboBox) event.getSource();
			String fortifySelect1 = (String) fillerBox.getSelectedItem();
		
		// populate fortifyJComboBox2 (# of armies to relocate)
			fortifyJComboBox2.removeAllItems();
		
			int armies = model.getBoard().getCountryByName( fortifySelect1 ).getNumArmies();
			
			for( int i = 1 ; i < armies ; i++ )
			{
				fortifyJComboBox2.addItem( i );
			}

		// populate fortifyJComboBox3 (which adjacent owned territory to move relocate armies to)
			fortifyJComboBox3.removeAllItems();

			List<Country> adjacents = model.getBoard().getCountryByName( fortifySelect1 ).getAdjacencies();
			
		// If territory is not owned by current player, cannot fortify the territory
			Iterator<Country> it = adjacents.iterator();
				while( it.hasNext() )
				{
					Country test = it.next();
					if( test.getOccupant() !=  model.getPlayer( model.getTurn() ) )
					{
						it.remove();
					}
				}
		
			for( Country adj: adjacents )
			{
				fortifyJComboBox3.addItem( adj.getName() );
			}
		
			revalidate();
			repaint();

		}
	}

	private class AttackListener1 implements ActionListener
	{	
		public void actionPerformed( ActionEvent event )
		{

			@SuppressWarnings("unchecked")
			JComboBox<String> fillerBox = (JComboBox) event.getSource();
			String attackSelect1 = (String) fillerBox.getSelectedItem();

		// populate attackJComboBox2 (# of armies to attack with)
			attackJComboBox2.removeAllItems();		

			int armies = model.getBoard().getCountryByName( attackSelect1 ).getNumArmies();	
			if( armies > 3 )
			{
				armies = 4;
			}
			for( int i = 1 ; i < armies ; i++ )
			{
				attackJComboBox2.addItem( i );
			}

		// populate attackJComboBox3 (which country to attack)
			attackJComboBox3.removeAllItems();
			
			List<Country> adjacents = model.getBoard().getCountryByName( attackSelect1 ).getAdjacencies();
	
			for( Country adj: adjacents )
			{
				if( !model.getPlayer( model.getTurn() ).hasCountry( adj ) )
				{
					attackJComboBox3.addItem( adj.getName() );
				}
			}

			generalJPanel.add( blankJLabel );
			generalJPanel.add( attackJLabel3 );
			generalJPanel.add( attackJComboBox2 ); // # of armies to attack with
			generalJPanel.add( blankJLabel );
			generalJPanel.add( attackJLabel4 );    // 'Attack whom?'
			generalJPanel.add( attackJComboBox3 ); // which country to attack
			generalJPanel.add( blankJLabel );
			generalJPanel.add( blankJLabel );
			
			generalJPanel.add( attackJButton1 );

			revalidate();
			repaint();

		}
	}

	private class Screen1Handler implements ActionListener
	{
		private void drawScreen()
		{
			mainMap = new MapComponent();
		
			getContentPane().removeAll();

		//Draw main map
			setContentPane( mainMap );
			setSize( 1500, 850 );

		//Draw Save and Quit Buttons
			setLayout( new BorderLayout() );				
			mainMenuJPanel.setLayout( new GridLayout( 1, 2 ) );
			subMenu1JPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
			mainMenuJPanel.add( subMenu1JPanel );
			subMenu2JPanel.add( saveGameJButton );
			mainMenuJPanel.add( subMenu2JPanel );
			add( mainMenuJPanel, BorderLayout.SOUTH );		

		//Draw HandJPanel stuff
			mainEastJPanel.setLayout( new GridLayout( 2, 1 ) );
			handJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
			handJPanel.setLayout( new GridLayout( 3, 1) );		
			mainEastJPanel.add( handJPanel );
			generalJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
			generalJPanel.setLayout( new GridLayout( 13, 1 ) );
			mainEastJPanel.add( generalJPanel );
			String playerLabelInfo = model.getPlayerName( model.getTurn() );
			playersHandJLabel[0] = new JLabel( " " + playerLabelInfo + " Hand" );
			handJPanel.add( playersHandJLabel[0] );
			cardsJList.setListData( model.getPlayer( model.getTurn() ).getHandStrings() );
			cardsJList.setVisibleRowCount( 6 );
			cardsJList.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
			JScrollPane cardsScroll = new JScrollPane( cardsJList );
			
			handJPanel.add( cardsScroll );
			add( mainEastJPanel, BorderLayout.EAST );

		// To force mainEastJPanel to retain it's size
			handJPanel.add( spacerJLabel );

		// Color the screen to indicate which player is in control
			setBackground( model.getPlayersColor( model.getTurn() ) );
			
		// Initialize all actionListeners
			goThrough = 1;
			
		}
	
		public void actionPerformed( ActionEvent event )
		{
			JButton screenOneButton = (JButton)event.getSource();

			Screen1Handler handler1 = new Screen1Handler();
 			AttackListener1 handler2 = new AttackListener1();
			FortifyListener1 handler3 = new FortifyListener1();

			if( screenOneButton.getText() == "New" || screenOneButton.getText() == "Back" )
			{
				
				getContentPane().removeAll();
			
				setLayout( new GridLayout( 2, 1 ) );
	
				setSize( 250, 125 );
				
				playersCountJLabel = new JLabel("How many players?");
				add( playersCountJLabel );

				playersCountJPanel = new JPanel();

				playersCountJPanel.add( playersCountJCombo );
				playersCountJPanel.add( confirmPlayerCountJButton );
				playersCountJPanel.add( backToMainJButton );
		
				add( playersCountJPanel );

				revalidate();
				repaint();
				
			}
			
			else if( screenOneButton.getText() == "Load" )
			{
				int test = chooser.showDialog( RiskGui.this, "Load" );
				if( test == JFileChooser.APPROVE_OPTION )
				{
					String path = chooser.getCurrentDirectory().getAbsolutePath();
					path += "/";
					path += chooser.getSelectedFile().getName();
					model.load( path );
					System.out.println( "Load path = " + path );
					
					int choice = JOptionPane.showConfirmDialog( null, "Load this Game:\n " + model.getPlayersCount() + " Players"  , "Load File?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE ); 				
				//if choice == 1, reset model to unloaded one

					if( choice == 0 )
					{	
						drawScreen();
						System.out.println( model.getStageOfTurn() );
						JButton ghostButton;
						if( model.getStageOfTurn() == 1 )
						{
							ghostButton = new JButton( "reinforce" );
							ghostButton.addActionListener( handler1 );
							ghostButton.doClick(); 
						}
						else if( model.getStageOfTurn() == 2 )
						{
							ghostButton = new JButton( "attack" );
							ghostButton.addActionListener( handler1 );
							ghostButton.doClick(); 
						}
					
					}
					
				}
			}

			else if( screenOneButton.getText() == "Quit" || screenOneButton.getText() == "Quit Game" )
			{
				System.exit( 0 );
			}

			else if( screenOneButton.getText() == "Go Back" )
			{
				setLayout( new FlowLayout() );	
				getContentPane().removeAll();
				add( newJButton );
				add( loadJButton );
				add( quitJButton );
				revalidate();
				repaint();

			}

			else if( screenOneButton.getText() == "OK" )
			{
				int number = playersCountJCombo.getSelectedIndex();
				number += 3;
				model.setPlayersCount( number ); 			
				
				getContentPane().removeAll();
			
				setSize( 500, 300 );

				setLayout( new GridLayout( ( model.getPlayersCount() + 1 ) , 1 ));
				playersNameJLabel = new JLabel[ model.getPlayersCount() ];
				playersNameJTextField = new JTextField[ model.getPlayersCount() ];
				playersNameJPanel = new JPanel[ ( model.getPlayersCount() + 1 ) ];
				
				playersColorsJComboBox = new JComboBox[ model.getPlayersCount() ];			

				for(int i = 0; i < model.getPlayersCount() ; i ++)
				{
					String prompt = "What is Player " + (i + 1) + "'s Name?";
					playersNameJPanel[i] = new JPanel();

					playersNameJLabel[i] = new JLabel( prompt );
					playersNameJPanel[i].add( playersNameJLabel[i] );

					playersNameJTextField[i] = new JTextField( 15 );
					playersNameJPanel[i].add( playersNameJTextField[i] );

					playersColorsJComboBox[i] = new JComboBox<>( colors );
					playersNameJPanel[i].add( playersColorsJComboBox[i] );
	
					add( playersNameJPanel[i] );
				}
	
			
				playersNameJPanel[ ( model.getPlayersCount() ) ] = new JPanel();
				playersNameJPanel[ ( model.getPlayersCount() ) ].add( backToCountJButton );
				playersNameJPanel[ ( model.getPlayersCount() ) ].add( startGameJButton );
				add( playersNameJPanel[ model.getPlayersCount() ] );				
				
				revalidate();
				repaint();
			}

			else if ( screenOneButton.getText() == "Save Game" )
			{
				
				int test = chooser.showDialog( RiskGui.this, "Save" );
				if( test == JFileChooser.APPROVE_OPTION )
				{
					String path = chooser.getCurrentDirectory().getAbsolutePath();	
					path += "/";
					path += chooser.getSelectedFile().getName(); 
					model.save( path );
				}
		
			}

			else if( screenOneButton.getText() == "Start Game")
			{			
				mainMap = new MapComponent();				
	
				ArrayList<String> names = new ArrayList<String>( model.getPlayersCount() );
				for( int i = 0; i < model.getPlayersCount() ; i++ )
				{
					names.add( playersNameJTextField[ i ].getText() );
				}				
				
				model.setUpGame( names );

				for( int i = 0; i < model.getPlayersCount() ; i++ )
				{
					model.setPlayersColor( i, (String)playersColorsJComboBox[ i ].getSelectedItem() );
				}
	
				countryStrings = new String[ ( model.getBoard().getCountries().size() ) ];
				
				for( int i = 0 ; i < model.getBoard().getCountries().size() ; i++ )
				{
					countryStrings[i] = model.getBoard().getCountries().get( i ).getName();
				}

				getContentPane().removeAll();

			//Draw main map
				setContentPane( mainMap );
				setSize( 1500, 850 );

				setLayout( new BorderLayout() );				
				mainMenuJPanel.setLayout( new GridLayout( 1, 2 ) );
				subMenu1JPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
				mainMenuJPanel.add( subMenu2JPanel );
				add( mainMenuJPanel, BorderLayout.SOUTH );		
			
				mainEastJPanel.setLayout( new GridLayout( 2, 1 ) );
				handJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
				handJPanel.setLayout( new GridLayout( 4, 1) );		
				mainEastJPanel.add( handJPanel );
				generalJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
				generalJPanel.setLayout( new GridLayout( 13, 1 ) );
				mainEastJPanel.add( generalJPanel );
				add( mainEastJPanel, BorderLayout.EAST );

			// To force mainEastJPanel to retain it's size
				handJPanel.add( spacerJLabel );

				String countrySetupString = model.getPlayerName( model.getTurn() );
				String countrySetupString2 = "Please Select Your Next Country"; 
				choosePrompt = new JLabel( countrySetupString );
				generalJPanel.add( choosePrompt );
				choosePrompt2 = new JLabel( countrySetupString2 );
				generalJPanel.add( choosePrompt2 );
				countriesBox = new JComboBox<String>( countryStrings );
				generalJPanel.add( countriesBox );
				generalJPanel.add( confirmSelection );				

			// Color the screen to indicate which player is in control
				setBackground( model.getPlayersColor( model.getTurn() ) );
			
				revalidate();
				repaint();				
			}

			else if( screenOneButton.getText() == "Confirm" )
			{
			//	FOUR POSSIBLE CONDITIONS TO PROGRESS THROUGH IN ORDER
			//
			//		
			// ***	2nd condition	***
			// 	
			//	After all other countries have been selected, leaving one country left
			// 	Assign last country to player and
			// 	set up GUI for armies distribution
			//
				generalJPanel.remove( choosePrompt );
				generalJPanel.remove( choosePrompt2 );
				generalJPanel.remove( countriesBox );
				generalJPanel.remove( confirmSelection );
					
				if( countryStrings.length == 1 && goThroughSetup == 0 )
				{
					goThroughSetup++;
		
				//retrieved String of selected country from combobox
					String countryName = (String) countriesBox.getSelectedItem();
					
				//give player reference to country as owner
					model.givePlayerCountry( model.getPlayer( model.getTurn() ), model.getBoard().getCountryByName( countryName ) );
					
				//increase armies by one
					model.changeArmyCount( model.getBoard().getCountryByName( countryName ), 1 );
					
				// Set this array size to be greater than 1
				// Loop will never again enter this if block
					countryStrings = new String[2];
					
					model.nextTurn();
				
				// Color the screen to indicate which player is in control
					setBackground( model.getPlayersColor( model.getTurn() ) );
			
					
					toAddStrings = new ArrayList< ArrayList<String> >( model.getPlayersCount() );
	

				// Build and populate ArrayList<String> full of owned territories
				// Do this for each player
					for( int i = 0 ; i < model.getPlayersCount() ; i++ )
					{
						ArrayList<String> tempList = new ArrayList<String>( model.getPlayer( i ).getCountriesCount() );
						toAddStrings.add( tempList );
						ArrayList<Country> list = model.getPlayer( i ).getListCountries();						

						for( int j = 0 ; j < list.size() ; j++ )
						{
							toAddStrings.get( i ).add( list.get( j ).getName() );
						}
					}	
							
					playersWithArmiesLeft = model.getPlayersCount();				
	
				
				// Draw the instructions for player
					String prompt1 = " "; 
					prompt1 += model.getPlayerName( model.getTurn() );
					String prompt2 = " has ";
					prompt2 += model.getPlayer( model.getTurn() ).getNumArmies();
					prompt2 += " armies left to place.";
					choosePrompt = new JLabel( prompt1 );
					generalJPanel.add( choosePrompt );
					choosePrompt2 = new JLabel( prompt2 );
					generalJPanel.add( choosePrompt2 );
					choosePrompt3 = new JLabel( " Please place one army." );	
					generalJPanel.add( choosePrompt3 );
					
					String[] countriesArray = new String[ toAddStrings.get( model.getTurn() ).size() ];
					toAddStrings.get( model.getTurn() ).toArray( countriesArray );
					countriesBox = new JComboBox<String>( countriesArray );
					generalJPanel.add( countriesBox );
				
					generalJPanel.add( confirmSelection );

					revalidate();
					repaint();

				}

			//	*** 3rd condition	***
			//
			// 	Main armies distribution block
			//
				else if( playersWithArmiesLeft > 1 && goThroughSetup == 1 )
				{
					
					generalJPanel.remove( choosePrompt3 );
					String countryName = (String) countriesBox.getSelectedItem(); //retrieved String of selected country from combobox
					
				// Increase armies in occupied country by one
					model.changeArmyCount( model.getBoard().getCountryByName( countryName ), 1 );
					
				// Decrease armies for player by one
					model.getPlayer( model.getTurn() ).decrementArmies( 1 );
					
				// Check if current player has distributed their last army
					if( model.getPlayer( model.getTurn() ).getNumArmies() == 0 )
					{
						playersWithArmiesLeft--;
					}

					model.nextTurn();
			
				// Color the screen to indicate which player is in control
					setBackground( model.getPlayersColor( model.getTurn() ) );
			
					String prompt1 = " ";
					prompt1 += model.getPlayerName( model.getTurn() );
					String prompt2 = " you have ";
					prompt2 += model.getPlayer( model.getTurn() ).getNumArmies();
					prompt2 += " armies left to place.";
					choosePrompt = new JLabel( prompt1 );
					generalJPanel.add( choosePrompt );
					choosePrompt2 = new JLabel( prompt2 );
					generalJPanel.add( choosePrompt2 );
					choosePrompt3 = new JLabel( " Please place one army." );	
					generalJPanel.add( choosePrompt3 );
					String[] countriesArray = new String[ toAddStrings.get( model.getTurn() ).size() ];
					toAddStrings.get( model.getTurn() ).toArray( countriesArray );
					countriesBox = new JComboBox<String>( countriesArray );
					generalJPanel.add( countriesBox );
				
					generalJPanel.add( confirmSelection );

					revalidate();
					repaint();
				}
				

			// ***	4th and Last condition	***
			//
				else if(  playersWithArmiesLeft == 1 && goThroughSetup == 1 )
				{ 
					//retrieved String of selected country from combobox
					String countryName = (String) countriesBox.getSelectedItem();
					//increase armies in occupied country by one
					model.changeArmyCount( model.getBoard().getCountryByName( countryName ), 1 );
					//decrease armies for player by one
					model.getPlayer( model.getTurn() ).decrementArmies( 1 );
					
					generalJPanel.remove( choosePrompt3 );
					generalJPanel.add( beginGameJButton );

					revalidate();
					repaint();
				}						
			
			// ***  1st condition  ***
			//	
			//	if there is more than one country left to select
			//	carry on as usual
			//			
				else
				{
					generalJPanel.remove( choosePrompt );
					generalJPanel.remove( choosePrompt2 );
					generalJPanel.remove( countriesBox );
					generalJPanel.remove( confirmSelection );
			
					String countryName = (String) countriesBox.getSelectedItem(); //retrieved String of selected country from combobox
				// Give player reference to country as owner
					model.givePlayerCountry( model.getPlayer( model.getTurn() ), model.getBoard().getCountryByName( countryName ) );
				// Increase armies by one
					model.changeArmyCount( model.getBoard().getCountryByName( countryName ), 1 );
			
					countriesTempList = new ArrayList<>( Arrays.asList( countryStrings ) );
					countriesTempList.remove( countriesBox.getSelectedItem() );
					countryStrings = new String[ countriesTempList.size() ];
					countryStrings = countriesTempList.toArray( countryStrings ); 
					
					model.nextTurn();

				// Color the screen to indicate which player is in control
					setBackground( model.getPlayersColor( model.getTurn() ) );
			
					choosePrompt = new JLabel( model.getPlayerName( model.getTurn() ) );
					generalJPanel.add( choosePrompt );
					generalJPanel.add( choosePrompt2 );
					countriesBox = new JComboBox<String>( countryStrings );
					generalJPanel.add( countriesBox );
					generalJPanel.add( confirmSelection );

					revalidate();
					repaint();				

				}
			}	
			
			else if( screenOneButton.getText() == "Skip Fortifying" || screenOneButton.getText() == "Fortify Positions" )
			{

			// If player captured atleast one territory this round, award card from deck
				if( awardCard )
				{
				 	model.getPlayer( model.getTurn() ).addRiskCard( model.getDeck().draw() );
					
					// reset awardCard
					awardCard = false;
				}

			// Move to next player's turn
				model.nextTurn();
			
			// Color the screen to indicate which player is in control
				setBackground( model.getPlayersColor( model.getTurn() ) );
			
				generalJPanel.remove( fortifyJLabel1 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( fortifyJLabel2 );
				generalJPanel.remove( fortifyJComboBox1 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( fortifyJLabel3 );
				generalJPanel.remove( fortifyJComboBox2 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( fortifyJLabel4 );
				generalJPanel.remove( fortifyJComboBox3 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( fortifyJButton1 );

				subMenu1JPanel.remove( fortifyJButton2 );
				
				handJPanel.remove( playersHandJLabel[0] );
				
				if( screenOneButton.getText() == "Fortify Positions" )
				{
					model.fortify( model.getBoard().getCountryByName( (String) fortifyJComboBox1.getSelectedItem() ), 
						model.getBoard().getCountryByName( (String) fortifyJComboBox3.getSelectedItem() ),
						(Integer) fortifyJComboBox2.getSelectedItem() );
				}
	
			// Display the current players cards
				String playerLabelInfo = model.getPlayerName( model.getTurn() );
				playersHandJLabel[0] = new JLabel( " " + playerLabelInfo + "'s hand" );
				handJPanel.add( playersHandJLabel[0] );
				
				cardsJList.setListData( model.getPlayer( model.getTurn() ).getHandStrings() );
				cardsJList.setVisibleRowCount( 9 );
				cardsJList.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
				handJPanel.add( cardsJList );

			
			//Before allowing player ability to turn in cards, check if player has enough to turn in
			//If player does not have enough cards (3 or more) skip this step in the turn
				if( model.getPlayer( model.getTurn() ).getHandSize() >= 3 )	
				{
					
					subMenu1JPanel.add( turnInCardsJButton ); 
				
				//If player has 5 or 6 cards, they must turn in atleast one set
				if( model.getPlayer( model.getTurn() ).getHandSize() < 5 )
					{
						subMenu1JPanel.add( skipCardsSubmitJButton );
					}		
				}
				
				revalidate();
				repaint();

			//If player does not have enough cards to turn in
			//Move onto reinforce stage of player's turn
				if( model.getPlayer( model.getTurn() ).getHandSize() < 3 )
				{
					skipCardsSubmitJButton.doClick();
				}

			}
			
			else if(  screenOneButton.getText() == "Begin Game" || screenOneButton.getText() == "Submit Selected Cards" || screenOneButton.getText() == "I Don't Want to Turn in Cards This Round" || screenOneButton.getText() == "reinforce" )
			{
				model.setStageOfTurn( 1 );

			// Update the player's continent occupation status
				List<Continent> continents = model.getBoard().getContinents();
				for( Continent cont : continents )
				{
					int counter = 0;
				//get list of territories of this continent
					List<Country> countries = cont.getMemberCountries();
					for( Country thisCountry : countries )
					{
					//if player has territory, +1 to counter
						if( model.getPlayer( model.getTurn() ).hasCountry( thisCountry ) )
						{
							counter++;
						}
					}
					if( counter == countries.size() )
					{
						if( !model.getPlayer( model.getTurn() ).hasContinent( cont ) )
						{
						//give player continent
							model.givePlayerContinent( model.getPlayer( model.getTurn() ), cont );
						}
					}
					else
					{
						if( model.getPlayer( model.getTurn() ).hasContinent( cont ) )
						{
						//remove continent from player
							model.removePlayerContinent( model.getPlayer( model.getTurn() ), cont );
						}
					}
				}


			// Initialize main game GUI
				if( screenOneButton.getText() == "Begin Game" )
				{

				// Move to next player's turn
					model.nextTurn();

				// Color the screen to indicate which player is in control
					setBackground( model.getPlayersColor( model.getTurn() ) );
			
				// At this point in the game, the user is given the option to save progress
					subMenu2JPanel.add( saveGameJButton );
					
					generalJPanel.remove( beginGameJButton );

				//Draw Save and Quit Buttons
					setLayout( new BorderLayout() );				
					mainMenuJPanel.setLayout( new GridLayout( 1, 2 ) );
					subMenu1JPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
					mainMenuJPanel.add( subMenu1JPanel );
					mainMenuJPanel.add( subMenu2JPanel );
					add( mainMenuJPanel, BorderLayout.SOUTH );		

				//Draw HandJPanel stuff
					mainEastJPanel.setLayout( new GridLayout( 2, 1 ) );
					handJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
					handJPanel.setLayout( new GridLayout( 4, 1) );		
					mainEastJPanel.add( handJPanel );
					generalJPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
					generalJPanel.setLayout( new GridLayout( 13, 1 ) );
					mainEastJPanel.add( generalJPanel );
					add( mainEastJPanel, BorderLayout.EAST );

				// Draw Player's Hand
					String playerLabelInfo = model.getPlayerName( model.getTurn() );
					playersHandJLabel[0] = new JLabel( " " + playerLabelInfo + " Hand" );
					handJPanel.add( playersHandJLabel[0] );
				
					cardsJList.setListData( model.getPlayer( model.getTurn() ).getHandStrings() );
					cardsJList.setVisibleRowCount( 6 );
					cardsJList.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
					handJPanel.add( cardsJList );
				}
			
				if( screenOneButton.getText() == "Submit Selected Cards" )
				{
					ArrayList<String> valuesJList = (ArrayList<String>) cardsJList.getSelectedValuesList();

				// Get the elected cards' indices
					int[] indices = cardsJList.getSelectedIndices();

				// Award armies to player
					int awardedCount = model.turnInCards( indices, model.getPlayer( model.getTurn() ) );

					String[] cardsList = valuesJList.toArray( new String[ valuesJList.size() ] );
					String cardDialog = "";
					for(int i = 0 ; i < cardsList.length ; i++)
					{
						cardDialog += " " + cardsList[i];
					}
					cardDialog += " " + awardedCount + " armies awarded!";
					JOptionPane.showMessageDialog( null, "cards turned in = " + cardDialog, "Cards turned in", JOptionPane.PLAIN_MESSAGE );
			
				// Update list of players cards
					cardsJList.setListData( model.getPlayer( model.getTurn() ).getHandStrings() );

				}

				subMenu1JPanel.remove( skipCardsSubmitJButton );
				subMenu1JPanel.remove( turnInCardsJButton );

			// Award appropriate # of armies according to:
			//	a) Number of territories player occupies
			//	b) Sum of the values of all (if any) continents player occupies
			//	
			//	Note: # of armies awarded to this player and to be distributed
			//		at this time is also determined by the turning in of cards 
			//		that occured in the stage just prior to this

				model.calculateArmies( model.getPlayer( model.getTurn() ) );

			// Create and populate comboBox with list of current player's territories
				ArrayList<Country> list = model.getPlayer( model.getTurn() ).getListCountries();	
				String[] listStrings = new String[ list.size() ];
				for( int i = 0 ; i < list.size() ; i++ )
						{
							listStrings[ i ] = list.get( i ).getName();
						}
				reinJComboBox1 = new JComboBox<>( listStrings );

				String label3Text = "You have to place " + model.getPlayer( model.getTurn() ).getNumArmies() + " armies";
				reinJLabel3 = new JLabel( label3Text );

				generalJPanel.add( reinJLabel1 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( reinJLabel3 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( reinJLabel2 );
				generalJPanel.add( reinJComboBox1 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( reinJButton1 );


				revalidate();
				repaint();

			}
			
			else if(  screenOneButton.getText() == "Place Reinforcements" || screenOneButton.getText() == "attack" )
			{
				
				model.setStageOfTurn( 2 );
				
			// Distribute reinforcements to the selected territory
			// Fires a popup notification
				if( screenOneButton.getText() == "Place Reinforcements" )
				{
					String territory = (String)reinJComboBox1.getSelectedItem();
					Country terr = model.getBoard().getCountryByName( territory );
					int reinforceCount = model.getPlayer( model.getTurn() ).getNumArmies();
					model.reinforce( terr, reinforceCount, model.getPlayer( model.getTurn() ) );	
					JOptionPane.showMessageDialog( null, reinforceCount + " reinforcement armies added to " + territory , "Reinforcements Added" , JOptionPane.PLAIN_MESSAGE );
					
			// If coming from reinforcements stage, remove reinforcements GUI elements from layout
					generalJPanel.remove( reinJLabel1 );
					generalJPanel.remove( blankJLabel );
					generalJPanel.remove( reinJLabel2 );
					generalJPanel.remove( reinJComboBox1 );
					generalJPanel.remove( blankJLabel );
					generalJPanel.remove( reinJLabel3 );
					generalJPanel.remove( blankJLabel );
					generalJPanel.remove( blankJLabel );
					generalJPanel.remove( reinJButton1 );
				}
				
				model.setLoaded( false );

				generalJPanel.add( attackJLabel1 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( attackJLabel2 );






				ArrayList<Country> list = model.getPlayer( model.getTurn() ).getListCountries();	
				Iterator<Country> it = list.iterator();
				while( it.hasNext() )
				{
					Country test = it.next();
					
				 // If country has less than 2 armies, player cannot attack from
					if( test.getNumArmies() < 2 )
					{
						it.remove();
					}
				
				// If country has no adjacent enemy territories, player cannot attack from this territory	
					else
					{
						List<Country> adjacents = test.getAdjacencies();
	
						int numAdjacentEnemies = adjacents.size();
						for( Country adj: adjacents )
						{
							if( model.getPlayer( model.getTurn() ).hasCountry( adj ) )
							{
								numAdjacentEnemies--;
							}
						}
						if( numAdjacentEnemies == 0 )
						{
							it.remove();
						}
					}


				}	
				String[] listStrings = new String[ list.size() ];
				for( int i = 0 ; i < list.size() ; i++ )
				{
					listStrings[ i ] = list.get( i ).getName();				
				}

				attackJComboBox1 = new JComboBox<>( listStrings );
				attackJComboBox1.addActionListener( handler2 );
				generalJPanel.add( attackJComboBox1 );



				subMenu1JPanel.add( attackJButton2 );




				String atkSelect = (String) attackJComboBox1.getSelectedItem();

			// populate attackJComboBox2 (# of armies to attack with)
				attackJComboBox2.removeAllItems();		

				int armies = model.getBoard().getCountryByName( atkSelect ).getNumArmies();	
				if( armies > 3 )
				{
					armies = 4;
				}
				for( int i = 1 ; i < armies ; i++ )
				{
					attackJComboBox2.addItem( i );
				}

			// populate attackJComboBox3 (which country to attack)
				attackJComboBox3.removeAllItems();
			
				List<Country> adjacents = model.getBoard().getCountryByName( atkSelect ).getAdjacencies();
	
				for( Country adj: adjacents )
				{
					if( !model.getPlayer( model.getTurn() ).hasCountry( adj ) )
					{
						attackJComboBox3.addItem( adj.getName() );
					}
				}

				generalJPanel.add( blankJLabel );
				generalJPanel.add( attackJLabel3 );
				generalJPanel.add( attackJComboBox2 ); // # of armies to attack with
				generalJPanel.add( blankJLabel );
				generalJPanel.add( attackJLabel4 );    // 'Attack whom?'
				generalJPanel.add( attackJComboBox3 ); // which country to attack
				generalJPanel.add( blankJLabel );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( attackJButton1 );


	
				revalidate();
				repaint();
				
			}
			
			else if( screenOneButton.getText() == "Commence Attack" )
			{
				String attackTerritory = (String)attackJComboBox1.getSelectedItem();
				Integer numberArmies = (Integer)attackJComboBox2.getSelectedItem();
				String defendTerritory = (String)attackJComboBox3.getSelectedItem();	
				int numArmies = model.getBoard().getCountryByName( defendTerritory ).getNumArmies();	
				
				Integer[] defendingCount;

				if( numArmies >= 2 )
				{
					numArmies = 2;
					defendingCount = new Integer[2];
				}

				else
				{
					defendingCount = new Integer[ numArmies ];
				}

				for( int i = 0 ; i < numArmies ; i++ )
				{
					defendingCount[i] = i + 1;
				}

				String[] options = {"Defend"};
				JPanel defendersPanel = new JPanel();
				defendersPanel.setLayout( new GridLayout( 3, 1 ) );
				JLabel text1;
				if( numberArmies == 1 )
				{
					text1 = new JLabel( attackTerritory + " attacks " + defendTerritory + " with " + numberArmies + " army" );
				}
				else
				{
					text1 = new JLabel( attackTerritory + " attacks " + defendTerritory + " with " + numberArmies + " armies" );
				}
				JLabel text2 = new JLabel( "How many armies would you like to defend with?" );
				JComboBox box = new JComboBox<>( defendingCount );
				defendersPanel.add( text1 );
				defendersPanel.add( text2 );
				defendersPanel.add( box );

			// Ask defending player for a choice on how many armies to defend with
				JOptionPane.showOptionDialog( null, defendersPanel, model.getBoard().getCountryByName( defendTerritory ).getOccupant().getName() + ", how many armies will defend?" , JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

				int defendChoice = (Integer) box.getSelectedItem();
				
				int[] results = model.attack( model.getBoard().getCountryByName( attackTerritory ), model.getBoard().getCountryByName( defendTerritory ), numberArmies, defendChoice );
			
				JLabel rLabel1 = new JLabel( "Defenders lose " + results[0] );
				JLabel rLabel2 = new JLabel( "Attackers lose " + results[1] );
				JPanel rPanel = new JPanel();
				rPanel.setLayout( new GridLayout( 2, 1 ) );
				rPanel.add( rLabel1 );
				rPanel.add( rLabel2 );
				String[] ok = {"OK"};

				JOptionPane.showOptionDialog( null, rPanel, "Result of attack: Armies lost", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, ok, ok[0] );

			// Check if defending territory army count has been reduced to 0
			// If so, attacking player has to move some quantity of armies into 
			// this newly acquired territory( minimal number moved is number 
			// player attacked with )

				if( model.getBoard().getCountryByName( defendTerritory ).getNumArmies() == 0 )
				{
					String[] conquer = {"Conquer!"};

					JLabel dText1 = new JLabel( "How many armies would you like" );
					JLabel dText2 = new JLabel( "to move into " + defendTerritory + "?" );
					
					Integer[] moveCounts = new Integer[ model.getBoard().getCountryByName( attackTerritory ).getNumArmies() - numberArmies ];
					for( int i = 0; i < moveCounts.length ; i++ )
					{
						moveCounts[ i ] = i + numberArmies;
					}

					JComboBox dBox = new JComboBox<>( moveCounts );
					JPanel dPanel = new JPanel();
					dPanel.setLayout( new GridLayout( 3, 1 ) );
					dPanel.add( dText1 );
					dPanel.add( dText2 );
					dPanel.add( dBox );

					JOptionPane.showOptionDialog( null, dPanel, model.getPlayer( model.getTurn() ).getName() + " conquers " + defendTerritory , JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, conquer, conquer[0]);
					int conquerChoice = (Integer) dBox.getSelectedItem();
					
					model.fortify( model.getBoard().getCountryByName( attackTerritory ), model.getBoard().getCountryByName( defendTerritory ), conquerChoice );	
				
				// Identify player who was just attacked
					Player defeatedPlayer = model.getBoard().getCountryByName( defendTerritory ).getOccupant();
				
				// Change ownership of conquered territory
					model.givePlayerCountry( model.getPlayer( model.getTurn() ), model.getBoard().getCountryByName( defendTerritory ) );

				// Take note that attacking player conquered a territory
				// This will be used at end of turn to determine if 
				// player should be awarded a card

					awardCard = true;

				// Check if player who just lost territory has also just lost their last territory
					if( defeatedPlayer.getCountriesCount() == 0 )
					{
	
					// Remove defeated player from turn cycle
					
						model.removePlayer( defeatedPlayer );
					
					// Reduce model's player count
					// This ensures that game loops from last player's turn to
					// first player's turn without breaking
						model.setPlayersCount( model.getPlayersCount() - 1 );
	
					// Check if attacking player is now the only player left in game
					// and therefore, the winner

						if( model.isDownToLastPlayer() )
						{
						
							System.out.println( "Game Over" );
							System.out.println( model.getPlayer( model.getTurn() ).getName() + " is victorious!!!" );

							System.exit( 0 );
						}
				
					// Popup message signifying that a player was defeated and that
					// all of the defeated player's cards are now in the 
					// possession of the conquering player
				
						String[] accept = {"Accept Cards"};

						JPanel aPanel = new JPanel();
						JLabel aText1 = new JLabel( defeatedPlayer.getName() + " no longer occupies any territories" );
						JLabel aText2 = new JLabel( "As a reward for defeating " + defeatedPlayer.getName() );
						JLabel aText3 = new JLabel( model.getPlayer( model.getTurn() ).getName() + " will receive any cards " + defeatedPlayer.getName() );
						JLabel aText4 = new JLabel( "had in their hand." );
						aPanel.setLayout( new GridLayout( 4, 1 ) );
						aPanel.add( aText1 );
						aPanel.add( aText2 );
						aPanel.add( aText3 );
						aPanel.add( aText4 );

						JOptionPane.showOptionDialog( null, aPanel, model.getPlayer( model.getTurn() ).getName() +
							" defeats " + defeatedPlayer.getName() , JOptionPane.NO_OPTION, 
							JOptionPane.PLAIN_MESSAGE, null, accept, accept[0]);
					

					// Give conquering player all of the defeated player's cards
					
						int defeatedHandSize = defeatedPlayer.getHandSize();
						Card[] cardsToTransfer = new Card[ defeatedHandSize ];
						for( int i = 0; i < defeatedHandSize; i++ )
						{
							cardsToTransfer[ i ] = defeatedPlayer.getCard( i );
						}

						for( int i = 0; i < cardsToTransfer.length; i++ )
						{
							model.getPlayer( model.getTurn() ).addRiskCard( cardsToTransfer[ i ] );
						}

					// Update the current players cards
						cardsJList.setListData( model.getPlayer( model.getTurn() ).getHandStrings() );


					// If the conquering player's hand now consists of 6 or more cards
					// player must turn in sets until hand is reduced below 5 cards
						if( model.getPlayer( model.getTurn() ).getHandSize() > 5 )
						{
						// Remove attack GUI elements
							generalJPanel.remove( attackJLabel1 );
							generalJPanel.remove( blankJLabel );
							generalJPanel.remove( attackJLabel2 );
							generalJPanel.remove( attackJComboBox1 );
							generalJPanel.remove( blankJLabel );
							generalJPanel.remove( attackJLabel3 );
							generalJPanel.remove( attackJComboBox2 );
							generalJPanel.remove( blankJLabel );
							generalJPanel.remove( attackJLabel4 );
							generalJPanel.remove( attackJComboBox3 );
							generalJPanel.remove( blankJLabel );
							generalJPanel.remove( blankJLabel );
							generalJPanel.remove( attackJButton1 );
							subMenu1JPanel.remove( attackJButton2 );

						// Add card turning in button to GUI

							subMenu1JPanel.add( turnInCardsJButton2 ); 
						
						// Show popup to explain that player needs to turn in cards to proceed
							JOptionPane.showMessageDialog( null, model.getPlayer( model.getTurn() ).getName() + " now has too many cards in their hand and must turn in sets of 3 until hand size is below 5 to proceed" , "Please Turn In Cards" , JOptionPane.PLAIN_MESSAGE );
						}

					}
				}
			
			// Reevaluate current player's territories' army counts
			// Repopulate attackJComboBox1
	
				ArrayList<Country> list = model.getPlayer( model.getTurn() ).getListCountries();	
				Iterator<Country> it = list.iterator();
				while( it.hasNext() )
				{
					Country test = it.next();
			
				// If country has less than 2 armies, player cannot attack from
					if( test.getNumArmies() < 2 )
					{
						it.remove();
					}
			
				// If country has no adjacent enemy territories, player cannot attack from this territory	
					else
					{
						List<Country> adjacents = test.getAdjacencies();
	
						int numAdjacentEnemies = adjacents.size();
						for( Country adj: adjacents )
						{
							if( model.getPlayer( model.getTurn() ).hasCountry( adj ) )
							{
								numAdjacentEnemies--;
							}
						}
						if( numAdjacentEnemies == 0 )
						{
							it.remove();
						}
					}		
				}

			// If player no longer has any territories to attack from,
			// popup to let user know and move to fortify stage
				if( list.size() == 0 )
				{
					JOptionPane.showMessageDialog( null, model.getPlayer( model.getTurn() ).getName() + " does not have any more territories that can attack this round" , "Attack Stage Complete" , JOptionPane.PLAIN_MESSAGE );
					JButton ghostButton = new JButton( "Finished attacking" );
					ghostButton.addActionListener( handler1 );
					ghostButton.doClick(); 
	
				}

				String[] listStrings = new String[ list.size() ];
				for( int i = 0 ; i < list.size() ; i++ )
				{
					listStrings[ i ] = list.get( i ).getName();				
				}

			// editing a JComboBox fires an actionEvent
			// temporarily unregister actionListener to avoid firing an actionEvent
				ActionListener[] aL = attackJComboBox1.getActionListeners();
				attackJComboBox1.removeActionListener( aL[0] );

				attackJComboBox1.removeAllItems();
				for( String territory : listStrings )
				{
					attackJComboBox1.addItem( territory );
				}	


			// Repopulate attackJComboBox2 (# of armies to attack with)
				attackJComboBox2.removeAllItems();		

				int armies = model.getBoard().getCountryByName( attackJComboBox1.getItemAt( 0 ) ).getNumArmies();	
				if( armies > 3 )
				{
					armies = 4;
				}
				for( int i = 1 ; i < armies ; i++ )
				{
					attackJComboBox2.addItem( i );
				}

			// Repopulate attackJComboBox3 (which country to attack)
				attackJComboBox3.removeAllItems();
			
				List<Country> adjacents = model.getBoard().getCountryByName( attackJComboBox1.getItemAt( 0 ) ).getAdjacencies();
		
				for( Country adj: adjacents )
				{
					if( !model.getPlayer( model.getTurn() ).hasCountry( adj ) )
					{
						attackJComboBox3.addItem( adj.getName() );
					}
				}

				attackJComboBox1.setSelectedItem( attackTerritory );
				if( model.getBoard().getCountryByName( attackTerritory ).getNumArmies() > numberArmies )
				{
					attackJComboBox2.setSelectedItem( numberArmies );
				}
				attackJComboBox3.setSelectedItem( defendTerritory );

			// Reregister actionListener
				attackJComboBox1.addActionListener( handler2 );

				revalidate();
				repaint();


			}
		
			else if( screenOneButton.getText() == "Submit Cards" )
			{
				ArrayList<String> valuesJList = (ArrayList<String>) cardsJList.getSelectedValuesList();

			// Get the elected cards' indices
				int[] indices = cardsJList.getSelectedIndices();

			// Award armies to player
				int awardedCount = model.turnInCards( indices, model.getPlayer( model.getTurn() ) );

				String[] cardsList = valuesJList.toArray( new String[ valuesJList.size() ] );
				String cardDialog = "";
				for(int i = 0 ; i < cardsList.length ; i++)
				{
					cardDialog += " " + cardsList[i];
				}
				cardDialog += " " + awardedCount + " armies awarded!";
				JOptionPane.showMessageDialog( null, "cards turned in = " + cardDialog, "Cards turned in", JOptionPane.PLAIN_MESSAGE );
			
			// Update list of players cards
				cardsJList.setListData( model.getPlayer( model.getTurn() ).getHandStrings() );

			// If Player now has less than 5 cards, allow attack stage to continue
				if( model.getPlayer( model.getTurn() ).getHandSize() < 5 )
				{	
				// Remove card turning in button to GUI
					subMenu1JPanel.remove( turnInCardsJButton2 );

				// Repopulate attackJComboBox( which countries player can attack from ) with accurate data
					ArrayList<Country> list = model.getPlayer( model.getTurn() ).getListCountries();	
					Iterator<Country> it = list.iterator();
					while( it.hasNext() )
					{
						Country test = it.next();
						
					// If country has less than 2 armies, player cannot attack from this territory
						if( test.getNumArmies() < 2 )
						{
							it.remove();
						}

					// If country has no adjacent enemy territories, player cannot attack from this territory	
						else
						{
							List<Country> adjacents = test.getAdjacencies();
	
							int numAdjacentEnemies = adjacents.size();
							for( Country adj: adjacents )
							{
								if( model.getPlayer( model.getTurn() ).hasCountry( adj ) )
								{
									numAdjacentEnemies--;
								}
							}
							if( numAdjacentEnemies == 0 )
							{
								it.remove();
							}

						}
					}	
					String[] listStrings = new String[ list.size() ];
					for( int i = 0 ; i < list.size() ; i++ )
					{
						listStrings[ i ] = list.get( i ).getName();				
					}
					attackJComboBox1 = new JComboBox<>( listStrings );
					attackJComboBox1.addActionListener( handler2 );
			
				// Redraw attack stage GUI elements
					generalJPanel.add( attackJLabel1 );
					generalJPanel.add( blankJLabel );
					generalJPanel.add( attackJLabel2 );
					generalJPanel.add( attackJComboBox1 );
					
				// populate attackJComboBox2 (# of armies to attack with)
					attackJComboBox2.removeAllItems();		

					int armies = model.getBoard().getCountryByName( (String) attackJComboBox1.getSelectedItem() ).getNumArmies();	
					if( armies > 3 )
					{
						armies = 4;
					}
					for( int i = 1 ; i < armies ; i++ )
					{
						attackJComboBox2.addItem( i );
					}

				// populate attackJComboBox3 (which country to attack)
					attackJComboBox3.removeAllItems();
				
					List<Country> adjacents = model.getBoard().getCountryByName( (String) attackJComboBox1.getSelectedItem() ).getAdjacencies();
	
					for( Country adj: adjacents )
					{
						if( !model.getPlayer( model.getTurn() ).hasCountry( adj ) )
						{
							attackJComboBox3.addItem( adj.getName() );
						}
					}
	
					generalJPanel.add( blankJLabel );
					generalJPanel.add( attackJLabel3 );
					generalJPanel.add( attackJComboBox2 ); // # of armies to attack with
					generalJPanel.add( blankJLabel );
					generalJPanel.add( attackJLabel4 );    // 'Attack whom?'
					generalJPanel.add( attackJComboBox3 ); // which country to attack
					generalJPanel.add( blankJLabel );
					generalJPanel.add( blankJLabel );
					generalJPanel.add( attackJButton1 );


					subMenu1JPanel.add( attackJButton2 );

					revalidate();
					repaint();
				}

			}


			else if( screenOneButton.getText() == "Finished attacking" )
			{
				// For saving/loading purposes
				model.setStageOfTurn( 3 );
				
				generalJPanel.remove( attackJLabel1 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( attackJLabel2 );
				generalJPanel.remove( attackJComboBox1 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( attackJLabel3 );
				generalJPanel.remove( attackJComboBox2 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( attackJLabel4 );
				generalJPanel.remove( attackJComboBox3 );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( blankJLabel );
				generalJPanel.remove( attackJButton1 );

				subMenu1JPanel.remove( attackJButton2 );

				// Build String data for fortifyJComboBox1 (which territories this player owns that have more than one army?)
				ArrayList<Country> myTerritoryList = model.getPlayer( model.getTurn() ).getListCountries();	
				Iterator<Country> it = myTerritoryList.iterator();
				while( it.hasNext() )
				{
					Country test = it.next();
				
				// If country has less than 2 armies, player cannot fortify from
					if( test.getNumArmies() < 2 )
					{
						it.remove();
					}

				}	

				String[] listStrings = new String[ myTerritoryList.size() ];
				for( int i = 0 ; i < myTerritoryList.size() ; i++ )
				{
					listStrings[ i ] = myTerritoryList.get( i ).getName();				
				}
				
				// editing a JComboBox fires an actionEvent
				// temporarily unregister actionListener to avoid firing an actionEvent
				ActionListener[] aL = fortifyJComboBox1.getActionListeners();
				fortifyJComboBox1.removeActionListener( aL[0] );

				fortifyJComboBox1.removeAllItems();

				for( String territory: listStrings )
				{
					fortifyJComboBox1.addItem( territory );
				}
				
				// Reregister actionListener
				fortifyJComboBox1.addActionListener( handler3 );

				generalJPanel.add( fortifyJLabel1 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( fortifyJLabel2 );
				generalJPanel.add( fortifyJComboBox1 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( fortifyJLabel3 );
				generalJPanel.add( fortifyJComboBox2 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( fortifyJLabel4 );
				generalJPanel.add( fortifyJComboBox3 );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( blankJLabel );
				generalJPanel.add( fortifyJButton1 );

				subMenu1JPanel.add( fortifyJButton2 );

				goThrough = 1;  //Flag so that actionListeners aren't registered more than once			
	
				revalidate();
				repaint();

			}

		}

	}

}
