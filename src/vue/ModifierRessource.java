package vue;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import modele.Ressource;

public class ModifierRessource extends JFrame
{
    /**
     * +-------------+
     * |  ATTRIBUTS  |
     * +-------------+
     */

    private JTextField texteNom;
    private JTextField texteId;
    private Ressource ressource;

    /**
     * +--------------+
     * | CONSTRUCTEUR |
     * +--------------+
     */

    public ModifierRessource(Ressource ressource)
    {
        this.ressource = ressource;

        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label pour l'ID de la ressource
        JLabel labelId = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelPrincipal.add(labelId, gbc);

        // Champ pour l'ID de la ressource
        texteId = new JTextField(20);
        texteId.setText(String.valueOf(ressource.getId()));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panelPrincipal.add(texteId, gbc);

        // Label pour le nom de la ressource
        JLabel labelNom = new JLabel("Nom:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelPrincipal.add(labelNom, gbc);

        // Champ pour le nom de la ressource
        texteNom = new JTextField(20);
        texteNom.setText(ressource.getNom());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelPrincipal.add(texteNom, gbc);

        // Bouton pour enregistrer les modifications
        JButton btnEnregistrer = new JButton("Enregistrer");

        btnEnregistrer.addActionListener(e ->
        {
            String nouveauNom = texteNom.getText();
            String nouvelId = texteId.getText();
            if (nouveauNom != null && !nouveauNom.trim().isEmpty() && nouvelId != null && !nouvelId.trim().isEmpty())
            {
                File ancienChemin = new File("./lib/ressources/" + ressource.getId() + "_" + ressource.getNom());
                File nouveauChemin = new File("./lib/ressources/" + nouvelId + "_" + nouveauNom);

                if (ancienChemin.exists() && !nouveauChemin.exists())
                {
                    boolean succes = ancienChemin.renameTo(nouveauChemin);

                    if (succes)
                    {
                        ressource.setNom(nouveauNom);
                        ressource.setId(nouvelId);
                        dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(this, "Impossible de renommer le répertoire", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panelPrincipal.add(btnEnregistrer, gbc);

        // Bouton pour annuler
        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(e -> dispose());
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelPrincipal.add(btnAnnuler, gbc);

        // Ajout du panel principal à la frame et configuration de cette dernière
        this.add(panelPrincipal);
        this.setTitle("Modifier Ressource");
        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
