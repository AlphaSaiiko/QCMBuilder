package vue;

import controleur.Controleur;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
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
		//Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());


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

		JPanel panelBtnMenu = new JPanel();
		panelBtnMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelBtnMenu.add(btnMenu);


		// Panel pour les points et le temps
		JPanel panelPointsEtTemps = new JPanel();
		panelPointsEtTemps.setLayout(new GridLayout(1, 2));

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
		String[] decRessource = nomRessource.split("_", 2);
		String id = "", nom = "";
		if (decRessource.length == 2)
		{
			nom = decRessource[0];
			id  = decRessource[1];
		}
		
		this.listeNotions = new JComboBox<String>(Controleur.trouverRessourceParId(id).getNomsNotions());
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
		panelDifficulte.add(new JLabel("Difficulté"), BorderLayout.NORTH);


		// Création des boutons ronds pour les niveaux de difficultés avec les couleurs et texte correspondants
		BoutonRond tresFacile = creerBoutonRond(Color.GREEN, "TF");
		BoutonRond facile = creerBoutonRond(Color.CYAN, "F");
		BoutonRond moyen = creerBoutonRond(Color.RED, "M");
		BoutonRond dur = creerBoutonRond(Color.WHITE, "D");


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
		JPanel panelRondsDifficulte = new JPanel();
		panelRondsDifficulte.setLayout(new FlowLayout());
		panelRondsDifficulte.add(tresFacile);
		panelRondsDifficulte.add(facile);
		panelRondsDifficulte.add(moyen);
		panelRondsDifficulte.add(dur);
		
		panelDifficulte.add(panelRondsDifficulte, BorderLayout.CENTER);


		// Panel pour les choix de ressource, notion et difficulté
		JPanel panelResNtnDif = new JPanel();
		panelResNtnDif.setLayout(new GridLayout(1, 3));
		panelResNtnDif.add(panelRessources);
		panelResNtnDif.add(panelNotions);
		panelResNtnDif.add(panelDifficulte);


		//Panel pour le type de question
		JPanel panelType = new JPanel();


		//JComboBox pour le type de question
		JComboBox<String> listeTypes = new JComboBox<String>(new String[] { "Question à choix multiple à réponse unique", "Question à choix multiple à réponse multiple", "Question à association d’éléments", "Question avec élimination de propositions de réponses" });
		panelType.add(listeTypes, BorderLayout.SOUTH);


		// Bouton pour créer une nouvelle question
		JButton btnCreerQuestion = new JButton("Créer nouvelle question");
		JPanel panelBtnCreerQuestion = new JPanel();
		panelBtnCreerQuestion.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelBtnCreerQuestion.add(btnCreerQuestion);


		// Action listener pour le bouton de création de question
		btnCreerQuestion.addActionListener(e -> {
			String erreurs = "";
			
			// Récupération des informations pour la création de la question
			int nbPoints     = 1;
			int tempsReponse = 60;
			
			try { nbPoints = Integer.parseInt(((JTextArea) panelPoints.getComponent(1)).getText()); }
			catch (Exception ex) { erreurs = "Veuillez rajouter le nombre de points.\n"; }

			try { 
				String chaineTemps = ((JTextArea) panelTemps.getComponent(1)).getText();
				int minute  = Integer.parseInt(chaineTemps.substring(0, chaineTemps.indexOf(":")-1));
				int seconde = Integer.parseInt(chaineTemps.substring(chaineTemps.indexOf(":")+1, chaineTemps.length()));
				
				tempsReponse = minute * 60 + seconde;
			} catch (Exception ex) { erreurs += "Veuillez rajouter le temps de réponse (format XX:XX).\n"; }

			String[] nomRessource = ((String) listeRessources.getSelectedItem()).split("_", 2);
			String idRes = "", nomRes = "";
			if (nomRessource.length == 2)
			{
				nomRes = nomRessource[0];
				idRes  = nomRessource[1];
			}
			Notion notion = Notion.trouverNotionParNom((String) listeNotions.getSelectedItem(), Controleur.trouverRessourceParId(idRes));

			int difficulte = 0;
			if      (tresFacile.getBackground() == Color.GREEN && facile.getBackground() == Color.CYAN) { difficulte = 0; }
			else if (tresFacile.getBackground() == Color.GREEN)                                         { difficulte = 1; }
			else if (facile.getBackground()     == Color.CYAN )                                         { difficulte = 2; }
			else if (moyen.getBackground()      == Color.RED  )                                         { difficulte = 3; }
			else if (dur.getBackground()        == Color.WHITE)                                         { difficulte = 4; }

			String typeSelectionne = (String) listeTypes.getSelectedItem();
			if (notion != null && typeSelectionne != null && difficulte != 0 && erreurs.isEmpty())
			{
				if ("Question à choix multiple à réponse unique".equals(typeSelectionne))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, notion, difficulte, "QCMRU");
					new QuestionReponseUnique(tmp);
					dispose();
				}
				else if ("Question à choix multiple à réponse multiple".equals(typeSelectionne))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, notion, difficulte, "QCMRM");
					new QuestionReponseMultiple(tmp);
					dispose();
				}
				else if ("Question à association d’éléments".equals(typeSelectionne))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, notion, difficulte, "QAE");
					new QuestionAssociation(tmp);
					dispose();
				}
				else if ("Question avec élimination de propositions de réponses".equals(typeSelectionne))
				{
					Question tmp = Question.creerQuestion(nbPoints, tempsReponse, notion, difficulte, "QAEPR");
					new QuestionAvecElimination(tmp);
					dispose();
				}
			}

			if (difficulte == 0)
				erreurs += "Veuillez sélectionner la difficulté.";
		
			if (! erreurs.isEmpty())
				JOptionPane.showMessageDialog(null, erreurs, "Erreur : paramètres manquants", JOptionPane.ERROR_MESSAGE);
		});
	

		//Création d'un panel gérant la toute la configuration de la question
		JPanel panelConfiguration = new JPanel();
		panelConfiguration.setLayout(new GridLayout(3, 1));
		panelConfiguration.add(panelPointsEtTemps);
		panelConfiguration.add(panelResNtnDif);
		panelConfiguration.add(panelType);


		// Ajout des panels au panel principal
		panelPrincipal.add(panelBtnMenu, BorderLayout.NORTH);
		panelPrincipal.add(panelConfiguration, BorderLayout.CENTER);
		panelPrincipal.add(panelBtnCreerQuestion, BorderLayout.SOUTH);
		

		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal);
		this.setTitle("Créer une question");
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
	public static BoutonRond creerBoutonRond(Color couleur, String texte)
	{
		BoutonRond btnRond = new BoutonRond(texte);
		btnRond.setPreferredSize(new Dimension(45, 45));
		btnRond.setBackground(couleur);
		btnRond.setOpaque(false);
		btnRond.setBorderPainted(false);
		return btnRond;
	}



	/**
	 * Gère l'événement déclenché par une action de l'utilisateur.
	 *
	 * Cette méthode est appelée lorsqu'une action est effectuée (comme un clic ou une sélection). 
	 * Elle met à jour la liste déroulante des notions (`listeNotions`) en fonction de la ressource
	 * actuellement sélectionnée dans la liste déroulante des ressources (`listeRessources`).
	 *
	 * @param e l'événement déclenché par une action de l'utilisateur.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		this.listeNotions.removeAllItems();

		this.nomRessource = String.valueOf(this.listeRessources.getSelectedItem());

		String[] decRessource = this.nomRessource.split("_", 2);
		String id = "", nom = "";
		if (decRessource.length == 2)
		{
			nom = decRessource[0];
			id  = decRessource[1];
		}

		for (String nomNotion : Controleur.trouverRessourceParId(id).getNomsNotions())
			this.listeNotions.addItem(nomNotion);
	}



	// Main
	public static void main(String[] args)
	{
		new CreerQuestion();
	}
}




/**
 * +-------------------+
 * | CLASSE BOUTONROND |
 * +-------------------+
 */

