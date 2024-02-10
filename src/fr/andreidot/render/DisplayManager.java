package fr.andreidot.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class DisplayManager {

    /*
    Creating the frame
     */
    public static void createFrame(String frameTitle, int frameWidth, int frameHeight, boolean isResizable) {
        try {
            Display.setDisplayMode(new DisplayMode(frameWidth, frameHeight));
            Display.setTitle(frameTitle);
            Display.setResizable(isResizable);
            Display.create();

            GL11.glEnable(GL11.GL_CULL_FACE);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void update() {
        Display.update();
    }

    public static void clearBuffers() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public static boolean isClosed() {
        return Display.isCloseRequested();
    }

    public static void dispose() {
        Display.destroy();
    }
}
