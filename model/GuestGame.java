package model;

import network.*;

import java.util.Timer;
import java.util.TimerTask;

import java.util.HashMap;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Initiates the passive guest game that is run by the guest of the game. 
 * The state loader which it creates updates the space based on messages
 * received from the host.
 * 
 * @author  Magnus Larsson
 * @version 2017-03-02
 */
public class GuestGame
{
    private Space space;    
    private MessBox messageBox;

    public GuestGame()
    {
        space = new Space();
        messageBox = new MessBox(); 

        messageBox.addObserver( new StateLoader(space) );
    }

    /**
     * Returns its message box.
     * 
     * @return  the message box
     * @version 2017-02-21
     */
    public MessBox getMessageBox()
    {
        return messageBox;
    }

    /**
     * Returns its space.
     * 
     * @return  the space
     * @version 2017-02-21
     */
    public Space getSpace()
    {
        return space;
    }
}
