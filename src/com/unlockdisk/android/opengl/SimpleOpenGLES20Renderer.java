package com.unlockdisk.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class SimpleOpenGLES20Renderer implements GLSurfaceView.Renderer {

    public float mAngle;

    static String TAG = "SimpleTest";

    final int SIZEOF_FLOAT = Float.SIZE / 8;
    final int SIZEOF_SHORT = Short.SIZE / 8;


    private int[] mVBOid = new int[2];      // 2 ids needed for VBO and index buffer oject


    enum TestType {
        USE_ARRAY,          // (almost) the original code
        USE_ELEMENTS,       // rendering, using glDrawElements call
        USE_VBO_ELEMENTS    // using a vertex buffer object (VBO)
    }
    private TestType mUsage = TestType.USE_ARRAY;//TestType.USE_VBO_ELEMENTS;


    private boolean mFourComponents = false;

    private int mNumIndices = 0;

    private FloatBuffer mTriangleVB;
    private ShortBuffer mIndices;
    private final String vertexShaderCode = 
        // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;   \n" +

        "attribute vec4 vPosition;  \n" +
        "void main(){               \n" +

        // the matrix must be included as a modifier of gl_Position
        " gl_Position = uMVPMatrix * vPosition; \n" +

        "}  \n";

    private final String fragmentShaderCode = 
        "precision mediump float;  \n" +
        "void main(){              \n" +
        " gl_FragColor = vec4 (0.63671875, 0.0, 0.22265625, 1.0); \n" +
        "}                         \n";

    private int mProgram;

    private int maPositionHandle;

    private int muMVPMatrixHandle;

    private float[] mMVPMatrix = new float[16];
    private float[] mMMatrix = new float[16];
    private float[] mVMatrix = new float[16];
    private float[] mProjMatrix = new float[16];

    public static void checkGLError(String msg) {
        int e = GLES20.glGetError();
        if (e != GLES20.GL_NO_ERROR) {
            Log.d(TAG, "GLES20 ERROR: " + msg + " " + e);
            Log.d(TAG, errString(e));
        }
    }
    public static String errString(int ec) {
        switch (ec) {
        case GLES20.GL_NO_ERROR:
            return "No error has been recorded.";
        case GLES20.GL_INVALID_ENUM:
            return "An unacceptable value is specified for an enumerated argument.";
        case GLES20.GL_INVALID_VALUE:
            return "A numeric argument is out of range.";
        case GLES20.GL_INVALID_OPERATION:
            return "The specified operation is not allowed in the current state.";
        case GLES20.GL_INVALID_FRAMEBUFFER_OPERATION:
            return "The command is trying to render to or read from the framebuffer" +
            " while the currently bound framebuffer is not framebuffer complete (i.e." +
            " the return value from glCheckFramebufferStatus is not" +
            " GL_FRAMEBUFFER_COMPLETE).";
        case GLES20.GL_OUT_OF_MEMORY:
            return "There is not enough memory left to execute the command." +
                    " The state of the GL is undefined, except for the state" +
                    " of the error flags, after this error is recorded.";
        default :
            return "UNKNOW ERROR";
        }
    }

    public void onSurfaceCreated(GL10 uu, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        checkGLError("onSurfaceCreated 1");

        initShapes();

        Log.d(TAG, "load vertex shader");
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        Log.d(TAG, "load fragment shader");
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        checkGLError("onSurfaceCreated 2");
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        checkGLError("onSurfaceCreated 3");
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        checkGLError("onSurfaceCreated 4");
        GLES20.glLinkProgram(mProgram);                  // creates OpenGL program executables
        checkGLError("onSurfaceCreated 5");

        // get handle to the vertex shader's vPosition member
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        checkGLError("onSurfaceCreated 6");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        checkGLError("onSurfaceCreated 7");
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }  

    public void onDrawFrame(GL10 uu) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        checkGLError("onDrawFrame 1");

        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);
        checkGLError("onDrawFrame 2");

        // Use the mAngle member as the rotation value
        Matrix.setRotateM(mMMatrix, 0, mAngle, 0, 0, 1.0f);

        // Apply a ModelView Projection transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, mMMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        checkGLError("onDrawFrame 3");

        int nc = mFourComponents ? 4 : 3;
        int stride = nc * SIZEOF_FLOAT;

        switch (mUsage) {
        case USE_ARRAY:
            // Prepare the triangle data
            GLES20.glVertexAttribPointer(maPositionHandle, nc, GLES20.GL_FLOAT, false, stride, mTriangleVB);
            checkGLError("onDrawFrame 4");
            GLES20.glEnableVertexAttribArray(maPositionHandle);
            checkGLError("onDrawFrame 5");

            // Draw the triangle
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, mNumIndices);
            checkGLError("onDrawFrame 6");
            break;
        case USE_ELEMENTS:
            // Prepare the triangle data
            GLES20.glVertexAttribPointer(maPositionHandle, nc, GLES20.GL_FLOAT, false, stride, mTriangleVB);
            checkGLError("onDrawFrame 7");
            GLES20.glEnableVertexAttribArray(maPositionHandle);
            checkGLError("onDrawFrame 8");

            // Draw the triangle
            // int indicesSizeInBytes = SIZEOF_SHORT * mNumIndices;
            GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, mNumIndices, GLES20.GL_UNSIGNED_SHORT, mIndices);
            checkGLError("onDrawFrame 9");
            break;
        case USE_VBO_ELEMENTS:
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBOid[0]);
            checkGLError("onDrawFrame 14");
            GLES20.glVertexAttribPointer(maPositionHandle, nc, GLES20.GL_FLOAT, false, stride, 0);
            checkGLError("onDrawFrame 15");
            GLES20.glEnableVertexAttribArray(maPositionHandle);
            checkGLError("onDrawFrame 16");

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mVBOid[1]);
            checkGLError("onDrawFrame 17");
            GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN, mNumIndices, GLES20.GL_UNSIGNED_SHORT, 0);
            checkGLError("onDrawFrame 18");
            break;
        }
    }

    private void initShapes(){

        float triangleCoords3[] = {
                // X, Y, Z
                -0.5f, -0.5f, 0,
                -0.5f,  0.5f, 0,
                -0.2f, -0.2f, 0,
                 0.5f, -0.5f, 0
            };
        float triangleCoords4[] = {
                // X, Y, Z, W
                -0.5f, -0.5f, 0, 1,
                -0.5f,  0.5f, 0, 1,
                -0.2f, -0.2f, 0, 1,
                 0.5f, -0.5f, 0, 1
            };

        short[] indices = {0,1,2,3};

        float[] triangleCoords;

        int numComponentsPerVertex;
        if (mFourComponents) {
            triangleCoords = triangleCoords4;
            numComponentsPerVertex = 4;
        } else {
            triangleCoords = triangleCoords3;
            numComponentsPerVertex = 3;
        }

        mNumIndices = triangleCoords.length / numComponentsPerVertex;

        Log.d(TAG, "Components per Vertex: " + numComponentsPerVertex);
        Log.d(TAG, "Number of Indices    : " + mNumIndices);

        switch (mUsage) {
        case USE_ARRAY:
        {
            Log.d(TAG, "using array");
            // initialize vertex Buffer for triangle  
            ByteBuffer vbb = ByteBuffer.allocateDirect(
                    // (# of coordinate values * 4 bytes per float)
                    triangleCoords.length * SIZEOF_FLOAT); 
            vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
            mTriangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
            mTriangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
            mTriangleVB.position(0);            // set the buffer to read the first coordinate
            break;
        }
        case USE_ELEMENTS:
        {
            Log.d(TAG, "using VBO elements");
            // initialize vertex Buffer for triangle  
            ByteBuffer vbb = ByteBuffer.allocateDirect(
                    // (# of coordinate values * 4 bytes per float)
                    triangleCoords.length * SIZEOF_FLOAT); 
            vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
            mTriangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
            mTriangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
            mTriangleVB.position(0);            // set the buffer to read the first coordinate

            vbb = ByteBuffer.allocateDirect(
                    // (# of coordinate values * 2 bytes per short)
                    indices.length * SIZEOF_SHORT);
            vbb.order(ByteOrder.nativeOrder()); // use the device hardware's native byte order
            mIndices = vbb.asShortBuffer();     // create a short buffer from the ByteBuffer
            mIndices.put(indices);              // add the indices to the Buffer
            mIndices.position(0);               // set the buffer to read the first index
            break;
        }
        case USE_VBO_ELEMENTS:
        {
            Log.d(TAG, "using VBO elements");
            ByteBuffer vbb = ByteBuffer.allocateDirect(
                    // (# of coordinate values * 4 bytes per float)
                    triangleCoords.length * SIZEOF_FLOAT); 
            vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
            mTriangleVB = vbb.asFloatBuffer();  // create a floating point buffer from the ByteBuffer
            mTriangleVB.put(triangleCoords);    // add the coordinates to the FloatBuffer
            mTriangleVB.position(0);            // set the buffer to read the first coordinate

            ByteBuffer ibb = ByteBuffer.allocateDirect(
                    indices.length * SIZEOF_SHORT);
            ibb.order(ByteOrder.nativeOrder()); // use the device hardware's native byte order
            mIndices = ibb.asShortBuffer();     // create a short buffer from the ByteBuffer
            mIndices.put(indices);              // add the indices to the Buffer
            mIndices.position(0);               // set the buffer to read the first index

            GLES20.glGenBuffers(2, mVBOid, 0);
            checkGLError("initShapes 4");

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVBOid[0]);
            checkGLError("initShapes 5");
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER,
                    numComponentsPerVertex * SIZEOF_FLOAT,
                    mTriangleVB,
                    GLES20.GL_STATIC_DRAW);
            checkGLError("initShapes 6");

            GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, mVBOid[1]);
            checkGLError("initShapes 7");
            GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER,
                    mNumIndices * SIZEOF_SHORT,
                    mIndices,
                    GLES20.GL_STATIC_DRAW);
            checkGLError("initShapes 8");
            break;
        }
        }
    }

    private int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type); 
        checkGLError("loadShader 1");

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        checkGLError("loadShader 2");
        GLES20.glCompileShader(shader);
        checkGLError("loadShader 3");

        // Get the compilation status.
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        checkGLError("loadShader 4");

        // If the compilation failed, delete the shader.
        if (compileStatus[0] == 0) 
        {
            Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shader));
            GLES20.glDeleteShader(shader);
            checkGLError("loadShader 5");
            shader = 0;
        }

        return shader;
    }
}