package model;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;

//Constants imported from
import static model.ModelConsts.WPROJ_RADIUS;
import static model.ModelConsts.WPROJ_TTL;
/**
 * A class for holding information
 * about weapon projectiles. The field
 * timeLeft holds information about
 * how many gameticks are left for
 * the projectile to exist.
 *
 * @author Eric Groseclos
 * @version 2017-03-03
 */
public class WeaponProj extends MoveObject
{

    private int timeLeft;

    /**
     * Constructor for the HostGame. Passes the parameters
     * to the superclass MoveObject. Sets timeLeft to
     * the constant imported from ModelConsts.
     *
     * @param   xPos    the current x position of the projectile
     * @param   yPos    the current y position of the projectile
     * @param   speed   the current speed of the projectile
     * @param   course  the current course of the projectile
     */
    public WeaponProj(float xPos, float yPos, float speed, float course){
        super(xPos, yPos, new Circle(WPROJ_RADIUS), speed, course);
        this.timeLeft = WPROJ_TTL;
    }
    /**
    * Constructor for the GuestGame loading the state.
    * Passes the parameters to the superclass MoveObject.
    * Sets timeLeft, speed and course to zero.
    *
    * @param    xPos    the current x position of the projectile
    * @param    yPos    the current y position of the projectile
    */
    public WeaponProj(float xPos, float yPos)
    {
        super(xPos, yPos, new Circle(WPROJ_RADIUS), 0, 0);
        this.timeLeft = 0;
    }
    /**
     * Accessor method for the field
     * timeLeft
     *
     * @ return timeLeft
     */
    public int getTimeLeft(){
        return timeLeft;
    }
    /**
     * Sets the weapon projectiles time left to 0, which means destroyed.
     */
    public void setDestroyed(){
        timeLeft = 0;
    }
    /**
     * Returns Whether the weapon projectile is destroyed or not.
     *
     * @ return     whether the weapon projectile is destroyed or not
     */
    public boolean isDestroyed()
    {
        return timeLeft == 0 ? true : false;
    }

    /**
     * Decrease time left for weapon projectile by 1 gametick.
     */
    public void decTimeLeft(){
        timeLeft--;
    }
}
