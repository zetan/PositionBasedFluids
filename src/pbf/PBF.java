package pbf;

import particlesystem.Particle;
import particlesystem.ParticleSystem;
import util.Vector3D;

public class PBF {

	public PBF(){}
	
	// main function, apply the position-based algorithm
	public void UpdateFluid(ParticleSystem particleSystem){
		System.out.println("---------start update----------");
		particleSystem.ApplyExternelForce();
		ODESolver.EulerToStar(particleSystem.getParticles());
		System.out.println("---------end euler----------");
		//find neighbours
		// make it align with constraints
		for(int i = 0; i < 1; i++){}
		
		//v = (posStar-pos) / t
		for(Particle particle : particleSystem.getParticles())
			particle.setVelocity(Vector3D.Scale(Vector3D.Substract(particle.getPosStar(), particle.getPos()), 1/0.2f));
		System.out.println("---------v = (posStar-pos) / t----------");
		//apply vorticity confinement and XSPH viscosity
		// pos = posStar
		for(Particle particle : particleSystem.getParticles()){
			Vector3D posStar = particle.getPosStar();
			particle.getPos().set(posStar.x, posStar.y, posStar.z);
		}
		System.out.println("--------pos = posStar----------");
	}
	
}
