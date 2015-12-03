package r47717.life.field;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class LifeField extends JPanel implements Runnable {

	private Thread thread;
	private boolean stopped = false;
	
	private final int BORDER = 15;
	
	private final int WIDTH = 700;
	private final int COLS = 70;
	
	private final int HEIGHT = 500;
	private final int ROWS = 50;

	private final int CELLS = 700;
	
	private final int DELAY = 500;
	
	
	private char[][] field = new char[ROWS][COLS];
	
	public LifeField() {
		super();
		fillRandom();
		thread = new Thread(this);
		start();
	}
	
	private void fillRandom() {
		Random rand = new Random();
		
		for(int i = 0; i < ROWS; i++)
			for(int j = 0; j < COLS; j++)
				field[i][j] = 0;
		
		for(int k = 0; k < CELLS; k++) {
			int i = rand.nextInt(ROWS);
			int j = rand.nextInt(COLS);
			field[i][j] = 1;
		}
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	
	private void nextDay() {
		char[][] buf = new char[ROWS][COLS];
		
		// create new generation in buf
		//
		for(int i = 0; i < ROWS; i++)
			for(int j = 0; j < COLS; j++) {
				int nb = 0;
	
				// all 8 neighbors are counted from left-top clockwise
				//
				if(getNeighbor(i, j, 1) == 1) nb++;
				if(getNeighbor(i, j, 2) == 1) nb++;
				if(getNeighbor(i, j, 3) == 1) nb++;
				if(getNeighbor(i, j, 4) == 1) nb++;
				if(getNeighbor(i, j, 5) == 1) nb++;
				if(getNeighbor(i, j, 6) == 1) nb++;
				if(getNeighbor(i, j, 7) == 1) nb++;
				if(getNeighbor(i, j, 8) == 1) nb++;
				
				buf[i][j] = field[i][j];
				
				if(field[i][j] == 0 && nb == 3)
					buf[i][j] = 1; // birth
				
				if(field[i][j] == 1 && (nb < 2 || nb > 3))
					buf[i][j] = 0; // death
			}
		
		// copy buf to the field
		//
		for(int i = 0; i < ROWS; i++)
			for(int j = 0; j < COLS; j++)
				field[i][j] = buf[i][j];
	}
	
	private int getNeighbor(int i, int j, int n) {
		int in = i;
		int jn = j;
		
		switch(n) {
		case 1:
			in--;
			jn--;
			break;
		case 2:
			in--;
			break;
		case 3:
			in--;
			jn++;
			break;
		case 4:
			jn++;
			break;
		case 5:
			in++;
			jn++;
			break;
		case 6:
			in++;
			break;
		case 7:
			in++;
			jn--;
			break;
		case 8:
			jn--;
			break;
		}
		
		if(in < 0) in = ROWS - 1;
		if(jn < 0) jn = COLS - 1;
		if(in >= ROWS) in = 0;
		if(jn >= COLS) jn = 0;
		
		return field[in][jn];
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////

	private void paintGrid(Graphics g) {
		g.setColor(Color.GRAY);
		
		int x1, x2;
		int y1, y2;
		int dx = WIDTH/COLS;
		int dy = HEIGHT/ROWS;
		
		// draw horizontal lines
		x1 = y1 = y2 = BORDER;
		x2 = WIDTH + BORDER;
		for(int i = 0; i <= ROWS; i++) {
			g.drawLine(x1, y1, x2, y2);
			y1 = y2 = y1 + dy;
		}
		
		// draw vertical lines
		x1 = x2 = y1 = BORDER;
		y2 = HEIGHT + BORDER;
		for(int i = 0; i <= COLS; i++) {
			g.drawLine(x1, y1, x2, y2);
			x1 = x2 = x1 + dx;
		}
	}
	
	private void paintCell(Graphics g, int i, int j) {
		int dx = WIDTH/COLS;
		int dy = HEIGHT/ROWS;
		int x = j * dx + BORDER;
		int y = i * dy + BORDER;
		
		if(field[i][j] == 1)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.WHITE);
		g.fillRect(x + 1, y + 1, dx - 2, dy - 2);
	}
	
	private void paintCells(Graphics g) {
		for(int i = 0; i < ROWS; i++)
			for(int j = 0; j < COLS; j++)
				paintCell(g, i, j);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintGrid(g);
		paintCells(g);
	}

	
	///////////////////////////////////////////////////////////////////////////////////

	@Override
	public void run() {
		while(!stopped) {
			try {
				thread.sleep(DELAY);
			} catch (InterruptedException e) {
				stopped = true;
			}
			nextDay();
			repaint();
		}
	}
	
	public void start() {
		stopped = false;
		thread.start();
	}
	
	public void stop() {
		stopped = true;
	}
	
}
