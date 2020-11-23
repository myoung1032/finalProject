package xyz.chengzi.aeroplanechess.view;

import xyz.chengzi.aeroplanechess.controller.GameController;
import xyz.chengzi.aeroplanechess.listener.GameStateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Random;

public class GameFrame extends JFrame implements GameStateListener {
    private static final String[] PLAYER_NAMES = {"Yellow", "Blue", "Green", "Red"};

    private final JLabel statusLabel = new JLabel();
    public Point[] pieces = new Point[52];//棋盘上的普通点
    public Point[] endpieces = new Point[24];//最后一列点
    public Point[] airport = new Point[16];//飞机场的点
    public boolean waitingselect = false;

    public GameFrame(GameController controller) {


        controller.registerListener(this);
        setTitle("Super Aeroplane Chess");
        setSize(772, 825);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        statusLabel.setLocation(0, 700);
        statusLabel.setFont(statusLabel.getFont().deriveFont(18.0f));
        statusLabel.setSize(450, 22);
        add(statusLabel);


        DiceSelectorComponent diceSelectorComponent = new DiceSelectorComponent();
        diceSelectorComponent.setLocation(396, 700);
        add(diceSelectorComponent);

        // roll 按钮，覆写变成两个骰子
        JButton button = new JButton("roll");
        Random r = new Random();
        button.addActionListener((e) -> {
            if (diceSelectorComponent.isRandomDice()) {
                int dice = controller.rollDice();

                int dice2 = 1 + r.nextInt(6);
                if (dice != -1) {
                    //提示可以走的步数
                    int[] num = new int[4];

                    num[0] = dice * dice2;
                    if (num[0] > 12) {
                        num[0] = 12;
                    }
                    num[1] = dice - dice2;
                    if (num[1] < 0) {
                        num[1] = -num[2];
                    }
                    num[2] = 0;
                    if (dice >= dice2 && (dice / (dice2 + 0.0)) % 1 == 0) {
                        num[2] = dice / dice2;
                    } else if (dice <= dice2 && (dice2 / (dice + 0.0)) % 1 == 0) {
                        num[2] = dice2 / dice;
                    }
                    num[3] = dice + dice2;
                    String s = "";
                    for (int i = 0; i < 3; i++) {
                        if (num[i] != 0) {
                            boolean alreadyDisplayed = false;
                            for (int j = 0; j < i; j++) {
                                if (num[i] == num[j]) {
                                    alreadyDisplayed = true;
                                    break;
                                }
                            }
                            if (!alreadyDisplayed) {
                                s = s + num[i] + " ";
                            }
                        }

                    }
                    s = s + "or " + num[3] + " ";

                    statusLabel.setText(String.format("[%s] Rolled %d and %d,could go " + s + "steps",
                            PLAYER_NAMES[controller.getCurrentPlayer()], dice, dice2));
                    waitingselect = true;
                } else {
                    JOptionPane.showMessageDialog(this, "You have already rolled the dice");
                }
            } else {
                JOptionPane.showMessageDialog(this, "You selected " + diceSelectorComponent.getSelectedDice());
            }
        });
        button.setLocation(668, 700);
        button.setFont(button.getFont().deriveFont(18.0f));
        button.setSize(90, 30);
        add(button);


        int tx = 119;
        int ty = 133;
        for (int i = 0; i < airport.length; i++) {
            if (i == 4)
                tx += 435;
            if (i == 8)
                ty += 430;
            if (i == 12)
                tx -= 435;
            airport[i] = new Point(tx + (i % 2) * 54, ty + ((i / 2) % 2) * 54);
            // airport[i];
        }


        //前面的airport数组应该已经把各点的位置都存好了，这时只需要new对应的图片作为JLabel对象，棋子就也好了
        JLabel[] jarray = new JLabel[airport.length];
        for (int i = 0; i < airport.length; i++) {
            //jarray[i] = new JLabel(new ImageIcon("C:\\Users\\jxqyw\\IdeaProjects\\CS102A-AeroplaneChess\\src\\xyz\\chengzi\\aeroplanechess\\view\\棋子.jpg"));//你最好换成其他图片。。

            jarray[i] = new JLabel(new ImageIcon("src\\xyz\\chengzi\\aeroplanechess\\view\\棋子.jpg"));//你最好换成其他图片。。
            jarray[i].setSize(40, 40);
            jarray[i].setLocation(airport[i]);
            jarray[i].setVisible(true);
            int finalI = i;
            jarray[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (waitingselect) {
                        jarray[finalI].setVisible(false);//这里只是把它弄的看不见了，你应该改成让它走到下一格去
                        waitingselect = false;
                        onPlayerStartRound(controller.nextPlayer());

                    }
                }
                //只有当投过骰子以后点击才有用(当前只针对 auto模式，未考虑manual模式)
            });
            add(jarray[i]);
        }

        //直接整一个棋盘图片作JLabel对象，当作背景图
        //ImageIcon board_icon = new ImageIcon("C:\\Users\\jxqyw\\IdeaProjects\\CS102A-AeroplaneChess\\src\\xyz\\chengzi\\aeroplanechess\\view\\飞行棋棋盘.jpg");//最好换张图！

        ImageIcon board_icon = new ImageIcon("src\\xyz\\chengzi\\aeroplanechess\\view\\飞行棋棋盘.jpg");//最好换张图！
        JLabel board = new JLabel(board_icon);
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX() + "," + e.getY());//任何一个JLabel都可以用这个方法，只需要改掉这一句，点击即可发生你想要发生的事。这句是为了点击时能够显示x，y坐标，方便你设置其他Point的位置
            }
        });

        board.setSize(772, 800);
        add(board);


    }


    @Override
    public void onPlayerStartRound(int player) {
        statusLabel.setText(String.format("[%s] Please roll the dice", PLAYER_NAMES[player]));
    }

    @Override
    public void onPlayerEndRound(int player) {

    }
}
