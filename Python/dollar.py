import math
pr=487909.68
pr00=pr
y=0.0407

pr0=pr*(1+(y/365))**31
print(f"July has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**31
print(f"August has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**30
print(f"September has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**31
print(f"October has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**30
print(f"November has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**31
print(f"December has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**31
print(f"January has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
prny=pr
pr0=pr*(1+(y/365))**29
print(f"February has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**31
print(f"March has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**30
print(f"April has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**31
print(f"May has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0
pr0=pr*(1+(y/365))**30
print(f"June has a original principal of {round(pr,2)} and a new principal of {round(pr0,2)}")
pr=pr0


print(f"\nOverall profit = {round(pr-pr00,2)}\nAverage monthly profit = {round((pr-pr00)/12,2)}")
print(f"\nNext year's profit = {round((prny*(1+(y/365))**365)-prny,2)}")
print(f"Next decade's profit = {round((prny*(1+(y/365))**365)-prny,2)}")

print(math.factorial(20758))

