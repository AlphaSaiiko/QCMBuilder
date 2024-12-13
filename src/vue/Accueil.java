package vue;

import controleur.Controleur;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Accueil extends JFrame
{
    /*
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */
    public Accueil()
    {
        setTitle("Accueil");

        // Configuration du panel principal + espace de 25 au nord
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(25, 0, 0, 0));
        panelPrincipal.setLayout(new BorderLayout());

        // Configuration du titre
        JPanel panelTitre = new JPanel();
        panelTitre.setLayout(new BorderLayout());
        JLabel lblTitre = new JLabel("Bienvenue dans l'Application QCM Builder");
        lblTitre.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        panelTitre.add(lblTitre, BorderLayout.CENTER);

        // Configuration du panel des boutons de l'accueil
        JPanel panelBoutons = new JPanel();
        panelBoutons.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;

        // Création des boutons
        JButton btnGenererEval = new JButton("Générer une Evaluation");
        JButton btnCreerQuestion = new JButton("Créer une question");
        JButton btnParametres = new JButton("Paramètres");

        // Configuration police des boutons
        btnGenererEval.setFont(new Font("Arial", Font.PLAIN, 18));
        btnCreerQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
        btnParametres.setFont(new Font("Arial", Font.PLAIN, 18));

        // Configuration de la taille des boutons
        Dimension buttonSize = new Dimension(300, 50);
        btnGenererEval.setPreferredSize(buttonSize);
        btnCreerQuestion.setPreferredSize(buttonSize);
        btnParametres.setPreferredSize(buttonSize);

        // Ajout des interactions des boutons
        btnGenererEval.addActionListener(e -> {
            if (Controleur.getListRessource().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Aucune ressource n'existe. Veuillez ajouter une ressource.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Controleur.creerEvaluation();
                this.dispose();
            }
        });

        btnCreerQuestion.addActionListener(e -> {
            if (Controleur.getListRessource().isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Aucune ressource n'existe. Veuillez ajouter une ressource.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                Controleur.creerQuestion();
                this.dispose();
            }
        });

        btnParametres.addActionListener(e -> {
            Controleur.ouvrirParametres();
            this.dispose();
        });

        // Ajout des boutons au panel des boutons
        panelBoutons.add(btnParametres, gbc);
        panelBoutons.add(btnCreerQuestion, gbc);
        panelBoutons.add(btnGenererEval, gbc);

        // Ajout des panels secondaires au panel principal
        panelPrincipal.add(panelTitre, BorderLayout.NORTH);
        panelPrincipal.add(panelBoutons, BorderLayout.CENTER);

        // Ajout du panel principal et configuration de la frame
        this.add(panelPrincipal);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Vérifier la liste des ressources après l'initialisation de l'interface graphique
        SwingUtilities.invokeLater(() -> {
            if (Controleur.getListRessource().isEmpty())
            {
                btnGenererEval.setEnabled(false);
                btnCreerQuestion.setEnabled(false);
            }
        });
    }
}
