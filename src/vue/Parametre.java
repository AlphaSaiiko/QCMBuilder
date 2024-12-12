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
    private JList<String> ressourceList;
    private JList<String> notionList;
    private DefaultListModel<String> ressourceListModel;
    private DefaultListModel<String> notionListModel;
    private CreerRessource creerRessource;
    private CreerNotion creerNotion;

    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public Parametre()
    {
        setTitle("Paramètres");

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

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
        ajtRessource.addActionListener(e -> {
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
                    loadRessources();
                }
            });
        });

        JButton supprRessource = new JButton(resizeIcon("./lib/icones/delete.png", 20, 20));
        supprRessource.setPreferredSize(new Dimension(30, 30));
        supprRessource.addActionListener(e -> {
            String selectedRessource = ressourceList.getSelectedValue();
            if (selectedRessource != null)
            {
                Controleur.supprimerRessource(selectedRessource);
                loadRessources();
            }
        });

        JButton modifRessource = new JButton(resizeIcon("./lib/icones/edit.png", 20, 20));
        modifRessource.setPreferredSize(new Dimension(30, 30));
        modifRessource.addActionListener(e -> {
            String selectedRessource = ressourceList.getSelectedValue();
            if (selectedRessource != null)
            {
                Controleur.modifierRessource(selectedRessource);
                loadRessources();
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
        ajtNotion.addActionListener(e -> {
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
                    updateNotionList(ressourceList.getSelectedValue());
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
                Controleur.supprimerNotion(selectedRessource, selectedNotion);
                updateNotionList(selectedRessource);
            }
        });

        JButton modifNotion = new JButton(resizeIcon("./lib/icones/edit.png", 20, 20));
        modifNotion.setPreferredSize(new Dimension(30, 30));
        modifNotion.addActionListener(e -> {
            String selectedRessource = ressourceList.getSelectedValue();
            String selectedNotion = notionList.getSelectedValue();
            if (selectedRessource != null && selectedNotion != null)
            {
                Controleur.modifierNotion(selectedRessource, selectedNotion);
                updateNotionList(selectedRessource);
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
        retourButton.addActionListener(e -> {
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
        JPanel panelRetour = new JPanel();
        panelRetour.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelRetour.add(retourButton);

        // Ajout des panels au panel principal
        panelPrincipal.add(panelRetour, BorderLayout.NORTH);
        panelPrincipal.add(listPanel, BorderLayout.CENTER);
        panelPrincipal.add(panelCreation, BorderLayout.SOUTH);

        this.add(panelPrincipal);
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

    // Méthode pour charger les ressources dans la JList
    private void loadRessources()
    {
        ressourceListModel.clear();
        for (Ressource ressource : Controleur.getListRessource())
        {
            ressourceListModel.addElement(ressource.getNom());
        }
    }

    // Méthode pour mettre à jour la liste des notions en fonction de la ressource sélectionnée
    private void updateNotionList(String ressourceName)
    {
        notionListModel.clear();
        Ressource ressource = Controleur.trouverRessourceParNom(ressourceName);
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
