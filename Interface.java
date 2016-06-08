import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;


import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Created by Victoria on 12.05.2016.
 */
public class Interface {
    ProfileInformation myProfile;
    String accessToken;
    NewsInterface news;
    JFrame simpleFrame;
    JTabbedPane tabbedPane;

    public void start(String _accessToken) {
        accessToken = _accessToken;
        myProfile = new ProfileInformation();
        myProfile.connectWithFacebook(accessToken, "me");
        news = new NewsInterface();
        news.readNews(accessToken);
        simpleFrame = new JFrame();
        SimpleWindow();
    }

    public void getAccessToken() {
        JFrame firstFrame = new JFrame();
        myProfile = new ProfileInformation();
        firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        firstFrame.setSize(700, 500);
        JLabel message = new JLabel("<html>This application want to get access for your friends,<br/> " +
                "messages, relationships and posts in Facebook. <br/>If you're agree, copy" +
                " an access token from your browser<br/> in the text field and take \"Ok.\" ");
        setLabelFormat(message);
        JPanel mainPanel = new JPanel();
        JTextField accessToken = new JTextField();
        accessToken.setPreferredSize(new Dimension(150, 20));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(message, BorderLayout.NORTH);
        mainPanel.add(accessToken, BorderLayout.CENTER);
        JButton readToken = new JButton("Ok");
        readToken.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myProfile.connectWithFacebook(accessToken.getText(), "user");
                firstFrame.setVisible(false);
                firstFrame.dispose();
            }
        });
        mainPanel.add(readToken, BorderLayout.SOUTH);
        firstFrame.add(mainPanel, BorderLayout.CENTER);
        firstFrame.pack();
        firstFrame.setVisible(true);
    }


    public void SimpleWindow(){
        simpleFrame.setSize(700,500);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(simpleFrame);
        simpleFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.WRAP_TAB_LAYOUT);
        tabbedPane.setBackground(Color.decode("#436F9E"));
        JLabel icon = new JLabel();
        setProfilePage(tabbedPane);
        setFriendsPage(tabbedPane);
        tabbedPane.addTab("", new ImageIcon("E:\\Univer\\Java\\FacebookClient\\src\\" + 3 + ".png"),
                getMyNews(), "My Hronic");
        for (int i = 2; i <= 3; i++) {
            JPanel panel = new JPanel();
            panel.add(icon);
            panel.add(new JButton("Кнопка № " + i));
            tabbedPane.addTab("", new ImageIcon("E:\\Univer\\Java\\FacebookClient\\src\\" + i + ".png"),panel, "Do somth");
        }
        simpleFrame.add(tabbedPane);
        simpleFrame.setVisible(true);
    }

    public JScrollPane getMyNews() {
        JScrollPane scrollPane;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        for (int i = 0; i < 10; i++) {
            panel.add(news.oneNews(i+10*news.getPageNumber()));
            panel.add(new JSeparator());
            panel.add(new JSeparator());
            panel.add(new JSeparator());
        }
        panel.add(getButtonPanel());
        scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        return scrollPane;
    }

    public JPanel getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton back = new JButton("Back");
        JButton next = new JButton("Next");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                news.setPageNumber(news.getPageNumber()-1);
                tabbedPane.setComponentAt(2, getMyNews());
                simpleFrame.repaint();
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                news.setPageNumber(news.getPageNumber()+1);
                tabbedPane.setComponentAt(2, getMyNews());
                simpleFrame.repaint();
            }
        });
        buttonPanel.add(back, BorderLayout.WEST);
        buttonPanel.add(next, BorderLayout.EAST);
        return buttonPanel;
    }

    public void setFriendsPage(JTabbedPane tabbedPane)  {

        ProfileInformation friendsProfile = new ProfileInformation();
        friendsProfile.connectWithFacebook(accessToken, myProfile.friends().get(0));

        JPanel panel = new JPanel();
        JPanel up = new JPanel();
        JPanel down = new JPanel();
        JLabel image = new JLabel();
        JLabel name = new JLabel();
        JLabel info = new JLabel();
        name.setText(friendsProfile.getMyName());
        setInfoLabel(info, friendsProfile);
        setLabelFont(name);
        try {
            BufferedImage bufferedImage = null;
            URL url = new URL(friendsProfile.getPictureURL());
            bufferedImage = ImageIO.read(url);
            image.setIcon(new ImageIcon(bufferedImage));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        up.setLayout(new BoxLayout(up, BoxLayout.LINE_AXIS));
        up.add(image);
        up.add(name);
        down.add(info);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add( up);
        panel.add( down);

        tabbedPane.addTab("", new ImageIcon("E:\\Univer\\Java\\FacebookClient\\src\\friends.png"),panel, "Friends that's use this app.");
    }

    public void setProfilePage(JTabbedPane tabbedPane) {
        JPanel panel = new JPanel();
        JPanel up = new JPanel();
        JPanel down = new JPanel();
        JLabel image = new JLabel();
        JLabel name = new JLabel();
        JLabel info = new JLabel();
        name.setText(myProfile.getMyName());
        setInfoLabel(info, myProfile);
        setLabelFont(name);
        try {
            BufferedImage bufferedImage = null;
            URL url = new URL(myProfile.getPictureURL());
            bufferedImage = ImageIO.read(url);
            image.setIcon(new ImageIcon(bufferedImage));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        up.setLayout(new BoxLayout(up, BoxLayout.LINE_AXIS));
        up.add(image);
        up.add(name);
        down.add(info);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add( up);
        panel.add( down);
        tabbedPane.addTab("", new ImageIcon("E:\\Univer\\Java\\FacebookClient\\src\\" + 2 + ".png"),
                panel, "My Profile");
    }

    public void setInfoLabel(JLabel label , ProfileInformation page) {
        Font font = new Font("Century Gothic", Font.PLAIN, 16);
        label.setFont(font);
        label.setForeground(Color.decode("#263B52"));
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setText("<html><b>Email: </b>" + page.getMyEmail()+
                "<br/><br/><b>Birthday: </b>" + page.getMyBirthday()+
                "<br/><br/><b>Gender: </b>" + page.getGender() +
                "<br/><br/><b>Favourite quotes: </b>" + page.getQuotes() +
                "<br/>" + page.getMyEducation() + "</html>");

    }

    public void setLabelFont(JLabel name) {
        Font font = new Font("Century Gothic", Font.BOLD, 25);
        name.setVerticalAlignment(JLabel.CENTER);
        name.setHorizontalAlignment(JLabel.CENTER);
        name.setFont(font);
        name.setForeground(Color.decode("#436F9E"));
    }

    public void setLabelFormat(JLabel label) {
        Font font = new Font(null, Font.BOLD, 12);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.RIGHT);
        label.setForeground(Color.GRAY);
        label.setFont(font);

    }
}
