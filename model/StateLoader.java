package model;

import network.*;

import java.util.HashMap;
import java.util.LinkedList;

import java.net.InetAddress;

import java.util.Observer;
import java.util.Observable;
import java.util.Iterator;

import java.nio.ByteBuffer;

/**
 * Loads the state data in messages deliverd to its message box 
 * into the space. Used by the guest game.
 * 
 * @author Magnus Larsson 
 * @version 2017-03-03
 */
public class StateLoader implements Observer
{
    private static Space space;

    /**
     * Sets the space to which to load the game state gathered from the
     * recieved game state messages.
     * 
     * @param   the space to which load received game state data
     * 
     */
    public StateLoader(Space space)
    {
        this.space = space;
    }

    /**
     * Updates the Space based on data gathered from game state messages delivered
     * to its message box.
     * 
     * @param  messageList   A list of messages containing game state data
     * retrieved from its message box
     * 
     */
    public static synchronized void updateSpace(LinkedList<Message> messageList)
    {
        space.clearObjects();

        for(Message message : messageList)
        {
            ByteBuffer cont = messageList.remove().content;
            while(cont.hasRemaining())
            {
                switch(cont.getInt())
                {
                    case NetConsts.POBJ_SHIP:
                    space.addObject(new Spaceship(cont.getFloat(), cont.getFloat(),
                            cont.getFloat(), cont.getInt()) );
                    break;

                    case NetConsts.POBJ_WEAP:
                    space.addObject(new WeaponProj(cont.getFloat(), cont.getFloat()) );
                    break;

                    case NetConsts.POBJ_CBOD:
                    space.addObject(new CelestBody(cont.getFloat(), cont.getFloat()) );
                    break;

                    case NetConsts.POBJ_GRAV:
                    space.addObject(new Gravity(cont.getFloat(), cont.getFloat()) );
                    break;
                }
            }

        }
        space.updateReady();
    }

    @SuppressWarnings("unchecked")
    /**
     * Initiates the update of the game state when notified with a message 
     * by the observed message box.
     * 
     * @param obs   the observed, its message box
     * @param arg   the argument, the list of messages
     * 
     */
    public void update(Observable obs, Object arg)
    {
        if(obs instanceof MessBox && arg instanceof LinkedList)
        {
            updateSpace((LinkedList<Message>)arg);
        }
    }
}
