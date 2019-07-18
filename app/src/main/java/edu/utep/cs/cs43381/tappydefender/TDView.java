package edu.utep.cs.cs43381.tappydefender;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import edu.utep.cs.cs43381.tappydefender.Model.EnemyShip;
import edu.utep.cs.cs43381.tappydefender.Model.PlayerShip;
import edu.utep.cs.cs43381.tappydefender.Model.SpaceDust;

public class TDView extends SurfaceView implements Runnable{

    private SoundPool soundPool;
    private PlayerShip player;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;
    public EnemyShip enemy1;
    public EnemyShip enemy2;
    public EnemyShip enemy3;
    public List<SpaceDust> dustList =new CopyOnWriteArrayList<SpaceDust>();
    private float distanceRemaining;
    private long timeTaken;
    private long timeStarted;
    private long fastestTime;
    private int screenX;
    private int screenY;
    private Thread gameThread = null;
    volatile boolean playing;
    private Context context;
    private boolean gameEnded;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    SoundEffect soundEffect;
    public TDView(Context ctx, int x, int y){
        super(ctx);
        this.context=ctx;
        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        screenX=x;

        screenY=y;
        holder = getHolder();
        paint = new Paint();
        prefs = context.getSharedPreferences("HiScores",context.MODE_PRIVATE);
        soundEffect=new SoundEffect(context);

        editor = prefs.edit();

        fastestTime=prefs.getLong("fastestTime",0);
       startGame();
    }


    @Override
    public void run() {
        while(playing){
            update();
            draw();
            control();

        }
    }
    public void pause(){
        playing=false;
        try{
            gameThread.join();
        }
        catch (InterruptedException e){

        }

    }
    public void resume(){
        playing=true;
        gameThread= new Thread(this);
        gameThread.start();
    }
    private void update(){
        boolean hitDetected = false;
        if(Rect.intersects(player.getHitBox(),enemy1.getHitBox())){
           hitDetected=true;
            enemy1.setX(-1000);
        }

        if(Rect.intersects(player.getHitBox(),enemy2.getHitBox())){
            hitDetected=true;
            enemy2.setX(-1000);
        }

        if(Rect.intersects(player.getHitBox(),enemy3.getHitBox())){
            hitDetected=true;
            enemy3.setX(-1000);
        }
        if(hitDetected){
            //soundEffect.play(SoundEffect.Sound.BUMP);
            player.reduceShieldStrength();
            if(player.getShieldStrength()<0){
                //soundEffect.play(SoundEffect.Sound.DESTROYED);
                gameEnded=true;
            }
        }

        player.update();
        enemy1.update(player.getSpeed());
        enemy2.update(player.getSpeed());
        enemy3.update(player.getSpeed());
        for(SpaceDust sd:dustList){
            sd.update(player.getSpeed());
        }
        if(!gameEnded){
            distanceRemaining-= player.getSpeed();
            timeTaken= System.currentTimeMillis()-timeStarted;
        }
        if(distanceRemaining<0){
           //soundEffect.play(SoundEffect.Sound.WIN);
            if(timeTaken<fastestTime){
                editor.putLong("fastestTime",timeTaken);
                fastestTime=prefs.getLong("fastestTime",Long.MAX_VALUE);
                fastestTime=timeTaken;
            }
            distanceRemaining=0;
            gameEnded=true;
        }
    }
    private void draw(){
        if(holder.getSurface().isValid()){
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255,255,255,255));

            for(SpaceDust sd:dustList) {
                canvas.drawPoint(sd.getX(), sd.getY(), paint);
                canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
                canvas.drawBitmap(enemy1.getBitmap(), enemy1.getX(), enemy1.getY(), paint);
                canvas.drawBitmap(enemy2.getBitmap2(), enemy2.getX(), enemy2.getY(), paint);
                canvas.drawBitmap(enemy3.getBitmap3(), enemy3.getX(), enemy3.getY(), paint);

            }
            if(!gameEnded) {
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(25);
                canvas.drawText("Fastest: " + fastestTime + "s", 10, 20, paint);
                canvas.drawText("Time: " + timeTaken + "s", screenX / 2, 20, paint);
                canvas.drawText("Distance: " + distanceRemaining / 1000 + "KM", screenX / 3, screenY - 20, paint);

                canvas.drawText("Shield: " + player.getShieldStrength(), 10, screenY - 20, paint);
                canvas.drawText("Speed: " + player.getSpeed() * 60 + " MPS", (screenX / 3) * 2, screenY - 20, paint);
            }else {
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenX/2,100,paint);
                paint.setTextSize(25);
                canvas.drawText("Fastest:"+ fastestTime+"s", screenX/2,160, paint);
                canvas.drawText("Time:"+ timeTaken+"s",screenX/2,200,paint);
                canvas.drawText("Distance remaining: "+ distanceRemaining/1000+ "KM",screenX/2,240,paint);
                paint.setTextSize(80);
                canvas.drawText("Tap to replay!", screenX/2,350,paint);
            }

            holder.unlockCanvasAndPost(canvas);
        }
    }
    private void control(){
        try{
            gameThread.sleep(15);
        } catch (InterruptedException e){

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                if(gameEnded){
                    startGame();
                }
                break;

        }
        return true;
    }
    private void startGame(){
        player = new PlayerShip(context,screenX,screenY);
        enemy1 = new EnemyShip(context, screenX,screenY);
        enemy2 = new EnemyShip(context,screenX,screenY);
        enemy3 = new EnemyShip(context,screenX,screenY);

        int numSpecs = 40;
        for(int i = 0; i < numSpecs; i++) {
            SpaceDust spec = new SpaceDust(screenX,screenY);
            dustList.add(spec);
        }
        distanceRemaining = 10000;
        timeTaken=0;
        timeStarted= System.currentTimeMillis();
        gameEnded = false;
       //soundEffect.play(SoundEffect.Sound.START);
    }



}
