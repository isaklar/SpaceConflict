package model;

/**
 * All game objects are physical objects.
 * Holds information about x position, y position
 * and Geometry.
 *
 * @author Gabriel Lindeby
 * @version 2017-02-11
 *
 */
public abstract class PhysObject{
    
    private float xPos;
    private float yPos;
    private Geometry geometry;

    /**
     * Constructor for the PhysObject.
     *
     * @param xPos  The desired x position of the PhysObject
     * @param yPos  The desired y position of the PhysObject
     * @param geometry  The desired Geometry of the PhysObject
     */
    public PhysObject(float xPos, float yPos, Geometry geometry){
        this.xPos = xPos;
        this.yPos = yPos;
        this.geometry = geometry;
    }
    /**
     * Accessor method for field xPos
     *
     * @return      the current x position of the physical object
     */
    public float getXpos(){
        return xPos;
    }
    /**
     * Accessor method for field yPos
     * @return      the current y position of the physical object
     */
    public float getYpos(){
        return yPos;
    }
    /**
     * Accessor method for the field geometry
     *
     * @return  the geometry of the physical object
     */
    public Geometry getGeometry(){
        return geometry;
    }
    /**
     * Sets the field xPos equal to the
     * input parameter.
     *
     * @param xPos  The desired x position of the PhysObject
     */
    public void setXPos(float xPos){
        this.xPos = xPos;
    }
    /**
     * Sets the field yPos equal to the
     * input parameter.
     *
     * @param yPos  The desired y position of the PhysObject
     */
    public void setYPos(float yPos){
        this.yPos = yPos;
    }
    /**
     * Returns that the physical object hasn't been destroyed. Physical objects that
     * can be destroyed need to override this method.
     *
     * @return false
     */
    public boolean isDestroyed()
    {
        return false;
    }

}
