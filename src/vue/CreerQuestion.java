package vue;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import controleur.Controleur;
import modele.Notion;
import modele.Question;

public class CreerQuestion extends JFrame implements ActionListener
{
	/**
	 * +-------------+
	 * |  ATTRIBUTS  |
	 * +-------------+
	 */

	private JComboBox<String> listeNotions   ;
	private JComboBox<String> listeRessources;
	private String            nomRessource   ;

	

	
	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */

	public CreerQuestion()
	{
		setTitle("Créer une question");


		//Panel principal
		JPanel panelprincipal = new JPanel();
		panelprincipal.setLayout(new BorderLayout());


		// Bouton "Menu principal"
		String imageMenu = "./lib/icones/home.png";
		ImageIcon icon = new ImageIcon(imageMenu);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);

		JButton btnMenu = new JButton(icon);
		btnMenu.addActionListener(e -> {
			Controleur.ouvrirAccueil();
			dispose();
		});

		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelMenu.add(btnMenu);


		// Panel pour les points et le temps
		JPanel panelPointsEtTemps = new JPanel();
		panelPointsEtTemps.setLayout(new GridLayout(2, 1));

		JPanel panelPoints = new JPanel();
		panelPoints.setLayout(new BorderLayout());
		panelPoints.add(new JLabel("Nombre de points"), BorderLayout.NORTH);
		panelPoints.add(new JTextArea(), BorderLayout.CENTER);

		JPanel panelTemps = new JPanel();
		panelTemps.setLayout(new BorderLayout());
		panelTemps.add(new JLabel("Temps de réponse (min : sec)"), BorderLayout.NORTH);
		panelTemps.add(new JTextArea(), BorderLayout.CENTER);

		panelPointsEtTemps.add(panelPoints);
		panelPointsEtTemps.add(panelTemps);


		//Creation d'une JComboBox pour les ressources et matières
		this.listeRessources = new JComboBox<String>(Controleur.getNomsRessources());
		this.listeRessources.setPreferredSize(new Dimension(150, 30));
		
		this.nomRessource = String.valueOf(listeRessources.getSelectedItem());
		this.listeNotions = new JComboBox<String>(Controleur.trouverRessourceParNom(nomRessource).getNomsNotions());
		listeNotions.setPreferredSize(new Dimension(150, 30));
		

		// Panel pour les ressources
		JPanel panelRessources = new JPanel();
		panelRessources.setLayout(new BorderLayout());
		panelRessources.add(new JLabel("Ressources"), BorderLayout.NORTH);
		panelRessources.add(listeRessources, BorderLayout.CENTER);
		

		// Panel pour les notions
		JPanel panelNotions = new JPanel();
		panelNotions.setLayout(new BorderLayout());
		panelNotions.add(new JLabel("Notions"), BorderLayout.NORTH);
		panelNotions.add(listeNotions, BorderLayout.CENTER);
		

		// Panel pour bien placer les ronds de difficultés et mettre le titre
		JPanel panelDifficulte = new JPanel();
		panelDifficulte.setLayout(new BorderLayout());
		panelDifficulte.add(new JLabel("niveau"), BorderLayout.NORTH);


		// Création des boutons ronds pour les niveaux de difficultés avec les couleurs et texte correspondants
		RoundButton tresFacile = creerBoutonRond(Color.GREEN, "TF");
		RoundButton facile = creerBoutonRond(Color.CYAN, "F");
		RoundButton moyen = creerBoutonRond(Color.RED, "M");
		RoundButton dur = creerBoutonRond(Color.WHITE, "D");


		// Définition de la taille du texte
		Font police = new Font("Arial", Font.BOLD, 8);
		tresFacile.setFont(police);
		facile.setFont(police);
		moyen.setFont(police);
		dur.setFont(police);


		// Grouper les boutons pour permettre une seule sélection à la fois
		ButtonGroup group = new ButtonGroup();
		group.add(tresFacile);
		group.add(facile);
		group.add(moyen);
		group.add(dur);


		// ActionListeners pour changer les couleurs lorsqu'un bouton est sélectionné
		tresFacile.addActionListener(e -> {
			tresFacile.setBackground(Color.GREEN);
			facile.setBackground(Color.GRAY);
			moyen.setBackground(Color.GRAY);
			dur.setBackground(Color.GRAY);
		});

		facile.addActionListener(e -> {
			tresFacile.setBackground(Color.GRAY);
			facile.setBackground(Color.CYAN);
			moyen.setBackground(Color.GRAY);
			dur.setBackground(Color.GRAY);
		});

		moyen.addActionListener(e -> {
			tresFacile.setBackground(Color.GRAY);
			facile.setBackground(Color.GRAY);
			moyen.setBackground(Color.RED);
			dur.setBackground(Color.GRAY);
		});

		dur.addActionListener(e -> {
			tresFacile.setBackground(Color.GRAY);
			facile.setBackground(Color.GRAY);
			moyen.setBackground(Color.GRAY);
			dur.setBackground(Color.WHITE);
		});


		// Panel pour les ronds de difficultés
		JPanel panelRond = new JPanel();
		panelRond.setLayout(new FlowLayout());
		panelRond.add(tresFacile);
		panelRond.add(facile);
		panelRond.add(moyen);
		panelRond.add(dur);
		
