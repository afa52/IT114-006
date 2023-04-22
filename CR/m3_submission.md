<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone3</td></tr>
<tr><td> <em>Student: </em> Anthony Aliotta (afa52)</td></tr>
<tr><td> <em>Generated: </em> 4/21/2023 11:29:13 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-chatroom-milestone3/grade/afa52" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone3 from the proposal document:&nbsp;&nbsp;<a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Connection Screens </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots showing the screens with the following data</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233753193-72a3314e-592d-431f-87fa-6ed018e14675.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the UI screens for the Host and Panel labels with<br>a &quot;next&quot; button<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233753233-fa406623-c92b-4e02-b5f6-8028d139437e.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the UI screen for entering a username with &quot;connect&quot; and<br>&quot;pevious&quot; buttons<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain the code for each step of the process</td></tr>
<tr><td> <em>Response:</em> <p>User.java<div>&nbsp; &nbsp; &nbsp;- Displays username of clients</div><div><br></div><div>ClientUI.java</div><div>&nbsp; &nbsp; &nbsp;- UI panels are created<br>in ClientUI constructor method</div><div>&nbsp; &nbsp; &nbsp;- Panels are created and assigned to instance<br>variables</div><div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;- ex. <b>csPanel = new ConnectionPanel(this);</b></div><div>&nbsp; &nbsp; &nbsp;- The<br>panels are then created in their respective objects with layout and formats</div><div>&nbsp; &nbsp;<br>&nbsp;- Then, the panels are added to main JFrame window using add()</div><div>&nbsp; &nbsp;<br>&nbsp;- ICardControls interface allows for adding panels dynamically and navigating between them using<br>next() and previous().&nbsp;</div><div><br></div><div>ICardControls.java</div><div>&nbsp; &nbsp; -&nbsp; used to control cards/panels in UI</div><div>&nbsp; &nbsp; -<br>contains methods: next(), previous(), show(), add(), connect()</div><div><br></div><div>IClientEvents.java</div><div>&nbsp; &nbsp; -&nbsp;</div><div>&nbsp; &nbsp; -</div><div>Card.java</div><div>&nbsp; &nbsp; -<br>eNum class that defines set of constants &quot;CONNECT, USER_INFO, CHAT, and ROOMS</div><div>&nbsp; &nbsp;<br>- Used to switch between different panels in UI</div><div>&nbsp; &nbsp; - For example<br>if the user clicks connect after entering a username, the CONNECT will be<br>passed to the show() method to display appropriate panel&nbsp;</div><div><br></div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Chatroom view </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots showing the related UI</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233755550-1a3c1e3b-b886-4d5c-a39c-2e0958dc5af4.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the user list panel, with all clients listed with a<br>blue background<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233755608-7ef167a3-1228-4994-adf0-e8b43d0658fb.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the chat message panel with chat history<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233755723-e7bd169a-6919-4b1a-b476-eeac7de47343.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the create message area where a user can create a<br>room<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233756483-6cc413d5-68a0-444f-9732-c8b62c7b542b.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the user being able to send a message by pressing<br>enter. Using the JTextField named textValue, a KeyListener is added to the JTextField.<br>Inside keyPressed(), the code checks if the key pressed is the Enter key<br>and then simultaneously clicks the &quot;send&quot; button<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Chat Activities </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show screenshots of the result of the following commands</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233756906-c6c48740-01ae-4a0f-ad5a-82d3a54c2d39.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the /flip command with the output appearing in a different<br>text format<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233756907-cc53c6ec-0b74-4a47-b4ee-2fe627761098.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the /roll command with 3 different outputs appearing in a<br>unique text format<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show the code snippets for each command</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233757227-71435929-69a2-4242-84ff-bb346912b577.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the server side implementation for /flip generation<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233757228-725d804e-f3e0-48a3-82e4-ae3ffdd5a126.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the server side implementation for /roll generation <br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the code flow of each command</td></tr>
<tr><td> <em>Response:</em> <p><b>/roll&nbsp;</b><div><br></div><div>Waits for /roll command from user. Creates a string array rollArgs that splits<br>the message at any space. The condition statement checks if rollArgs == 2,<br>meaning that the user entered two words, AND the second the word in<br>the array matches the format (&quot;d#d&quot;), then the code block executes. It will<br>first split the second word of rollArgs at the letter &quot;d&quot;, and then<br>assign the variable numDice to the number left of &#39;d&#39; a (&quot;dice[0])&quot; and<br>assign the variable max to the number right of &quot;d&quot; as &quot;(dice[1])&quot;. An<br>int variable total is also assigned to 0, and later used in the<br>for loop that rolls the assigned numofDice with the assigned max value. Finally,<br>the variable result is set to how many dice the user rolled and<br>what the total value is. The else-if code block test if the user<br>entered two words but without the format. If executed, then it will take<br>the second word of rollArgs (rollArgs[1]) and change it to an int value<br>using parseInt and assign it to the variable max. Another int variable num<br>is a random generated number between 1 and the user&#39;s input. Errors are<br>handled and the sendMessage() is called to send results back to user.<br><div><br></div><div><br></div><div><br></div><div><b>/flip&nbsp;</b></div><div><br></div><div>Waits for<br>/flip command from user. The first line stores a random number between 0-1<br>in a variable called flip. The condition statement checks if flip is less<br>than 0.5, if true the result is &quot;heads&quot; and else the result is<br>&quot;tails&quot;. Finally, sendMessage() is called to send the results back to the user.</div><div><br></div><div><br></div><div><b>text<br>formatting:</b></div><div><br></div><div>I added generic html elements to change the style of the outputted text,<br>and by changing the &quot;text/plain&quot; to &quot;text/html&quot; so that the JEditorPane will accept<br>html styles</div><div><div>&nbsp; &nbsp; &nbsp; &nbsp; JEditorPane textContainer = new JEditorPane(&quot;text/html&quot;, text);</div></div><div><br></div><div><br></div><div><br></div><div><b>client-&gt;server-&gt;client flow:&nbsp;</b></div><div><br></div><div>When a<br>client sends a message, the server checks if it starts with a command<br>trigger &quot;/&quot;. If it does, the server processes the message as a command.<br>If the first word of the message after the trigger character is roll,<br>the server parses the message to extract the parameters of the roll command.<br>If the parameters are not in the correct format, the server sends an<br>error message back to the client.</div><div><br></div><div>If the command is flip, the server randomly<br>chooses between heads or tails and sends the result back to the client.</div><div><br></div><div><br></div><div><br></div></div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Custom Text </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Screenshots of examples</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233758486-f110a076-25f2-48c1-b252-5d7cacaa149f.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates bold format working correctly with input string included in chat<br>send area<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233758484-25c2bde0-d596-4cac-bc76-0f2b6d7da6f6.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates italics format working correctly with input string included in chat<br>send area<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233758485-0e2bf739-95b7-469b-a12b-edba4b1f48dc.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates underline format working correctly with input string included in chat<br>send area<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233758488-ac2707b8-f6f2-48af-b92f-88df79216ec8.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates colors format working correctly with input string included in chat<br>send area<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233758489-37a9aeb6-af87-41e1-a7e9-db4e959b1bb4.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates all formats being combined into one sentence, are fragments of<br>a sentence.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how you got the UI side to render the text accordingly</td></tr>
<tr><td> <em>Response:</em> <p>The UI side renders html text from changing the constructor of JEditorPane from<br>(&quot;text/plain&quot;) to (&quot;text/html&quot;), allowing the JEditorPane to support rendering HTML content.&nbsp;<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 5: </em> Whisper </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots showing a demo of whisper commands</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233758795-00d1f1bf-1e81-4f89-b632-27e84d778381.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates a private message being sent across two clients using the<br>@ trigger, there are 3 people in the room and it is proven<br>that only the two parties see the messages in the same room<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show the server-side code snippets of how this feature works</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233759040-19a39100-35ec-450e-93ca-32c25ba85362.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the message being processed a target being found to whisper<br>to<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/233759186-5c1c0599-e762-4f18-8b07-84ec29bb986f.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot demonstrates the code that shows how the sender and whisper target<br>are the only ones to get the message<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the code logic of how this was achieved</td></tr>
<tr><td> <em>Response:</em> <p>If &#39;message&#39; starts with the &#39;WHISPER&#39; string, which is &quot;@&quot;, the sender of<br>this command is identified by the &#39;ServerThread&#39; object that is passed as the<br>first argument in the method. The sender&#39;s client ID is then retrieved using<br>getClientId(). The method iterates through all connected clients and checks if the client<br>name specified in the whisper command matches the name of any of the<br>connected clients. If there is a match, the message is sent only to<br>the target client using sendMessage().<div><br></div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 6: </em> Mute/Unmute </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707795-a9c94a71-7871-4572-bfae-ad636f8f8474.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots demoing this feature</td></tr>
<tr><td><table><tr><td>Missing Image</td></tr>
<tr><td> <em>Caption:</em> (missing)</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshots of the code snippets that achieve this feature</td></tr>
<tr><td><table><tr><td>Missing Image</td></tr>
<tr><td> <em>Caption:</em> (missing)</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the code logic of how this was achieved</td></tr>
<tr><td> <em>Response:</em> <p>(missing)</p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 7: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707795-a9c94a71-7871-4572-bfae-ad636f8f8474.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Pull request from milestone3 to main</td></tr>
<tr><td>Not provided</td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-chatroom-milestone3/grade/afa52" target="_blank">Grading</a></td></tr></table>