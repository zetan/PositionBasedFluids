package util;

import javax.swing.text.Position;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

public class Vector3D {
	public float x;
	public float y;
	public float z;

	public Vector3D() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3D Clone(){
		return new Vector3D(x, y, z);
	}
	
	public static Vector3D LinearSum(Vector3D[] vecs, float[] coes) {
		Vector3D newVec = new Vector3D();
		for (int i = 0; i < vecs.length; i++) {
			newVec.x += vecs[i].x * coes[i];
			newVec.y += vecs[i].y * coes[i];
			newVec.z += vecs[i].z * coes[i];
		}
		return newVec;
	}
	
	public float Length() {
		float length = x * x + y * y + z * z;
		return (float) Math.sqrt(length);
	}
	
	public float LengthSquare(){
		return  x * x + y * y + z * z;
	}
	
	/* static method, return a new one */
	public static Vector3D Normalize(Vector3D vec){
		float length = vec.Length();
		if(length != 0){
			return new Vector3D(vec.x / length, vec.y / length, vec.z / length);
		}else{
			return vec;
		}
	}
	
	public static Vector3D Multiply(Vector3D vec, float coe) {
		return new Vector3D(vec.x * coe, vec.y * coe, vec.z * coe);
	}

	public static Vector3D Add(Vector3D a, Vector3D b) {
		return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Vector3D Substract(Vector3D a, Vector3D b) {
		return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
	}

	public static float DotProduct(Vector3D a, Vector3D b) {
		float product = a.x * b.x + a.y * b.y + a.z * b.z;
		return product;
	}
	public static Vector3D Scale(Vector3D vec, float scale){
		return new Vector3D(vec.x * scale, vec.y * scale, vec.z * scale);
	}
	
	/* non-static method, modify itself, more time-saving */
	public void Add(Vector3D vec){
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}
	public void Substract(Vector3D vec){
		this.x -= vec.x;
		this.y -= vec.y;
		this.z -= vec.z;
	}
	public void Scale(float scale){
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}
	
	public static Vector3D CrossProduct(Vector3D a, Vector3D b){
		float x = a.y * b.z - a.z * b.y;
		float y = a.z * b.x - a.x * b.z;
		float z = a.x * b.y - a.y * b.x;
		return new Vector3D(x, y, z);
	}
	
	public static float cos(Vector3D a, Vector3D b) {
		float product = Vector3D.DotProduct(a, b);
		float lengthA = a.Length();
		float lengthB = b.Length();
		if (lengthA == 0 || lengthB == 0) {
			return -9999;
		} else {
			return product / lengthA / lengthB;
		}
	}

	
	
	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public void Print(String description){
		System.out.println(description + "= " + x + "\t" + y + "\t" + z);
		
	}
}
