
package application.simplex;

import java.util.ArrayList;
import java.util.List;

import application.Arc;
import application.Graphe;
import application.Noeud;

public class Arbre {

	/**
	 * Donne l'arbre recouvrant d'un graphe, c'est à dire un arbre reliant tous les noeuds du graphe
	 * autour d'un même noeud central
	 * @param indNoeud	noeud choisit comme 'center' du graphe
	 * @param g			graphe à traiterr
	 * @return l'arbre recouvrant correspondant
	 */
	static Graphe arbreRecouvrant(final int indNoeud, final Graphe g) {
		// Variables
		List<Arc> ancArcs = null;		// anciens arcs
		ArrayList<Arc> arcs = null;		// nouveaux arcs
		List<Noeud> noeuds = null;		// noeuds
		int a = 0;						// indice (arcs)
		boolean trouv = false;			// booléen

		// Initialisation
		ancArcs = g.getArcs();
		arcs = new ArrayList<Arc>();
		noeuds = g.getNoeuds();
		
		// On parcourt tous les noeuds... sauf celui choisit comme centre
		for (int n = 0; n < noeuds.size(); ++n) {
			if (n != indNoeud) {
				// Et on regarde s'il existe un arc les reliant au centre
				a = 0;
				trouv = false;
				while (a < ancArcs.size() && trouv == false) {
					Arc arc = ancArcs.get(a);
					if ((arc.getSrc() == n && arc.getDest() == indNoeud) || (arc.getSrc() == indNoeud && arc.getDest() == n)) {
						arcs.add(new Arc(arc.getSrc(), arc.getDest(), arc.getCapacité()));
						trouv = true;
					} else
						a++;
				}
			}
		}
		//  On crée le nouveau graphe
		return new Graphe(noeuds, arcs);
	}
	
	
	/**
	 * Modifie le graphe pour ajouter des arcs virtuels.
	 * Les coûts des arcs réels sont positionnés à 0, et ceux des virtuels à 1.
	 * @param indNoeud		noeud à utiliser comme centre
	 * @param g				Graphe à modifier
	 * @return le nouveau graphe
	 */
	static Graphe ajouterArcsVirtuels(final int indNoeud, final Graphe g) {
		// Variables
		List<Arc> ancArcs = null;		// anciens arcs
		ArrayList<Arc> arcs = null;		// nouveaux arcs
		List<Noeud> noeuds = null;		// noeuds
		int a = 0;						// indice (arcs)
		boolean trouv = false;			// booléen
		Noeud curNoeud = null;			// noeud en cours
		
		// Initialisation
		ancArcs = g.getArcs();
		arcs = new ArrayList<Arc>();
		noeuds = g.getNoeuds();
		
		// On ajoute tous les arcs existants avec un poids de 0
		for (a = 0; a < ancArcs.size(); ++a)
			arcs.add(new Arc(ancArcs.get(a).getSrc(), ancArcs.get(a).getDest(), 0));
		
		// On parcourt tous les noeuds... sauf celui choisit comme centre
		for (int n = 0; n < noeuds.size(); ++n) {
			if (n != indNoeud) {
				curNoeud = noeuds.get(n);

				// Et on regarde s'il existe un arc les reliant au centre
				a = 0;
				trouv = false;
				while (a < ancArcs.size() && trouv == false) {
					Arc arc = ancArcs.get(a);
					if (   (arc.getSrc() == n && arc.getDest() == indNoeud && curNoeud.getCapacité() < 0)
						|| (arc.getSrc() == indNoeud && arc.getDest() == n && curNoeud.getCapacité() >= 0)) {
						trouv = true;
					} else
						a++;
				}
				// Si non, on ajoute un arc avec un poids de 1
				if (trouv == false) {
					Arc newArc = null;
					newArc = (curNoeud.getCapacité() < 0)
						? new Arc(n, indNoeud, 1)
						: new Arc(indNoeud, n, 1);
					arcs.add(newArc);
				}
			}
		}
		//  On crée le nouveau graphe
		return new Graphe(noeuds, arcs);
	}

	
	/**
	 * Donne la liste des arcs virtuels restants dans un arbre après l'application du Simplex
	 * @param gSrc			grahe de départ
	 * @param gModif		graphe modifié
	 * @return la liste des arcs virtuels
	 */
	static List<Arc> arcsVirtuelsRestants(final Graphe gSrc, final Graphe gModif) {
		// Variables
		ArrayList<Arc> virtuels = new ArrayList<Arc>();		// arcs virtuels
		List srcArcs = gSrc.getArcs();						// arcs source
		
		// On parcourt tous les arcs du graphe modifié
		for (Arc arc : gModif.getArcs()) {
			// On regarde pour chacun d'entre eux s'il est virtuel ou non
			if (! srcArcs.contains(arc))
				virtuels.add(arc);
		}
		return virtuels;
	}
	
	
	/**
	 * Sépare l'arbre donné en 2 sous-arbres indépendants, tel que pour un arc virtuel u-->v,
	 * on ait au final :
	 *  R = { k / Yk <= Yu }
	 *  S = { k / Yk >  Yu }
	 * @param g			arbre à découper
	 * @param virtuels	liste des arcs virtuels disponibles
	 * @return les seux arbres coupés
	 */
	static List<Graphe> séparerArbresIndépendants(final Graphe g, final List<Arc> virtuels) {
		// Variables
		ArrayList<Graphe> resu = null;		// résultat
		List<Noeud> srcNoeuds = null;		// liste des noeuds source
		List<Arc> srcArcs= null;			// liste des arcs du grapge source
		ArrayList<Noeud> noeudsR = null;	// liste des noeuds de l'ensemble R
		ArrayList<Noeud> noeudsS = null;	// liste des noeuds de l'ensemble S
		ArrayList<Arc> arcsR = null;		// liste des arcs de l'ensemble R
		ArrayList<Arc> arcsS = null;		// liste des arcs de l'ensemble S
		
		// Detection d'erreurs
		if (g == null || virtuels == null || virtuels.isEmpty())
			throw new IllegalArgumentException("Les paramètres ne peuvent être nulls ou vides");
		
		// Initialisation
		resu = new ArrayList<Graphe>();
		srcNoeuds = g.getClonedNoeuds();
		srcArcs = g.getArcs();
		noeudsR = new ArrayList<Noeud>();
		noeudsS = new ArrayList<Noeud>();
		arcsR = new ArrayList<Arc>();
		arcsS = new ArrayList<Arc>();
		
		// On ne travaille qu'avec le premier arc virtuel trouvé
		Arc virtuel = virtuels.get(0);
		
		// On sépare les noeuds en 2 groupes
		int Yu = srcNoeuds.get(virtuel.getSrc()).getCapacité();
		for (Noeud n : srcNoeuds) {
			if (n.getCapacité() <= Yu)
				noeudsR.add(n);
			else
				noeudsS.add(n);
		}
		
		// On déplace également les arcs (en omettant l'arc virtuel) 
		for (Arc arc : srcArcs) {
			if (! arc.equals(virtuel)) {
				Noeud u = srcNoeuds.get(arc.getSrc());
				Noeud v = srcNoeuds.get(arc.getDest());
				// On pense à mettre a jour les indices des noeuds pour le nouveau tableau
				if (noeudsR.contains(u))
					arcsR.add(new Arc(noeudsR.indexOf(u), noeudsR.indexOf(v), arc.getCapacité()));
				else
					arcsS.add(new Arc(noeudsS.indexOf(u), noeudsS.indexOf(v), arc.getCapacité()));
			}
		}
		
		// On crée les 2 graphes, et on les retourne
		resu.add(new Graphe(noeudsR, arcsR));
		resu.add(new Graphe(noeudsS, arcsS));
		return resu;
	}


