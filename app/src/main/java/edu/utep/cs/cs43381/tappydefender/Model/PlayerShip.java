package edu.utep.cs.cs43381.tappydefender.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import edu.utep.cs.cs43381.tappydefender.R;

public class PlayerShip {
    private int x;
    private int y;
    private int speed=0;
    private boolean boosting;
    private final int GRAVITY = -12;
    private int maxY;
    private int minY;
    private final int MIN_SPEED=1;
    private final int MAX_SPEED=20;
    private Rect hitBox;
    private int shieldStrength;
    private Bitmap bitmap;
    public PlayerShip(Context context, int screenX, int screenY){
        boosting = false;
        x=50;
         y = 50;
        speed=1;
        shieldStrength=2;

        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);
        maxY=screenY-bitmap.getHeight();
        minY=0;
        hitBox=new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());
    }
    public void update(){
        if (boosting){
            speed += 2;
        } else{
            speed -= 5;
        }
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if(speed<MIN_SPEED){
            speed=MIN_SPEED;
        }
        y-= speed+GRAVITY;
        if (y <minY){
            y=minY;
        }
        if(y>maxY) {
            y = maxY;
        }
        hitBox.left=x;
        hitBox.top=y;
        hitBox.right=x+bitmap.getWidth();
        hitBox.bottom= y+bitmap.getHeight();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setBoosting(){
        boosting = true;
    }
    public void stopBoosting(){
        boosting = false;
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public int getShieldStrength() {
        return shieldStrength;
    }
    public void reduceShieldStrength(){
        shieldStrength--;
    }

}
