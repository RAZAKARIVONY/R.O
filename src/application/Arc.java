
package application;


public class Arc implements Cloneable {
	
	private final int src;
	private final int dest;
	private int capacité;
	
	
	public Arc(final int src, final int dest, final int capacité) {
		this.src = src;
		this.dest = dest;
		this.capacité = capacité;
	}
	
	public int getSrc() {
		return src;
	}
	
	public int getDest() {
		return dest;
	}
	
	public int getCapacité() {
		return capacité;
	}
	
	public void setCapacité(final int capacité) {
		this.capacité = capacité;
	}
	
	public boolean contientNoeud(final int noeud) {
		return (noeud == src || noeud == dest);
	}
	
	public int donneAutreNoeud(final int noeud) {
		return (src == noeud)
			? dest
			: src;
	}

	@Override public Arc clone() {
		try {
			return (Arc)super.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override public boolean equals(final Object o) {
        if(!(o instanceof Arc))
            return false;
            Arc a = (Arc)o;
            return a.src == src && a.dest == dest;
        }
	
	@Override public String toString() {
		StringBuilder sb = new StringBuilder(String.valueOf(src));
		sb.append("-->").append(dest).append(" ");
		sb.append("(").append(capacité).append(")");
		return sb.toString();
	}
}
