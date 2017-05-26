import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by sigae on 5/12/16.
 */
public class MainActivity {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("보드텔 관리기");
        frame.setContentPane(new Main().moneyView);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setVisible(true);


    }
}
