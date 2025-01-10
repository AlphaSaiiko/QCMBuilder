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
import vue.Accueil;
import vue.PanelSaisie;

public class QuestionReponseUnique extends JFrame
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
	private       JFrame      frameExplication      ;
	private       ButtonGroup groupeBtnsRadio       ;
	private       int         nbOptions        = 0  ;
	private final int         nbMaxOptions     = 6  ;
	private final int         HAUTEUR_OPTIONS  = 150;
	private final int         LARGEUR_OPTIONS  = 800;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public QuestionReponseUnique(Question question)
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
			if (ancienneQst == null) Controleur.ouvrirCreerQuestion();
			else                     Controleur.ouvrirCreerQuestion(question);
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
		this.groupeBtnsRadio = new ButtonGroup();


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
				// Vérification si la question à une réponse : AKA si au moins une des options est valide
				// Vérification si l'une des réponses n'est pas remplie
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
						JRadioButton btnRadio          = (JRadioButton) composantsOption[2];


						// Vérifier les champs de texte vides
						if (panelSaisieOption.getContenu().isEmpty()) 
							reponsesRemplies = false;

						if (btnRadio.isSelected())
							aReponse = true;
					}
						
				}

				String erreurs = "";

				if (! aReponse)                                                           { erreurs += "N'oubliez pas de sélectionner une réponse.\n"    ; }
				if (! reponsesRemplies)                                                   { erreurs += "N'oubliez pas de remplir toutes les réponses.\n" ; }
				if (nbOptions < 2)                                                        { erreurs += "Vous devez avoir au moins deux options. \n"      ; }
				if (nbOptions > nbMaxOptions)                                             { erreurs += "Vous ne pouvez avoir que six options maximum. \n"; }
				if (QuestionReponseUnique.this.panelEnonce.getContenu().trim().isEmpty()) { erreurs += "Veuillez remplir la question. \n"                ; }

				if (erreurs.trim().isEmpty())
				{

					
					// Enregistrer l'énoncé de la question
					question.setEnonce(panelEnonce.getContenu());
					
					// Enregistrer l'explication
					if (! panelFeedback.getContenu().trim().isEmpty())
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
						JRadioButton btnRadio          = (JRadioButton) composantsOption[2];

						Option option = Controleur.creerReponse(panelSaisieOption.getContenu(), btnRadio.isSelected(), question);

						question.ajouterOption(option);
					}



					Notion notionActuelle = question.getNotion();

					ControleurFichier ctrlFichier = new ControleurFichier(
							"lib/ressources/" + notionActuelle.getRessource().getId() + "_"
									+ notionActuelle.getRessource().getNom() + "/"
									+ notionActuelle.getNom() + "/");

					// Enregistrer la question dans un fichier
					if (ancienneQst == null) question.creerFichierQuestion();
					else                     ctrlFichier.ecrireQuestion("question" + question.getIdQuestion() + "/question" + question.getIdQuestion(), question);
					

					// Fermer la fenêtre
					QuestionReponseUnique.this.dispose();
					new Accueil();
				
				}
				else JOptionPane.showMessageDialog(QuestionReponseUnique.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);
				
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
					if (opt instanceof Option && ancienneQst.getType().equals("QCMRU"))
					{
						this.ajouterOption((Option) opt);
					}
				}
			}
		}

		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setTitle("Question à réponse unique");
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void ajouterOption(Option opt)
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
		if (opt != null)  panelSaisieOption.setContenu(opt.getEnonce());

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.weightx = 1.0;
		panelOption.add(panelSaisieOption, gbc);
		// Ajouter un bouton radio
		JRadioButton btnRadio = new JRadioButton();
		btnRadio.setPreferredSize(new Dimension(30, 30));
		panelOption.add(btnRadio, gbc);

		// Si la question est une modifiable, et que l'option est correcte
		if (opt != null)
		{
			if (opt.getEstReponse())
			{
				btnRadio.setSelected(true);
			}
		}

		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		groupeBtnsRadio.add(btnRadio);
		panelOption.add(btnRadio, gbc);


		// Ajout du Panel de l'option au panel des options
		panelOptions.add(panelOption);
		nbOptions++;
		panelOptions.revalidate();
		panelOptions.repaint();
	}
}
