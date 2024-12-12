package vue;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import controleur.Controleur;
import modele.Notion;
import modele.Ressource;

public class Parametre extends JFrame
{
    private JList<String> listeRessources;
    private JList<String> listeNotions;
    private DefaultListModel<String> modeleListeRessources;
    private DefaultListModel<String> modeleListeNotions;
    private CreerRessource creerRessource;
    private CreerNotion creerNotion;
    private JButton boutonAjouterNotion;

    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public Parametre()
    {
        setTitle("Paramètres");

        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new BorderLayout());

        // Création d'un panneau gérant la création de ressource et notion
        JPanel panneauCreation = new JPanel();
        panneauCreation.setLayout(new BorderLayout());

        // Panneau pour les boutons de ressources
        JPanel panneauRessources = new JPanel();
        panneauRessources.setLayout(new FlowLayout());

        // Création des boutons pour les ressources
        JButton boutonAjouterRessource = new JButton("Ajouter une ressource");
        boutonAjouterRessource.setBackground(Color.GREEN);
        boutonAjouterRessource.setPreferredSize(new Dimension(180, 30));
        boutonAjouterRessource.addActionListener(e -> {
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

        JButton boutonSupprimerRessource = new JButton(redimensionnerIcone("./lib/icones/delete.png", 20, 20));
        boutonSupprimerRessource.setPreferredSize(new Dimension(30, 30));
        boutonSupprimerRessource.addActionListener(e -> {
            String ressourceSelectionnee = listeRessources.getSelectedValue();
            if (ressourceSelectionnee != null)
            {
                Controleur.supprimerRessource(ressourceSelectionnee);
                chargerRessources();
            }
        });

        JButton boutonModifierRessource = new JButton(redimensionnerIcone("./lib/icones/edit.png", 20, 20));
        boutonModifierRessource.setPreferredSize(new Dimension(30, 30));
        boutonModifierRessource.addActionListener(e -> {
            String ressourceSelectionnee = listeRessources.getSelectedValue();
            if (ressourceSelectionnee != null)
            {
                Controleur.modifierRessource(ressourceSelectionnee);
                chargerRessources();
            }
        });

        // Ajout des boutons de ressources au panneauRessources
        panneauRessources.add(boutonAjouterRessource);
        panneauRessources.add(boutonSupprimerRessource);
        panneauRessources.add(boutonModifierRessource);

        // Panneau pour les boutons de notions
        JPanel panneauNotions = new JPanel();
        panneauNotions.setLayout(new FlowLayout());

        // Création des boutons pour les notions
        boutonAjouterNotion = new JButton("Ajouter une notion");
        boutonAjouterNotion.setBackground(Color.GREEN);
        boutonAjouterNotion.setPreferredSize(new Dimension(150, 30));
        boutonAjouterNotion.setEnabled(false); // Désactivé par défaut
        boutonAjouterNotion.addActionListener(e -> {
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
                {
                    mettreAJourListeNotions(listeRessources.getSelectedValue());
                }
            });
        });

        JButton boutonSupprimerNotion = new JButton(redimensionnerIcone("./lib/icones/delete.png", 20, 20));
        boutonSupprimerNotion.setPreferredSize(new Dimension(30, 30));
        boutonSupprimerNotion.addActionListener(e -> {
            String ressourceSelectionnee = listeRessources.getSelectedValue();
            String notionSelectionnee = listeNotions.getSelectedValue();
            if (ressourceSelectionnee != null && notionSelectionnee != null)
            {
                Controleur.supprimerNotion(ressourceSelectionnee, notionSelectionnee);
                mettreAJourListeNotions(ressourceSelectionnee);
            }
        });

