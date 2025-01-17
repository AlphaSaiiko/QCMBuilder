package vue.questions;

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
import modele.option.OptionElimination;
import vue.Accueil;
import vue.PanelSaisie;

public class QuestionElimination extends JFrame
{
	/**
	 * +-------------+
	 * |  ATTRIBUTS  |
	 * +-------------+
	 */

	private       Question    question              ;
	private       JPanel      panelQuestion         ;
	private       JPanel      panelBoutons          ;
	private       JPanel      panelOptions          ;
	private       PanelSaisie panelEnonce           ;
	private       PanelSaisie panelFeedback         ;
	private       ButtonGroup groupeBtnsRadio       ;
	private       int         nbOptions        = 0  ;
	private final int         nbMaxOptions     = 6  ;
	private final int         HAUTEUR_OPTIONS  = 150;
	private final int         LARGEUR_OPTIONS  = 600;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public QuestionElimination(Question question)
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
		panelEnonce = new PanelSaisie();
		Dimension dimensionsEnonce = new Dimension(0, 400);
		panelEnonce.setPreferredSize(dimensionsEnonce);
		panelEnonce.setMinimumSize(dimensionsEnonce);
		panelEnonce.setMaximumSize(dimensionsEnonce);
		panelQuestion.add(panelEnonce, BorderLayout.NORTH);

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

