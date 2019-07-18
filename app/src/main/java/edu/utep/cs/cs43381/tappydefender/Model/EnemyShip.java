package edu.utep.cs.cs43381.tappydefender.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import edu.utep.cs.cs43381.tappydefender.R;

public class EnemyShip {
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private int x,y;
    private int speed=1;
     private int maxX;
     private int minX;
     private int maxY;
     private int minY;
     private Rect hitBox;
     public EnemyShip(Context context,int screenX, int screenY){
         Random generator = new Random();
         int whichBitmap = generator.nextInt(3);
         bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
         bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy2);
         bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy3);


         maxX=screenX;
         maxY=screenY;
         minX=0;
         minY=0;
         speed= generator.nextInt(6)+10;
         x= screenX;
         y=generator.nextInt(maxY)-bitmap.getHeight();
         hitBox=new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());
     }


    public Bitmap getBitmap(){
        return bitmap;
    }

    public Bitmap getBitmap2() {
        return bitmap2;
    }

    public Bitmap getBitmap3() {
        return bitmap3;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void update(int playerSpeed){
         x-= playerSpeed;
         x-= speed;

         if(x<minX-bitmap.getWidth()){
             Random generator = new Random();
             speed=generator.nextInt(10)+10;
             x=maxX;
             y = generator.nextInt(maxY)-bitmap.getHeight();
         }

        hitBox.left=x;
        hitBox.top=y;
        hitBox.right=x+bitmap.getWidth();
        hitBox.bottom= y+bitmap.getHeight();
    }

    public Rect getHitBox() {
        return hitBox;
    }
}
