import matplotlib.pyplot as plt;
def gcd(a, b):
    while b:
        a, b = b, a % b
    return a

def totient(n):
    if n == 1:
        return 1
    
    result = 0
    for i in range(1, n + 1):
        if gcd(i, n) == 1:
            result += 1
    return result
def factors(n):
    result = []
    for i in range(1, int(n**0.5) + 1):
        if n % i == 0:
            result.append(i)
            if i != n // i: 
                result.append(n // i)
    return sorted(result)
def ord2(n):
    if n == 1:
        return 1
    value = 1
    power = 2 % n
    while power != 1:
        power = (power * 2) % n
        value += 1
    return value
x = []
y = []
for i in range(3, 400, 2):
    a = 0

    for j in factors(i):
        a += totient(j)/ord2(j)
    print(f"For i = {i}, a = {a}")
    x.append(i)
    y.append(a)
log = lambda a : a ** 2
plt.plot(x, y, marker="o")
plt.plot(list(map(lambda x: x**2, range(20))), range(20), marker="o")
plt.grid(True)
plt.show()
    