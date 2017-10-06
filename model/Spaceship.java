package model;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.pow;

//Constants imported from ModelConsts
import static model.ModelConsts.SHIP_RADIUS;
import static model.ModelConsts.ACCELERATION;
import static model.ModelConsts.MAX_SPEED;
import static model.ModelConsts.ORIENT_RATE;
import static model.ModelConsts.WPROJ_RADIUS;
import static model.ModelConsts.WPROJ_BASE_SPEED;
import static model.ModelConsts.RELOAD_TIME;
import static model.ModelConsts.EXPLODE_TIME;
/**
 * Contains the data and functionality of the spaceship. A spaceship is a move object
 * that also has an orientation as well as capability to rotate and accelerate, an owner,
 * capability to shoot and a requirement to reload afterwards and suffer the risk of
 * being destroyed which it is after having exploded.
 * 
 * @author  Gabriel Lindeby
 * @version 2017-03-03
 */
public class Spaceship extends MoveObject
{
    private float orientation;
    private Player owner;
    private int reloadingLeft;
    private int explodeTimeLeft;
    private boolean destroyed;
    /**
     * Constructor used by the host game. Creates a spaceship for a player with a position,
     * orientation, default radius and appropriate initial states.
     * 
     * @param       the x position of the spaceship
     * @param       the y position of the spaceship
     * @param       the orientation of the spaceship
     * @param       the player who owns of the spaceship
     */
    public Spaceship(float xPos, float yPos, float orientation, Player owner){
        super(xPos, yPos, new Circle(SHIP_RADIUS), 0, 0);
        this.owner = owner;
        this.orientation = orientation;
        reloadingLeft = 0;
        explodeTimeLeft = -1;
        destroyed = false;
    }

    /**
     * Simpler constructor used by the guest game for loading the game state. Owning
     * player is not required by the guest games; whether the spaceship is exploding or
     * not is however.
     * 
     * @param xPos  the current x position of the spaceship
     * @param YPos  the current y position of the spaceship
     * @param orientation  the orientation of the spaceship
     * @param int explodeTimeLeft  the time left before exploding
     */
    public Spaceship(float xPos, float yPos, float orientation, int explodeTimeLeft)
    {
        super(xPos, yPos, new Circle(SHIP_RADIUS), 0, 0);
        this.owner = null;
        this.orientation = orientation;
        reloadingLeft = 0;
        this.explodeTimeLeft = explodeTimeLeft;
        destroyed = false;
    }

    /**
     * Determines if the Spaceship is exploding
     *  
     * @return      true if the spaceship is exploding.
     *              false if it is not.
     */
    public boolean isExploding(){
        return explodeTimeLeft > 0 ? true : false;
    }

    /**
     * Accessor method for the field orientation 
     * 
     *  @return      orientation   
     * 
     */
    public float getOrientation(){
        return orientation;
    }

    /**
     * Accessor method for the Player field owner
     * 
     * @param owner  the owner of this ship 
     * 
     */
    public Player getOwner(){
        return owner;
    }

    /**
     * Sets the spaceship as destroyed.
     * 
     */
    public void setDestroyed()
    {
        destroyed = true;
    }

    /**
     * Return whether tha spaceship is destroyed.
     * 
     * @return  whether the spaceship is destroyed   
     * 
     */
    public boolean isDestroyed()
    {
        return destroyed;
    }

    /**
     * Sets the orientation of the spaceship.
     * 
     * @param orientation  the orientation of the spaceship to set
     * 
     */
    public void setOrientation(float orientation){
        this.orientation = orientation;
    }

