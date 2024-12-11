package vue;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import modele.Notion;
import modele.Ressource;

public class ModifierNotion extends JFrame
{

    private JTextField nomField;
    private Notion notion;
    private Ressource ressource;

    public ModifierNotion(Ressource ressource, Notion notion)
    {
        this.ressource = ressource;
        this.notion = notion;
        setTitle("Modifier Notion");
        setSize(300, 150);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champ pour le nom de la notion
        nomField = new JTextField(20);
        nomField.setText(notion.getNom());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(nomField, gbc);

        // Bouton pour enregistrer les modifications
        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(e ->
        {
            String newNom = nomField.getText();
            if (newNom != null && !newNom.trim().isEmpty())
            {
                File oldDir = new File("./lib/ressources/" + ressource.getNom() + "/" + notion.getNom());
                File newDir = new File("./lib/ressources/" + ressource.getNom() + "/" + newNom);
                if (oldDir.exists() && !newDir.exists())
                {
                    boolean success = oldDir.renameTo(newDir);
                    if (success)
                    {
                        notion.setNom(newNom);
                        dispose();
                    }
                    else
                    {
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
