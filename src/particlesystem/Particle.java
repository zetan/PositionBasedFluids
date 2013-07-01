package particlesystem;


import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUtessellator;

import com.sun.opengl.util.GLUT;

import util.Vector3D;

public class Particle {
	private Vector3D pos;
	private float mass;
	
	public Particle(){}

	public Particle(Vector3D pos, float mass) {
		this.pos = pos;
		this.mass = mass;
	}

	public void Draw(GL gl, GLU glu, GLUT glut){
		gl.glPushMatrix();
		gl.glTranslatef(pos.x, pos.y, pos.z);
	
		glut.glutSolidSphere(1, 6, 16);
		//glTranslatef(-pos.x, -pos.y, -pos.z);
		gl.glPopMatrix();
	}
	
	

	public Vector3D getPos() {
		return pos;
	}

	public void setPos(Vector3D pos) {
		this.pos = pos;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}
	
	
}
