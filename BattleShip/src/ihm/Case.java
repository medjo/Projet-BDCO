package ihm;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import modele.Ship;
import Controleur.ControleurPartie;


public class Case{
	
		private int x;
		private int y; 
		private JMenuBar cell;
		private JMenu menu;
		private int type;
		private JMenuItem destroyeur;
		private JMenuItem escorteur;
		private JMenuItem supprimer;
		private Case[][] map;
		private boolean pivot;
		private Ship bateau;
		
		public Case(int xx, int yy, int typee, Case[][] mapp){
			this.x = xx;
			this.y = yy;
			this.type = typee;
			this.map = mapp;
			this.pivot = false;
			cell = new JMenuBar();
			menu = new JMenu("    ");
			destroyeur = new JMenuItem("Destroyeur");
			escorteur = new JMenuItem("Escorteur");
			supprimer = new JMenuItem("Supprimer");
			menu.add(destroyeur);
			menu.add(escorteur);
			cell.add(menu);
			cell.setBackground(Color.white);
			cell.setBounds(x*32,y*22,30,22);
			destroyeur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						bateau = ControleurPartie.placerBateau(x, y, 3);
						pivot = true;
						creerBateau(2);
					} catch (Exception e) {
						// TODO Message d'erreur placement impossible
						System.err.println("Placement impossible");
					}
				}
			});
			escorteur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						bateau = ControleurPartie.placerBateau(x, y, 2);
						pivot = true;
						creerBateau(1);
					} catch (Exception e) {
						// TODO Message d'erreur placement impossible
						System.err.println("Placement impossible");
					}
				}
			});
			supprimer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					map[bateau.getXBateau()][bateau.getYBateau()].deleteBateau(type+1, bateau.getDirBateauString());
				}
			});
		}
		
		public int getX(){
			return x;
		}

		public int getY(){
			return y;
		}
		
		public JMenuBar getCell(){
			return cell;
		}
		
		public void setType(int typee){
			if (typee == 0) {
				this.type = typee;
				cell.setBackground(Color.white);
			} else if (typee == 2) {
				this.type = typee;
				cell.setBackground(Color.black);
			} else if (typee == 1) {
				this.type = typee;
				cell.setBackground(Color.green);
			} else {
				System.err.println("Type de case inconnu");
			}
		}
		
		public void setMenuBateau(){
			menu.remove(destroyeur);
			menu.remove(escorteur);
			menu.add(supprimer);
		}
		
		public void setMenuMer(){
			menu.remove(supprimer);
			menu.add(destroyeur);
			menu.add(escorteur);
		}
		
		public void setBateau(Ship bat){
			this.bateau = bat;
		}
		
		public void creerBateau(int type){
			Case c;
			for (int i = 0; i <= type; i++){
				c = map[x][y-i];
				c.setType(type);
				c.setBateau(bateau);
				c.setMenuBateau();
			}
		}
		
		public void deleteBateau(int taille, String dir){
			Case c;
			for (int i = 0; i < taille; i++){
				switch(dir){
				case "n" :
					c = map[x][y-i];
					break;
				case "s" :
					c = map[x][y+i];
					break;
				case "e" :
					c = map[x+i][y];
					break;
				case "o" :
					c = map[x-i][y];
					break;
				default :
					throw new IllegalArgumentException("Direction incorrecte");
				}
				c.setBateau(null);
				c.setType(0);
				c.setMenuMer();
			}
		}
}
