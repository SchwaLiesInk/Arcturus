/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arcengine;

/**
 *
 * @author Gerwyn Jones
 */
@FunctionalInterface
interface GraphicsUpdate {

    public abstract void Update(GameTime time, Camera cam);
}

public class GameTime {

    double gameTime;
    double animTime;
    double animTime2;
    double cameraTime;
    double simTime;
    double aiTime;
    long last;
    int frames;
    int fps;
    int year;
    double day;
    double daysPerSecond = 0.01;
    static double GameSpeed = 24;
    static double AnimSpeed = 1;
    double secs;
    boolean paused = false;

    public void Start() {
        last = System.nanoTime();
        gameTime = 0;
        animTime = 0;
        cameraTime = 0;
        simTime = 0;
        aiTime = 0;
        frames = 0;
        fps = 0;
        secs = 0;
        paused = false;
    }

    private double Elapsed() {
        long t = System.nanoTime();
        long dif = t - last;
        last = t;
        return (double) (dif)*1.0e-9;
    }

    public double ElapsedTime() {
        return cameraTime;
    }

    public double AnimTime() {
        return animTime;
    }

    public double AnimTime2() {
        return animTime2;
    }

    public int Year() {
        return year;
    }

    public void CalcTimes() {
        double e = Elapsed();
        secs += e;
        frames++;
        if (secs >= 1.0) {
            fps = frames;
            secs -= 1.0;
            frames = 0;
        }
        if (paused) {
            cameraTime = e;
        } else {
            day += e * daysPerSecond;
            if (day > 356) {
                day = 0;
                year++;
            }
            cameraTime = e*GameSpeed;
            animTime = e*AnimSpeed*GameSpeed;
            animTime2 = animTime * animTime;
            gameTime += e;
            simTime += e;
            aiTime += e;
        }
    }

    public boolean OnSim(int period) {
        if ((int) (simTime + 1) % period == 0) {
            simTime = 0;
            return true;
        }
        return false;
    }

    public boolean OnGame(int period) {
        if ((int) (gameTime + 1) % period == 0) {
            gameTime = 0;
            return true;
        }
        return false;
    }

    public boolean OnAi(int period) {
        if ((int) (aiTime + 1) % period == 0) {
            aiTime = 0;
            return true;
        }
        return false;
    }
}

class Simulation extends GameTime implements Runnable {

    protected boolean complete;
    static volatile java.util.ArrayList<MapElement> peopleSims = new java.util.ArrayList();
    static volatile java.util.ArrayList<MapElement> vehicleSims = new java.util.ArrayList();
    static volatile java.util.ArrayList<MapElement> monsterSims = new java.util.ArrayList();
    static volatile java.util.ArrayList<MapElement> alienSims = new java.util.ArrayList();
    static volatile java.util.ArrayList<MapElement> animalSims = new java.util.ArrayList();
    static volatile java.util.ArrayList<MapElement> shotSims = new java.util.ArrayList();
    static volatile java.util.ArrayList<MapElement> explosionSims = new java.util.ArrayList();

    @Override
    public void run() {

    }

    boolean Complete() {
        return complete;
    }

}
