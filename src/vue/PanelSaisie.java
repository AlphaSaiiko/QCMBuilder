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

public class PanelSaisie extends JPanel
{
	/**
	 * +-----------+
	 * | ATTRIBUTS |
	 * +-----------+
	 */

	private JTextPane texte;
	private JPanel panelPiecesJointes;
	private JScrollPane scrollPanePiecesJointes;
	private List<File> listePiecesJointes;
	
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
		if (pieceJointe) // Initialisation des pièces jointes
			listePiecesJointes = new ArrayList<>();
		
		// Définir un layout qui occupe tout l'espace disponible
		this.setLayout(new BorderLayout());
		
		// Zone de texte avec StyledDocument pour permettre la mise en forme
		texte = new JTextPane();
		StyledDocument document = texte.getStyledDocument();
		
		// Barre d'outils pour les options de mise en forme
		JToolBar barreOutils = new JToolBar();
		barreOutils.setFloatable(false);
		
		// Bouton pour le gras
		JButton btnGras = new JButton("Gras");
		btnGras.addActionListener(e -> appliquerStyleTexte(document, StyleConstants.Bold));
		
		// Bouton pour l'italique
		JButton btnItalique = new JButton("Italique");
		btnItalique.addActionListener(e -> appliquerStyleTexte(document, StyleConstants.Italic));
		
		// Bouton pour le souligné
		JButton btnSouligne = new JButton("Souligné");
		btnSouligne.addActionListener(e -> appliquerStyleTexte(document, StyleConstants.Underline));
		
		// Ajouter les boutons à la barre d'outils
		barreOutils.add(btnGras);
		barreOutils.add(btnItalique);
		barreOutils.add(btnSouligne);
		
		if (pieceJointe) // Bouton pour importer des pièces jointes
		{
			JButton btnPieceJointe = new JButton("Ajouter une pièce jointe");
			btnPieceJointe.addActionListener(e -> addAttachment());
			barreOutils.add(btnPieceJointe);
		}
		
		// Ajouter la barre d'outils au panel
		this.add(barreOutils, BorderLayout.NORTH);
		
		// Ajouter la zone de texte dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(texte);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Propagate mouse wheel events to the parent component
		texte.addMouseWheelListener(e -> {
			// Find the parent scroll pane of panelQuestion in QuestionAssociation
			Component parent = this.getParent();
			while (parent != null) {
				if (parent instanceof JScrollPane) {
					((JScrollPane) parent).dispatchEvent(e);
					return;
				}
				parent = parent.getParent();
			}
		});

		// Ajouter le JScrollPane au centre du panel
		this.add(scrollPane, BorderLayout.CENTER);
		
		if (pieceJointe) // Panneau pour afficher les pièces jointes (masqué par défaut)
		{
			panelPiecesJointes = new JPanel();
			panelPiecesJointes.setLayout(new FlowLayout(FlowLayout.LEFT));
			scrollPanePiecesJointes = new JScrollPane(panelPiecesJointes);
			scrollPanePiecesJointes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPanePiecesJointes.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPanePiecesJointes.setPreferredSize(new Dimension(0, 65));
		}
		
		// Configurer le drop target pour le JTextPane pour accepter les images
		texte.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				try {
					evt.acceptDrop(DnDConstants.ACTION_COPY);
					Transferable transferable = evt.getTransferable();
					if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
						List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
						for (File file : files) {
							if (isImage(file)) {
								insererImage(file);
							}
						}
						evt.dropComplete(true);
					} else {
						evt.rejectDrop();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					evt.rejectDrop();
				}
			}
			
			private boolean isImage(File file) {
				String fileName = file.getName().toLowerCase();
				return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif");
			}
		});
	}
	
	/**
	 * +----------+
	 * | GETTEURS |
	 * +----------+
	 */
	
	public String getTexte() { return this.texte.getText(); }
	
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
	private void appliquerStyleTexte(StyledDocument document, Object style) {
		int debut = texte.getSelectionStart();
		int fin = texte.getSelectionEnd();
	
		if (debut == fin) { // Aucun texte sélectionné
			JOptionPane.showMessageDialog(this, "Sélectionnez du texte pour appliquer ou retirer le style.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	
		// Récupérer les attributs actuels
		AttributeSet currentAttributes = document.getCharacterElement(debut).getAttributes();
		SimpleAttributeSet newAttributes = new SimpleAttributeSet(currentAttributes);
	
		// Basculer l'état du style
		if (style == StyleConstants.Bold) {
			StyleConstants.setBold(newAttributes, !StyleConstants.isBold(currentAttributes));
		} else if (style == StyleConstants.Italic) {
			StyleConstants.setItalic(newAttributes, !StyleConstants.isItalic(currentAttributes));
		} else if (style == StyleConstants.Underline) {
			StyleConstants.setUnderline(newAttributes, !StyleConstants.isUnderline(currentAttributes));
		}
	
		// Appliquer les nouveaux attributs
		document.setCharacterAttributes(debut, fin - debut, newAttributes, false);
	}
	
	/**
	 * Insère une image dans le texte à l'emplacement actuel du curseur.
	*
	* @param file Le fichier image à insérer.
	*/
	private void insererImage(File file) {
		try {
			ImageIcon icon = new ImageIcon(file.getAbsolutePath());
			texte.insertIcon(icon); // Utiliser insertIcon pour insérer l'image correctement
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Impossible d'insérer l'image : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Permet à l'utilisateur de sélectionner un fichier à ajouter en tant que pièce jointe.
	 * Les pièces jointes sont visualisées dans un panneau sous forme de liste avec une option
	 * pour les supprimer individuellement.
	 */
	private void addAttachment() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(this);
	
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			listePiecesJointes.add(file);
	
			JPanel panelPieceJointe = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panelPieceJointe.setBorder(new LineBorder(Color.BLACK, 1));
			JLabel nomPieceJointe = new JLabel(file.getName());
	
			ImageIcon iconeSupprimer = new ImageIcon(
					new ImageIcon("./lib/icones/delete.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
			JButton btnSupprimer = new JButton(iconeSupprimer);
			btnSupprimer.setPreferredSize(new Dimension(25, 25));
			btnSupprimer.setBorderPainted(false);
			btnSupprimer.setContentAreaFilled(false);
			btnSupprimer.setFocusPainted(false);
			btnSupprimer.setOpaque(false);
	
			btnSupprimer.addActionListener(e -> {
				listePiecesJointes.remove(file);
				panelPiecesJointes.remove(panelPieceJointe);
	
				if (listePiecesJointes.isEmpty()) {
					this.remove(scrollPanePiecesJointes);
				}
				this.revalidate();
				this.repaint();
			});
	
			panelPieceJointe.add(nomPieceJointe);
			panelPieceJointe.add(btnSupprimer);
			panelPiecesJointes.add(panelPieceJointe);
	
			// Mise à jour de l'interface en une seule fois à la fin
			panelPiecesJointes.revalidate();
			panelPiecesJointes.repaint();
			if (!this.isAncestorOf(scrollPanePiecesJointes)) {
				this.add(scrollPanePiecesJointes, BorderLayout.SOUTH);
			}
			this.revalidate();
			this.repaint();
		}
	}
	
	
	public void setFixedHeight(int height) {
		this.setPreferredSize(new Dimension(this.getPreferredSize().width, height));
	}
}