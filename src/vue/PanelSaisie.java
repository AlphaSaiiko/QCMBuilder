package vue;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import modele.Question;

public class PanelSaisie extends JPanel
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private JTextPane   texte                  ;
	private JPanel      panelPiecesJointes     ;
	private JScrollPane scrollPanePiecesJointes;
	private List<File>  listePiecesJointes     ;
	



	/**
	 * +--------------+
	 * | CONSTRUCTEUR |
	 * +--------------+
	 */
	
	public PanelSaisie()
	{
		this(true);
	}
	
	public PanelSaisie(boolean pieceJointe)
	{
		listePiecesJointes = new ArrayList<>();
		

		// Définir un layout qui occupe tout l'espace disponible
		this.setLayout(new BorderLayout());
		

		// Zone de texte avec StyledDocument pour permettre la mise en forme
		texte = new JTextPane();
		StyledDocument document = texte.getStyledDocument();
		

		// Barre d'outils pour les options de mise en forme (et l'ajout de pièces jointes)
		JToolBar barreOutils = new JToolBar();
		barreOutils.setFloatable(false);
		

		// Bouton "Gras"
		JButton btnGras = new JButton("Gras");
		btnGras.addActionListener(e -> appliquerStyle(document, StyleConstants.Bold));
		

		// Bouton "Italique"
		JButton btnItalique = new JButton("Italique");
		btnItalique.addActionListener(e -> appliquerStyle(document, StyleConstants.Italic));
		

		// Bouton "Souligné"
		JButton btnSouligne = new JButton("Souligné");
		btnSouligne.addActionListener(e -> appliquerStyle(document, StyleConstants.Underline));
		

		// Ajout des boutons à la barre d'outils
		barreOutils.add(btnGras);
		barreOutils.add(btnItalique);
		barreOutils.add(btnSouligne);
		
		if (pieceJointe)
		{
			JButton btnPieceJointe = new JButton("Ajouter une pièce jointe");
			btnPieceJointe.addActionListener(e -> ajouterPieceJointe());
			barreOutils.add(btnPieceJointe);
		}
		

		// Ajouter la barre d'outils au panel
		this.add(barreOutils, BorderLayout.NORTH);
		

		// Ajouter la zone de texte dans un JScrollPane
		JScrollPane scrollPaneTexte = new JScrollPane(texte);
		scrollPaneTexte.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTexte.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		

		// On fait en sorte que la zone de texte n'empêche pas de scroll dans la frame principale
		texte.addMouseWheelListener(e ->
		{
			Component parent = this.getParent();
			while (parent != null)
			{
				if (parent instanceof JScrollPane)
				{
					((JScrollPane) parent).dispatchEvent(e);
					return;
				}

				parent = parent.getParent();
			}
		});

		
		// Ajouter la zone de texte au centre du panel
		this.add(scrollPaneTexte, BorderLayout.CENTER);
		

		// JPanel pour afficher les pièces jointes
		if (pieceJointe)
		{
			panelPiecesJointes = new JPanel();
			panelPiecesJointes.setLayout(new FlowLayout(FlowLayout.LEFT));
			scrollPanePiecesJointes = new JScrollPane(panelPiecesJointes);
			scrollPanePiecesJointes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPanePiecesJointes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPanePiecesJointes.setPreferredSize(new Dimension(0, 65));
		}
		

		// Configurer le drop target pour le JTextPane pour accepter les images
		texte.setDropTarget(new DropTarget()
		{
			public synchronized void drop(DropTargetDropEvent evt)
			{
				try
				{
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					Transferable transferable = evt.getTransferable();
					if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
					{
						List<File> fichiers = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

						for (File fichier : fichiers)
						{
							if (estImage(fichier))
								insererImage(fichier);
						}

						evt.dropComplete(true);
					}
					else
						evt.rejectDrop();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					evt.rejectDrop();
				}
			}
			
			private boolean estImage(File fichier)
			{
				String nomFichier = fichier.getName().toLowerCase();
				return nomFichier.endsWith(".png") || nomFichier.endsWith(".jpg") || nomFichier.endsWith(".jpeg") || nomFichier.endsWith(".gif");
			}
		});
	}
	



	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */

	public String getContenu()
	{
		StyledDocument document = texte.getStyledDocument();
		String contenu = "";

		for (int i = 0; i < document.getLength(); i++)
		{
			Element      element = document.getCharacterElement(i)    ;
			AttributeSet attrs   = element.getAttributes       ()     ;
			Icon         image   = StyleConstants.getIcon      (attrs);

			if (image != null && image instanceof ImageIcon)
			{
				String emplacementImage = ((ImageIcon) image).getDescription();

				contenu += "<img src=\"" + emplacementImage + "\">";
			}
			else
			{
				try
				{
					String caractere = document.getText(i, 1);

					if (caractere.equals("\n"))
						contenu += "<br>";
					else
						contenu += caractere;
				}
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
			}
		}

		if (!this.listePiecesJointes.isEmpty())
		{
			for (File pieceJointe : listePiecesJointes)
			{
				String emplacementFichier = pieceJointe.getAbsolutePath();
				String nomFichier         = pieceJointe.getName();
				
				contenu += "<br><a href=\"file:///" + emplacementFichier + "\" target=\"_blank\">" + nomFichier + "</a>";
			}
		}

		return contenu;
	}




	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setHauteur(int hauteur) { this.setPreferredSize(new Dimension(this.getPreferredSize().width, hauteur)); }
	public void setLargeur(int largeur) { this.setPreferredSize(new Dimension(largeur, this.getPreferredSize().height)); }
	


	
	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */
	
	/**
	 * Applique ou retire un style (gras, italique, souligné) sur une sélection de texte.
	 *
	 * @param document Le document stylisé (`StyledDocument`) sur lequel appliquer les modifications.
	 * @param style    L'objet de style à appliquer ou retirer (exemple : `StyleConstants.Bold`, 
	 *                 `StyleConstants.Italic`, `StyleConstants.Underline`).
	 */
	private void appliquerStyle(StyledDocument document, Object style)
	{
		int debut = texte.getSelectionStart();
		int fin   = texte.getSelectionEnd  ();
	
		if (debut == fin)
		{
			JOptionPane.showMessageDialog(this, "Sélectionnez du texte pour appliquer ou retirer le style.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	

		// Récupérer les styles actuels
		AttributeSet stylesActuels = document.getCharacterElement(debut).getAttributes();
		SimpleAttributeSet nouveauxStyles = new SimpleAttributeSet(stylesActuels);
	

		// Basculer l'état du style
		if (style == StyleConstants.Bold)
			StyleConstants.setBold(nouveauxStyles, !StyleConstants.isBold(stylesActuels));
		else
		{
			if (style == StyleConstants.Italic)
				StyleConstants.setItalic(nouveauxStyles, !StyleConstants.isItalic(stylesActuels));
			else
			{
				if (style == StyleConstants.Underline)
					StyleConstants.setUnderline(nouveauxStyles, !StyleConstants.isUnderline(stylesActuels));
			}
		}

	
		// Appliquer les nouveaux styles
		document.setCharacterAttributes(debut, fin - debut, nouveauxStyles, false);
	}
	


	/**
	 * Insère une image dans le texte à l'emplacement actuel du curseur.
	 *
	 * @param file Le fichier image à insérer.
	 */
	private void insererImage(File fichier) {
		try {
			ImageIcon icone = new ImageIcon(fichier.getAbsolutePath());
			texte.insertIcon(icone); // Utiliser insertIcon pour insérer l'image correctement
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Impossible d'insérer l'image : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	


	/**
	 * Permet à l'utilisateur de sélectionner un fichier à ajouter en tant que pièce jointe.
	 * Les pièces jointes sont visualisées dans un panneau sous forme de liste avec une option
	 * pour les supprimer individuellement.
	 */
	private void ajouterPieceJointe() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(this);
	
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File fichier = fileChooser.getSelectedFile();
			this.listePiecesJointes.add(fichier);
			System.out.println(this.listePiecesJointes);
	
			JPanel panelPieceJointe = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panelPieceJointe.setBorder(new LineBorder(Color.BLACK, 1));
			JLabel nomPieceJointe = new JLabel(fichier.getName());
	
			ImageIcon iconeSupprimer = new ImageIcon(
				new ImageIcon("./lib/icones/delete.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)
			);

			JButton btnSupprimer = new JButton(iconeSupprimer);
			btnSupprimer.setPreferredSize(new Dimension(25, 25));
			btnSupprimer.setBorderPainted(false);
			btnSupprimer.setContentAreaFilled(false);
			btnSupprimer.setFocusPainted(false);
			btnSupprimer.setOpaque(false);
	
			btnSupprimer.addActionListener(e ->
			{
				this.listePiecesJointes.remove(fichier);
				panelPiecesJointes.remove(panelPieceJointe);
	
				if (this.listePiecesJointes.isEmpty())
					this.remove(scrollPanePiecesJointes);
		
				this.revalidate();
				this.repaint();
			});
	
			panelPieceJointe.add(nomPieceJointe);
			panelPieceJointe.add(btnSupprimer);
			panelPiecesJointes.add(panelPieceJointe);

			panelPiecesJointes.revalidate();
			panelPiecesJointes.repaint();

			if (!this.isAncestorOf(scrollPanePiecesJointes))
				this.add(scrollPanePiecesJointes, BorderLayout.SOUTH);

			this.revalidate();
			this.repaint();
		}
	}
}