package chapter02;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class test {
public static void main(String[] args) {

	DecimalFormat formater = new DecimalFormat();
	double a=1024.12;
	formater.setMaximumFractionDigits(0);
	formater.setRoundingMode(RoundingMode.CEILING);
	System.out.println(formater.format(a));
	
}
}
