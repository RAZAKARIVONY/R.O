
package application;

import java.awt.Point;

public class Noeud implements Cloneable {

	private final String nom;
	private int capacité;
	private Point position;
	
	public Noeud(final String nom, final int capacité, Point position) {
		this.nom = nom;
		this.capacité = capacité;
                this.position = position;
	}
	
	public int getCapacité() {
		return capacité;
	}
	
	public void setCapacité(final int capacité) {
		this.capacité = capacité;
	}

        public Point getPosition() {
            return position;
        }

        public void setPosition(Point position) {
            this.position = position;
        }
        
	public String getNom() {
		return nom;
	}
	
	@Override public Noeud clone() {
		try {
			return (Noeud)super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public @Override boolean equals(final Object o) {
            if(!(o instanceof Noeud))
                return false;
            Noeud n = (Noeud)o;
            return n.nom.equals(nom);
        }
	
	@Override public String toString() {
		StringBuilder sb = new StringBuilder(nom);
		sb.append("(").append(capacité).append(")");
		return sb.toString();
	}
	
}
