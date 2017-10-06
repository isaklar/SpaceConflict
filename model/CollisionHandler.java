package model;

import java.util.List;
import static java.lang.Math.pow;

//Constants imported from ModelConsts
import static model.ModelConsts.BOARD_WIDTH;
import static model.ModelConsts.BOARD_HEIGHT;

/**
 * Detects collision between the physical objects in the space and executes
 * the consequences.
 *
 * @author  Isak Einler Larsson
 * @version 2017-02-25
 */
public class CollisionHandler
{

    private CollisionHandler()
    {
    }

    /**
    *Checks if any PhysObject in Space are intersecting with one and other,
    *if so execConsequences are preformed with the two objects that intersect.
    *
    *@param pObjI     object I of the pair of objects to check if intersect
    *@param pObjJ     object J of the pair of objects to check if intersect
    *@version 2017-02-25
    */

    public static synchronized void collisionCheck(Space space)
    {
        int listSize = space.getNumOfObjects();

        PhysObject objI;
        PhysObject objJ;

        for(int i = 0; i < listSize; i++){
            for(int j = i + 1; j < listSize; j++){
                if(intersect(objI = space.getObject(i), objJ = space.getObject(j)))
                {
                    execConsequences(objI, objJ);
                }
            }
        }
    }

    /**
     * Executes the consequences of a collision between two physical objects.
     *
     * @param pObjI     object I of the pair of objects to execute consequences for
     * @param pObjJ     object J of the pair of objects to execute consequences for
     * @version 2017-02-25
     */
    private static void execConsequences(PhysObject pObjI, PhysObject pObjJ)
    {
        if(pObjI instanceof Spaceship)
        {
            if(pObjJ instanceof Spaceship)
            {
                ((Spaceship)pObjI).explode();
                ((Spaceship)pObjJ).explode();
            }
            else if(pObjJ instanceof CelestBody)
            {
                ((Spaceship)pObjI).explode();
            }
            else if(pObjJ instanceof WeaponProj)
            {
                ((Spaceship)pObjI).explode();
                ((WeaponProj)pObjJ).setDestroyed();
            }
            else if(pObjJ instanceof Gravity)
            {
                Gravity grav = (Gravity)pObjJ;
                ((Spaceship)pObjI).gravitate(grav.getXpos(), grav.getYpos(), grav.getMass());
            }
        }
        else if(pObjI instanceof CelestBody)
        {
            if(pObjJ instanceof Spaceship)
            {
                ((Spaceship)pObjJ).explode();
            }
            else if(pObjJ instanceof WeaponProj)
            {
                ((WeaponProj)pObjJ).setDestroyed();
            }
        }
        else if(pObjI instanceof WeaponProj)
        {
            if(pObjJ instanceof Spaceship)
            {
                ((WeaponProj)pObjI).setDestroyed();
                ((Spaceship)pObjJ).explode();
            }
            else if(pObjJ instanceof CelestBody)
            {
                ((WeaponProj)pObjI).setDestroyed();
            }
            else if(pObjJ instanceof WeaponProj)
            {
                ((WeaponProj)pObjI).setDestroyed();
                ((WeaponProj)pObjJ).setDestroyed();
            }
            else if(pObjJ instanceof Gravity)
            {
                Gravity grav = (Gravity)pObjJ;
                ((WeaponProj)pObjI).gravitate(grav.getXpos(), grav.getYpos(), grav.getMass());
            }
        }
        else if(pObjI instanceof Gravity)
        {
            if(pObjJ instanceof Spaceship)
            {
                Gravity grav = (Gravity)pObjI;
                ((Spaceship)pObjJ).gravitate(grav.getXpos(), grav.getYpos(), grav.getMass());
            }
            else if(pObjJ instanceof WeaponProj)
            {
                Gravity grav = (Gravity)pObjI;
                ((WeaponProj)pObjJ).gravitate(grav.getXpos(), grav.getYpos(), grav.getMass());
            }
        }
    }

    /**
     * Detects collision between the physical objects. Runs the correct detector for each
     * Geometry type. There is only one geometry in this version however.
     *
     * @param obj1  object 1 in the pair of objects to detect collision for
     * @param obj2  object 2 in the pair of objects to detect collision for
     * @return  whether the objects have collided or not
     * @version 2017-02-25
     */
    public static boolean intersect(PhysObject obj1, PhysObject obj2)
    {
        if((obj1.getGeometry()) instanceof Circle && (obj2.getGeometry()) instanceof Circle)
        {
            return CirclesIntersect(obj1, obj2);
        }

        return false;
    }

    /**
     * Detects collision between the PhysObjects that both have Geometry type Circle.
     * Does 4 checks - 3 are needed for checking over edges.
     *
     * @param obj1  object 1 in the pair of objects to detect circle collision for
     * @param obj2  object 2 in the pair of objects to detect circle collision for
     * @version 2017-02-25
     */
    private static boolean CirclesIntersect(PhysObject obj1, PhysObject obj2)
    {
        float x1 = obj1.getXpos();
        float x2 = obj2.getXpos();
        float y1 = obj1.getYpos();
        float y2 = obj2.getYpos();

        //For testing over the edges
        float modX1 = x1 < x2 ? x1 + BOARD_WIDTH : x1;
        float modX2 = x2 <= x1 ? x2 + BOARD_WIDTH : x2;
        float modY1 = y1 < y2 ? y1 + BOARD_HEIGHT : y1;
        float modY2 = y2 <= y1 ? y2 + BOARD_HEIGHT : y2;

        float radiusSum = ((Circle)obj1.getGeometry()).getRadius() + ((Circle)obj2.getGeometry()).getRadius();

        //The latter 3 are over edge tests
        return (pow(x1 - x2, 2) + pow(y1 - y2, 2) <= pow(radiusSum, 2))
        || (pow(modX1 - modX2, 2) + pow(y1 - y2, 2) <= pow(radiusSum, 2))
        || (pow(x1 - x2, 2) + pow(modY1 - modY2, 2) <= pow(radiusSum, 2))
        || (pow(modX1 - modX2, 2) + pow(modY1 - modY2, 2) <= pow(radiusSum, 2));
    }
}
