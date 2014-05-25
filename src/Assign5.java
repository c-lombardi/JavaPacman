import javax.imageio.ImageIO;
import javax.management.timer.TimerNotification;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Assign5 extends JFrame implements Runnable
{

    private boolean running;
    private ShapePanel drawPanel;
    private JButton endGame, pause, resume, exit, newGame;
    private JLabel score, yourScore, level;
    private JTextField levelChosen;
    private Vector<Ghost> Ghosts;
    private Line2D.Double[] Lines;
    private ArrayList<Ellipse2D> Food;
    private BufferedImage ghostImage;
    private BufferedImage pacManClosedDown;
    private BufferedImage pacManClosedLeft;
    private BufferedImage pacManClosedRight;
    private BufferedImage pacManClosedUp;
    private BufferedImage pacManOpenDown;
    private BufferedImage pacManOpenLeft;
    private BufferedImage pacManOpenRight;
    private BufferedImage pacManOpenUp;
    private BufferedImage currentPacmanImage;
    private int pacmanDirection;
    private boolean focus;

    Pacman pacman;
    Rectangle2D Left;
    Rectangle2D Top;

    Rectangle2D Right;
    Rectangle2D Bottom;

    public Assign5(){

        super("PacMan");

        //Ghosts
        try {
            ghostImage = ImageIO.read(new File("ms.ghost.png"));
            pacManClosedDown = ImageIO.read(new File("ms.PacManClosedDown.png"));
            pacManClosedLeft = ImageIO.read(new File("ms.PacManClosedLeft.png"));
            pacManClosedRight = ImageIO.read(new File("ms.PacManClosedRight.png"));
            pacManClosedUp = ImageIO.read(new File("ms.PacManClosedUp.png"));
            pacManOpenDown = ImageIO.read(new File("ms.PacManOpenDown.png"));
            pacManOpenLeft = ImageIO.read(new File("ms.PacManOpenLeft.png"));
            pacManOpenRight = ImageIO.read(new File("ms.PacManOpenRight.png"));
            pacManOpenUp = ImageIO.read(new File("ms.PacManOpenUp.png"));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "ERROR");
        }

        //Lines
        Lines = new Line2D.Double[146];
        // Top and Bottom Sides
        for (int i = 0; i <15; i++ ) {
            Lines[i] = new Line2D.Double(i * 50, 0, (i + 1)*50, 0);
            Lines[15 + i] = new Line2D.Double(i * 50, 500, (i + 1)*50, 500);
        }

        // Left and Right Sides
        for (int j = 0; j <10; j++ ) {
            Lines[30 + j] = new Line2D.Double(0, j * 50, 0, (j + 1)*50);
            Lines[40 + j] = new Line2D.Double(750, j * 50, 750, (j + 1)*50);
        }

        Lines[50] = new Line2D.Double(50, 50, 100, 50);
        Lines[51] = new Line2D.Double(150, 50, 200, 50);
        Lines[52] = new Line2D.Double(200, 50, 250, 50);
        Lines[53] = new Line2D.Double(350, 50, 400, 50);
        Lines[54] = new Line2D.Double(450, 50, 500, 50);
        Lines[55] = new Line2D.Double(550, 50, 600, 50);
        Lines[56] = new Line2D.Double(600, 50, 650, 50);
        Lines[57] = new Line2D.Double(650, 50, 700, 50);
        Lines[58] = new Line2D.Double(100, 100, 150, 100);
        Lines[59] = new Line2D.Double(200, 100, 250, 100);
        Lines[60] = new Line2D.Double(500, 100, 550, 100);
        Lines[61] = new Line2D.Double(600, 100, 650, 100);
        Lines[62] = new Line2D.Double(650, 100, 700, 100);
        Lines[63] = new Line2D.Double(250, 150, 300, 150);
        Lines[64] = new Line2D.Double(400, 150, 450, 150);
        Lines[65] = new Line2D.Double(500, 150, 550, 150);
        Lines[66] = new Line2D.Double(600, 150, 650, 150);
        Lines[67] = new Line2D.Double(200, 200, 250, 200);
        Lines[68] = new Line2D.Double(350, 200, 400, 200);
        Lines[69] = new Line2D.Double(400, 200, 450, 200);
        Lines[70] = new Line2D.Double(450, 200, 500, 200);
        Lines[71] = new Line2D.Double(500, 200, 550, 200);
        Lines[72] = new Line2D.Double(100, 250, 150, 250);
        Lines[73] = new Line2D.Double(400, 250, 450, 250);
        Lines[74] = new Line2D.Double(450, 250, 500, 250);
        Lines[75] = new Line2D.Double(500, 250, 550, 250);
        Lines[76] = new Line2D.Double(150, 300, 200, 300);
        Lines[77] = new Line2D.Double(300, 300, 350, 300);
        Lines[78] = new Line2D.Double(450, 300, 500, 300);
        Lines[79] = new Line2D.Double(550, 300, 600, 300);
        Lines[80] = new Line2D.Double(500, 350, 550, 350);
        Lines[81] = new Line2D.Double(550, 350, 600, 350);
        Lines[82] = new Line2D.Double(600, 350, 650, 350);
        Lines[83] = new Line2D.Double(100, 400, 150, 400);
        Lines[84] = new Line2D.Double(450, 400, 500, 400);
        Lines[85] = new Line2D.Double(600, 400, 650, 400);
        Lines[86] = new Line2D.Double(50, 450, 100, 450);
        Lines[87] = new Line2D.Double(100, 450, 150, 450);
        Lines[88] = new Line2D.Double(350, 450, 400, 450);
        Lines[89] = new Line2D.Double(500, 450, 550, 450);
        Lines[90] = new Line2D.Double(600, 450, 650, 450);
        Lines[91] = new Line2D.Double(700, 450, 750, 450);


        Lines[92] = new Line2D.Double(300, 0, 300, 50);
        Lines[93] = new Line2D.Double(100, 50, 100, 100);
        Lines[94] = new Line2D.Double(250, 50, 250, 100);
        Lines[95] = new Line2D.Double(300, 50, 300, 100);
        Lines[96] = new Line2D.Double(450, 50, 450, 100);
        Lines[97] = new Line2D.Double(50, 100, 50, 150);
        Lines[98] = new Line2D.Double(100, 100, 100, 150);
        Lines[99] = new Line2D.Double(200, 100, 200, 150);
        Lines[100] = new Line2D.Double(300, 100, 300, 150);
        Lines[101] = new Line2D.Double(350, 100, 350, 150);
        Lines[102] = new Line2D.Double(400, 100, 400, 150);
        Lines[103] = new Line2D.Double(700, 100, 700, 150);
        Lines[104] = new Line2D.Double(50, 150, 50, 200);
        Lines[105] = new Line2D.Double(100, 150, 100, 200);
        Lines[106] = new Line2D.Double(150, 150, 150, 200);
        Lines[107] = new Line2D.Double(200, 150, 200, 200);
        Lines[108] = new Line2D.Double(600, 150, 600, 200);
        Lines[109] = new Line2D.Double(150, 200, 150, 250);
        Lines[110] = new Line2D.Double(200, 200, 200, 250);
        Lines[111] = new Line2D.Double(300, 200, 300, 250);
        Lines[112] = new Line2D.Double(350, 200, 350, 250);
        Lines[113] = new Line2D.Double(600, 200, 600, 250);
        Lines[114] = new Line2D.Double(650, 200, 650, 250);
        Lines[115] = new Line2D.Double(700, 200, 700, 250);
        Lines[116] = new Line2D.Double(50, 250, 50, 300);
        Lines[117] = new Line2D.Double(100, 250, 100, 300);
        Lines[118] = new Line2D.Double(250, 250, 250, 300);
        Lines[119] = new Line2D.Double(350, 250, 350, 300);
        Lines[120] = new Line2D.Double(400, 250, 400, 300);
        Lines[121] = new Line2D.Double(650, 250, 650, 300);
        Lines[122] = new Line2D.Double(700, 250, 700, 300);
        Lines[123] = new Line2D.Double(100, 300, 100, 350);
        Lines[124] = new Line2D.Double(200, 300, 200, 350);
        Lines[125] = new Line2D.Double(250, 300, 250, 350);
        Lines[126] = new Line2D.Double(350, 300, 350, 350);
        Lines[127] = new Line2D.Double(400, 300, 400, 350);
        Lines[128] = new Line2D.Double(450, 300, 450, 350);
        Lines[129] = new Line2D.Double(50, 350, 50, 400);
        Lines[130] = new Line2D.Double(150, 350, 150, 400);
        Lines[131] = new Line2D.Double(200, 350, 200, 400);
        Lines[132] = new Line2D.Double(250, 350, 250, 400);
        Lines[133] = new Line2D.Double(300, 350, 300, 400);
        Lines[134] = new Line2D.Double(450, 350, 450, 400);
        Lines[135] = new Line2D.Double(550, 350, 550, 400);
        Lines[136] = new Line2D.Double(700, 350, 700, 400);
        Lines[137] = new Line2D.Double(50, 400, 50, 450);
        Lines[138] = new Line2D.Double(200, 400, 200, 450);
        Lines[139] = new Line2D.Double(250, 400, 250, 450);
        Lines[140] = new Line2D.Double(300, 400, 300, 450);
        Lines[141] = new Line2D.Double(350, 400, 350, 450);
        Lines[142] = new Line2D.Double(400, 400, 400, 450);
        Lines[143] = new Line2D.Double(450, 400, 450, 450);
        Lines[144] = new Line2D.Double(550, 400, 550, 450);
        Lines[145] = new Line2D.Double(350, 450, 350, 500);
        //end Lines


        drawPanel = new ShapePanel(770, 500);
        newGame = new JButton("New Game");
        endGame = new JButton("End Game");
        pause = new JButton("Pause");
        resume = new JButton("Resume");
        exit = new JButton("Exit");
        score = new JLabel("Score");
        level = new JLabel("Level: (1, 2, or 3)");
        yourScore = new JLabel("0");
        levelChosen = new JTextField("1");
        Ghosts = new Vector<Ghost>();
        running = true;
        endGame.setEnabled(false);
        pause.setEnabled(false);
        resume.setEnabled(false);
        pacmanDirection = 0;
        currentPacmanImage = pacManClosedRight;
        focus = true;

        setFocusable(true);

        addFocusListener(
                new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        focus = true;
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        focus = false;
                    }
                }
        );
        addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (focus) {
                            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                                pacmanDirection = 1;
                            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                                pacmanDirection = 2;
                            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                pacmanDirection = 3;
                            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                                pacmanDirection = 4;
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );
        newGame.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        yourScore.setText("0");
                        running = true;
                        pacman = new Pacman();
                        if (levelChosen.getText().length() > 0) {
                            for (int i = 0; i < (Integer.parseInt(levelChosen.getText())) * 2; i++) {
                                Ghosts.add(new Ghost());
                            }
                        } else {
                            for (int i = 0; i < 2; i++) {
                                Ghosts.add(new Ghost());
                            }
                        }
                        levelChosen.setEnabled(false);
                        requestFocusInWindow();
                        for (int i = 0; i < Ghosts.size(); i++)
                            (new Thread(Ghosts.elementAt(i))).start();
                        new Thread(pacman).start();
                        newGame.setEnabled(false);
                        pause.setEnabled(true);
                        resume.setEnabled(false);
                        endGame.setEnabled(true);
                        //Food
                        Food = new ArrayList<Ellipse2D>();
                        for (int i = 1; i < 16 ; i++)
                            for (int j = 1; j < 11; j++) {
                                Food.add(new Ellipse2D.Double((50 * i)-25, (50 * j)-25, 10, 10));
                            }
                    }
                }
        );
        endGame.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        levelChosen.setEnabled(true);
                        running = false;
                        for (int i = 0; i < Ghosts.size(); i++)
                            ((Ghost) Ghosts.elementAt(i)).stop();
                        pacman.stop();
                        Ghosts.removeAllElements();
                        endGame.setEnabled(false);
                        newGame.setEnabled(true);
                        pause.setEnabled(false);
                        resume.setEnabled(false);
                    }
                }
        );
        pause.addActionListener(
                new ActionListener()
                {

                    public void actionPerformed(ActionEvent e) {
                        running = false;
                        for (int i = 0; i < Ghosts.size(); i++)
                            (Ghosts.elementAt(i)).stop();
                        pacman.stop();
                        resume.setEnabled(true);
                        pause.setEnabled(false);
                    }
                }
        );
        resume.addActionListener(
                new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                running = true;
                for (int i = 0; i < Ghosts.size(); i++)
                    (new Thread(Ghosts.elementAt(i))).start();
                new Thread(pacman).start();
                resume.setEnabled(false);
                pause.setEnabled(true);
            }
        });
        exit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        running = false;
                        System.exit(0);
                    }
                }
        );
        levelChosen.setDocument(new PlainDocument() {
            public void insertString(int offset, String str, AttributeSet a) {
                if (!Character.isISOControl(str.charAt(0))) {
                    if (!this.isInteger(str)) {
                        return;
                    }
                    if (this.isInteger(str)) {
                        if ((levelChosen.getText().length() > 0) || (Integer.parseInt(str) > 3)) {
                            return;
                        }
                    }
                }
                try {
                    super.insertString(offset, str, a);

                } catch (Exception e) {
                }
            }

            private boolean isInteger(String str) {
                for (int i = 0; i < str.length(); i++){
                    if (!Character.isDigit(str.charAt(i))) {
                        return false;
                    }
                }
                  return true;
            }
        });
        super.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 500;
        c.ipadx = 750;
        c.weightx = 0.0;
        c.gridwidth = 6;
        add(drawPanel, c);

        c.ipady = 0;
        c.ipadx = 0;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        add(newGame, c);

        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        add(endGame, c);

        c.gridx = 3;
        c.gridy = 1;
        add(exit, c);

        c.gridx = 4;
        c.gridy = 1;
        add(pause, c);

        c.gridx = 5;
        c.gridy = 1;
        add(resume, c);

        c.gridx = 1;
        c.gridy = 2;
        add(level, c);

        c.gridx = 2;
        c.gridy = 2;
        add(levelChosen, c);

        c.gridx = 3;
        c.gridy = 2;
        add(score, c);

        c.gridx = 4;
        c.gridy = 2;
        add(yourScore, c);

        Thread refresher = new Thread(this);
        refresher.start();

        setResizable(false);
        setSize(770, 600);
        setVisible(true);
    }

    public void run()
    {
        while (true)
        {
            drawPanel.repaint();
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {}
        }
    }

    public static void main(String [] args){
        Assign5 application = new Assign5();

        application.addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    { System.exit(0); }
                }
        );
    }
    public boolean isHorizontal(Line2D line) {
        if (line.getY1() == line.getY2())
            return true;
        return false;
    }

    private class Pacman implements Runnable
    {
        private int size, speed;
        private int deltax, deltay, startx, starty;
        int distance;
        int score;
        public Pacman()
        {
            size = 40;
            speed = 10;
            startx = 55;
            starty = 55;
            distance = 10;
        }
        public synchronized boolean checkIntersection()
        {
            for(Line2D l:Lines) {
                Point center = new Point(startx+20+deltax, starty+20+deltay);
                for (Ghost g: Ghosts)
                {
                    if (center.distance(g.startx+20, g.starty+20) <= 20)
                    {
                        JOptionPane.showMessageDialog(null, "You lost");
                        endGame.doClick();
                        break;
                    }
                }
                for (Ellipse2D e: Food)
                {
                    if (center.distance(e.getCenterX(), e.getCenterY())<=20)
                    {
                        Food.remove(e);
                        yourScore.setText(String.valueOf(Integer.parseInt(yourScore.getText())+50));
                        break;
                    }
                }
                if (l.ptSegDist(center) <= 20)
                {
                    return true;
                }
            }
            return false;
        }
        public void goLeft()
        {
            this.deltax = -1;
            this.deltay = 0;
            if ((distance % 50) == 0)
                currentPacmanImage = pacManClosedLeft;
            if ((distance % 50 ==0) && (distance %60 == 0))
                currentPacmanImage = pacManOpenLeft;
        }
        public void goRight()
        {
            this.deltax = 1;
            this.deltay = 0;
            if ((distance % 50) == 0)
                currentPacmanImage = pacManClosedRight;
            if ((distance %50 ==0) && (distance %60 == 0))
                currentPacmanImage = pacManOpenRight;
        }
        public void goUp()
        {
            this.deltax = 0;
            this.deltay = -1;
            if ((distance % 50) == 0)
                currentPacmanImage = pacManClosedUp;
            if ((distance %50 ==0) && (distance %60 == 0))
                currentPacmanImage = pacManOpenUp;
        }
        public void goDown()
        {
            this.deltax = 0;
            this.deltay = 1;
            if ((distance %50) == 0)
                currentPacmanImage = pacManClosedDown;
            if ((distance %50 ==0) && (distance %60 == 0))
                currentPacmanImage = pacManOpenDown;
        }
        public void goNowhere()
        {
            this.deltax = 0;
            this.deltay = 0;
        }
        public void stop()
        {
            running = false;
        }

        public synchronized void run()
        {
            while (running && score < 7500)
            {
                score = (Integer.parseInt(yourScore.getText()));
                distance += speed;
                switch (pacmanDirection){
                    case 1:
                        goLeft();
                        break;
                    case 2:
                        goUp();
                        break;
                    case 3:
                        goRight();
                        break;
                    case 4:
                        goDown();
                        break;
                    default:
                        goNowhere();
                        break;
                }
                try {
                    Thread.sleep(speed);
                }
                catch (InterruptedException e) {}
                int oldx = startx;    // compare to thisBall.getX() from Ex20
                int oldy = starty;
                //changeDirection();
                if (!checkIntersection()) {
                    startx = startx + deltax;
                    starty = starty + deltay;
                }
                else
                {
                    pacmanDirection = 0;
                }
            }
            if (score == 7500)
            {
                endGame.doClick();
                JOptionPane.showMessageDialog(null, "Congratulations, you won!");
            }
        }
        public void draw(Graphics2D g2d)
        {
            g2d.drawImage(currentPacmanImage, this.startx, this.starty, size, size, null);
        }
    }

    private class Ghost implements Runnable
        {
        private BufferedImage ghostimg;
        private int size, speed;
        private int deltax, deltay, startx, starty;
        private int currentDirection;

        public Ghost()
        {
            ghostimg = ghostImage;
            size = 40;
            speed = 10;
            startx = 650;
            starty = 50;
            currentDirection = 2;
        }
        public boolean checkIntersection()
        {
            for(Line2D l:Lines) {
                Point topRight = new Point (startx +40, starty);
                Point topLeft = new Point (startx, starty);
                Point bottomLeft = new Point (startx, starty+40);
                Point bottomRight = new Point (startx+40, starty+40);
                Point center = new Point(startx + 20, starty+20);
                switch (currentDirection){
                    case 1:

                        if (!isHorizontal(l))
                        {
                            if (l.ptSegDist(topLeft) <= 5 && l.ptSegDist(bottomLeft) <= 5) {
                                return true;
                            }
                        }
                        break;
                    case 2:
                        if (isHorizontal(l))
                        {
                            if (l.ptSegDist(topLeft) <= 5 && l.ptSegDist(topRight) <= 5) {
                                return true;
                            }
                        }
                        break;
                    case 3:
                        if (!isHorizontal(l))
                        {
                            if (l.ptSegDist(topRight) <= 5 && l.ptSegDist(bottomRight) <= 5) {
                                return true;
                            }
                        }
                        break;
                    case 4:
                        if (isHorizontal(l))
                        {
                            if (l.ptSegDist(bottomLeft) <= 5 && l.ptSegDist(bottomRight) <= 5) {
                                return true;
                            }
                        }
                        break;
                    default:
                        break;
                }

            }
            return false;
        }
        public int checkDistance(double dist) {
            int returnVal = 0;
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            for (Line2D l : Lines) {
                Point topLeft = new Point(startx, starty);
                Left = new Rectangle2D.Double(topLeft.getX() - dist, topLeft.getY(), dist, 40);
                if (Left.intersectsLine(l)) {
                    flag1 = false;
                    break;
                }
            }
            for (Line2D l : Lines) {
                Point topRight = new Point(startx + 40, starty);
                Right = new Rectangle2D.Double(topRight.getX(), topRight.getY(), dist, 40);
                if (Right.intersectsLine(l)) {
                    flag3 = false;
                    break;
                }
            }
            for (Line2D l : Lines) {
                Point topLeft = new Point(startx, starty);
                Top = new Rectangle2D.Double(topLeft.getX(), topLeft.getY() - dist, 40, dist);
                if (Top.intersectsLine(l)) {
                    flag2 = false;
                    break;
                }
            }
            for (Line2D l : Lines) {
                Point bottomLeft = new Point(startx, starty + 40);
                Bottom = new Rectangle2D.Double(bottomLeft.getX(), bottomLeft.getY(), 40, dist);
                if (Bottom.intersectsLine(l)) {
                    flag4 = false;
                    break;
                }
            }
            ArrayList<Integer> returnVals = new ArrayList<Integer>();
            if (flag1)
            {
                returnVals.add(1);
            }
            if (flag2)
            {
                returnVals.add(2);
            }
            if (flag3){
                returnVals.add(3);
            }
            if (flag4)
            {
                returnVals.add(4);
            }
            if (returnVals.size() > 0) {
                return returnVals.get((int) (Math.floor(Math.random() * returnVals.size())));
            }
            return 0;
        }
        public void goLeft()
        {
            this.deltax = -1;
            this.deltay = 0;
        }
        public void goRight()
        {
            this.deltax = 1;
            this.deltay = 0;
        }
        public void goUp()
        {
            this.deltax = 0;
            this.deltay = -1;
        }
        public void goDown()
        {
            this.deltax = 0;
            this.deltay = 1;
        }
        public void changeDirection()
        {
            switch((int)(Math.random() * ((4) + 1))){
                case 1:
                    if (!checkIntersection())
                        goLeft();
                    currentDirection = 1;
                    break;
                case 2:
                    if (!checkIntersection())
                        goUp();
                    currentDirection = 2;
                    break;
                case 3:
                    if (!checkIntersection())
                        goRight();
                    currentDirection = 3;
                    break;
                case 4:
                    if (!checkIntersection())
                        goDown();
                    currentDirection = 4;
                    break;
                default:
                    break;
            }
        }
        public void stop()
        {
            running = false;
        }

        public void run()
        {   // reset ballStarted to true since now run() can be
            while (running)   // executed more than once for the same NewBall2
            {
                switch (currentDirection){
                    case 1:
                        goLeft();
                        break;
                    case 2:
                        goUp();
                        break;
                    case 3:
                        goRight();
                        break;
                    case 4:
                        goDown();
                        break;
                    default:
                        break;
                }
                try {
                    Thread.sleep(speed);
                }
                catch (InterruptedException e) {}
                int oldx = startx;    // compare to thisBall.getX() from Ex20
                int oldy = starty;
                //changeDirection();
                if (!checkIntersection()) {
                    startx = startx + deltax;
                    starty = starty + deltay;
                }
                else
                {
                    startx = oldx;
                    starty = oldy;
                    changeDirection();
                }
                int altDirection = checkDistance(40);
                if (altDirection > 0){
                    if ((int)(Math.random() * ((300) + 1)) == 1)
                        currentDirection = altDirection;
                }
            }
        }

        public void draw(Graphics2D g2d)
        {
            g2d.drawImage(this.ghostimg, this.startx, this.starty, size, size, null);
        }
    }

    private class ShapePanel extends JPanel
    {
        private int prefwid, prefht;

        public ShapePanel (int pwid, int pht)
        {
            prefwid = pwid;
            prefht = pht;
        }

        public Dimension getPreferredSize()
        {
            return new Dimension(prefwid, prefht);
        }

        public void paintComponent (Graphics g) {

            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            for (Ghost Ghost : Ghosts) {
                Ghost.draw(g2d);
            }
            for (Line2D l : Lines) {
                g2d.drawLine((int) l.getX1(), (int) l.getY1(), (int) l.getX2(), (int) l.getY2());
            }
            if (Food != null) {
                for (Ellipse2D f : Food) {
                    g2d.setPaint(Color.red);
                    g2d.fill(f);
                    g2d.draw(f);
                }
            }
            if (pacman != null)
                pacman.draw(g2d);
        }
    }
}