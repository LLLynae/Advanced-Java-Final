 /**
 *Lauren Lira - lllira1048
 *CIT 4423 01
 *Nov 23,2022
 *Windows 11
 */

package lauren1048;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.awt.BorderLayout;
import java.awt.Color;
//Imports

public class ReceiptGUI extends JFrame {/*Class for creating an editable receipt when user selects 
    checkout button. This interface displays information and is interactive through buttons.*/

    GridLayout gridLayout = new GridLayout(2, 2);//GridLayout for the two buttons
    JPanel backingPanel = new JPanel(new BorderLayout(20, 20));//panel to attach other panels to
    JPanel centralPanel = new JPanel();//central panel, holds information about products and prices
    JPanel northPanel = new JPanel(new GridLayout(1, 3));//Top panel for store information
    JPanel southPanel = new JPanel(gridLayout);//Bottom panel for two buttons
    JButton editButton = new JButton("Edit \nOrder");//instantiates edit button for editing order
    JButton completeButton = new JButton("Complete \nOrder");//Instantiates button for completing order
    JTextArea purchaseQUantity = new JTextArea("Quantity Purchased: ");//textarea for quantity label
    JTextArea indivPrice = new JTextArea("Unit Price: ");//textarea for unit price label
    JTextArea totalPrice = new JTextArea("Price: ");//textarea for total price of added units label
    JTextArea productName = new JTextArea("Product: ");//textarea for product name label
    JTextArea tax = new JTextArea("Tax: ");//textarea for tax label
    Font font = new Font("Arial", Font.PLAIN, 12);//default font set up

    ArrayList<ProductData> products;//Array list passed - from class holding database data
    ArrayList<Integer> amounts;// Array list passed of product quantities

    //Datatypes and access modifiers
    private double userTotal = 0.0;
    private double userSubtotal = 0.0;
    private double userTax = 0.0;
    int numberOrdered = 0;

