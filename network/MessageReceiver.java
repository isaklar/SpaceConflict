package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.LinkedList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.io.IOException;

import static network.NetConsts.MESS_ACTION;
import static network.NetConsts.LISTEN_HOST;
import static network.NetConsts.MESS_STATE;
import static network.NetConsts.LISTEN_GUEST;
import static network.NetConsts.MULTICAST_ADDR;

/**
 * Listens for, receives, formats and places network messages in message boxes
 * for the appropriate recipients.
 *
 * @author Magnus Larsson
 * @version 2017-02-25
 */
public class MessageReceiver
{
    private static final int BUFF_SIZE = 4096;
    private MessBox messBox;

    /**
     * Sets the message box for the message receiver.
     *
     * @param messBox   the message box to set
     *
     * @version 2017-02-23
     *
     */
    public MessageReceiver(MessBox messBox)
    {
        this.messBox = messBox;
    }

    /**
     * Listens for unicast messages, in effect input messages, and places them as Messages in
     * the message box.
     *
     * @version 2017-02-20
     *
     */
    public void listen()
    {
        DatagramSocket dSocket;
        DatagramPacket dPacket;

        try
        {
            dPacket = new DatagramPacket(new byte[BUFF_SIZE], BUFF_SIZE);
            dSocket = new DatagramSocket(LISTEN_HOST);

            while(true)
            {
                dSocket.receive(dPacket);

                ByteBuffer message = ByteBuffer.wrap( dPacket.getData() );

                //This indicates the type of the message. It is not used in this version.
                message.getInt();

                messBox.add(new Message(dPacket.getAddress(), message));
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     *
     * Listens for multicast messages, in effect state messages, and places them as Messages in
     * the message box.
     *
     * @version 2017-02-20
     *
     */
    public void listenMulticast()
    {
        try
        {
            DatagramPacket dPacket = new DatagramPacket(new byte[BUFF_SIZE], BUFF_SIZE);
            MulticastSocket mSocket = new MulticastSocket(LISTEN_GUEST);
            mSocket.joinGroup(InetAddress.getByName(MULTICAST_ADDR));

            while(true)
            {
                mSocket.receive(dPacket);

                ByteBuffer message = ByteBuffer.wrap( Arrays.copyOf( dPacket.getData(), dPacket.getLength() ) );

                //This indicates the type of the message. It is not used in this version.
                message.getInt();

                messBox.add(new Message(dPacket.getAddress(), message));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
