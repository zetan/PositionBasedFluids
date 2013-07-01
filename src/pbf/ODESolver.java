package pbf;

import java.util.List;

import particlesystem.Particle;
import util.Vector3D;

public class ODESolver {
	//apply euler ode solver to particle system, 
	//and save the newest pos in posStar
	public static void EulerToStar(List<Particle> particles){
		float t = 0.2f;
		for(Particle particle : particles){
			particle.getVelocity().Add(Vector3D.Scale(particle.getForce(), t));
			particle.setPosStar(Vector3D.Add(particle.getPos(), Vector3D.Scale(particle.getVelocity(), t)));
		}
	}
	
	
}
