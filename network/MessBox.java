package network;

import java.util.Observable;

import java.util.LinkedList;

import java.nio.ByteBuffer;

/**
 * Serves as a message box and holds a list of messages. When a message has been
 * added to it, it notifies its observers.
 * 
 * @author Magnus Larsson
 * @version 2017-02-23
 */
public class MessBox extends Observable
{
    private LinkedList<Message> list;

    public MessBox()
    {
        list = new LinkedList<>();
    }

    /**
     * Adds a message to its list and notifies the observers with the list. 
     * 
     * @param message   the message to add to the list of the box
     */
    public synchronized void add(Message message)
    {
        list.add(message);

        setChanged();
        notifyObservers(list);
    }
}
