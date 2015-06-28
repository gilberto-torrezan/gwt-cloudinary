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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.safehtml.client.HasSafeHtml;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;

/**
 * The GWT wrapper for the Cloudinary's Upload Widget. It is a {@link Composite} built around a GWT {@link Button}. 
 * 
 * @author Gilberto Torrezan Filho
 *
 * @since v.1.0.0
 * 
 * @see http://cloudinary.com/documentation/upload_widget
 */
public class CloudinaryUploadWidget extends Composite implements HasCloudinaryUploadFinishedHandlers, 
HasText, HasHTML, HasSafeHtml, HasEnabled {
	
	protected Button button;
	protected JSONObject options;
	
	public CloudinaryUploadWidget() {
		button = new Button();
		initialize();
	}
	
	public CloudinaryUploadWidget(String html) {
		button = new Button(html);
		initialize();
	}
	
	public CloudinaryUploadWidget(SafeHtml html) {
		button = new Button(html);
		initialize();
	}
	
	protected CloudinaryUploadWidget(Element elem) {
	    button = Button.wrap(elem);
	    initialize();
	}
	
	// executed after any constructor
	protected void initialize(){
		initWidget(button);
		options = new JSONObject();
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				openUploadWidget(options.getJavaScriptObject());
			}
		});
	}
	
	/**
	 * Opens the Cloudinary's Upload Widget in a IFrame. This methos is called automatically by the click handler on the button.
	 */
	public native void openUploadWidget(JavaScriptObject options)/*-{
		var widget = this;
		$wnd.cloudinary.openUploadWidget(options, 
  			function(error, result) {
  				console.log(error, result);
  				widget.@com.github.gilbertotorrezan.gwtcloudinary.client.CloudinaryUploadWidget::fireUploadFinished(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(error, result);
  			});
	}-*/;
	
	/**
	 * Fires the {@link CloudinaryUploadFinishedEvent} by using the native objects, converting them to {@link CloudinaryUploadInfo} objects.
	 */
	protected void fireUploadFinished(JavaScriptObject error, JavaScriptObject result){
		String message = null;
		List<CloudinaryUploadInfo> infos = new ArrayList<>();
		
		if (error != null){
			JSONObject obj = new JSONObject(error);
			message = getSafeString(obj.get("message"));
		}
		
		if (result != null) {
			JSONArray resultArray = new JSONArray(result);
			int size = resultArray.size();
			for (int i = 0; i< size; i++){
				JSONObject object = resultArray.get(i).isObject();
				
				CloudinaryUploadInfo info = new CloudinaryUploadInfo();
				info.setPublicId(getSafeString(object.get("public_id")));
				info.setSecureUrl(getSafeString(object.get("secure_url")));
				info.setThumbnailUrl(getSafeString(object.get("thumbnail_url")));
				info.setUrl(getSafeString(object.get("url")));
				info.setType(getSafeString(object.get("type")));
				info.setVersion(getSafeString(object.get("version")));
				info.setWidth(getSafeInteger(object.get("width")));
				info.setHeight(getSafeInteger(object.get("height")));
				info.setFormat(getSafeString(object.get("format")));
				info.setResourceType(getSafeString(object.get("resource_type")));
				info.setSignature(getSafeString(object.get("signature")));
				info.setBytes(getSafeInteger(object.get("bytes")));
				info.setOriginalFilename(getSafeString(object.get("original_filename")));
				info.setEtag(getSafeString(object.get("etag")));
				info.setPath(getSafeString(object.get("path")));
				info.setCreatedAt(getSafeString(object.get("created_at")));
				
				JSONValue tagsValue = object.get("tags");
				if (tagsValue != null && tagsValue.isArray() != null){
					JSONArray array = tagsValue.isArray();
					String[] tags = new String[array.size()]; 
					for (int j = 0; j< tags.length; j++){
						JSONValue v = array.get(j);
						tags[j] = getSafeString(v);
					}
					info.setTags(tags);
				}
				
				JSONValue coordinatesValue = object.get("coordinates");
				if (coordinatesValue != null && coordinatesValue.isObject() != null){
					JSONObject obj = coordinatesValue.isObject();
					
					JSONValue customValue = obj.get("custom");
					if (customValue != null && customValue.isArray() != null){
						JSONArray array = customValue.isArray();
						CloudinaryCoordinates[] coordinatesArray = new CloudinaryCoordinates[array.size()];
						for (int j = 0; j< coordinatesArray.length; j++){
							JSONValue value = array.get(j);
							if (value != null && value.isArray() != null && value.isArray().size() >= 4){
								JSONArray valueArray = value.isArray();
								coordinatesArray[j] = new CloudinaryCoordinates();
								coordinatesArray[j].setX(getSafeInteger(valueArray.get(0)));
								coordinatesArray[j].setY(getSafeInteger(valueArray.get(1)));
								coordinatesArray[j].setWidth(getSafeInteger(valueArray.get(2)));
								coordinatesArray[j].setHeight(getSafeInteger(valueArray.get(3)));								
							}
						}
						info.setCustomCoordinates(coordinatesArray);
					}
					
					JSONValue facesValue = obj.get("faces");
					if (facesValue == null || facesValue.isArray() == null){
						facesValue = obj.get("face");
					}
					if (facesValue != null && facesValue.isArray() != null){
						JSONArray array = facesValue.isArray();
						CloudinaryCoordinates[] coordinatesArray = new CloudinaryCoordinates[array.size()];
						for (int j = 0; j< coordinatesArray.length; j++){
							JSONValue value = array.get(j);
							if (value != null && value.isArray() != null && value.isArray().size() >= 4){
								JSONArray valueArray = value.isArray();
								coordinatesArray[j] = new CloudinaryCoordinates();
								coordinatesArray[j].setX(getSafeInteger(valueArray.get(0)));
								coordinatesArray[j].setY(getSafeInteger(valueArray.get(1)));
								coordinatesArray[j].setWidth(getSafeInteger(valueArray.get(2)));
								coordinatesArray[j].setHeight(getSafeInteger(valueArray.get(3)));								
							}
						}
						info.setFaceCoordinates(coordinatesArray);
					}
				}
				
				infos.add(info);
			}
		}
		
		CloudinaryUploadFinishedEvent.fireUploadFinished(this, infos, message, result, error);
	}
	
	private String getSafeString(JSONValue value){
		if (value == null){
			return null;
		}
		JSONString string = value.isString(); 
		if (string == null){
			return null;
		}
		return string.stringValue();
	}
	
	private Integer getSafeInteger(JSONValue value){
		if (value == null){
			return null;
		}
		JSONNumber number = value.isNumber();
		if (number == null){
			return null;
		}
		return (int) number.doubleValue();
	}

	/**
	 * Directly set the upload options.
	 */
	public CloudinaryUploadWidget setOptions(JSONObject options) {
		if (options == null){
			clearOptions();
		}
		else {
			this.options = options;			
		}
		return this;
	}

	/**
	 * @return The options set so far, never <code>null</code>.
	 */
	public JSONObject getOptions() {
		return options;
	}
	
	/**
	 * Clears all the options set so far, by creating a new {@link JSONObject} internally.
	 */
	public CloudinaryUploadWidget clearOptions(){
		options = new JSONObject();
		return this;
	}

	/**
	 * The cloud name of your Cloudinary's account. Can be set either globally using setCloudName or explicitly for each widget creation call.
	 * 
	 * @param cloudName Mandatory string. Example: 'demo'
	 */
	public CloudinaryUploadWidget setCloudName(String cloudName) {
		options.put("cloud_name", cloudName == null ? null : new JSONString(cloudName));
		return this;
	}

	/**
	 * The name of an unsigned upload preset defined for your Cloudinary account either through the Settings page or using the Admin API.
	 * 
	 * @param uploadPreset Mandatory string. Example: 'a5vxnzbp'
	 */
	public CloudinaryUploadWidget setUploadPreset(String uploadPreset) {
		options.put("upload_preset", uploadPreset == null ? null : new JSONString(uploadPreset));
		return this;
	}

	/**
	 * List of file sources that should be available as tabs of the widget's helper.
	 * Supported sources are: local files using selection or drag & drop, remote HTTP URL and webcam capturing. 
	 * Note: Camera is currently supported in all modern browsers, not including Internet Explorer and Desktop Safari.
	 * 
	 * @param sources Array of strings: local, url, camera. Default: ['local', 'url', 'camera']
	 */
	public CloudinaryUploadWidget setSources(String[] sources) {
		if (sources != null){
			JSONArray array = new JSONArray();
			for (int i = 0; i< sources.length; i++){
				array.set(i, new JSONString(sources[i]));
			}			
			options.put("sources", array);
		}
		else {
			options.put("sources", null);
		}
		return this;
	}

	/**
	 * The default selected source tab when the widget is opened.
	 * 
	 * @param defaultSource String. Default: local
	 */
	public CloudinaryUploadWidget setDefaultSource(String defaultSource) {
		options.put("default_source", defaultSource == null ? null : new JSONString(defaultSource));
		return this;
	}

	/**
	 *  Whether selecting and uploading multiple images is allowed. Completion callback is called only when all images complete uploading. 
	 *  Multiple hidden fields of image identifiers are created if set to true. If set to false, only a single image is allowed in any source.
	 *  
	 *  @param multiple Boolean. Default: true
	 */
	public CloudinaryUploadWidget setMultiple(Boolean multiple) {
		options.put("multiple", multiple == null ? null : JSONBoolean.getInstance(multiple));
		return this;
	}

	/**
	 * The maximum number of files allowed in multiple upload mode. If selecting or dragging more files, only the first max_images files will be uploaded.
	 * 
	 * @param maxFiles Integer. Default: null. Unlimited. Example: 10
	 */
	public CloudinaryUploadWidget setMaxFiles(Integer maxFiles) {
		options.put("max_files", maxFiles == null ? null : new JSONNumber(maxFiles));
		return this;
	}

	/**
	 * Whether to enable interactive cropping of images before uploading to Cloudinary. Interactive cropping allows users to mark the interesting part of images.
	 * The selected dimensions are sent as the custom_coordinates upload parameter of Cloudinary. 
	 * Setting gravity to custom when generating delivery URLs will focus on the marked region.
	 * Incoming cropping on the server-side can be applied by applying the crop mode with the custom gravity of in your upload preset.
	 * Enabling cropping forces single file uploading.
	 * 
	 *  @param cropping String. Cropping modes: 'server' Default: null. No cropping. Example: 'server' 
	 */
	public CloudinaryUploadWidget setCropping(String cropping) {
		options.put("cropping", cropping == null ? null : new JSONString(cropping));
		return this;
	}

	/**
	 * If specified, enforce the given aspect ratio on selected region when performing interactive cropping. Relevant only if cropping is enabled.
	 * The aspect ratio is defined as width/height. For example, 0.5 for portrait oriented rectangle or 1 for square.
	 * 
	 * @param croppingAspectRatio Decimal. Default: null. No constraint. Example: 0.5
	 */
	public CloudinaryUploadWidget setCroppingAspectRatio(Double croppingAspectRatio) {
		options.put("cropping_aspect_ratio", croppingAspectRatio == null ? null : new JSONNumber(croppingAspectRatio));
		return this;
	}


	/**
	 * Initialize the size of the cropping selection box to a different value than the default (0.9). Relevant only if the cropping feature is enabled.
	 * The cropping_default_selection_ratio value is calculated as a proportion of the image's size.
	 * 
	 * @param croppingDefaultSelectionRatio Decimal. Default: 0.9. Range: 0.1 to 1.0. Example: 0.75
	 */
	public CloudinaryUploadWidget setCroppingDefaultSelectionRatio(Double croppingDefaultSelectionRatio) {
		options.put("cropping_default_selection_ratio", croppingDefaultSelectionRatio == null ? null : new JSONNumber(croppingDefaultSelectionRatio));
		return this;
	}

	/**
	 * Custom public ID to assign to a single uploaded image.
	 * If not specified, either a randomly generated string or the original file-name is used as the public ID according to the unsigned upload preset.
	 * To ensure secure usage, overwriting previously uploaded images sharing the same public ID is prevented.
	 * 
	 * @param publicId String. Default: null. Example: 'profile_11002'
	 */
	public CloudinaryUploadWidget setPublicId(String publicId) {
		options.put("public_id", publicId == null ? null : new JSONString(publicId));
		return this;
	}


	/**
	 * Folder name for all uploaded images. Acts as the prefix of assigned public IDs.
	 * 
	 * @param folder String. Default: null. Example: 'user_photos'
	 */
	public CloudinaryUploadWidget setFolder(String folder) {
		options.put("folder", folder == null ? null : new JSONString(folder));
		return this;
	}


	/**
	 *  One or more tags to assign to the uploaded images.
	 *  
	 *  @param tags String or array of strings. Default: null. Example: '['users' 'content']
	 */
	public CloudinaryUploadWidget setTags(String[] tags) {
		if (tags != null){
			JSONArray array = new JSONArray();
			for (int i = 0; i< tags.length; i++){
				array.set(i, new JSONString(tags[i]));
			}			
			options.put("tags", array);
		}
		else {
			options.put("tags", null);
		}
		return this;
	}

	/**
	 * The resource type of the uploaded files. In default, both images and raw files are allowed.
	 * Setting to either 'raw' or 'image' forces only raw files or only images respectively.
	 * 
	 * @param resourceType String: 'auto', 'image', 'raw'. Default: 'auto' Example: 'image'
	 */
	public CloudinaryUploadWidget setResourceType(String resourceType) {
		options.put("resource_type", resourceType == null ? null : new JSONString(resourceType));
		return this;
	}

	/**
	 * Additional context metadata to attach to the uploaded images.
	 * 
	 * @param context Map of key-value pairs. Example: { alt: "my_alt", caption: "my_caption"}
	 */
	public CloudinaryUploadWidget setContext(JSONObject context) {
		options.put("context", context);
		return this;
	}

	/**
	 * Allows client-side validation of the uploaded files based on their file extensions. You can specify one or more image or raw file extensions.
	 * 
	 * @param clientAllowedFormats Array of file formats: png, jpg, gif, doc, xls, etc. Default: null. All formats allowed. Example: ["png","gif", "jpeg"]
	 */
	public CloudinaryUploadWidget setClientAllowedFormats(String[] clientAllowedFormats) {
		if (clientAllowedFormats != null){
			JSONArray array = new JSONArray();
			for (int i = 0; i< clientAllowedFormats.length; i++){
				array.set(i, new JSONString(clientAllowedFormats[i]));
			}			
			options.put("client_allowed_formats", array);
		}
		else {
			options.put("client_allowed_formats", null);
		}
		return this;
	}

	/**
	 * If specified, perform client side validation that prevents uploading files bigger than the given bytes size.
	 * 
	 * @param maxFileSize Integer. Number of bytes. Default: null. No size limit. Example: 130000
	 */
	public CloudinaryUploadWidget setMaxFileSize(Integer maxFileSize) {
		options.put("max_file_size", maxFileSize == null ? null : new JSONNumber(maxFileSize));
		return this;
	}

	/**
	 * If specified, client-side scale-down resizing takes place before uploading if the width of the selected file is bigger than the specified value.
	 * 
	 * @param maxImageWidth Integer. Number of pixels. Default: null. No resizing. Example: 2000
	 */
	public CloudinaryUploadWidget setMaxImageWidth(Integer maxImageWidth) {
		options.put("max_image_width", maxImageWidth == null ? null : new JSONNumber(maxImageWidth));
		return this;
	}

	/**
	 * If specified, client-side scale-down resizing takes place before uploading if the height of the selected file is bigger than the specified value.
	 * 
	 * @param maxImageHeight Integer. Number of pixels. Default: null. No resizing. Example: 2000
	 */
	public CloudinaryUploadWidget setMaxImageHeight(Integer maxImageHeight) {
		options.put("max_image_height", maxImageHeight == null ? null : new JSONNumber(maxImageHeight));
		return this;
	}

	/**
	 * The selector (CSS path) of the form, to which you would like to append hidden fields with the identifiers of the uploaded images.
	 * Implicitly set by default to the containing form of the given element when the widget is created using applyUploadWidget or $.fn.cloudinary_upload_widget.
	 * Note: Supported only if jQuery is loaded in your site.
	 * 
	 * @param form String. jQuery-style selector. Default: null Example: '#my_form'
	 */
	public CloudinaryUploadWidget setForm(String form) {
		options.put("form", form == null ? null : new JSONString(form));
		return this;
	}


	/**
	 * The name of the hidden field added to your form when image uploading is completed.
	 * Multiple hidden fields with the same name are created for multiple uploaded images.
	 * The name may include '[]' for supporting web frameworks such as Ruby on Rails.
	 * Note: Supported only if jQuery is loaded in your site.
	 * 
	 * @param fieldName String. Form field name. Default: 'image' Example: 'photo[]'
	 */
	public CloudinaryUploadWidget setFieldName(String fieldName) {
		options.put("field_name", fieldName == null ? null : new JSONString(fieldName));
		return this;
	}


	/**
	 * Selector (CSS path) of an HTML element that acts as the container for appending uploaded images thumbnails to.
	 * Implicitly set by default to the containing form of the given element when the widget is created using applyUploadWidget or $.fn.cloudinary_upload_widget.
	 * Note: Supported only if jQuery is loaded in your site.
	 * 
	 * @param thumbnails String. jQuery-style selector. Default: null Example: '.content .uploaded'
	 */
	public CloudinaryUploadWidget setThumbnails(String thumbnails) {
		options.put("thumbnails", thumbnails == null ? null : new JSONString(thumbnails));
		return this;
	}

	/**
	 * The Cloudinary transformation (image manipulation) to apply on uploaded images for embedding thumbnails in your site.
	 * Any resizing, cropping, effects and other Cloudinary transformation options can be applied by specifying a transformation string, a map of transformations parameters or an array of chained transformations.
	 * Thumbnails transformations can be eagerly generated during upload by defining a set of eager transformations in your defined upload preset.
	 * 
	 * @param thumbnailTransformation String, Map or Array of maps. Default: { width: 90, height: 60, crop: 'limit' } 
	 * Examples: { width: 200, height: 200, crop: 'fill' }[ {width: 200, height: 200, crop: 'fill'}, {effect: 'sepia'} ]
	 * "w_200"
	 */
	public CloudinaryUploadWidget setThumbnailTransformation(JSONObject thumbnailTransformation) {
		options.put("thumbnail_transformation", thumbnailTransformation);
		return this;
	}


	/**
	 * Allows overriding the default CSS class name of the upload button added to your site.
	 * Default CSS style is applied to the cloudinary-button class, that you can override using CSS directives.
	 * Alternatively, you can specify any class name that matches your website design.
	 * 
	 * @param buttonClass String. Default: 'cloudinary-button' Example: 'my_button'
	 */
	public CloudinaryUploadWidget setButtonClass(String buttonClass) {
		options.put("button_class", buttonClass == null ? null : new JSONString(buttonClass));
		return this;
	}

	/**
	 * Allows overriding the default caption of the upload button added to your site.
	 * 
	 * @param buttonCaption String. Default: 'Upload image' Example: 'Pick photo...'
	 */
	public CloudinaryUploadWidget setButtonCaption(String buttonCaption) {
		options.put("button_caption", buttonCaption == null ? null : new JSONString(buttonCaption));
		return this;
	}

	/**
	 * The name of a predefined widget theme. Widget behavior is the same for all themes, while look & feel changes.
	 * 
	 * @param theme String. Supported themes: 'default', 'white', 'minimal', 'purple'. Default: 'default' Example: 'white'
	 */
	public CloudinaryUploadWidget setTheme(String theme) {
		options.put("theme", theme == null ? null : new JSONString(theme));
		return this;
	}

	/**
	 * Advanced customization of the widget's look & feel.
	 * Allows overriding the widget theme's colors, fonts, icons and other elements by providing custom style definition.
	 * See the white and minimal themes as reference implementations.
	 * 
	 * @param stylesheet String. Either a URL of a CSS file or inline CSS styles. Default: null 
	 * Examples: 
	 * 'http://mydomain/widget_style.css'
	 * '//mydomain/widget_style.css'
	 * '#cloudinary-overlay { background-color: #a7a7a7; } #cloudinary-widget { background: #f0f0f0; }'
	 * 
	 */
	public CloudinaryUploadWidget setStylesheet(String stylesheet) {
		options.put("stylesheet", stylesheet == null ? null : new JSONString(stylesheet));
		return this;
	}

	/**
	 * Mainly for debug purposes. If set to true, the upload widget remains open when uploading is completed.
	 * 
	 * @param keepWidgetOpen Boolean. Default: false
	 */
	public CloudinaryUploadWidget setKeepWidgetOpen(Boolean keepWidgetOpen) {
		options.put("keep_widget_open", keepWidgetOpen == null ? null : JSONBoolean.getInstance(keepWidgetOpen));
		return this;
	}

	/**
	 * If set to false, the Powered By Cloudinary icon is not displayed.
	 * Note: Supported only for paid Cloudinary accounts and requires some time for cache expiration.
	 * 
	 * @param showPoweredBy Boolean. Default: true
	 */
	public CloudinaryUploadWidget setShowPoweredBy(Boolean showPoweredBy) {
		options.put("show_powered_by", showPoweredBy == null ? null : JSONBoolean.getInstance(showPoweredBy));
		return this;
	}

	@Override
	public HandlerRegistration addCloudinaryUploadFinishedHandler(CloudinaryUploadFinishedHandler handler) {
		return addHandler(handler, CloudinaryUploadFinishedEvent.getType());
	}

	@Override
	public String getHTML() {
		return button.getHTML();
	}

	@Override
	public void setHTML(String html) {
		button.setHTML(html);
	}

	@Override
	public void setText(String text) {
		button.setText(text);
	}
	
	@Override
	public String getText() {
		return button.getText();
	}

	@Override
	public boolean isEnabled() {
		return button.isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}

	@Override
	public void setHTML(SafeHtml html) {
		button.setHTML(html);
	}
}
