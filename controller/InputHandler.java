package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.HashSet;

//Constants imported from InputConsts
import static controller.InputConsts.COMM_ACC;
import static controller.InputConsts.COMM_LEFT;
import static controller.InputConsts.COMM_RIGHT;
import static controller.InputConsts.COMM_SHOOT;

/**
 * Handles all player keyboard input, which is required at the guest. 
 * Notifies the observers with the action that the player desires
 * to perform so that it can be sent over the network to the host.
 * 
 * @author  Isak Einler Larsson
 * @version 2017-02-25
 * 
 */
public class InputHandler extends Observable implements KeyListener
{
    private final HashSet<Integer> pressed = new HashSet<Integer>();

    /**
     * Handles all player keyboard input, which is required at the guest. 
     * Notifies the observers with the action that the player desires
     * to perform so that it can be sent over the network to the host.
     * 
     * @param spaceBoard    the playing field to listen for key input on
     * 
     */
    public InputHandler(JFrame spaceBoard)
    {
        spaceBoard.addKeyListener(this);
        new Timer(10, new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    //UP
                    if(pressed.contains(KeyEvent.VK_UP))
                    {
                        setChanged();
                        notifyObservers(COMM_ACC);
                    }
                    //LEFT
                    if(pressed.contains(KeyEvent.VK_LEFT))
                    {
                        setChanged();
                        notifyObservers(COMM_LEFT);
                    }
                    //RIGHT
                    if(pressed.contains(KeyEvent.VK_RIGHT))
                    {
                        setChanged();
                        notifyObservers(COMM_RIGHT);
                    }
                    //SHOOT
                    if(pressed.contains(KeyEvent.VK_SPACE))
                    {
                        setChanged();
                        notifyObservers(COMM_SHOOT);
                    }
                }
            }).start();
    }

    /**
     * Is not used but has to be overridden.
     * 
     * @param e    the key event
     * 
     */
    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    /**
     * The event fired when a key is pressed. Adds the key code to the set of pressed keys.
     * 
     * @param e    the key event
     * 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
    }

    /**
     * The event fired when a key is released. Removes the key code from the set of pressed keys.
     * 
     * @param e    the key event    
     * 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }
}
