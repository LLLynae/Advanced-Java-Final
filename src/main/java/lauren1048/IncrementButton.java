 /**
 *Lauren Lira - lllira1048
 *CIT 4423 01
 *Nov 13,2022
 *Windows 11
 */

package lauren1048;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridLayout;
//imports

public class IncrementButton extends JPanel{/*This class creates an incrementor for items 
    when the "edit receipt" button is pressed. It adds or subtracts items from the receipt.*/

    //Datatypes and access modifiers
    private int begin;
    private int limit;
    private JTextArea textSpace;
    private JButton addButton = new JButton("+");//Button for adding an item
    private JButton subtractButton = new JButton("-");//Button for subtracting an item
    private int userSelect;
    private GridLayout incrementGrid = new GridLayout();

    public IncrementButton(int begin, int limit){/*Constructor */

        //starts incrementor
        this.begin = begin;
        this.userSelect = begin;
        this.limit = limit;

        //New JTextArea for the value of what the user selects
        this.textSpace = new JTextArea(String.valueOf(userSelect));
        this.textSpace.setEditable(false);
        this.textSpace.setBorder(null);
        this.textSpace.setOpaque(false);

        //Add and Subtract buttons get action listeners for methods below
        subtractButton.addActionListener(e -> subtractItem());
        this.add(subtractButton);
        this.add(textSpace);
        addButton.addActionListener(e -> addItem());
        this.add(addButton);

    }

    private void addItem(){/*Allows + button to increase item quantity by one  */
        if(userSelect < limit){
            userSelect++;
        }else if(userSelect == limit){
            userSelect = 0;
        }
        textSpace.setText(String.valueOf(userSelect));
    }

    private void subtractItem(){/*Allows - button to decrease item quantity by one  */
        if(userSelect > 0){
            userSelect--;
        }else{
            userSelect = limit;
        }
        textSpace.setText(String.valueOf(userSelect));
    }

    //Getter
    public int getUserSelected() {
        return userSelect;
    }
}// End of class IncrementButton
