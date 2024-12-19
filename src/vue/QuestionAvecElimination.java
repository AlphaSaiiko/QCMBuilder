package vue;

import controleur.Controleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import modele.Question;
import modele.option.OptionElimination;

public class QuestionAvecElimination extends JFrame
{
	private JPanel panelQuestion;
	private JTextArea textareaQuestion;
	private ButtonGroup groupeBouton;
	private Question question;

	private int optionsMax		 = 6;
	private int optionsActuelles = 0;

	public QuestionAvecElimination(Question question)
	{
		this.question = question;
		this.groupeBouton = new ButtonGroup();
		// Initialiser le conteneur principal
		JPanel panelPrincipal = new JPanel(new BorderLayout());

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
		panelRetour.add(nomResEtNotion);
		this.add(panelRetour, BorderLayout.NORTH);

		// Initialiser le panel des questions
		panelQuestion = new JPanel();
		panelQuestion.setLayout(new BoxLayout(panelQuestion, BoxLayout.Y_AXIS));

		// Ajouter un champ de texte pour écrire la question
		textareaQuestion = new JTextArea(5, 60);
		textareaQuestion.setLineWrap(true);
		textareaQuestion.setWrapStyleWord(true);
		textareaQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
		textareaQuestion.setMaximumSize(new Dimension(Integer.MAX_VALUE, textareaQuestion.getPreferredSize().height));
		panelQuestion.add(new JScrollPane(textareaQuestion));

		// Ajouter un panel pour les boutons "Ajouter", "Explication" et "Enregistrer"
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));

		// Ajouter un bouton "Ajouter" 
		JButton boutonAjouter = new JButton("Ajouter");
		panelBoutons.add(boutonAjouter);

		// Ajouter un bouton "Explication" 
		JButton boutonExplication = new JButton("Explication");
		panelBoutons.add(boutonExplication);

		// Ajouter un bouton "Enregistrer"
		JButton boutonEnregistrer = new JButton("Enregistrer");
		panelBoutons.add(boutonEnregistrer);

		// Ajouter le panel des boutons au panel des questions
		panelQuestion.add(panelBoutons);

		// Ajouter un ActionListener au bouton "Ajouter"
		boutonAjouter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (optionsActuelles >= optionsMax)
				{
					JOptionPane.showMessageDialog(null,
							"Le nombre maximum de réponses est atteint (" + optionsMax + ").", "Erreur",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				// Créer un nouveau panel trash
				JPanel newTrashPanel = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.WEST;

				// Ajouter un label intitulé "Réponse:"
				JLabel labelReponse = new JLabel("Réponse:");
				labelReponse.setFont(new Font("Arial", Font.PLAIN, 18));
				newTrashPanel.add(labelReponse, gbc);

				// Augmenter la taille du JTextField
				JTextField newTextField = new JTextField();
				newTextField.setFont(new Font("Arial", Font.PLAIN, 18));
				newTextField.setPreferredSize(new Dimension(400, 30));
				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1.0;
				newTrashPanel.add(newTextField, gbc);

				gbc.gridx = 2;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;

				// Ajouter un label pour l'ordre
				JLabel labelOrdre = new JLabel("Ordre:");
				labelOrdre.setFont(new Font("Arial", Font.PLAIN, 18));
				gbc.gridx = 3;
				newTrashPanel.add(labelOrdre, gbc);

				// Ajouter le JTextField pour l'ordre
				JTextField texteOrdre = new JTextField();
				texteOrdre.setFont(new Font("Arial", Font.PLAIN, 18));
				texteOrdre.setPreferredSize(new Dimension(50, 30));
				gbc.gridx = 4;
				newTrashPanel.add(texteOrdre, gbc);


				texteOrdre.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						majLegalite();
					}
				});


				// Ajouter un label pour les points
				JLabel labelPoints = new JLabel("Points:");
				labelPoints.setFont(new Font("Arial", Font.PLAIN, 18));
				gbc.gridx = 5;
				newTrashPanel.add(labelPoints, gbc);

				// Ajouter le JTextField pour les points
				JTextField textePoints = new JTextField();
				textePoints.setFont(new Font("Arial", Font.PLAIN, 18));
				textePoints.setPreferredSize(new Dimension(50, 30));
				gbc.gridx = 6;
				newTrashPanel.add(textePoints, gbc);


				textePoints.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						majLegalite();
					}
				});


				// Ajouter un bouton radio
				JRadioButton newRadioButton = new JRadioButton();
				newRadioButton.setPreferredSize(new Dimension(30, 30));
				groupeBouton.add(newRadioButton);
				gbc.gridx = 7;
				newTrashPanel.add(newRadioButton, gbc);

				

				newRadioButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						majLegalite();
					}
				});



				// Redimensionner l'icône de poubelle
				ImageIcon newTrashIcon = new ImageIcon(new ImageIcon("./lib/icones/delete.png").getImage()
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
				JButton newTrashButton = new JButton(newTrashIcon);
				newTrashButton.setPreferredSize(new Dimension(40, 40));
				newTrashButton.setBorderPainted(false);
				newTrashButton.setContentAreaFilled(false);
				newTrashButton.setFocusPainted(false);
				newTrashButton.setOpaque(false);
				gbc.gridx = 8;
				newTrashPanel.add(newTrashButton, gbc);

				// Ajouter un ActionListener au bouton de poubelle
				newTrashButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Supprimer le panel parent lorsque l'icône de poubelle est cliquée
						panelQuestion.remove(newTrashPanel);
						panelQuestion.revalidate();
						panelQuestion.repaint();
					}
				});

				// Ajouter le nouveau panel trash au conteneur principal
				System.out.println(panelQuestion.getComponentCount() - 1);
				System.out.println(panelQuestion.getComponentCount());
				panelQuestion.add(newTrashPanel, panelQuestion.getComponentCount() - 1);

				// Incrémenter le compteur de réponses
				optionsActuelles++;

				// Rafraîchir l'interface utilisateur
				panelQuestion.revalidate();
				panelQuestion.repaint();
			}
		});

		// Ajouter un ActionListener au bouton "Explication"
		boutonExplication.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Créer et afficher une nouvelle fenêtre pour écrire l'explication
				JFrame explicationFrame = new JFrame("Explication");
				explicationFrame.setSize(400, 300);
				explicationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				JTextArea explicationArea = new JTextArea(10, 30);
				explicationArea.setLineWrap(true);
				explicationArea.setWrapStyleWord(true);
				explicationArea.setFont(new Font("Arial", Font.PLAIN, 18));

				explicationFrame.add(new JScrollPane(explicationArea));
				explicationFrame.setVisible(true);
			}
		});

		// Ajouter un ActionListener au bouton "Enregistrer"
		boutonEnregistrer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				
				// Vérification si la question à une réponse : AKA si une des options est valide
				// Vérification si l'une des réponses n'est pas remplie
				// Vérification si l'une des réponse est illégale (Si une réponse valide à un ordre et un point en moins)
				boolean aReponse = false;
				boolean reponsesRemplies = true;
				if (optionsActuelles < 1) reponsesRemplies = false;
				if (groupeBouton != null && optionsActuelles > 0)
				{
					
					for (int i = 1; i < panelQuestion.getComponentCount() - 1; i++)
					{
						JPanel panel = (JPanel) panelQuestion.getComponent(i);
						JTextField textField = (JTextField) panel.getComponent(1);
						JRadioButton radioButton = (JRadioButton) panel.getComponent(6);
						
						if (radioButton.isSelected())
							aReponse = true;
						if (textField.getText().trim().isEmpty())
							reponsesRemplies = false;
					}
						
				}


				
					
				String erreurs = "";

				if (! aReponse)																	erreurs += "N'oubliez pas de sélectionner une réponse.\n";
				if (! reponsesRemplies)															erreurs += "N'oubliez pas de remplir toutes les réponses.\n";
				if (optionsActuelles < 2)														erreurs += "Vous devez avoir au moins deux options. \n";
				if (optionsActuelles > optionsMax)												erreurs += "Vous ne pouvez avoir que six options maximum. \n";
				if (QuestionAvecElimination.this.textareaQuestion.getText().trim().isEmpty())	erreurs += "Veuillez remplir la question. \n";

				if (erreurs.trim().isEmpty())
				{
					// Enregistrer la question dans un fichier		
					question.setEnonce(textareaQuestion.getText());

					//Enregistrer les réponses
					for (int i = 1; i < panelQuestion.getComponentCount() - 1; i++)
					{
						JPanel panel = (JPanel) panelQuestion.getComponent(i);

						JTextField textField = (JTextField) panel.getComponent(1);
						JTextField ordre = (JTextField) panel.getComponent(3);
						JTextField points = (JTextField) panel.getComponent(5);
						JRadioButton radioButton = (JRadioButton) panel.getComponent(6);

						
						if (ordre.getText().isEmpty())
						{
							ordre.setText("-1");
						}
						
						if (points.getText().isEmpty()) 
						{
							points.setText("-1");
						}

						OptionElimination rep = Controleur.creerReponseElimination(textField.getText(), Integer.parseInt(ordre.getText()), Double.parseDouble(points.getText()), radioButton.isSelected(), question);

						question.ajouterOption(rep);
					}

					// Fermer la fenêtre
					QuestionAvecElimination.this.dispose();
					new Accueil();
				
				}
				else JOptionPane.showMessageDialog(QuestionAvecElimination.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});



		panelPrincipal.add(panelQuestion, BorderLayout.NORTH);

		// Ajouter tout dans le conteneur principal
		add(panelPrincipal, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Avec Elimination");
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void majLegalite()
	{
		if (groupeBouton != null && optionsActuelles > 0)
			{
				
				for (int i = 1; i < panelQuestion.getComponentCount() - 1; i++)
				{
					JPanel panel = (JPanel) panelQuestion.getComponent(i);
					JTextField textField     = (JTextField) panel.getComponent(1);
					JTextField ordre         = (JTextField) panel.getComponent(3);
					JTextField points        = (JTextField) panel.getComponent(5);
					JRadioButton radioButton = (JRadioButton) panel.getComponent(6);
					
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

}
