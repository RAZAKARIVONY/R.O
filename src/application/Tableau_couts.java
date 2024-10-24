package application;

import java.util.ArrayList;
import java.util.Scanner;

public class Tableau_couts {

	private static final int LIGNE = 1;
	private static final int COLONNE = 2;

	private int _tableau_cout_unitaire_transport[][];
	private int _quantites_demandees[];
	private int _quantites_disponibles[];
	private int _quantites_effectivement_transportees[][];

	private int _nb_ligne;
	private int _nb_colonne;
	private int _ligne_colonne;

	private final char _alphabet[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private int _numero_ligne;
	private int _numero_colonne;

	public Tableau_couts(int nb_ligne, int nb_colonne) {
		_nb_ligne = nb_ligne;
		_nb_colonne = nb_colonne;

		this._tableau_cout_unitaire_transport = new int[_nb_ligne][_nb_colonne];
		this._quantites_effectivement_transportees = new int[_nb_ligne][_nb_colonne];
		this._quantites_demandees = new int[_nb_colonne];
		this._quantites_disponibles = new int[_nb_ligne];
	}

	public boolean is_all_zero(int tab[][]) {

		for (int i = 0; i < get_nb_ligne(); i++) {
			for (int j = 0; j < get_nb_colonne(); j++) {

				if (tab[i][j] != 0) {
					return false;
				}

			}
		}
		return true;
	}

	public int[] max_element_tableau(int tab[][]) {
		int max = tab[0][0], max_ligne = 0, max_colonne = 0;
		int tab_max[] = new int[3];

		for (int i = 0; i < get_nb_ligne(); i++) {
			for (int j = 0; j < get_nb_colonne(); j++) {

				if (tab[i][j] > max) {
					max = tab[i][j];
					max_ligne = i;
					max_colonne = j;
				}

			}
		}

		tab_max[0] = max;
		tab_max[1] = max_ligne;
		tab_max[2] = max_colonne;

		return tab_max;
	}

	public int[] min_tableau(int tab[][]) {

		int tab_min[] = new int[3];
		int tab_max[] = max_element_tableau(tab);
		int min = tab_max[0], min_ligne = tab_max[1], min_colonne = tab_max[2];

		for (int i = 0; i < get_nb_ligne(); i++) {
			for (int j = 0; j < get_nb_colonne(); j++) {

				if (tab[i][j] < min && tab[i][j] != 0) {
					min = tab[i][j];
					min_ligne = i;
					min_colonne = j;
				}

			}
		}

		tab_min[0] = min;
		tab_min[1] = min_ligne;
		tab_min[2] = min_colonne;

		return tab_min;

	}

	public void differences_maximales(int tab_temp[][]) {

		int t[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			t[i] = (int[]) tab_temp[i].clone();
		}

		int max_difference_ligne_vertical = 0;
		int max_difference_colonne_horizontal = 0;

		int m = max_element_tableau(t)[0] + 1;

		int tab_difference_ligne[] = new int[get_nb_ligne()];
		int tab_difference_colonne[] = new int[get_nb_colonne()];

		int numero_ligne = 0, numero_colonne = 0;

		// ligne
		for (int i = 0; i < get_nb_ligne(); i++) {

			// Arrays.sort(t[i]);
			//
			// for(int j = 0; j < get_nb_colonne(); j++){
			// if(t[i][j] > 0 && j < get_nb_colonne() - 1){
			// tab_difference_ligne[i] = t[i][j + 1] - t[i][j] ;
			// } else if(t[i][j] > 0 && j == get_nb_colonne() - 1){
			// tab_difference_ligne[i] = t[i][j] ;
			// } else {
			// tab_difference_ligne[i] = 0;
			// }
			// }

			int min_one = max_element_tableau(tab_temp)[0] + 1;
			int min_two = max_element_tableau(tab_temp)[0] + 1;

			int mark_ligne = max_element_tableau(tab_temp)[0] + 1;

			for (int j = 0; j < get_nb_colonne(); j++) {
				if (tab_temp[i][j] < min_one && tab_temp[i][j] != 0) {
					min_one = tab_temp[i][j];
					mark_ligne = j;
				}
			}

			for (int k = 0; k < get_nb_colonne(); k++) {
				if (tab_temp[i][k] < min_two && k != mark_ligne && tab_temp[i][k] != 0) {
					min_two = tab_temp[i][k];
				}
			}

			if ((min_two != m) && (min_one != m)) {
				tab_difference_ligne[i] = min_two - min_one;
			} else if (min_two == m && min_one == m) {
				tab_difference_ligne[i] = 0;
			} else if (min_two == m && min_one != m) {
				tab_difference_ligne[i] = min_one;
			}

			if ((min_two - min_one) > max_difference_ligne_vertical) {
				max_difference_ligne_vertical = min_two - min_one;
				numero_ligne = i;
			}

		}

		int t1[];
		// colonne
		for (int j = 0; j < get_nb_colonne(); j++) {

			// t1 = new int[get_nb_ligne()];
			//
			// for(int k = 0; k < get_nb_ligne(); k++){
			//
			// t1[k] = t[k][j];
			//
			// }
			//
			// Arrays.sort(t1);
			//
			// for(int i = 0; i < get_nb_ligne(); i++){
			//
			// if(t1[i] > 0 && i < get_nb_ligne() - 1){
			// tab_difference_colonne[j] = t1[i + 1] - t1[i] ;
			// } else if(t1[i] > 0 && i == get_nb_ligne() - 1){
			// tab_difference_colonne[j] = t1[i] ;
			// } else {
			// tab_difference_colonne[j] = 0;
			// }
			// }

			int min_one = max_element_tableau(tab_temp)[0] + 1;
			int min_two = max_element_tableau(tab_temp)[0] + 1;

			int mark_colonne = max_element_tableau(tab_temp)[0] + 1;

			for (int i = 0; i < get_nb_ligne(); i++) {
				if (tab_temp[i][j] < min_one && tab_temp[i][j] != 0) {
					min_one = tab_temp[i][j];
					mark_colonne = i;
				}
			}

			for (int k = 0; k < get_nb_ligne(); k++) {
				if (tab_temp[k][j] < min_two && k != mark_colonne && tab_temp[k][j] != 0) {
					min_two = tab_temp[k][j];
				}
			}

			if ((min_two != m) && (min_one != m)) {
				tab_difference_colonne[j] = min_two - min_one;
			} else if (min_two == m && min_one == m) {
				tab_difference_colonne[j] = 0;
			} else if (min_two == m && min_one != m) {
				tab_difference_colonne[j] = min_one;
			}

		}

		max_difference_ligne_vertical = tab_difference_ligne[0];
		for (int i = 0; i < get_nb_ligne(); i++) {
			if (tab_difference_ligne[i] > max_difference_ligne_vertical) {
				max_difference_ligne_vertical = tab_difference_ligne[i];
				numero_ligne = i;
			}
		}

		max_difference_colonne_horizontal = tab_difference_colonne[0];
		for (int i = 0; i < get_nb_colonne(); i++) {
			if (tab_difference_colonne[i] > max_difference_colonne_horizontal) {
				max_difference_colonne_horizontal = tab_difference_colonne[i];
				numero_colonne = i;
			}
		}

		if (max_difference_colonne_horizontal > max_difference_ligne_vertical
				|| max_difference_colonne_horizontal == max_difference_ligne_vertical) {
			set_numero_colonne(numero_colonne);
			set_ligne_colonne(COLONNE);
		} else {
			set_numero_ligne(numero_ligne);
			set_ligne_colonne(LIGNE);
		}

	}
	
	public void entrer_cout(int x, int y){
		
		int valeur;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez le coût unitaire c" + x + y + " > ");
		valeur = sc.nextInt();
		get_tableau_cout_unitaire_transport()[x][y] = valeur;
		sc.close();
	}
	
	public void entrer_qte_disponible(int x){
		
		int valeur;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez la qte disponible : " + x + " > ");
		valeur = sc.nextInt();
		get_quantites_disponibles()[x] = valeur;
		sc.close();
	}
	
	public void entrer_qte_demande(int y){
		
		int valeur;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez la qte demande : " + y + " > ");
		valeur = sc.nextInt();
		get_quantites_demandees()[y] = valeur;
		sc.close();
	}

	public void creer_tableau() {

		Scanner sc = new Scanner(System.in);
		int t[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			for (int j = 0; j < get_nb_colonne(); j++) {

				System.out.print("Entrez le coût unitaire c" + (i + 1) + (j + 1) + " > ");
				t[i][j] = sc.nextInt();
				sc.nextLine();

			}
		}

		for (int j = 0; j < _nb_colonne; j++) {
			System.out.print("Entrez la quantité demandée" + (j + 1) + " > ");
			_quantites_demandees[j] = sc.nextInt();
			sc.nextLine();
		}

		for (int i = 0; i < _nb_ligne; i++) {
			System.out.print("Entrez la quantité disponible" + (i + 1) + " > ");
			_quantites_disponibles[i] = sc.nextInt();
			sc.nextLine();
		}

		set_tableau_cout_unitaire_transport(t);
		sc.close();
	}

	public int valeur_z(int tab[][]) {
		int z = 0;
		int tab_temp[][] = get_tableau_cout_unitaire_transport();

		for (int i = 0; i < get_nb_ligne(); i++) {
			for (int j = 0; j < get_nb_colonne(); j++) {

				if (tab[i][j] != 0) {
					z += (tab[i][j] * tab_temp[i][j]);
				}

			}
		}

		return z;
	}

	public void afficher_tableau(int tab[][], int qte_dispo[], int qte_demande[]) {
		System.out.print(" ");

		for (int i = 0; i < get_nb_colonne(); i++) {
			System.out.print("\t" + (i + 1));
		}

		System.out.println("\n");

		for (int i = 0; i < get_nb_ligne(); i++) {
			System.out.print(_alphabet[i]);
			for (int j = 0; j < get_nb_colonne(); j++) {
				System.out.print("\t" + tab[i][j]);
			}
			System.out.print("\t\t" + qte_dispo[i] + "\n");
		}

		System.out.println("\n ");

		for (int i = 0; i < get_quantites_demandees().length; i++) {
			System.out.print("\t" + qte_demande[i]);
		}

		System.out.println("\n\n");
	}

	public int[][] methode_coin_nord_ouest() {

		int tab_coin_no[][] = new int[get_nb_ligne()][get_nb_colonne()];
		int qte_demande[] = get_quantites_demandees().clone();
		int qte_disponible[] = get_quantites_disponibles().clone();

                int i = 0;
                int j = 0;
                while (j < get_nb_colonne()) {

                        if (i < get_nb_ligne()) {

                                if (qte_demande[j] <= qte_disponible[i]) {

                                        tab_coin_no[i][j] = qte_demande[j];
                                        qte_disponible[i] -= qte_demande[j];
                                        qte_demande[j] = 0;
                                        j++;

                                        afficher_tableau(tab_coin_no, qte_disponible, qte_demande);
                                        System.out.print("\n");

                                } else {

                                        tab_coin_no[i][j] = qte_disponible[i];
                                        qte_demande[j] -= qte_disponible[i];
                                        qte_disponible[i] = 0;
                                        i++;

                                        afficher_tableau(tab_coin_no, qte_disponible, qte_demande);
                                        System.out.print("\n");

                                }

                        }

                }

		return tab_coin_no;
	}

	public int[][] methode_minili() {

		int tab_minili[][] = new int[get_nb_ligne()][get_nb_colonne()];
		int tab_temp[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			tab_temp[i] = (int[]) get_tableau_cout_unitaire_transport()[i].clone();
		}

		int qte_demande[] = get_quantites_demandees().clone();
		int qte_disponible[] = get_quantites_disponibles().clone();

		int min_colonne = 0;

		for (int i = 0; i < get_nb_ligne(); i++) {

			while (qte_disponible[i] > 0) {

				int min = 100;

				for (int k = 0; k < get_nb_colonne(); k++) {
					if (tab_temp[i][k] < min && tab_temp[i][k] != 0) {
						min = tab_temp[i][k];
						min_colonne = k;
					}
				}

				if (qte_demande[min_colonne] < qte_disponible[i]) {

					tab_minili[i][min_colonne] = qte_demande[min_colonne];
					tab_temp[i][min_colonne] = 0;
					qte_disponible[i] -= qte_demande[min_colonne];
					qte_demande[min_colonne] = 0;

					if (i < (get_nb_ligne() - 1)) {
						for (int j = (i + 1); j < get_nb_ligne(); j++) {
							tab_temp[j][min_colonne] = 0;
						}
					}

					afficher_tableau(tab_minili, qte_disponible, qte_demande);
					System.out.print("\n");

				} else {

					tab_minili[i][min_colonne] = qte_disponible[i];
					tab_temp[i][min_colonne] = 0;
					qte_demande[min_colonne] -= qte_disponible[i];
					qte_disponible[i] = 0;

					afficher_tableau(tab_minili, qte_disponible, qte_demande);
					System.out.print("\n");

				}
			}

		}

		return tab_minili;

	}

	public int[][] methode_minico() {

		int tab_minico[][] = new int[get_nb_ligne()][get_nb_colonne()];
		int tab_temp[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			tab_temp[i] = (int[]) get_tableau_cout_unitaire_transport()[i].clone();
		}

		int qte_demande[] = get_quantites_demandees().clone();
		int qte_disponible[] = get_quantites_disponibles().clone();

		int min_ligne = 0;

		for (int i = 0; i < get_nb_colonne(); i++) {

			while (qte_demande[i] > 0) {

				// TODO à dynamiser
				int min = 100;

				for (int k = 0; k < get_nb_ligne(); k++) {
					if (tab_temp[k][i] < min && tab_temp[k][i] != 0) {
						min = tab_temp[k][i];
						min_ligne = k;
					}
				}

				if (qte_disponible[min_ligne] < qte_demande[i]) {

					tab_minico[min_ligne][i] = qte_disponible[min_ligne];
					tab_temp[min_ligne][i] = 0;
					qte_demande[i] -= qte_disponible[min_ligne];
					qte_disponible[min_ligne] = 0;

					if (i < (get_nb_colonne() - 1)) {
						for (int j = (i + 1); j < get_nb_ligne(); j++) {
							tab_temp[min_ligne][j] = 0;
						}
					}

					afficher_tableau(tab_minico, qte_disponible, qte_demande);
					System.out.print("\n");

				} else {

					tab_minico[min_ligne][i] = qte_demande[i];
					tab_temp[min_ligne][i] = 0;
					qte_disponible[min_ligne] -= qte_demande[i];
					qte_demande[i] = 0;

					System.out.print("here");
					afficher_tableau(tab_minico, qte_disponible, qte_demande);
					System.out.print("\n");

				}
			}

		}

		return tab_minico;
	}

	public int[][] methode_minitab() {

		int tab_minitab[][] = new int[get_nb_ligne()][get_nb_colonne()];
		int tab_temp[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			tab_temp[i] = (int[]) get_tableau_cout_unitaire_transport()[i].clone();
		}

		int qte_demande[] = get_quantites_demandees().clone();
		int qte_disponible[] = get_quantites_disponibles().clone();

		int min_ligne = 0, min_colonne = 0;

		while (!is_all_zero(tab_temp)) {
			int min_tab[] = min_tableau(tab_temp);
			min_ligne = min_tab[1];
			min_colonne = min_tab[2];

			System.out.println(min_ligne);
			System.out.println(min_colonne);

			if (qte_disponible[min_ligne] < qte_demande[min_colonne]) {

				tab_minitab[min_ligne][min_colonne] = qte_disponible[min_ligne];
				qte_demande[min_colonne] -= qte_disponible[min_ligne];
				qte_disponible[min_ligne] = 0;

				for (int i = 0; i < get_nb_colonne(); i++) {
					tab_temp[min_ligne][i] = 0;
				}

			} else if (qte_disponible[min_ligne] > qte_demande[min_colonne]) {

				tab_minitab[min_ligne][min_colonne] = qte_demande[min_colonne];
				qte_disponible[min_ligne] -= qte_demande[min_colonne];
				qte_demande[min_colonne] = 0;

				for (int i = 0; i < get_nb_ligne(); i++) {
					tab_temp[i][min_colonne] = 0;
				}
			} else {

				tab_minitab[min_ligne][min_colonne] = qte_demande[min_colonne];
				qte_demande[min_colonne] = 0;
				qte_disponible[min_ligne] = 0;

				for (int i = 0; i < get_nb_ligne(); i++) {
					tab_temp[i][min_colonne] = 0;
					tab_temp[min_ligne][i] = 0;
				}
			}

			afficher_tableau(tab_minitab, qte_disponible, qte_demande);
			System.out.print("\n");

		}

		return tab_minitab;
	}

	public int[][] algorithme_balas_hammer() {

		int tab_algo_ballas_hammer[][] = new int[get_nb_ligne()][get_nb_colonne()];
		int tab_temp[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			tab_temp[i] = (int[]) get_tableau_cout_unitaire_transport()[i].clone();
		}

		int qte_demande[] = get_quantites_demandees().clone();
		int qte_disponible[] = get_quantites_disponibles().clone();

		afficher_tableau(get_tableau_cout_unitaire_transport(), qte_disponible, qte_demande);

		while (!is_all_zero(tab_temp)) {
			// while (t < 10) {

			differences_maximales(tab_temp);

			if (get_ligne_colonne() == LIGNE) {

				int min = max_element_tableau(tab_temp)[0] + 1;
				int min_colonne = 0;

				for (int i = 0; i < get_nb_colonne(); i++) {

					if (tab_temp[get_numero_ligne()][i] < min && tab_temp[get_numero_ligne()][i] != 0) {
						min = tab_temp[get_numero_ligne()][i];
						min_colonne = i;
					}

				}

				if (qte_disponible[get_numero_ligne()] < qte_demande[min_colonne]) {
					tab_algo_ballas_hammer[get_numero_ligne()][min_colonne] = qte_disponible[get_numero_ligne()];
					qte_demande[min_colonne] -= qte_disponible[get_numero_ligne()];
					qte_disponible[get_numero_ligne()] = 0;

					for (int i = 0; i < get_nb_colonne(); i++) {
						tab_temp[get_numero_ligne()][i] = 0;
					}

				} else if (qte_disponible[get_numero_ligne()] > qte_demande[min_colonne]) {
					tab_algo_ballas_hammer[get_numero_ligne()][min_colonne] = qte_demande[min_colonne];
					qte_disponible[get_numero_ligne()] -= qte_demande[min_colonne];
					qte_demande[min_colonne] = 0;

					for (int i = 0; i < get_nb_ligne(); i++) {
						tab_temp[i][min_colonne] = 0;
					}

				} else {
					tab_algo_ballas_hammer[get_numero_ligne()][min_colonne] = qte_demande[min_colonne];
					qte_disponible[get_numero_ligne()] = 0;
					qte_demande[min_colonne] = 0;

					for (int i = 0; i < get_nb_ligne(); i++) {
						tab_temp[i][min_colonne] = 0;
					}

					for (int i = 0; i < get_nb_colonne(); i++) {
						tab_temp[get_numero_ligne()][i] = 0;
					}
				}

				afficher_tableau(tab_temp, qte_disponible, qte_demande);
				afficher_tableau(tab_algo_ballas_hammer, qte_disponible, qte_demande);
				System.out.println("\n");

			} else if (get_ligne_colonne() == COLONNE) {

				int min = max_element_tableau(tab_temp)[0] + 1;
				int min_ligne = 0;

				for (int i = 0; i < get_nb_ligne(); i++) {

					if (tab_temp[i][get_numero_colonne()] < min && tab_temp[i][get_numero_colonne()] != 0) {
						min = tab_temp[i][get_numero_colonne()];
						min_ligne = i;
					}

				}

				if (qte_disponible[min_ligne] < qte_demande[get_numero_colonne()]) {
					tab_algo_ballas_hammer[min_ligne][get_numero_colonne()] = qte_disponible[min_ligne];
					qte_demande[get_numero_colonne()] -= qte_disponible[min_ligne];
					qte_disponible[min_ligne] = 0;

					for (int i = 0; i < get_nb_colonne(); i++) {
						tab_temp[min_ligne][i] = 0;
					}

				} else if (qte_disponible[min_ligne] > qte_demande[get_numero_colonne()]) {
					tab_algo_ballas_hammer[min_ligne][get_numero_colonne()] = qte_demande[get_numero_colonne()];
					qte_disponible[min_ligne] -= qte_demande[get_numero_colonne()];
					qte_demande[get_numero_colonne()] = 0;

					for (int i = 0; i < get_nb_ligne(); i++) {
						tab_temp[i][get_numero_colonne()] = 0;
					}

				} else {
					tab_algo_ballas_hammer[min_ligne][get_numero_colonne()] = qte_demande[get_numero_colonne()];
					qte_disponible[min_ligne] = 0;
					qte_demande[get_numero_colonne()] = 0;

					for (int i = 0; i < get_nb_colonne(); i++) {
						tab_temp[min_ligne][i] = 0;
					}

					for (int i = 0; i < get_nb_ligne(); i++) {
						tab_temp[i][get_numero_colonne()] = 0;
					}

				}

				afficher_tableau(tab_temp, qte_disponible, qte_demande);
				afficher_tableau(tab_algo_ballas_hammer, qte_disponible, qte_demande);
				System.out.println("\n");

			}

			set_numero_ligne(0);
			set_numero_colonne(0);
			set_ligne_colonne(0);
		}

		afficher_tableau(tab_algo_ballas_hammer, qte_disponible, qte_demande);
		afficher_tableau(get_tableau_cout_unitaire_transport(), qte_disponible, qte_demande);
		System.out.println("z : " + valeur_z(tab_algo_ballas_hammer));

		return tab_algo_ballas_hammer;
	}

	public void algorithme_stepping_stone(int qte_eff_trans[][]) {

		int t[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			t[i] = (int[]) qte_eff_trans[i].clone();
		}

		for (int i = 0; i < get_nb_ligne(); i++) {
			for (int j = 0; j < get_nb_colonne(); j++) {

				if (t[i][j] == 0) {

					int c = 0;
					while (c < get_nb_colonne()) {

						if (t[i][c] != 0) {

						}

					}

				}

			}
		}

	}

	public void chemin_a_suivre(int x_ln, int y_cl, int[][] tab) {

		int ln = 0;
		int cl = 0;
		
		ArrayList y = new ArrayList();
		
		int t[][] = new int[get_nb_ligne()][get_nb_colonne()];

		for (int i = 0; i < get_nb_ligne(); i++) {
			t[i] = (int[]) tab[i].clone();
		}

		for(int i = 0; i < get_nb_colonne(); i++){
			
			if(t[x_ln][i] != 0){
				y.add(i);
			}
			
		}
		
	}

	public int[][] get_tableau_cout_unitaire_transport() {
		return _tableau_cout_unitaire_transport;
	}

	public void set_tableau_cout_unitaire_transport(int[][] _tableau_cout_unitaire_transport) {
		this._tableau_cout_unitaire_transport = _tableau_cout_unitaire_transport;
	}

	public int[] get_quantites_demandees() {
		return _quantites_demandees;
	}

	public void set_quantites_demandées(int[] _quantites_demandées) {
		this._quantites_demandees = _quantites_demandées;
	}

	public int[] get_quantites_disponibles() {
		return _quantites_disponibles;
	}

	public void set_quantites_disponibles(int[] _quantites_disponibles) {
		this._quantites_disponibles = _quantites_disponibles;
	}

	public int[][] get_quantites_effectivement_transportées() {
		return _quantites_effectivement_transportees;
	}

	public void set_quantites_effectivement_transportées(int[][] _quantites_effectivement_transportées) {
		this._quantites_effectivement_transportees = _quantites_effectivement_transportées;
	}

	public int get_nb_ligne() {
		return _nb_ligne;
	}

	public void set_nb_ligne(int _nb_ligne) {
		this._nb_ligne = _nb_ligne;
	}

	public int get_nb_colonne() {
		return _nb_colonne;
	}

	public void set_nb_colonne(int _nb_colonne) {
		this._nb_colonne = _nb_colonne;
	}

	public int get_ligne_colonne() {
		return _ligne_colonne;
	}

	public void set_ligne_colonne(int _ligne_colonne) {
		this._ligne_colonne = _ligne_colonne;
	}

	public int get_numero_ligne() {
		return _numero_ligne;
	}

	public void set_numero_ligne(int _numero_ligne) {
		this._numero_ligne = _numero_ligne;
	}

	public int get_numero_colonne() {
		return _numero_colonne;
	}

	public void set_numero_colonne(int _numero_colonne) {
		this._numero_colonne = _numero_colonne;
	}

	public char[] get_alphabet() {
		return _alphabet;
	}

}