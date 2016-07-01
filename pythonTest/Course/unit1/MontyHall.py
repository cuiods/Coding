import random

def MontyHall(Dselect, Dchange):
    Dcar = random.randint(1,3)
    if Dcar == Dselect and Dchange == 0:
        return 1
    elif Dcar == Dselect and Dchange == 1:
        return 0
    elif Dcar != Dselect and Dchange == 0:
        return 0
    else:
        return 1
    
n = 10000
win = 0
for i in range(n):
    Dselect = random.randint(1,3)
    Dchange = random.randint(0,1)
    win+=MontyHall(Dselect, Dchange)
print float(win)/float(n)

n = 10000
win = 0
for i in range(n):
    Dselect = random.randint(1,3)
    Dchange = 0
    win+=MontyHall(Dselect, Dchange)
print float(win)/float(n)

n = 10000
win = 0
for i in range(n):
    Dselect = random.randint(1,3)
    Dchange = 1
    win+=MontyHall(Dselect, Dchange)
print float(win)/float(n)