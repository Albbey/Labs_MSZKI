import socket
import math
import random
import time

def hash(inp):
    _input = str(inp)
    return int(len(_input)%(math.exp(len(_input))))

def int_to_bytes(value, length):
    result = []
    for i in range(0, length):
        result.append(value >> (i * 8) & 0xff)
    result.reverse()

    return result

conn = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
conn.connect(('localhost', 9090))
#conn.send(b"Hello! \n")
#data = b""

k = 3
g = 3
S1 = b""
B1 = b""
R1 = b""

N = int(input("Enter Prime number :  ") )

N1 = str(N)
N1 = bytes(N1,'utf-8')

s = ''.join([random.choice('qwertyuioplkjhgfdsazxcvbnm') for _ in range(random.randint(1,10))])
s1 = str(s)
s1 = bytes(s,'utf-8')
#print(s)
#print(s1)

I=str(input('Username (I): '))
Ia = bytes(I, 'utf-8')

p=str(input('Password: '))



print('Generator g = ', g)
x=hash(s+p)
print('x=',x)

v=(g^x)%N
v1 = (v).to_bytes(2,byteorder='big')
print('v=',v)

print("__________________FIRST PHASE__________________")
print('Sending to server I,s,v,N... ')
time.sleep(1)
conn.send(Ia)
time.sleep(1)
conn.send(s1)
time.sleep(1)
conn.send(v1)
time.sleep(1)
conn.send(N1)
print('Data send')

#22222222222222222222222222222222222222222222222222222

a=random.randint(1,100)
print('Generating a=',a)
A=pow(g,a)%N
print('Calculating A=g^a mod N=',A)
print('Sending to server I,A')
A1 = (A).to_bytes(2,byteorder='big')
conn.send(A1)
print ("Server cheking...")


B = conn.recv(2048)
#print('kek')

B1 +=B
uB = int.from_bytes(B1, 'big')
if B!=0:

    print("Have from server B = " +str(uB))
    print("B != 0")
else:
    print('Error!!!! B = 0')
    

u=hash(str(A)+str(uB))
print('Calculating U=h(A,B)=',u)
if u!=0:
    
    S=(pow((uB-k*((pow(g,x))%N)),(a+u*x)))%N
    K=hash(S)
    print('Calculating K=h(((B-k*(g^x mod N))^a+u*x) mod N = ',K)

print("__________________SECOND PHASE__________________")
M = hash(str((hash(N)^hash(g)))+str(hash(I))+s+str(A)+str(uB)+str(K))
print('Calculating M=',M)

conn.send((M).to_bytes(2,byteorder='big'))


RC = hash(str(A)+str(M)+str(K))

RS = conn.recv(2048)
R1 +=RS
uR = int.from_bytes(R1,'big')

if uR == RC:
    print("R from Server = R from Client, SUCSESS!!!!")
else:
    print("No way man!")

conn.close()





#mystring = 'kek'

#data = b""
#tmp = conn.recv(1024)
#while tmp:
#    data += tmp
#    tmp = conn.recv(1024) 
#conn.send(mystring.decode('utf-8'))
#print ("Send[1]: ", mystring)
#rer = conn.recv(1024)
#urer = rer.encode("utf-8")
#print (rer)

#conn.close()
#print ("molodez")

