package edu.utep.cs.cs43381.tappydefender.Model;

import java.util.Random;

public class SpaceDust {
    private int x,y;
    private int speed;
    private int maxX,maxY,minX,minY;
    public SpaceDust(int screenX, int screenY){
        maxX=screenX;
        maxY=screenY;
        minX=0;
        minY=0;
        Random generator = new Random();
        speed = generator.nextInt(10);
        x= generator.nextInt(maxX);
        y=generator.nextInt(maxY);

    }
    public void update(int playerSpeed){
        x-=playerSpeed;
        x-=speed;
        if(x<0){
            x=maxX;
            Random generator=new Random();
            y=generator.nextInt(maxY);
            speed=generator.nextInt(15);

        }
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getSpeed() {
        return speed;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }
}
