package vue;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controleur.Controleur;
import modele.Notion;
import modele.Ressource;

public class Parametre extends JFrame
{
	/**
	 * +-------------+
	 * |  ATTRIBUTS  |
	 * +-------------+
	 */

	private JList<String>            ressourceList     ;
	private JList<String>            notionList        ;
	private DefaultListModel<String> ressourceListModel;
	private DefaultListModel<String> notionListModel   ;
	private CreerRessource           creerRessource    ;
	private CreerNotion              creerNotion       ;




	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Parametre()
	{
		//Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BorderLayout());


		// Création du panel qui contiens tous les boutons en bas de la fenètre
		JPanel panelBoutons = new JPanel();
		panelBoutons.setLayout(new BorderLayout());
		

		// Panel pour les boutons de ressources
		JPanel panelRessources = new JPanel();
		panelRessources.setLayout(new FlowLayout());

		// Création des boutons pour les ressources
		JButton ajtRessource = new JButton("Ajouter une ressource");
		ajtRessource.setBackground(Color.GREEN);
		ajtRessource.setPreferredSize(new Dimension(180, 30));
		ajtRessource.addActionListener(e ->
		{
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
				{
					chargerRessources();
				}
			});
		});

		JButton supprRessource = new JButton(resizeIcon("./lib/icones/delete.png", 20, 20));
		supprRessource.setPreferredSize(new Dimension(30, 30));
		supprRessource.addActionListener(e -> {
			
			String selectedRessource = ressourceList.getSelectedValue();
			if (selectedRessource != null) 
			{
				Ressource ressource = Controleur.trouverRessourceParNom(selectedRessource);
				if (ressource != null) 
				{
					// Supprimer le répertoire associé à la ressource
					File ressourceDir = new File("./lib/ressources/" + ressource.getNom());
					if (ressourceDir.exists()) 
					{
						supprimerDossier(ressourceDir);
					}

					// Supprimer la ressource de la liste
					Controleur.supprRessource(ressource);
					chargerRessources();
				}
			}
		});


		JButton modifRessource = new JButton(resizeIcon("./lib/icones/edit.png", 20, 20));
		modifRessource.setPreferredSize(new Dimension(30, 30));
		modifRessource.addActionListener(e -> {
			
			String selectedRessource = ressourceList.getSelectedValue();
			if (selectedRessource != null) 
			{
				Ressource ressource = Controleur.trouverRessourceParNom(selectedRessource);
				if (ressource != null) 
				{
					ModifierRessource modifierRessourceFrame = new ModifierRessource(ressource);
					modifierRessourceFrame.setVisible(true);

					modifierRessourceFrame.addWindowListener(new WindowAdapter() 
					{
						public void windowClosed(WindowEvent e) 
						{
							chargerRessources();
						}
					});
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
		ajtNotion.setBackground(Color.GRAY);  // Initialiser en gris
		ajtNotion.setEnabled(false);  // Initialiser comme désactivé
		ajtNotion.setPreferredSize(new Dimension(150, 30));
		

		ajtNotion.addActionListener(e ->
		{
			if (this.creerNotion != null)
			{
				this.creerNotion.dispose();
				this.creerNotion = null;
			}

			Ressource selectedRessource = Controleur.trouverRessourceParNom(ressourceList.getSelectedValue());
			this.creerNotion = new CreerNotion(selectedRessource);
			this.creerNotion.setVisible(true);
			this.creerNotion.addWindowListener(new WindowAdapter()
			{
				public void windowClosed(WindowEvent e)
				{
					chargerRessources();
					majListeNotion(selectedRessource.getNom());
				}
			});
		});



		JButton supprNotion = new JButton(resizeIcon("./lib/icones/delete.png", 20, 20));
		supprNotion.setPreferredSize(new Dimension(30, 30));
		supprNotion.addActionListener(e -> {

			String selectedRessource = ressourceList.getSelectedValue();
			String selectedNotion = notionList.getSelectedValue();

			if (selectedRessource != null && selectedNotion != null) 
			{
				Ressource ressource = Controleur.trouverRessourceParNom(selectedRessource);
				if (ressource != null) 
				{
					Notion notion = ressource.getNotion(selectedNotion);
					if (notion != null) 
					{
						// Supprimer le répertoire associé à la notion
						File notionDir = new File("./lib/ressources/" + notion.getRessource().getNom() + "/" + notion.getNom());
						if (notionDir.exists()) 
						{
							supprimerDossier(notionDir);
						}

						// Supprimer la notion de la liste
						ressource.getEnsNotions().remove(notion);
						majListeNotion(selectedRessource);
					}
				}
			}
		});

		JButton modifNotion = new JButton(resizeIcon("./lib/icones/edit.png", 20, 20));
		modifNotion.setPreferredSize(new Dimension(30, 30));
		modifNotion.addActionListener(e -> {

			String selectedRessource = ressourceList.getSelectedValue();
			String selectedNotion = notionList.getSelectedValue();
			if (selectedRessource != null && selectedNotion != null) 
			{
				Ressource ressource = Controleur.trouverRessourceParNom(selectedRessource);
				if (ressource != null) 
				{
					for (Notion notion : ressource.getEnsNotions()) 
					{
						if (notion.getNom().equals(selectedNotion)) 
						{
							ModifierNotion modifierNotionFrame = new ModifierNotion(ressource, notion);
							modifierNotionFrame.setVisible(true);
							modifierNotionFrame.addWindowListener(new WindowAdapter() 
							{
								public void windowClosed(WindowEvent e) 
								{
									majListeNotion(selectedRessource);
								}
							});
							break;
						}
					}
				}
			}
		});


		// Ajout des boutons de notions au panelNotions
		panelNotions.add(ajtNotion);
		panelNotions.add(supprNotion);
		panelNotions.add(modifNotion);

		// Ajout des deux panels au panelCreation
		panelBoutons.add(panelRessources, BorderLayout.WEST);
		panelBoutons.add(panelNotions, BorderLayout.EAST);

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
					majListeNotion(selectedRessource);

					// Activer et changer la couleur du bouton "Ajouter une notion" lorsque 
					// une ressource est sélectionnée
					if (selectedRessource != null)
					{
						ajtNotion.setBackground(Color.GREEN);
						ajtNotion.setEnabled(true);
					}
					else
					{
						ajtNotion.setBackground(Color.GRAY);
						ajtNotion.setEnabled(false);
					}
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


		// Ajout des composants au panel principal
		panelPrincipal.add(panelRetour, BorderLayout.NORTH);
		panelPrincipal.add(listPanel, BorderLayout.CENTER);
		panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);

		
		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal);
		this.setTitle("Paramètres");
		this.setSize(700, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);


		// Remplir la liste des ressources au démarrage
		this.chargerRessources();
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
	private void supprimerDossier(File directory) 
	{
		File[] files = directory.listFiles();
		if (files != null) { // Vérifier si le répertoire n'est pas vide
			for (File file : files) {
				if (file.isDirectory()) {
					supprimerDossier(file);
				} else {
					file.delete();
				}
			}
		}
		directory.delete();
	}

	// Méthode pour charger les ressources dans la JList
	private void chargerRessources()
	{
		String selectedRessource = ressourceList.getSelectedValue();
		ressourceListModel.clear();
		for (Ressource ressource : Controleur.getListRessource())
		{
			ressourceListModel.addElement(ressource.getNom());
		}
		ressourceList.setSelectedValue(selectedRessource, true);
	}


	// Méthode pour mettre à jour la liste des notions en fonction de la ressource sélectionnée
	private void majListeNotion(String ressourceName)
	{
		String selectedNotion = notionList.getSelectedValue();
		notionListModel.clear();
		Ressource ressource = Controleur.trouverRessourceParNom(ressourceName);
		
		if (ressource != null)
		{
			for (String notion : ressource.getNomsNotions())
			{
				notionListModel.addElement(notion);
			}
		}
		notionList.setSelectedValue(selectedNotion, true);
	}
}

