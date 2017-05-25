import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import java.util.Vector;

/**
 * Created by sigae on 5/12/16.
 */
public class Main extends JFrame {
    private JTextField Field1000;
    private JTextField Field5000;
    private JTextField Field10000;
    private JTextField errorfield;
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

        String[] header = new String[]{"이름", "입실", "퇴실", "인원"};
        String[][] data = null;

        Field1000.addFocusListener(new changeListener());
        Field5000.addFocusListener(new changeListener());
        Field10000.addFocusListener(new changeListener());
        errorfield.addFocusListener(new changeListener());

        clearButton.addActionListener(e -> {
            JTextField arr[] = { Field1000, Field5000, Field10000, errorfield};
            for (int i=0;i<7;i++)
                arr[i].setText("");});

        istbt.addActionListener(e -> {
            try {
                Connection con = dbconnect.makeConnection();
                Date dt =  new Date();

                String sq = "INSERT INTO coin.work(worker, date, diff) VALUES";
                //sq+="('" + getWorker() + "','" + dt.toString() + "','"+getDiff()+"')";

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
            change_value();
        }
    }

    private void select() {
        String sql = "SELECT id, date, worker, diff FROM coin.work;";
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
                cont.add(rs.getString("date"));
                cont.add(rs.getString("worker"));
                cont.add(rs.getString("diff"));
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
    private void change_value() {
        int error = Integer.parseInt(errorfield.getText());
        int coin[] = {0, 0};
        int bill[] = {0, 0, 0};
        JTextField attr[] = {Field1000, Field5000, Field10000};
        int money;
        for(int i = 0; i < 5; i++) {
            money = Integer.parseInt(attr[i].getText());

            if (i < 2)
                coin[i] = money;
            else
                bill[i - 2] = money;

        }




    }


}
