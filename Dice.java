// SlamaRisk v1.0
// Dice class 
// code by Dean Slama Jr
// June 2014

import java.util.Random;

public class Dice
{

    private Random die;

    public Dice()
    {
	die = new Random();
    }

    public int[] rollDice( int numberOfDice )
    {
	int[] rolledDice = new int[ numberOfDice ];
	for( int i = 0; i < numberOfDice; i++ )
	{
		rolledDice[ i ] = die.nextInt( 6 ) + 1;
		
	}
	return rolledDice;
    }

}