    public ReceiptGUI(ArrayList<ProductData> products, ArrayList<Integer> amounts) {/*Constructor */

        //ensures the JFrame is visible and establishes array lists
        this.setVisible(true);
        this.products = products;
        this.amounts = amounts;

        //Exit on close and GridLayout for information line up
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout centralLayout = new GridLayout(products.size() + 4, 4, 10, 10);

        //GridLayout set for center panel
        this.centralPanel.setLayout(centralLayout);

        //formats all JTextAreas and adds them to the central panel
        prepTextArea(purchaseQUantity);
        centralPanel.add(purchaseQUantity);

        prepTextArea(indivPrice);
        centralPanel.add(indivPrice);

        prepTextArea(totalPrice);
        centralPanel.add(totalPrice);

        prepTextArea(productName);
        centralPanel.add(productName);

        for (int i = 0; i < products.size(); i++) {/*Creates array of JTextAreas for data read from arraylists*/

            JTextArea pq = new JTextArea(String.valueOf(this.amounts.get(i).intValue()));
            prepTextArea(pq);
            pq.setFont(font);
            centralPanel.add(pq);

            JTextArea ppu = new JTextArea(this.products.get(i).getPriceAsString());
            prepTextArea(ppu);
            ppu.setFont(font);
            centralPanel.add(ppu);

            Double userTotalPrice = (this.amounts.get(i).intValue() * this.products.get(i).getPrice());
            userSubtotal += userTotalPrice;

            String totalString = String.format("$%,.2f", userTotalPrice);
            JTextArea area = new JTextArea(totalString);
            prepTextArea(area);
            area.setFont(font);
            centralPanel.add(area);

            JTextArea prod = new JTextArea(this.products.get(i).getName());
            prepTextArea(prod);
            prod.setFont(font);
            centralPanel.add(prod);

        }

        //Tax added to central panel
        JTextArea tax = new JTextArea("tax:");
        prepTextArea(tax);
        centralPanel.add(tax);

        //User's tax value calculated and added to central panel
        this.userTax = userSubtotal * (0.08);
        JTextArea printTax = new JTextArea(String.format("$%,.2f", userTax));
        prepTextArea(printTax);
        centralPanel.add(printTax);

        //Spacers
        centralPanel.add(new JPanel());
        centralPanel.add(new JPanel());

        //Subtotal added to central panel
        JTextArea subtotal = new JTextArea("subtotal:");
        prepTextArea(subtotal);
        centralPanel.add(subtotal);

        //Formats string for subtotal price
        String print = String.format("$%,.2f", this.userSubtotal + userTax);

        //User's subtotal value calculated and added to central panel
        JTextArea printSubtotal = new JTextArea(print);
        prepTextArea(printSubtotal);
        centralPanel.add(printSubtotal);

        //Spacers
        centralPanel.add(new JPanel());
        centralPanel.add(new JPanel());

        //Shipping message added to central panel
        JTextArea shippingMessage = new JTextArea("Shipping: ");
        prepTextArea(shippingMessage);
        centralPanel.add(shippingMessage);

        //Shipping calculations completed using a method and added to central panel
        numberOrdered = itemsCalculation();
        double shipping = numberOrdered * 0.50;
        String ship = String.format("$%,.2f", shipping);
        JTextArea printShipping = new JTextArea(ship);
        prepTextArea(printShipping);
        centralPanel.add(printShipping);

        //User total calculated
        userTotal = userSubtotal + userTax + shipping;

        //total amount added to central panel
        JTextArea totalAmount = new JTextArea("Your total price is: ");
        prepTextArea(totalAmount);
        southPanel.add(totalAmount);

        //user's total formatted and added to central panel
        JTextArea printTotalAmount = new JTextArea(String.format("$%,.2f", userTotal));
        prepTextArea(printTotalAmount);
        southPanel.add(printTotalAmount);

        //Store Name added to top panel
        JTextArea storeName = new JTextArea("Online Shop\n 123 Main Street\n Anytown, USA 35678\n (512)222-8945");
        prepTextArea(storeName);
        northPanel.add(storeName, BorderLayout.CENTER);

        //local date instantiated
        LocalDate currentDate = LocalDate.now();

        //local date added to top panel
        JTextArea printDate = new JTextArea("Date purchased:\n" + currentDate.toString());
        prepTextArea(printDate);
        northPanel.add(printDate);

        //Buttons are added to bottom panel and given action listeners
        editButton.addActionListener(e -> editAction());
        southPanel.add(editButton);
        completeButton.addActionListener(e -> completeAction());
        southPanel.add(completeButton);

        //backing panel's size and color is set
        backingPanel.setPreferredSize(new Dimension(853, 480));
        backingPanel.setBackground(Color.WHITE);

        //top and bottom panel's size are set in relation to backing panel
        southPanel.setPreferredSize(new Dimension(backingPanel.getPreferredSize().width - 20,
                (backingPanel.getPreferredSize().height / 4)));
        northPanel.setPreferredSize(new Dimension(backingPanel.getPreferredSize().width - 20,
                (backingPanel.getPreferredSize().height) / 7));

        //Scroll object
        JScrollPane scroll = new JScrollPane(centralPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //All panels are added to backing panel
        backingPanel.add(northPanel, BorderLayout.NORTH);
        backingPanel.add(scroll, BorderLayout.CENTER);
        backingPanel.add(southPanel, BorderLayout.SOUTH);

        //backing panel added to JFrame
        this.add(backingPanel);
        this.pack();
    }

    
    private void completeAction() {//Exits system when complete button is pressed
        System.exit(0);
    }

    
    private void editAction() {//Opens receipt editor when button is pressed

        EditButton e = new EditButton(this.products, this.amounts);
        e.setVisible(true);
        this.dispose();

    }

    
    private int itemsCalculation() {//Calculates items and adds them together
        int addTogether = 0;

        for (int i = 0; i < amounts.size(); i++) {
            addTogether += amounts.get(i).intValue();
        }

        return addTogether;
    }

    private void prepTextArea(JTextArea area) {//Prepares and formats all text areas
        area.setOpaque(false);
        area.setEditable(false);
        area.setBorder(null);
    }
}//End of class ReceiptGUI
