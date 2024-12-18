package vue;

import javax.swing.*;
import java.awt.*;

public class PanelSaisie extends JPanel
{
	private JTextArea texte;

	public PanelSaisie()
	{
		// Définir un layout qui occupe tout l'espace disponible
		this.setLayout(new BorderLayout());


		// Zone de texte
		texte = new JTextArea();
		texte.setLineWrap(false);
		texte.setWrapStyleWord(false);

		
		// Ajouter la zone de texte dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(texte);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


		// Ajouter le JScrollPane au centre du panel
		this.add(scrollPane, BorderLayout.CENTER);
	}

	public String getTexte()
	{
		return this.texte.getText();
	}
}
