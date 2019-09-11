package ro.pss.asm.tutorials.spring.ui.model.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationStatus {
	private String result;
	private String name;
}
