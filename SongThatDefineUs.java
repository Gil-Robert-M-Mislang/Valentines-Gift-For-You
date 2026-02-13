import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;

public class SongThatDefineUs extends JFrame {

    private JPanel[] songWrapper = new JPanel[5];
    private JLabel songLabel, lastMessageLabel;
    private JPanel wrapper;
    private int index = 0;
    private int charIndex = 0;
    private String currentText = "";
    Timer typingTimer;

    private String[] LastMessage = {
        "Hindi pa dito nagtatapos yan",
        "Marami pa tayong memories na gagawin",
        "Marami pa tayong adventures na pagsasamahan",
        "Marami pa tayong kwento na pag-uusapan",
        "Marami pa tayong pagkain na pagsasaluhan",
        "I love you so so much! and I'll be forever yours and only yours",
        "So for my last message in this gift, please take your time to watch the video in",
        "3",
        "2",
        "1",
        "I love you, Lovee! Happy Valentines!"
    };

    private String[] infoSong = {
        "<html><strong>Euphoria</strong><br><div style='font-size:12px;'>The Ridleys</div></html>",
        "<html><strong>Aphrodite</strong><br><div style='font-size:12px;'>The Ridleys</div></html>",
        "<html><strong>Love is</strong><br><div style='font-size:12px;'>The Ridleys</div></html>",
        "<html><strong>Be With You</strong><br><div style='font-size:12px;'>The Ridleys</div></html>",
        "<html><strong>Someday</strong><br><div style='font-size:12px;'>The Ridleys</div></html>"
    };

    private String[] pathSong = {
        "/Images-Kathy/Euphoria.jpg",
        "/Images-Kathy/Aphrodite.jpg",
        "/Images-Kathy/LoveIs.png",
        "/Images-Kathy/Be-With-You.jpg",
        "/Images-Kathy/Someday.jpg"
    };

    private String[] soundPath = {
        "C:\\Users\\MSI\\Desktop\\Gil-VsCode\\Gil-Files-main\\Java(vs-files)\\Music-Kathy\\Euphoria.wav",
        "C:\\Users\\MSI\\Desktop\\Gil-VsCode\\Gil-Files-main\\Java(vs-files)\\Music-Kathy\\Aphrodite.wav",
        "C:\\Users\\MSI\\Desktop\\Gil-VsCode\\Gil-Files-main\\Java(vs-files)\\Music-Kathy\\LoveIs.wav",
        "C:\\Users\\MSI\\Desktop\\Gil-VsCode\\Gil-Files-main\\Java(vs-files)\\Music-Kathy\\BeWithYou.wav",
        "C:\\Users\\MSI\\Desktop\\Gil-VsCode\\Gil-Files-main\\Java(vs-files)\\Music-Kathy\\Someday.wav"
    };

    private Border combinedBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
    );

    private Border shadow = BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 1, 4, 4, new Color(0, 0, 0, 50)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
    );

    public SongThatDefineUs() {
        super("Songs That Define Us");
        Container content = getContentPane();
        content.setLayout(null);
 
        for (int i = 0; i < songWrapper.length; i++) {
            songWrapper[i] = addImage(infoSong[i], pathSong[i]);
            content.add(songWrapper[i]);
        }
 
        lastMessageLabel = new JLabel();
        lastMessageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        lastMessageLabel.setBounds(50, 0, 400, 200);
        content.add(lastMessageLabel);

        Timer lastPanelTimer = new Timer(368000, e -> {
            index = 0;
            lastMessage();
        });
        lastPanelTimer.setRepeats(false);
        lastPanelTimer.start();

        Timer videoTimer = new Timer(405000, e -> {
            dispose();
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://drive.google.com/file/d/1W7fYczfZt9aINQB-E7WXNVHOgcutu1TB/view"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        videoTimer.setRepeats(false);
        videoTimer.start();

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startSequence();
    }

    private void lastMessage() {
        String fullMessage = LastMessage[index].replace("\n", "<br>");

        typingTimer = new Timer(70, e -> {

            if (charIndex < fullMessage.length()) {
                currentText += fullMessage.charAt(charIndex);
                lastMessageLabel.setText("<html>" + currentText + "</html>");
                charIndex++;
            } else {
                typingTimer.stop();

                Timer pauseTimer = new Timer(1000, ev -> {
                    ((Timer) ev.getSource()).stop();

                    index++;
                    if (index < LastMessage.length) {
                        currentText = "";
                        charIndex = 0;
                        lastMessage();
                    }
                });
                pauseTimer.setRepeats(false);
                pauseTimer.start();
            }
        });

        typingTimer.start();
    }

    private void startSequence() {
        playNext();
    }

    private void playNext() {
        if (index >= songWrapper.length) return;

        JPanel panel = songWrapper[index];

        slideUp(panel, 20, () -> {
            long duration = playSound(soundPath[index]);

            Timer waitForSong = new Timer((int) duration, e -> {
                slideRight(panel, 700, () -> {
                    index++;
                    playNext();
                });
            });
            waitForSong.setRepeats(false);
            waitForSong.start();
        });
    }

    public static void slideUp(JComponent panel, int endY, Runnable onFinish) {
        Timer timer = new Timer(20, null);
        timer.addActionListener(e -> {
            Point p = panel.getLocation();
            if (p.y <= endY) {
                panel.setLocation(p.x, endY);
                timer.stop();
                if (onFinish != null) onFinish.run();
            } else {
                panel.setLocation(p.x, p.y - 5);
            }
        });
        timer.start();
    }

    public static void slideRight(JComponent panel, int endX, Runnable onFinish) {
        Timer timer = new Timer(20, null);
        timer.addActionListener(e -> {
            Point p = panel.getLocation();
            if (p.x >= endX) {
                panel.setLocation(endX, p.y);
                timer.stop();
                if (onFinish != null) onFinish.run();
            } else {
                panel.setLocation(p.x + 5, p.y);
            }
        });
        timer.start();
    }

    public static long playSound(String path) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(path)));
            clip.start();
            return clip.getMicrosecondLength() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public JPanel addImage(String songInfo, String imagePath) {
        wrapper = new JPanel();
        wrapper.setBounds(17, 400, 450, 190);
        wrapper.setBorder(combinedBorder);
        wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        Image img = icon.getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH);

        JLabel label = new JLabel(new ImageIcon(img));
        label.setBorder(shadow);

        songLabel = new JLabel(songInfo);
        songLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        wrapper.add(label);
        wrapper.add(songLabel);
        return wrapper;
    }
}
