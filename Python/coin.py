from random import randint

n=1
total = 0
for i in range(100000000):
    while randint(1,2)==1:
        n *= 2
    total += 2*n
    n = 1

print(total/10000)