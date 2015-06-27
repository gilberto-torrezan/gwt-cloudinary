/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 Gilberto Torrezan Filho
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */
package com.github.gilbertotorrezan.gwtcloudinary.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event triggered by the Upload Widget when the upload is done, eithei by error or success.
 * 
 * @author Gilberto Torrezan Filho
 *
 * @since v.1.0.0
 */
public class CloudinaryUploadFinishedEvent extends GwtEvent<CloudinaryUploadFinishedHandler> {

	private static Type<CloudinaryUploadFinishedHandler> TYPE;

	public static void fireUploadFinished(HasCloudinaryUploadFinishedHandlers source, List<CloudinaryUploadInfo> infos, String errorMessage, 
			JavaScriptObject nativeResult, JavaScriptObject nativeError) {
		if (TYPE != null) {
			CloudinaryUploadFinishedEvent event = new CloudinaryUploadFinishedEvent(infos, errorMessage, nativeResult, nativeError);
			source.fireEvent(event);
		}
	}
	
	public static Type<CloudinaryUploadFinishedHandler> getType() {
		if (TYPE == null) {
			TYPE = new Type<CloudinaryUploadFinishedHandler>();
		}
		return TYPE;
	}

	private final boolean error;
	private final String errorMessage;
	private final List<CloudinaryUploadInfo> uploadInfos;
	private final JavaScriptObject nativeResult;
	private final JavaScriptObject nativeError;

	protected CloudinaryUploadFinishedEvent(List<CloudinaryUploadInfo> infos, String errorMessage, JavaScriptObject nativeResult, JavaScriptObject nativeError) {
		this.error = errorMessage != null;
		this.errorMessage = errorMessage;
		this.nativeError = nativeError;
		this.nativeResult = nativeResult;
		uploadInfos = infos;
	}
	
	public JavaScriptObject getNativeError() {
		return nativeError;
	}
	
	public JavaScriptObject getNativeResult() {
		return nativeResult;
	}
	
	public boolean isError() {
		return error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public List<CloudinaryUploadInfo> getUploadInfos() {
		return uploadInfos;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public final Type<CloudinaryUploadFinishedHandler> getAssociatedType() {
		return (Type) TYPE;
	}

	@Override
	protected void dispatch(CloudinaryUploadFinishedHandler handler) {
		handler.onUploadFinished(this);
	}

}
