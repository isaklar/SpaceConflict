package model;

import network.*;
import controller.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.net.InetAddress;
import java.util.Observer;
import java.util.Observable;
import java.util.Iterator;
import java.net.UnknownHostException;

//Constants imported from InputConsts
import static controller.InputConsts.COMM_ACC;
import static controller.InputConsts.COMM_LEFT;
import static controller.InputConsts.COMM_RIGHT;
import static controller.InputConsts.COMM_SHOOT;

/**
 * Loads the received input, or action, data in received messages into the respective player.
 * The loaded data determines which actions to exceute for the players' spaceships.
 * Used by the host game.
 *
 * @author  Magnus Larsson
 * @version 2017-02-25
 */
public class InputLoader implements Observer
{
    private static HashMap<InetAddress, Player> playerTable;

    /**
     * Sets the player table of the input loader to the given player table.
     * 
     * @param playerTable   the player table to set as the input loaders player table
     * 
     */
    public InputLoader(HashMap<InetAddress, Player> playerTable)
    {
        this.playerTable = playerTable;
    }

    /**
     * Updates the players' action flags based on data gathered from input messages
     * delivered to its message box by the message receiver.
     * 
     * @param messageList   the list of messages containing the players desired actions  
     * 
     */
    public synchronized static void updatePlayerActions(LinkedList<Message> messageList)
    {

        for(Message message : messageList)
        {
            messageList.remove();
            int command = message.content.getInt();

            if(playerTable.containsKey(message.sender))
            {
                switch(command)
                {
                    case COMM_ACC:
                    playerTable.get(message.sender).setActionAcc();
                    break;

                    case COMM_LEFT:
                    playerTable.get(message.sender).setActionRotLeft();
                    break;

                    case COMM_RIGHT:
                    playerTable.get(message.sender).setActionRotRight();
                    break;

                    case COMM_SHOOT:
                    playerTable.get(message.sender).setActionShoot();
                    break;
                }
            }

        }
    }

    @SuppressWarnings("unchecked")
    /**
     * Initiates the update of the player action flags when notified by the observed
     * message box.
     * 
     * @param obs   the observed, its message box
     * @param arg   the argument, the list of messages
     * 
     */
    public void update(Observable obs, Object arg)
    {
        if(obs instanceof MessBox && arg instanceof LinkedList)
        {
            updatePlayerActions((LinkedList<Message>)arg);
        }
    }
}

