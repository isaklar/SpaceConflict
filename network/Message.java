package network;

import java.net.InetAddress;
import java.nio.ByteBuffer;

/**
 * A simple message class for holding received network messages.
 * 
 * @author Magnus Larsson
 * @version 2017-02-15
 * 
 */
public class Message
{
    public final InetAddress sender;
    public final ByteBuffer content;

    /**
     * Sets the sender IP address and message content
     * 
     * @param address   the IP address of the sender
     * @param content   the content of the message
     * 
     */
    public Message(InetAddress address, ByteBuffer content)
    {
        this.sender = address;
        this.content = content;
    }
}
