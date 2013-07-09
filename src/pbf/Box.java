package pbf;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

import particlesystem.Particle;
import util.Vector3D;

public class Box {
	private float left;
	private float right;
	private float top;
	private float bottom;
	private float front;
	private float back;

	private int time = 0;

	public Box(float left, float right, float bottom, float top, float back,
			float front) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
		this.front = front;
		this.back = back;
	}

	public boolean isInBox(Vector3D position) {
		if (position.x < left || position.x > right)
			return false;
		if (position.y < bottom || position.y > top)
			return false;
		if (position.z < back || position.z > front)
			return false;
		return true;
	}

	public void ForceInsideBox(Vector3D position, Vector3D velocity) {
		float padding = 0.01f;
		if (position.x < left + padding) {
			position.x = left + padding;
			if (velocity != null && velocity.x < 0)
				velocity.x *= -0.5;
		}
		if (position.x > right - padding) {
			position.x = right - padding;
			if (velocity != null && velocity.x > 0)
				velocity.x *= -0.5;
		}
		if (position.y < bottom + padding) {
			position.y = bottom + padding;
			if (velocity != null && velocity.y < 0)
				velocity.y *= -0.5;
		}
		if (position.y > top - padding) {
			position.y = top - padding;
			if (velocity != null && velocity.y > 0)
				velocity.y *= -0.5;
		}
		if (position.z < back + padding) {
			position.z = back + padding;
		}
		if (position.z > front - padding) {
			position.z = front - padding;
		}
	}

	public void ForceOutsideBox(Vector3D position, Vector3D velocity) {
		float padding = 0.01f;
		float midX = (left + right) / 2;
		float midY = (bottom + top) / 2;
		float midZ = (back + front) / 2;
		if (position.x > left && position.x < midX) {
			position.x = left - padding;
			if (velocity != null && velocity.x > 0)
				velocity.x *= -0.5;
		}
		if (position.x < right && position.x >= midX) {
			position.x = right + padding;
			if (velocity != null && velocity.x < 0)
				velocity.x *= -0.5;
		}
		/*
		 * if(position.y > bottom && position.y < midY){ position.y = bottom-
		 * padding; } if(position.y < top && position.y > midY){ position.y =
		 * top + padding; } if(position.z > back && position.z < midZ){
		 * position.z = back - padding; } if(position.z < front && position.z >
		 * midZ){ position.z = front + padding; }
		 */
	}

	public void Draw(GL gl, GLU glu, GLUT glut) {
		float mat_ambient[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float mat_diffuse[] = { 0.4f, 0.4f, 1.0f, 1.0f };
		float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float mat_shininess[] = { 50.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess, 0);
		gl.glLineWidth(1.0f);

		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(left, top, front);
		gl.glVertex3f(left, bottom, front);
		gl.glVertex3f(right, bottom, front);
		gl.glVertex3f(right, top, front);
		gl.glVertex3f(left, top, front);
		gl.glEnd();

		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glVertex3f(left, top, back);
		gl.glVertex3f(left, bottom, back);
		gl.glVertex3f(right, bottom, back);
		gl.glVertex3f(right, top, back);
		gl.glVertex3f(left, top, back);
		gl.glEnd();

		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(left, top, back);
		gl.glVertex3f(left, top, front);
		gl.glEnd();

		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(right, top, back);
		gl.glVertex3f(right, top, front);
		gl.glEnd();

		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(left, bottom, back);
		gl.glVertex3f(left, bottom, front);
		gl.glEnd();

		gl.glBegin(gl.GL_LINES);
		gl.glVertex3f(right, bottom, back);
		gl.glVertex3f(right, bottom, front);
		gl.glEnd();

	}

	public void DrawSolid(GL gl, GLU glu, GLUT glut) {
		float mat_ambient[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float mat_diffuse[] = { 0.4f, 0.4f, 1.0f, 1.0f };
		float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float mat_shininess[] = { 50.0f };
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, mat_shininess, 0);

		gl.glBegin(GL.GL_QUADS);
		// front
		gl.glVertex3f(left, top, front);
		gl.glVertex3f(left, bottom, front);
		gl.glVertex3f(right, bottom, front);
		gl.glVertex3f(right, top, front);

		// bottom
		gl.glVertex3f(left, bottom, front);
		gl.glVertex3f(right, bottom, front);
		gl.glVertex3f(right, bottom, back);
		gl.glVertex3f(left, bottom, back);

		// back
		gl.glVertex3f(right, top, back);
		gl.glVertex3f(right, bottom, back);
		gl.glVertex3f(left, bottom, back);
		gl.glVertex3f(left, top, back);

		// top
		gl.glVertex3f(left, top, front);
		gl.glVertex3f(right, top, front);
		gl.glVertex3f(right, top, back);
		gl.glVertex3f(left, top, back);

		// left
		gl.glVertex3f(left, top, front);
		gl.glVertex3f(left, bottom, front);
		gl.glVertex3f(left, bottom, back);
		gl.glVertex3f(left, top, back);

		// right
		gl.glVertex3f(right, top, front);
		gl.glVertex3f(right, bottom, front);
		gl.glVertex3f(right, bottom, back);
		gl.glVertex3f(right, top, back);

		gl.glEnd();
	}

	public int getWidth() {
		return (int) (right - left);
	}

	public int getHeight() {
		return (int) (top - bottom);
	}

	public int getDepth() {
		return (int) (front - back);
	}

	public void UpdateLeftSide() {
		if (time >= 300) {
			if (((int) (time / 100)) % 2 == 1) {
				left += 0.3;
			} else
				left -= 0.3;
		}

		time++;
	}

	public void Move() {
		/*
		 * if (time >= 0) { if (((int) (time / 50)) % 2 == 0) { left += 0.6f;
		 * right += 0.6f; } else{ left -= 0.6; right -= 0.6; }
		 * 
		 * }
		 */
		int mode = ((int) (time / 15)) % 8;
		if (mode == 4 || mode == 6) {
			left += 0.8f;
			right += 0.8f;
			front += 0.3f;
			back += 0.3f;
		}
		if (mode == 5 || mode == 7) {
			left += 0.8f;
			right += 0.8f;
			front -= 0.3f;
			back -= 0.3f;
		}

		if (mode == 0 || mode == 2) {
			left -= 0.8f;
			right -= 0.8f;
			front += 0.3f;
			back += 0.3f;
		}
		if (mode == 1 || mode == 3) {
			left -= 0.8f;
			right -= 0.8f;
			front -= 0.3f;
			back -= 0.3f;
		}
		time++;
	}
}
