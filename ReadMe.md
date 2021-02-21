# User's Guide

This app is writen without using any IDE, so all the work of writing, compiling must be done manually.  
1. compile all file in [src](src) into a directory named classes, with the same order (ex: src/music/business/ ... into classes/music/business/... ) 
2. copy files into tomcat's webapps directory, the directory structure like below: 

```
tomcat
└── webapps
    └── musicStore
        ├── admin
        ├── cart
        ├── catalog
        ├── customer_service
        ├── email
        ├── images
        ├── incluses        
        ├── META-INF (content.xml file)
        ├── sound
        |   ├── 8601
        |   ├── jr01
        |   ├── pf01
        |   └── pf02
        ├── styles
        ├── catalog.jsp
        ├── index.jsp
        ├── login.jsp
        └── WEB-INF (web.xml file)
            ├── classes (the root directory for java classes)
            |   └── music
            |       ├── business
            |       ├── controller
            |       ├── data
            |       └── util
            └── lib (JAR files for Java class libraries)        

```
3. run tomcat (this application use tomcat 8.5)
4. access at [http://localhost:8080/musicStore](http://localhost:8080/musicStore)
