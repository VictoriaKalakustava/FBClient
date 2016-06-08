import javax.swing.*;

/**
 * Created by Victoria on 08.06.2016.
 */
public class News {
    JLabel image;
    String name;

    public String getName() {
        return name;
    }

    public JLabel getImage() {
        return image;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    String message;
    String data;

    public News(JLabel _image, String _name, String _message, String _data) {
        image = _image;
        name = _name;
        message = _message;
        data = _data;
    }
}