        JButton boutonModifierNotion = new JButton(redimensionnerIcone("./lib/icones/edit.png", 20, 20));
        boutonModifierNotion.setPreferredSize(new Dimension(30, 30));
        boutonModifierNotion.addActionListener(e -> {
            String ressourceSelectionnee = listeRessources.getSelectedValue();
            String notionSelectionnee = listeNotions.getSelectedValue();
            if (ressourceSelectionnee != null && notionSelectionnee != null)
            {
                Controleur.modifierNotion(ressourceSelectionnee, notionSelectionnee);
                mettreAJourListeNotions(ressourceSelectionnee);
            }
        });

        // Ajout des boutons de notions au panneauNotions
        panneauNotions.add(boutonAjouterNotion);
        panneauNotions.add(boutonSupprimerNotion);
        panneauNotions.add(boutonModifierNotion);

        // Ajout des deux panneaux au panneauCreation
        panneauCreation.add(panneauRessources, BorderLayout.WEST);
        panneauCreation.add(panneauNotions, BorderLayout.EAST);

        // Liste des ressources
        modeleListeRessources = new DefaultListModel<>();
        listeRessources = new JList<>(modeleListeRessources);
        listeRessources.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (!e.getValueIsAdjusting())
                {
                    String ressourceSelectionnee = listeRessources.getSelectedValue();
                    mettreAJourListeNotions(ressourceSelectionnee);
                    boutonAjouterNotion.setEnabled(ressourceSelectionnee != null); // Activer le bouton si une ressource est sélectionnée
                }
            }
        });

        // Liste des notions
        modeleListeNotions = new DefaultListModel<>();
        listeNotions = new JList<>(modeleListeNotions);

        // Ajout des listes au panneau principal
        JPanel panneauListes = new JPanel(new GridLayout(1, 2));
        panneauListes.add(new JScrollPane(listeRessources));
        panneauListes.add(new JScrollPane(listeNotions));

        // Bouton de retour
        String cheminImage = "./lib/icones/home.png";
        File fichierImage = new File(cheminImage);
        ImageIcon icone = new ImageIcon(fichierImage.getAbsolutePath());
        Image img = icone.getImage();
        Image imgRedimensionnee = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icone = new ImageIcon(imgRedimensionnee);
        JButton boutonRetour = new JButton(icone);
        boutonRetour.addActionListener(e -> {
            if (this.creerRessource != null)
            {
                this.creerRessource.dispose();
            }
            if (this.creerNotion != null)
            {
                this.creerNotion.dispose();
            }
            Controleur.ouvrirAccueil();
            dispose();
        });
        JPanel panneauRetour = new JPanel();
        panneauRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
        panneauRetour.add(boutonRetour);

        // Ajout des panneaux au panneau principal
        panneauPrincipal.add(panneauRetour, BorderLayout.NORTH);
        panneauPrincipal.add(panneauListes, BorderLayout.CENTER);
        panneauPrincipal.add(panneauCreation, BorderLayout.SOUTH);

        this.add(panneauPrincipal);
        this.setSize(700, 400); // Augmenter la taille pour accueillir les listes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Remplir la liste des ressources au démarrage
        chargerRessources();
    }

    // Méthode utilitaire pour redimensionner les icônes
    private ImageIcon redimensionnerIcone(String chemin, int largeur, int hauteur)
    {
        ImageIcon icone = new ImageIcon(chemin);
        Image img = icone.getImage();
        Image imgRedimensionnee = img.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        return new ImageIcon(imgRedimensionnee);
    }

    // Méthode pour charger les ressources dans la JList
    private void chargerRessources()
    {
        modeleListeRessources.clear();
        for (Ressource ressource : Controleur.getListeRessources())
        {
            modeleListeRessources.addElement(ressource.getNom());
        }
    }

    // Méthode pour mettre à jour la liste des notions en fonction de la ressource sélectionnée
	private void mettreAJourListeNotions(String nomRessource)
	{
		modeleListeNotions.clear();
		Ressource ressource = Controleur.trouverRessourceParNom(nomRessource);
		if (ressource != null)
		{
			for (String notion : ressource.getNomsNotions())
			{
				modeleListeNotions.addElement(notion);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Parametre();
	}
}
	