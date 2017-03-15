# Winsbroker-play Project

## Steps to run this project

---

### **Install sbt (version 0.13.13)**
##### [Download sbt from here](http://www.scala-sbt.org/download.html)
##### *Download zip if you using window os*

---

### **Change directory to your project folder**
##### Example: siggy@siggy: ```cd develop/java-play-project```

---

### **Start sbt and compile project**
##### Now you are in ```develop/java-play-project``` folder then you type ```sbt``` and waiting for download (take long time..) then everything finished, type ```~run``` for start server..

### **We using port 9000**
##### Then you can go ```localhost:9000```

---

## Config MySql

---

### **Go to conf/application.conf**
```
Winsbroker-project-folder
|
└─── app
|   |
|   └─── ...
|
└─── conf
|   |
|   └─── application.conf
|
└─── logs
|   |
|   └─── ...
|
└─── project
|   |
|   └─── ...
|
└─── public
|   |
|   └─── ...
└─── target
|   |
|   └─── ...
└─── test
|   |
|   └─── ...
└─── .gitignore
|
└─── .travis.yml
|
└─── build.sbt
|
└─── LICENSE
|
└─── README.md
```

And config database name, database url, username, password like this (at bottom file) :
```
db {
  default.driver=com.mysql.jdbc.Driver
  default.url="jdbc:mysql://localhost/finance?useSSL=false"
  default.username=root
  default.password="1234"
}
```
