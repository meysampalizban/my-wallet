package ir.mywallet.services.jalali;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDate;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JalaliService {
	
	private DateConverter dateConverter(){
		return new DateConverter();
	}
	
	
	public LocalDate convertJalaliToGregorian(@NotEmpty String date){
		String[] dateSplit = date.split("-");
		int year = Integer.valueOf(dateSplit[0]);
		int month = Integer.valueOf(dateSplit[1]);
		int day = Integer.valueOf(dateSplit[2]);
		return this.dateConverter().jalaliToGregorian(year,month,day);
	}
	
	public JalaliDate convertGregorianToJalali(@NotEmpty String date){
		String[] dateSplit = date.split("-");
		int year = Integer.valueOf(dateSplit[0]);
		int month = Integer.valueOf(dateSplit[1]);
		int day = Integer.valueOf(dateSplit[2]);
		return this.dateConverter().gregorianToJalali(year,month,day);
	}
}
