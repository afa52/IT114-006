<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone4</td></tr>
<tr><td> <em>Student: </em> Anthony Aliotta (afa52)</td></tr>
<tr><td> <em>Generated: </em> 5/7/2023 10:30:53 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-chatroom-milestone4/grade/afa52" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone3 from the proposal document:&nbsp;&nbsp;<a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Client can export chat history of their current session (client-side) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot of related UI</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/236708464-e95e0624-1fee-4043-93a6-c8e13c0ade53.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows 3 clients connected to a server and the &quot;Export Chat&quot;<br>button implemented next to the &quot;Send&quot; button<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot of exported data</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/236709256-6733def4-047d-41bd-9ba3-435aa7c628b6.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot displays the exported data from the server into the file &quot;chat_logs.txt&quot;<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>First, I created a new JButton called &quot;Export Chat&quot; and then I attached<br>an addActionListener() method to the &#39;histButton&#39; JButton and inside that method is the<br>exportChatHistory() method that runs when the button is clicked.&nbsp;<div>When exportChatHistory() is called, it<br>first creates a StringBuilder object and gets all the components inside the &#39;chatArea&#39;<br>container and iterates over each component. The method then grabs the text from<br>area by calling &#39;getText()&#39; and appends it to the StringBuilder.&nbsp;</div><div>When all chat messages<br>are collected in the StringBuilder object, the method converts it to a String<br>and then creates a new file named &quot;chat_logs.txt&quot;. It then writes the contents<br>of the &#39;logs&#39; String to the file using a FileWriter object.&nbsp;</div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Client's mute list will persist across sessions (server-side) </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add a screenshot of how the mute list is stored</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/236710419-f0b75f05-8657-4ac7-9b29-a261e2706b56.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the file &quot;hope.txt&quot; and displays the client&#39;s name and the<br>users that were added to client&#39;s mute list.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add a screenshot of the code saving/loading mute list</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/236710867-cb6f3887-0771-4e25-82a6-d8ce06b3c75c.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the methods: getMutedClients(), mute(), unmute(), and isMuted()<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/236710873-0169abe9-4855-46a3-aed8-866b1c374036.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the methods: saveMuteList() and loadMuteList()<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>The mute() method adds a muted client to the mutedClients list and saves<br>the list to a file by calling the saveMuteList() method.<div><br></div><div>The unmute() method removes<br>a muted client from the mutedClients list and saves the updated list to<br>a file.</div><div><br></div><div>The saveMuteList() method saves the list of muted clients to a text<br>file with the name of the current client.</div><div><br></div><div>The loadMuteList() method is used to<br>load the previously saved list of muted clients from a text file with<br>the name of the current client.</div><div><br></div><div>The isMuted() method checks if a given client<br>is muted.&nbsp;<br></div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Client's will receive a message when they get muted/unmuted by another user </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add a screenshot showing the related chat messages</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/236713271-e81418ee-5621-45ba-a5ff-1143eefb0922.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot displays messages that clearly inform the muted/unmuted client that they were<br>muted or unmuted by the specific user<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add a screenshot of the related code snippets</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/236713504-1dc0ef7e-f732-44b3-b9c7-f08a8a54c42e.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows where the muted/unmuted messages occur in Room.java<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>MUTE case: called when a client sends /mute command to the server. The<br>first line splits the list into individual usernames and creates an empty list<br>called &quot;muteList&quot; to keep track of the users that were actually muted. Then,<br>it checks if the user is already muted using the isMuted() method of<br>the client object. If the user is not muted, the method calls the<br>mute() method of the client object to mute the user and adds the<br>username to the muteList. Then it looks for a ServerThread object called &quot;targetClient&quot;,<br>which searches for a user who was potentially muted, and it sends a<br>message to the user informing them that they have been muted by the<br>client.<div><br></div><div>UNMUTE case: called when a client sends /unmute command to the server. Essentially<br>works the same structure as the MUTE case, but instead checks if the<br>user is already unmuted using isMuted() of the client object. If the user<br>is muted, the unmute() method of the client object is called to unmute<br>the user and adds the username to an unmuteList. Finally, it looks for<br>a ServerThread object called &quot;targetClient&quot;, which searches for a user who was potentially<br>un-muted, and it sends a message to the user informing them that they<br>have been un-muted by the client.<br></div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> User list should update per the status of each user </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707795-a9c94a71-7871-4572-bfae-ad636f8f8474.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshot for Muted users by the client should appear grayed out</td></tr>
<tr><td><table><tr><td>Missing Image</td></tr>
<tr><td> <em>Caption:</em> (missing)</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshot for Last person to send a message gets highlighted</td></tr>
<tr><td><table><tr><td>Missing Image</td></tr>
<tr><td> <em>Caption:</em> (missing)</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain how you implemented this</td></tr>
<tr><td> <em>Response:</em> <p>(missing)</p><br></td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-chatroom-milestone4/grade/afa52" target="_blank">Grading</a></td></tr></table>