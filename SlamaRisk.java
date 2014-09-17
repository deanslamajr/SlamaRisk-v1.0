// SlamaRisk v1.0
// Main Program class 
// code by Dean Slama Jr
// June 2014

import javax.swing.JFrame;

public class SlamaRisk
{
 	//testing purposes
	private static final String[] hand = { "Card1", "Card2", "Card3", "Card4" };

	public static void main( String[] args )
	{
		RiskModel model = new RiskModel( hand );
		RiskGui game = new RiskGui( model );
		model.addObserver( game );
		game.setResizable( false );
		game.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		game.setSize( 250, 125 );
		game.setVisible( true );
	}

}
