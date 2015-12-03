package r47717.life;

import java.awt.*;
import javax.swing.*;

import r47717.life.controls.LifeControls;
import r47717.life.field.LifeField;


public class LifeMain implements Runnable {

	public static final int WIDTH = 750;
	public static final int HEIGHT = 600;
	
	private JFrame frame;
	private JPanel panel;
	private LifeField field;
	private LifeControls controls;
	
	@Override
	public void run() {
		
		frame = new JFrame("Life Simulation");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(300, 50);
		frame.setResizable(false);
        
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        field = new LifeField();
        controls = new LifeControls();
        panel.add(field);
        //panel.add(controls);

        
        frame.add(panel);
        frame.setForeground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	}

	public static void main(String[] args) {
		LifeMain app = new LifeMain();
        SwingUtilities.invokeLater(app);
	}
}

