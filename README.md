PlayMorphia Play 2.8.x Module
=====================================

This is a Play 2.7.x, 2.8.x Module for [Morphia](https://github.com/MorphiaOrg/morphia)
(a MongoDB Java driver wrapper).

Installation
-----------

Add the following to your build.sbt:
    
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

    libraryDependencies ++= Seq(
        guice,
        "org.mongodb" % "mongo-java-driver" % "3.12.0",
        "dev.morphia.morphia" % "core" % "1.5.8",
        "io.github.ducthienbui97" % "play-morphia" % "{version}"
    )


| Play version | Play-morphia version  |
|---|---|
|  2.7.x | 1.0.2-2.7 |
|  2.8.x | 1.0.2-2.8 |

You will need to specify your MongoDB configuration in the `conf/application.conf`file:

    playmorphia {
        uri="mongodb://127.0.0.1:27017/"
        database="YourDB"
        models="models"
        mongoClientFactory="controllers.mongoConfiguration.MyMongoClientFactory"
    }


#### MongoClient Factory

Not all MongoClient options are supported by the MongoClientURI.  For full customization of the generated MongoClient, you can specify your own MongoClientFactory class in the `playmorphia` section of the `conf/application.conf`, like this:

    mongoClientFactory="controllers.mongoConfiguration.MyMongoClientFactory"

(I created a package inside `controllers` named `mongoConfiguration`)

The value should be the name of a class that extends from `it.unifi.cerm.playmorphia.MongoClientFactory` and provide at least an empty constructor or a constructor that takes a play Configuration.  For example:

```java
package controllers.mongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.typesafe.config.Config;
import it.unifi.cerm.playmorphia.MongoClientFactory;

import java.util.Arrays;

public class MyMongoClientFactory  extends MongoClientFactory {

    private Config config;

    public MyMongoClientFactory(Config config) {
        super(config);
        this.config = config;
    }

    public MongoClient createClient() throws Exception {
         return new MongoClient(Arrays.asList(
                 new ServerAddress("localhost", 27017)
                 )
         );
     }

    public String getDBName() {
        return config.getString("playmorphia.database");
    }

}
```

Usage
-----

**Play Framework 2.8.x**

A way to use PlayMorphia is to create a repositories package containing repository classes, one for each model. A repository class contains all methods to access to the collection members.
The package structure should be similar to the following:
```
|- controllers
|- models
|- repositories
```

Model example:

```java

import org.bson.types.ObjectId;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity(value = "DB.users")
public class User  {

    @Id
    private ObjectId _id;
    private String firstname;
    private String lastname;
    private String email;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

Repository example:

```java
package repositories;
       

import it.unifi.cerm.playmorphia.PlayMorphia;
import models.User;
import org.bson.types.ObjectId;

import javax.inject.Inject;

public class UserRepository {

    private final PlayMorphia morphia;

    @Inject
    public UserRepository(PlayMorphia morphia) {
        this.morphia = morphia;
    }

    public User findById(String id) {
        User user = morphia.
                datastore().
                createQuery(User.class).
                field("_id").
                equal(new ObjectId(id)).
                get();
        return user;
    }

    public void save(User u) {
        morphia.datastore().save(u);
    }
}
```

Controller example:

```java
import views.html.modifyUserView;

public class UserController extends Controller {

    @Inject
    private UserRepository user;

    public Result modifyUser(String id) {
        User u = user.findById(id);
        return ok(modifyUserView.render(u));
    }
}
```


Contact
-------

If you have a question or need some help you can just [open an issue](https://github.com/morellik/play-morphia/issues). 

License
-------

The license is Apache 2.0, see LICENSE.txt.
