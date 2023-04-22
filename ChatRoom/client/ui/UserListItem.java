package ChatRoom.client.ui;

import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import ChatRoom.client.ClientUtils;

public class UserListItem extends JPanel {
    private long clientId;
    private String clientName;
    JEditorPane text = new JEditorPane("text/plain", "");

    public UserListItem(String clientName, long clientId) {
        this.clientId = clientId;
        this.clientName = clientName;
        setBackground(Color.BLUE);

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