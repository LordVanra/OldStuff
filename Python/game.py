from random import randint,sample,random
from math import ceil
import sys
import json
class Player:
	def __init__(this,hp=100):
		this.hp=hp
		this.attr=0
		this.power=5
		this.hgr=-2
		this.comb=False
		this.inv={
			"Mine":[0,0],
			"Hunt":[0,0],
			"Visit Village":[0,0],
			"Raid Village":[0,0],
			"Smelt Ore":[0,0],
			"Other":[0],
		}
		this.invKey={
			"Mine":["Iron Ore","Copper Ore"],
			"Hunt":["Chicken(Raw)","Pork(raw)"],
			"Visit Village":["Corn","Carrots"],
			"Raid Village":["Bronze Coin", "Silver Coin"],
			"Smelt Ore":["Iron Ingot", "Copper Ingot"],
			"Other":["Stone"],
		}
		this.foodKey=[5,20,7,23]
		this.rep=[0]
		this.repKey=["Villagers"]
		this.herbs=[]
class Game():
	def __init__(this):
		this.plr=Player()
		this.sit="Plain"
		this.opt={
			"Plain":["Mine","Hunt","Visit Village","Pick Herbs"]
		}
		this.pairs=[]
		this.max=10
		this.refOpts=True
	def play(this):
		this.begSess()
		while True:
			print("\n\n")
			if this.plr.attr==15:
				this.plr.comb=True
				this.combat(this.plr.attr*6,this.plr.attr*2.5,this.plr.hp,this.plr.power)
			if this.plr.hgr>=50:
				this.plr.hp-=10
			this.plr.hgr+=2
			this.showUI()
			if this.refOpts:
				options=this.action()
			this.refOpts=True
			this.writeOpts(options)
			this.handleResp()
	def showUI(this):
		print("HP:"+str(this.plr.hp))
		print("Attraction:"+str(this.plr.attr))
		print("Hunger:"+str(this.plr.hgr))
		for i in range(len(this.plr.rep)):
			print("Reputation with "+this.plr.repKey[i]+": "+str(this.plr.rep[i]))
	def action(this):
		this.opts=0
		options=[]
		if this.sit=="Plain":
			opts=randint(-5,this.max)
			if opts<0:
				opts=0
			elif opts<5:
				opts=1
			elif opts<8:
				opts=2
			elif opts<10:
				opts=3
			elif opts>=10:
				opts=4
			options=[]
			li=[]
			for i in range(len(this.opt["Plain"])):
				li.append(i)
			v2=sample(li,k=opts)
			v2.sort()
			for i in v2:
				options.append(this.opt["Plain"][i])
			if "Visit Village" in options:
				options.append("Raid Village")
				if randint(1,2)==1:
					options.append("Smelt Ore")
			options.append("Continue")
			n=1
			this.pairs=[]
			for i in options:
				this.pairs.append([n,i])
				n+=1
			return options
	def writeOpts(this, options):
		print("Choose an option:")
		n=1
		for i in options:
			print(f"{n}. {i}")
			n+=1
		print("9. Show other options\n\n")
	def loop(this,list1,list2):
		for i in range(len(list1)):
			
			if i==0:
				sta1= str(list1[i])+" "+str(list2[i])
			if i==1:
				sta2= str(list1[i])+" "+str(list2[i])
		try:	
			return sta1+"\n"+sta2
		except UnboundLocalError:
			return sta1
	def eat(this):
		n=1
		v=[0,0,0,0]
		if this.plr.inv["Hunt"][0]!=0:
			print(str(n)+". Eat chicken -5 Hunger("+str(this.plr.inv["Hunt"][0])+")")
			v[0]=n
			n+=1
		if this.plr.inv["Hunt"][1]!=0:
			print(str(n)+". Eat pork -20 Hunger ("+str(this.plr.inv["Hunt"][1])+")")
			v[1]=n
			n+=1
		if this.plr.inv["Visit Village"][0]!=0:
			print(str(n)+". Eat corn -7 Hunger ("+str(this.plr.inv["Visit Village"][0])+")")
			v[2]=n
			n+=1
		if this.plr.inv["Visit Village"][1]!=0:
			print(str(n)+". Eat pork -23 Hunger ("+str(this.plr.inv["Visit Village"][1])+")")
			v[3]=n
			n+=1
		z=int(input())
		if z<=n:
			for i in range(4):
				if z==v[i]:
					n=1
					valid=False
					while not valid:
						print("How many would you like to eat?")
						ans=int(input())
						try:
							if ans<=this.plr.inv["Hunt"][i]:
								valid=True
						except IndexError:
							if ans<=this.plr.inv["Visit Village"][i-2]:
								valid=True
						if ans<=0:
							valid=False
					this.plr.hgr-=ans*n*this.plr.foodKey[i]
					this.plr.hgr-=3
					try:
						this.plr.inv["Hunt"][i]-=ans
					except IndexError:
						this.plr.inv["Visit Village"][i-2]-=ans
		else:
			print("Invalid input")
	def otherOpts(this):
		n=1
		v=[0,0,0,0,0]
		if (this.plr.inv["Hunt"]!=[0,0] or this.plr.inv["Visit Village"]!=[0,0]) and this.plr.hgr>0:
			v[0]=n
			print(str(n)+". Consume food")
			n+=1
		if this.plr.attr>0:
			print(str(n)+". Face the people who have been following you")	
			v[1]=n
			n+=1
		if this.plr.hp<100 and this.plr.herbs!=[]:
			print(str(n)+". Apply Medical Herbs")	
			v[2]=n
			n+=1
		print(str(n)+". Show Inventory")
		v[3]=n
		n+=1
		print(str(n)+". Break\n\n")
		v[4]=n
		passTest=False
		while not passTest:
			try:
				ans=int(input())
			except ValueError:
				print("Invalid Input")
			else:
				if ans<=n:
					for i in range(len(v)):
						if ans==v[i]:
							passTest=True
							if i==0:
								this.eat()
								this.refOpts=False
							if i==1:
								this.plr.comb=True
								this.combat(this.plr.attr*5,this.plr.attr*2//1,this.plr.hp,this.plr.power)
							if i==2:
								herbCount=this.plr.herbs[0][0]
								herbHeal=this.plr.herbs[0][1]
								this.plr.hp+=herbCount*herbHeal
								del this.plr.herbs[0]
								this.refOpts=False
							if i==3:
								this.showinv()
								this.refOpts=False
							if i==4:
								this.endSess()
				if passTest==False:
					print("Invalid Input")
	def handleResp(this):
		resp=input()
		try:
			resp=int(resp)
			a=this.pairs[resp-1]
		except ValueError:
			print("Invalid input")
		except IndexError:
			if resp==9:
				this.otherOpts()
			else:
				print("Invalid input")
		else:
			rn=randint(1,100)
			if a[1]=="Continue":
				this.plr.attr+=1;
			elif a[1]=="Mine":
				pass
			elif a[1]=="Visit Village":
				pass
			elif a[1]=="Smelt Ore":
				pass
			elif a[1]=="Hunt":
				pass
			elif a[1]=="Pick Herbs":
				pass
			elif a[1]=="Raid Village":
				pass
			elif rn<=80:
				this.plr.inv[a[1]][0]+=1
			elif rn<=100:
				this.plr.inv[a[1]][1]+=1
			if a[1]=="Raid Village":
				this.plr.rep[0]-=1
			if a[1]=="Visit Village":
				this.visitVillage()
			if a[1]=="Smelt Ore":
				this.smelt()
			if a[1]=="Hunt":
				this.hunt()
			if a[1]=="Mine":
				this.mine()
			if a[1]=="Pick Herbs":
				this.herbs()
			if a[1]=="Raid Village":
				this.raidVillage()
	def showinv(this):
		print("Mining loot:\n" + this.loop(this.plr.inv["Mine"],this.plr.invKey["Mine"]))
		print("Hunting loot:\n" + this.loop(this.plr.inv["Hunt"],this.plr.invKey["Hunt"]))
		print("Village Visiting loot:\n" + this.loop(this.plr.inv["Visit Village"],this.plr.invKey["Visit Village"]))
		print("Village Raiding loot:\n" + this.loop(this.plr.inv["Raid Village"],this.plr.invKey["Raid Village"]))
		print("Ore Smelting loot:\n" + this.loop(this.plr.inv["Smelt Ore"],this.plr.invKey["Smelt Ore"]))
		print("Other:\n" + this.loop(this.plr.inv["Other"],this.plr.invKey["Other"]))
	def herbs(this):
		this.plr.herbs.append([randint(1,4),randint(25,50)*0.1])
	def hunt(this):
		rn11=randint(1,4)
		rn12=randint(100-(25*rn11),100-(15*rn11))
		rn13=randint(rn11*15+10,40+(15*rn11))
		print("\n1. Hunt herd one")
		print("Meat gained: "+str(rn11)+"\nSuccess Chance: "+str(rn12)+"\nInjury Chance: "+str(rn13))
		rn21=randint(1,4)
		rn22=randint(100-(25*rn21),100-(15*rn21))
		rn23=randint(rn21*15+10,40+(15*rn21))
		print("\n2. Hunt herd two")
		print("Meat gained: "+str(rn21)+"\nSuccess Chance: "+str(rn22)+"\nInjury Chance: "+str(rn23))
		rn31=randint(1,4)
		rn32=randint(100-(25*rn31),100-(15*rn31))
		rn33=randint(rn31*15+10,40+(15*rn31))
		print("\n3. Hunt herd three")
		print("Meat gained: "+str(rn31)+"\nSuccess Chance: "+str(rn32)+"\nInjury Chance: "+str(rn33))
		rnarr=[[rn11,rn12,rn13],[rn21,rn22,rn23],[rn31,rn32,rn33]]
		ans=0
		while ans not in [1,2,3]:
			ans=int(input())
			if ans not in [1,2,3]:
				print("Invalid Input")
		rnt1=randint(1,100)
		rnt2=randint(1,100)
		rnt3=randint(1,100)
		rnans=rnarr[ans-1]
		success=False
		injury=False
		if rnt1<=rnans[1]:
			success=True
		if rnt2<=rnans[2]:
			injury=True
		if injury:
			this.plr.hp-=randint(5,12)
			print("You were injured")
		if success:
			print("Success")
			if rnt3<=90:
				this.plr.inv["Hunt"][0]+=rnans[0]
			elif rnt3<=100:
				this.plr.inv["Hunt"][1]+=rnans[0]
		else:
			print("Failure")
	def mine(this):
		rn11=randint(1,4)
		rn12=randint(100-(25*rn11),100-(15*rn11))
		rn13=randint(rn11*15+10,40+(15*rn11))
		print("\n1. Mine cave one")
		print("Ore gained: "+str(rn11)+"\nSuccess Chance: "+str(rn12)+"\nInjury Chance: "+str(rn13))
		rn21=randint(1,4)
		rn22=randint(100-(25*rn21),100-(15*rn21))
		rn23=randint(rn21*15+10,40+(15*rn21))
		print("\n2. Mine cave two")
		print("Ore gained: "+str(rn21)+"\nSuccess Chance: "+str(rn22)+"\nInjury Chance: "+str(rn23))
		rn31=randint(1,4)
		rn32=randint(100-(25*rn31),100-(15*rn31))
		rn33=randint(rn31*15+10,40+(15*rn31))
		print("\n3. Mine cave three")
		print("Ore gained: "+str(rn31)+"\nSuccess Chance: "+str(rn32)+"\nInjury Chance: "+str(rn33))
		rnarr=[[rn11,rn12,rn13],[rn21,rn22,rn23],[rn31,rn32,rn33]]
		ans=0
		while ans not in [1,2,3]:
			ans=int(input())
			if ans not in [1,2,3]:
				print("Invalid Input")
		rnt1=randint(1,100)
		rnt2=randint(1,100)
		rnt3=randint(1,100)
		rnans=rnarr[ans-1]
		success=False
		injury=False
		if rnt1<=rnans[1]:
			success=True
		if rnt2<=rnans[2]:
			injury=True
		if injury:
			this.plr.hp-=randint(7,15)
			print("You were injured")
		if success:
			print("Success")
			if rnt3<=80:
				this.plr.inv["Mine"][0]+=rnans[0]
			elif rnt3<=100:
				this.plr.inv["Mine"][1]+=rnans[0]
		else:
			print("Failure")
	def smelt(this):
		if this.plr.inv["Mine"]==[0,0]:
			print("Not enough ore")
		else:
			for i in range(len(this.plr.inv["Mine"])):
				n=this.plr.inv["Mine"][i]
				if n>0:
					print("How many "+ str(this.plr.invKey["Mine"][i].lower())+" would you like to smelt? Max: "+str(n))
					o=False
					while o==False:
						m=int(input())
						if m>n:
							print("Not Enough Ore")
						else:
							this.plr.inv["Mine"][i]-=m
							this.plr.inv["Smelt Ore"][i]+=m
							this.plr.inv["Other"][0]+=m
							o=True
	def visitVillage(this):
		n=1
		if this.plr.rep[0]<0:
			n=1.15**this.plr.rep[0]
		if n>random():
			rn=randint(1,100)
			if rn<=80:
				this.plr.inv["Visit Village"][0]+=1
			elif rn<=100:
				this.plr.inv["Visit Village"][1]+=1
		else:
			print("The villagers know your bad reputation and refuse to give you anything")
			print("1. Punish the villagers")
			print("2. Leave them be")
			gn=False
			while gn==False:
				ans=int(input())
				if ans!=1 and ans!=2:
					print("invalid Input")
				elif ans==1:
					gn=True
					this.plr.rep[0]-=2
					rn=randint(1,100)
					rn2=randint(1,2)
					if rn<=40:
						if rn2==1:
							this.plr.inv["Visit Village"][0]+=1
							print("You stole a corn")
						else:
							this.plr.inv["Raid Village"][0]+=1
							print("You stole a bronze coin")
					elif rn<=70:
						this.plr.inv["Visit Village"][0]+=1
						this.plr.inv["Raid Village"][0]+=1
						print("You stole a corn and a bronze coin")
					elif rn<=90:
						if rn2==1:
							this.plr.inv["Visit Village"][1]+=1
							this.plr.inv["Raid Village"][0]+=1
							print("You stole a carrot and a bronze coin")
						else:
							this.plr.inv["Visit Village"][0]+=1
							this.plr.inv["Raid Village"][1]+=1
							print("You stole a corn and a silver coin")
					elif rn<=95:
						this.plr.inv["Visit Village"][1]+=1
						this.plr.inv["Raid Village"][1]+=1
						print("You stole a carrot and a silver coin")
	def raidVillage(this):
		rn=randint(10,100)
		diff=ceil((rn/this.plr.power))
		rn1=randint(1,40)/2
		rn2=rn1+2
		print(f"Random {rn} Diff: {diff}  {rn1}  {rn2}")
		if rn2<diff:
			print("You were injured in the raid");
			this.plr.hp-=randint(2,5)
		if rn1<diff:
			print("Raid Failed")
			this.plr.rep[0]-=1
			this.plr.hp-=randint(4,7)
		else:
			validans=False
			print("Raid Successful")
			print("1. Steal all valuables, raid the farm, and slaughter the villagers")
			print("2. Steal all valuables and raid the farm")
			print("3. Steal all valuables")
			print("4. Steal half of the valuables")
			while not validans:
				ans=input()
				try:
					ans=int(ans)
				except TypeError:
					print("Invalid answaser")
				if(ans<5):
					validans=True;
				else:
					print("Invalid answer")
	def combat(this,eHP,ePow,pHP,pPow):
		while this.plr.comb:
			emove=randint(1,3)
			print("Choose a combat move:\n1. Attack\n2. Deflect\n3. Feint")
			pmove=int(input())
			if pmove==emove:
				r=randint(1,2)
				if (r==1):
					emove=randint(1,3)
			if (pmove==3 and emove==2) or (pmove==1 and emove==3) or (pmove==2 and emove==1):
				print("Player strikes")
				eHP-=pPow
			elif pmove==emove:
				print("No one strikes")
			else:
				print("Enemy Strikes")
				pHP-=ePow
			this.plr.hp=pHP
			if eHP<=0:
				this.plr.comb=False
				this.plr.attr=0
				print("Player Won!")
			elif pHP<=0:
				this.plr.comb=False
				this.plr.attr=0
				print("Bandits Won")
			else:
				print(f"Enemy HP={eHP}\nPlayer HP={pHP}")		
	def endSess(this):
		saveData={
			"sAttr":this.plr.attr,
			"sInv":this.plr.inv,
			"sHP":this.plr.hp,
			"sRep":this.plr.rep
		}
		with open('jfile.json','w') as fo:
			json.dump(saveData,fo);
			print("Data saved, closing program")
			sys.exit()			
	def begSess(this):
		with open('jfile.json') as fo:
			saveData=json.load(fo)
			re=input("Would you like to use old save data (y/n) ")
			if re=='y':
				this.plr.attr=saveData['sAttr']
				this.plr.inv=saveData['sInv']
				this.plr.hp=saveData['sHP']
				this.plr.rep=saveData['sRep']
			elif re=="n":
				this.reqBonus()
	def reqBonus(this):
		bc=input("Would you like a boost (y/n) ")
		if bc=='y':
			this.plr.inv={
				"Mine":[5,1],
				"Hunt":[5,1],
				"Visit Village":[5,1],
				"Raid Village":[5,1],
				"Smelt Ore":[5,1],
				"Other":[4]
			}
run=Game()
run.play()