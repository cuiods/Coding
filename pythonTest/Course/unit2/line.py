import numpy as np
import matplotlib.pyplot as plt
x = np.linspace(-10,10,100)
y = np.random.normal(0,1,size=(100,))
plt.plot(x,y,color='red')
plt.show()
