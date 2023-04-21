<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone 2</td></tr>
<tr><td> <em>Student: </em> Anthony Aliotta (afa52)</td></tr>
<tr><td> <em>Generated: </em> 4/5/2023 6:36:39 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-chatroom-milestone-2/grade/afa52" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone2 from the proposal document:&nbsp; <a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Payload </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Payload Screenshots</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230221382-91a54567-9a58-426f-8ec5-41eeff98a32f.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot displays half of the properties applied to the Payload class, and<br>includes a comment explaining the general use of it <br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230221396-255b7797-711d-492e-ac7d-88e40d053aa8.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot displays the other half of the properties applied to Payload class<br>along with the toString() method, and includes a comment explaining the general use<br>of it <br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230221722-07508d5a-2361-4eed-9b76-fa3500a9f85a.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot displays three different types of Payload processes, one type being [CLIENT_ID],<br>the second type being [RESET_USER_LIST], and the third type being [CONNECT]<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230222148-9d5139b6-cbeb-498d-9ebc-ec834e75a904.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot displays two other types of Payloads, type [MESSAGE] and type [DISCONNECT]<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230222311-9200827a-794d-40e5-addb-6dacf878f8d5.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot contains various types of payloads. Each payload has a Type, ClientId,<br>ClientName, and a Message field.<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Server-side commands </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show the code for the mentioned commands</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230221233-1dea28c3-7131-4abd-b027-4e768a3cfa83.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the ROLL case code with explanation of what is being<br>attempted. <br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230221027-c8b6c779-4c2e-4a15-9d0a-c92a3a0b6f3d.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the FLIP case with explanation of what is being attempted.<br><br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230222752-68a9d016-5d7e-4c7c-a1ee-28a79b493677.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the user using the /roll command and how the code<br>works. It displays each format being used and working, and also an &quot;invalid<br>command format&quot; message that displays when incorrectly inputs /roll format<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230222765-ec26892c-c2a4-4a14-a3a5-eb6072e54edc.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the user using the /flip command and how the code<br>works.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Explain the logic/implementation of each commands</td></tr>
<tr><td> <em>Response:</em> <div>case ROLL: waits for /roll command from user. Creates a string array rollArgs<br>that splits the message at any whitespace characters. The condition statement checks if<br>rollArgs == 2, meaning that the user entered two words, AND if the<br>second word in the array matches the second format ("#d#"), then the code<br>block executes. It will first split the second word of "(dice[0])" and assign<br>the variable max to the number right of 'd' as "(dice[1])". An int<br>variable total is also assigned to 0, and later used in the for<br>loop that rolls the assigned number of dice with the assigned max value.<br>Finally, the variable result is set to how many dice the user rolled<br>and what the total value was. The else-if code block tests the first<br>format. If the user entered two words but without the ("#d#") format, then<br>it will take the second word of rollArgs[1] and change it to an<br>int value using parseInt and assign to the variable max. Another int variable<br>num is random generated number between 1 and the user inputted integer. If<br>the user calls the command but does not match the format or does<br>not input an integer, then the result variable is assigned to an error<br>message. Lastly, the sendMessage() function is called to send the results back to<br>the user.&nbsp;</div><div><br></div><div><div>case FLIP: waits for /flip command from user. The first line stores<br>a random number between 0 and 1 into a variable called flip. The<br>condition statement checks if flip is less than 0.5, if true the result<br>is "heads" and else the result is "tails". Finally, the sendMessage() function is<br>called to send the results back to the user.*/&nbsp;</div></div><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Text Display </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show the code for the various style handling via markdown or special characters</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230223594-7ff07fe6-702c-4c59-9d3c-d393084ebdd9.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot is an implementation of the processTextFormatting() method, and includes comments explaining<br>how each text style is processed.<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230223910-cf6d63d1-f2d4-42a5-a6e0-100d1d95ff15.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the processTextFormatting() method being assigned to the variable message. This<br>method is called to process the message before it is sent to all<br>clients. <br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show source message and the result output in the terminal (note, you won't actually see the styles until Milestone3)</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230224332-61a3d880-d33f-4e35-a6a6-53608375eb9c.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows how the text styles bold, italics, and underlined are displayed<br>in the terminal<br></p>
</td></tr>
<tr><td><img width="768px" src="https://user-images.githubusercontent.com/123400241/230224477-c1006bc5-ace4-4abe-b29e-9978be54704b.png"/></td></tr>
<tr><td> <em>Caption:</em> <p>This screenshot shows the the color of text is changed within the terminal,<br>and also includes a mix of all styles in one substring being printed<br>to the terminal<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Explain how you got each style applied</td></tr>
<tr><td> <em>Response:</em> <div>The processTextFormatting() method is used to apply styles to a user inputted message.<br>The method replaces a user inputted message with HTML formatting tags based on<br>the user's desired format. If the inputted string begins with "!b" and ends<br>with "b!", then the text will be bold, if the string begins with<br>"!i" and ends with "i!", then the text will be italics, if the<br>string begins with "!u" and ends with "u!", then the text will be<br>underlined, finally if the inputted string matches "!red/green/blue" and ends with "red/green/blue!" then<br>the text will change to the inputted color. The replaceAll() takes two parameters,<br>one that is the occurrence of the inputted string, and the second is<br>the desired replacement field. The (.?) is used to match any sequence of<br>characters that occurs between two specific strings. And the $1 is a capture<br>of what is being stored in (.?), so the second parameter of replaceAll()<br>is applying the HTML formatting tag to user inputted message.<br></div><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707795-a9c94a71-7871-4572-bfae-ad636f8f8474.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Include the pull request for Milestone2 to main</td></tr>
<tr><td>Not provided</td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-006-S23/it114-chatroom-milestone-2/grade/afa52" target="_blank">Grading</a></td></tr></table>