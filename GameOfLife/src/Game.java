import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Game implements ActionListener, KeyListener, Runnable {

    int rows;
    int cols;
    JFrame frame;
    JPanel gamePanel;
    JButton[][] btnGrid;
    boolean[][] alive;
    boolean isRunning = false;
    Thread gameThread;

    public void initGame() {

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, cols));

        btnGrid = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                btnGrid[i][j] = new JButton();
                btnGrid[i][j].setBackground(Color.black);
                btnGrid[i][j].addActionListener(this);  
                gamePanel.add(btnGrid[i][j]);
            }
        }

        alive = new boolean[rows][cols];

        frame = new JFrame("The Game Of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.addKeyListener(this);
        frame.requestFocus();
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        initGame();
    }

    public void actionPerformed(ActionEvent e) {
        JButton actionBtn = (JButton) e.getSource();
        if (actionBtn.getBackground() == Color.black) {
            actionBtn.setBackground(Color.green);
        } else {
            actionBtn.setBackground(Color.black);
        }
        frame.requestFocus();
    }

    public boolean isAlive(int i, int j) {
        if (i < 0 || j < 0 || i >= rows || j >= cols) return false;
        return btnGrid[i][j].getBackground().equals(Color.green);
    }

    public int countNeighbours(int x, int y) {
        int neighbours = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i == x && j == y) continue;
                if (isAlive(i, j)) neighbours++;
            }
        }
        return neighbours;
    }

    public void update() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int neighbours = countNeighbours(i, j);
                if (neighbours == 2) {
                    alive[i][j] = btnGrid[i][j].getBackground().equals(Color.green);
                }
                else if (neighbours == 3) {
                    alive[i][j] = true;
                }
                else {
                    alive[i][j] = false;
                }
            }
        }
    }

    public void render() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (alive[i][j]) btnGrid[i][j].setBackground(Color.green);
                else btnGrid[i][j].setBackground(Color.black);
            }
        }
    }

    public void run() {
        long fps = 4;
        long timePerFrame = 1000000000 / fps;
        long currFrame;
        long prevFrame = System.nanoTime();
        while(isRunning) {
            currFrame = System.nanoTime();
            if (currFrame - prevFrame >= timePerFrame){
                update();
                render();
                prevFrame = currFrame;
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        // Auto-generated method stub

    }

    public synchronized void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (isRunning) {
                isRunning = false;
                try {
                    gameThread.join();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                isRunning = true;
                gameThread = new Thread(this);
                gameThread.start();
            }
        }        
    }

    public void keyReleased(KeyEvent e) {
        // Auto-generated method stub

    }
}