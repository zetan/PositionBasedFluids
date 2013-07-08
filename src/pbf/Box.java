package pbf;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

import particlesystem.Particle;
import util.Vector3D;

public class Box {
	private float left;
	private float right;
	private float top;
	private float bottom;
	private float front;
	private float back;
	


	public Box(float left, float right,  float bottom, float top,
			float back,float front) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.front = front;
		this.back = back;
	}
	
	public boolean isInBox(Vector3D position){
		if(position.x < left || position.x > right) return false;
		if(position.y < bottom || position.y > top) return false;
		if(position.z < back || position.z > front) return false;
		return true;
	}
	
	public void ForceInsideBox(Vector3D position){
		float padding = 0.1f;
		if(position.x < left) position.x = left + padding;
		if(position.x > right) position.x = right - padding;
		if(position.y < bottom) position.y = bottom + padding;
		if(position.y > top) position.y = top - padding;
		if(position.z < back) position.z = back + padding;
		if(position.z > front) position.z = front - padding;
	}
	
	public void Draw(GL gl, GLU glu, GLUT glut){
		float mat_ambient[] =  {1.0f, 1.0f, 1.0f, 1.0f};
		float mat_diffuse[] = { 0.4f, 0.4f, 1.0f, 1.0f};
		float mat_specular[] = {1.0f, 1.0f, 1.0f, 1.0f};  
		float mat_shininess[] = {50.0f};
		 gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient, 0);
		 gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR,mat_specular, 0);  
		gl.glMaterialfv(GL.GL_FRONT,GL.GL_SHININESS,mat_shininess, 0);
		gl.glLineWidth(1.0f);
		
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(left, top, front);
		gl.glVertex3f(left, bottom, front);
		gl.glVertex3f(right, bottom, front);
		gl.glVertex3f(right, top, front);
		gl.glVertex3f(left, top, front);
		gl.glEnd();
		
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(left, top, back);
		gl.glVertex3f(left, bottom, back);
		gl.glVertex3f(right, bottom, back);
		gl.glVertex3f(right, top, back);
		gl.glVertex3f(left, top, back);
		gl.glEnd();
		
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(left, top, back);
		gl.glVertex3f(left, top, front);
		gl.glEnd();
		
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(right, top, back);
		gl.glVertex3f(right, top, front);
		gl.glEnd();
		
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(left, bottom, back);
		gl.glVertex3f(left, bottom, front);
		gl.glEnd();
		
		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(right, bottom, back);
		gl.glVertex3f(right, bottom, front);
		gl.glEnd();
		
	}
	
	public int getWidth(){return (int) (right - left);}
	public int getHeight(){return (int)(top - bottom);}
	public int getDepth(){return (int)(front - back);}
}