		panelDifficulte.add(panelRond, BorderLayout.CENTER);


		// Panel pour les différents niveaux de difficultés
		JPanel ressourceNotionLvl = new JPanel();
		ressourceNotionLvl.setLayout(new GridLayout(1, 3));
		ressourceNotionLvl.add(panelRessources);
		ressourceNotionLvl.add(panelNotions);
		ressourceNotionLvl.add(panelDifficulte);
		

		//Panel rassemble les deux panel points et temps
		JPanel rassemble = new JPanel();
		rassemble.setLayout(new GridLayout(1, 2));
		rassemble.add(panelPoints);
		rassemble.add(panelTemps);


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
			String erreur = "";
			
			// Récupération des informations pour la création de la question
			int nbPoints     = 1;
			int tempsReponse = 60;
			
			try { nbPoints	 = Integer.parseInt(((JTextArea) panelPoints.getComponent(1)).getText());
			} catch (Exception ex) { erreur = "Veuillez rajouter le nombre de points.  \n" ;}

			try { 
				String chaineTemps = ((JTextArea) panelTemps.getComponent(1)).getText();
				int minute  = Integer.parseInt(chaineTemps.substring(0, chaineTemps.indexOf(":")-1));
				int seconde = Integer.parseInt(chaineTemps.substring(chaineTemps.indexOf(":")+1, chaineTemps.length()));
				
				tempsReponse = minute * 60 + seconde;
			} catch (Exception ex) { 
				ex.printStackTrace();
				erreur += "Veuillez rajouter le temps de réponse (format = XX:XX).  \n" ;
			}

			Notion not = Notion.trouverNotionParNom((String) listeNotions.getSelectedItem(), Controleur.trouverRessourceParNom((String) listeRessources.getSelectedItem()));

			int difficulte = 0;
			if      (tresFacile.getBackground() == Color.GREEN && facile.getBackground() == Color.CYAN) { difficulte = 0; }
			else if (tresFacile.getBackground() == Color.GREEN) { difficulte = 1; }
			else if (facile.getBackground()     == Color.CYAN ) { difficulte = 2; }
			else if (moyen.getBackground()      == Color.RED  ) { difficulte = 3; }
			else if (dur.getBackground()        == Color.WHITE) { difficulte = 4; }

			String selectedType = (String) typeQuestion.getSelectedItem();
			if (not != null && selectedType != null && difficulte != 0 && erreur.isEmpty())
			{
				if ("Question à choix multiple à réponse unique".equals(selectedType))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QCMRU");
					new QuestionReponseUnique(tmp);
					dispose();
				}
				else if ("Question à choix multiple à réponse multiple".equals(selectedType))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QCMRM");
					new QuestionReponseMultiple(tmp);
					dispose();
				}
				else if ("Question à association d’éléments".equals(selectedType))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QAE");
					new QuestionLiaison(tmp);
					dispose();
				}
				else if ("Question avec élimination de propositions de réponses".equals(selectedType))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, not, difficulte, "QAEPR");
					new QuestionAvecElimination(tmp);
					dispose();
				}
			}

			if (difficulte == 0)
				erreur += "Veuillez sélectionner la difficulté.";
		
			if (! erreur.isEmpty())
			{
				JOptionPane.showMessageDialog(null, erreur, "Erreur : paramètres manquants", JOptionPane.ERROR_MESSAGE);
			}
			
		});

	

		//Création d'un panel gérant la création de question
		JPanel panelCreation = new JPanel();
		panelCreation.setLayout(new GridLayout(3, 1));
		panelCreation.add(rassemble);
		panelCreation.add(ressourceNotionLvl);
		panelCreation.add(panelQuestion);

		// Ajout des panels au panel principal
		panelprincipal.add(panelMenu, BorderLayout.NORTH);
		panelprincipal.add(panelCreation, BorderLayout.CENTER);
		panelprincipal.add(panelBouton, BorderLayout.SOUTH);
		
		this.add(panelprincipal);
		this.setSize(700, 330);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		listeRessources.addActionListener(this);
	}




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	/**
	 * Crée un bouton rond personnalisé avec une couleur et un texte spécifiques.
	 *
	 * @param couleur la couleur de fond du bouton
	 * @param texte   le texte affiché sur le bouton
	 * @return un objet {@link RoundButton} configuré avec les propriétés spécifiées
	 */
	public static RoundButton creerBoutonRond(Color couleur, String texte)
	{
		RoundButton btnRond = new RoundButton(texte);
		btnRond.setPreferredSize(new Dimension(45, 45));
		btnRond.setBackground(couleur);
		btnRond.setOpaque(false);
		btnRond.setBorderPainted(false);
		return btnRond;
	}

	public void actionPerformed(ActionEvent e) 
	{
		// Assumant que la seule source est la liste des ressources
		this.listeNotions.removeAllItems();

		this.nomRessource = String.valueOf(this.listeRessources.getSelectedItem());

		for (String nomNotion : Controleur.trouverRessourceParNom(this.nomRessource).getNomsNotions())
			this.listeNotions.addItem(nomNotion);
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