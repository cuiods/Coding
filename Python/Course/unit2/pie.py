import matplotlib.pyplot as plt

labels = "Frogs","Hogs","Dogs","Logs"
sizes = [15,30,45,10]
colors = ['yellowgreen','gold','lightskyblue','lightcoral']
explode = (0,0.1,0,0)
plt.pie(sizes, explode=explode, labels=labels, colors=colors, autopct='%1.1f%%')
plt.axis('equal')
plt.show()