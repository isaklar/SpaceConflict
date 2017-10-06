package model;

import java.net.InetAddress;
import java.net.UnknownHostException;

//Constants imported from ModelConsts
import static model.ModelConsts.NUM_LIVES;

/**
 * Player identifies a player in the model with an IP address.
 * It keeps track of the actions that the player wants to perform and 
 * how many lives they have left and, if they don't have any left, when
 * their game was over.
 * 
 * @author  Eric Groseclos
 * @version 2017-03-02
 */
public class Player
{
    private boolean actionAcc;
    private boolean actionRotRight;
    private boolean actionRotLeft;
    private boolean actionShoot;

    private boolean alive;

    private int livesLeft = NUM_LIVES;
    private int tickOfDefeat;

    public Player()
    {
        alive = false;
        tickOfDefeat = -1;
        clearActions();
    }

    /**
     * Clears all player action flags that indicates what the player wants to do.
     * 
     * @version 2017-02-25
     */
    private void clearActions()
    {
        actionAcc = false;
        actionRotRight = false;
        actionRotLeft = false;
        actionShoot = false;
    }

    /**
     * Returns the number of lives that the player has left.
     * 
     * return   the number of lives that the player has left
     * 
     * @version 2017-02-25
     */
    public int getLives()
    {
        return livesLeft;
    }

    /**
     * Returns the state of the acceleration flag
     * 
     * return   the state of the acceleration flag
     * 
     * @version 2017-02-25
     */ 
    public boolean getActionAcc()
    {
        return actionAcc;
    }

    /**
     * Returns the state of the rotate right flag
     * 
     * return   the state of the rotate right flag
     * 
     * @version 2017-02-25
     */
    public boolean getActionRotRight()
    {
        return actionRotRight;
    }

    /**
     * Returns the state of the rotate left flag
     * 
     * return   the state of the rotate left flag
     * 
     * @version 2017-02-25
     */
    public boolean getActionRotLeft()
    {
        return actionRotLeft;
    }

    /**
     * Returns the state of the shoot flag
     * 
     * return   the state of the shoot flag
     *  
     * @version 2017-02-25
     */
    public boolean getActionShoot()
    {
        return actionShoot;
    }

    /**
     * Sets the shoot flag
     * 
     * @version 2017-02-25
     */
    public void setActionAcc()
    {
        actionAcc = true;
    }

    /**
     * Sets the rotate right flag
     * 
     * @version 2017-02-25
     */
    public void setActionRotRight()
    {
        actionRotRight = true;
    }

    /**
     * Sets the rotate left flag
     * 
     * @version 2017-02-25
     */
    public void setActionRotLeft()
    {
        actionRotLeft = true;
    }

    /**
     * Sets the shoot flag
     * 
     * @version 2017-02-25
     */
    public void setActionShoot()
    {
        actionShoot = true;
    }

    /**
     * Clears the accelerate flag
     * 
     * @version 2017-02-25
     */
    public void clearActionAcc()
    {
        actionAcc = false;
    }

    /**
     * Clears the rotate right flag
     * 
     * @version 2017-02-25
     */
    public void clearActionRotRight()
    {
        actionRotRight = false;
    }

    /**
     * Clears the rotate left flag
     *  
     * @version 2017-02-25
     */
    public void clearActionRotLeft()
    {
        actionRotLeft = false;
    }

    /**
     * Clears the shoot flag
     * 
     * @version 2017-02-25
     */
    public void clearActionShoot()
    {
        actionShoot = false;
    }

    /**
     * Subtracts a life from the player's number of lives left and clears the alive flag.
     * 
     * @version 2017-02-25
     */    
    public void died()
    {
        livesLeft--;
        alive = false;
    }

    /**
     * Sets the alive flag.
     * 
     * @version 2017-02-25
     */
    public void setAlive()
    {
        alive = true;
    }

    /**
     * Sets the alive flag.
     * 
     * @return  the state of the alive flag
     * 
     * @version 2017-02-25
     */
    public boolean getAlive()
    {
        return alive;
    }

    /**
     * Sets the tick of defeat if the tick hasn't been set already.
     * 
     * @param   the tick when the player lost their last life
     * 
     * @version 2017-02-25
     */
    public void informDefeated(int tickOfDefeat)
    {
        this.tickOfDefeat = this.tickOfDefeat == -1 ? tickOfDefeat : this.tickOfDefeat;
    }

    public int getTickOfDefeat()
    {
        return tickOfDefeat;
    }
}
