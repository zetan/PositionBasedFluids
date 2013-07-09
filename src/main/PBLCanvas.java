package main;

import java.awt.BorderLayout;
import java.nio.FloatBuffer;
import java.util.TimerTask;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUtessellator;
import javax.swing.JFrame;

import particlesystem.Particle;
import particlesystem.ParticleSystem;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.GLUT;

public class PBLCanvas extends GLCanvas implements GLEventListener {
	private GLU glu;
	private GLUT glut;
	private int width;
	private int height;

	ParticleSystem particleSystem;

	public PBLCanvas(GLCapabilities capabilities, int width, int height) {
		this.width = width;
		this.height = height;

		addGLEventListener(this);

		particleSystem = new ParticleSystem();

		java.util.Timer timer = new java.util.Timer(true);
		TimerTask task = new TimerTask() {
			public void run() {
				update();			
			}
		};
		timer.schedule(task, 0, 50);   
	}

	private static GLCapabilities createGLCapabilities() {
		GLCapabilities capabilities = new GLCapabilities();
		capabilities.setRedBits(8);
		capabilities.setBlueBits(8);
		capabilities.setGreenBits(8);
		capabilities.setAlphaBits(8);
		capabilities.setDoubleBuffered(true);
		return capabilities;
	}

	public void init(GLAutoDrawable drawable) {
		drawable.setGL(new DebugGL(drawable.getGL()));
		final GL gl = drawable.getGL();

		glu = new GLU();
		glut = new GLUT();

		gl.glShadeModel(GL.GL_SMOOTH); // Enables Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
		gl.glClearDepth(1.0f); // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL.GL_LESS); // The Type Of Depth Test To Do
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_FASTEST); // Really
																	// Nice
																	// Perspective
																	// Calculations

		float ambient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		float diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float position[] = { 0.0f, 3.0f, 2.0f, 0.0f };
		float lmodel_ambient[] = { 0.4f, 0.4f, 0.4f, 1.0f };


		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, lmodel_ambient, 0);

		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);

	}

	public void update() {
		particleSystem.Update();
		display();
	}

	public void display(GLAutoDrawable drawable) {
		//drawable.setGL(new DebugGL(drawable.getGL()));
		final GL gl = drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		System.out.println("start draw");
		particleSystem.Draw(gl, glu, glut);
		System.out.println("end draw");
	//	drawable.swapBuffers();
		gl.glFlush();
		
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		GL gl = drawable.getGL();

		gl.glViewport(0, 0, width, height);
		// height = h;
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		// glu.gluOrtho2D(0.0, (double) width, 0.0, (double) height);
		glu.gluPerspective(100.0f, ((float) width) / height, 0.1f, 100.0f);
		glu.gluLookAt(50.0, 30.0, 40.0, 40.0, 10.0, -100.0, 0.0, 1.0, 0.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
			boolean deviceChanged) {
		throw new UnsupportedOperationException(
				"Changing display is not supported.");
	}

	public static void main(String[] args) {
		int WIDTH = 800;
		int HEIGHT = 600;
		GLCapabilities capabilities = createGLCapabilities();
		PBLCanvas canvas = new PBLCanvas(capabilities, WIDTH, HEIGHT);
		JFrame frame = new JFrame("Mini JOGL Demo (breed)");
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		canvas.requestFocus();
	}
}
