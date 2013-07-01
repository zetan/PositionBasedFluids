package particlesystem;


import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUtessellator;

import com.sun.opengl.util.GLUT;

import util.Vector3D;

public class Particle {
	private Vector3D pos;
	private Vector3D posStar; // a temp pos before applying constaints
	private Vector3D velocity;
	private Vector3D force;
	private float mass;
	
	public Particle(){}

	public Particle(Vector3D pos, float mass) {
		this.pos = pos;
		this.mass = mass;
		velocity = new Vector3D(0,0,0);
		force = new Vector3D(0,0,0);
		posStar = new Vector3D(0,0,0);
	}

	public void Draw(GL gl, GLU glu, GLUT glut){
		gl.glPushMatrix();
		gl.glTranslatef(pos.x, pos.y, pos.z);
		glut.glutSolidSphere(0.5, 8, 8);
	//	gl.glTranslatef(-pos.x, -pos.y, -pos.z);
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


	public Vector3D getPosStar() {
		return posStar;
	}

	public void setPosStar(Vector3D posStar) {
		this.posStar = posStar;
	}

	public Vector3D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3D velocity) {
		this.velocity = velocity;
	}

	public Vector3D getForce() {
		return force;
	}

	public void setForce(Vector3D force) {
		this.force = force;
	}
	
	
}
