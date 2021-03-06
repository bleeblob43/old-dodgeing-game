/*
 * Most important
 * 
 */
import java.awt.*;
import java.awt.image.*;
import java.util.*;
public class Game extends Canvas implements Runnable{
 public static final int WIDTH = 640, HEIGHT = WIDTH/12*9;
 private Thread thread;
 private boolean running = false;
 private Handler handler;
 private Random r = new Random();
 private HUD heads;
  public Game(){
    handler = new Handler();
    this.addKeyListener(new KeyInput(handler));
    new Window(WIDTH,HEIGHT,"DANIEL'S FIRST GAME!!!!",this); 
    
    
    handler.addObject(new Particle(r.nextInt(WIDTH)+1,r.nextInt(HEIGHT)+1,ID.Particle));
    handler.addObject(new Player(300,200,ID.Player,handler));
    heads = new HUD();
    for(int i = 0;i<=3;i++)
     handler.addObject(new BasicEnemy(r.nextInt(WIDTH)+1,r.nextInt(HEIGHT)+1,ID.BasicEnemy,handler));
}
  public synchronized void start(){
   thread = new Thread(this);
   thread.start();
   running = true;
  }
  public synchronized void stop(){
    try{
      thread.join();
      running = false;
    }catch(Exception e){
     e.printStackTrace(); 
    }
  }
  public void run(){
    this.requestFocus();
    long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;s
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
         long now = System.nanoTime();
         delta += (now - lastTime) / ns;
         lastTime = now;
            while(delta >=1){
              tick();
              delta--;
            }
            if(running)
             render();
             frames++;
                            
            if(System.currentTimeMillis() - timer > 1000){
             timer += 1000;
             System.out.println("FPS: "+ frames);
             frames = 0;
            }
        }
                stop();
  }
private void tick(){
  handler.tick();
  heads.tick();
}
private void render(){
  BufferStrategy bs = this.getBufferStrategy();
  
  if(bs == null){
   this.createBufferStrategy(3);
   return;
  }
  Graphics g = bs.getDrawGraphics();
  g.setColor(Color.blue);
  g.fillRect(0,0,WIDTH,HEIGHT);
  handler.render(g);
  heads.render(g);
  g.dispose();
  bs.show();
  
}
public static int clamp(int var, int min, int max){
 if(var>=max)
   return var = max;
 else if(var<=min)
   return var = min;
  else
    return var;
}
  public static void main(String args[]){
   new Game(); 
  }
}