		// Panel pour les options
		panelOptions = new JPanel();
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));

		// Nouveau panneau intermédiaire
		JPanel panelCentre = new JPanel();
		panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
		panelCentre.add(panelFeedbackWrapper); // Ajoute le panel d'explication
		panelCentre.add(panelOptions);            // Ajoute le panel des options

		// Ajoute le panneau combiné au BorderLayout.CENTER de panelQuestion
		panelQuestion.add(panelCentre, BorderLayout.CENTER);



		// Panel pour les boutons
        panelBoutons = new JPanel();
        panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));


		// Initialiser le groupe de boutons radio
		groupeBtnsRadio = new ButtonGroup();


		// Ajouter un ActionListener au bouton "Ajouter"
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
				// Vérification si la question à une réponse : AKA si une des options est valide
				// Vérification si l'une des réponses n'est pas remplie
				// Vérification si l'une des réponse est illégale (Si une réponse valide à un ordre et un point en moins)
				boolean aReponse         = false;
				boolean reponsesRemplies = true ;

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

						PanelSaisie  panelSaisieOption = (PanelSaisie ) composantsOption[1];
						JRadioButton btnRadio          = (JRadioButton) composantsOption[6];


						// Vérifier les champs de texte vides
						if (panelSaisieOption.getContenu().isEmpty()) 
							reponsesRemplies = false;


						// Vérifier si il y a une bonne réponse
						if (btnRadio.isSelected())
							aReponse = true;
					}
				}
					
				String erreurs = "";

				if (! aReponse)                                                         { erreurs += "N'oubliez pas de sélectionner une réponse.\n"    ; }
				if (! reponsesRemplies)                                                 { erreurs += "N'oubliez pas de remplir toutes les réponses.\n" ; }
				if (nbOptions < 2)                                                      { erreurs += "Vous devez avoir au moins deux options. \n"      ; }
				if (nbOptions > nbMaxOptions)                                           { erreurs += "Vous ne pouvez avoir que six options maximum. \n"; }
				if (QuestionElimination.this.panelEnonce.getContenu().trim().isEmpty()) { erreurs += "Veuillez remplir la question. \n"                ; }

				if (erreurs.trim().isEmpty())
				{
					
					

					// Enregistrer l'énoncé	
					question.setEnonce(panelEnonce.getContenu());

					// Enregistrer l'explication
					if (! panelFeedback.getContenu().isEmpty())
						question.setFeedback(panelFeedback.getContenu());


					if (ancienneQst != null) question.supprimerAllOptions();
					
					//Enregistrer les réponses
					for (int i = 0; i < panelOptions.getComponentCount(); i++)
					{
						Component composant = panelOptions.getComponent(i);

						JPanel panelOption = (JPanel) composant;
				

						// Récupérez les sous-composants du JPanel
						Component[] composantsOption = panelOption.getComponents();

						PanelSaisie  panelSaisieOption = (PanelSaisie ) composantsOption[1];
						JTextField   ordre             = (JTextField  ) composantsOption[3];
						JTextField   points            = (JTextField  ) composantsOption[5];
						JRadioButton btnRadio          = (JRadioButton) composantsOption[6];
					
						if (ordre.getText().isEmpty())
						{
							ordre.setText("-1");
						}
						
						if (points.getText().isEmpty()) 
						{
							points.setText("-1");
						}

						OptionElimination option = Controleur.creerReponseElimination(panelSaisieOption.getContenu(), Integer.parseInt(ordre.getText()), Double.parseDouble(points.getText()), btnRadio.isSelected(), question);

						question.ajouterOption(option);
					}
					

					Notion notionActuelle = question.getNotion();

					ControleurFichier ctrlFichier = new ControleurFichier(
							"lib/ressources/" + notionActuelle.getRessource().getId() + "_"
									+ notionActuelle.getRessource().getNom() + "/"
									+ notionActuelle.getNom() + "/");

					// Enregistrer la question dans un fichier
					if (ancienneQst == null) question.creerFichierQuestion();
					else 
					{
						ctrlFichier.ecrireQuestion("question" + question.getIdQuestion() + "/question" + question.getIdQuestion(), question);
						ctrlFichier.modifierQuestion("question" + question.getIdQuestion() + "/question" + question.getIdQuestion(), question);
					}
					
					// Fermer la fenêtre
					QuestionElimination.this.dispose();
					new Accueil();
				}
				else JOptionPane.showMessageDialog(QuestionElimination.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);
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
					if (opt instanceof OptionElimination && ancienneQst.getType().equals("QAEPR"))
					{
						this.ajouterOption((OptionElimination) opt);
					}
				}
			}
			
			majLegalite();
		}

		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setTitle("Question Elimination");
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void majLegalite()
	{
		if (nbOptions > 0)
		{
			for (int i = 0; i < panelOptions.getComponentCount(); i++)
			{
				Component composant = panelOptions.getComponent(i);

				JPanel panelOption = (JPanel) composant;
		

				// Récupérez les sous-composants du JPanel
				Component[] composantsOption = panelOption.getComponents();

				JTextField   ordre             = (JTextField  ) composantsOption[3];
				JTextField   points            = (JTextField  ) composantsOption[5];
				JRadioButton radioButton       = (JRadioButton) composantsOption[6];
				
				if (radioButton.isSelected())
				{
					ordre .setEnabled(false);
					points.setEnabled(false);
					ordre .setText("");
					points.setText("");
				}
				else
				{
					ordre .setEnabled(true);
					points.setEnabled(true);
				}

				if (ordre.getText().trim().length() > 0 || points.getText().trim().length() > 0)
				{
					radioButton.setEnabled(false);
				}
				else
				{
					radioButton.setEnabled(true);
					ordre .setText("");
					points.setText("");
				}
			}
				
		}

	}

	
	public void ajouterOption(OptionElimination opt)
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


		// Panel de saisie pour l'option
		PanelSaisie panelSaisieOption = new PanelSaisie(false);
		panelSaisieOption.setHauteur(HAUTEUR_OPTIONS);
		panelSaisieOption.setLargeur(LARGEUR_OPTIONS);
		if (opt != null) panelSaisieOption.setContenu(opt.getEnonce());

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1.0;
		panelOption.add(panelSaisieOption, gbc);


		// Label "Ordre :"
		JLabel labelOrdre = new JLabel("Ordre :");

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		panelOption.add(labelOrdre, gbc);


		// JTextField pour l'ordre
		JTextField texteOrdre = new JTextField();
		texteOrdre.setPreferredSize(new Dimension(30, 30));
		if (opt != null) texteOrdre.setText(String.valueOf(opt.getOrdre()));

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		panelOption.add(texteOrdre, gbc);

		texteOrdre.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				majLegalite();
			}
		});


		// Label "Points :"
		JLabel labelPoints = new JLabel("Points :");

		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		panelOption.add(labelPoints, gbc);


		// JTextField pour les points
		JTextField textePoints = new JTextField();
		textePoints.setPreferredSize(new Dimension(30, 30));
		if (opt != null) textePoints.setText(String.valueOf(opt.getNbPointsMoins()));

		gbc.gridx = 5;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		panelOption.add(textePoints, gbc);

		textePoints.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				majLegalite();
			}
		});


		// Bouton radio pour sélectionner la bonne réponse
		JRadioButton btnRadio = new JRadioButton();
		btnRadio.setPreferredSize(new Dimension(30, 30));
		if (opt != null) 
			if (opt.getEstReponse())
				btnRadio.setSelected(true);

		gbc.gridx = 6;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		groupeBtnsRadio.add(btnRadio);
		panelOption.add(btnRadio, gbc);

		btnRadio.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				majLegalite();
			}
		});


		// Ajout du Panel de l'option au panel des options
		panelOptions.add(panelOption);
		nbOptions++;
		panelOptions.revalidate();
		panelOptions.repaint();
	}
}
