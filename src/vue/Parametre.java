package vue;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modele.Notion;
import modele.Ressource;

public class Parametre extends JFrame
{

	private JList<String> ressourceList;
	private JList<String> notionList;
	private DefaultListModel<String> ressourceListModel;
	private DefaultListModel<String> notionListModel;

	private CreerRessource creerRessource;
	private CreerNotion creerNotion;

	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Parametre()
	{
		setTitle("Paramètres");

		JPanel panelprincipal = new JPanel();
		panelprincipal.setLayout(new BorderLayout());

		// Création d'un panel gérant la création de question
		JPanel panelCreation = new JPanel();
		panelCreation.setLayout(new BorderLayout());

		// Panel pour les boutons de ressources
		JPanel panelRessources = new JPanel();
		panelRessources.setLayout(new FlowLayout());

		// Création des boutons pour les ressources
		JButton ajtRessource = new JButton("Ajouter une ressource");
		ajtRessource.setBackground(Color.GREEN);
		ajtRessource.setPreferredSize(new Dimension(180, 30));
		ajtRessource.addActionListener(e ->
		{
			// Code pour lancer la création d'une ressource

			// Pour avoir une fenêtre unique
			if (this.creerRessource != null)
			{
				this.creerRessource.dispose();
				this.creerRessource = null;
			}

			this.creerRessource = new CreerRessource();
			this.creerRessource.setVisible(true);
			this.creerRessource.addWindowListener(new WindowAdapter()
			{
				public void windowClosed(WindowEvent e)
				{ // Actualiser la liste des ressources après la fermeture de la fenêtre
					loadRessources();
				}
			});
		});

		JButton supprRessource = new JButton(resizeIcon("./lib/icones/delete.png", 20, 20));
		supprRessource.setPreferredSize(new Dimension(30, 30));
		supprRessource.addActionListener(e -> {
			String selectedRessource = ressourceList.getSelectedValue();
			if (selectedRessource != null) {
				Ressource ressource = Ressource.trouverRessourceParNom(selectedRessource);
				if (ressource != null) {
					// Supprimer le répertoire associé à la ressource
					File ressourceDir = new File("./lib/ressources/" + ressource.getNom());
					if (ressourceDir.exists()) {
						deleteDirectory(ressourceDir);
					}
					// Supprimer la ressource de la liste
					Ressource.getListRessource().remove(ressource);
					loadRessources();
				}
			}
		});


		JButton modifRessource = new JButton(resizeIcon("./lib/icones/edit.png", 20, 20));
		modifRessource.setPreferredSize(new Dimension(30, 30));
		modifRessource.addActionListener(e ->
		{
			String selectedRessource = ressourceList.getSelectedValue();
			if (selectedRessource != null)
			{
				String newRessourceName = JOptionPane.showInputDialog("Modifier la ressource", selectedRessource);
				if (newRessourceName != null && !newRessourceName.trim().isEmpty())
				{
					Ressource ressource = Ressource.trouverRessourceParNom(selectedRessource);
					if (ressource != null)
					{
						ressource.setNom(newRessourceName);
						loadRessources();
					}
				}
			}
		});

		// Ajout des boutons de ressources au panelRessources
		panelRessources.add(ajtRessource);
		panelRessources.add(supprRessource);
		panelRessources.add(modifRessource);

		// Panel pour les boutons de notions
		JPanel panelNotions = new JPanel();
		panelNotions.setLayout(new FlowLayout());

		// Création des boutons pour les notions
		JButton ajtNotion = new JButton("Ajouter une notion");
		ajtNotion.setBackground(Color.GREEN);
		ajtNotion.setPreferredSize(new Dimension(150, 30));
		ajtNotion.addActionListener(e ->
		{
			// Code pour lancer la création d'une notion

			// Pour avoir une fenêtre unique
			if (this.creerNotion != null)
			{
				this.creerNotion.dispose();
				this.creerNotion = null;
			}

			this.creerNotion = new CreerNotion();
			this.creerNotion.setVisible(true);
			this.creerNotion.addWindowListener(new WindowAdapter()
			{
				public void windowClosed(WindowEvent e)
				{ // Actualiser la liste des ressources après la fermeture de la fenêtre
					loadRessources();
				}
			});
		});

		JButton supprNotion = new JButton(resizeIcon("./lib/icones/delete.png", 20, 20));
		supprNotion.setPreferredSize(new Dimension(30, 30));
		supprNotion.addActionListener(e ->
		{
			String selectedRessource = ressourceList.getSelectedValue();
			String selectedNotion = notionList.getSelectedValue();
			if (selectedRessource != null && selectedNotion != null)
			{
				Ressource ressource = Ressource.trouverRessourceParNom(selectedRessource);
				if (ressource != null)
				{
					Iterator<Notion> iterator = ressource.getEnsNotions().iterator();
					while (iterator.hasNext())
					{
						Notion notion = iterator.next();
						if (notion.getNom().equals(selectedNotion))
						{
							iterator.remove();
							break;
						}
					}
					updateNotionList(selectedRessource);
				}
			}
		});

		JButton modifNotion = new JButton(resizeIcon("./lib/icones/edit.png", 20, 20));
		modifNotion.setPreferredSize(new Dimension(30, 30));
		modifNotion.addActionListener(e ->
		{
			String selectedRessource = ressourceList.getSelectedValue();
			String selectedNotion = notionList.getSelectedValue();
			if (selectedRessource != null && selectedNotion != null)
			{
				String newNotionName = JOptionPane.showInputDialog("Modifier la notion", selectedNotion);
				if (newNotionName != null && !newNotionName.trim().isEmpty())
				{
					Ressource ressource = Ressource.trouverRessourceParNom(selectedRessource);
					if (ressource != null)
					{
						// Trouver la notion dans la liste des notions de la ressource
						for (Notion notion : ressource.getEnsNotions())
						{
							if (notion.getNom().equals(selectedNotion))
							{
								notion.setNom(newNotionName);
								break;
							}
						}
						updateNotionList(selectedRessource);
					}
				}
			}
		});

		// Ajout des boutons de notions au panelNotions
		panelNotions.add(ajtNotion);
		panelNotions.add(supprNotion);
		panelNotions.add(modifNotion);

		// Ajout des deux panels au panelCreation
		panelCreation.add(panelRessources, BorderLayout.WEST);
		panelCreation.add(panelNotions, BorderLayout.EAST);

		// Liste des ressources
		ressourceListModel = new DefaultListModel<>();
		ressourceList = new JList<>(ressourceListModel);
		ressourceList.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				if (!e.getValueIsAdjusting())
				{
					String selectedRessource = ressourceList.getSelectedValue();
					updateNotionList(selectedRessource);
				}
			}
		});

		// Liste des notions
		notionListModel = new DefaultListModel<>();
		notionList = new JList<>(notionListModel);

		// Ajout des listes au panneau principal
		JPanel listPanel = new JPanel(new GridLayout(1, 2));
		listPanel.add(new JScrollPane(ressourceList));
		listPanel.add(new JScrollPane(notionList));

		// Bouton de retour
		String imagePath = "./lib/icones/home.png";
		File imageFile = new File(imagePath);
		ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);
		JButton retourButton = new JButton(icon);
		retourButton.addActionListener(e ->
		{
			if (this.creerRessource != null)
			{
				this.creerRessource.dispose();
			}
			if (this.creerNotion != null)
			{
				this.creerNotion.dispose();
			}
			Accueil accueil = new Accueil();
			accueil.setVisible(true);
			dispose();
		});
		JPanel panelRetour = new JPanel();
		panelRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelRetour.add(retourButton);

		// Ajout des panels au panel principal
		panelprincipal.add(panelRetour, BorderLayout.NORTH);
		panelprincipal.add(listPanel, BorderLayout.CENTER);
		panelprincipal.add(panelCreation, BorderLayout.SOUTH);

		this.add(panelprincipal);
		this.setSize(700, 400); // Augmenter la taille pour accueillir les listes
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		// Remplir la liste des ressources au démarrage
		loadRessources();
	}

	// Méthode utilitaire pour redimensionner les icônes
	private ImageIcon resizeIcon(String path, int width, int height)
	{
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImg);
	}

	// Méthode pour supprimer un répertoire et son contenu
	private void deleteDirectory(File directory) {
		File[] files = directory.listFiles();
		if (files != null) { // Vérifier si le répertoire n'est pas vide
			for (File file : files) {
				if (file.isDirectory()) {
					deleteDirectory(file);
				} else {
					file.delete();
				}
			}
		}
		directory.delete();
	}

	// Méthode pour charger les ressources dans la JList
	private void loadRessources()
	{
		ressourceListModel.clear();
		for (Ressource ressource : Ressource.getListRessource())
		{
			ressourceListModel.addElement(ressource.getNom());
		}
	}

	// Méthode pour mettre à jour la liste des notions en fonction de la ressource sélectionnée
	private void updateNotionList(String ressourceName)
	{
		notionListModel.clear();
		Ressource ressource = Ressource.trouverRessourceParNom(ressourceName);
		if (ressource != null)
		{
			for (String notion : ressource.getNomsNotions())
			{
				notionListModel.addElement(notion);
			}
		}
	}

	public static void main(String[] args)
	{
		new Parametre();
	}
}

