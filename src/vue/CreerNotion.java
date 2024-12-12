package vue;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;
import modele.Metier;
import modele.Ressource;

public class CreerNotion extends JFrame
{
    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public CreerNotion()
    {
        setTitle("Créer une notion");

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Marges entre les composants

        // JComboBox pour choisir le type de ressource
        String[] allRessources = Metier.getNomsRessources();
        if (allRessources == null)
        {
            allRessources = new String[0];
        }
        JComboBox<String> choixRessource = new JComboBox<>(allRessources);
        choixRessource.setBorder(BorderFactory.createTitledBorder("Ressource"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.3;
        panelPrincipal.add(choixRessource, gbc);

        // JTextArea pour le titre de la ressource
        JTextArea titre = new JTextArea(1, 20); // Taille réduite
        titre.setBorder(BorderFactory.createTitledBorder("Titre de la notion"));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        panelPrincipal.add(titre, gbc);

        // JButton pour ajouter une ressource à côté du titre
        JButton ajouter = new JButton("Ajouter");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.2;
        panelPrincipal.add(ajouter, gbc);

        // Récupérer le contenu du JTextArea et créer une nouvelle ressource
        ajouter.addActionListener(e -> {
            String titreNotion = titre.getText();
            String nomRessource = (String) choixRessource.getSelectedItem();
            Ressource ressource = Metier.trouverRessourceParNom(nomRessource);
            if (!titreNotion.trim().isEmpty())
            {
                Controleur.creerNotion(titreNotion, ressource);
                this.dispose();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Le titre de la notion ne peut pas être vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(panelPrincipal);
        this.setSize(600, 100);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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

    public static void main(String[] args)
    {
        new CreerNotion();
    }
}
