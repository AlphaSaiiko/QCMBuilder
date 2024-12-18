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

	private JPanel      panelQuestion       ;
	private PanelSaisie panelSaisie         ;
	private PanelSaisie panelExplication    ;
	private Question    question            ;
	private int         optionsMax       = 6;
	private int         optionsActuelles = 0;
	private JFrame      explicationFrame    ;




	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public QuestionAssociation(Question question)
	{
		this.question = question;



		// Dimensions de l'écran
		Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();


		// Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());


		// Ajouter le bouton "Retour" en haut à gauche
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(e -> {
			Controleur.ouvrirCreerQuestion();
			dispose();
		});
		JPanel panelRetour = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelRetour.add(btnRetour);

		// Ajouter la ressource et la notion en haut à gauche
		JLabel nomResEtNotion = new JLabel("Ressource : " + question.getNotion().getRessource().getId() + "_" + question.getNotion().getRessource().getNom() + 
		                              "  ;  Notion : " + question.getNotion().getNom());
		
		this.add(panelRetour, BorderLayout.NORTH);

		// Panel de la question (texte et options)
		panelQuestion = new JPanel(new GridLayout(2, 1));


		// Champ de texte pour écrire la question
		panelSaisie = new PanelSaisie();
		panelQuestion.add(panelSaisie);


		// Ajouter un panel pour les options
		JPanel panelOptions = new JPanel();
		panelOptions.setLayout(new BoxLayout(panelOptions, BoxLayout.Y_AXIS));
		
		JScrollPane scrollPane = new JScrollPane(panelOptions);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		panelQuestion.add(scrollPane);


		// Ajouter un panel pour les boutons "Ajouter", "Explication" et "Enregistrer"
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));


		// Bouton "ajouter"
		JButton btnAjouter = new JButton("Ajouter");

		btnAjouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Cas où il y a trop d'options
				if (optionsActuelles >= optionsMax)
				{
					JOptionPane.showMessageDialog(
						null,
						"Le nombre maximum de réponses est atteint (" + optionsMax + ").",
						"Erreur",
						JOptionPane.ERROR_MESSAGE
					);
					return;
				}
		

				// Panel ligne
				JPanel panelLigne = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5); // Marges autour des composants


				// Icone "Supprimer"
				ImageIcon iconeSupprimer = new ImageIcon(
					new ImageIcon("./lib/icones/delete.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
				JButton btnSupprimer = new JButton(iconeSupprimer);
				btnSupprimer.setPreferredSize(new Dimension(40, 40));
				btnSupprimer.setBorderPainted(false);
				btnSupprimer.setContentAreaFilled(false);
				btnSupprimer.setFocusPainted(false);
				btnSupprimer.setOpaque(false);


				// Composant 1 : Icone (à gauche)
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.WEST;
				gbc.fill = GridBagConstraints.NONE;
				panelLigne.add(btnSupprimer, gbc);


				// Composant 2 : Premier élément (JTextArea 1)
				JTextArea texteElement1 = new JTextArea();
				texteElement1.setLineWrap(false); // Retour à la ligne automatique (désactivé)
				texteElement1.setWrapStyleWord(true);

				JScrollPane scrollPane1 = new JScrollPane(texteElement1);
				scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


				// Les barres de défilement ne seront visibles que si le texte dépasse la taille de la zone
				scrollPane1.setPreferredSize(new Dimension(250, 120));

				gbc.gridx = 1;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1.0;
				panelLigne.add(scrollPane1, gbc);


				// Composant 3 : Deuxième élément (JTextArea 2)
				JTextArea texteElement2 = new JTextArea();
				texteElement2.setLineWrap(false);
				texteElement2.setWrapStyleWord(true);

				JScrollPane scrollPane2 = new JScrollPane(texteElement2);
				scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


				// Les barres de défilement ne seront visibles que si le texte dépasse la taille de la zone
				scrollPane2.setPreferredSize(new Dimension(250, 120)); // Taille du JScrollPane (ajustée ici pour tester la barre horizontale)

				gbc.gridx = 2;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.HORIZONTAL; // Permet à l'élément de se redimensionner horizontalement
				gbc.weightx = 1.0; // Le texte prend tout l'espace restant horizontalement
				panelLigne.add(scrollPane2, gbc);


				// ActionListener du bouton "Supprimer"
				btnSupprimer.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						panelOptions.remove(panelLigne);
						optionsActuelles--;
						panelOptions.revalidate();
						panelOptions.repaint();
					}
				});
		

				// Ajouter le panel ligne au panel de la question
				panelOptions.add(panelLigne);
				optionsActuelles++;
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
				if (explicationFrame == null || !explicationFrame.isDisplayable())
				{
					explicationFrame = new JFrame("Explication");
					explicationFrame.setSize(dimensionsEcran.width / 3, dimensionsEcran.height / 3);
					explicationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


					// Champ de texte pour écrire l'explication
					panelExplication = new PanelSaisie();
					explicationFrame.add(panelExplication);

					explicationFrame.setLocationRelativeTo(null);
					explicationFrame.setVisible(true);
				}
				else
					explicationFrame.toFront();
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
				if (optionsActuelles < 1) reponsesRemplies = false;
				if (optionsActuelles > 0)
				{
					for (int i = 0; i < panelOptions.getComponentCount(); i++) 
					{


						Component comp = panelOptions.getComponent(i);
						
						// Vérifiez que le composant est bien un JPanel
						if (comp instanceof JPanel) 
						{



							JPanel rowPanel = (JPanel) comp;
					
							// Récupérez les sous-composants du JPanel
							Component[] rowComponents = rowPanel.getComponents();
					
							if (rowComponents.length >= 3) 
							{ // Assurez-vous que les composants attendus existent


								
								JScrollPane scrollPane1 = (JScrollPane) rowComponents[1];
								JScrollPane scrollPane2 = (JScrollPane) rowComponents[2];
					
								JTextArea textField1 = (JTextArea) scrollPane1.getViewport().getView();
								JTextArea textField2 = (JTextArea) scrollPane2.getViewport().getView();
					
								// Vérifiez les champs de texte vides
								if (textField1.getText().isEmpty() || textField2.getText().isEmpty()) 
								{
									reponsesRemplies = false;
								}
							}
						}


					}
				}
						
				
					
				String erreurs = "";

				if (! reponsesRemplies)                                                 erreurs += "N'oubliez pas de remplir toutes les réponses.\n";
				if (optionsActuelles < 2)                                               erreurs += "Vous devez avoir au moins deux lignes d'options. \n";
				if (optionsActuelles > optionsMax)                                      erreurs += "Vous ne pouvez avoir que six lignes d'options maximum. \n";
				if (QuestionAssociation.this.panelSaisie.getTexte().trim().isEmpty())   erreurs += "Veuillez remplir la question. \n";

				if (erreurs.trim().isEmpty())
				{

					// Sauvegarder la question
					question.setEnonce(panelSaisie.getTexte());


					// Sauvegarder les réponses
					for (int i = 0; i < panelOptions.getComponentCount(); i++) {
						Component comp = panelOptions.getComponent(i);
					
						// Vérifiez que le composant est bien un JPanel
						if (comp instanceof JPanel) {
							JPanel rowPanel = (JPanel) comp;
					
							// Récupérez les sous-composants du JPanel
							Component[] rowComponents = rowPanel.getComponents();
					
							if (rowComponents.length >= 3) 
							{ // Assurez-vous que les composants attendus existent
								try {
										
									JScrollPane scrollPane1 = (JScrollPane) rowComponents[1];
									JScrollPane scrollPane2 = (JScrollPane) rowComponents[2];
						
									JTextArea textField1 = (JTextArea) scrollPane1.getViewport().getView();
									JTextArea textField2 = (JTextArea) scrollPane2.getViewport().getView();
						
									System.out.println("Réponse 1 : " + textField1.getText());
									System.out.println("Réponse 2 : " + textField2.getText());
									
						
									OptionAssociation o1 = Controleur.creerReponseAssociation(textField1.getText(), question);
									OptionAssociation o2 = Controleur.creerReponseAssociation(textField2.getText(), question);
						
									o1.setAssocie(o2);
									o2.setAssocie(o1);
						
									question.ajouterOption(o1);
									question.ajouterOption(o2);

									System.out.println("Options ajoutées avec succès.");
								} catch (Exception ex) {
									System.err.println("Erreur lors de la récupération des composants: " + ex.getMessage());
								}
							} else {
								System.err.println("Structure inattendue dans rowPanel.");
							}
						} else {
							System.err.println("Composant inattendu dans panelOptions : " + comp.getClass().getName());
						}
					}


					// Fermer la fenêtre
					QuestionAssociation.this.dispose();
					new Accueil();
				}
				else JOptionPane.showMessageDialog(QuestionAssociation.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);

			}
		});

		
		// Ajouter les boutons au panel des boutons
		panelBoutons.add(btnAjouter    );
		panelBoutons.add(btnExplication);
		panelBoutons.add(btnEnregistrer);


		// Ajouter le panel des boutons au conteneur principal
		panelPrincipal.add(new JScrollPane(panelQuestion));
		panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

		
		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal, BorderLayout.CENTER);
		this.setTitle("Question Association");
		this.setSize(dimensionsEcran.width / 2, dimensionsEcran.height - dimensionsEcran.height / 8);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	// MAIN TEMPORAIRE
	public static void main(String[] args) {
		new QuestionAssociation(null);
	}
}