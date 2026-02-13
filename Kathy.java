import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Kathy extends JFrame implements MouseMotionListener{
    private JButton yes_btn, no_btn;
    private JLabel mouseLocationStatus;
    private JLabel questionLabel;
    private JPanel wrapper;

    public Kathy () {
        super("Hello, lovee");

        Container content = getContentPane();
        content.setLayout(null);

        Border roundedBorder = BorderFactory.createLineBorder(Color.WHITE, 4, true);

        wrapper = new JPanel();
        wrapper.setBounds(-20, 10, 500, 215);
        wrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        ImageIcon icon = new ImageIcon(
            getClass().getResource("/Images-Kathy/UsTwo.jpg")
        );

        Image img = icon.getImage().getScaledInstance(
            150, 200, Image.SCALE_SMOOTH
        );
        JLabel label = new JLabel(new ImageIcon(img));
        label.setBorder(roundedBorder);
        wrapper.add(label);

        questionLabel = new JLabel("Do you love me?");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        questionLabel.setBounds(160, 225, 200, 30);

        yes_btn = new JButton("Yes");
        yes_btn.setFocusable(false);
        yes_btn.setBounds(150, 265, 80, 30);
        yes_btn.addActionListener(e -> {
            dispose();
            Yes openYes = new Yes();
            openYes.setLocationRelativeTo(null);
            openYes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
        content.add(yes_btn);

        no_btn = new JButton("No");
        no_btn.setFocusable(false);
        no_btn.setBounds(260, 265, 80, 30);
        no_btn.addActionListener(e -> {
            int x = (int) no_btn.getX() + 10;
            int y = (int) no_btn.getY() + 10;
            no_btn.setLocation(x, y);
        });
        content.add(no_btn);
        content.add(questionLabel);

        mouseLocationStatus = new JLabel("");
        addMouseMotionListener(this);
        content.add(mouseLocationStatus);
        content.add(wrapper);

        setSize(500,500);
        setVisible(true);
    }

    public void mouseMoved(MouseEvent event) {
        mouseLocationStatus.setText("Location: " + event.getX() + ", " + event.getY());
        int mouseX = event.getX();
        int mouseY = event.getY();

        int btnX = no_btn.getX();
        int btnY = no_btn.getY();

        int distance = (int) Math.hypot(mouseX - btnX, mouseY - btnY);

        if(distance < 120) {
            int newX = (int) (Math.random() * (getWidth() - 100));
            int newY = (int) (Math.random() * (getHeight() - 150));
            no_btn.setLocation(newX, newY);
        }
    }

    public void mouseDragged(MouseEvent event) {
        mouseLocationStatus.setText("Location: " + event.getX() + ", " + event.getY());
            int x = (int) no_btn.getX() + 10;
            int y = (int) no_btn.getY() + 10;
            no_btn.setLocation(x, y);
    }

    public static void main(String args[]) {
        Kathy application = new Kathy();
        application.setLocationRelativeTo(null);
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}