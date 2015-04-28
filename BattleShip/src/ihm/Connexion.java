package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import Controleur.ControleurConnexion;
import Controleur.ControleurHistorique;
import Controleur.ControleurPartie;

import java.sql.*;
import java.util.ArrayList;

import jdbc.*;
import ihm.org.eclipse.wb.swing.FocusTraversalOnArray;
import modele.*;
import net.miginfocom.swing.MigLayout;



public class Connexion extends JFrame {

	
	
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPanel Connexion;
	private JPanel Inscription;
	private JPanel Identification;
	private JPanel Jeu;
	private JPanel OberservationPartie;
	private JPanel ObserveAction;
	private JPanel ChercheAdv;
	private JPanel PrepareBataille;
	private JPanel Jouer;
	private JPanel pan;
	private JTextField pseudo;
	private JTextField nom;
	private JTextField prenom;
	private JTextField email;
	private JTextField numeroRue;
	private JTextField rue;
	private JTextField codePoste;
	private JTextField ville;
	private JTextField txtJj;
	private JTextField txtMm;
	private JTextField txtAnnee;
	private JTextField idPartie;
	private JTextField vainqueur;
	private JTextField txtJoueurN;
	private String pseudoAdv;
	private String pseudoJo;
	private String pseudObs1 = new String();
	private String pseudObs2 = new String();
	private JLabel lblJoueurObs1;
	private JLabel lblJoueurObs2;
	private Case[][] map;
	private Case[][] map1;
	private JTextField textJoueurObs1;
	private JTextField textJoueurObs2;
	

