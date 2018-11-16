import xmlrpc.client
import random

def main():
    with xmlrpc.client.ServerProxy("http://localhost:7001/") as proxy:
        P = int(input('Enter long prime number P: '))
        Q = int(input('Enter long prime number Q: '))
        N = P*Q
        print('Calculated N=',N)
        f = (P-1)*(Q-1)
        print('Calculating f=',f)
        b=True
        while(b):
            b=False
            d = random.randint(1,f)
            for i in range(d):
                if(i==1)|(i==0):
                    continue
                if(f%i==0)&(d%i==0):
                    b=True
                    break
        e=0
        while(True):
            e = e+1
            if((e*d)%f==1)&(e!=d):
                break
            if(e>f):
                b=True
                while(b):
                    b=False
                    d = random.randint(1,f)
                    for i in range(d):
                        if(i==1)|(i==0):
                            continue
                        if(f%i==0)&(d%i==0):
                            b=True
                            break   
        print('Generating d=',d)
        print('Calculating e=',e) 
        m = str(input('Enter message (numbers only): '))
        c = []
        for i in range(len(m)):
            c.append(str((int(m[i])**d)%N))
        print('Encrypted message send to server!')
        print('Server responded with: ', end='')
        print(''.join(proxy.send(c,e,N)))
        
if __name__ =='__main__':
    main()      