for i = -100:100
    a(i+101)=i;
    b(i+101)=i^2;
end 
plot(a,b)
title('Numbers vs. their squares');
xlabel('Number')
ylabel('Square')
clear