package ir.mywallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "ir.mywallet")
public class MyWalletBackApplication {
	public static void main(String[] args){
		SpringApplication.run(MyWalletBackApplication.class,args);
	}
}
