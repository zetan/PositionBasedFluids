package pbf;

import particlesystem.Particle;
import particlesystem.ParticleSystem;
import util.Vector3D;

public class PBF {

	public PBF(){}
	
	// main function, apply the position-based algorithm
	public void UpdateFluid(ParticleSystem particleSystem){
		particleSystem.ApplyExternelForce();
		ODESolver.EulerToStar(particleSystem.getParticles());
		//find neighbours
		// make it align with constraints
		for(int i = 0; i < 1; i++){}
		
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
	
}
