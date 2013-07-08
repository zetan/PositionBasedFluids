package pbf;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;

import particlesystem.CellCube;
import particlesystem.Particle;
import particlesystem.ParticleSystem;
import util.Vector3D;

public class PBF {
	
	public static final float H = 3;
	public static final float KPOLY = (float) (315 / (64.0 * Math.PI * Math.pow(H, 9)));
	public static final float REST_DENSITY = 0.33f;

	public PBF(){}
	
	// main function, apply the position-based algorithm
	public void UpdateFluid(ParticleSystem particleSystem){
		particleSystem.ApplyExternelForce();
		ODESolver.EulerToStar(particleSystem.getParticles());
		//find neighbours
		particleSystem.FitIntoCells();
		particleSystem.UpdateNeighbours();
		// make it align with constraints
		for(int i = 0; i < 1; i++){
			for(Particle particle:particleSystem.getParticles()){
				ComputeC(particle);
				ComputeLamda(particle);
			}
			particleSystem.CollisionWithBox();
		}
		
		//v = (posStar-pos) / t
		for(Particle particle : particleSystem.getParticles())
			particle.setVelocity(Vector3D.Scale(Vector3D.Substract(particle.getPosStar(), particle.getPos()), 1/0.05f));
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
			return vec;
			
		}else{	// k == j
			Vector3D gradient = KernelGradient(particle.getPosStar(), neighbour.getPosStar());
			gradient.Scale(-1);
			return gradient;
		}
	}
	
	public Vector3D KernelGradient(Vector3D pi, Vector3D pj){
		Vector3D dist = Vector3D.Substract(pi, pj);
		float r = dist.Length();
		float a = (float) Math.pow(H*H - r*r, 2);
		Vector3D vec = new Vector3D(H*H - 2 * dist.x, H*H - 2 * dist.y, H*H - 2 * dist.z);
		vec.Scale(KPOLY * a);
		return vec;
	}
	public float Kernel(Vector3D pi, Vector3D pj){
		float r = Vector3D.Substract(pi, pj).Length();
		return (float) (KPOLY * Math.pow(H*H - r*r, 3));
	}
	
	public void ComputeLamda(Particle particle){
		List<Particle> neighbours = particle.getNeighbours();
		float sumGradient = 0;
		for(Particle neighbour:neighbours){
			Vector3D grad = ComputeGrandientC(particle, neighbour);
			sumGradient += grad.LengthSquare();
		}
		particle.setLamda(particle.getConstraint() / sumGradient * -1);
	}
}
