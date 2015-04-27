package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


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
					setType(1);
					map[getX()][getY()-1].setType(1);
					map[getX()][getY()-2].setType(1);
					pivot = true;
					setBateau();
					map[getX()][getY()-1].setBateau();
					map[getX()][getY()-2].setBateau();
				}
			});
			escorteur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setType(2);
					map[getX()][getY()-1].setType(2);
					pivot = true;
					setBateau();
					map[getX()][getY()-1].setBateau();
				}
			});
			supprimer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setType(0);
					setMer();
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
			} else if (typee == 1) {
				this.type = typee;
				cell.setBackground(Color.black);
			} else if (typee == 2) {
				this.type = typee;
				cell.setBackground(Color.green);
			} else {
				System.err.println("Type de case inconnu");
			}
		}
		
		public void setBateau(){
			menu.remove(destroyeur);
			menu.remove(escorteur);
			menu.add(supprimer);
		}
		
		public void setMer(){
			menu.remove(supprimer);
			menu.add(destroyeur);
			menu.add(escorteur);
		}
}
