package chapter10;

public class TestSplit {
public static void main(String[] args) {
//	String s="你好 中国    我爱你";
//	String[] list=s.split("\\s+");
//	for(String str:list) {
//		System.out.println(str);
//	}
String s="aa:bb:cc:dd";
String[] list=s.split("[-:]"); 
//System.out.println(s.toUpperCase());
for(String str:list) {
System.out.println(str);
}

}
}
