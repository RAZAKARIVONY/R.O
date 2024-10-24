
package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Graphe {
	
	private final ArrayList<Arc> tArcs;
	private final ArrayList<Noeud> tNoeuds;

        
	public Graphe(final List<Noeud> noeuds, final List<Arc> arcs) {
		
		// Detection des erreurs
		if (arcs == null || noeuds == null || arcs.size() == 0 || noeuds.size() == 0)
			throw new IllegalArgumentException("Agruments null ou vides !");

		// On initialise les arcs et les noeuds
		tArcs = new ArrayList<Arc>(arcs.size());
		for (Arc arc : arcs)
			this.tArcs.add(arc);
		tNoeuds = new ArrayList<Noeud>(noeuds.size());
		for (Noeud noeud : noeuds)
			this.tNoeuds.add(noeud);
	}
	
	public List<Noeud> getNoeuds() {
		return Collections.unmodifiableList(tNoeuds);
	}
	
	public List<Arc> getArcs() {
		return Collections.unmodifiableList(tArcs);
	}
	
	public Noeud getNoeud(int index){
		return tNoeuds.get(index);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Arc> getClonedArcs() {
		ArrayList<Arc> dest = new ArrayList<Arc>();
		for (Arc arc : tArcs)
			dest.add(arc.clone());
		return dest;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Noeud> getClonedNoeuds() {
		ArrayList<Noeud> dest = new ArrayList<Noeud>();
		for (Noeud noeud : tNoeuds)
			dest.add(noeud.clone());
		return dest;
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder("Graphe :\n");
		sb.append("  Noeuds : ").append(Arrays.asList(tNoeuds).toString()).append("\n");
		sb.append("  Arcs   : ");
		for (Arc arc: tArcs)
			sb.append(tNoeuds.get(arc.getSrc()).getNom())
			  .append("-->")
			  .append(tNoeuds.get(arc.getDest()).getNom())
			  .append(" (").append(arc.getCapacité()).append("), ");
		if (tArcs.size() > 0)
		sb.delete(sb.length()-2, sb.length());

		return sb.toString();
	}
}
