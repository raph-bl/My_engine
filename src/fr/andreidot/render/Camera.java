package fr.andreidot.render;

import fr.andreidot.math.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.glu.GLU;

import org.lwjgl.util.glu.GLU;

import java.security.Key;

import static org.lwjgl.opengl.GL11.*;

public class Camera {

    private float fov, zNear, zFar;

    public static float mouseSpeed  = 0.3f;
    public static float moveSpeed   = 3f;

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

    public void update() {
        glPushAttrib(GL_TRANSFORM_BIT);

        // Idem que pour le translatef(c'est indispensable) => ça se fait par axe par exemple rotate.getX(), est égal à x y z
        // Exemple ; rotate.getX(),1,0,0 va faire rotate sur l'axe X
        glRotatef(rot.getX(),1,0,0);
        glRotatef(rot.getY(),0,1,0);
        glRotatef(rot.getZ(),0,0,1);
        // glTranslatef = /!\ Important ne pas oublier, fonction permettant de nous faire bouger.

        glTranslatef(-pos.getX(),-pos.getY(),-pos.getZ());
        glPopMatrix();
    }

    public Vector3f getForward() {
        Vector3f r = new Vector3f();
        Vector3f rot = new Vector3f(this.rot);

        float cosinusY = (float) Math.cos(Math.toRadians(rot.getY() - 90));
        float sinusY = (float) Math.sin(Math.toRadians(rot.getY() - 90));
        float cosinusP = (float) Math.cos(Math.toRadians(-rot.getX()));
        float sinusP = (float) Math.sin(Math.toRadians(-rot.getX()));

        // Euler Angles

        r.setX(cosinusY * cosinusP);
        r.setY(sinusP);
        r.setZ(sinusY * cosinusP);

        r.setY(0);

        if(r.length() > 0) {
            r = r.normalize();
        }

        return new Vector3f(r);
    }

    public void keyInputListener() {
        rot.addX(-Mouse.getDY() * mouseSpeed);
        rot.addY(Mouse.getDX() * mouseSpeed);

        if(rot.getX() > 90)
            rot.setX(90);
        if(rot.getX() < -90)
            rot.setX(-90);

        if(Keyboard.isKeyDown(Keyboard.KEY_Z) || Keyboard.isKeyDown(Keyboard.KEY_UP))
            pos.add(getForward().mul(moveSpeed));
    }

    // Getters & Setters

    public Vector3f getPosition() { return pos; }

    public void setPosition() { this.pos = pos; }
    public void setRotation() { this.rot = rot; }
}
