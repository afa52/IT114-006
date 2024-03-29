
package CR.client;


import java.awt.BorderLayout;


import javax.swing.JPanel;
import javax.swing.JTextField;


public class User extends JPanel {
    private String name;
    private JTextField nameField;


    public User(String name) {
        this.name = name;
        nameField = new JTextField(name);
        nameField.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add(nameField);
    }


    public String getName() {
        return name;
    }

    public String getName(String wrapper) {
    	return String.format(wrapper, name);
    }
    
	public void setName(String name, String wrapper) {
		//this.name = name;
		// TODO Auto-generated method stub
		nameField.setText(String.format(wrapper, name));
	}
    

}
