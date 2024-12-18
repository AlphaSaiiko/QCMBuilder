package vue;

import controleur.Controleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;
import modele.*;
import modele.option.Option;

public class QuestionReponseUnique extends JFrame
{
	private JPanel questionPanel;
	private ButtonGroup groupeBouton;
	private JTextArea questionArea;
	private Question question;

	private int optionsMax     = 6; // Limite du nombre de réponses
	private int optionsActuelles = 0; // Compteur des réponses ajoutées

	public QuestionReponseUnique(Question question)
	{
		this.question = question;

		// Initialiser le conteneur principal
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Ajouter le bouton "Retour" en haut à gauche
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(e -> {
			Controleur.ouvrirCreerQuestion();
			dispose();
		});
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPanel.add(btnRetour);

		// Ajouter la ressource et la notion en haut à gauche
		JLabel nomResEtNotion = new JLabel("Ressource : " + question.getNotion().getRessource().getId() + "_" + question.getNotion().getRessource().getNom() + 
		                              "  ;  Notion : " + question.getNotion().getNom());
		topPanel.add(nomResEtNotion);
		mainPanel.add(topPanel, BorderLayout.NORTH);

		// Initialiser le panel des questions
		questionPanel = new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));

		// Ajouter un champ de texte pour écrire la question avec un JScrollPane
		this.questionArea = new JTextArea(10, 80);
		this.questionArea.setLineWrap(true);
		this.questionArea.setWrapStyleWord(true);
		this.questionArea.setFont(new Font("Arial", Font.PLAIN, 18));
		JScrollPane questionScrollPane = new JScrollPane(questionArea);
		questionScrollPane.setPreferredSize(new Dimension(800, 200));
		questionPanel.add(questionScrollPane);

		// Ajouter un panel pour les boutons "Ajouter", "Explication" et
		// "Enregistrer"
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));

		
		// Ajouter un bouton "Ajouter" 
		JButton boutonAjouter = new JButton("Ajouter");
		panelBoutons.add(boutonAjouter);


		// Ajouter un bouton "Explication" 
		JButton boutonExplication = new JButton("Explication");
		panelBoutons.add(boutonExplication);

		// Ajouter un bouton "Enregistrer"
		JButton saveBtn = new JButton("Enregistrer");
		panelBoutons.add(saveBtn);

		// Ajouter le panel des boutons au panel des questions
		questionPanel.add(panelBoutons);

		// Initialiser le groupe de boutons radio
		groupeBouton = new ButtonGroup();

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
				JPanel poubellePnl = new JPanel(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.insets = new Insets(5, 5, 5, 5);
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.anchor = GridBagConstraints.WEST;

				// Redimensionner l'icône de poubelle
				ImageIcon poubelleIcon = new ImageIcon(new ImageIcon("./lib/icones/delete.png").getImage()
						.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
				JButton poubelleBtn = new JButton(poubelleIcon);
				poubelleBtn.setPreferredSize(new Dimension(40, 40));
				poubelleBtn.setBorderPainted(false);
				poubelleBtn.setContentAreaFilled(false);
				poubelleBtn.setFocusPainted(false);
				poubelleBtn.setOpaque(false);
				poubellePnl.add(poubelleBtn, gbc);

				// Ajouter un ActionListener au bouton de poubelle
				poubelleBtn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Supprimer le panel parent lorsque l'icône de poubelle
						// est cliquée
						questionPanel.remove(poubellePnl);
						optionsActuelles--;
						questionPanel.revalidate();
						questionPanel.repaint();
					}
				});

				// Augmenter la taille du JTextField
				JTextField newTextField = new JTextField();
				newTextField.setFont(new Font("Arial", Font.PLAIN, 18));
				newTextField.setPreferredSize(new Dimension(400, 30));
				gbc.gridx = 1;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.weightx = 1.0;
				poubellePnl.add(newTextField, gbc);

				gbc.gridx = 2;
				gbc.fill = GridBagConstraints.NONE;
				gbc.weightx = 0;

				// Ajouter un bouton radio
				JRadioButton newRadioButton = new JRadioButton();
				newRadioButton.setPreferredSize(new Dimension(30, 30));
				groupeBouton.add(newRadioButton);
				poubellePnl.add(newRadioButton, gbc);

				// Ajouter le nouveau panel trash au conteneur principal
				questionPanel.add(poubellePnl, questionPanel.getComponentCount() - 1);

				// Incrémenter le compteur de réponses
				optionsActuelles++;

				// Rafraîchir l'interface utilisateur
				questionPanel.revalidate();
				questionPanel.repaint();
			}
		});

		// Ajouter un ActionListener au bouton "Explication"
		boutonExplication.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// Créer et afficher une nouvelle fenêtre pour écrire
				// l'explication
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
		saveBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				// Vérification si la question à une réponse : AKA si une des options est valide
				// Vérification si l'une des réponses n'est pas remplie
				boolean aReponse = false;
				boolean reponsesRemplies = true;
				if (optionsActuelles < 1) reponsesRemplies = false;
				if (groupeBouton != null && optionsActuelles > 0)
				{
					for (int i = 1; i < questionPanel.getComponentCount() - 1; i++)
					{
						JPanel panel = (JPanel) questionPanel.getComponent(i);
						JTextField textField = (JTextField) panel.getComponent(1);
						JRadioButton radioButton = (JRadioButton) panel.getComponent(2);
						
						if (radioButton.isSelected())
							aReponse = true;
						if (textField.getText().trim().isEmpty())
							reponsesRemplies = false;
					}
						
				}


				
					
				String erreurs = "";

				if (! aReponse)                                                         erreurs += "N'oubliez pas de sélectionner une réponse.\n";
				if (! reponsesRemplies)                                                 erreurs += "N'oubliez pas de remplir toutes les réponses.\n";
				if (optionsActuelles < 2)                                               erreurs += "Vous devez avoir au moins deux options. \n";
				if (optionsActuelles > optionsMax)                                      erreurs += "Vous ne pouvez avoir que six options maximum. \n";
				if (QuestionReponseUnique.this.questionArea.getText().trim().isEmpty()) erreurs += "Veuillez remplir la question. \n";

				if (erreurs.trim().isEmpty())
				{
					// Enregistrer la question dans un fichier
					question.setEnonce(questionArea.getText());

					// Enregistrer les réponses
					for (int i = 1; i < questionPanel.getComponentCount() - 1; i++)
					{
						JPanel panel = (JPanel) questionPanel.getComponent(i);
						JTextField textField = (JTextField) panel.getComponent(1);
						JRadioButton radioButton = (JRadioButton) panel.getComponent(2);
						Option o = Controleur.creerReponse(textField.getText(), radioButton.isSelected(), question);
						question.ajouterOption(o);
					}

					// Ouvrir une nouvelle instance de la classe Accueil
					QuestionReponseUnique.this.dispose();
					new Accueil();
				}
				else JOptionPane.showMessageDialog(QuestionReponseUnique.this, erreurs, "Erreur", JOptionPane.ERROR_MESSAGE);
			}

		});

		mainPanel.add(questionPanel, BorderLayout.CENTER);

		// Ajouter tout dans le conteneur principal
		add(mainPanel, BorderLayout.CENTER);

		// Afficher la fenêtre
		setTitle("Question Réponse Unique");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
