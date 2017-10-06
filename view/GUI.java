package view;

import model.*;
import network.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Observer;
import java.util.Observable;

import java.util.ArrayList;

import java.util.List;
import java.util.LinkedList;
import java.nio.ByteBuffer;

import java.net.*;

@SuppressWarnings("unchecked")
/**
 * Creates menus to allow the user to interact with the
 * game as well as initiates the space board that draws
 * and displyas the space, the playing field, to
 * the player. 
 * Is observed by the game starter and, when in guest mode,
 * the space board.
 * Observes the space, the playing field, when in guest mode.
 * 
 * @author Gabriel Lindeby
 * @version 2017-03-03
 */
public class GUI extends Observable implements Observer{

    JFrame spaceFrame = new JFrame("Space Conflict");

    JPanel panelContainer = new JPanel();
    SpaceBoard spaceBoard = new SpaceBoard();
    JPanel startMenu = new JPanel();
    JPanel hostGame = new JPanel();
    JPanel joinGame = new JPanel();
    JPanel options = new JPanel();
    JPanel lobby = new JPanel();

    CardLayout cl = new CardLayout();

    DefaultListModel model;
    JList list;

    MessBox messageBox;

    public GUI(){

        panelContainer.setLayout(cl);

        startMenu();
        joinGame();
        hostGame();

        panelContainer.add(spaceBoard,"game");
        panelContainer.add(startMenu,"startMenu");
        panelContainer.add(joinGame,"joinGame");
        panelContainer.add(hostGame,"hostGame");
        panelContainer.add(options,"options");
        panelContainer.add(lobby,"lobby");
        cl.show(panelContainer,"startMenu");

        spaceFrame.add(panelContainer);
        spaceFrame.setResizable(false);
        spaceFrame.pack();
        spaceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        spaceFrame.setFocusable(true);
        spaceFrame.setLocationRelativeTo(null);
        spaceFrame.setVisible(true);

        messageBox = new MessBox();
        messageBox.addObserver(this);

    }

    /**
     * Creates the start menu, which gives the user to alternatives: Host and Join.
     * Host leads to the host menu and Join leads to the join menu. 
     * 
     * @author Gabriel Lindeby
     * @version 2017-03-03
     */
    private void startMenu(){
        startMenu.setLayout(new GridLayout(2,1,3,3));

        JButton btnHostMenu = new JButton("Host");
        JButton btnJoinMenu = new JButton("Join");

        btnHostMenu.addActionListener(e -> cl.show(panelContainer,"hostGame"));
        btnJoinMenu.addActionListener(e -> cl.show(panelContainer,"joinGame"));

        startMenu.add(btnHostMenu);
        startMenu.add(btnJoinMenu);
    }

    /**
     * Creates the join menu. In the host ip text field the user enters the
     * ip address of the device that is running the game in host mode. After
     * having entered the address the user joins the game by pressing Join, which
     * notifies the observers, in effect the game starter.
     * The user can also go back to the start menu by pressing Back.
     * 
     * @version 2017-03-03
     */
    private void joinGame(){
        joinGame.setLayout(new GridLayout(6,1,3,3));

        JPanel fillUpp = new JPanel();
        JPanel textHost = new JPanel();
        JPanel buttons = new JPanel();

        JTextArea explainer = new JTextArea("host ip");
        JTextField hostAddress = new JTextField(20);
        JButton btnJoin = new JButton("Join");
        JButton btnBack = new JButton("Back");

        btnJoin.addActionListener(e -> {
                if(!hostAddress.getText().equals("")){
                    startGuestMode(hostAddress.getText());
                    cl.show(panelContainer, "game");
                }
            });

        btnBack.addActionListener(e -> cl.show(panelContainer,"startMenu"));

        joinGame.add(fillUpp);
        joinGame.add(textHost);
        textHost.add(explainer);
        textHost.add(hostAddress);
        joinGame.add(buttons);
        buttons.add(btnJoin);
        buttons.add(btnBack);

    }

