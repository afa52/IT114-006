package CR.client.ui;


import java.awt.Color;


import javax.swing.JEditorPane;
import javax.swing.JPanel;


import CR.client.ClientUtils;


public class UserListItem extends JPanel {
    private long clientId;
    private String clientName;
    JEditorPane text = new JEditorPane("text/plain", "");


    public UserListItem(String clientName, long clientId) {
        this.clientId = clientId;
        this.clientName = clientName;
        setBackground(Color.cyan);


        text.setEditable(false);
        text.setText(getBaseText());


        this.add(text);
        ClientUtils.clearBackground(text);
    }


    private String getBaseText() {
        return String.format("%s[%s]", clientName, clientId);
    }


    public long getClientId() {
        return clientId;
    }
}
