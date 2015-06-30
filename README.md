# GWT-Cloudinary

 A GWT wrapper for the Cloudinary's Upload Widget.

## Cloudinary

Cloudinary is a image back-end for web and mobile developers. More info at http://cloudinary.com/

It provides several APIs for uploading and fetching data, and the [Upload Widget](http://cloudinary.com/documentation/upload_widget) is the simple solution to manipulate, visualize and upload multiple files to the cloud, using a javascript library.

## GWT-Cloudinary

The project GWT-Cloudinary is the GWT wrapper for the Cloudinary's Upload Widget. By using it you have the power of the Upload Widget using a type-safe, IDE friendly GWT Widget and event handling. It is built with GWT 2.7.0 and with Java 7 syntax.

## Example

This is how you create a "Select image..." button in your UI:

```java
FlowPanel panel = new FlowPanel();
CloudinaryUploadWidget upload = new CloudinaryUploadWidget("Select image...");
upload.setCloudName("Your cloud name") //mandatory
	  .setUploadPreset("Your upload preset"); //mandatory
panel.add(upload);
```

You can set any property defined [here](http://cloudinary.com/documentation/upload_widget#upload_widget_options) in your CloudinaryUploadWidget. Here is how you define an upload with cropping at 1:1 aspect ratio:

```java
CloudinaryUploadWidget upload = new CloudinaryUploadWidget();
upload.setCloudName("Your cloud name") //mandatory
	  .setUploadPreset("Your upload preset") //mandatory
	  .setCropping("server")
	  .setCroppingAspectRatio(1.0)
	  .setText("Select and crop image...");
```

To set a custom CSS to your Upload Widget, using a file placed on you `public` folder, use:

```java
upload.setStylesheet(GWT.getModuleBaseForStaticFiles() + "cloudinary.css")
```

### Event handling

To receive events when the upload is done (either by error or by success), simply add a `CloudinaryUploadFinishedHandler`:

```java
CloudinaryUploadWidget upload = new CloudinaryUploadWidget();
upload.addCloudinaryUploadFinishedHandler(new CloudinaryUploadFinishedHandler() {
	@Override
	public void onUploadFinished(CloudinaryUploadFinishedEvent event) {
		if (event.isError()){
			// show error message or do something else
			GWT.log(event.getErrorMessage());
		}
		List<CloudinaryUploadInfo> infos = event.getUploadInfos();
		//infos is the List of uploaded files with their information, such as size, url, original file name and so on
	}
});
```

## Setup

Before anything, make sure you have a Cloudinary account, with a cloud name and upload preset at hand. More info here: http://cloudinary.com/documentation/upload_widget#setup

### Cloudinary javascript

Add the Cloudinary javscript to your hosting page:

```html
<script src="//widget.cloudinary.com/global/all.js" type="text/javascript"></script>
```

### Add the gwt-cloudinary to your classpath

Using Apache Maven:

```xml
<dependency>
	<groupId>com.github.gilberto-torrezan</groupId>
	<artifactId>gwt-cloudinary</artifactId>
	<version>1.0.1</version>
	<scope>provided</scope>
</dependency>
```

You can download the jar directly from [The Central Repository](http://search.maven.org/#search|gav|1|g%3A%22com.github.gilberto-torrezan%22%20AND%20a%3A%22gwt-cloudinary%22) as well - gwt-cloudinary only depends on GWT itself.

### GWT module

Add the gwtcloudinary module to your project.gwt.xml:

```xml
<inherits name="com.github.gilbertotorrezan.gwtcloudinary.gwtcloudinary"/>
```

## Javadoc

You can browse the project javadoc at javadoc.io:

[http://www.javadoc.io/doc/com.github.gilberto-torrezan/gwt-cloudinary](http://www.javadoc.io/doc/com.github.gilberto-torrezan/gwt-cloudinary)
	
## License

This project is licensed under the terms of The MIT License (MIT).