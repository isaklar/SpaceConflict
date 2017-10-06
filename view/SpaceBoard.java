package view;

import model.*;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Image;

import static java.lang.Math.PI;
import static java.lang.Math.round;

//Constants imported from ModelConsts
import static model.ModelConsts.BOARD_WIDTH;
import static model.ModelConsts.BOARD_HEIGHT;
import static model.ModelConsts.SHIP_RADIUS;
import static model.ModelConsts.CBODY_RADIUS;

/**
 * Paints the game objects in a JPanel. This is done 9 times over so as to achieve proper edge 
 * crossover in the 'torus universe'. In a 'torus universe' the space is 'wrapped around'. An object
 * crossing the edge appears on the opposite side of the field.
 * 
 * @author Eric Groseclos
 * @version 2017-02-28 
 * 
 */
public class SpaceBoard extends JPanel
{

    private boolean readyToPaint;

    private List<PhysObject> objectList;
    BufferedImage imgSaucer;
    BufferedImage imgBH;

    /**
     * Appropriate initializations are made when this constructor is called.
     * The dimension of the JPanel is set to 800 x 600 pixels by default.
     * setFocusable allows keyboard and mouse wheel events to be sent to the focus owner. 
     * The game board is set to be black via the setBackground method.
     * Double buffered graphics is enabled. This allows for drawing into a off-screen buffer
     * in main memory. The final image is copied from the off-screen buffer to the screen.
     * The boolean field readyToPaint is set to true, to enable drawing.
     * It also loads the sprites used in the game.
     * 
     * @version 2017-02-28
     * 
     */
    public SpaceBoard()
    {         
        try
        {
            setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));    
            setFocusable(true);
            setBackground(Color.black);
            setDoubleBuffered(true);        
            readyToPaint = true;

            imgSaucer = ImageIO.read(new File("saucer.png"));
            imgBH = ImageIO.read(new File("blackhole.png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * The boolean value readyToPaint is set to false to ensure that the previous drawing has 
     * been finished before a new one is drawn. 
     * The field ObjectList is assigned the parameter ObjectList.
     * repaint() makes a call to the paintComponent method.
     * 
     * @param  objectList   The list of PhysObjects. 
     * 
     */
    public synchronized void updateView(List<PhysObject> objectList){
        if(readyToPaint)
        {
            readyToPaint = false;
            this.objectList = objectList;
            repaint();

        }   
    }

    /**
     * Draws nine default width x height JPanels, while being weary of an IOException. 
     * Only the JPanel in the middle, (0, 0) to (width, height), is shown to the player. 
     * 
     * @param  g   Essential graphics component.
     * 
     * @version 2017-02-28
     * 
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        try
        {
            drawObjects(g,-1,-1);
            drawObjects(g,-1,0);
            drawObjects(g,-1,1);
            drawObjects(g,0,-1);
            drawObjects(g,0,0);
            drawObjects(g,0,1);
            drawObjects(g,1,-1);
            drawObjects(g,1,0);
            drawObjects(g,1,1);
        }catch(IOException e){}

    }

    /**
     * Goes through the entire list of objects. Graphics 2D is used. 
     * Its rotation method is used to orient the spaceship.
     * Makes a spaceship blink yellow and red if it collides with a projectile, another 
     * spaceship or with the CelestBody. The factor 1.23 is for ensuring that circular hitbox
     * covers the appropriate part of the image, for the sake of realism.
     *
     * @param  g   Essential graphics component. 
     * @param  xOffset  The offset multiplier along the x-dimension. A multiple of the width. 
     * @param  yOffset  The offset multiplier along the y-dimension. A multiple of the height. 
     * 
     * @version 2017-02-28
     */
    private void drawObjects(Graphics g,float xOffset,float yOffset) throws IOException
    {
        if(objectList != null)
        {

            for(PhysObject pObj : objectList)
            {

                Graphics2D g2d = (Graphics2D)g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

                Geometry geom = pObj.getGeometry(); 

                if(geom instanceof Circle)
                {
                    Circle circle = (Circle)geom;
                    float radius = circle.getRadius();

                    if(pObj instanceof Spaceship)
                    {
                        if(((Spaceship)pObj).isExploding())
                        {
                            if( ((Spaceship)pObj).getExplodeTimeLeft() % 2 == 0)
                            {
                                g2d.setColor(Color.red);
                            }
                            else
                            {
                                g2d.setColor(Color.yellow);
                            }
                            g2d.fillOval(round(pObj.getXpos() - radius + xOffset*BOARD_WIDTH), round(pObj.getYpos() - radius + yOffset*BOARD_HEIGHT),
                                round(radius*2), round(radius*2));
                        }
                        else
                        {  
                            float xPos = pObj.getXpos();
                            float yPos = pObj.getYpos();
                            float orientation = (float)(((Spaceship)pObj).getOrientation() + PI/2);

                            float factor = (float)1.2;

                            AffineTransform old = g2d.getTransform();
                            g2d.rotate(orientation, xPos + xOffset*BOARD_WIDTH, yPos + yOffset*BOARD_HEIGHT);

                            g2d.drawImage(imgSaucer, (int)round(pObj.getXpos() - radius*factor + xOffset*BOARD_WIDTH), 
                                (int)round(pObj.getYpos() - radius*factor + yOffset*BOARD_HEIGHT),
                                (int)(SHIP_RADIUS*2*factor), (int)(SHIP_RADIUS*2*factor), null, null);
                            g2d.setTransform(old);

                        }
                    }
                    else if(pObj instanceof CelestBody)
                    {
                        float factor = (float)1.23;

                        g2d.drawImage(imgBH, (int)round(pObj.getXpos() - radius*factor + xOffset*BOARD_WIDTH), 
                            (int)round(pObj.getYpos() - radius*factor + yOffset*BOARD_HEIGHT),
                            (int)(CBODY_RADIUS*2*factor), (int)(CBODY_RADIUS*2*factor), null, null);
                    }
                    //Can only be the weapon projectile in this version
                    else
                    {
                        g2d.setColor(Color.blue);
                        g2d.fillOval(round(pObj.getXpos() - radius + xOffset*BOARD_WIDTH), round(pObj.getYpos() - radius + yOffset*BOARD_HEIGHT),
                            round(radius*2), round(radius*2));
                    }
                }
            }
            readyToPaint = true;
        }
    }
}