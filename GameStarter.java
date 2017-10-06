import model.*;
import view.*;
import network.*;
import controller.*;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Observer;
import java.util.Observable;
import java.util.Arrays;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.Timer;

@SuppressWarnings("unchecked")
/**
 * This is the games entry point. It starts the main menu and then the appropriate game mode,
 * host or guest, based on user selections.
 *
 * @author Gabriel Lindeby
 * @version 2017-03-03
 */
public class GameStarter implements Observer
{
    private GUI gui;
    public GameStarter()
    {
        gui = new GUI();
        gui.addObserver(this);
    }

    public static void main(String args[])
    {
        new GameStarter();
    }

    /**
     * Starts the host process of the game and sets it to listen for incomming player
     * input messages. For each given IP address a player is added to the game.
     *
     * @param   playerIPaddrList
     *
     * @version 2017-03-03
     */
    public static void createHost(List<String> playerIPaddrList)
    {
        HostGame game = new HostGame();

        if(playerIPaddrList == null)
        {
            try
            {
                game.addPlayer(InetAddress.getLocalHost());
            }
            catch(UnknownHostException e)
            {
                System.err.println("Could not get local host");
            }
        }
        else
        {
            try
            {
                for(String playerIPaddr : playerIPaddrList)
                {

                    game.addPlayer(InetAddress.getByName(playerIPaddr));
                }
            }
            catch(UnknownHostException e)
            {
                System.err.println("Could not get host");
            }
        }

        game.initSpace();

        game.getSpace().addObserver( new StateSender() );

        new Thread( () -> ( new MessageReceiver(game.getMessageBox()) ).listen() ).start();

        game.start();
    }

    /**
     * Starts the guest process of the game, sets it to listen for game state messages and to send
     * user keyboard input to the given IP address, which should be a multicast address
     * to which the guests should be listening.
     *
     * @version 2017-03-03
     */
    public static void createGuest(String hostIP, GUI gui)
    {
        try
        {
            GuestGame game = new GuestGame();

            game.getSpace().addObserver(gui);

            new Thread( () -> ( new MessageReceiver(game.getMessageBox()) ).listenMulticast() ).start();

            InputSender inputSender = hostIP == null ? new InputSender() : new InputSender(hostIP);

            new Thread( () -> (new InputHandler(gui.getSpaceFrame())).addObserver(inputSender) ).start();
        }
        catch(UnknownHostException e)
        {
            System.err.println("Could not get host");
        }
    }

    /**
     * Starts the appropriate host and guest processes based on whether the user has selected
     * to host or join a game.
     *
     * @version 2017-03-03
     */
    public void update(Observable o, Object arg){
        if(o instanceof GUI && arg instanceof List){
            List list = (List<String>)arg;
            createHost(list.subList(1,list.size()));
            createGuest((String)list.get(0), gui);
        }
        if(o instanceof GUI && arg instanceof String){
            createGuest((String)arg, gui);
        }
    }

}