    /**
     * Creates the host menu. In the host ip text field the user enters their
     * own ip address if they wish to partake in their own game. In the player  
     * ip address text fields the user enters the ip addresses of all players
     * that are to participate. After having entered all addresses the user 
     * initiates the games pressing Start, which notifies the observers, 
     * in effect the game starter.
     * The user can also go back to the start menu by pressing Back.
     * 
     * @version 2017-03-03
     */
    private void hostGame(){
        hostGame.setLayout(new GridLayout(6,1,3,3));

        JPanel host = new JPanel();
        JPanel player1 = new JPanel();
        JPanel player2 = new JPanel();
        JPanel player3 = new JPanel();
        JPanel player4 = new JPanel();
        JPanel buttons = new JPanel();

        JTextField hostText = new JTextField(16);
        JTextField player1Text = new JTextField(16);
        JTextField player2Text = new JTextField(16);
        JTextField player3Text = new JTextField(16);
        JTextField player4Text = new JTextField(16);

        JTextArea hostExplainer = new JTextArea("Host ip");
        JTextArea player1Explainer = new JTextArea("Player1 ip");
        JTextArea player2Explainer = new JTextArea("Player2 ip");
        JTextArea player3Explainer = new JTextArea("Player3 ip");
        JTextArea player4Explainer = new JTextArea("Player4 ip");

        JButton btnStart = new JButton("Start");
        JButton btnBack = new JButton("Back");

        btnStart.addActionListener(e -> {
                ArrayList<String> list = new ArrayList<String>();
                if(!hostText.getText().equals("")){
                    list.add(hostText.getText());
                }
                if(!player1Text.getText().equals("")){
                    list.add(player1Text.getText());
                }
                if(!player2Text.getText().equals("")){
                    list.add(player2Text.getText());
                }
                if(!player3Text.getText().equals("")){
                    list.add(player3Text.getText());
                }
                if(!player4Text.getText().equals("")){
                    list.add(player4Text.getText());
                }

                while(list.size() < 2)
                {
                    list.add("127.0.0.1");
                }

                startHostMode(list);
                cl.show(panelContainer,"game");
            });

        btnBack.addActionListener(e -> cl.show(panelContainer,"startMenu"));

        host.add(hostExplainer);
        host.add(hostText);
        player1.add(player1Explainer);
        player1.add(player1Text);
        player2.add(player2Explainer);
        player2.add(player2Text);
        player3.add(player3Explainer);
        player3.add(player3Text);
        player4.add(player4Explainer);
        player4.add(player4Text);
        buttons.add(btnStart);
        buttons.add(btnBack);

        hostGame.add(host);
        hostGame.add(player1);
        hostGame.add(player2);
        hostGame.add(player3);
        hostGame.add(player4);
        hostGame.add(buttons);
    }

    /**
     * Notifies the observers, in effect the game starter, that the user desires to
     * initiate a game in host mode. Sends the list of addresses.
     * 
     * @version 2017-03-03
     */
    private void startHostMode(ArrayList<String> list){
        setChanged();
        notifyObservers(list);
    }

    /**
     * Notifies the observers, in effect the game starter, that the user desires to
     * initiate a game in guest mode. Sends the address to connect to.
     * 
     * @version 2017-03-03
     */
    private void startGuestMode(String ip){
        setChanged();
        notifyObservers(ip);
    }

    /**
     * Initiates a redrawing of the space board, that displays the space,
     * the playing field, to the player, when notified by the observed,
     * in effect the space.
     * 
     * @version 2017-03-03
     */
    public void update(Observable o, Object arg){
        if( o instanceof Space && arg instanceof List){
            spaceBoard.updateView((List<PhysObject>)arg);
        }
    }

    /**
     * Returns the frame of the GUI
     * 
     * @return  the frame of the GUI
     * @version 2017-03-03
     */
    public JFrame getSpaceFrame(){
        return spaceFrame;
    }
}
