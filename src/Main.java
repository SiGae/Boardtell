import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

/**
 * Created by sigae on 5/12/16.
 */
public class Main extends JFrame {
    private JTextField inDate;
    private JTextField much;
    private JTextField part;
    private JTextField inNAME;
    private JButton clearButton;
    private JButton istbt;
    private DefaultTableModel model;
    public JPanel moneyView;
    private JTable table1;
    private JButton DelBt;

    public Main() {
         //origVal.addFocusListener(new FocusAdapter()  {
          //  @Override
           // public void focusLost(FocusEvent e) {
            //    int money;
             //   super.focusLost(e);
              //  change_value();
               // try {
                //    money = Integer.parseInt(origVal.getText());
                //} catch(java.lang.NumberFormatException e1) {
                 //   money = 0;
                //}
            //}
        //});

        String[] header = new String[]{"누적","이름", "입실일자", "숙박일수", "추가인원"};
        String[][] data = null;

        inDate.addFocusListener(new changeListener());
        much.addFocusListener(new changeListener());
        part.addFocusListener(new changeListener());
        inNAME.addFocusListener(new changeListener());

        clearButton.addActionListener(e -> {
            JTextField arr[] = {inNAME ,inDate, much, part};
            for (int i=0;i<4;i++)
                arr[i].setText("");});

        istbt.addActionListener(e -> {
            try {
                Connection con = dbconnect.makeConnection();

                String sq = "INSERT INTO coin.work(name, date, much, part) VALUES";
                sq+="('" + getWorker() + "','" + getDate() + "','"+getMUCH()+ "','"+getPart()+"')";

                PreparedStatement stmt = con.prepareStatement(sq);
                int i =stmt.executeUpdate();
                if (i==1) {
                    System.out.println("성공!");
                    select();
                }
                else {
                    System.out.println("ㅠㅠ");
                }
            }
            catch (SQLException e3) {
                e3.printStackTrace();
            }

        });

        model = new CustomTableModel(data, header);
        table1.setModel(model);

        table1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                DelBt.addActionListener(e1->{
                    JTable target = (JTable) e.getSource();
                    delete_item(Integer.parseInt((String) model.getValueAt(target.getSelectedRow(), 0)));
                });

            }
        });
        select();

    }

    private String getWorker() {
        return inNAME.getText();
    }

    private String getDate() {
        return inDate.getText();
    }

    private String getMUCH() {
        return much.getText();
    }

    private String getPart() {
        return part.getText();
    }

    private class CustomTableModel extends DefaultTableModel {
        public CustomTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }


    private class changeListener extends FocusAdapter {
        @Override
        public void focusLost(FocusEvent e) {
            super.focusLost(e);
        }
    }

    private void select() {
        String sql = "SELECT  id,name , date, much, part FROM coin.work ORDER BY date;";
        ResultSet rs = null;
        PreparedStatement stmt = null;


        Connection con = dbconnect.makeConnection();
        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                Vector<String> cont = new Vector<>();
                cont.add(rs.getString("id"));
                cont.add(rs.getString("name"));
                cont.add(rs.getString("date"));
                cont.add(rs.getString("much"));
                cont.add(rs.getString("part"));
                model.addRow(cont);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert rs != null;
                
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void delete_item(int id) {
        String sql = "DELETE FROM coin.work WHERE id=" + id;
        int result;
        PreparedStatement stmt = null;


        Connection con = dbconnect.makeConnection();
        try {
            stmt = con.prepareStatement(sql);
            result = stmt.executeUpdate();

            System.out.println("Success: " + result);
            select();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
