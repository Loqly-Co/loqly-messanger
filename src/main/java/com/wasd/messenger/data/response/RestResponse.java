package com.wasd.messenger.data.response;

import com.wasd.messenger.keys.ResponseStatus;

public record RestResponse<D>(ResponseStatus status, D data, String message) {

	public static <D> RestResponse<D> success(D data) {
		return RestResponse.of(ResponseStatus.SUCCESS, data, "Success");
	}
	
	public static <D> RestResponse<D> success(D data, String message) {
		return RestResponse.of(ResponseStatus.SUCCESS, data, message);
	}
	
	public static <D> RestResponse<D> failure(D data) {
		return RestResponse.of(ResponseStatus.FAILURE, data, "Failure");
	}
	
	public static <D> RestResponse<D> failure(D data, String message) {
		return RestResponse.of(ResponseStatus.FAILURE, data, message);
	}

	private static <D> RestResponse<D> of(ResponseStatus status, D data, String message) {
		return new RestResponse<>(status, data, message);
	}
}