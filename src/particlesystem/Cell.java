package particlesystem;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	List<Particle> particles = new ArrayList<Particle>();
	List<Cell> neighbours = new ArrayList<Cell>();
	public Cell() {
	}

	public void AddParticle(Particle particle) {
		particles.add(particle);
	}

	public void ClearParticle() {
		particles.clear();
	}

	public void AddNeighbour(Cell cell){
		neighbours.add(cell);
	}
	
	
	public List<Cell> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(List<Cell> neighbours) {
		this.neighbours = neighbours;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public void setParticles(List<Particle> particles) {
		this.particles = particles;
	}
	
	
}
