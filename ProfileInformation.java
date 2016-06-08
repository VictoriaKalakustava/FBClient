import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Created by Victoria on 22.05.2016.
 */
public class ProfileInformation {
    FacebookClient fbClient;
    String userID;
    User user;

    public void connectWithFacebook(String accessToken, String userType) {
        fbClient = new DefaultFacebookClient(accessToken);
        userID = userType;
        user = fbClient.fetchObject(userID, User.class);
    }

    public String getMyName() {
       return user.getName();
    }

    public String getMyBirthday() {
        return user.getBirthday();
    }

    public String getGender() {
        if(user.getGender() == null) return "Unknown";
        else return user.getGender();
    }

    public List<String> friends() {
        Connection<User> myFriends = fbClient.fetchConnection(userID + "/friends", User.class);

        List<User> users = myFriends.getData();
        ArrayList<String> id = new ArrayList();
        for(Iterator iterator=users.iterator();iterator.hasNext();)
        {
            User user=(User)iterator.next();
            System.out.println(user.getId());
            id.add(user.getId());
        }
        return id;
    }

    public String getQuotes() {
        if( user.getQuotes() == null) return "Unknown";
        else return user.getQuotes();
    }

    public String getMyEmail() {
        if(user.getEmail() == null) return "Unknown";
        else return user.getEmail();
    }

    public List<User.Education> getEducationInfo() {
        List<User.Education> educations;
        educations = user.getEducation();
        return educations;
    }

    public String getHomeTown() {
        return user.getHometownName();
    }

    public String getMyEducation() {
        Vector<String> info = new Vector<>();
        List<User.Education> list = getEducationInfo();
        if(list == null) return "Unknown";
        System.out.println(list);
        for(User.Education x : list)  {
            String year = "Unknown";
            if(x.getYear()!= null) year = x.getYear().getName();
            info.add("<html><br/><b>" + x.getType() +  ": </b>" + x.getSchool().getName()+
                    "<br/><b>Date of graduation: </b>" + year + "<br/>");
        }
        String fullInfo = "";
        for(String str : info) {
            fullInfo = fullInfo + str;
        }
        return fullInfo;
    }

    public void setAbout(String info) {
        user.setQuotes(info);
    }

    public String getPictureURL() {
        Page p = fbClient.fetchObject(userID, Page.class, Parameter.with("fields", "picture"));
        return p.getPicture().getUrl();
    }

    public List<User> getMyFriends() {
        Connection<User> myFriends = fbClient.fetchConnection(
                userID + "/friends", User.class,
                Parameter.with("fields", "first_name, gender"));
        System.out.println(myFriends);
        return myFriends.getData();
    }

    public void myWall() {
        Connection <Post> result = fbClient.fetchConnection(userID + "/feed",Post.class);
        for(List<Post> page:result)  {
            for(Post myPost : page)  {
                //System.out.println(myPost.getMessage());
//                System.out.println("fb.com/"+myPost.getId());
//                myPost.getPicture();
                System.out.println(myPost.getPicture());
            }
        }
    }

    public void saveImage(String link) {
        try {
            BufferedImage image = null;
            URL url = new URL(link);
            // read the url
            image = ImageIO.read(url);
            // проверяеdsм на пусто
            if (image != null){
                // for jpg
                String homeDirectory = "E:\\Univer\\Java\\FacebookClient\\src\\";
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(homeDirectory));
                fileChooser.showSaveDialog(new Frame());
                ImageIO.write(image, "jpg", new File(fileChooser.getSelectedFile()+".jpg"));
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
