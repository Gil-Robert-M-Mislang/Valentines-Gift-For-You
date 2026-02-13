import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.util.LinkedList;

class BackgroundPanel extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1));

        int width = getWidth();
        int height = getHeight();

        for (int y = 20; y < height; y += 19) {
            g2.drawLine(0, y, width, y);
        }
    }
}

public class Yes extends JFrame {
    private String message =
        "HELLO, LOVEEE<br><br><br>" +
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;First of all, Happy Valentines day and I love you so muchh. Ito yung<br><br>" + 
        "&nbsp;&nbsp;improved version ng Valentines gift ko last year AHAHAHAHAHAHHA<br><br>"+
        "&nbsp;&nbsp;HAHAHA bukod sa tayo na, hehe tayo na may label na officially, lovee na<br><br>"+
        "&nbsp;&nbsp;tawag ko sayo. Di pa rin ako makapaniwala na girlfriend na kita. I,<br><br>" +
        "&nbsp;&nbsp;sometimes, find myself being all giddy kasi naaalala ko na girlfriend<br><br>" +
        "&nbsp;&nbsp;na kita. And yun, HAHAHAHAHAH I personally don't look forward to life<br><br>" + 
        "&nbsp;&nbsp;as I once lived like today is my last day but when you come into my life,<br><br>" +
        "&nbsp;&nbsp;I never stopped getting all excited and looking forward to my future,<br><br>" +
        "&nbsp;&nbsp;to our future, our life together. Mahal na mahal kita palagi at palagi<br><br>" +
        "&nbsp;&nbsp;kitang pipiliin araw-araw.<br><br><br>" +

        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Well, I have nothing to say but thank you so much. Thank you for<br><br>" +
        "&nbsp;&nbsp;freeing me from the depressing thoughts I had, the suicidal thoughts,<br><br>" +
        "&nbsp;&nbsp;and everything about having to die. You set me free from the worries<br><br>" +
        "&nbsp;&nbsp;and made me realize that there's someone that can and will make me<br><br>" +
        "&nbsp;&nbsp;happy, and that's you. Thank you for being there whenever I need<br><br>" +
        "&nbsp;&nbsp;someone to hold me, hug me, kiss me, and comfort me. You are my safe<br><br>" +
        "&nbsp;&nbsp;place, you are my home, and my only love.<br><br><br>"+

        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;We've faced a lot of things this year and every time we never stopped<br><br>" +
        "&nbsp;&nbsp;choosing each other. Hindi tayo naging magkaklase, lesser time to see<br><br>" +
        "&nbsp;&nbsp;each other, and lately we've had a lot of miscommunications, and tbh, as<br><br>" +
        "&nbsp;&nbsp;masochistic as it sounds, I love that we've became comfortable enough<br><br>" +
        "&nbsp;&nbsp;to let our emotions set free towards each other. Dito ko lang kasi<br><br>" +
        "&nbsp;&nbsp;nalalabas ng safely without filter yung mga gusto kong sabihin pag<br><br>" +
        "&nbsp;&nbsp;ganun nararamdaman ko, something na di ko magawa kahit kaynino and it<br><br>" +
        "&nbsp;&nbsp;made me feel so safe.<br><br><br>" +

        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I'll never stop learning what LOVE IS with you until SOMEDAY I can<br><br>" +
        "&nbsp;&nbsp;BE WITH YOU forever. This EUPHORIA that I'm feeling whenever we<br><br>" +
        "&nbsp;&nbsp;talk about our future is indescribable. Before I end this message, I<br><br>" +
        "&nbsp;&nbsp;would love to say to my only APHRODITE that I love you so much and<br><br>" +
        "&nbsp;&nbsp;I'll forever choose you.<br><br><br>" +

        "<div style='text-align:right; margin-top:20px;'>" +
        "Sincerely and only yours,<br><br>" +
        "Gil =)<br><br>" +
        "</div>";

    private static final int maxLines = 40;

    private JLabel msgLabel;
    private LinkedList<String> visibleLines = new LinkedList<>();

    public Yes() {
        super("Hehe Really??");

        Container content = getContentPane();
        content.setLayout(new FlowLayout());

        Border topMargin = BorderFactory.createEmptyBorder(15, 0, 0, 0);

        BackgroundPanel wrapper = new BackgroundPanel();
        wrapper.setPreferredSize(new Dimension(500, 800));
        wrapper.setBorder(topMargin);
        wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));

        msgLabel = new JLabel();
        msgLabel.setFont(new Font("Ink Free", Font.BOLD, 15));

        wrapper.add(msgLabel);
        content.add(wrapper);

        typingEffect(msgLabel, message, 50);

        Timer lastTextTimer = new Timer(50 * message.length(), e -> {
            dispose();
            SongThatDefineUs application = new SongThatDefineUs();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
        lastTextTimer.setRepeats(false);
        lastTextTimer.start();

        setSize(550, 835);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void typingEffect(JLabel label, String text, int delay) {
        Timer timer = new Timer(delay, null);
        StringBuilder currentLine = new StringBuilder(); 
        StringBuilder currentHTML = new StringBuilder("<html>");
        int[] index = {0}; 

        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (index[0] < text.length()) {
                    if (text.startsWith("<br>", index[0])) {
                        visibleLines.add(currentLine.toString() + "<br>");
                        currentLine.setLength(0); 
                        index[0] += 4;
                    }
                    else if (text.charAt(index[0]) == '<') {
                        int close = text.indexOf('>', index[0]);
                        if (close != -1) {
                            String tag = text.substring(index[0], close + 1);
                            currentLine.append(tag); 
                            index[0] = close + 1;
                        } else {
                            currentLine.append(text.charAt(index[0]));
                            index[0]++;
                        }
                    } else {
                        currentLine.append(text.charAt(index[0]));
                        index[0]++;
                    }

                    while (visibleLines.size() > maxLines) {
                        visibleLines.removeFirst();
                    }

                    currentHTML.setLength(0);
                    currentHTML.append("<html>");
                    for (String line : visibleLines) {
                        currentHTML.append(line);
                    }
                    currentHTML.append(currentLine); 
                    currentHTML.append("</html>");

                    label.setText(currentHTML.toString());
                } else {
                    if (currentLine.length() > 0) {
                        visibleLines.add(currentLine.toString());
                        currentLine.setLength(0);
                    }
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }
}