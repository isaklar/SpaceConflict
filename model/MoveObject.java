package model;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.Math.atan;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

//Constants imported from 
import static model.ModelConsts.MAX_SPEED;
/**
 * All game objects that can move are move objects.
 * Extends physical object with speed and course.
 * 
 * @author Magnus Larsson
 * @version 2017-02-11
 */
public abstract class MoveObject extends PhysObject
{
    private float speed;
    private float course;
    /**
     * Constructor for MoveObjects. Passes the desired
     * x position, y position and Geometry to PhysObject.
     * Initializes the fields speed and course.
     * 
     * @param xPos      the x position of the move object
     * @param yPos      the y position of the move object
     * @param geometry  the geometry of the move object
     * @param speed     the speed of the move object
     * @param course    the course of the move object
     */
    public MoveObject(float xPos, float yPos, Geometry geometry, float speed, float course)
    {
        super(xPos, yPos, geometry); 
        this.speed = speed;
        this.course = course;
    }
    /**
     * Returns the speed of the move object.
     * 
     * @return  the speed of the move object
     */
    public float getSpeed(){
        return speed;
    }
    /**
     * Returns the course of the move object.
     * 
     * @return  the course of the move object
     */
    public float getCourse(){
        return course;
    }
    /**
     * Sets the course of the move object
     * 
     * @param course    the course to set
     */
    public void setCourse(float course){
        this.course = course;
    }
    /**
     * Sets the speed of the move object
     * 
     * @param speed     the speed to set
     */
    public void setSpeed(float speed){
        this.speed = speed;
    }
    /**
     * Calculates and returns the course of the MoveObject.
     * 
     * @param xSpeed    the speed along the x axis
     * @param ySpeed    the speed along the y axis
     * @return      the course in radians
     */
    protected float calcCourse(float xSpeed, float ySpeed)
    {
        if(xSpeed > 0)
        {
            return (float)atan(ySpeed/xSpeed);
        }
        else if(xSpeed < 0)
        {
            return (float)(atan(ySpeed/xSpeed) + PI);
        }
        else
        {
            return (float)(ySpeed < 0 ? 3*(PI)/2 : (PI)/2);
        }
    }
    
    /**
     * Modifies the speed and course of the move object based
     * on its position relative the gravity exerting mass
     * and the magnitude of the mass.
     * 
     * @param xPos  the x position of the gravity exerting mass  
     * @param yPos  the y position of the gravity exerting mass  
     * @param celBodMass    the magnitude of the gravity mass
     */
    public void gravitate(float xPos, float yPos, float celBodMass)
    {
        float xDiff = xPos - getXpos();
        float yDiff = yPos - getYpos();

        //A Diff of 0 should not be able to happen, but to be thorough:
        if(xDiff + yDiff != 0)
        {

            float course = getCourse();

            float xSpeed = (float)(getSpeed() * cos(course));
            float ySpeed = (float)(getSpeed() * sin(course));

            xSpeed = (float)( xSpeed + celBodMass*xDiff/pow(pow(xDiff, 2)+pow(yDiff, 2), 1.5) );
            ySpeed = (float)( ySpeed + celBodMass*yDiff/pow(pow(xDiff, 2)+pow(yDiff, 2), 1.5) );

            course = calcCourse(xSpeed, ySpeed);
            float speed = (float)pow(pow(xSpeed, 2) + pow(ySpeed, 2), 0.5);

            setCourse( (float)(course < 0 ? course + 2*PI : course) );

            setSpeed(speed);
        }
    }
}
