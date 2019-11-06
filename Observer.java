import java.util.ArrayList;

class Point {
	protected int x,y;
	protected ArrayList<PointObserver> observerCollection;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.observerCollection = new ArrayList<>();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		if (x != this.x) {
			this.x = x;
			notifyObserver();
		}
	}
	
	public void setY(int y) {
		if (x != this.y) {
			this.y = y;
			notifyObserver();
		}
	}
	
	public void addObserver(PointObserver o) {
		this.observerCollection.add(o);
	}
	
	public void removeObserver(PointObserver o) {
		this.observerCollection.remove(o);
	}
	
	public void notifyObserver() {
		for(PointObserver o : this.observerCollection) {
			o.update();
		}
	}
}

interface PointObserver {
	public void update();
}

class CoordinateObserver implements PointObserver {
	protected boolean x,y;
	protected Point p;
	
	public CoordinateObserver(Point p, boolean x, boolean y) {
		super();
		this.x = x;
		this.y = y;
		this.p = p;
	}
	public void update() {
		if(x)
			System.out.println("X: " + p.getX());
		if(y)
			System.out.println("Y: " +p.getY());
	}
}

public class Main {
	public static void main(String args[]) {
		Point p = new Point(1,2);
		CoordinateObserver observer = new CoordinateObserver(p, true, false);
		CoordinateObserver observer2 = new CoordinateObserver(p, false, true);
		CoordinateObserver observer3 = new CoordinateObserver(p, true, true);
		p.addObserver(observer);
		p.addObserver(observer3);
		p.setY(5);
		p.setX(2);
		p.addObserver(observer2);
		p.setY(10);
		p.setX(6);
		p.removeObserver(observer);
		p.setX(20);
	}
}
