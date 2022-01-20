import java.io.Serializable;

/**
  * This class represents a location and is used for the location of an accommodation.
  * The fields are the country, the town, the street and the number of the street. 
  */
public class Location implements Serializable {
    private String country;
    private String town;
    private String street;
    private String streetNumber;
    
    /**
      * This is an empty constructor
      */
    public Location()
    {
    }
    
    /**
      * This is a constructor that sets the fields of the class. 
      * @param country the country that the accommodation is located
      * @param town is the town of the Location
      * @param street is the name of the street of the Location
      * @param streetNumber is the street's number of the Location
      */
    public Location(String country, String town, String street, String streetNumber)
    {
        this.country = country;
        this.town = town;
        this.street = street;
        this.streetNumber = streetNumber;

    }
    
    /**
      * Sets the town of the Location
      * @param town is the location's town
      */
    public void setTown(String town)
    {
        this.town = town;
    }
    
    /**
      * Sets the street of the Location
      * @param street is the location's street
      */
    public void setStreet(String street)
    {
        this.street = street;
    }
    
    /**
      * Sets the number of the street of the Location
      * @param streetNumber is the location's street number
      */
    public void setNumber(String streetNumber)
    {
        this.streetNumber = streetNumber;
    }

    /**
      * Sets the country of the Location. 
      * @param country is the location's country. 
      */
    public void setCountry(String country){
        this.country = country;
    }

    /**
      * This method returns the country of the location. 
      * @return the county of the location. 
      */
    public String getCountry()
    {
        return this.country;
    }

    /**
      *This a method that returns the town of the location. 
      * @return the town of the location. 
      */
    public String getTown()
    {
        return this.town;
    }

    /**
      * This method returns the Street of the location.
      * @return the street of the location
      */
    public String getStreet()
    {
        return this.street;
    }

    /**
      * This method returns the street number of the location. 
      * @return the street number of the location. 
      */
    public String getStreetNumber()
    {
        return this.streetNumber;
    }
}
