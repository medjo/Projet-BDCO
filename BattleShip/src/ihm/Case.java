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
		private JMenuBar cell1;
		private JMenu menu;
		private JMenu menu1;
		private int type;
		private boolean pivot;
		private JMenuItem destroyeur;
		private JMenuItem escorteur;
		private JMenuItem tirer;
		private JMenuItem attaquer;
		private JMenu deplacer;
		private JMenu pivoter;
		private JMenuItem supprimer;
		private JMenuItem dnord;
		private JMenuItem dsud;
		private JMenuItem dest;
		private JMenuItem douest;
		private JMenuItem pnord;
		private JMenuItem psud;
		private JMenuItem pest;
		private JMenuItem pouest;
		private ButtonGroup direction;
		private Case[][] map;
		private int xBateau;
		private int yBateau;
		private String dirBateau;
		private int idBateau;
		private int nDes;
		private int nEsc;
		private Case[][] map2;
		
		public Case(int xx, int yy, int typee, Case[][] mapp){
			this.x = xx;
			this.y = yy;
			this.type = typee;
			this.map = mapp;
			this.idBateau = 0;
			this.nDes = 0;
			this.nEsc = 0;
			this.pivot = false;
			cell = new JMenuBar();
			menu = new JMenu("    ");
			destroyeur = new JMenuItem("Destroyeur");
			escorteur = new JMenuItem("Escorteur");
			dnord = new JMenuItem("Nord");
			dsud = new JMenuItem("Sud");
			dest = new JMenuItem("Est");
			douest = new JMenuItem("Ouest");
			pnord = new JMenuItem("Nord");
			psud = new JMenuItem("Sud");
			pest = new JMenuItem("Est");
			pouest = new JMenuItem("Ouest");
			deplacer = new JMenu("Déplacer");
			pivoter = new JMenu("Pivoter");
			deplacer.add(dnord);
			deplacer.add(dsud);
			deplacer.add(dest);
			deplacer.add(douest);
			pivoter.add(pnord);
			pivoter.add(psud);
			pivoter.add(pest);
			pivoter.add(pouest);
			menu.add(destroyeur);
			menu.add(escorteur);
			cell.add(menu);
			cell.setBackground(Color.white);
			cell.setBounds(x*32,y*22,30,22);
			destroyeur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						//bateau = ControleurPartie.placerBateau(x, y, 3);
						if(map[0][0].getNDes()==0) {
							creerBateau(3, "n", getNBateau()+1);
							map[0][0].setNDes(1);
						} else {
							System.err.println("Plus de destroyer disponible");
						}
					} catch (Exception e) {
						// TODO Message d'erreur placement impossible
						System.err.println("Placement impossible");
					}
				}
			});
			escorteur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						//bateau = ControleurPartie.placerBateau(x, y, 2);
						int ne = map[0][0].getNEsc();
						if(ne==0 || ne ==1) {
							creerBateau(2, "n", getNBateau()+1);
							map[0][0].setNEsc(ne+1);
						} else {
							System.err.println("Plus d'escorteur disponible");
						}
					} catch (Exception e) {
						// TODO Message d'erreur placement impossible
						System.err.println("Placement impossible");
					}
				}
			});
			/*supprimer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					map[bateau.getXBateau()][bateau.getYBateau()].deleteBateau(type+1, bateau.getDirBateauString());
				}
			});*/
			dnord.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						deplacerBateau("n", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
			dsud.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						deplacerBateau("s", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
			dest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						deplacerBateau("e", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
			douest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						deplacerBateau("o", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
			pnord.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						pivoterBateau("n", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
			psud.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						pivoterBateau("s", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
			pest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						pivoterBateau("e", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
			pouest.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						pivoterBateau("o", type+1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			});
		}
		
		public Case(int xx, int yy, int typee, Case[][] map1, Case[][] map2){
			this(xx, yy, typee, map1);
			this.map2 = map2;
			cell1 = new JMenuBar();
			menu1 = new JMenu("    ");
			attaquer = new JMenuItem("Attaquer");
			tirer = new JMenuItem("Tirer");
			menu.remove(destroyeur);
			menu.remove(escorteur);
			menu.add(deplacer);
			menu.add(pivoter);
			menu.add(tirer);
			menu1.add(attaquer);
			cell1.add(menu1);
			cell1.setBackground(Color.gray);
			setCellSize(22, 21);
			setCell1Size(22, 21);
			tirer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					menu.remove(deplacer);
					menu.remove(pivoter);
					
					// TODO Auto-generated method stub	
				}
			});
			attaquer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub	
					menu.add(deplacer);
					menu.add(pivoter);
				}
			});
		}
		
		
		public Case(int xx, int yy, int typee, Case[][] map1, Case[][] map2, int Observation){
			this(xx,yy,typee,map1,map2);
			menu.remove(tirer);
			menu.remove(deplacer);
			menu.remove(pivoter);
			menu1.remove(attaquer);
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
		
		public JMenuBar getCell1(){
			return cell1;
		}
		
		public void setCellSize(int a, int b){
			cell.setBounds(x*a,y*b,a-2,b);
		}
		public void setCell1Size(int a, int b){
			cell1.setBounds(10*a+x*a,y*b,a-2,b);
		}
		

		public void setType(int typee){
			if (typee == 0) {
				this.type = typee;
				cell.setBackground(Color.white);
			} else if (typee == 2) {
				this.type = typee;
				cell.setBackground(Color.gray);
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
			//menu.add(supprimer);
			String dir;
			switch(dirBateau){
			case "n" :
				dir = " Λ";
				break;
			case "s" :
				dir = " V";
				break;
			case "e" :
				dir = " >";
				break;
			case "o" :
				dir = " <";
				break;
			default :
				throw new IllegalArgumentException("Direction incorrecte");
			}
			menu.setText(dir);
			menu.add(deplacer);
			menu.add(pivoter);
		}
		
		public void setMenuMer(){
			menu.remove(deplacer);
			menu.remove(pivoter);
			//menu.remove(supprimer);
			menu.setText("    ");
			menu.add(destroyeur);
			menu.add(escorteur);
		}
		
		public void creerBateau(int taille, String dir, int id) throws Exception{
			checkBateau(dir, taille);
			Case c;
			pivot = true;
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
				c.setIdBateau(id);
				c.setCoordBateau(x, y);
				c.setType(taille-1);
				c.setDirBateau(dir);
				c.setMenuBateau();
			}
		}
		
		public void checkBateau(String dir, int taille) throws Exception{
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
				if (c.getIdBateau() != 0){
					throw new IllegalArgumentException("Case occupée");
				}
			}
		}
		
		public void deplacerBateau(String dir, int taille) throws Exception{
			Case c = map[xBateau][yBateau];
			String dirB = dirBateau;
			int id = idBateau;
			c.deleteBateau(taille, dirBateau);
			try {
				switch(dir){
				case "n" :
					c = map[xBateau][yBateau-1];
					break;
				case "s" :
					c = map[xBateau][yBateau+1];
					break;
				case "e" :
					c = map[xBateau+1][yBateau];
					break;
				case "o" :
					c = map[xBateau-1][yBateau];
					break;
				default :
					throw new IllegalArgumentException("Direction incorrecte");
				}
				c.creerBateau(taille, dirB, id);
			} catch (IllegalArgumentException e) {
				c = map[xBateau][yBateau];
				c.creerBateau(taille, dirB, id);
				throw new IllegalArgumentException("Case occupée");
			} catch (Exception e) {
				c = map[xBateau][yBateau];
				c.creerBateau(taille, dirB, id);
				throw new IllegalArgumentException("Case en dehors de la carte");
			}
		}
		
		public void pivoterBateau(String dir, int taille) throws Exception{
			Case c = map[xBateau][yBateau];
			String dirB = dirBateau;
			int id = idBateau;
			c.deleteBateau(taille, dirB);
			try{
				c.creerBateau(taille, dir, id);
			} catch (IllegalArgumentException e) {
				c.creerBateau(taille, dirB, id);
				throw new IllegalArgumentException("Case occupée");
			} catch (Exception e) {
				c.creerBateau(taille, dirB, id);
				throw new IllegalArgumentException("Case en dehors de la carte");
			}
		}
		
		public void deleteBateau(int taille, String dir){
			Case c;
			pivot = false;
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
				c.setIdBateau(0);
				c.setType(0);
				c.setMenuMer();
			}
		}
		
		public void setCoordBateau(int xx, int yy){
			xBateau = xx;
			yBateau = yy;
		}
		
		public void setDirBateau(String dir){
			dirBateau = dir;
		}
		
		public int getNBateau(){
			return map[0][0].getNDes()+map[0][0].getNEsc();
		}
		
		public int getNDes(){
			return nDes;
		}
		
		public int getNEsc(){
			return nEsc;
		}
		
		public void setIdBateau(int id){
			idBateau = id;
		}
		
		public int getIdBateau(){
			return idBateau;
		}
		
		public void setNDes(int nd){
			nDes = nd;
		}
		
		public void setNEsc(int ne){
			nEsc = ne;
		}
		
		public boolean isPivot(){
			return pivot;
		}
		
		public String getDirBateau(){
			return dirBateau;
		}
		
		public int getType(){
			return type;
		}
		
		public void reset(){
			this.type = 0;
			this.idBateau = 0;
			this.nDes = 0;
			this.nEsc = 0;
			this.pivot = false;
			menu.setText("    ");
			menu.removeAll();
			menu.add(destroyeur);
			menu.add(escorteur);
			cell.setBackground(Color.white);
		}
}
