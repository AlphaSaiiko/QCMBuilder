package vue;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import modele.Ressource;

public class ModifierRessource extends JFrame {

    private JTextField nomField;
    private Ressource ressource;

    public ModifierRessource(Ressource ressource) {
        this.ressource = ressource;
        setTitle("Modifier Ressource");
        setSize(300, 150);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champ pour le nom de la ressource
        nomField = new JTextField(20);
        nomField.setText(ressource.getNom());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(nomField, gbc);

        // Bouton pour enregistrer les modifications
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e -> {
            String newNom = nomField.getText();
            if (newNom != null && !newNom.trim().isEmpty()) {
                File oldDir = new File("./lib/ressources/" + ressource.getNom());
                File newDir = new File("./lib/ressources/" + newNom);
                if (oldDir.exists() && !newDir.exists()) {
                    boolean success = oldDir.renameTo(newDir);
                    if (success) {
                        ressource.setNom(newNom);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Impossible de renommer le répertoire", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(saveButton, gbc);

        // Bouton pour annuler
        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(e -> dispose());
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(cancelButton, gbc);
    }
}
