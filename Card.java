// SlamaRisk v1.0
// Card class 
// code by Dean Slama Jr
// June 2014

import java.io.Serializable;

public final class Card implements Serializable
{

    private final String type;
    private final Country country;

    public Card( String type, Country country )
    {
	this.type = type;
	this.country = country;
    }

    public String getType()
    {
	return this.type;
    }

    public Country getCountry()
    {
	return this.country;
    }


}
