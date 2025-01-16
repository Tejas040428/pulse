package com.cds.projectpulse.constant;

public enum Status {
	GOOD(0, ConstantUtil.GOOD), ATTENTION(1, ConstantUtil.ATTENTION), CRITICAL(2, ConstantUtil.CRITICAL);

	private final int code;
	private final String description;

	private Status(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public static Status getStatusFromValue(String value) {
		for (Status status : Status.values()) {
			if (status.description.equals(value)) {
				return status;
			}
		}
		return null;
	}

	public int getCode() {
		return this.code;
	}
}
