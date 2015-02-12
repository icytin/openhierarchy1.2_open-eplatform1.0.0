package se.unlogic.hierarchy.core.validationerrors;

import se.unlogic.standardutils.validation.ValidationError;
import se.unlogic.standardutils.validation.ValidationErrorType;
import se.unlogic.standardutils.xml.XMLElement;

@XMLElement(name = "validationError")
public class TooLongContentValidationError extends ValidationError {

	@XMLElement
	private final int currentLength;

	@XMLElement
	private final int maxLength;

	public TooLongContentValidationError(int currentLength, int maxLength) {

		super("TooLongFieldContent");
		this.currentLength = currentLength;
		this.maxLength = maxLength;
	}

	public TooLongContentValidationError(String fieldName,int currentLength, int maxLength) {

		super(fieldName, ValidationErrorType.TooLong);
		this.currentLength = currentLength;
		this.maxLength = maxLength;
	}

	public int getMaxLength() {

		return maxLength;
	}


	public int getCurrentLength() {

		return currentLength;
	}
}
