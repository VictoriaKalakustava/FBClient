import com.restfb.Connection;
import com.restfb.types.Post;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;

/**
 * Created by Victoria on 07.06.2016.
 */
public class NewsInterface extends ProfileInformation{
    String accessToken;
    ArrayList<News> newses = new ArrayList();
    int pageNumber;

    public int getPageNumber() {
        return pageNumber;
    }

    public void readNews(String _accessToken) {
        accessToken = _accessToken;
        connectWithFacebook(accessToken, "me");
        pageNumber = 0;
        Connection<Post> result = fbClient.fetchConnection(userID + "/feed",Post.class);
        for(List<Post> page:result)  {
            for(Post myPost : page)  {
                newses.add(new News(getImageLabel(myPost), myPost.getName(), myPost.getMessage(),
                        myPost.getCreatedTime().toString()));
            }
        }
    }


    public void setPageNumber(int pageNumber) {
        if(pageNumber > 0 && pageNumber <= (newses.size()/10)+1) {
            this.pageNumber = pageNumber;
        }
    }

    public JPanel oneNews(int index) {
        JPanel one = new JPanel();
        one.setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel(newses.get(index).getName());
        JTextArea messageArea = new JTextArea();
        setTextFieldFormat(messageArea);

        if(nameLabel.getText() != null) {
            nameLabel.setText("           " + newses.get(index).getName());
            setLabelFormat(nameLabel);
            one.add(nameLabel, BorderLayout.NORTH);
        }
        if(newses.get(index).getMessage() != null) {
            messageArea.setText(changeMessage("      " + newses.get(index).getMessage()));
            messageArea.setMaximumSize(new Dimension(500, 300));
            one.add(messageArea, BorderLayout.CENTER);
        }
        JLabel dataLabel = new JLabel("      " + newses.get(index).getData());

        JPanel imagesPane = new JPanel();
        imagesPane.setLayout(new BorderLayout());
        if(newses.get(index).getImage()!= null) {
            imagesPane.add(newses.get(index).getImage(), BorderLayout.CENTER);
            imagesPane.add(new Label("      "), BorderLayout.WEST);
            one.add(imagesPane, BorderLayout.SOUTH);
        }


        mainPanel.add(one, BorderLayout.NORTH);
        mainPanel.add(dataLabel,BorderLayout.CENTER);

        return mainPanel;
    }

    public void setLabelFormat(JLabel label) {
        Font font = new Font("Century Gothic", Font.BOLD, 12);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setForeground(Color.decode("#263B52"));
        label.setFont(font);

    }

    public void setTextFieldFormat(JTextArea textArea) {
        Font font = new Font("Century Gothic", Font.PLAIN, 12);
        textArea.setForeground(Color.decode("#263B52"));
        textArea.setBackground(Color.decode("#F0F0F0"));
        textArea.setFont(font);
        textArea.setEditable(false);
    }

    public JLabel getImageLabel(Post post) {
        JLabel image = new JLabel();
        try {
            if(post.getPicture() != null) {
                BufferedImage bufferedImage = null;
                URL url = new URL(post.getPicture());
                System.out.println(post.getPicture());
                bufferedImage = ImageIO.read(url);
                image.setIcon(new ImageIcon(bufferedImage));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return image;
        }
    }

    private String changeMessage(String message){
        if(message == null) return " ";
        String mess = "";
        if(message.indexOf("\n") > 0){
            for(int i = 0; i < message.length(); i++) {
                if(message.charAt(i) == '\n') {
                    mess = mess + message.charAt(i) + "      ";
                }
                else mess = mess + message.charAt(i);
            }
            return mess;
        }
        else{
            for(int i = 0; i < message.length(); i++) {
                if(i % 70 == 0) {
                    while(message.charAt(i) != ' ')
                    {
                        if(i >= message.length()) {
                            break;
                        }
                        mess = mess + message.charAt(i);
                        i++;
                    }
                    if(i < message.length()) {
                        mess = mess + message.charAt(i) + "\n      ";
                    }
                }
                else mess = mess + message.charAt(i);
            }
        }
        return  mess;
    }

}
