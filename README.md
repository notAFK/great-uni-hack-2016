![guh16logo](http://greatunihack.com/img/logo.png)
---

# (code name)Photo Merge ![winnerbadge](https://img.shields.io/badge/greatunihack2016-winner-a000a0.svg)
###### Making events great again.
###### Making Android great again.

### Short description
A web and mobile application that helps people at events put all their pictures together.

### Motivation
People take lots of photos at events, and then forget to share them. People attend events and would like to see all the pictures that were taken in there in one place. Would there be any way to automate this process and centralize it?

### Architecture
The event organiser regsiters and creates an Event on the website. A new folder is created for that event.
People download the Android App, start it, and go to the event. When they enter the area of the event, if the event is currently happening, they get a notification which takes them to a screen allowing them to insert a password of the event. If the password is correct, the auto-upload is enabled for that Event. 
The App looks out for new pictures taken and uploads them to the Server as soon as they are taken (it has a toggle for pausing this behaviour). 
The Server receives the pictures and stores them in the Event's folder.
When attendees leave the event area, or the event ends, the auto-upload is disabled.
All the uploaded photos can be accessed on the website, using the password given beforehand.

### Technichal
```app``` contains the Android Studio source files.


```server``` contains the Laravel web server files.

---
#### Devpost submission:
https://devpost.com/software/great-uni-hack-2016
