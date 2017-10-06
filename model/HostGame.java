package model;

import network.MessBox;

import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import static java.lang.Math.PI;

//Constants imported from ModelConsts
import static model.ModelConsts.BOARD_WIDTH;
import static model.ModelConsts.BOARD_HEIGHT;
import static model.ModelConsts.GRAV_BASE_MASS;
import static model.ModelConsts.PERIOD;

/**
 * Runs the active host game loop. It initiates the following: 
 * the spawning of players with lives left, the execution of each player's desired
 * actions, the movement of all physical objects in the space, the collision handling, the
 * clearing of all destroyed physical objects and check if the game is over, which is when
 * there is only one player left, and if so saves winner information to a text file. Afterwards 
 * it informs the observer, State sender, that the game state is ready to be sent to the guests
 * and increases the tick count by one.
 * 
 * @author  Magnus Larsson 
 * @version 2017-03-03
 */
public class HostGame
{
    private Space space;
    private HashMap<InetAddress, Player> playerList;
    private CollisionHandler collisionHandler;
    private MessBox messageBox;
    private int tick;
    private boolean gameOver;
    Random randomizer;
    private static final Timer timer = new Timer();

    public HostGame()
    {
        space = new Space();
        playerList = new HashMap<>();
        messageBox = new MessBox(); 
        messageBox.addObserver( new InputLoader(playerList) );
        randomizer = new Random();
        tick = 0;
        gameOver = false;
    }

    /**
     * The active host game loop. It initiates all the events that need to occur every game loop. 
     * 
     * @version 2017-03-03
     */
    private class hostGameLoop extends TimerTask
    {
        public void run() 
        {
            spawnPlayers();
            checkGameOver();
            MoveHandler.performActions(space);
            MoveHandler.moveObjects(space);
            CollisionHandler.collisionCheck(space);
            space.clearDestroyedObjects();

            space.updateReady();
            tick++;
        }
    }

    /**
     * Registers an IP address for a player who is to participate in the game. The IP address identifies the player.
     * 
     * @param IPaddr    the players IP address
     * @version 2017-02-23
     */
    public void addPlayer(String IPaddr)
    {
        try
        {
            Player player = new Player();
            playerList.put(InetAddress.getByName(IPaddr), player);
        }
        catch(UnknownHostException e)
        {
            System.err.println("Could not get address for: " + IPaddr);
        }
    }

    /**
     * Sets up the space with the celestial body and the players spaceships.
     * 
     * @version 2017-02-23
     */
    public void addPlayer(InetAddress IPaddr)
    {
        Player player = new Player();
        playerList.put(IPaddr, player);
    }

    /**
     * Sets up the space with the celestial body and the players spaceships.
     * 
     * @version 2017-02-23
     */
    public void initSpace()
    {
        CelestBody cBod = new CelestBody(BOARD_WIDTH/2, BOARD_HEIGHT/2, GRAV_BASE_MASS);
        space.addObject(cBod);

        space.addObject(cBod.getGravity());
    }

    /**
     * Spawns all players with lives left. Defeated players are given the current tick to register.
     * 
     */
    private void spawnPlayers()
    {
        for(Player player : playerList.values())
        {
            if(!player.getAlive())
            {
                if(player.getLives() > 0)
                {
                    spawnShip(player);
                }
                else
                {
                    player.informDefeated(tick);
                }
            }
        }

    }

    /**
     * Spawns a spaceship for the given player at an empty area and sets the players alive flag.
     * 
     * @param player    the player to create a spaceship for
     * @version 2017-02-25
     */
    private void spawnShip(Player player)
    {
        boolean flag;
        Spaceship ship;

        do
        {
            flag = false;

            ship = new Spaceship((float)(randomizer.nextFloat()*BOARD_WIDTH),
                (float)(randomizer.nextFloat()*BOARD_HEIGHT), (float)(randomizer.nextFloat()*2*PI),
                player); 

            for(int i = 0; i < space.getNumOfObjects() && !flag; i++)
            {
                flag = CollisionHandler.intersect(ship, space.getObject(i));
            }
        }
        while(flag);

        space.addObject(ship);
        player.setAlive();
    }

    /**
     * Starts the host game loop with the default period
     * 
     * @version 2017-02-23
     */
    public void start()
    {
        timer.schedule(new hostGameLoop(), 0, PERIOD);
    }

    /**
     * Returns the message box in which incoming user input messages are placed.
     * 
     * @return      the message box  
     * @version 2017-02-23
     */
    public MessBox getMessageBox()
    {
        return messageBox;
    }

    /**
     * Returns the space, the playing field
     * 
     * @return      the space, the playing field    
     * @version 2017-02-23
     */
    public Space getSpace()
    {
        return space;
    }

    /**
     * Checks if the game is over, if there is only one player left.
     * 
     * @version 2017-03-03
     */
    private void checkGameOver()
    {
        if(!gameOver)
        {
            int numUndefeated = 0;
            InetAddress winnerIP = null;

            for(InetAddress playerIP : playerList.keySet())
            {
                if(playerList.get(playerIP).getTickOfDefeat() == -1)
                {
                    numUndefeated++;
                    winnerIP = playerIP;
                }
            }
            //System.out.println("Players left: " + numUndefeated);
            if(numUndefeated == 1 && winnerIP != null)
            {
                saveWinner(winnerIP.getHostAddress());
                gameOver = true;
            }
        }
    }

    /**
     * Saves the IP address of the winner to a textfile together with time when the game was won.
     * 
     * @param winnerIP  the IP address of the winner
     * @version 2017-03-03
     */
    private void saveWinner(String winnerIP)
    {
        try
        {
            FileWriter writer = new FileWriter("Winners.txt", true);
            writer.write("Winner of the game won " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
                + ": Player at " + winnerIP + "\r\n");
            writer.close();
        }
        catch(IOException e)
        {
            //Do nothing
        }
    }
}
