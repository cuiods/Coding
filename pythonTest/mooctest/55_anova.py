#-*- coding:utf-8 -*-
from scipy import stats
import numpy as np

def oneway_anova():
    A1=[27.0, 26.2, 28.8, 33.5, 28.8]
    A2=[22.8, 23.1, 27.7, 27.6, 24.0]
    A3=[21.9, 23.4, 20.1, 27.8, 19.3]
    A4=[23.5, 19.6, 23.7, 20.8, 23.9]

    A=[A1, A2, A3, A4]
    n=20
    As=np.sum(A, axis=1)
    QA=0.0
    for i in range(4):
        QA=QA+As[i]*As[i]
    QA=QA/5
    
    QT=0.0
    for i in range(4):
        for j in range(5):
            QT=QT+A[i][j]*A[i][j]
    
    C=np.sum(A)*np.sum(A)/n
    ST=QT-C
    SA=QA-C
    Se=ST-SA
    F=(SA/3)/(Se/16)
    
    print ('QA is ' + str(QA))
    print ('QT is ' + str(QT))
    print ('C is ' + str(C))
    print ('ST is ' + str(ST))
    print ('SA is ' + str(SA))
    print ('Se is ' + str(Se))
    print ('F is '+ str(F))

def twoway_anova():
    a=4 
    b=6
    A1 = [0.05, 0.46, 0.12, 0.16, 0.84,1.30]
    A2 = [0.08, 0.38, 0.40, 0.10, 0.92,1.57]
    A3 = [0.11, 0.43, 0.05, 0.10, 0.94,1.10]
    A4 = [0.11, 0.44, 0.08, 0.03, 0.93,1.15]

    A = [A1, A2, A3, A4]
    n = a*b
    As = np.sum(A, axis=1)
    Bs = []
    for i in range(b):
        Bs.append(A1[i]+A2[i]+A3[i]+A4[i])
    
    
    QA = 0.0
    for i in range(a):
        QA = QA + As[i] ** 2
    QA = QA / b
    QB = 0.0
    for i in range(b):
        QB = QB + Bs[i] ** 2
    QB = QB / a
    QT = 0.0
    for i in range(a):
        for j in range(b):
            QT = QT + A[i][j] ** 2
    
    C = np.sum(A) ** 2 / n
    ST = QT - C
    SA = QA - C
    SB = QB - C 
    Se = ST - SA - SB
    Va = SA / (a-1)
    Vb = SB / (b-1)
    Ve = Se/((a-1)*(b-1))
    Fa = Va/Ve
    Fb = Vb/Ve

    
    print('QA is ' + str(QA))
    print('QB is ' + str(QB))
    print('QT is ' + str(QT))
    print('C is ' + str(C))
    print('ST is ' + str(ST))
    print('SA is ' + str(SA))
    print('SB is ' + str(SB))
    print('Se is ' + str(Se))
    print('Fa is ' + str(Fa))
    print('Fb is ' + str(Fb))


#the code should not be changed
if __name__ == '__main__':
        oneway_anova()
        twoway_anova()
