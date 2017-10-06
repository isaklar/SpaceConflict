package model;
/**
 * Rectangle is a type of Geometry. Its size is determined by its radius. 
 * This is not used in our game. The original idea was to
 * have rectangular spaceships. The idea was abandoned,
 * as circular collision detection is considerably
 * easier than rectangular.
 * 
 * @author ??
 * @version 2017-02-11
 */
public class Rectangle extends Geometry
{
    private float width;
    private float height;
    
    public Rectangle(float height, float width)
    {
        this.height = height;
        this.width = width;
    }
    
    public float getHeight()
    {
        return height;
    }
    
    public float getWidth()
    {
        return width;
    }
    
    public void setWidth(float width)
    {
        this.width = width;
    }
    
    public void setHeight(float height)
    {
        this.height = height;
    }
}
