package pbf;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import particlesystem.CellCube;
import particlesystem.Particle;
import particlesystem.ParticleSystem;
import util.Vector3D;

public class PBF {
	
	public static final float H_H = 2.25f;
	public static final float KPOLY = (float) (315 / (64.0 * Math.PI * Math.pow(Math.sqrt(H_H), 9)));
	public static final float REST_DENSITY = 0.90f;
	public static final float ARTI_PRESSUER = (float)(KPOLY * Math.pow(H_H - 0.1*H_H, 3));

	public PBF(){}
	
	// main function, apply the position-based algorithm
	public void UpdateFluid(ParticleSystem particleSystem){
		particleSystem.ApplyExternelForce();
		ODESolver.EulerToStar(particleSystem.getParticles());
		//find neighbours
		long startTime=System.currentTimeMillis();
		particleSystem.FitIntoCells();
		particleSystem.UpdateNeighbours();
		long neighbourEndTime=System.currentTimeMillis();
		// make it align with constraints
		for(int i = 0; i < 3; i++){
			long beginDeltaPos=System.currentTimeMillis();
			for(Particle particle:particleSystem.getParticles()){
				ComputeC(particle);
			}
			for(Particle particle:particleSystem.getParticles()){
				ComputeLamda(particle);
			}
			for(Particle particle:particleSystem.getParticles()){
				ComputeDeltaPos(particle);
			}
			long endDeltaPos=System.currentTimeMillis();
			particleSystem.CollisionWithBox();
			long endCollision=System.currentTimeMillis();
			for(Particle particle:particleSystem.getParticles()){
				particle.getPosStar().Add(particle.getDeltaPos());
			}
			System.out.println("neighbour time = " + (neighbourEndTime - startTime) + "\t delta pos = " + (endDeltaPos - beginDeltaPos) + "\t collistion = " + (endCollision - endDeltaPos));
		}
		
		//v = (posStar-pos) / t
		for(Particle particle : particleSystem.getParticles()){
			particle.setVelocity(Vector3D.Scale(Vector3D.Substract(particle.getPosStar(), particle.getPos()), 1/0.05f));
		}
			
		//apply vorticity confinement and XSPH viscosity
		// pos = posStar
		for(Particle particle : particleSystem.getParticles()){
			Vector3D posStar = particle.getPosStar();
			particle.getPos().set(posStar.x, posStar.y, posStar.z);
		}
	}
	
	
	public void ComputeC(Particle particle){
		//density
		float density = 0;
		List<Particle> neighbours = particle.getNeighbours();
		for(Particle neigh : neighbours){
			density += Kernel(particle.getPosStar(), neigh.getPosStar());
		}
		particle.setDensity(density);
		particle.setConstraint(density / REST_DENSITY -1);
	//	System.out.println("density = " + density);
	}
	
	
	
	public Vector3D ComputeGrandientC(Particle particle, Particle neighbour){
		if(particle.equals(neighbour)){	// k==i
			Vector3D vec = new Vector3D(0,0,0);
			List<Particle> neighbours = particle.getNeighbours();
			for(Particle neigh:neighbours){
				if(! neigh.equals(particle)){
					Vector3D gradient = KernelGradient(particle.getPosStar(), neigh.getPosStar());
					vec.Add(gradient);
				}
			}
			vec.Scale(1 / REST_DENSITY);
			return vec;
			
		}else{	// k == j
			Vector3D gradient = KernelGradient(particle.getPosStar(), neighbour.getPosStar());
			gradient.Scale(-1 / REST_DENSITY);
			return gradient;
		}
	}
	
	public Vector3D KernelGradient(Vector3D pi, Vector3D pj){
		Vector3D dist = Vector3D.Substract(pi, pj);
		float r_r = dist.LengthSquare();
		if(r_r > H_H) return new Vector3D(0,0,0);
		float a = (float) Math.pow(H_H - r_r, 2);
		Vector3D vec = new Vector3D(- 2 * dist.x, - 2 * dist.y, - 2 * dist.z);
		vec.Scale(KPOLY *3* a);
		return vec;
	}
	public float Kernel(Vector3D pi, Vector3D pj){
		float r_r = Vector3D.Substract(pi, pj).LengthSquare();
		if(r_r > H_H) return 0;
		return (float) (KPOLY * Math.pow(H_H - r_r, 3));
	}
	
	
	public void ComputeLamda(Particle particle){
		List<Particle> neighbours = particle.getNeighbours();
		float sumGradient = 0;
		for(Particle neighbour:neighbours){
			Vector3D grad = ComputeGrandientC(particle, neighbour);
			sumGradient += grad.LengthSquare();
		}
		//System.out.println("constraint = " + particle.getConstraint() + "\tsumgradient = " + sumGradient);
		particle.setLamda(particle.getConstraint() / (sumGradient + 10f) * -1);
	}
	
	public void ComputeDeltaPos(Particle particle){
		List<Particle> neighbours = particle.getNeighbours();
		Vector3D delta = new Vector3D(0,0,0);
		for(Particle neigh:neighbours){
			if(neigh.equals(particle) == false){
				Vector3D gradient = KernelGradient(particle.getPosStar(), neigh.getPosStar());
				gradient.Scale(particle.getLamda() + neigh.getLamda()/* + ComputeArtiPressure(particle, neigh)*/);
				delta.Add(gradient);
			}
			
		}
		delta.Scale(1/REST_DENSITY);
		particle.setDeltaPos(delta);
	}
	
	public float ComputeArtiPressure(Particle pi, Particle pj){
		float wpipj = Kernel(pi.getPosStar(), pj.getPosStar());
		
		return (float) (-0.1 * Math.pow(wpipj / ARTI_PRESSUER, 4));
	}
}
