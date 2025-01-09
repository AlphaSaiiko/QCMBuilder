package vue;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
	 * +---------------+
	 * | CONSTRUCTEURS |
	 * +---------------+
	 */
	
	public PanelSaisie()
	{
		this(true);
	}
	
	public PanelSaisie(boolean pieceJointe)
	{
		listePiecesJointes = new ArrayList<>();
		
		this.setLayout(new BorderLayout());
		
		texte = new JTextPane();
		texte.setStyledDocument(new DefaultStyledDocument()); // Utilise StyledDocument		
		StyledDocument document = texte.getStyledDocument();

		// Toolbar
		JToolBar barreOutils = new JToolBar();
		barreOutils.setFloatable(false);
		
		JButton btnGras = new JButton("Gras");
		btnGras.addActionListener(e -> appliquerStyle(document, StyleConstants.Bold));
		
		JButton btnItalique = new JButton("Italique");
		btnItalique.addActionListener(e -> appliquerStyle(document, StyleConstants.Italic));
		
		JButton btnSouligne = new JButton("Souligné");
		btnSouligne.addActionListener(e -> appliquerStyle(document, StyleConstants.Underline));
		

		// Choix des couleurs dans la toolbar
		String[] couleurs        = { "Noir"     , "Rouge"  , "Vert"     , "Bleu"    , "Jaune"      };
		Color [] valeursCouleurs = { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW };

		JComboBox<String> listeCouleurs = new JComboBox<>(couleurs);
		listeCouleurs.setMaximumSize(new Dimension(100, 30));

		listeCouleurs.addActionListener(e ->
		{
			int indexCouleur = listeCouleurs.getSelectedIndex();
			if (indexCouleur < 0) return;

			Color couleur = valeursCouleurs[indexCouleur];

			int debut = texte.getSelectionStart();
			int fin   = texte.getSelectionEnd  ();

			if (debut == fin)
			{
				SimpleAttributeSet attrs = new SimpleAttributeSet();

				StyleConstants.setForeground         (attrs, couleur);
				texte         .setCharacterAttributes(attrs, false  );
			}
			else
			{
				StyledDocument     doc   = texte.getStyledDocument();
				SimpleAttributeSet attrs = new SimpleAttributeSet ();

				StyleConstants.setForeground         (attrs, couleur                          );
				doc           .setCharacterAttributes(debut, fin - debut, attrs, false);
			}

			texte.repaint();
		});


		barreOutils.add(btnGras      );
		barreOutils.add(btnItalique  );
		barreOutils.add(btnSouligne  );
		barreOutils.add(listeCouleurs);
		
		if (pieceJointe)
		{
			JButton btnPieceJointe = new JButton("Ajouter une pièce jointe");

			btnPieceJointe.addActionListener(e -> ajouterPieceJointe());
			barreOutils   .add              (btnPieceJointe           );
		}
		
		
		this.add(barreOutils, BorderLayout.NORTH);
		

		// Ajout de la zone de texte dans un JScrollPane
		JScrollPane scrollPaneTexte = new JScrollPane(texte);

		scrollPaneTexte.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPaneTexte.setVerticalScrollBarPolicy  (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED  );
		

		// La zone de texte n'empêche pas de scroll dans la frame principale
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

		this.add(scrollPaneTexte, BorderLayout.CENTER);
		

		// JPanel pour afficher les pièces jointes
		if (pieceJointe)
		{
			panelPiecesJointes = new JPanel();
			panelPiecesJointes.setLayout(new FlowLayout(FlowLayout.LEFT));

			scrollPanePiecesJointes = new JScrollPane(panelPiecesJointes);
			scrollPanePiecesJointes.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
			scrollPanePiecesJointes.setVerticalScrollBarPolicy  (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPanePiecesJointes.setPreferredSize            (new Dimension(0, 65)               );
		}
		

		// Glissé déposé
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
				return nomFichier.endsWith(".png")  ||
				       nomFichier.endsWith(".jpg")  || 
					   nomFichier.endsWith(".jpeg") ||
					   nomFichier.endsWith(".gif")    ;
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
		StringBuilder  contenu  = new StringBuilder      ();

		try
		{
			int     length        = document.getLength();
			boolean wasBold       = false               ;
			boolean wasItalic     = false               ;
			boolean wasUnderline  = false               ;
			Color   previousColor = null                ;

			for (int i = 0; i < length; )
			{
				Element      element = document      .getCharacterElement(i)    ;
				AttributeSet attrs   = element       .getAttributes      ()     ;
				Icon         image   = StyleConstants.getIcon            (attrs);

				if (image != null && image instanceof ImageIcon)
				{
					String emplacementImage = ((ImageIcon) image).getDescription();

					contenu.append("<img src=\"").append(emplacementImage).append("\">");
					i++;
				}
				else
				{
					int     start        = element       .getStartOffset()                  ;
					int     end          = element       .getEndOffset  ()                  ;
					String  text         = document      .getText       (start, end - start);
					boolean isBold       = StyleConstants.isBold        (attrs)             ;
					boolean isItalic     = StyleConstants.isItalic      (attrs)             ;
					boolean isUnderline  = StyleConstants.isUnderline   (attrs)             ;
					Color   currentColor = StyleConstants.getForeground (attrs)             ;
	
					if (isBold      != wasBold     ) { contenu.append(isBold      ? "<b>" : "</b>"); wasBold      = isBold     ; }
					if (isItalic    != wasItalic   ) { contenu.append(isItalic    ? "<i>" : "</i>"); wasItalic    = isItalic   ; }
					if (isUnderline != wasUnderline) { contenu.append(isUnderline ? "<u>" : "</u>"); wasUnderline = isUnderline; }

					if (!currentColor.equals(previousColor))
					{
						if (isBold               ) { contenu.append("</b>"   ); }
						if (isItalic             ) { contenu.append("</i>"   ); }
						if (isUnderline          ) { contenu.append("</u>"   ); }
						if (previousColor != null) { contenu.append("</span>"); }

						String colorHex = String.format(
							"#%02x%02x%02x" , 
							currentColor.getRed  (), 
							currentColor.getGreen(), 
							currentColor.getBlue ()
						);
						
						contenu.append("<span style=\"color: ").append(colorHex).append("\">");

						if (isBold               ) { contenu.append("<b>"   ); }
						if (isItalic             ) { contenu.append("<i>"   ); }
						if (isUnderline          ) { contenu.append("<u>"   ); }

						previousColor = currentColor;
					}

					contenu.append(text.replace("\n", "<br>"));
					i = end;
				}
			}
			if (wasUnderline         ) { contenu.append("</u>"   ); }
			if (wasItalic            ) { contenu.append("</i>"   ); }
			if (wasBold              ) { contenu.append("</b>"   ); }
			if (previousColor != null) { contenu.append("</span>"); }
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}

		if (!listePiecesJointes.isEmpty())
		{
			for (File pieceJointe : listePiecesJointes) 
			{
				String emplacementFichier = pieceJointe.getAbsolutePath();
				String nomFichier         = pieceJointe.getName        ();

				contenu.append("<br><a href=\"file:///").append(emplacementFichier).append("\" target=\"_blank\">").append(nomFichier).append("</a>");
			}
		}
		return contenu.toString();
	}
	
	


	/**
	 * +----------+
	 * | SETTEURS |
	 * +----------+
	 */

	public void setHauteur(int hauteur) { this.setPreferredSize(new Dimension(this.getPreferredSize().width, hauteur)); }
	public void setLargeur(int largeur) { this.setPreferredSize(new Dimension(largeur, this.getPreferredSize().height)); }
	
	public void setContenu(String contenu)
	{
		StyledDocument document = texte.getStyledDocument();
		document.setCharacterAttributes(0, document.getLength(), document.getStyle("regular"), true);

		try
		{
			document.remove(0, document.getLength());
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	
		SimpleAttributeSet attrs        = new SimpleAttributeSet();
		Color              currentColor = Color.BLACK             ;
	
		int i = 0;
		while (i < contenu.length())
		{
			if      (contenu.startsWith("<b>" , i)) { StyleConstants.setBold     (attrs, true ); i += 3; }
			else if (contenu.startsWith("</b>", i)) { StyleConstants.setBold     (attrs, false); i += 4; }
			else if (contenu.startsWith("<i>" , i)) { StyleConstants.setItalic   (attrs, true ); i += 3; }
			else if (contenu.startsWith("</i>", i)) { StyleConstants.setItalic   (attrs, false); i += 4; }
			else if (contenu.startsWith("<u>" , i)) { StyleConstants.setUnderline(attrs, true ); i += 3; }
			else if (contenu.startsWith("</u>", i)) { StyleConstants.setUnderline(attrs, false); i += 4; }

			else if (contenu.startsWith("<span style=\"color: ", i))
			{
				int    endColorIndex = contenu.indexOf  ("\">" , i            );
				String colorHex      = contenu.substring(i + 21, endColorIndex);
				try
				{
					if (!colorHex.startsWith("#"))
						colorHex = "#" + colorHex;

					currentColor = Color.decode(colorHex);
				}
				catch (NumberFormatException e)
				{
					System.err.println("Invalid color format: " + colorHex);
					currentColor = Color.BLACK;
				}

				StyleConstants.setForeground(attrs, currentColor);
				i = endColorIndex + 2;
			}

			else if (contenu.startsWith("</span>", i))
			{
				currentColor = Color.BLACK;
				StyleConstants.setForeground(attrs, currentColor);
				i += 7;
			}

			else if (contenu.startsWith("<img src=\"complements/", i))
			{
				int endSrcIndex = contenu.indexOf("\">", i);
				int endCommentIndex = contenu.indexOf("-->", i);
				String imagePath = contenu.substring(endSrcIndex + 6, endCommentIndex);
				File   image  = new File(imagePath)                                      ;
				System.out.println(imagePath);
				insererImage(image);
				i=endCommentIndex+3;
			}

			else if (contenu.startsWith("<br>", i))
			{
				try {
					document.insertString(document.getLength(), "\n", attrs);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				i += 4;
			}
			
			else if (contenu.startsWith("<a href=\"complements/", i))
			{
				int    endHrefIndex = contenu.indexOf  (" target=\"_blank\"><!--", i           );
				int endLinkIndex = contenu.indexOf  ("</a>", i           );
				int    endCommentIndex = contenu.indexOf  ("-->"                 , endHrefIndex);
				String filePath     = contenu.substring(i + 21                 , endCommentIndex);
				File   pieceJointe  = new File(filePath)                                      ;

				ajouterPieceJointe(pieceJointe);


				i = endLinkIndex + 4;
			}

			else
			{
				try
				{
					document.insertString(document.getLength(), String.valueOf(contenu.charAt(i)), attrs);
				} 
				catch (BadLocationException e)
				{
					e.printStackTrace();
				}
				i++;
			}
		}
	}
	
	
	

	
	
	


	


	/**
	 * +----------+
	 * | METHODES |
	 * +----------+
	 */
			
	private void appliquerStyle(StyledDocument document, Object style) {
		int debut = texte.getSelectionStart();
		int fin = texte.getSelectionEnd();
	
		if (debut == fin) {
			JOptionPane.showMessageDialog(this, "Sélectionnez du texte pour appliquer ou retirer le style.", "Info", JOptionPane.INFORMATION_MESSAGE);
			// TEST SETTEXT POUR LA MODIFICATION DE QUESTION, DEBUT DE LA ZONE A MODIFIER 

			String texteASet = "<b></b><span style=\"color: #333333\"><b>nfj</b>snd<u>ifnd</u></span><span style=\"color: #ff0000\"><u>r<b>njdn<i>gjsdh</b></i>kjsdvjbh</u></span><span style=\"color: #00ff00\"><u>kjb</u>ndsj<i>kbjsbjvb</i>sdh<u>vq</u></span><span style=\"color: #0000ff\"><u>doibi</u>oiozz<b>nndvndwk</b></span><span style=\"color: #ffff00\"><b>ds6518</b>18s91<u>9sd1vsd<img src=\"complements/ciel.jpg\"><!--lib/ressources/R1.01_Initiation au développement/Algorithmique/question3/complements/ciel.jpg--></u></span><span style=\"color: #333333\"><br><br><b>vdsqvjvbqd</b>d         fdbfdb<br>d<img src=\"complements/ciel.jpg\"><!--lib/ressources/R1.01_Initiation au développement/Algorithmique/question3/complements/ciel.jpg-->dfbdf<img src=\"complements/soleil.png\"><!--lib/ressources/R1.01_Initiation au développement/Algorithmique/question3/complements/soleil.png-->yovfbdhvb</span><br><a href=\"complements/soleil.pdf\" target=\"_blank\"><!--lib/ressources/R1.01_Initiation au développement/Algorithmique/question3/complements/soleil.pdf-->soleil.pdf</a><br><a href=\"complements/test.html\" target=\"_blank\"><!--lib/ressources/R1.01_Initiation au développement/Algorithmique/question3/complements/test.html-->test.html</a><br><a href=\"complements/soleil.pdf\" target=\"_blank\"><!--lib/ressources/R1.01_Initiation au développement/Algorithmique/question3/complements/soleil.pdf-->soleil.pdf</a><br><a href=\"complements/prompt.txt\" target=\"_blank\"><!--lib/ressources/R1.01_Initiation au développement/Algorithmique/question3/complements/prompt.txt-->prompt.txt</a>";

			setContenu(texteASet);

			// FIN DE LA ZONE A MODIFIER
			
			return;
		}
	
		// Compteurs pour le style
		int avecStyle = 0;
		int sansStyle = 0;
	
		// Première passe : compter les caractères avec et sans le style
		for (int i = debut; i < fin; ) {
			Element element = document.getCharacterElement(i);
			int elementStart = element.getStartOffset();
			int elementEnd = element.getEndOffset();
	
			int start = Math.max(debut, elementStart);
			int end = Math.min(fin, elementEnd);
	
			AttributeSet stylesActuels = element.getAttributes();
	
			boolean hasStyle = false;
			if (style == StyleConstants.Bold) {
				hasStyle = StyleConstants.isBold(stylesActuels);
			} else if (style == StyleConstants.Italic) {
				hasStyle = StyleConstants.isItalic(stylesActuels);
			} else if (style == StyleConstants.Underline) {
				hasStyle = StyleConstants.isUnderline(stylesActuels);
			}
	
			if (hasStyle) {
				avecStyle += end - start;
			} else {
				sansStyle += end - start;
			}
	
			i = elementEnd;
		}
	
		// Déterminer l'action à effectuer
		boolean appliquerStyle = sansStyle >= avecStyle;
	
		// Deuxième passe : appliquer ou retirer le style
		for (int i = debut; i < fin; ) {
			Element element = document.getCharacterElement(i);
			int elementStart = element.getStartOffset();
			int elementEnd = element.getEndOffset();
	
			int start = Math.max(debut, elementStart);
			int end = Math.min(fin, elementEnd);
	
			AttributeSet stylesActuels = element.getAttributes();
			SimpleAttributeSet nouveauxStyles = new SimpleAttributeSet(stylesActuels);
	
			if (style == StyleConstants.Bold) {
				StyleConstants.setBold(nouveauxStyles, appliquerStyle);
			} else if (style == StyleConstants.Italic) {
				StyleConstants.setItalic(nouveauxStyles, appliquerStyle);
			} else if (style == StyleConstants.Underline) {
				StyleConstants.setUnderline(nouveauxStyles, appliquerStyle);
			}
	
			document.setCharacterAttributes(start, end - start, nouveauxStyles, true);
	
			i = elementEnd;
		}
	}
			
				
	

	private void insererImage(File fichier)
	{
		try
		{
			ImageIcon iconeOriginale = new ImageIcon(fichier.getAbsolutePath());
			Image     image          = iconeOriginale.getImage()               ;

			int originalWidth  = image.getWidth (null);
			int originalHeight = image.getHeight(null);
			int maxHeight      = 100                           ;

			if (originalHeight > maxHeight)
			{
				double scale  = (double)  maxHeight     / originalHeight ;
				int newWidth  = (int)    (originalWidth * scale         );
				int newHeight = maxHeight                                ;

				image = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			}

			ImageIcon iconeRedimensionnee = new ImageIcon(image);
			iconeRedimensionnee.setDescription(fichier.getAbsolutePath());

			texte.insertIcon(iconeRedimensionnee);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, "Impossible d'insérer l'image : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

	

	private void ajouterPieceJointe()
	{
		JFileChooser fileChooser = new JFileChooser          ()    ;
		int          returnValue = fileChooser.showOpenDialog(this);
	
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			File fichier = fileChooser.getSelectedFile();
			ajouterPieceJointe(fichier);
		}
	}

	private void ajouterPieceJointe(File fichier)
	{
		this.listePiecesJointes.add(fichier);
	
		JPanel panelPieceJointe = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelPieceJointe.setBorder(new LineBorder(Color.BLACK, 1));

		JLabel nomPieceJointe = new JLabel(fichier.getName());

		ImageIcon iconeSupprimer = new ImageIcon(
			new ImageIcon("./lib/icones/delete.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)
		);

		JButton btnSupprimer = new JButton(iconeSupprimer);
		btnSupprimer.setPreferredSize    (new Dimension(25, 25));
		btnSupprimer.setBorderPainted    (false                             );
		btnSupprimer.setContentAreaFilled(false                             );
		btnSupprimer.setFocusPainted     (false                             );
		btnSupprimer.setOpaque           (false                             );

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
		panelPieceJointe.add(btnSupprimer  );

		panelPiecesJointes.add       (panelPieceJointe);
		panelPiecesJointes.revalidate()                ;
		panelPiecesJointes.repaint   ()                ;

		if (!this.isAncestorOf(scrollPanePiecesJointes))
			this.add(scrollPanePiecesJointes, BorderLayout.SOUTH);

		this.revalidate();
		this.repaint   ();
	}
}