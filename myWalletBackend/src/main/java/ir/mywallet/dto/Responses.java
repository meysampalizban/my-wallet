package ir.mywallet.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Responses {
	private Date timestamp;
	private int statusCode;
	private String statusType;
	private Map<String,List<Object>> messages;
}
