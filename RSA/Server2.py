from xmlrpc.server import SimpleXMLRPCServer
import random

def main():
    server = SimpleXMLRPCServer(('127.0.0.1', 7001))
    server.register_introspection_functions()
    server.register_multicall_functions()
    server.register_function(send)
    print("Server ready")
    server.serve_forever()
def send(mes,d,N):
    print('Got message=%s, e=%d and N=%d. Decoding..' %(mes,d,N))
    c=[]
    for i in range(len(mes)):
        c.append(str((int(mes[i])**d)%N))
    print('Sending client ',c)
    return c
main()