	/**
	 * Launch the application.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				BattleShip.theConnection= new TheConnection(new ConnectionInfo("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1","guys","guys"));
				BattleShip.theConnection.open();
				try {
					Connexion frame = new Connexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public Connexion() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 300);
		setResizable(true);
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
//interface d'accueil
		final JPanel Identification = new JPanel();
		contentPane.add(Identification, "name_31750759103752");
		Identification.setLayout(null);
		Identification.setVisible(true);
		
//interface de connexion
		final JPanel Connexion = new JPanel();
		contentPane.add(Connexion, "name_31673698091126");
		
//interface de connexion->Lancer Partie 	
		final JPanel ChercheAdv = new JPanel();
		contentPane.add(ChercheAdv, "name_68167019743368");
		ChercheAdv.setLayout(null);

//interface de connexion ->Lancer Partie -> preparer Bataille 
		final JPanel PrepareBataille = new JPanel();
		contentPane.add(PrepareBataille, "name_43179446908468");
		PrepareBataille.setLayout(null);
		
//interface de connexion -> lancer partie -> preparer bataille -> Jouer 
		final JPanel Jouer = new JPanel();
		contentPane.add(Jouer, "name_61813199911534");
		
		JButton btnRetour = new JButton("Quitter Partie");
		btnRetour.setFont(new Font("Dialog", Font.BOLD, 9));
		btnRetour.setBounds(325, 235, 100, 25);
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Jouer.setVisible(false);
				Connexion.setVisible(true);
			}
		});
		Jouer.setLayout(null);
		Jouer.add(btnRetour);
		
		JButton btnRafraichir = new JButton("Rafraichir");
		btnRafraichir.setFont(new Font("Dialog", Font.BOLD, 10));
		btnRafraichir.setBounds(3, 235, 105, 25);
		btnRafraichir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( (boolean)BattleShip.partie.aMoiDeJouer()){ //>>>>>>A voir pb dans Partie<<<<<<<
					txtJoueurN.setText("Tour de "+pseudoJo);
				}
				txtJoueurN.setText("Tour de " + pseudoAdv);
			}
		});
		Jouer.setLayout(null);
		Jouer.add(btnRafraichir);
		
		
		txtJoueurN = new JTextField();
		txtJoueurN.setText("Tour Joueur...");
		txtJoueurN.setBounds(113, 235, 100, 25);
		Jouer.add(txtJoueurN);
		txtJoueurN.setColumns(10);
		
		JButton btnValider_1 = new JButton("Valider Tour");
		btnValider_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnValider_1.setFont(new Font("Dialog", Font.BOLD, 10));
		btnValider_1.setBounds(215, 235, 105, 25);
		Jouer.add(btnValider_1);
		
//interface de connexion -> Observer une partie 
		final JPanel OberservationPartie = new JPanel();
		contentPane.add(OberservationPartie, "name_2587200245776");
		OberservationPartie.setLayout(null);

//interface de connexion -> Observer une partie ->Observer		
		final JPanel ObserveAction = new JPanel();
		contentPane.add(ObserveAction, "name_65504645385822");
		ObserveAction.setLayout(null);
		
		
//interface d'inscription
		final JPanel Inscription = new JPanel();
		contentPane.add(Inscription, "name_31675927568189");
		Inscription.setLayout(null);
				
//Contenu de l'interface connexion
		JButton btnNewButton = new JButton("Jouer une partie");
		btnNewButton.setBounds(125, 82, 230, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ControleurPartie.lancerNouvellePartie();
				} catch (ExceptionNoAdv e1) {
					// TODO Message erreur
					System.out.println("Il n'y a pas d'adversaire disponible");
				}
				pseudoAdv =BattleShip.partie.getPseudoAdv();
				JLabel lblVotre = new JLabel("Votre adversaire " + pseudoAdv + " est prêt");
				lblVotre.setBounds(50, 100, 400, 20);
				ChercheAdv.add(lblVotre);
						
				Connexion.setVisible(false);
				ChercheAdv.setVisible(true);
			}
		});
		Connexion.setLayout(null);
		Connexion.add(btnNewButton);
				
		JButton btnNewButton_1 = new JButton("Observer une partie");
		btnNewButton_1.setBounds(125, 156, 230, 25);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connexion.setVisible(false);
				OberservationPartie.setVisible(true);
				idPartie.setText("0");
				ControleurHistorique.lancerHistorique();
			}
		});
		Connexion.add(btnNewButton_1);
				
		JButton btnNewButton_2 = new JButton("Déconnexion");
		btnNewButton_2.setBounds(125, 193, 230, 25);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connexion.remove(3);
				Connexion.setVisible(false);
				Identification.setVisible(true);
			}
		});
		Connexion.add(btnNewButton_2);
		
		JButton btnReprendreUnePartie = new JButton("Reprendre une partie");
		btnReprendreUnePartie.setBounds(125, 119, 230, 25);
		Connexion.add(btnReprendreUnePartie);
		
		//Contenu de interface de connexion-> lancer une partie-> PrepareBataille
		JButton btnNewButton_6 = new JButton("Préparer Bataille ");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChercheAdv.setVisible(false);
				PrepareBataille.setVisible(true);
			}
		});
		btnNewButton_6.setBounds(125, 210, 200, 25);
		ChercheAdv.add(btnNewButton_6);
		
		JButton btnNewButton_4 = new JButton("Commencer le jeu");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Ship> batInit= new ArrayList<Ship>();
				for (Case[] l : map){
					for (Case c : l){
						if (c.isPivot()){
							try {
								int id = ControleurPartie.placerBateau(c.getX(), c.getY(), c.getDirBateau(), c.getType()+1);
								map1[c.getX()][c.getY()].creerBateau1(c.getType()+1, c.getDirBateau(), id);
							} catch (Exception e1) {
								System.out.println("Erreur placement bateau");
							}
						}
						c.reset();
					}
				}
				ControleurPartie.validerPlacement();
				ControleurPartie.debutTour();
				PrepareBataille.setVisible(false);
				Jouer.setVisible(true);
			}
		});
		btnNewButton_4.setBounds(5, 235, 160, 25);
		PrepareBataille.add(btnNewButton_4);
		
		JButton btnQuitter_1 = new JButton("Quitter");
		btnQuitter_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0; i<10; i++){
					for (int j=0; j<10; j++){
						map[i][j].reset();
					}	
				}
				PrepareBataille.setVisible(false);
				Connexion.setVisible(true);
			}
		});
		btnQuitter_1.setBounds(340, 235, 85, 25);
		PrepareBataille.add(btnQuitter_1);
		
		JLabel lblEscortreur = new JLabel("Destroyeur : 1");
		lblEscortreur.setBounds(330, 48, 100, 30);
		PrepareBataille.add(lblEscortreur);
		
		JLabel lblEscortreurPlac = new JLabel("Escorteur : 2");
		lblEscortreurPlac.setBounds(330, 76, 100, 30);
		PrepareBataille.add(lblEscortreurPlac);
		
		JLabel lblBateauxPlacs = new JLabel("Bateaux à placer");
		lblBateauxPlacs.setBounds(320, 12, 150, 30);
		PrepareBataille.add(lblBateauxPlacs);
		
		JButton btnAnnulerPlacement = new JButton("Annuler Placement");
		btnAnnulerPlacement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (int i=0; i<10; i++){
					for (int j=0; j<10; j++){
						map[i][j].reset();
					}	
				}
			}
		});
		btnAnnulerPlacement.setBounds(167, 235, 170, 25);
		PrepareBataille.add(btnAnnulerPlacement);
		
		

		map = new Case[10][10];
		for (int i=0; i<10; i++){
			for (int j=0; j<10; j++){
				Case C = new Case(i, j, 0, map);
				map[i][j]=C;
				PrepareBataille.add(C.getCell());	
			}	
		}
		Connexion.setVisible(false);
		
		
		
		//Contenu de interface de connexion-> lancer une partie-> PrepareBataille-> Jouer
		map1 = new Case[10][10];
		Case[][] map2 = new Case[10][10];
		for (int i=0; i<10; i++){
			for (int j=0; j<10; j++){
				Case C = new Case(i, j, 0, map1, map2);
				map1[i][j]=C;
				Jouer.add(C.getCell());
				Jouer.add(C.getCell1());
			}	
		}
		Connexion.setVisible(false);
		PrepareBataille.setVisible(false);
		
		JPanel ReprendrePartie = new JPanel();
		contentPane.add(ReprendrePartie, "name_11310189880415");
		
//Contenu interface de connexion -> Observer une partie 		
		JLabel lblNewLabel_2 = new JLabel("Identifiant de Partie");
		lblNewLabel_2.setBounds(56, 30, 165, 15);
		OberservationPartie.add(lblNewLabel_2);
		
		JLabel lblVainqueur = new JLabel("Vainqueur");
		lblVainqueur.setBounds(56, 130, 109, 15);
		OberservationPartie.add(lblVainqueur);
		
		idPartie = new JTextField();
		idPartie.setBounds(210, 30, 150, 20);
		OberservationPartie.add(idPartie);
		idPartie.setColumns(10);
		
		vainqueur = new JTextField();
		vainqueur.setColumns(10);
		vainqueur.setBounds(140, 128, 220, 20);
		OberservationPartie.add(vainqueur);
		
		JButton btnNewButton_3 = new JButton("Observer");
		btnNewButton_3.setFont(new Font("Dialog", Font.BOLD, 10));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OberservationPartie.setVisible(false);
				ObserveAction.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(5, 225, 100, 25);
		OberservationPartie.add(btnNewButton_3);
		
		//Contenu interface de connexion -> Observer une partie -> Observer
		JButton btnRafraichirObs = new JButton("Rafraichir");
		btnRafraichirObs.setBounds(60, 225, 117, 25);
		ObserveAction.add(btnRafraichirObs);
		
		JButton btnQuitter_2 = new JButton("Quitter");
		btnQuitter_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ObserveAction.setVisible(false);
				OberservationPartie.setVisible(true);
			}
		});
		btnQuitter_2.setBounds(245, 225, 117, 25);
		ObserveAction.add(btnQuitter_2);
		
		Case[][] map3 = new Case[10][10];
		Case[][] map4 = new Case[10][10];
		for (int i=0; i<10; i++){
			for (int j=0; j<10; j++){
				Case C = new Case(i, j, 0, map3, map4,1);
				map3[i][j]=C; //si problème au merge, renomme map3 en ce que tu veux mais pas map ou map1
				ObserveAction.add(C.getCell());
				ObserveAction.add(C.getCell1());
			}	
		}
		
		
		
		JButton btnNewB = new JButton("<Précedent");
		btnNewB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewB.setFont(new Font("Dialog", Font.BOLD, 10));
		btnNewB.setBounds(110, 225, 100, 25);
		OberservationPartie.add(btnNewB);
		
		JButton btnNewB_1 = new JButton("Suivant>");
		btnNewB_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControleurHistorique.suivant();
				idPartie.setText(String.valueOf(ControleurHistorique.suivant().getId())); 
				if (ControleurHistorique.suivant().getPseudo1()!= null ){
					pseudObs1= ControleurHistorique.suivant().getPseudo1();
					pseudObs2= ControleurHistorique.suivant().getPseudo2();
					if (pseudObs1!=null){
						textJoueurObs1.setText(pseudObs1);
						textJoueurObs2.setText(pseudObs2);
					}
				}
				if (ControleurHistorique.suivant().getVainqueur()!=null){
					vainqueur.setText(ControleurHistorique.suivant().getVainqueur());
				}else{
					vainqueur.setText("Partie en cours");
				}
				
			}
		});
		btnNewB_1.setFont(new Font("Dialog", Font.BOLD, 10));
		btnNewB_1.setBounds(215, 225, 100, 25);
		OberservationPartie.add(btnNewB_1);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OberservationPartie.setVisible(false);
				Connexion.setVisible(true);
			}
		});
		btnQuitter.setFont(new Font("Dialog", Font.BOLD, 10));
		btnQuitter.setBounds(320, 225, 100, 25);
		OberservationPartie.add(btnQuitter);
		
		JLabel lblVs = new JLabel("VS");
		lblVs.setBounds(195, 70, 45, 25);
		OberservationPartie.add(lblVs);
		
		textJoueurObs1 = new JTextField();
		textJoueurObs1.setText("Joueur1");
		textJoueurObs1.setBounds(56, 70, 120, 20);
		OberservationPartie.add(textJoueurObs1);
		textJoueurObs1.setColumns(10);
		
		textJoueurObs2 = new JTextField();
		textJoueurObs2.setText("Joueur2");
		textJoueurObs2.setColumns(10);
		textJoueurObs2.setBounds(240, 70, 120, 20);
		OberservationPartie.add(textJoueurObs2);
		

		
//Contenu de l'interface d'inscription
		JLabel lblVe = new JLabel("Veuillez remplir le formulaire suivant:");
		lblVe.setBounds(90, 5, 287, 21);
		Inscription.add(lblVe);
		
		JPanel Pseudo = new JPanel();
		Pseudo.setBounds(0, 25, 412, 45);
		Pseudo.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Pseudo", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Pseudo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Inscription.add(Pseudo);
		Pseudo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Entrez votre Pseudo");
		lblNewLabel.setBounds(12, 18, 153, 15);
		Pseudo.add(lblNewLabel);
		
		pseudo = new JTextField();
		pseudo.setBounds(211, 16, 189, 19);
		Pseudo.add(pseudo);
		pseudo.setColumns(10);
		
		JPanel InfosPerso = new JPanel();
		InfosPerso.setBounds(0, 72, 412, 102);
		InfosPerso.setBorder(new TitledBorder(null, "Informations Personnelles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Inscription.add(InfosPerso);
		InfosPerso.setLayout(null);
		
		JLabel label = new JLabel("Nom");
		label.setBounds(12, 20, 31, 15);
		InfosPerso.add(label);
		
		JLabel label_1 = new JLabel("Prénom");
		label_1.setBounds(12, 40, 54, 15);
		InfosPerso.add(label_1);
		
		JLabel label_2 = new JLabel("Adresse e-mail");
		label_2.setBounds(12, 80, 105, 15);
		InfosPerso.add(label_2);
		
		nom = new JTextField();
		nom.setBounds(211, 18, 189, 19);
		InfosPerso.add(nom);
		nom.setColumns(10);
		prenom = new JTextField();
		prenom.setColumns(10);
		prenom.setBounds(211, 38, 189, 19);
		InfosPerso.add(prenom);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(211, 77, 189, 19);
		InfosPerso.add(email);
		
		JLabel lblDateDeNaissance = new JLabel("Date de naissance");
		lblDateDeNaissance.setBounds(12, 60, 181, 15);
		InfosPerso.add(lblDateDeNaissance);
		
		txtJj = new JTextField();
		txtJj.setText("JJ");
		txtJj.setColumns(10);
		txtJj.setBounds(211, 58, 30, 19);
		InfosPerso.add(txtJj);
		
		txtMm = new JTextField();
		txtMm.setText("MM");
		txtMm.setColumns(10);
		txtMm.setBounds(261, 58, 30, 19);
		InfosPerso.add(txtMm);
		
		txtAnnee = new JTextField();
		txtAnnee.setText("ANNEE");
		txtAnnee.setColumns(10);
		txtAnnee.setBounds(311, 58, 89, 19);
		InfosPerso.add(txtAnnee);
		
		JPanel AdressPerso = new JPanel();
		AdressPerso.setBounds(0, 172, 412, 67);
		AdressPerso.setBorder(new TitledBorder(null, "Adresse Personnelle", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Inscription.add(AdressPerso);
		AdressPerso.setLayout(null);
		
		JLabel lblN = new JLabel("N°");
		lblN.setBounds(12, 20, 31, 15);
		AdressPerso.add(lblN);
		
		numeroRue = new JTextField();
		numeroRue.setBounds(34, 20, 46, 19);
		AdressPerso.add(numeroRue);
		numeroRue.setColumns(10);
		
		JLabel label_3 = new JLabel("Rue");
		label_3.setBounds(112, 20, 31, 15);
		AdressPerso.add(label_3);
		
		rue = new JTextField();
		rue.setColumns(10);
		rue.setBounds(146, 20, 254, 19);
		AdressPerso.add(rue);
		
		JLabel label_4 = new JLabel("Code Postal");
		label_4.setBounds(12, 45, 100, 15);
		AdressPerso.add(label_4);
		
		codePoste = new JTextField();
		codePoste.setColumns(10);
		codePoste.setBounds(103, 45, 63, 19);
		AdressPerso.add(codePoste);
		
		JLabel label_5 = new JLabel("Ville");
		label_5.setBounds(205, 45, 131, 15);
		AdressPerso.add(label_5);
		
		ville = new JTextField();
		ville.setColumns(10);
		ville.setBounds(247, 45, 153, 19);
		AdressPerso.add(ville);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ControleurConnexion.inscription(pseudo.getText(), nom.getText(), prenom.getText(), Integer.parseInt(txtJj.getText()), Integer.parseInt(txtMm.getText()), Integer.parseInt(txtAnnee.getText()), email.getText(), Integer.parseInt(numeroRue.getText()), rue.getText(), Integer.parseInt(codePoste.getText()), ville.getText());
					Inscription.setVisible(false);
					Identification.setVisible(true);
				} catch (NumberFormatException e1) {
					
					// TODO Auto-generated catch block
				} catch (InscriptionInvalideException e1) {
					// TODO Message Inscription invalide
					JOptionPane InscriptionInvalide = new JOptionPane(); 
					InscriptionInvalide.showMessageDialog(null, "Inscription invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
				} catch (UtilisateurExistantException e1) {
					JOptionPane UserExistant = new JOptionPane(); 
					UserExistant.showMessageDialog(null, "Ce pseudo est déjà  utilisé", "Warning", JOptionPane.WARNING_MESSAGE);
					// TODO Auto-generated catch block
				}
				
			}
		});
		btnValider.setBounds(77, 240, 117, 20);
		Inscription.add(btnValider);
		
		JButton btnNewButton_11 = new JButton("Annuler");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Inscription.setVisible(false);
				Identification.setVisible(true);
			}
		});
		btnNewButton_11.setBounds(257, 240, 117, 20);
		Inscription.add(btnNewButton_11);
		Inscription.setVisible(false);

//Contenu de l'interface Identification
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ControleurConnexion.connexion(txtLogin.getText());
					pseudoJo = BattleShip.user.getPseudo();
					JLabel lblNewLabel_1 = new JLabel("Bienvenue " + pseudoJo);
					lblNewLabel_1.setBounds(164, 51, 230, 15);
					Connexion.add(lblNewLabel_1);
					Connexion.setVisible(true);
					Identification.setVisible(false);
				} catch (UtilisateurInconnuException e) {
					JOptionPane IdInconnue = new JOptionPane(); 
					IdInconnue.showMessageDialog(null, "Utilisateur inconnu", "Erreur", JOptionPane.ERROR_MESSAGE); 
				}
			}
		});
		btnConnexion.setBounds(296, 74, 117, 25);
		Identification.add(btnConnexion);
		
		JButton btnInscription = new JButton("Inscription");
		btnInscription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inscription.setVisible(true);
				Identification.setVisible(false);
			}
		});
		btnInscription.setBounds(296, 138, 117, 25);
		Identification.add(btnInscription);
		
		txtLogin = new JTextField();
		txtLogin.setText("Login");
		txtLogin.setBounds(119, 75, 153, 19);
		Identification.add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblEntrerLogin = new JLabel("Entrer Login");
		lblEntrerLogin.setBounds(12, 77, 89, 15);
		Identification.add(lblEntrerLogin);
		
		JLabel lblBienvenue = new JLabel("Bienvenue");
		lblBienvenue.setFont(new Font("Dialog", Font.BOLD, 20));
		lblBienvenue.setBounds(150, 12, 142, 50);
		Identification.add(lblBienvenue);
		
		JButton btnNewButton_10 = new JButton("Quitter");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BattleShip.theConnection.close();
					ControleurConnexion.quitter();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contentPane.setVisible(false);
			}
		});
		btnNewButton_10.setBounds(296, 198, 117, 25);
		Identification.add(btnNewButton_10);
			
	}
}