    /**
     * Modifies the speed and course based on the current orientation
     * and the default acceleration.
     * The speed cannot be greater than the maximum speed.
     * 
     */
    public void accelerate()
    {
        float course = getCourse();

        float xSpeed = (float)(getSpeed() * cos(course));
        float ySpeed = (float)(getSpeed() * sin(course));

        xSpeed += (float)(ACCELERATION * cos(orientation));
        ySpeed += (float)(ACCELERATION * sin(orientation));

        float speed = (float)pow(pow(xSpeed, 2) + pow(ySpeed, 2), 0.5);

        course = calcCourse(xSpeed, ySpeed);

        setSpeed(speed > MAX_SPEED ? MAX_SPEED : speed);       
        setCourse( (float)(course < 0 ? course + 2*PI : course) );
    }

    /**
     * Applies move objects gravitate and prevents the speed from being greater than
     * the maximum speed.
     * 
     * @param xPos  the x position of the gravity mass
     * @param yPos  the y position of the gravity mass
     * @param celBodMass  the magnitude of the mass
     */
    public void gravitate(float xPos, float yPos, float celBodMass)
    {
        super.gravitate(xPos, yPos, celBodMass);
        float speed = getSpeed();
        setSpeed(speed > MAX_SPEED ? MAX_SPEED : speed);
    }

    /**
     * Rotates the spaceship left.
     * 
     */
    public void rotLeft()
    {
        orientation = (float)( (orientation - ORIENT_RATE) % (2*PI) );
    }

    /**
     * Rotates the spaceship right.
     * 
     */
    public void rotRight()
    {
        orientation = (float)( ((orientation + ORIENT_RATE) + (2*PI)) % (2*PI) );
    }

    /**
     * Shoots a projectile if the spaceship isn't reloading. A new weapon projectile is created
     * and iherits the course and speed of the spaceship. The inherited speed and course is then
     * modified by the spaceships orientation and the default weapon projectil speed.
     * 
     * @return  the weapon projectil or null if a new weapon projectil wasn't allowed
     *          due to reloading
     * 
     */
    public WeaponProj shoot()
    {
        if(reloadingLeft == 0)
        {
            float shipHeight = ((Circle)getGeometry()).getRadius()*2;

            //We don't want to create the shot inside of the spaceship       
            float xPos = (float)(getXpos() + (shipHeight/1.85 + WPROJ_RADIUS) * cos(orientation));
            float yPos = (float)(getYpos() + (shipHeight/1.85 + WPROJ_RADIUS) * sin(orientation));

            float course = getCourse();

            float xSpeed = (float)(getSpeed() * cos(course));
            float ySpeed = (float)(getSpeed() * sin(course));

            xSpeed += (float)(WPROJ_BASE_SPEED * cos(orientation));
            ySpeed += (float)(WPROJ_BASE_SPEED * sin(orientation)); 

            course = calcCourse(xSpeed, ySpeed);

            float speed = (float)pow(pow(xSpeed, 2) + pow(ySpeed, 2), 0.5);

            reloadingLeft = RELOAD_TIME;

            return new WeaponProj(xPos, yPos, speed, course);
        }
        return null;
    }

    /**
     * Decreases the reload time left by 1 if it isn't 0 already.
     *
     */
    public void reload()
    {
        reloadingLeft = (reloadingLeft == 0) ? 0 : reloadingLeft-1;
    }

    /**
     * Starts the explode time count down if the spaceship isn't exploding already.
     *
     */
    public void explode()
    {
        if(explodeTimeLeft < 0)
        {
            explodeTimeLeft = EXPLODE_TIME;
        }
    }

    /**
     * Decreases the explode time left by 1 if the spaceship is exploding.
     * Destroys the spaceship and flags the player as dead if the time left to explode
     * is 0.
     *
     */
    public void doIfExploding()
    {
        if(explodeTimeLeft != -1)
        {
            if(explodeTimeLeft == 0)
            {
                destroyed = true;
                owner.died();
            }
            else
            {
                explodeTimeLeft--;
            }
        }  
    }

    /**
     * Accessor method for the field explodeTimeLeft
     * 
     *   @return      explodeTimeLeft
     * 
     */
    public int getExplodeTimeLeft()
    {
        return explodeTimeLeft;
    }
}

