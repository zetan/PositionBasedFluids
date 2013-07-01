package pbf;

import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

import particlesystem.Particle;
import util.Vector3D;

public class Gravity extends Force{
	public static final Vector3D GRAVITY_VECTOR3D = new Vector3D(0, -10, 0);
	public static void ApplyForce(List<Particle> particles){
		for(Particle particle:particles)
			particle.getForce().Add(GRAVITY_VECTOR3D);
	}
}
