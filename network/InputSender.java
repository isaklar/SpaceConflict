package network;

import controller.InputHandler;

import java.util.Observer;
import java.util.Observable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.io.IOException;

import static network.NetConsts.LISTEN_HOST;
import static network.NetConsts.MESS_ACTION;

/**
 * Sends a message with the action that corresponds to the player input to the host game
 * when notified.
 *
 * @author Magnus Larsson
 * @version 2017-02-23
 */
public class InputSender implements Observer
{
    DatagramSocket dSocket;
    private final InetAddress ipAddr;

    /**
     * Sets the IP address to send the input messages to.
     *
     * @param ipAddr    the address to be set
     *
     * @version 2017-02-23
     */
    public InputSender(String ipAddr) throws UnknownHostException
    {
        this.ipAddr = InetAddress.getByName(ipAddr);
        getSocket();
    }

    /**
     * Sets the IP address to send the input messages to local host.
     *
     * @version 2017-02-23
     */
    public InputSender() throws UnknownHostException
    {
        this.ipAddr = InetAddress.getLocalHost();
        getSocket();
    }
    /**
     * Creates a new datagram socket.
     *
     * @version 2017-02-23
     */
    private void getSocket()
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

    /**
     * Initiates sending when notified with an input, a player action,
     *
     * @param obs    the notifying observed object
     * @param arg    the argument of the notification, in effect the input, or action
     *
     * @version 2017-02-23
     */
    public void update(Observable obs, Object arg)
    {
        if(obs instanceof InputHandler && arg instanceof Integer)
        {
            sendAction((int)arg);
        }
    }

    /**
     * Sends a network message marked as an action message containing
     * the player's action, or input.
     *
     * @param action    the action, or input
     * 
     * @version 2017-02-21
     */
    public void sendAction(int action)
    {
        try
        {
            DatagramSocket dSocket = new DatagramSocket();

            ByteBuffer messageBuff = ByteBuffer.allocate(8);

            messageBuff.putInt(MESS_ACTION);
            messageBuff.putInt(action);

            byte[] message = messageBuff.array();

            dSocket.send( new DatagramPacket(message, 0, 8, ipAddr, LISTEN_HOST) );
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
