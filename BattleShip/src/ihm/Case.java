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
		
		public Case(int xx, int yy, int typee){
			this.x = xx;
			this.y = yy;
			this.type = typee;
			cell = new JMenuBar();
			menu = new JMenu("    ");
			JMenuItem destroyeur = new JMenuItem("Destroyeur");
			JMenuItem escorteur = new JMenuItem("Escorteur");
			menu.add(destroyeur);
			menu.add(escorteur);
			cell.add(menu);
			cell.setBackground(Color.white);
			cell.setBounds(x*32,y*22,30,22);
			destroyeur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cell.setBackground(Color.black);
					type = 1;
				}
			});
			escorteur.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cell.setBackground(Color.green);
					type = 2;
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
}
