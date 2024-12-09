package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil extends JFrame implements ActionListener
{
	/*
	 *  +------------+
	 *  | PARAMETRES |
	 *  +------------+
	 */
	private JButton btn1, btn2, btn3, btn4;

	private JLabel lbl1;
	
	private JPanel panel1, panel2;


	/*
	 *  +--------------+
	 *  | CONSTRUCTEUR |
	 *  +--------------+
	 */
	public Accueil()
	{
		setTitle("Accueil");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(2, 1));

		panel1 = new JPanel();
		panel2 = new JPanel();

		lbl1 = new JLabel("Bienvenue dans notre application");
		lbl1.setFont(new Font("Arial", Font.BOLD, 20));
		panel1.add(lbl1);

		btn1 = new JButton("Ajouter un produit");
		btn2 = new JButton("Consulter les produits");
		btn3 = new JButton("Modifier un produit");
		btn4 = new JButton("Supprimer un produit");

		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);

		panel2.add(btn1);
		panel2.add(btn2);
		panel2.add(btn3);
		panel2.add(btn4);

		add(panel1);
		add(panel2);

		setVisible(true);
	}


	public void actionPerformed ( ActionEvent e )
	{
		
	}
	
	public static void main(String[] args) {
		new Accueil();
	}
}