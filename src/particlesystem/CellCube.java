package particlesystem;

import java.util.ArrayList;
import java.util.List;

import javax.swing.border.Border;

import pbf.Box;

import util.Vector3D;

import com.sun.swing.internal.plaf.basic.resources.basic;

public class CellCube {
	Cell neighourhood[][][];
	int width;
	int height;
	int depth;
	public CellCube(int width, int height, int depth){
		this.width = width;
		this.height = height;
		this.depth = depth;
		neighourhood = new Cell[width][height][depth];
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				for(int k = 0; k < depth; k++)
					neighourhood[i][j][k] = new Cell();
		//set up neighbours
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				for(int k = 0; k < depth; k++){
					for(int a = -1; a <=1; a++)
						for(int b = -1; b <= 1; b++)
							for(int c = -1; c <= 1; c++){
								if(i+a < 0 || i + a >= width
										|| j + b < 0 || j + b >= height
										|| k + c < 0 || k + c >= depth) continue;
								neighourhood[i][j][k].AddNeighbour(neighourhood[i+a][j+b][k+c]); //include itself
							}
				}
	}
	
	private void Clear(){
		for(int i = 0; i < width; i++)
			for(int j = 0; j < height; j++)
				for(int k = 0; k < depth; k++){
					Cell cell = neighourhood[i][j][k];
					cell.getParticles().clear();
				}
	}
	
	public void FitIntoCells(List<Particle> particles, Box box){
		Clear();
		for(Particle particle: particles){
			try {
				Vector3D pos = particle.getPosStar();
				Cell cell = neighourhood[(int)pos.x][(int)pos.y][(int)pos.z];
				cell.AddParticle(particle);
				particle.setCell(cell);
			} catch (Exception e) {	//out of index
				Vector3D posClone = particle.getPosStar().Clone();
				box.ForceInsideBox(posClone);
				Cell cell = neighourhood[(int)posClone.x][(int)posClone.y][(int)posClone.z];
				cell.AddParticle(particle);
				particle.setCell(cell);				
			}
		}
	}
	
	public List<Particle> getNeighbours(Particle particle){
		List<Particle> neighList = new ArrayList<Particle>();
		List<Cell> neighbourCells = particle.getCell().getNeighbours();
		for(Cell cell : neighbourCells){
			if(cell.getParticles().size() > 0){
				neighList.addAll(cell.getParticles());
			}
			
		}
		return neighList;
	}
	
}
