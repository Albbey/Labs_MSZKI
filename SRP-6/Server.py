import socket
import random
import math
import time


def hash(inp):
    _input = str(inp)
    return int(len(_input)%(math.exp(len(_input))))
k = 3
g=11

sock = socket.socket()
sock.bind(('', 9090))
sock.listen(10)
print ("Server is ready!!!")
b=random.randint(1,1000)

try:
    while (1):
        
        conn,addr = sock.accept()
        
        print ("New connection from ..." + addr[0])
        #print (b)
        data = b""
        I1 = b""
        s1 = b""
        v1 = b""
        N1 = b""
        A1 = b""
        M1 = b""
        
        print ("__________________FIRST PHASE __________________")
        I = conn.recv(2048)
        
        print ("Recieving I...")
        time.sleep(1)
        s = conn.recv(2048)
        print ("Recieving s...")
        time.sleep(1)
        v = conn.recv(2048)
        print ("Recieving v...")
        time.sleep(1)
        N = conn.recv(2048)
        print ("Recieving N...")
        time.sleep(1)
        A = conn.recv(2048)
        print ("Recieving A...")
        time.sleep(1)
        
        if not I:
            break
        else:
            I1 += I
        uI = I1.decode("utf-8")
        print ("Have a I = " + uI)
       
        
        if not s:
            break
        else:
            s1 +=s
        us = s1.decode('utf-8')
        
        print ("Have a s = " + us)
        
        
        if not v:
            break
        else:
            v1 +=v
        uv = int.from_bytes(v1, 'big')
        print ("Have a v = " + str(uv))
        
        
        if not N:
            break
        else:
            N1 +=N
        uN = int(N1.decode('utf-8'))
       # uN = int.from_bytes(N1, 'big')
        print ("Have a N = " + str(uN))
        print("I,s,v,N are recieved, well done!\n")
        
        
        if not A:
            break
        else:
            A1 += A
        uA = int.from_bytes(A1, 'big')
        print ("Have an A " + str(uA))
        if uA == 0:
            break
        else:
            print ("A != 0")
            
        
        
        
        
        B=(k*uv+pow(g,b)%uN)%uN
        #print(B)
        conn.send((B).to_bytes(10,byteorder='big'))
        
        print("Calculating and sending to client B = " +str(B))
        
        u=hash(str(uA)+str(B))
        S=((uA*(pow(uv,u)%uN))**b)%uN
        
        K=hash(S)
        print('Calculating K = h(((A*(pow(v,u)%N))**b)%N) = ',K)
        
        print ("__________________SECOND PHASE__________________")
        M=conn.recv(2048)
        if not M:
            break
        else:
            M1 += M
        uM = int.from_bytes(M1, 'big')
        print("Have a M = %d" % (uM))
        
        MS = hash(str((hash(N)^hash(g)))+str(hash(uI))+us+str(uA)+str(B)+str(K))
        print ("Calculating M = "+ str(MS))
        
        if MS != uM:
            break
        else:
            print ("M from Client = M from Server, well done!!!")
            RS = hash(str(uA)+str(MS)+str(K))
            print("Sending to Client R = "+str(RS))
            conn.send((RS).to_bytes(10,byteorder='big'))
            print("R send, Client is cheking...")
            
        
        conn.close()
      
       
finally: sock.close()
            

            
    
