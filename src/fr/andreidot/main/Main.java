package fr.andreidot.main;

import fr.andreidot.math.Vector3f;
import fr.andreidot.render.Camera;
import fr.andreidot.render.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.*;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    boolean isRunning = false;

    Camera cam;

    public Main() {
        DisplayManager.createFrame("AndreiEngine",800,600, false );
        cam = new Camera(new Vector3f(2,1,2));
        cam.setPerspectiveProjetion(90.0f, 0.1f, 1000.0f);
    }

    public void start() {
        isRunning = true;
        loop();
    }

    public void stop() {
        isRunning = false;
    }

    public void tick() {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mouse.setGrabbed(false);
        } else if(Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
        }
        if(!Mouse.isGrabbed()) return;
    }

    public void quit() {
        DisplayManager.dispose();
        System.exit(0);
    }

    public void render() {
        DisplayManager.clearBuffers();
        cam.getPerspectiveProjection();
    
        glBegin(GL_QUADS);
            glVertex3f(-1,-0.5f,-1);
            glVertex3f(1,-0.5f,-1);
            glVertex3f(1,-0.5f,-3);
            glVertex3f(-1,-0.5f,-3);
        glEnd();
    }

    public void loop() {
        long lastTick = System.nanoTime();

        double tickTime = 1000000000.0 / 60.0;
        double renderTime = 1000000000.0 / 75.0; // 75.0 = FPS max

        int ticks = 0;
        int FPS = 0;

        long timer = System.currentTimeMillis();

        boolean rendered = false;

        while(isRunning) {
            // Condition to check if the window is closed
            if(DisplayManager.isClosed()) {
                stop();
            }

            if(System.nanoTime() - lastTick > tickTime) {
                tick();
                ticks++;
                lastTick += tickTime;
            } else if(System.nanoTime() - lastTick > renderTime) {
                render();
                DisplayManager.update();

                FPS++;
                rendered = true;
                lastTick += renderTime;
            }

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                 System.out.println(ticks + " ticks, " + FPS + " fps");


                ticks = 0;
                FPS = 0;
            } else {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Main thread = new Main();
        thread.start();
    }
}