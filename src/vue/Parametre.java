package vue;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import modele.Ressource;

public class Parametre extends JFrame {

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
    public Parametre() {
        setTitle("Paramètres");

        JPanel panelprincipal = new JPanel();
        panelprincipal.setLayout(new BorderLayout());

        // Création d'un panel gérant la création de question
        JPanel panelCreation = new JPanel();
        panelCreation.setLayout(new FlowLayout());

        // Création des boutons verts
        JButton ajtRessource = new JButton("Ajouter une ressource");
        ajtRessource.setBackground(Color.GREEN);
        ajtRessource.setPreferredSize(new Dimension(150, 30));
        ajtRessource.addActionListener(e -> {
            // Code pour lancer la création d'une ressource

            // Pour avoir une fenêtre unique
            if (this.creerRessource != null) {
                this.creerRessource.dispose();
                this.creerRessource = null;
            }

            this.creerRessource = new CreerRessource();
            this.creerRessource.setVisible(true);
            this.creerRessource.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) { // Actualiser la liste des ressources après la fermeture de la fenêtre
                    loadRessources();
                }
            });
        });

        JButton ajtNotion = new JButton("Ajouter une notion");
        ajtNotion.setBackground(Color.GREEN);
        ajtNotion.setPreferredSize(new Dimension(150, 30));
        ajtNotion.addActionListener(e -> {
            // Code pour lancer la création d'une notion

            // Pour avoir une fenêtre unique
            if (this.creerNotion != null) {
                this.creerNotion.dispose();
                this.creerNotion = null;
            }

            this.creerNotion = new CreerNotion();
            this.creerNotion.setVisible(true);
            this.creerNotion.addWindowListener(new WindowAdapter() {
                public void windowClosed(WindowEvent e) { // Actualiser la liste des ressources après la fermeture de la fenêtre
                    loadRessources();
                }
            });
        });

        // Ajout des composants au panel
        panelCreation.add(ajtRessource);
        panelCreation.add(ajtNotion);

        // Liste des ressources
        ressourceListModel = new DefaultListModel<>();
        ressourceList = new JList<>(ressourceListModel);
        ressourceList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
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
            if (this.creerRessource != null) {
                this.creerRessource.dispose();
            }
            if (this.creerNotion != null) {
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

    // Méthode pour charger les ressources dans la JList
    private void loadRessources() {
        ressourceListModel.clear();
        for (Ressource ressource : Ressource.getListRessource()) {
            ressourceListModel.addElement(ressource.getNom());
        }
    }

    // Méthode pour mettre à jour la liste des notions en fonction de la ressource sélectionnée
    private void updateNotionList(String ressourceName) {
        notionListModel.clear();
        Ressource ressource = Ressource.trouverRessourceParNom(ressourceName);
        if (ressource != null) {
            for (String notion : ressource.getNomsNotions()) {
                notionListModel.addElement(notion);
            }
        }
    }

    public static void main(String[] args) {
        new Parametre();
    }
}
