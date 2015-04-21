package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import ihm.org.eclipse.wb.swing.FocusTraversalOnArray;

import net.miginfocom.swing.MigLayout;



public class Connexion extends JFrame {

	private JPanel contentPane;
	private JTextField txtLogin;
	private JPanel Connexion;
	private JPanel Inscription;
	private JPanel Identification;
	private JPanel Jeu;
	private JPanel OberservationPartie;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField txtAnnee;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField txtJj;
	private JTextField txtMm;
	private JTextField txtAnnee_1;
	private JTextField textField_3;
	private JTextField textField_8;
	private JTable Carte;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
	 */
	public Connexion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		Connexion.setLayout(null);
//interface de connexion -> lancer une partie
		final JPanel PrepareBataille = new JPanel();
		contentPane.add(PrepareBataille, "name_43179446908468");
		PrepareBataille.setLayout(null);
		
		Carte = new JTable(new Object[10][10], new Object[10][10]); 
		Carte.setCellSelectionEnabled(true);
		Carte.setBounds(0, 0, 430, 160);
		PrepareBataille.add(Carte);
		Connexion.setVisible(false);
//interface de connexion -> Observer une partie 
		final JPanel OberservationPartie = new JPanel();
		contentPane.add(OberservationPartie, "name_2587200245776");
		OberservationPartie.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Identifiant de Partie");
		lblNewLabel_2.setBounds(56, 84, 165, 15);
		OberservationPartie.add(lblNewLabel_2);
		
		JLabel lblEtatDePartie = new JLabel("Etat de Partie");
		lblEtatDePartie.setBounds(56, 138, 109, 15);
		OberservationPartie.add(lblEtatDePartie);
		
		textField_3 = new JTextField();
		textField_3.setBounds(210, 82, 150, 20);
		OberservationPartie.add(textField_3);
		textField_3.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(210, 136, 150, 20);
		OberservationPartie.add(textField_8);
		
		JButton btnNewButton_3 = new JButton("Observer");
		btnNewButton_3.setFont(new Font("Dialog", Font.BOLD, 10));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_3.setBounds(5, 225, 100, 25);
		OberservationPartie.add(btnNewButton_3);
		
		JButton btnNewB = new JButton("<Précedent");
		btnNewB.setFont(new Font("Dialog", Font.BOLD, 10));
		btnNewB.setBounds(110, 225, 100, 25);
		OberservationPartie.add(btnNewB);
		
		JButton btnNewB_1 = new JButton("Suivant>>");
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
//interface d'inscription
		final JPanel Inscription = new JPanel();
		contentPane.add(Inscription, "name_31675927568189");
		Inscription.setLayout(null);
		
//Contenu de l'interface connexion
		JLabel lblNewLabel_1 = new JLabel("Vous Souhaitez: ");
		lblNewLabel_1.setBounds(164, 51, 130, 15);
		Connexion.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Jouer une partie");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connexion.setVisible(false);
				PrepareBataille.setVisible(true);
			}
		});
		btnNewButton.setBounds(125, 102, 230, 25);
		Connexion.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Observer une partie");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connexion.setVisible(false);
				OberservationPartie.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(125, 154, 230, 25);
		Connexion.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Vous déconnecter");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connexion.setVisible(false);
				Identification.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(125, 213, 230, 25);
		Connexion.add(btnNewButton_2);
		
//Contenu de l'interface d'inscription
		JLabel lblVe = new JLabel("Veuillez remplir le formulaire suivant:");
		lblVe.setBounds(90, 5, 287, 21);
		Inscription.add(lblVe);
		
		JPanel Pseudo = new JPanel();
		Pseudo.setBounds(0, 25, 412, 45);
		Pseudo.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Pseudo", TitledBorder.LEADING, TitledBorder.TOP, null, null), "Pseudo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Inscription.add(Pseudo);
		Pseudo.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Entrer votre Pseudo");
		lblNewLabel.setBounds(12, 18, 153, 15);
		Pseudo.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(211, 16, 189, 19);
		Pseudo.add(textField);
		textField.setColumns(10);
		
		JPanel InfosPerso = new JPanel();
		InfosPerso.setBounds(0, 72, 412, 102);
		InfosPerso.setBorder(new TitledBorder(null, "Informations Personnelles", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Inscription.add(InfosPerso);
		InfosPerso.setLayout(null);
		
		JLabel label = new JLabel("Nom");
		label.setBounds(12, 20, 31, 15);
		InfosPerso.add(label);
		
		JLabel label_1 = new JLabel("Prenom");
		label_1.setBounds(12, 40, 54, 15);
		InfosPerso.add(label_1);
		
		JLabel label_2 = new JLabel("Adresse e-mail");
		label_2.setBounds(12, 80, 105, 15);
		InfosPerso.add(label_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(211, 18, 189, 19);
		InfosPerso.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(211, 38, 189, 19);
		InfosPerso.add(textField_2);
		
		txtAnnee = new JTextField();
		txtAnnee.setColumns(10);
		txtAnnee.setBounds(211, 77, 189, 19);
		InfosPerso.add(txtAnnee);
		
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
		
		txtAnnee_1 = new JTextField();
		txtAnnee_1.setText("ANNEE");
		txtAnnee_1.setColumns(10);
		txtAnnee_1.setBounds(311, 58, 89, 19);
		InfosPerso.add(txtAnnee_1);
		
		JPanel AdressPerso = new JPanel();
		AdressPerso.setBounds(0, 172, 412, 67);
		AdressPerso.setBorder(new TitledBorder(null, "Adresse Personnelle", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Inscription.add(AdressPerso);
		AdressPerso.setLayout(null);
		
		JLabel lblN = new JLabel("N°");
		lblN.setBounds(12, 20, 31, 15);
		AdressPerso.add(lblN);
		
		textField_4 = new JTextField();
		textField_4.setBounds(34, 20, 46, 19);
		AdressPerso.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel label_3 = new JLabel("Rue");
		label_3.setBounds(112, 20, 31, 15);
		AdressPerso.add(label_3);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(146, 20, 254, 19);
		AdressPerso.add(textField_5);
		
		JLabel label_4 = new JLabel("Code Postal");
		label_4.setBounds(12, 45, 100, 15);
		AdressPerso.add(label_4);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(103, 45, 63, 19);
		AdressPerso.add(textField_6);
		
		JLabel label_5 = new JLabel("Ville");
		label_5.setBounds(205, 45, 131, 15);
		AdressPerso.add(label_5);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(247, 45, 153, 19);
		AdressPerso.add(textField_7);
		
		JButton btnValider = new JButton("Valider");
		btnValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inscription.setVisible(false);
				Identification.setVisible(true);
			}
		});
		btnValider.setBounds(157, 240, 117, 20);
		Inscription.add(btnValider);
		Inscription.setVisible(false);
		
		

//Contenu de l'interface Identification
		JButton btnConnexion = new JButton("Connexion");
		btnConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connexion.setVisible(true);
				Identification.setVisible(false);
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
		
		
		
		
		
	}
}
