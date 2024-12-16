package vue;

import java.awt.*;
import java.io.File;
import javax.swing.*;

import modele.Notion;
import modele.Ressource;

public class ModifierNotion extends JFrame
{
	/**
	 * +-------------+
	 * |  ATTRIBUTS  |
	 * +-------------+
	 */

	private JTextField texteNom ;
	private Notion     notion   ;
	private Ressource  ressource;




	/**
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */

	public ModifierNotion(Ressource ressource, Notion notion)
	{
		this.ressource = ressource;
		this.notion = notion;


		//Panel principal
		JPanel panelPrincipal = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;


		// Champ pour le nom de la notion
		texteNom = new JTextField(20);
		texteNom.setText(notion.getNom());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panelPrincipal.add(texteNom, gbc);


		// Bouton pour enregistrer les modifications
		JButton btnEnregistrer = new JButton("Enregistrer");

		btnEnregistrer.addActionListener(e ->
		{
			String nouveauNom = texteNom.getText();
			if (nouveauNom != null && !nouveauNom.trim().isEmpty())
			{
				File ancienChemin = new File("./lib/ressources/" + ressource.getNom() + "/" + notion.getNom());
				File nouveauChemin = new File("./lib/ressources/" + ressource.getNom() + "/" + nouveauNom);

				if (ancienChemin.exists() && !nouveauChemin.exists())
				{
					boolean succes = ancienChemin.renameTo(nouveauChemin);
					
					if (succes)
					{
						notion.setNom(nouveauNom);
						this.dispose();
					}
					else
						JOptionPane.showMessageDialog(this, "Impossible de renommer le répertoire", "Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		panelPrincipal.add(btnEnregistrer, gbc);

		
		// Bouton pour annuler
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(e -> dispose());
		gbc.gridx = 1;
		gbc.gridy = 1;
		panelPrincipal.add(btnAnnuler, gbc);


		// Ajout du panel principal à la frame et configuration de cette dernière
		this.add(panelPrincipal);
		this.setTitle("Modifier une notion");
		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
