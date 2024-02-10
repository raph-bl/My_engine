package fr.andreidot.render;

import com.sun.prism.impl.BufferUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> VAOs = new ArrayList<>();
    private List<Integer> VBOs = new ArrayList<>();
    private List<Integer> tex  = new ArrayList<>();

    public RawModel loadToVAO(float[] pos, int[] indices) {
        int vaoId = createVAO();
        bindIndiciesBuffer(indices);
        storeDataInAttribueList(0, 3, pos);
        unbindVAO();
        return new RawModel(vaoId, indices.length);
    }

    public void cleanup() {
        for(int vao:VAOs) { GL30.glDeleteVertexArrays(vao); }
        for(int vbo:VBOs) { GL15.glDeleteBuffers(vbo); }
    }

    private int createVAO() {
        int vaoId = GL30.glGenVertexArrays();
        VAOs.add(vaoId);
        GL30.glBindVertexArray(vaoId);
        return vaoId;
    }

    private void unbindVAO() { GL30.glBindVertexArray(0); }

    private void storeDataInAttribueList(int attributeNumber, int coordSize, float[] data) {
        int vboId = GL15.glGenBuffers();
        VBOs.add(vboId);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buff = storeDataFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private IntBuffer storeDataIntBuffer(int[] data) {
        IntBuffer buff = BufferUtils.createIntBuffer(data.length);
        buff.put(data);
        buff.flip();
        return buff;
    }

    private FloatBuffer storeDataFloatBuffer(float[] data) {
        FloatBuffer buff = BufferUtils.createFloatBuffer(data.length);
        buff.put(data);
        buff.flip();
        return buff;
    }

    private void bindIndiciesBuffer(int[] indicies) {
        int vboId = GL15.glGenBuffers();
        VBOs.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        IntBuffer buff = storeDataIntBuffer(indicies);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buff, GL15.GL_STATIC_DRAW);
    }

}

