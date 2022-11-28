 /**
 *Lauren Lira - lllira1048
 *CIT 4423 01
 *Nov 13,2022
 *Windows 11
 */

package lauren1048;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductStorage {/*Class for storing products in an array list */

    ArrayList<ProductData> products = new ArrayList<ProductData>();

    public ProductStorage() {/*Constructor */
        String query = "SELECT Names FROM products_information";
        String url = "jdbc:mysql://127.0.0.1:3306/adv_java_data";
        String user = "root";
        String password = "F10w3r8798*";
        
        try {/*Creates array of products after accessing database */
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                products.add(new ProductData(result.getString("Names")));
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Getters
    public ProductData[] getProduct() {
        return this.products.toArray(new ProductData[products.size()]);
    }

    public ProductData getProduct(int i) {
        return this.products.get(i);
    }

    

}//End of class
