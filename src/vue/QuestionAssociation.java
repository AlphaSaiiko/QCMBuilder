package vue;

import controleur.Controleur;
import controleur.ControleurFichier;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import modele.Notion;
import modele.Question;
import modele.option.IOption;
import modele.option.Option;
import modele.option.OptionAssociation;
import modele.option.OptionElimination;

public class QuestionAssociation extends JFrame
{
	/**
	 * +-------------+
	 * |  ATTRIBUTS  |
	 * +-------------+
	 */

		private       Question    question              ;
		private       JPanel      panelQuestion         ;
		private       JPanel      panelOptions          ;
		private       PanelSaisie panelEnonce           ;
		private       PanelSaisie panelFeedback         ;
		private       int         nbOptions        = 0  ;
		private final int         nbMaxOptions     = 6  ;
		private final int         HAUTEUR_OPTIONS  = 150;


	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public QuestionAssociation(Question question)
	{
		this.question = question;

		final Question ancienneQst;
		if (question.getEnsOptions().size() > 0)
		{
			ancienneQst = question;
		}
		else
		{
			ancienneQst = null;
		}

		// Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());

		// Bouton "Retour" en haut à gauche
		JButton btnRetour = new JButton("Retour");

		btnRetour.addActionListener(e ->
		{
			Controleur.ouvrirCreerQuestion();
			dispose();
		});

		JPanel panelRetour = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelRetour.add(btnRetour);

		// Ressource et la notion en haut à gauche
		JLabel lblRessourceNotion = new JLabel(
			"Ressource : " + question.getNotion().getRessource().getId() + "_" + question.getNotion().getRessource().getNom() + 
			", notion : " + question.getNotion().getNom()
		);
		panelRetour.add(lblRessourceNotion);
		this.add(panelRetour, BorderLayout.NORTH);

		// Panel de la question (texte et options)
		panelQuestion = new JPanel(new BorderLayout());

		// PanelSaisie pour l'énoncé
		JPanel panelEnonceWrapper = new JPanel(new BorderLayout());
		JLabel lblEnonce = new JLabel("Énoncé");
		panelEnonceWrapper.add(lblEnonce, BorderLayout.NORTH);
		panelEnonce = new PanelSaisie();
		Dimension dimensionsEnonce = new Dimension(0, 200);
		panelEnonce.setPreferredSize(dimensionsEnonce);
		panelEnonce.setMinimumSize(dimensionsEnonce);
		panelEnonce.setMaximumSize(dimensionsEnonce);
		panelEnonceWrapper.add(panelEnonce, BorderLayout.CENTER);
		panelQuestion.add(panelEnonceWrapper, BorderLayout.NORTH);

		// PanelSaisie pour l'explication
		JPanel panelFeedbackWrapper = new JPanel(new BorderLayout());
		JLabel lblExplication = new JLabel("Explication");
		panelFeedbackWrapper.add(lblExplication, BorderLayout.NORTH);
		panelFeedback = new PanelSaisie();
		Dimension dimensionsExplication = new Dimension(0, 200);
		panelFeedback.setPreferredSize(dimensionsExplication);
		panelFeedback.setMinimumSize(dimensionsExplication);
		panelFeedback.setMaximumSize(dimensionsExplication);
		panelFeedbackWrapper.add(panelFeedback, BorderLayout.CENTER);
		panelQuestion.add(panelFeedbackWrapper, BorderLayout.CENTER);

		// Panel pour les options
		panelOptions = new JPanel();
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));
		panelQuestion.add(panelOptions, BorderLayout.SOUTH);

		// Panel pour les boutons
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Bouton "Ajouter"
		JButton btnAjouter = new JButton("Ajouter une option");

		btnAjouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ajouterOption(null);
			}
		});

		// Bouton "Enregistrer"
		JButton btnEnregistrer = new JButton("Enregistrer");

		btnEnregistrer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Vérification si l'une des réponses n'est pas remplie
				boolean reponsesRemplies = true;

				if (nbOptions < 1)
					reponsesRemplies = false;

				if (nbOptions > 0)
				{
					for (int i = 0; i < panelOptions.getComponentCount(); i++) 
					{
						Component composant = panelOptions.getComponent(i);
						
						JPanel panelOption = (JPanel) composant;
					

						// Récupérez les sous-composants du JPanel
						Component[] composantsOption = panelOption.getComponents();

						PanelSaisie panelSaisieOption1 = (PanelSaisie) composantsOption[1];
						PanelSaisie panelSaisieOption2 = (PanelSaisie) composantsOption[2];

						String stringOption1 = panelSaisieOption1.getContenu();
						String stringOption2 = panelSaisieOption2.getContenu();

						// Vérifiez les champs de texte vides
						if (stringOption1.isEmpty() || stringOption2.isEmpty()) 
							reponsesRemplies = false;
					}
				}
					
				String erreurs = "";

				if (! reponsesRemplies)                                                 { erreurs += "N'oubliez pas de remplir toutes les réponses.\n"          ; }
				if (nbOptions < 2)                                                      { erreurs += "Vous devez avoir au moins deux lignes d'options. \n"      ; }
				if (nbOptions > nbMaxOptions)                                           { erreurs += "Vous ne pouvez avoir que six lignes d'options maximum. \n"; }
				if (QuestionAssociation.this.panelEnonce.getContenu().trim().isEmpty()) { erreurs += "Veuillez remplir la question. \n"                         ; }

				if (erreurs.trim().isEmpty())
				{
					
					

					// Enregistrer l'énoncé
					question.setEnonce(panelEnonce.getContenu());

					// Enregistrer l'explication
					if (! panelFeedback.getContenu().trim().isEmpty())
						question.setFeedback(panelFeedback.getContenu());

					// Enregistrer les réponses
					for (int i = 0; i < panelOptions.getComponentCount(); i++)
					{
						Component composant = panelOptions.getComponent(i);
						
						JPanel panelOption = (JPanel) composant;
					

						// Récupérez les sous-composants du JPanel
						Component[] composantsOption = panelOption.getComponents();
					
						try 
						{
							PanelSaisie panelSaisieOption1 = (PanelSaisie) composantsOption[1];
							PanelSaisie panelSaisieOption2 = (PanelSaisie) composantsOption[2];

							String stringOption1 = panelSaisieOption1.getContenu();
							String stringOption2 = panelSaisieOption2.getContenu();

							System.out.println("Réponse 1 : " + stringOption1);
							System.out.println("Réponse 2 : " + stringOption2);

							if (stringOption1.isEmpty() || stringOption2.isEmpty())
							{
								JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs de texte.", "Erreur", JOptionPane.ERROR_MESSAGE);
								return;
							}

							OptionAssociation option1 = Controleur.creerReponseAssociation(stringOption1, question);
							OptionAssociation option2 = Controleur.creerReponseAssociation(stringOption2, question);

							option1.setAssocie(option2);
							option2.setAssocie(option1);

							question.ajouterOption(option1);
							question.ajouterOption(option2);
							
							System.out.println("Options ajoutées avec succès.");
						}
						catch (Exception ex)
						{
							System.err.println("Erreur lors de la récupération des composants: " + ex.getMessage());
						}
					}


					Notion notionActuelle = question.getNotion();

					ControleurFichier ctrlFichier = new ControleurFichier(
							"lib/ressources/" + notionActuelle.getRessource().getId() + "_"
									+ notionActuelle.getRessource().getNom() + "/"
									+ notionActuelle.getNom() + "/");

					// Enregistrer la question dans un fichier
					if (ancienneQst == null) question.creerFichierQuestion();
					else                     ctrlFichier.ecrireQuestion("question" + question.getNumQuestion() + "/question" + question.getNumQuestion(), question);
					
					// Fermer la fenêtre
					QuestionAssociation.this.dispose();
					new Accueil();
				}

				else JOptionPane.showMessageDialog(QuestionAssociation.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});

		// Ajout des boutons au JPanel contenant les boutons
		panelBoutons.add(btnAjouter);
		panelBoutons.add(btnEnregistrer);

		// Ajout des composants au panel principal
		JScrollPane scrollPaneQuestion = new JScrollPane(panelQuestion);
		scrollPaneQuestion.getVerticalScrollBar().setUnitIncrement(16);
		panelPrincipal.add(scrollPaneQuestion);
		panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

		// Modifications si la question n'est pas nouvelle, est à modifier
		if (ancienneQst != null)
		{
			// Placer l'enoncé de la question et du feedback
			if (ancienneQst.getEnonce() != null)
				if (! ancienneQst.getEnonce().trim().isEmpty())
					this.panelEnonce.setContenu(ancienneQst.getEnonce());

			if (ancienneQst.getFeedback() != null)
				if (! ancienneQst.getFeedback().trim().isEmpty())
					this.panelFeedback.setContenu(ancienneQst.getFeedback());

			// Ajouter les options de la question
			if (ancienneQst.getEnsOptions() != null)
			{
				for (IOption opt : ancienneQst.getEnsOptions())
				{
					if (opt instanceof OptionAssociation && ancienneQst.getType().equals("QAE") && (opt.getId()%2==0))
					{
						this.ajouterOption((OptionAssociation) opt);
					}
				}
			}
		}


		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setTitle("Question Association");
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	public void ajouterOption(OptionAssociation opt)
	{
		// Cas ou il y a trop d'options
		if (nbOptions >= nbMaxOptions && opt == null)
		{
			JOptionPane.showMessageDialog(
				null,
				"Le nombre maximum de réponses est atteint (" + nbMaxOptions + ").",
				"Erreur",
				JOptionPane.ERROR_MESSAGE
			);

			return;
		}

		// Panel contenant l'option
		JPanel panelOption = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		// Bouton "Supprimer"
		ImageIcon iconeSupprimer = new ImageIcon(
			new ImageIcon("./lib/icones/delete.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)
		);

		JButton btnSupprimer = new JButton(iconeSupprimer);
		btnSupprimer.setPreferredSize(new Dimension(40, 40));
		btnSupprimer.setBorderPainted(false);
		btnSupprimer.setContentAreaFilled(false);
		btnSupprimer.setFocusPainted(false);
		btnSupprimer.setOpaque(false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.NONE;
		panelOption.add(btnSupprimer, gbc);

		btnSupprimer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				panelOptions.remove(panelOption);
				nbOptions--;
				panelOptions.revalidate();
				panelOptions.repaint();
			}
		});

		// Premier élément
		PanelSaisie element1 = new PanelSaisie(false);
		element1.setHauteur(HAUTEUR_OPTIONS);
		if (opt != null) element1.setContenu(opt.getEnonce());

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1.0;
		panelOption.add(element1, gbc);

		// Second élément
		PanelSaisie element2 = new PanelSaisie(false);
		element2.setHauteur(HAUTEUR_OPTIONS);
		if (opt != null) element2.setContenu(opt.getAssocie().getEnonce());

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1.0;
		panelOption.add(element2, gbc);

		// Ajout du Panel de l'option au panel des options
		panelOptions.add(panelOption);
		nbOptions++;
		panelOptions.revalidate();
		panelOptions.repaint();
	}
}
