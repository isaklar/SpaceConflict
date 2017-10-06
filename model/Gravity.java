package model;

//Constants imported from 
import static model.ModelConsts.GRAV_RADIUS;

/**
 * Represents the mass and gravity of objects for which this is relevant, which in
 * this version is only the celestial body.
 * 
 * @author  Gabriel Lindeby
 * @version 2017-03-02
 */

public class Gravity extends PhysObject
{
    private float gravityMass;
    
    /**
     * Constructor used by the HostGame. Passes the parameters
     * to the superclass PhysObject. Sets field gravityMass.
     * 
     * @param   xPos    the x position of the gravity
     * @param   yPos    the y position of the gravity
     * @param   yPos    mass of the gravity
     */
    public Gravity(float xPos, float yPos, float gravityMass){
        super(xPos, yPos, new Circle(GRAV_RADIUS));
        this.gravityMass = gravityMass;
    }

    /**
     * Simpler constructor used by the GuestGame. GravityMass is not required by the Guest.
     * 
     * @param   xPos    the x position of the gravity
     * @param   yPos    the y position of the gravity
     */   
    public Gravity(float xPos, float yPos)
    {
        super(xPos, yPos, new Circle(GRAV_RADIUS));
        this.gravityMass = 0;
    }
    
    /**
     * Accessor method for field gravityMass
     * 
     * @return gravityMass
     */
    public float getMass(){
        return gravityMass;
    }
    
    /**
     * Sets the gravityMass field equal
     * to the input parameter.
     * 
     * @param gravityMass
     */
    public void setMass(float gravityMass){
        this.gravityMass = gravityMass;
    }

}
