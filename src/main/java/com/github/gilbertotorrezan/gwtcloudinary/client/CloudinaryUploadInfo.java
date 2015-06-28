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

import java.io.Serializable;

/**
 * This is a type-safe wrapper for the upload result object.
 * 
 * @author Gilberto Torrezan Filho
 *
 * @since v.1.0.0
 * 
 * @see CloudinaryUploadFinishedEvent
 */
public class CloudinaryUploadInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String url;
	private String secureUrl;
	private String thumbnailUrl;
	private String type;
	private String publicId;
	private String version;
	private Integer width;
	private Integer height;
	private String format;
	private String resourceType;
	private String signature;
	private Integer bytes;
	private String etag;
	private String originalFilename;
	private String path;
	private String createdAt;
	private String[] tags;
	private CloudinaryCoordinates[] customCoordinates;
	private CloudinaryCoordinates[] faceCoordinates;
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBytes() {
		return bytes;
	}

	public void setBytes(Integer bytes) {
		this.bytes = bytes;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSecureUrl() {
		return secureUrl;
	}

	public void setSecureUrl(String secureUrl) {
		this.secureUrl = secureUrl;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public String[] getTags() {
		return tags;
	}

	public CloudinaryCoordinates[] getCustomCoordinates() {
		return customCoordinates;
	}

	public void setCustomCoordinates(CloudinaryCoordinates[] customCoordinates) {
		this.customCoordinates = customCoordinates;
	}

	public CloudinaryCoordinates[] getFaceCoordinates() {
		return faceCoordinates;
	}

	public void setFaceCoordinates(CloudinaryCoordinates[] faceCoordinates) {
		this.faceCoordinates = faceCoordinates;
	}
}
