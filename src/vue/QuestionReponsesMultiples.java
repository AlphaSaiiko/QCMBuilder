package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import controleur.Controleur;
import modele.Question;
import modele.option.Option;

public class QuestionReponsesMultiples extends JFrame
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
	private       PanelSaisie panelExplication      ;
	private       JFrame      frameExplication      ;
	private       int         nbOptions        = 0  ;
	private final int         nbMaxOptions     = 6  ;
	private final int         HAUTEUR_OPTIONS  = 150;
	private final int         LARGEUR_OPTIONS  = 800;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public QuestionReponsesMultiples(Question question)
	{
		this.question = question;


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
		

		// Panel pour les options
		panelOptions = new JPanel();
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));
		panelQuestion.add(panelOptions, BorderLayout.CENTER);


		// Panel pour les boutons
		panelBoutons = new JPanel();
		panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));
 

		// Ajouter un ActionListener au bouton "Ajouter"
		JButton btnAjouter = new JButton("Ajouter une option");

		btnAjouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Cas ou il y a trop d'options
				if (nbOptions >= nbMaxOptions)
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

				gbc.gridx = 1;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.anchor = GridBagConstraints.CENTER;
				gbc.weightx = 1.0;
				panelOption.add(panelSaisieOption, gbc);


				// Ajouter une case à cocher
				JCheckBox caseACocher = new JCheckBox();
				caseACocher.setPreferredSize(new Dimension(30, 30));
				panelOption.add(caseACocher, gbc);

				gbc.gridx = 2;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.anchor = GridBagConstraints.CENTER;
				panelOption.add(caseACocher, gbc);

				// Ajout du Panel de l'option au panel des options
				panelOptions.add(panelOption);
				nbOptions++;
				panelOptions.revalidate();
				panelOptions.repaint();
			}
		});


		// Bouton "Explication"
		JButton btnExplication = new JButton("Explication");

        btnExplication.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (frameExplication == null || !frameExplication.isDisplayable())
                {
                    frameExplication = new JFrame("Explication");
                    frameExplication.setSize(400, 300);
                    frameExplication.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    panelExplication = new PanelSaisie();
                    frameExplication.add(panelExplication);

                    frameExplication.setLocationRelativeTo(null);
                    frameExplication.setVisible(true);
                }
                else
                {
                    frameExplication.toFront();
                }
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
				int     nbReponses       = 0   ;
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

						PanelSaisie panelSaisieOption = (PanelSaisie) composantsOption[1];
						JCheckBox   caseACocher       = (JCheckBox  ) composantsOption[2];


						// Vérifier les champs de texte vides
						if (panelSaisieOption.getContenu().isEmpty()) 
							reponsesRemplies = false;


						// Compter les bonnes réponse
						if (caseACocher.isSelected())
							nbReponses++;
					}
				}
					
				String erreurs = "";

				if (nbReponses == 0)                                                          { erreurs += "N'oubliez pas de sélectionner des réponses.\n"                                                                                        ; }
				if (nbReponses == 1)                                                          { erreurs += "Sélectionnez au moins deux réponses, si vous souhaitez une seule,\n nous vous demanderons d'utiliser une question à réponse unique.\n"; }
				if (! reponsesRemplies)                                                       { erreurs += "N'oubliez pas de remplir toutes les réponses.\n"                                                                                      ; }
				if (nbOptions < 2)                                                            { erreurs += "Vous devez avoir au moins deux options. \n"                                                                                           ; }
				if (nbOptions > nbMaxOptions)                                                 { erreurs += "Vous ne pouvez avoir que six options maximum. \n"                                                                                     ; }
				if (QuestionReponsesMultiples.this.panelEnonce.getContenu().trim().isEmpty()) { erreurs += "Veuillez remplir la question. \n"                                                                                                     ; }

				if (erreurs.trim().isEmpty())
				{
					// Enregistrer l'énoncé
					question.setEnonce(panelEnonce.getContenu());


					//Enregistrer les réponses
					for (int i = 0; i < panelOptions.getComponentCount(); i++)
					{
						Component composant = panelOptions.getComponent(i);

						JPanel panelOption = (JPanel) composant;
				

						// Récupérez les sous-composants du JPanel
						Component[] composantsOption = panelOption.getComponents();

						PanelSaisie panelSaisieOption = (PanelSaisie) composantsOption[1];
						JCheckBox   caseACocher       = (JCheckBox  ) composantsOption[2];

						Option option = Controleur.creerReponse(panelSaisieOption.getContenu(), caseACocher.isSelected(), question);

						question.ajouterOption(option);
					}


					// Fermer la fenêtre
					QuestionReponsesMultiples.this.dispose();
					new Accueil();
				
				}
				else JOptionPane.showMessageDialog(QuestionReponsesMultiples.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);
				
			}
		});

		
		// Ajout des boutons au JPanel contenant les boutons
		panelBoutons.add(btnAjouter);
		panelBoutons.add(btnExplication);
		panelBoutons.add(btnEnregistrer);


		// Ajout des composants au panel principal
		JScrollPane scrollPaneQuestion = new JScrollPane(panelQuestion);
		scrollPaneQuestion.getVerticalScrollBar().setUnitIncrement(16);
		panelPrincipal.add(scrollPaneQuestion);
		panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);


		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setTitle("Question Elimination");
		this.setSize(1000, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
