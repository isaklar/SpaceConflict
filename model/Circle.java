package model;

/**
 * Circle is a type of Geometry. Its size is determined by its radius.
 * 
 * @author Gabriel Lindeby
 * @version 2017-02-11
 */
public class Circle extends Geometry
{
    private float radius; 

    /**
     * Initializes the radius of the Circle object. 
     * 
     * @param radius    the radius of the circle
     * 
     */
    public Circle(float radius)
    {
        this.radius = radius;
    }
    /**
     * Accessor method for field radius.
     * 
     * @return  the radius of the circle
     */
    public float getRadius()
    {
        return radius;
    }
    /**
     * Sets the field radius to the desired radius.
     * 
     * @param radius    the radius of the circle
     */
    public void setRadius(float radius)
    {
        this.radius = radius;
    }
}
