 /**
 *Lauren Lira - lllira1048
 *CIT 4423 01
 *Nov 13,2022
 *Windows 11
 */

package lauren1048;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;

import java.awt.GridLayout;
import java.util.ArrayList;

public class UI extends JFrame {/*Class for GUI display of checkboxes to make customer
    receipt. Dialog box returns subtotal, and JFrame holds customer select info. */

    GridLayout layout = new GridLayout(11, 3, 50, 20);
    JPanel panel = new JPanel(layout);
    JCheckBox[] checkboxes = new JCheckBox[10];
    JComboBox<Integer>[] combo = new JComboBox[checkboxes.length];
    JTextField[] spacers = new JTextField[combo.length + 1];
    ProductStorage store = new ProductStorage();
    JButton checkoutButton = new JButton("Check Out");
    Integer[] nums = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    public UI() {/*Constructor to build JFrame and add components*/
        this.setBackground(Color.BLACK);
        this.setSize(900, 600);
        // this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (int i = 0; i < checkboxes.length; i++) {/*Creates arrays of checkboxes, spacers, and 
            comboboxes. Gridlayout fits them together.*/
            this.checkboxes[i] = new JCheckBox();
            this.combo[i] = new JComboBox(nums);
            this.checkboxes[i].setText(store.getProduct(i).getName() + " " + store.getProduct(i).getPriceAsString());

            this.spacers[i] = new JTextField();
            this.spacers[i].setEditable(false);
            this.spacers[i].setBorder(null);
            this.spacers[i].setOpaque(false);

            panel.add(this.checkboxes[i]);
            panel.add(this.combo[i]);

        }
        spacers[spacers.length - 1] = spacers[0];

        checkoutButton.addActionListener(e -> checkoutAction());
        panel.add(checkoutButton);

        this.add(panel);
        this.setVisible(true);
    }

    private void checkoutAction(){/*Adds actionlistenr to checkout button. Allows for randomly 
        generated products and prices to be added up.*/
        double subtotal = 0.0;
        ArrayList<ProductData> data = new ArrayList<ProductData>();
        ArrayList<Integer> multipliers = new ArrayList<Integer>();

        for(int i = 0; i < checkboxes.length; i++){
            if(checkboxes[i].isSelected()){
                StringBuilder build = new StringBuilder(checkboxes[i].getText());
                String name = build.substring(0, build.indexOf("$") -1);
                data.add(new ProductData(name));
                multipliers.add(combo[i].getSelectedIndex());
            }
        }
        if(data.isEmpty()){
            return;
            //Nothing happens if checkbox is not selected
        }else{
            String userMessage = "We appreciate your patronage\n";
            for(int i = 0; i < data.size(); i++){
                ProductData product = data.get(i);
                String part = String.format("%s %s %s%n", multipliers.get(i), product.getName(), product.getPriceAsString());
                userMessage += part;
                subtotal += product.getPrice() * (int)multipliers.get(i);
            }//Iterates through array to get JOptionPane info
            ReceiptGUI gui = new ReceiptGUI(data, multipliers);
            dispose();
            
        }

    }

}//End of class
