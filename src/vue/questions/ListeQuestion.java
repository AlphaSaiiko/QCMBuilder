package vue.questions;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controleur.Controleur;
import controleur.ControleurFichier;
import modele.Notion;
import modele.Question;
import modele.Ressource;

public class ListeQuestion extends JFrame implements ActionListener 
{

	private JComboBox lstRessources;
	private JComboBox lstNotions;

	private String nomRessource;

	private DefaultListModel<String> lstModeleQuestion;
	private JList<String> lstQuestion;

	private Question questionActuelle;

	private VoirQuestion voirQuestion;
	private CreerQuestion modificationQst;

	public ListeQuestion() 
	{
		// Paramètres de la Frame
		this.setTitle("Liste des questions");
		this.setSize(850, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new BorderLayout());

		// Création du panel qui contient le bouton retour et les listes
		JPanel panelHaut = new JPanel();
		panelHaut.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());

		// Création du panel qui contient tous les boutons en bas de la fenètre
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Bouton "Menu principal"
		String imageMenu = "./lib/icones/home.png";
		ImageIcon icon = new ImageIcon(imageMenu);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);

		JButton btnMenu = new JButton(icon);
		btnMenu.addActionListener(e -> 
		{
			Controleur.ouvrirAccueil();
			dispose();
		});

		// Creation d'une JComboBox pour les ressources et matières
		this.lstRessources = new JComboBox<String>(Controleur.getIDsNomsRessources());
		this.lstRessources.setPreferredSize(new Dimension(450, 30));
		this.lstRessources.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 10)); // Espacement

		this.nomRessource = String.valueOf(lstRessources.getSelectedItem());
		String[] decRessource = nomRessource.split("_", 2);
		String id = "", nom = "";
		if (decRessource.length == 2) 
		{
			nom = decRessource[1];
			id = decRessource[0];
		}

		this.lstRessources.addActionListener(this);

		this.lstNotions = new JComboBox<String>(Controleur.trouverRessourceParId(id).getNomsNotions());
		lstNotions.setPreferredSize(new Dimension(300, 30));
		lstNotions.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Espacement

		this.lstNotions.addActionListener(this);

		panelHaut.add(btnMenu);
		panelHaut.add(this.lstRessources);
		panelHaut.add(this.lstNotions);

		// Ajout du bouton retour et des listes à la Frame
		this.add(panelHaut, BorderLayout.NORTH);

		// Liste des questions
		this.lstModeleQuestion = new DefaultListModel<>();
		this.lstQuestion = new JList<>(lstModeleQuestion);

		this.majListeQuestion(String.valueOf(ListeQuestion.this.lstNotions.getSelectedItem()));

		panelPrincipal.add(new JScrollPane(this.lstQuestion), BorderLayout.CENTER);
		this.add(panelPrincipal, BorderLayout.CENTER);

		lstQuestion.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) 
		{
				Notion not = Controleur.trouverNotionParNom(String.valueOf(ListeQuestion.this.lstNotions.getSelectedItem()));

				if (!e.getValueIsAdjusting()) 
				{
					if (not != null)
					{
						for (Question qst : not.getListeQuestions()) 
						{
							if (qst.getEnonce() == ListeQuestion.this.lstQuestion.getSelectedValue()) 
							{
								ListeQuestion.this.questionActuelle = qst;
							}
						}
					}
					else
					{
						System.err.println("Erreur : Notion non trouvée");
					}
					
				}
			}
		});

		Notion not = Controleur.trouverNotionParNom(String.valueOf(ListeQuestion.this.lstNotions.getSelectedItem()));

		for (Question qst : not.getListeQuestions()) 
		{
			if (qst.getEnonce() == ListeQuestion.this.lstQuestion.getSelectedValue()) 
			{
				ListeQuestion.this.questionActuelle = qst;
			}
		}

		// Création des boutons en bas
		JButton btnVoir = new JButton("Voir attributs");

		btnVoir.addActionListener(e -> 
		{

			if (this.voirQuestion != null) 
			{
				this.voirQuestion.dispose();
			}

			if (this.questionActuelle != null)
			{
				this.voirQuestion = new VoirQuestion(this.questionActuelle);
			}	

		});

		JButton btnModifier = new JButton("Modifier");

		btnModifier.addActionListener(e -> 
		{

			if (this.modificationQst != null) 
			{
				this.modificationQst.dispose();
			}

			if (this.questionActuelle != null)
			{
				this.modificationQst = Controleur.ouvrirCreerQuestion(this.questionActuelle);
				this.dispose();
			}

		});

		JButton btnSupprimer = new JButton("Supprimer");

		btnSupprimer.addActionListener(e -> 
		{

			if (this.questionActuelle != null) 
			{
				Controleur.supprQuestion(this.questionActuelle);

				Notion notionActuelle = this.questionActuelle.getNotion();

				ControleurFichier ctrlFichier = new ControleurFichier(
						"lib/ressources/" + notionActuelle.getRessource().getId() + "_"
								+ notionActuelle.getRessource().getNom() + "/"
								+ notionActuelle.getNom() + "/");
								
				if (this.questionActuelle.getComplements().size() > 0)
				{
					for (String fichier : this.questionActuelle.getComplements())
					{
						(new ControleurFichier("")).supprimerFichier(fichier);
					}
				}

				ctrlFichier.supprimerRtf("question" + this.questionActuelle.getNumQuestion() + "/question" + this.questionActuelle.getNumQuestion());


				majListeQuestion(notionActuelle.getNom());
			}

		});

		panelBoutons.add(btnVoir);
		panelBoutons.add(btnModifier);
		panelBoutons.add(btnSupprimer);

		this.add(panelBoutons, BorderLayout.SOUTH);

	}

	// Méthode pour mettre à jour la liste des notions en fonction de la ressource
	// sélectionnée
	private void majListeQuestion(String nomNotion) 
	{

		lstModeleQuestion.clear();

		Notion notion = null;
		if (nomNotion != null) 
		{
			notion = Controleur.trouverNotionParNom(nomNotion);
		}

		if (notion != null) 
		{
			for (Question question : notion.getListeQuestions()) 
			{
				lstModeleQuestion.addElement(question.getEnonce());
				System.out.println(question);
			}

			if (lstQuestion.getModel().getSize() > 0)
				lstQuestion.setSelectedIndex(0);
			
		}

	}

	/**
	 * Gère l'événement déclenché par une action de l'utilisateur.
	 *
	 * Cette méthode est appelée lorsqu'une action est effectuée (comme un clic ou
	 * une sélection).
	 * Elle met à jour la liste déroulante des notions (`listeNotions`) en fonction
	 * de la ressource
	 * actuellement sélectionnée dans la liste déroulante des ressources
	 * (`listeRessources`).
	 *
	 * @param e l'événement déclenché par une action de l'utilisateur.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.lstRessources) 
		{
			this.lstNotions.removeAllItems();

			this.nomRessource = String.valueOf(this.lstRessources.getSelectedItem());

			String[] decRessource = this.nomRessource.split("_", 2);
			String id = "", nom = "";
			if (decRessource.length == 2) 
			{
				nom = decRessource[1];
				id = decRessource[0];
			}

			for (String nomNotion : Controleur.trouverRessourceParId(id).getNomsNotions())
				this.lstNotions.addItem(nomNotion);
		}

		if (e.getSource() == this.lstNotions) 
		{

			String notionActuelle = String.valueOf(ListeQuestion.this.lstNotions.getSelectedItem());
			majListeQuestion(notionActuelle);

			if (notionActuelle != null) 
			{
				// ajtNotion.setBackground(Color.GREEN);
				// ajtNotion.setEnabled(true);
			} else 
			{
				// ajtNotion.setBackground(Color.GRAY);
				// ajtNotion.setEnabled(false);

			}

		}

	}

}
