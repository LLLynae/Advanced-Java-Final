 /**
 *Lauren Lira - lllira1048
 *CIT 4423 01
 *Nov 13,2022
 *Windows 11
 */

package lauren1048;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.GridLayout;

public class EditButton extends JFrame implements ActionListener {/*This class is designed to create the "edit"
button on the receipt when the user checks out. The class is called in the ReceiptGUI where the edit button is added.*/

    private double userTotal = 0.0;
    private Timer userTime;
    private final int MAXVALUE = 10;
    //datatypes and access modifiers

    ArrayList<IncrementButton> incrementButtons = new ArrayList<IncrementButton>();
    ArrayList<ProductData> products = new ArrayList<ProductData>();// Product list
    ArrayList<Integer> amounts = new ArrayList<Integer>();// Amount purchased list
    ArrayList<JTextArea> printTotals = new ArrayList<JTextArea>();// This is where totals are shown, and then updated
                                                                  // corresponding with the amount number
    ArrayList<Double> userTotals = new ArrayList<Double>();// Total of each item times the number ordered

    JTextArea printUserTotal = formatted(" ");// Individually shown

    JButton updateButton = new JButton("Update Order");
    JButton backToShop = new JButton("Back to store");
    //Buttons created

    public EditButton(ArrayList<ProductData> products, ArrayList<Integer> amounts) {/*Constructor */

        setTitle("Edit Receipt");
        this.setVisible(true);

        //Arraylists
        this.amounts = amounts;
        this.products = products;
        
        //Gridlayout so information lines up correctly
        GridLayout layout = new GridLayout(products.size() + 3, 4, 10, 10);
        
        
        JPanel editPanel = new JPanel(layout);//JPanel created
        
        //Adds JTextArea formatted strings to the panel in order to label information 
        editPanel.add(formatted("Product Name"));
        editPanel.add(formatted("Quantity: "));
        editPanel.add(formatted("Unit Price: "));
        editPanel.add(formatted("Price: "));
        

        
        for (int i = 0; i < products.size(); i++) {/*adds array of products selected, and the incrementors to go with them. 
         Incrementors bring the product price up in amount.
         Array of totals is the established 
         Added to panel lastly
         Incrementor buttons established in another class */
            editPanel.add(formatted(products.get(i).getName()));
            incrementButtons.add(new IncrementButton(amounts.get(i).intValue(), MAXVALUE));
            editPanel.add(incrementButtons.get(i));
            editPanel.add(formatted(products.get(i).getPriceAsString()));
            userTotals.add(products.get(i).getPrice() * amounts.get(i).intValue());
            printTotals.add(formatted(formatTotal(userTotals.get(i))));
            editPanel.add(printTotals.get(i));
        }
        editPanel.add(formatted("Total: "));//String added to define total owed by user

        //Spacers
        editPanel.add(new JPanel());
        editPanel.add(new JPanel());

        //Calls method to calculate the total the user owes
        userTotal = calculateUserTotal();

        //formats the user's total using a method, and then adds it the the panel
        printUserTotal.setText(formatTotal(userTotal));
        editPanel.add(printUserTotal);

        //Spacer
        editPanel.add(new JPanel());

        //Buttons are added to JPanel here
        backToShop.addActionListener(this);
        editPanel.add(backToShop);
        updateButton.addActionListener(this);
        editPanel.add(updateButton);

        //ScrollPane established, size of panel it encompasses established
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JScrollPane scrollPane = new JScrollPane(editPanel);
        scrollPane.setPreferredSize(new Dimension(853, 480));
        this.add(scrollPane);
        this.pack();

        //Timer so the incremented values are updated, every 10 miliseconds
        userTime = new Timer(10, this);
        userTime.start();

    }

    private String formatTotal(double userTotal) {/*returns format for user's total price */
        return String.format("$%,.2f", userTotal);
    }

    private JTextArea formatted(String string) {/*returns format for all textareas */
        JTextArea textArea = new JTextArea(string);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setBorder(null);

        return textArea;
    }

    private double calculateUserTotal(int index) {/*returns array of calculations result as double*/
        return products.get(index).getPrice() * incrementButtons.get(index).getUserSelected();
    }

    private double calculateUserTotal() {/*calculates the user's total owed */
        double completeTotal = 0.0;

        for (int i = 0; i < userTotals.size(); i++) {
            completeTotal += userTotals.get(i);
        }

        return completeTotal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {/*Action listener override method */
        if (e.getSource() == updateButton) {
            ReceiptGUI rgui = new ReceiptGUI(this.products, this.amounts);//new receipt created when update is clicked
            this.dispose();
        } else if (e.getSource() == backToShop) {
            UI ui = new UI();//New interface created if user wishes to delete receipt and return to store
            this.dispose();
        } else {
            for (int i = 0; i < userTotals.size(); i++) {
                printTotals.get(i).setText(formatTotal(calculateUserTotal(i)));
                userTotals.set(i, calculateUserTotal(i));
                amounts.set(i, incrementButtons.get(i).getUserSelected());
            }
            userTotal = calculateUserTotal();
            printUserTotal.setText(formatTotal(userTotal));
        }

    }

}//End of class EditButton
