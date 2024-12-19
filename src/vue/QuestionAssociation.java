package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controleur.Controleur;
import modele.Question;
import modele.option.OptionAssociation;

public class QuestionAssociation extends JFrame
{
	/**
	 * +-------------+
	 * |  ATTRIBUTS  |
	 * +-------------+
	 */

	 private       Question    question              ;
	 private       JPanel      panelQuestion         ;
	 private       PanelSaisie panelEnonce           ;
	 private       PanelSaisie panelExplication      ;
	 private       JFrame      frameExplication      ;
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
		    "  ;  Notion : " + question.getNotion().getNom()
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
		JPanel panelOptions = new JPanel();
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));
		panelQuestion.add(panelOptions, BorderLayout.CENTER);


		// Panel pour les boutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));


		// Bouton "Ajouter"
        JButton btnAjouter = new JButton("Ajouter une option");

        btnAjouter.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
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

                JPanel panelLigne = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);

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
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.fill = GridBagConstraints.NONE;
                panelLigne.add(btnSupprimer, gbc);

                PanelSaisie element1 = new PanelSaisie(false);
                element1.setHauteur(HAUTEUR_OPTIONS);

                gbc.gridx = 1;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                panelLigne.add(element1, gbc);

                PanelSaisie element2 = new PanelSaisie(false);
                element2.setHauteur(HAUTEUR_OPTIONS);

                gbc.gridx = 2;
                gbc.gridy = 0;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                panelLigne.add(element2, gbc);

                btnSupprimer.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        panelOptions.remove(panelLigne);
                        nbOptions--;
                        panelOptions.revalidate();
                        panelOptions.repaint();
                    }
                });

                panelOptions.add(panelLigne);
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
				// Vérification si l'une des réponses n'est pas remplie
				boolean reponsesRemplies = true;

				if (nbOptions < 1)
					reponsesRemplies = false;

				if (nbOptions > 0)
				{
					for (int i = 0; i < panelOptions.getComponentCount(); i++) 
					{
						Component composant = panelOptions.getComponent(i);
						
						if (composant instanceof JPanel) 
						{
							JPanel panelLigne = (JPanel) composant;
					

							// Récupérez les sous-composants du JPanel
							Component[] composantsLigne = panelLigne.getComponents();
					
							if (composantsLigne.length >= 3) 
							{
								PanelSaisie panelSaisieOption1 = (PanelSaisie) composantsLigne[1];
								PanelSaisie panelSaisieOption2 = (PanelSaisie) composantsLigne[2];

								String stringOption1 = panelSaisieOption1.getTexte();
								String stringOption2 = panelSaisieOption2.getTexte();


								// Vérifiez les champs de texte vides
								if (stringOption1.isEmpty() || stringOption2.isEmpty()) 
								{
									reponsesRemplies = false;
								}
							}
						}
					}
				}
					
				String erreurs = "";

				if (! reponsesRemplies)                                               { erreurs += "N'oubliez pas de remplir toutes les réponses.\n"          ; }
				if (nbOptions < 2)                                                    { erreurs += "Vous devez avoir au moins deux lignes d'options. \n"      ; }
				if (nbOptions > nbMaxOptions)                                         { erreurs += "Vous ne pouvez avoir que six lignes d'options maximum. \n"; }
				if (QuestionAssociation.this.panelEnonce.getTexte().trim().isEmpty()) { erreurs += "Veuillez remplir la question. \n"                         ; }

				if (erreurs.trim().isEmpty())
				{
					// Sauvegarder l'énoncé
					question.setEnonce(panelEnonce.getTexte());


					// Sauvegarder les réponses
					for (int i = 0; i < panelOptions.getComponentCount(); i++) {
						Component composant = panelOptions.getComponent(i);
					
						if (composant instanceof JPanel) {
							JPanel panelLigne = (JPanel) composant;
					

							// Récupérez les sous-composants du JPanel
							Component[] composantsLigne = panelLigne.getComponents();
					
							if (composantsLigne.length >= 3)
							{
								try 
								{
									PanelSaisie panelSaisieOption1 = (PanelSaisie) composantsLigne[1];
									PanelSaisie panelSaisieOption2 = (PanelSaisie) composantsLigne[2];
	
									String stringOption1 = panelSaisieOption1.getTexte();
									String stringOption2 = panelSaisieOption2.getTexte();
	
									System.out.println("Réponse 1 : " + stringOption1);
									System.out.println("Réponse 2 : " + stringOption2);
	
									if (stringOption1.isEmpty() || stringOption2.isEmpty())
									{
										JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs de texte.", "Erreur", JOptionPane.ERROR_MESSAGE);
										return;
									}
	
									OptionAssociation optionAssociation1 = Controleur.creerReponseAssociation(stringOption1, question);
									OptionAssociation optionAssociation2 = Controleur.creerReponseAssociation(stringOption2, question);
	
									optionAssociation1.setAssocie(optionAssociation2);
									optionAssociation2.setAssocie(optionAssociation1);
	
									question.ajouterOption(optionAssociation1);
									question.ajouterOption(optionAssociation2);
									
									System.out.println("Options ajoutées avec succès.");
								}
								catch (Exception ex)
								{
									System.err.println("Erreur lors de la récupération des composants: " + ex.getMessage());
								}
							}

							else
								System.err.println("Structure inattendue dans rowPanel.");
						}

						else
							System.err.println("Composant inattendu dans panelOptions : " + composant.getClass().getName());
					}

					// Fermer la fenêtre
					QuestionAssociation.this.dispose();
					new Accueil();
				}

				else JOptionPane.showMessageDialog(QuestionAssociation.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);
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
        this.setTitle("Question Association");
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
	}
}