import com.restfb.DefaultFacebookClient;
        import com.restfb.FacebookClient;
import com.restfb.types.Post;
        import com.restfb.types.Page;
import com.restfb.*;

import java.util.List;

/**
 * Created by Victoria on 12.05.2016.
 */
public class Test {
    public static void main(String args[])  {
        String accessToken = new String("EAACEdEose0cBACdU4tWPyYreWuouZCZCs3PLfax7m9nMVkCBPwCag4ZAAhOsLiiNXIHeNloSSHmObZClwJwzn2LbzLAukZAJQxYZCH3nlGjxm4Rx5oikya3Cw9KldzdzOxpdHRcDjlQjZBiknZA7b8q6afy5ukeXjQJUwPUoczZAK5QZDZD");
        Interface app = new Interface();
        app.start(accessToken);
//
//        ProfileInformation me = new ProfileInformation();
//               me.connectWithFacebook(accessToken, "100001131682525");
//        System.out.println(me.getMyName());
//        System.out.println(me.friends());
//        user.getMyNews();
//        user.getMyBirthday();
//        for (Map.Entry<String, Integer> x : user.getMyEducation()) {
//            System.out.print(x.getKey() + ": ");
//            System.out.println(x.getValue());
//        }
//        try {
//            BufferedImage image =null;
//            URL url = new URL("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/10154507_652218914825825_1072183603_n.jpg?oh=7d9d02423fefebfa7020f5e6ae20e43e&oe=57C5102A");
//            // read the url
//            image = ImageIO.read(url);
//            // проверяеdsм на пусто
//            if (image != null){
//                // for jpg
//                ImageIO.write(image, "jpg",new File("E:\\Univer\\Java\\FacebookClient\\src\\image.jpg"));
//            }
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        FacebookClient FBClient = new DefaultFacebookClient(accessToken);
//        Connection <Post> result = FBClient.fetchConnection("user/feed",Post.class);
//        for(List<Post> page:result)  {
//            for(Post myPost : page)  {
//                //System.out.println(myPost.getMessage());
////                System.out.println("fb.com/"+myPost.getId());
////                myPost.getPicture();
//                System.out.println(myPost.getPicture());
//            }
//        }
    }

    public static void GetAdmins() {
        String accessToken = "EAAQL08FQwScBANOY6CaWlpQQxrjtnLrQORUk1eU4xc7d23JW7ckjsqS83Ulx0pPrcppbE7ipUGcMjnojs8HN0pH3ZCFmDc7vOZCfFp57wqMZAbAFzyRXilksICFGWGqGIry6bTGRbDMSTrHZBHU1KiUHxJyoZAo0ZD";
        FacebookClient FBClient = new DefaultFacebookClient(accessToken);
        Connection <Page> result = FBClient.fetchConnection("user/home",Page.class);
        for(List<Page> feedPage:result)  {
            for(Page page : feedPage)  {
                System.out.println(page.getName());
                System.out.println(page.getLikes());
                System.out.println("facebook.com/"+page.getId());
            }
        }
    }

    public static void GetHomePage()  {
        String accessToken = "EAAQL08FQwScBANOY6CaWlpQQxrjtnLrQORUk1eU4xc7d23JW7ckjsqS83Ulx0pPrcppbE7ipUGcMjnojs8HN0pH3ZCFmDc7vOZCfFp57wqMZAbAFzyRXilksICFGWGqGIry6bTGRbDMSTrHZBHU1KiUHxJyoZAo0ZD";
        FacebookClient FBClient = new DefaultFacebookClient(accessToken);
        Connection <Post> result = FBClient.fetchConnection("user/home",Post.class);
        for(List<Post> page:result)  {
            for(Post myPost : page)  {
                System.out.println(myPost.getMessage());
                System.out.println("fb.com/"+myPost.getId());
            }
        }
    }
}
