myRobot=mlrobot
mlrobotstart(myRobot)
mlrobotsetmotorvoltage(myRobot,'left',-2.5)
mlrobotsetmotorvoltage(myRobot,'right',2.5)
pause(10)
mlrobotsetmotorvoltage(myRobot,'left',0)
mlrobotsetmotorvoltage(myRobot,'right',0)
mlrobotstop(myRobot)