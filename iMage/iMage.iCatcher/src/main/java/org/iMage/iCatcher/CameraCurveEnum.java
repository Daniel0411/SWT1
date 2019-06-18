package org.iMage.iCatcher;

public enum CameraCurveEnum {
	STANDARD_CURVE("Standard Curve"), LOADED_CURVE("Loaded Curve"), CALCULATED_CURVE("Calculated Curve");
	
	private String name;
	
	private CameraCurveEnum(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
