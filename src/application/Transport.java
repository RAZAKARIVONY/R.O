package application;

import java.util.ArrayList;
import java.util.List;

public class Transport extends Exception{
	public static int test = 0;
        public static String console = "";
	public static List<Case> getChemin;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
            int tab[][] = new int[4][4];

            tab[0][0] = 12;
            tab[0][1] = 42;
            tab[0][2] = 23;
            tab[0][3] = 32;

            tab[1][0] = 12;
            tab[1][1] = 32;
            tab[1][2] = 14;
            tab[1][3] = 5;

            tab[2][0] = 17;
            tab[2][1] = 18;
            tab[2][2] = 14;
            tab[2][3] = 22;

            tab[3][0] = 45;
            tab[3][1] = 18;
            tab[3][2] = 32;
            tab[3][3] = -1;

            PrintTab(tab, 4, 4);
            int result[][] = MinTab(tab, 4, 4);
            PrintTab(result, 4, 4);
            int fin[][] = SteppingStone(tab, result, 4, 4);
            PrintTab(fin, 3, 3);
	}
            
        public static int[][] BalasHammer(int data[][], int ligne, int col) {
            int res[][] = new int[ligne][col];
            int replica[][] = new int[ligne][col];
            //Copy de la valeur entrEe dans une autre table
            for (int i = 0; i < ligne; i++) {
                for (int j = 0; j < col; j++) {
                    replica[i][j] = data[i][j];
                }
            }

            while (ChercheMax(replica, ligne, col) != null) {
                int val[] = ChercheMax(replica, ligne, col);
                int line = val[0] // num ligne du minimale
                        , colonne = val[1]; // num colonne du minimale

                int offre = data[line][col - 1];
                int demande = data[ligne - 1][colonne];

                if (offre == demande) {
                    res[line][colonne] = demande;

                    System.out.println("res[" + line + "][" + colonne + "] = " + res[line][colonne]);

                    data[line][col - 1] = 0;//offre
                    data[ligne - 1][colonne] = 0; //demande

                    for (int i = 0; i < col; i++) {
                        //elimination del la ligne numero LINE
                        replica[line][i] = -1;
                    }

                    for (int i = 0; i < ligne; i++) {
                        //elimination de la colonne numero COLONNE
                        replica[i][colonne] = -1;
                    }
                }

                if (offre > demande) {
                    res[line][colonne] = demande;
                    System.out.println("res[" + line + "][" + colonne + "] = " + res[line][colonne]);
                    data[line][col - 1] = offre - demande;//offre
                    data[ligne - 1][colonne] = 0; //demande

                    for (int i = 0; i < ligne; i++) {
                        //elimination de la colonne numero COLONNE
                        replica[i][colonne] = -1;
                    }
                }

                if (offre < demande) {
                    res[line][colonne] = offre;
                    System.out.println("res[" + line + "][" + colonne + "] = " + res[line][colonne]);
                    data[line][col - 1] = 0;//offre
                    data[ligne - 1][colonne] = demande - offre; //demande

                    for (int i = 0; i < col; i++) {
                        //elimination del la ligne numero LINE
                        replica[line][i] = -1;
                    }
                }

            }
            return res;
        }
        
        public static int[] ChercheMax(int array[][], int line, int column) {
            for (int i = 0; i < line - 1; i++) {
                // difference sur la colonne
                int min1 = 0, min2 = 0, ind_min1 = 0, ind_min2 = 0;

                while (ind_min1 < column - 1) {
                    if (array[i][ind_min1] > 0) {
                        min1 = array[i][ind_min1];
                        ind_min2 = ind_min1 + 1;
                        break;
                    } else {
                        ind_min1++;
                    }
                }

                while (ind_min2 < column - 1) {
                    if (array[i][ind_min1] > 0) {
                        min2 = array[i][ind_min2];
                        break;
                    } else {
                        ind_min2++;
                    }
                }

                if (min1 > min2) {
                    int permu = min1;
                    min1 = min2;
                    min2 = permu;
                }

                for (int j = (ind_min2 + 1); j < column - 1; j++) {
                    if (min1 > min2) {
                        int permu = min1;
                        min1 = min2;
                        min2 = permu;
                    }

                    if (array[i][j] > 0) {
                        if (min1 < 0) {
                            min1 = array[i][j];
                        } else if (min2 < 0) {
                            min2 = array[i][j];
                        } else if (array[i][j] < min1) {
                            min2 = min1;
                            min1 = array[i][j];
                        } else if ((array[i][j] > min1) && (array[i][j] < min2)) {
                            min2 = array[i][j];
                        }
                    }
                }
                if (min1 < 0) {
                    min1 = 0;
                }
                if (min2 < 0) {
                    min2 = 0;
                }

                if (min1 > min2) {
                    int permu = min1;
                    min1 = min2;
                    min2 = permu;
                }

                array[i][column - 1] = min2 - min1;
                // System.out.println("array[" + i + "][" + (column - 1) + "] = " + array[i][column - 1]);
            }

            //System.out.println();
            // System.out.println();
            for (int j = 0; j < column - 1; j++) {
                // difference sur la ligne
                int min1 = 0, min2 = 0, ind_min1 = 0, ind_min2 = 0;

                while (ind_min1 < line - 1) {
                    if (array[ind_min1][j] > 0) {
                        min1 = array[ind_min1][j];
                        ind_min2 = ind_min1 + 1;
                        break;
                    } else {
                        ind_min1++;
                    }
                }

                while (ind_min2 < line - 1) {
                    if (array[ind_min2][j] > 0) {
                        min2 = array[ind_min2][j];
                        break;
                    } else {
                        ind_min2++;
                    }
                }

                if (min1 > min2) {
                    int permu = min1;
                    min1 = min2;
                    min2 = permu;
                }

                for (int i = (ind_min2 + 1); i < line - 1; i++) {
                    if (min1 > min2) {
                        int permu = min1;
                        min1 = min2;
                        min2 = permu;
                    }

                    if (array[i][j] > 0) {
                        if (min1 < 0) {
                            min1 = array[i][j];
                        } else if (min2 < 0) {
                            min2 = array[i][j];
                        } else if (array[i][j] < min1) {
                            min2 = min1;
                            min1 = array[i][j];
                        } else if ((array[i][j] > min1) && (array[i][j] < min2)) {
                            min2 = array[i][j];
                        }
                    }

                    if (min1 < 0) {
                        min1 = 0;
                    }
                    if (min2 < 0) {
                        min2 = 0;
                    }

                    if (min1 > min2) {
                        int permu = min1;
                        min1 = min2;
                        min2 = permu;
                    }
                }
                array[line - 1][j] = min2 - min1;
                // System.out.println("array[" + (line - 1) + "][" + j + "] = " + array[line - 1][j]);
            }

            //cherche max sur les lignes
            int maxL = array[0][column - 1], lineMax = 0;
            for (int i = 0; i < line - 1; i++) {
                if (array[i][column - 1] > 0) {
                    if (array[i][column - 1] > maxL) {
                        maxL = array[i][column - 1];
                        lineMax = i;
                    }
                }
            }
           // System.out.println();
           // System.out.println("Resultats");

            //cherche max sur les colonnes
            int maxC = array[line - 1][0], colMax = 0;
            for (int i = 0; i < column - 1; i++) {
                if (array[line - 1][i] > 0) {
                    if (array[line - 1][i] > maxC) {
                        maxC = array[line - 1][i];
                        colMax = i;
                    }
                }
            }

            if (maxC <= 0 && maxL <= 0) {
                return null;
            }

            if (maxL > maxC) {
                int i = 0, indmin = 0;

                while (i < column - 1) {
                    if (array[lineMax][i] > 0) {
                        indmin = i;
                        break;
                    } else {
                        i++;
                    }

                }
                int min = array[lineMax][i];
                while (i < column - 1) {
                    if (array[lineMax][i] > 0) {
                        if (array[lineMax][i] < min) {
                            indmin = i;
                            min = array[lineMax][i];
                        }
                    }
                    i++;
                }
                //  System.out.println("line = " + lineMax + ", Column = " + indmin + ", difference max = " + maxL + ", cout min = " + array[lineMax][indmin]);
                return new int[]{lineMax, indmin};
            } else {
                int i = 0, indmin = 0;

                while (i < line - 1) {
                    if (array[i][colMax] > 0) {
                        indmin = i;
                        break;
                    } else {
                        i++;
                    }
                }

                int min = array[i][colMax];
                while (i < line - 1) {
                    if (array[i][colMax] > 0) {
                        if (array[i][colMax] < min) {
                            indmin = i;
                            min = array[i][colMax];
                        }
                    }
                    i++;
                }
                // System.out.println("line = " + indmin + ", Column = " + colMax + ", difference max = " + maxC + ", cout min = " + array[indmin][colMax]);
                return new int[]{indmin, colMax};

            }
        }
        
	public static int[][] CoinNordWest(int data[][], int ligne, int col) {
		int res[][] = new int[ligne][col];

		int i = 0, j = 0;
		while (i < (ligne - 1)) {
			while (j < (col - 1)) {
				int stock = data[i][col - 1], demande = data[ligne - 1][j];
				if (stock == demande) {
					res[i][j] = demande;
					System.out.println("res[" + i + "][" + j + "] = " + res[i][j]);
					data[i][col - 1] = 0; // reste en stock
					data[ligne - 1][j] = 0; // demande a satisfaire
					j++;
					break;
				}

				if (stock > demande) {
					res[i][j] = demande;
					System.out.println("res[" + i + "][" + j + "] = " + res[i][j]);
					data[i][col - 1] = stock - demande; // reste en stock
					data[ligne - 1][j] = 0; // demande a satisfaire
					j++;
				}

				if (stock < demande) {
					res[i][j] = stock;
					System.out.println("res[" + i + "][" + j + "] = " + res[i][j]);
					data[i][col - 1] = 0; // reste en stock
					data[ligne - 1][j] = demande - stock; // demande a
															// satisfaire
					break;
				}
			}
			i++;
		}
                
		return res;
	}

	public static int[][] MinLine(int data[][], int ligne, int col) {
		int res[][] = new int[ligne][col];

		int i = 0, j;
		while (i < (ligne - 1)) {
			while (true) {

				j = 0;
				int ind_min = j, min = 0;
				while (j < (col - 1)) {
					// Recherche le premier cout minimale, positif
					ind_min = j;
					min = data[i][ind_min];
					if (min > 0) {
						break;
					} else {
						j++;
					}
				}

				while (j < (col - 1)) {
					if ((min > data[i][j]) && (data[i][j] > 0)) {
						ind_min = j;
						min = data[i][j];
					}
					j++;
				}
				if(min==-1)
				break;

				int stock = data[i][col - 1], demande = data[ligne - 1][ind_min];

				if (stock == demande) {
					res[i][ind_min] = demande;
					System.out.println("res[" + i + "][" + ind_min + "] = " + res[i][ind_min]);
					data[i][ind_min] = -1;

					data[i][col - 1] = 0;
					data[ligne - 1][ind_min] = 0;
					break;
				}

				if (stock > demande) {
					res[i][ind_min] = demande;
					System.out.println("res[" + i + "][" + ind_min + "] = " + res[i][ind_min]);
					data[i][ind_min] = -1;

					data[i][col - 1] = stock - demande;
					data[ligne - 1][ind_min] = 0;
				}

				if (stock < demande) {
					res[i][ind_min] = stock;
					System.out.println("res[" + i + "][" + ind_min + "] = " + res[i][ind_min]);
					data[i][ind_min] = -1;

					data[i][col - 1] = 0;
					data[ligne - 1][ind_min] = demande - stock;
					break;
				}
			}
			i++;
		}
		return res;
	}

	public static int[][] MinCol(int data[][], int ligne, int col) {
		int res[][] = new int[ligne][col];

		int i, j = 0;
		while (j < col - 1) {
			while (true) {
				i = 0;
				int ind_min = i, min = 0;
				while (i < (ligne - 1)) {
					// Recherche le premier cout minimale, positif d'une colonne
					ind_min = i;
					min = data[ind_min][j];
					if (min > 0) {
						break;
					} else {
						i++;
					}
				}

				while (i < (ligne - 1)) {
					if ((min > data[i][j]) && (data[i][j] > 0)) {
						ind_min = i;
						min = data[i][j];
					}
					i++;
				}
				
				if(min==-1)
				break;

				int stock = data[ind_min][col - 1], demande = data[ligne - 1][j];

				if (stock == demande) {
					res[ind_min][j] = demande;
					System.out.println("res[" + ind_min + "][" + j + "] = " + res[ind_min][j]);
					data[ind_min][j] = -1;

					data[ind_min][col - 1] = 0;
					data[ligne - 1][j] = 0;
					break;
				}

				if (stock > demande) {
					res[ind_min][j] = demande;
					System.out.println("res[" + ind_min + "][" + j + "] = " + res[ind_min][j]);
					data[ind_min][j] = -1;

					data[ind_min][col - 1] = stock - demande;
					data[ligne - 1][j] = 0;
					break;
				}

				if (stock < demande) {
					res[ind_min][j] = stock;
					System.out.println("res[" + ind_min + "][" + j + "] = " + res[ind_min][j]);
					data[ind_min][j] = -1;

					data[ind_min][col - 1] = 0;
					data[ligne - 1][j] = demande - stock;
				}
			}
			j++;
		}

		return res;
	}

	public static int[][] MinTab(int data[][], int ligne, int col) {
		int res[][] = new int[ligne][col];

		while (true) {
			int i = 0, j, ligne_min = 0, col_min = 0, min = 0;
			while (i < ligne - 1) {
				j = 0;
				while (j < col - 1) {
					ligne_min = i;
					col_min = j;
					min = data[ligne_min][col_min];
					if (min > 0) {
						break;
					} else {
						j++;
					}
				}
				if (min > 0) {
					break;
				} else {
					i++;
				}

			}

			// i =0;
			while (i < ligne - 1) {
				j = 0;
				while (j < col - 1) {
					if ((min > data[i][j]) && (data[i][j] > 0)) {
						min = data[i][j];
						ligne_min = i;
						col_min = j;
					}
					j++;
				}
				i++;
			}

			int stock = data[ligne_min][col - 1], demande = data[ligne - 1][col_min];
			if (min > 0) {
				if (stock == demande) {
					res[ligne_min][col_min] = demande;
					System.out.println("res[" + ligne_min + "][" + col_min + "] = " + res[ligne_min][col_min]);
					data[ligne_min][col_min] = -1;
					data[ligne_min][col - 1] = 0;
					data[ligne - 1][col_min] = 0;
				}

				if (stock > demande) {
					res[ligne_min][col_min] = demande;
					System.out.println("res[" + ligne_min + "][" + col_min + "] = " + res[ligne_min][col_min]);
					data[ligne_min][col_min] = -1;
					data[ligne_min][col - 1] = stock - demande;
					data[ligne - 1][col_min] = 0;
				}

				if (stock < demande) {
					res[ligne_min][col_min] = stock;
					System.out.println("res[" + ligne_min + "][" + col_min + "] = " + res[ligne_min][col_min]);
					data[ligne_min][col_min] = -1;
					data[ligne_min][col - 1] = 0;
					data[ligne - 1][col_min] = demande - stock;
				}
			} else {
				break;
			}
		}
		return res;
	}

	public static String printChemin(List<Case> c) {

                String chemin = "";
		if (c.size() > 0) {

			chemin += c.get(1).printCase();

			for (int i = 2; i < c.size(); i++) {
				chemin += " -> " + c.get(i).printCase();
			}

			chemin += " -> " + c.get(0).printCase() + "\n";

		} else {
			System.out.println("Chemin vide.");
		}
                
                return chemin;

	}

	public static void PrintTab(int tab[][], int ligne, int col) {

                console+="\n\n";
		for (int i = 0; i < ligne; i++) {
                        console+="\t";
			for (int j = 0; j < col; j++) {
				if (tab[i][j] == 0) {
                                        console += "-\t";
					System.out.print("-" + "\t");
				}else if(tab[i][j] == -1) {
                                    console += "Ɛ\t";
                                    System.out.print("Ɛ" + "\t");
                                }else {
                                        console += tab[i][j]+ "\t";
					System.out.print(tab[i][j] + "\t");
				}

			}
                        console+="\n";
			System.out.print("\n");
		}
                console+="\n";
                
		System.out.print("\n");
	}

	public static int[][] tableau_sans_od(int data[][], int ligne, int colonne) {

		int temp[][] = new int[ligne - 1][colonne - 1];
		for (int i = 0; i < ligne - 1; i++) {
			for (int j = 0; j < colonne - 1; j++) {

				temp[i][j] = data[i][j];

			}
		}
		return temp;

	}

	public static int[][] SteppingStone(int t_origine[][], int t_modifie[][], int nb_ligne, int nb_col) {
		
		int data[][] = tableau_sans_od(t_origine, nb_ligne, nb_col);
		int t_cout[][] = tableau_sans_od(t_modifie, nb_ligne, nb_col);
		nb_ligne -= 1;
		nb_col -= 1;
		
		List<Case> list_case_negative;
		List<Integer> list_cout_negatif;

		do {

			PrintTab(t_cout, nb_ligne, nb_col);

			for (int i = 0; i < nb_ligne; i++) {
				for (int j = 0; j < nb_col; j++) {

					if (t_cout[i][j] == 0) {
						List<Case> temp = new ArrayList<>();
						temp.add(new Case(i, j));

						List<Case> chemin_temp = chemin(t_cout, nb_ligne, nb_col, true, temp);
						// System.out.print(new Case(i, j).printCase() + " : ");
						// printChemin(chemin_temp);
						if (chemin_temp.isEmpty()) {

							// PrintTab(t_cout, 4, 6);
							t_cout[i][j] = -1;
//							j = nb_col;
//							i = nb_ligne;
						}

					}

				}
			}

			// System.out.println("Z = " + z(data, t_cout, nb_ligne, nb_col));

			list_case_negative = new ArrayList<>();
			list_cout_negatif = new ArrayList<>();

			for (int i = 0; i < nb_ligne; i++) {
				for (int j = 0; j < nb_col; j++) {

					if (t_cout[i][j] == 0) {
						List<Case> temp = new ArrayList<>();
						temp.add(new Case(i, j));
                                                printChemin(chemin(t_cout, nb_ligne, nb_col, true, temp));
						int c = CoutUnitaireChemin(data, chemin(t_cout, nb_ligne, nb_col, true, temp));
						if (c < 0) {
							list_case_negative.add(new Case(i, j));
							list_cout_negatif.add(c);
						}
					}

				}
			}

			if (!list_case_negative.isEmpty()) {

				int indice = 0, min = 0;

				for (int i = 0; i < list_cout_negatif.size(); i++) {

					List<Case> ch_negatif = new ArrayList<>();
					ch_negatif.add(list_case_negative.get(i));
					int t = list_cout_negatif.get(i) * quantiteADetourne(t_cout, nb_ligne, nb_col,
							chemin(t_cout, nb_ligne, nb_col, true, ch_negatif));
					if (t < min) {
						indice = i;
						min = t;

					}
				}

				// System.out.println(indice + " " +
				// list_case_negative.get(indice).printCase());

				List<Case> temp_2 = new ArrayList<>();
				temp_2.add(list_case_negative.get(indice));

				List<Case> chemin = chemin(t_cout, nb_ligne, nb_col, true, temp_2);
                                
                                console += "\nCase où l'on obtient le plus de gain : " + list_case_negative.get(indice).printCase() + " : " + printChemin(chemin) + "\n";
                                console += "\nCoût à ce stade : Z = " + z(data, t_cout, nb_ligne, nb_col) + "\n";
                                console+= "==============================================================================================";
				//printChemin(chemin);
				// System.out.println(quantiteADetourne(t_cout, nb_ligne,
				// nb_col, chemin));
				t_cout = detourneQte(t_cout, chemin, nb_ligne, nb_col,
						quantiteADetourne(t_cout, nb_ligne, nb_col, chemin));
				// PrintTab(t, 4, 6);

			}

		} while (!list_case_negative.isEmpty());

                
                console += "\nCoût à ce stade : Z = " + z(data, t_cout, nb_ligne, nb_col) + "\n";
                
		System.out.println("Z = " + z(data, t_cout, nb_ligne, nb_col));
		return t_cout;
	}

	public static List<Case> chemin(int t_cout[][], int nb_ligne, int nb_colonne, boolean ligne, List<Case> depart) {
		getChemin = new ArrayList<>();
		boucleChemin(t_cout, nb_ligne, nb_colonne, ligne, depart);
		return copyList(getChemin);
	}

	public static void boucleChemin(int t_cout[][], int nb_ligne, int nb_colonne, boolean ligne, List<Case> depart) {

		Case case_depart = depart.get(0);
		Case dernier = depart.get(depart.size() - 1);

		if (ligne) {

			for (int i = 0; i < nb_colonne; i++) {
				if (t_cout[dernier.get_l()][i] != 0 && i != dernier.get_c()) {
					List<Case> temp = copyList(depart);
					temp.add(new Case(dernier.get_l(), i));
					boucleChemin(t_cout, nb_ligne, nb_colonne, false, temp);
				}
			}

		} else {

			for (int i = 0; i < nb_ligne; i++) {
				if (case_depart.isEqual(new Case(i, dernier.get_c()))) {
					getChemin = depart;
				}

				if (t_cout[i][dernier.get_c()] != 0 && i != dernier.get_l()) {
					List<Case> temp = copyList(depart);
					temp.add(new Case(i, dernier.get_c()));
					boucleChemin(t_cout, nb_ligne, nb_colonne, true, temp);
				}
			}

		}

	}

	public static int CoutUnitaireChemin(int data[][], List<Case> lc) {
		int somme = 0;

		somme = data[lc.get(0).get_l()][lc.get(0).get_c()];
		int i = 1, signe = 1;

		// System.out.print(data[lc.get(0).get_l()][lc.get(0).get_c()]);

		while (i < lc.size()) {

			signe *= -1;

			somme += data[lc.get(i).get_l()][lc.get(i).get_c()] * signe;
			// System.out.print(" + " +
			// data[lc.get(i).get_l()][lc.get(i).get_c()] * signe);
			i++;
		}

		// System.out.print(" = " + somme + "\n");

		return somme;
	}

	public static int[][] detourneQte(int t_cout[][], List<Case> c, int nb_ligne, int nb_colonne, int qte_a_detourne) {

		int signe = -1;

		if (qte_a_detourne != -1) {
			for (int i = 0; i < c.size(); i++) {
				signe *= -1;

				if (t_cout[c.get(i).get_l()][c.get(i).get_c()] != -1) {
					t_cout[c.get(i).get_l()][c.get(i).get_c()] = t_cout[c.get(i).get_l()][c.get(i).get_c()]
							+ (signe * qte_a_detourne);
				} else {
					t_cout[c.get(i).get_l()][c.get(i).get_c()] = qte_a_detourne;
				}

			}
		} else {
			t_cout[c.get(0).get_l()][c.get(0).get_c()] = -1;
			for (int i = 1; i < c.size(); i++) {
				if (t_cout[c.get(i).get_l()][c.get(i).get_c()] == -1) {
					t_cout[c.get(i).get_l()][c.get(i).get_c()] = 0;
				}
			}
		}

		return t_cout;

	}

	public static int quantiteADetourne(int t_cout[][], int nb_ligne, int nb_col, List<Case> chemin) {

		int qte_a_detourne = t_cout[chemin.get(1).get_l()][chemin.get(1).get_c()];

		for (int i = 3; i < chemin.size(); i += 2) {

			if (t_cout[chemin.get(i).get_l()][chemin.get(i).get_c()] < qte_a_detourne) {
				qte_a_detourne = t_cout[chemin.get(i).get_l()][chemin.get(i).get_c()];
			}

		}

		return qte_a_detourne;
	}

	public static List<Case> copyList(List<Case> l) {
		List<Case> temp = new ArrayList<>();

		for (int i = 0; i < l.size(); i++) {
			temp.add(l.get(i));
		}
		return temp;
	}

	public static int z(int data[][], int t_cout[][], int nb_ligne, int nb_col) {
		int somme = 0;

		for (int i = 0; i < nb_ligne; i++) {
			for (int j = 0; j < nb_col; j++) {

				if (t_cout[i][j] != 0 && t_cout[i][j] != -1) {
					somme += t_cout[i][j] * data[i][j];
				} else if (t_cout[i][j] == -1) {

				}

			}
		}

		return somme;
	}

}