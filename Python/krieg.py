from random import randint
from math import e

troops = [[[120, 120, 120, 120, 120], [], [], []], [[120, 120, 120], [], [30], []]] #Inf, Art, Cav, Off

def typeToMult(type):
	if type == "i":
		return 1
	elif type == "a" or type == "c":
		return 1.2
	elif type == "o":
		return 0.6
		
def typeToIndex(type):
	if type == "i":
		return 0
	elif type == "a":
		return 1
	elif type == "c":
		return 2
	elif type == "o":
		return 3
		
def calcDamage(attackers, defender, attackerType, distance):
	baseCasualties = ((attackers/defender)**0.7)*25*(0.1*randint(1, 6)+0.6)*typeToMult(attackerType)
	totalCasualties = baseCasualties*((e**(-distance/250))+30/distance)
	defender -= defender*totalCasualties*0.01
	if defender<0:
		defender = 0
	return round(defender)
	
def doBattle():
	side = int(input("Attacking Side: "))
	typeStr = input("Attacking Type: ")
	type = typeToIndex(typeStr)
	attackers = []
	attackers.append(input("Add a battalion: "))
	while attackers[-1] != "q":
		try:
			int(attackers[-1])
		except ValueError: 
			attackers.pop()
			print("Invalid entry")
		attackers.append(input("Add a battalion: "))
		if(attackers[-1] in attackers[:-1]):
			attackers.pop()
			print("Already added battalion")
	attackers.pop()
	targetType = typeToIndex(input("Target Type: "))
	target = int(input("Target: "))
	totalAttackers = 0 
	for i in attackers:
		totalAttackers += troops[side][type][int(i)]
	defenders = troops[1-side][targetType][target]
	if typeStr == "i":
		mult = 1
	if typeStr == "a":
		mult = 4
	if typeStr == "c" or typeStr == "o":
		mult = 2
	distance = int(input("Distance: "))
	endDefenders = calcDamage(totalAttackers*mult, defenders, typeStr, distance)
	print(f"{totalAttackers} troops fired at {defenders} troops at a distance of {distance} meters and caused {defenders-endDefenders} deaths, resulting in them having {endDefenders} troops")
	troops[1-side][targetType][target] = endDefenders
	print(troops)
	return endDefenders
	
while True:
	doBattle()
