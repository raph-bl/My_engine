package fr.andreidot.main;

import fr.andreidot.math.Vector3f;
import fr.andreidot.render.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class Main {

    // TODO : ObjLoader

    boolean isRunning                               = false;
    public static boolean isDebugMenuShown          = false;

    public static int rotValue                      = 0;

    Camera cam;

    // Loader loader       = new Loader();
    // RawModel mdl        = OBJLoader.loadObjModel("monkey", loader);

    public Main() {
        DisplayManager.createFrame("AndreiEngine",800,600, false );
        cam = new Camera(new Vector3f(2,1,2));
        cam.setPerspectiveProjetion(90.0f, 0.1f, 1000.0f);
    }

    public void start() {
        isRunning = true;
        loop();
    }

    public void stop() { isRunning = false; }

    public void update() {
        if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Mouse.setGrabbed(false);
        } else if(Mouse.isButtonDown(0)) {
            Mouse.setGrabbed(true);
        }
        if(!Mouse.isGrabbed()) return;
        cam.keyInputListener();
    }

    public void quit() {
        DisplayManager.dispose();
        System.exit(0);
    }

    public void render() {
        DisplayManager.clearBuffers();
        cam.getPerspectiveProjection();
        cam.update();

        if(isDebugMenuShown) {
            System.out.println("hello");
        }

        // Grid Drawing
        glBegin(GL_LINES);
        for(int i=0;i<=10;i++) {
            if (i==0) { glColor3f(0.6f,0.3f,0.3f); } else { glColor3f(0.25f,0.25f,0.25f); };
            glVertex3f(i,0,0);
            glVertex3f(i,0,10);
            if (i==0) { glColor3f(0.3f,0.3f,0.6f); } else { glColor3f(0.25f,0.25f,0.25f); };
            glVertex3f(0,0,i);
            glVertex3f(10,0,i);
        };
        glEnd();

        glTranslatef(5,0.5f,5);
        glRotatef(rotValue, 0, 1, 0);
        glBegin(GL_QUADS);
            glColor3f(1.0f, 0.0f, 0.0f);
            // FRONT
            glVertex3f(-0.5f, -0.5f, 0.5f);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f( 0.5f, -0.5f, 0.5f);
            glVertex3f( 0.5f, 0.5f, 0.5f);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(-0.5f, 0.5f, 0.5f);
            // BACK
            glVertex3f(-0.5f, -0.5f, -0.5f);
            glVertex3f(-0.5f, 0.5f, -0.5f);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f( 0.5f, 0.5f, -0.5f);
            glVertex3f( 0.5f, -0.5f, -0.5f);

            glColor3f(0.0f, 1.0f, 0.0f);
            // LEFT
            glVertex3f(-0.5f, -0.5f, 0.5f);
            glVertex3f(-0.5f, 0.5f, 0.5f);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f(-0.5f, 0.5f, -0.5f);
            glColor3f(1.0f, 0.0f, 0.0f);
            glVertex3f(-0.5f, -0.5f, -0.5f);
            // RIGHT
            glVertex3f( 0.5f, -0.5f, -0.5f);
            glVertex3f( 0.5f, 0.5f, -0.5f);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f( 0.5f, 0.5f, 0.5f);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f( 0.5f, -0.5f, 0.5f);

            glColor3f(0.0f, 0.0f, 1.0f);
            // TOP
            glVertex3f(-0.5f, 0.5f, 0.5f);
            glVertex3f( 0.5f, 0.5f, 0.5f);
            glColor3f(0.0f, 1.0f, 0.0f);
            glVertex3f( 0.5f, 0.5f, -0.5f);
            glVertex3f(-0.5f, 0.5f, -0.5f);
            glColor3f(1.0f, 0.0f, 0.0f);
            // BOTTOM
            glVertex3f(-0.5f, -0.5f, 0.5f);
            glColor3f(0.0f, 0.0f, 1.0f);
            glVertex3f(-0.5f, -0.5f, -0.5f);
            glVertex3f( 0.5f, -0.5f, -0.5f);
            glVertex3f( 0.5f, -0.5f, 0.5f);
        glEnd();
    }

    public void loop() {
        long lastTickTime = System.nanoTime();
        long lastRenderTime = System.nanoTime();

        double tickTime = 1000000000.0 / 60.0;
        double renderTime = 1000000000.0 / 75; // FPS MAX

        int TICKS = 0;
        int FPS = 0;

        long timer = System.currentTimeMillis();

        while (isRunning) {
            if (DisplayManager.isClosed()) stop();

            if (System.nanoTime() - lastTickTime > tickTime) {
                lastTickTime += tickTime;

                rotValue++;
                update();
                TICKS++;
            } else if (System.nanoTime() - lastRenderTime > renderTime) {
                lastRenderTime += renderTime;
                render();
                DisplayManager.update();
                FPS++;
            }else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(TICKS + " ticks, " + FPS + " fps");
                TICKS = 0;
                FPS = 0;
            }
        }
        quit();
    }

    public static void main(String[] args) {
        Main thread = new Main();
        thread.start();
    }
}