package fr.andreidot.render;

import fr.andreidot.math.Vector3f;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import org.lwjgl.util.glu.GLU;
import static org.lwjgl.opengl.GL11.*;

public class Camera {

    private float fov, zNear, zFar;

    public static float mouseSpeed = 0.3f;

    public Vector3f pos, rot;

    public Camera(Vector3f pos) {
        this.pos = pos;
        rot = new Vector3f(0,0,0);
    }

    public Camera setPerspectiveProjetion(float fov, float zNear, float zFar) {
        this.fov = fov;
        this.zNear = zNear;
        this.zFar = zFar;

        return this;
    }

    public void getPerspectiveProjection() {
        glEnable(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(fov, (float) Display.getWidth() / Display.getHeight(), zNear, zFar);
        glEnable(GL_MODELVIEW);
    }

    // Getters & Setters

    public Vector3f getPosition() { return pos; }

    public void setPosition() { this.pos = pos; }
    public void setRotation() { this.rot = rot; }
}
