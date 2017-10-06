package model;

//Constants imported from ModelConsts
import static model.ModelConsts.CBODY_RADIUS;

/**
 * The celestial body serves as an immovable obstacle on the playing field. It also
 * has a gravity object.
 * 
 * @author  Gabriel Lindeby
 * @version 2017-02-20
 */
public class CelestBody extends PhysObject
{
    private Gravity gravity;

    /**
     * Constructor used by the host game. Creates a celestial body with at a
     * a specific position, default radius and a certain mass, for gravity.
     * 
     * @param xPos          the x position of the celestial body
     * @param yPos          the y position of the celestial body
     * @param gravityMass   the gravity exerting mass of the celestial body
     * 
     * @version 2017-02-20
     */
    public CelestBody(float xPos, float yPos, float gravityMass){
        super(xPos, yPos, new Circle(CBODY_RADIUS));
        gravity = new Gravity(xPos, yPos, gravityMass);
    }

    /**
     * Simpler constructor used by the guest game. Creates a celestial body with at a
     * a specific position and default radius. Gravity is not required by the guest game.
     * 
     * @param xPos          the x position of the celestial body
     * @param yPos          the y position of the celestial body
     * @param gravityMass   the gravity exerting mass of the celestial body
     * 
     * @version 2017-02-20
     */
    public CelestBody(float xPos, float yPos)
    {
        super(xPos, yPos, new Circle(CBODY_RADIUS));
        gravity = null;
    }

    /**
     * Returns the gravity of the celestial body.
     * 
     * @return      the gravity celestial body
     * 
     * @version 2017-02-20
     */
    public Gravity getGravity(){
        return gravity;
    }

}