	/**
	 * Remet les poids de départ dans les noeuds de curGraphe.
	 * @param g				graphe source
	 * @param curGraphe		ENTREE/SORTIE : graphe où recopier les valeurs des noeuds
	 */
	static void reassignerNoeuds(final Graphe g, Graphe curGraphe) {
		// Variables
		List<Noeud> noeuds = g.getNoeuds();
		
		for (Noeud n : curGraphe.getNoeuds()) {
			int capacité = noeuds.get(noeuds.indexOf(n)).getCapacité();
			n.setCapacité(capacité);
		}
	}
	
	
	/**
	 * Donne le coût total d'une solution (somme des Cij * Xij)
	 * @param gSrc		graphe de départ
	 * @param gResu		graphe qui a été obtenu
	 * @return le coût total
	 */
	public static int donneCoutTotal(final Graphe gSrc, final Graphe gResu) {
		// Variables
		int total = 0;					// cout total
		List<Arc> srcArcs = null;		// arcs de départ
		List<Noeud> srcNoeuds = null;	// noeuds de départ
		List<Noeud> resuNoeuds = null;	// noeuds de la solution trouvée
				
		// Initialisation
		srcArcs = gSrc.getArcs();
		srcNoeuds = gSrc.getNoeuds();
		resuNoeuds = gResu.getNoeuds();
		
		// On somme tous les arcs
		for (Arc arc : gResu.getArcs()) {
			int indSrc = srcNoeuds.indexOf(resuNoeuds.get(arc.getSrc()));
			int indDest= srcNoeuds.indexOf(resuNoeuds.get(arc.getDest()));
			Arc arcToFind = new Arc(indSrc, indDest, arc.getCapacité());
			total += arcToFind.getCapacité() * srcArcs.get(srcArcs.indexOf(arcToFind)).getCapacité();
		}
		return total;
	}
}
