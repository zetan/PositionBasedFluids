package particlesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import pbf.Box;
import pbf.Force;
import pbf.Gravity;
import pbf.PBF;

import sun.security.krb5.KdcComm;
import util.Vector3D;

import com.sun.opengl.util.GLUT;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class ParticleSystem {
	List<Particle> particles = new ArrayList<Particle>();
	PBF pbf = new PBF();
	Box box = new Box(0, 80, 0, 100, 0, 10);
	Box stick = new Box(62, 67, 0, 30, 1, 5);
	CellCube cellCube;
	

	Random random = new Random();
	public ParticleSystem() {
		// TODO Auto-generated constructor stub
		Random random = new Random();
		for(int i = 0; i <= 60; i+=1)
			for(int j = 0; j < 15; j++)
				for(int k = 0; k <= 5; k++){
					particles.add(new Particle(new Vector3D(i, j, k), 1));
				}
		
	/*	for(int i = 20; i <= 30; i+=1)
			for(int j = 30; j < 60; j++)
				for(int k = 3; k <= 7; k++){
					Particle particle = new Particle(new Vector3D(i, j, k), 1);
					particle.setVelocity(new Vector3D(0, -10, 0));
					particles.add(particle);
				}
	*/	
	/*	for(int i = 20; i <= 30; i+=1)
			for(int j = 80; j < 100; j++)
				for(int k = 2; k <= 8; k++){
					particles.add(new Particle(new Vector3D(i, j, k), 1));
				}
		*/
		//particles.add(new Particle(new Vector3D(40, 1, 3), 1));
		cellCube = new CellCube(box.getWidth(), box.getHeight(), box.getDepth());
		//box = new Box(0, 60, 0, 100, 0, 10);
		
	}
	
	public void Update(){
		pbf.UpdateFluid(this);
		//box.UpdateLeftSide();
		stick.Move();
	}

	public void ApplyExternelForce(){
		//clear force
		for(Particle particle: particles){
			particle.getForce().set(0, 0, 0);
		}
		Gravity.ApplyForce(particles);
	}
	
	public void CollisionWithBox(){
		for(Particle particle:particles){
			if(box.isInBox(particle.getPosStar()) == false) {
				box.ForceInsideBox(particle.getPosStar(), particle.getVelocity());
			}
			if(stick.isInBox(particle.getPosStar()) == true){
				stick.ForceOutsideBox(particle.getPosStar(), particle.getVelocity());
			}
		}
	}
	
	public void Draw(GL gl, GLU glu, GLUT glut) {
		//particle mat, init once
		float mat_ambient[] = { 0.7f, 0.7f, 0.7f, 1.0f };
		float mat_diffuse[] = { 0.4f, 0.4f, 1.0f, 1.0f};
		float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float mat_shininess[] = {50.0f};
	   // gl.glTranslatef(-3.75f, 0.0f, 0.0f);
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse, 0);
	    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess, 0);
	    for(Particle particle: particles) {
	    	//if(random.nextDouble() > 0.8)
	    		particle.Draw(gl, glu, glut);
	    }
	   // particles.get(0).Draw(gl, glu, glut);
	    box.Draw(gl, glu, glut);
	    stick.DrawSolid(gl, glu, glut);
	}

	public void FitIntoCells(){
		cellCube.FitIntoCells(particles, box);
	}
	
	public void UpdateNeighbours(){
		for(Particle particle : particles){
			particle.setNeighbours(cellCube.getNeighbours(particle));
		}
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public void setParticles(List<Particle> particles) {
		this.particles = particles;
	}
	
}
