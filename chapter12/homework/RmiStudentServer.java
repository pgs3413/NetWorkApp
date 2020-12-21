package chapter12.homework;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.RmiKitService;

public class RmiStudentServer {

	public static void main(String[] args) {
		try {
			Registry registry=LocateRegistry.createRegistry(1099);
			RmiKitService rmiKitService=new RmiKitServiceImpl();
			registry.rebind("RmiKitService", rmiKitService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
