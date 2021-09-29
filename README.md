# BU_CS655_PA1
 ##### Yanchong Peng  U25362525
 ##### Jinghao Ye  U67873405


### Part1:
First, you need to open the terminal and change the directory to the directory where these files are located. Next, using command 
>javac MultiTread.java Server.java Client.java

to compile the three java files. Then you have to run the Server first by using the command. 
> java Server  58888

The number 58888 is a port number. When you establish the server you have to enter a valid port number. You can change the port number to other valid port numbers. We use 58888 in our connection test. 
Then you can open a new terminal and change to the same directory to run the client part by the command:
>java Client "Server IP address" 58888

This command has two arguments. "Server IP address" is the IP address of the machine where you set up the server. In our test, we set up our server at csa@bu.edu whose IP address is 128.197.11.40. The second argument is the port number. The port number has to be the same as the one that the server uses. In our test, since we use 58888 for the server port number, the client has to use the same one. After the client runs successfully, you can enter a message you want to send to the server. After you enter the message and press the enter, the message will be sent to the server and the server will send the same message back to the client.

### Part2:
For part2 , the way of building connection between server and client is similar to part1. First, you need to change the directory to where these files are located and compile all three java files by using : 
>javac MultiTread.java Server.java Client.java

Next you have to set up server first:
>java Client "Server IP address" "port number"

After server is established successfully, you can run the client in another terminal:

>java Client "Server IP address" "port number"

