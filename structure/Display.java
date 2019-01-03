package structure;
import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel jlabel ;
	boolean visible;

	public  Display(String titre) {
		super(titre);       // Titre de la fenêtre
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
		int width  = (int)dimension.getWidth();
		setPreferredSize(new Dimension(height, 1100));  // largeur, hauteur

		jlabel = new JLabel();
		visible = false;
		this.add(jlabel, BorderLayout.CENTER);
		this.pack();
	}

	public void setImage(Image blop) {
		if (!visible){
			visible = true;
			this.setVisible(true);
		}
		jlabel.setIcon(new ImageIcon(blop));

	}

	/** La fenêtre n'est plus visible
	 *
	 */
	public void close() {
		this.dispose();
	}

} 
