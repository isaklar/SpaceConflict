package model;


/**
 * Constants used in model. They determine the size of the playing field as well
 * as various attributes of the objects that the playing field can contain. 
 * 
 * @author
 * @version 2017-03-02
 */
public final class ModelConsts
{
    //Space board dimensions
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 600;
    
    //Number of lives that the player has
    public static final int NUM_LIVES = 3;
    
    //Celestial body radius
    public static final int CBODY_RADIUS = 70;
    //Gravity radius
    public static final int GRAV_RADIUS = 300;
    //Base gravity mass
    public static final float GRAV_BASE_MASS = (float)250;
    
    //Length of the game loop in ms
    public static final int PERIOD = 10;
    
    //Weapon projectile constants
    public static final int WPROJ_TTL = 120;
    public static final float WPROJ_RADIUS = 3;
    public static final float WPROJ_BASE_SPEED = 4;
    
    //Spaceship dimensions
    public static final int SHIP_RADIUS = 10;
    //Spaceships explode for this long
    public static final int EXPLODE_TIME = 10;    
    //Spaceship reload time
    public static final int RELOAD_TIME = 50;
    //Spaceship action constants
    public static final float ACCELERATION = (float)0.03;
    public static final float ORIENT_RATE = (float)0.04;
    public static final float MAX_SPEED = (float)2;
    
    
}
