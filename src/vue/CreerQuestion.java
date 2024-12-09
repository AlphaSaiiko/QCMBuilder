package vue;

import javax.swing.*;
import java.awt.*;

public class CreerQuestion extends JFrame
{
	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public CreerQuestion() {

		setTitle("Créer une question");

		JPanel panelprincipal = new JPanel();
		panelprincipal.setLayout(new BorderLayout());

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
		
		// Panel pour bien placer les ronds de difficultés et mettre le titre
		JPanel panelDifficulte = new JPanel();
		panelDifficulte.setLayout(new BorderLayout());
		panelDifficulte.add(new JLabel("niveau"), BorderLayout.NORTH);

		// Creation des boutons rond pour les niveaux de difficultés avec les couleurs et texte correspondant
		JButton tresfacile = creerButtonRond(Color.GREEN, "TF");
		JButton facile = creerButtonRond(Color.CYAN, "F");
		JButton moyen = creerButtonRond(Color.RED, "M");
		JButton dur = creerButtonRond(Color.GRAY, "D");

		// Définir la taille du texte
		Font font = new Font("Arial", Font.BOLD, 8);
		tresfacile.setFont(font);
		facile.setFont(font);
		moyen.setFont(font);
		dur.setFont(font);
		
		// Panel pour les ronds de difficultés
		JPanel panelRond = new JPanel();
		panelRond.setLayout(new FlowLayout());
		panelRond.add(tresfacile);
		panelRond.add(facile);
		panelRond.add(moyen);
		panelRond.add(dur);
		
		panelDifficulte.add(panelRond, BorderLayout.CENTER);
		
		//Creation JComboBox pour les ressources et matières
		JComboBox<String> ressourcesList = new JComboBox<String>(new String[] { "R1.01.Init Dev", "R1.01.Init Dev", "R1.01.Init Dev", "R1.01.Init Dev" });
		ressourcesList.setPreferredSize(new Dimension(150, 25));
		
		JComboBox<String> matieresList = new JComboBox<String>(new String[] { "Variable", "Intro_Php", "Fuction", "MVC" });
		matieresList.setPreferredSize(new Dimension(150, 25));
		
		// Panel pour les ressources
		JPanel ressources = new JPanel();
		ressources.setLayout(new BorderLayout());
		ressources.add(new JLabel("Ressources"), BorderLayout.NORTH);
		ressources.add(ressourcesList, BorderLayout.CENTER);
		
		// Panel pour les notions
		JPanel notions = new JPanel();
		notions.setLayout(new BorderLayout());
		notions.add(new JLabel("Notions"), BorderLayout.NORTH);
		notions.add(matieresList, BorderLayout.CENTER);
		
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

		// Ajout des différents panels à panel principal
		panelprincipal.add(rassemble, BorderLayout.NORTH);
		panelprincipal.add(ressourceNotionLvl, BorderLayout.CENTER);
		
		this.add(panelprincipal);
		this.setSize(700, 160);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// Création des boutons ronds pour les niveaux de difficultés
	public static JButton creerButtonRond(Color couleur, String texte)
	{
		JButton button = new RoundButton(texte);
		button.setPreferredSize(new Dimension(45, 45));
		button.setBackground(couleur);
		button.setOpaque(false);
		button.setBorderPainted(false);
		return button;
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