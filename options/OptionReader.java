package options;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OptionReader{

  private static OptionReader instance = null;

  private static final String FILENAME = "options.txt";
  private BufferedReader br;

  private String nickname;
  private int boardWidth;
  private int boardHeight;
  private int numLives;
  private int cBodyRadius;
  private int gravRadious;
  private int gravBaseStr;
  private int period;
  private int wprojTtl;
  private int wprojRadius;
  private int wprojBaseSpeed;
  private int shipRadius;
  private int explodeTime;
  private int relodeTime;
  private float acceleration;
  private float orientRate;
  private float maxSpeed;
  private float gravBaseMass;

  private OptionReader(){
    readOption();
  }

  public static OptionReader getInstance(){
    if(instance == null){
      instance = new OptionReader();
    }
    return instance;
  }

  public void readOption(){
    try{
      br = new BufferedReader(new FileReader(FILENAME));
      String currentLine;

      while((currentLine = br.readLine()) != null){
        String[] parts = currentLine.split(" ");
        String syntax = parts[0].toLowerCase();
        String value = parts[1];

        switch (syntax){
          case "nickname":
            nickname = value;
            break;

          case "boardwidth":
            boardWidth = Integer.parseInt(value);
            break;

          case "boardheight":
            boardHeight = Integer.parseInt(value);
            break;

          case "numlives":
            numLives = Integer.parseInt(value);
            break;

          case "cbodyradius":
            cBodyRadius = Integer.parseInt(value);
            break;

          case "gravradius":
            gravRadious = Integer.parseInt(value);
            break;

          case "gravbasestr":
            gravBaseStr = Integer.parseInt(value);
            break;

          case "period":
            period = Integer.parseInt(value);
            break;

          case "wprojttl":
            wprojTtl = Integer.parseInt(value);
            break;

          case "wprojradius":
            wprojRadius = Integer.parseInt(value);
            break;

          case "wprojbasespeed":
            wprojBaseSpeed = Integer.parseInt(value);
            break;

          case "shipradius":
            shipRadius = Integer.parseInt(value);
            break;

          case "explodetime":
            explodeTime = Integer.parseInt(value);
            break;

          case "relodetime":
            relodeTime = Integer.parseInt(value);
            break;

          case "acceleration":
            acceleration = Float.parseFloat(value);
            break;

          case "orientrate":
            orientRate = Float.parseFloat(value);
            break;

          case "maxspeed":
            maxSpeed = Float.parseFloat(value);
            break;

          case "gravBaseMass":
            gravBaseMass = Float.parseFloat(value);
            break;
        }


      }
    }catch(IOException e){
      e.printStackTrace();
    } finally{
      try{
        if(br != null){
          br.close();
        }
      }catch(IOException ex){
        ex.printStackTrace();
      }
    }
  }

  public String getNickname(){
    return nickname;
  }

  public int getBoardWidth(){
    return boardWidth;
  }

  public int getBoardHeight(){
    return boardHeight;
  }

  public int getNumLives(){
    return numLives;
  }

  public int getCBodyRadius(){
    return cBodyRadius;
  }

  public int getGravRadius(){
    return gravRadious;
  }

  public int getGravBaseStr(){
    return gravBaseStr;
  }

  public int getPeriod(){
    return period;
  }

  public int getWprojTtl(){
    return wprojTtl;
  }

  public int getWprojRadius(){
    return wprojRadius;
  }

  public int getWprojBaseSpeed(){
    return wprojBaseSpeed;
  }

  public int getShipRadius(){
    return shipRadius;
  }

  public int getExplodeTime(){
    return explodeTime;
  }

  public int getReloadTime(){
    return relodeTime;
  }

  public float getAcceleration(){
    return acceleration;
  }

  public float getOrientRate(){
    return orientRate;
  }

  public float getMaxSpeed(){
    return maxSpeed;
  }

  public float getGravBaseMass(){
    return gravBaseMass;
  }

}
