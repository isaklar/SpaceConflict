package model;

import java.util.Observable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Iterator;

//Constants imported from ModelConsts
import static model.ModelConsts.BOARD_WIDTH;
import static model.ModelConsts.BOARD_HEIGHT;

@SuppressWarnings("unchecked")
/**
 * Space represents the playing field of the game. Space maintains a list of physical objects:
 * the celestial body and its gravity, weapon projectiles and the players spaceships. It's
 * observed by the state sender, that sends the game state to the guests, at the host and by
 * the GUI at the guests.
 *
 * @author  Gabriel Lindeby
 * @version 2017-02-25
 */
public class Space extends Observable
{
    private int xLen;
    private int yLen;

    private ArrayList<PhysObject> objectList;

    public Space()
    {
        objectList = new ArrayList<>();
        xLen = BOARD_WIDTH;
        yLen = BOARD_HEIGHT;
    }

    /**
     * Adds an object to the space's list.
     *
     * @param object    the object to be added to the list
     *
     * @version 2017-02-25
     */
    public synchronized void addObject(PhysObject object)
    {
        objectList.add(object);
    }

    public PhysObject getObject(int index)
    {
        return objectList.get(index);
    }

    /**
     * Returns the number of physical objects space has in its list
     *
     * @return      the number of physical object's in space's list
     *
     * @version 2017-02-25
     */
    public int getNumOfObjects()
    {
        return objectList.size();
    }

    /**
     * Clears all objects from the space's list. Is used at the guest before adding an
     * updated state of the game.
     *
     * @version 2017-02-25
     */
    public synchronized void clearObjects()
    {
        objectList.clear();
    }

    /**
     * Returns the x-axis length, or width, of the space.
     *
     * @return      the width of the space
     *
     * @version 2017-02-25
     */
    public int getXlen()
    {
        return xLen;
    }

    /**
     * Returns the y-axis length, or height, of the space.
     *
     * @return      the height of the space
     *
     * @version 2017-02-25
     */
    public int getYlen()
    {
        return yLen;
    }

    /**
     * Returns a read only clone of the list. It needs to be a clone so
     * as to avoid concurrency problems. A shallow copy is enough for this purpose.
     *
     * @return      the height of the space
     *
     * @version 2017-02-25
     */
    public final List<PhysObject> getObjectList()
    {
        //Need a clone to avoid concurrency errors. A shallow copy is enough.
        return Collections.unmodifiableList((ArrayList<PhysObject>)objectList.clone());
    }

    /**
     * Notifies the observers that an update is ready. On the host side the observer is the
     * state sender and on the guest side it's the GUI.
     *
     * @version 2017-02-25
     */
    public void updateReady(){
        setChanged();
        notifyObservers(getObjectList());
    }

    /**
     * Removes all destroyed physical objects from the space's list. Spaceships and weapon
     * projectiles can get destroyed.
     * 
     * @version 2017-02-25
     */
    public void clearDestroyedObjects()
    {
        Iterator<PhysObject> objIt = objectList.iterator();
        while (objIt.hasNext())
        {
            PhysObject pObj = objIt.next();

            if(pObj.isDestroyed())
            {
                objIt.remove();
            }
        }
    }
}
