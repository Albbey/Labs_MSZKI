from xmlrpc.server import SimpleXMLRPCServer
import random
import math
from _socket import SOL_IP

k=3
g=155
I=''
s=''
v=0
N=0 
A=0

def hash(inp):
    _input = str(inp)
    return int(len(_input)%(math.exp(len(_input))))
def main():
    server = SimpleXMLRPCServer(('127.0.0.1', 7001))
    server.register_introspection_functions()
    server.register_multicall_functions()
    server.register_function(One)
    server.register_function(Twoo)
    server.register_function(Three)
    server.register_function(Four)
    print("Server ready")
    server.serve_forever()
    
def One(userName,sol,randomString,easyNumber):
 
    global I
    global s
    global v
    global N
    I = userName
    s = sol
    v = int(randomString)
    N = int(easyNumber)
    print('Transfer I,s,v,N is successful!!!')
    return True
def Twoo(userName,Aa):
    global I
    global s
    global v
    global N
    global A
    global B
    A=Aa
    b=random.randint(1,1000)
    B=k*v+pow(g,b)%N
    sr = str(B)+'!'+str(s)
    print('Got from client I and A; calculating b=%d and B=%f; sending data to client...' %(b,B))
    return (sr)
def Three():
    u=hash(str(A)+str(B))
    S=((A*(pow(v,u)%N))^B)%N
    global K
    K=hash(S)
    print('Calculating and sending to client K= ',K)
    return K
def Four(M):
    print('Got from client M ...')
    if M == hash(str((hash(N)^hash(g)))+str(hash(I))+s+str(A)+str(B)+str(K)):
        print('OK!')
        return hash(str(A)+str(M)+str(K))
    else:
        print('Not OK -_-')
        return -1
    
    
    
    
main()
    