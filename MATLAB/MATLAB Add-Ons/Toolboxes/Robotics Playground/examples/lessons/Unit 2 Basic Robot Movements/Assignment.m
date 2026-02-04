Robo=mlrobot;
mlrobotstart(Robo);
pause(0.5)
for i = 1:4
mlrobotsetmotorvoltage(Robo,'left',-3);
mlrobotsetmotorvoltage(Robo,'right',3);
pause(4)
mlrobotsetmotorvoltage(Robo,'left',3);
mlrobotsetmotorvoltage(Robo,'right',3);
pause(3)
mlrobotreadcompassangle(Robo);
end
mlrobotstop(Robo);