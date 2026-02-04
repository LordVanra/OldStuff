import matplotlib.pyplot as plt
cards = []
cycles = []
for i in range(3, 200):
    a = []
    b = 0
    if i % 2 == 0:
        k = i-1
    else: 
        k = i
    for j in range(k):
        a.append(j)
    while a != [0]:
        n0 = n = a.pop(1)
        b += 1
        # print("Current Card = " + str(n))
        n *= 2
        while n != n0:
            a.remove(n)
            # print("Current Card = " + str(n))
            n *= 2
            if n > k:
                # print("Modded by " + str(k))
                n -= k
                # print("Resulting in " + str(n))
    # print(f"{i} cards: {b} cycles")
    cards.append(i)
    cycles.append(b)
import string
import random

# full ASCII-ish pool
symbols = list(string.ascii_lowercase)   # 26 lowercase
symbols += list(string.ascii_uppercase)  # 26 uppercase
symbols += list(string.digits)           # 10 digits
symbols += list(string.punctuation)      # ~32 punctuation
symbols += [chr(i) for i in range(128, 256)]  # extended Latin (non-ASCII)
# ~220 unique symbols

length = 3

def shuffle(changeable):
    mid = (len(changeable)+1) // 2
    left_half = changeable[:mid]
    right_half = changeable[mid:]
    
    counter_inserting = 1
    for element in right_half:
        left_half.insert(counter_inserting, element)
        counter_inserting += 2
    return left_half


data = {}

while length < 200:
    # print(f"Length {length}:")
    symbols_to_choose_from = symbols.copy()
    original = []
    for i in range(length):
        element_chosen = random.choice(symbols_to_choose_from)
        original.append(element_chosen)
        symbols_to_choose_from.remove(element_chosen)

    changeable = original[:]

    attempts_to_return_to_original = 1
    changeable = shuffle(changeable)

    while (changeable != original):
        changeable = shuffle(changeable)
        attempts_to_return_to_original += 1

    # print(attempts_to_return_to_original)
    data[length] = attempts_to_return_to_original  

    length += 1
  

# Write results to file
with open("data.txt", "w") as file:
    for i in sorted(data.keys()):
        file.write(f"{i}: {data[i]}\n")

# Now read them back
lengths = []
attempts = []
with open("data.txt", "r") as file:
    for line in file:
        if ":" in line:
            l, a = line.strip().split(":")
            lengths.append(int(l))
            attempts.append(int(a))
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
# Plot
toitents = []
for i in range(len(cycles)):
    cycles[i] *= attempts[i]
    toitents.append(totient(cards[i])/cycles[i])

odd_cards = [x for x in cards if x % 2 == 1]
odd_toitents = [toitents[i] for i, x in enumerate(cards) if x % 2 == 1]
        
plt.figure(figsize=(20,12))
plt.plot(odd_cards, odd_toitents, marker="o")
for i in range(len(odd_cards)):
    print(f"! !{odd_cards[i]} with toitent factor of  {round(odd_toitents[i],2)}! !")
plt.title("Total number of cycles vs deck length")
plt.xlabel("Card Deck Size")
plt.ylabel("Total Number of Cycles")
plt.grid(True)
plt.savefig("graph.png")
plt.show()