package network;

import model.*;

import java.util.Observer;
import java.util.Observable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.net.SocketException;
import java.io.IOException;

//Constants imported from NetConsts
import static network.NetConsts.LISTEN_GUEST;
import static network.NetConsts.MULTICAST_ADDR;
import static network.NetConsts.MESS_STATE;
import static network.NetConsts.POBJ_SHIP;
import static network.NetConsts.POBJ_WEAP;
import static network.NetConsts.POBJ_CBOD;

/**
 * Multicasts a message with the state of the host game to the guests when notified
 * with a game state.
 *
 * @author Magnus Larsson
 * @version 2017-02-23
 */
public class StateSender implements Observer
{
    DatagramSocket dSocket;

    public StateSender()
    {
        try
        {
            dSocket = new DatagramSocket();
        }
        catch(SocketException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * Initiates sending of the observed space's object list after a finished update.
     *
     * @version 2017-02-21
     */
    public void update(Observable obs, Object arg)
    {
        if(obs instanceof Space && arg instanceof List)
        {
            sendState( (List<PhysObject>)arg );
        }
    }

    /**
     * Sends a network message marked as game state message as well as all
     * required data about all pyshical objects that it needs to
     * sends.
     *
     * @param objectList    the list of physical objects
     *  
     * @version 2017-02-21
     */
    public void sendState(List<PhysObject> objectList)
    {
        try
        {
            InetAddress addr = InetAddress.getByName(MULTICAST_ADDR);
            ByteBuffer messageBuff = ByteBuffer.allocate(4096);

            messageBuff.putInt(MESS_STATE);

            for(PhysObject pobj : objectList)
            {
                if(pobj instanceof Spaceship)
                {
                    Spaceship ship = (Spaceship)pobj;

                    messageBuff.putInt(POBJ_SHIP);
                    messageBuff.putFloat(ship.getXpos());
                    messageBuff.putFloat(ship.getYpos());;
                    messageBuff.putFloat(ship.getOrientation());
                    messageBuff.putInt(ship.getExplodeTimeLeft());
                }
                else if(pobj instanceof WeaponProj)
                {
                    WeaponProj wProj = (WeaponProj)pobj;

                    messageBuff.putInt(POBJ_WEAP);
                    messageBuff.putFloat(wProj.getXpos());
                    messageBuff.putFloat(wProj.getYpos());
                }
                else if(pobj instanceof CelestBody)
                {
                    CelestBody cBod = (CelestBody)pobj;

                    messageBuff.putInt(POBJ_CBOD);
                    messageBuff.putFloat(cBod.getXpos());
                    messageBuff.putFloat(cBod.getYpos());
                }
                //Not necessary to send Gravity
            }

            byte[] message = new byte[messageBuff.position()];
            messageBuff.rewind();
            messageBuff.get(message);

            dSocket.send( new DatagramPacket(message, 0, message.length, addr, LISTEN_GUEST) );
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
