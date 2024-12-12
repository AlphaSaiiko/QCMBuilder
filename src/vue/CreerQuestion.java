package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import modele.Notion;
import modele.Question;
import modele.Ressource;

public class CreerQuestion extends JFrame implements ActionListener
{
	
	
	/*
	 *  +-------------+
	 *  |  ATTRIBUTS  |
	 *  +-------------+
	 */

	private JComboBox<String> notionList;
	private String nomRessource;
	private JComboBox<String> ressourcesList;
	
	
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public CreerQuestion() {

		setTitle("Créer une question");



		JPanel panelprincipal = new JPanel();
		panelprincipal.setLayout(new BorderLayout());



		// Bouton de retour
		String imagePath = "./lib/icones/home.png";
		File imageFile = new File(imagePath);
		ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);
		JButton retourButton = new JButton(icon);
		retourButton.addActionListener(e -> {
			new Accueil();
			dispose();
		});
		JPanel panelRetour = new JPanel();
		panelRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelRetour.add(retourButton);


		
		// Panel pour les niveaux et le temps
		JPanel niveauEtTps = new JPanel();
		niveauEtTps.setLayout(new GridLayout(2, 1));

		// Panel de niveauEtTps gérant le nombre de points
		JPanel points = new JPanel();
		points.setLayout(new BorderLayout());
		points.add(new JLabel("Nombre de points"), BorderLayout.NORTH);
		points.add(new JTextArea(), BorderLayout.CENTER);

		// Panel de niveauEtTps gérant le temps de la question
		JPanel temps = new JPanel();
		temps.setLayout(new BorderLayout());
		temps.add(new JLabel("Temps de réponse (min : sec)"), BorderLayout.NORTH);
		temps.add(new JTextArea(), BorderLayout.CENTER);

		// On ajoute points et temps à niveauEtTps
		niveauEtTps.add(points);
		niveauEtTps.add(temps);



		//Creation JComboBox pour les ressources et matières
		this.ressourcesList = new JComboBox<String>(Ressource.getNomsRessources());
		this.ressourcesList.setPreferredSize(new Dimension(150, 30));
		
		this.nomRessource = String.valueOf(ressourcesList.getSelectedItem());
		this.notionList = new JComboBox<String>(Ressource.trouverRessourceParNom(nomRessource).getNomsNotions());
		notionList.setPreferredSize(new Dimension(150, 30));
		
		// Panel pour les ressources
		JPanel ressources = new JPanel();
		ressources.setLayout(new BorderLayout());
		ressources.add(new JLabel("Ressources"), BorderLayout.NORTH);
		ressources.add(ressourcesList, BorderLayout.CENTER);
		
		// Panel pour les notions
		JPanel notions = new JPanel();
		notions.setLayout(new BorderLayout());
		notions.add(new JLabel("Notions"), BorderLayout.NORTH);
		notions.add(notionList, BorderLayout.CENTER);
		
		// Panel pour bien placer les ronds de difficultés et mettre le titre
		JPanel panelDifficulte = new JPanel();
		panelDifficulte.setLayout(new BorderLayout());
		panelDifficulte.add(new JLabel("niveau"), BorderLayout.NORTH);

        // Création des boutons ronds pour les niveaux de difficultés avec les couleurs et texte correspondants
        RoundButton tresfacile = creerButtonRond(Color.GREEN, "TF");
        RoundButton facile = creerButtonRond(Color.CYAN, "F");
        RoundButton moyen = creerButtonRond(Color.RED, "M");
        RoundButton dur = creerButtonRond(Color.WHITE, "D");

        // Définir la taille du texte
        Font font = new Font("Arial", Font.BOLD, 8);
        tresfacile.setFont(font);
        facile.setFont(font);
        moyen.setFont(font);
        dur.setFont(font);

        // Grouper les boutons pour permettre une seule sélection à la fois
        ButtonGroup group = new ButtonGroup();
        group.add(tresfacile);
        group.add(facile);
        group.add(moyen);
        group.add(dur);

        // Ajouter des action listeners pour changer la couleur lorsqu'un bouton est sélectionné
        tresfacile.addActionListener(e -> {
            tresfacile.setBackground(Color.GREEN);
            facile.setBackground(Color.GRAY);
            moyen.setBackground(Color.GRAY);
            dur.setBackground(Color.GRAY);
        });

        facile.addActionListener(e -> {
            tresfacile.setBackground(Color.GRAY);
            facile.setBackground(Color.CYAN);
            moyen.setBackground(Color.GRAY);
            dur.setBackground(Color.GRAY);
        });

        moyen.addActionListener(e -> {
            tresfacile.setBackground(Color.GRAY);
            facile.setBackground(Color.GRAY);
            moyen.setBackground(Color.RED);
            dur.setBackground(Color.GRAY);
        });

        dur.addActionListener(e -> {
            tresfacile.setBackground(Color.GRAY);
            facile.setBackground(Color.GRAY);
            moyen.setBackground(Color.GRAY);
            dur.setBackground(Color.WHITE);
        });

