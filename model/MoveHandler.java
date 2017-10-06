package model;

import java.util.List;
import java.lang.Math;
import java.net.InetAddress;

/**
 * Performs the player actions: acceleration, rotation and shooting. 
 * Moves all move objects and decreases count down timers for reloads,
 * explosions and weapon projectile life spans.
 *
 * @author  Magnus Larsson
 * @version 2017-02-22
 */
public class MoveHandler
{
    private MoveHandler()
    {
    }

    /**
     * Performs the desired actions of each spaceships owner for respective owner's spaceship. 
     * 
     * @param space     the space in which the spaceships reside
     * @version 2017-02-2
     */
    public synchronized static void performActions(Space space)
    {        
        int numObjs = space.getNumOfObjects();

        for(int i = 0; i < numObjs; i++)
        {
            PhysObject pObj = space.getObject(i);
            if(pObj instanceof Spaceship && !((Spaceship)pObj).isExploding())
            {
                Spaceship ship = (Spaceship)pObj;
                Player owner = ship.getOwner();

                if(owner.getActionAcc())
                {
                    ship.accelerate();
                    owner.clearActionAcc();
                }
                if(owner.getActionRotRight())
                {
                    ship.rotRight();
                    owner.clearActionRotRight();
                }
                if(owner.getActionRotLeft())
                {
                    ship.rotLeft();
                    owner.clearActionRotLeft();
                }
                if(owner.getActionShoot())
                {
                    WeaponProj wProj = ship.shoot();
                    if(wProj != null)
                    {
                        space.addObject(wProj);
                    }
                    owner.clearActionShoot();
                }

            }
        }

    }

    /**
     * Updates the positions of the move objects based on their speed and course variables.
     * 
     * @param space     the space in which the move objects reside
     * @version 2017-02-22
     */
    public static synchronized void moveObjects(Space space)
    {
        int numOfObjects = space.getNumOfObjects();

        for(int i = 0; i < numOfObjects; i++)
        {
            PhysObject physObj = space.getObject(i);

            if(physObj instanceof MoveObject)
            {
                MoveObject moveObj = (MoveObject)physObj;
                float speed = moveObj.getSpeed();
                float course = moveObj.getCourse();

                float spaceXlen = space.getXlen();
                float spaceYlen = space.getYlen();

                moveObj.setXPos( ( (moveObj.getXpos() + speed * (float)Math.cos(course)) 
                        % spaceXlen + spaceXlen ) % spaceXlen );

                moveObj.setYPos( ( (moveObj.getYpos() + speed * (float)Math.sin(course)) 
                        % spaceYlen + spaceYlen ) % spaceYlen );

                if(physObj instanceof Spaceship)
                {
                    ( (Spaceship)physObj ).reload();
                    ( (Spaceship)physObj ).doIfExploding();
                }
                else if(physObj instanceof WeaponProj)
                {
                    ( (WeaponProj)physObj ).decTimeLeft();
                }    
            }
        }
    }
}
