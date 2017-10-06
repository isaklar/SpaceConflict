package network;


/**
 * Input constants. Facilitates network communication.
 * 
 * @author  Magnus Larsson
 * @version 2017-02-25
 */
public final class NetConsts
{    
    //IP constants
    public static final int LISTEN_HOST = 1337;
    public static final int LISTEN_GUEST = 9001;
    public static final String MULTICAST_ADDR = "224.0.0.123"; 

    //Message type codes
    public static final int MESS_ACTION = 0;
    public static final int MESS_STATE = 1;    
    
    //Object type codes used in the messages
    public static final int POBJ_SHIP = 0;
    public static final int POBJ_WEAP = 1;
    public static final int POBJ_CBOD = 2;
    //Not required in this version
    public static final int POBJ_GRAV = 3;  
}
