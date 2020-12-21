package chapter12.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.HelloService;

public class HelloServer {
public static void main(String[] args) {
try {
	Registry registry = LocateRegistry.createRegistry(1099);
	HelloService helloService = new HelloServiceImpl("pan的远程服务");
	registry.rebind("helloService", helloService);
	System.out.println("发布了一个HelloService RMI远程服务");
}catch(Exception e) {
	e.printStackTrace();
}
}	
}
