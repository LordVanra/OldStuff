R=mlrobot;
mlrobotstart(R);
mlrobotsetmotorvoltage(R,'left',-1)
mlrobotsetmotorvoltage(R,'right',5)
while mlrobotreadcompassangle(R)<100
mlrobotreadcompassangle(R)
pause(1)
end
mlrobotstop(R)