        // Panel pour les ronds de difficultés
        JPanel panelRond = new JPanel();
        panelRond.setLayout(new FlowLayout());
        panelRond.add(tresfacile);
        panelRond.add(facile);
        panelRond.add(moyen);
        panelRond.add(dur);
		
		panelDifficulte.add(panelRond, BorderLayout.CENTER);
		
		// Panel pour les différents niveaux de difficultés
		JPanel ressourceNotionLvl = new JPanel();
		ressourceNotionLvl.setLayout(new GridLayout(1, 3));
		ressourceNotionLvl.add(ressources);
		ressourceNotionLvl.add(notions);
		ressourceNotionLvl.add(panelDifficulte);
		
		//Panel rassemble les deux panel points et temps
		JPanel rassemble = new JPanel();
		rassemble.setLayout(new GridLayout(1, 2));
		rassemble.add(points);
		rassemble.add(temps);


		//Panel pour le type de question
		JPanel panelQuestion = new JPanel();

		//JComboBox pour le type de question
		JComboBox<String> typeQuestion = new JComboBox<String>(new String[] { "Question à choix multiple à réponse unique", "Question à choix multiple à réponse multiple", "Question à association d’éléments", "Question avec élimination de propositions de réponses" });
		panelQuestion.add(typeQuestion, BorderLayout.SOUTH);

        // Bouton pour créer une nouvelle question
        JButton creerQuestionButton = new JButton("Créer nouvelle question");
        JPanel panelBouton = new JPanel();
        panelBouton.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBouton.add(creerQuestionButton);

		// Action listener pour le bouton de création de question
		creerQuestionButton.addActionListener(e -> {

			//Récupération des informations pour la création de la question
			int nbPoints     = Integer.parseInt(((JTextArea) points.getComponent(1)).getText());
			int tempsReponse = Integer.parseInt(((JTextArea) temps.getComponent(1)).getText());

			Notion not = Notion.trouverNotionParNom((String) notionList.getSelectedItem(), Ressource.trouverRessourceParNom((String) ressourcesList.getSelectedItem()));

			int difficulte = 0;
			if (tresfacile.getBackground() == Color.GREEN) { difficulte = 1; }
			else if (facile.getBackground() == Color.CYAN) { difficulte = 2; }
			else if (moyen.getBackground() == Color.RED) { difficulte = 3; }
			else if (dur.getBackground() == Color.WHITE) { difficulte = 4; }

			String selectedType = (String) typeQuestion.getSelectedItem();
			if (not != null && selectedType != null && difficulte != 0)
			{
				if ("Question à choix multiple à réponse unique".equals(selectedType))
				{
					Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QCMRU");
					new QuestionReponseUnique();
					dispose();
				}
				else if ("Question à choix multiple à réponse multiple".equals(selectedType))
				{
					Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QCMRM");
					new QuestionReponseMultiple();
					dispose();
				}
				else if ("Question à association d’éléments".equals(selectedType))
				{
					Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QAE");
					new QuestionLiaison();
					dispose();
				}
				else if ("Question avec élimination de propositions de réponses".equals(selectedType))
				{
					Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QAEPR");
					new QuestionAvecElimination();
					dispose();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		});

	

		//Création d'un panel gérant la création de question
		JPanel panelCreation = new JPanel();
		panelCreation.setLayout(new GridLayout(3, 1));
		panelCreation.add(rassemble);
		panelCreation.add(ressourceNotionLvl);
		panelCreation.add(panelQuestion);

		// Ajout des panels au panel principal
		panelprincipal.add(panelRetour, BorderLayout.NORTH);
		panelprincipal.add(panelCreation, BorderLayout.CENTER);
		panelprincipal.add(panelBouton, BorderLayout.SOUTH);
		
		this.add(panelprincipal);
		this.setSize(700, 330);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ressourcesList.addActionListener(this);
	}

	// Création des boutons ronds pour les niveaux de difficultés
	public static RoundButton creerButtonRond(Color couleur, String texte)
	{
		RoundButton button = new RoundButton(texte);
		button.setPreferredSize(new Dimension(45, 45));
		button.setBackground(couleur);
		button.setOpaque(false);
		button.setBorderPainted(false);
		return button;
	}

	public void actionPerformed(ActionEvent e) 
	{
		// Assumant que la seule source est la liste des ressources
		this.notionList.removeAllItems();

		this.nomRessource = String.valueOf(this.ressourcesList.getSelectedItem());

		for (String nomNotion : Ressource.trouverRessourceParNom(this.nomRessource).getNomsNotions())
			this.notionList.addItem(nomNotion);
	}

	public static void main(String[] args)
	{
		new CreerQuestion();
	}

}

class RoundButton extends JButton
{
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public RoundButton(String label)
	{
		super(label);
		setContentAreaFilled(false);
	}


	protected void paintComponent(Graphics g)
	{
		if (getModel().isArmed()) { g.setColor(Color.lightGray); }
		else { g.setColor(getBackground()); }
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
		super.paintComponent(g);
	}


	protected void paintBorder(Graphics g)
	{
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}


	public boolean contains(int x, int y)
	{
		int radius = getSize().width / 2;
		return (x - radius) * (x - radius) + (y - radius) * (y - radius) <= radius * radius;
	}
}