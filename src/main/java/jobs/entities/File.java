package jobs.entities;

/**
 * 
 * Representa un File / Archivo.
 *
 */

public class File {
	int id;
	String name;
	String path;
	
	long length;
	
	public long getLength() {
		return length;
	}
	
	public void setLength(long length) {
		this.length = length;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