class BoutonRond extends JButton
{
	/**
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

	public BoutonRond(String label)
	{
		super(label);
		setContentAreaFilled(false);
	}




	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */

	/**
	 * Dessine le composant graphique avec des modifications personnalisées.
	 *
	 * Cette méthode surcharge `paintComponent` pour personnaliser l'affichage du composant. 
	 * Elle dessine un ovale rempli qui change de couleur en fonction de l'état du modèle 
	 * (armé ou non). Ensuite, elle appelle la méthode `paintComponent` de la classe parente 
	 * pour assurer le rendu standard.
	 *
	 * Comportement :
	 * - Si le modèle est "armé", l'ovale est coloré en gris clair.
	 * - Sinon, il est coloré avec la couleur d'arrière-plan du composant.
	 *
	 * @param g l'objet `Graphics` utilisé pour dessiner le composant.
	 */
	protected void paintComponent(Graphics g)
	{
		if (getModel().isArmed()) { g.setColor(Color.lightGray); }
		else { g.setColor(getBackground()); }

		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
		super.paintComponent(g);
	}



	/**
	 * Dessine la bordure du composant graphique.
	 *
	 * Cette méthode surcharge `paintBorder` pour personnaliser l'affichage de la bordure 
	 * du composant. Elle dessine un ovale en utilisant la couleur de premier plan du 
	 * composant (`getForeground()`), correspondant à ses dimensions.
	 *
	 * @param g l'objet `Graphics` utilisé pour dessiner la bordure.
	 */
	protected void paintBorder(Graphics g)
	{
		g.setColor(getForeground());
		g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
	}



	/**
	 * Vérifie si un point donné est contenu dans les limites circulaires du composant.
	 *
	 * Cette méthode détermine si un point, défini par ses coordonnées `(x, y)`, 
	 * se trouve à l'intérieur du cercle dessiné dans le composant. Le cercle est centré 
	 * dans le composant et son rayon est calculé à partir de la largeur du composant.
	 *
	 * @param x la coordonnée X du point à vérifier.
	 * @param y la coordonnée Y du point à vérifier.
	 * @return `true` si le point est à l'intérieur du cercle, sinon `false`.
	 */
	public boolean contains(int x, int y)
	{
		int radius = getSize().width / 2;
		return (x - radius) * (x - radius) + (y - radius) * (y - radius) <= radius * radius;
	}
}