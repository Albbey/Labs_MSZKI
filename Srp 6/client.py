import xmlrpc.client
import random
import math
import string


def hash(inp):
    _input = str(inp)
    return int(len(_input)%(math.exp(len(_input))))

def main():
    with xmlrpc.client.ServerProxy("http://localhost:7001/") as proxy:
        #multicall.getCourse(addr,code)
        g = 15
        k=3
        N=2*int(input('Prime number: '))+1
        s = ''.join(random.choice('qwertyuiopasdfghjklzxcvbnm') for _ in range(15))
        I = str(input('Enter USERNAME: '))
        p = str(input('Enter PASSWORD: '))
        print ('GENERATOR g = ', g)    
        x = hash(s+p)
        print('New X = ', x)
        v = (g^x)%N
        print ('New V = ', v)
        print('Send to Server ...')
        proxy.One(I,s,v,N)
        
        a= random.randint(1,10000)
        A=pow(g,a)%N   
        
        print('Send to Server I,A ...', I,A)
        tempst = str(proxy.Twoo(I,A))
        tempst = tempst.split('!')
        B=int(tempst[0])
        print ('Recieving from SERVER B =', B)
        u = hash(str(A)+str(B))
        print ('Calculating U = ',u)
        if u !=0:
            proxy.Three()
            S=(pow((B-k*((pow(g,x))%N)),(a+u*x)))%N
            K=hash(S)
            print('Calculating K=h(((B-k*(g^x mod N))^a+u*x) mod N = ',K)
        
        M = hash(str((hash(N)^hash(g)))+str(hash(I))+s+str(A)+str(B)+str(K))
        print('Calculating M=',M)
        if(hash(str(A)+str(M)+str(K))==proxy.Four(M)):
            print('Success!')
        else:
            print('Error!')
        
        
        
if __name__ =='__main__':
    main()
        
        