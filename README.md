# Orma for Android [![Circle CI](https://circleci.com/gh/gfx/Android-Orma/tree/master.svg?style=svg)](https://circleci.com/gh/gfx/Android-Orma/tree/master) [ ![Download](https://api.bintray.com/packages/gfx/maven/orma/images/download.svg) ](https://bintray.com/gfx/maven/orma/_latestVersion)

This is an **alpha** software and the interface will change until a stable version is released.

**DO NOT USE THIS LIBRARY IN PRODUCTION**.

# Install

```groovy
dependencies {
    apt 'com.github.android.orma:orma-processor:0.0.1'
    provided 'com.github.android.orma:orma-annotations:0.0.1'
    compile 'com.github.android.orma:orma:0.0.1'
}
```

# Synopsis

First, define model classes annotated with `@Table`, `@Column`, and `@PrimaryKey`.

```java
package com.github.gfx.android.orma.example;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import android.support.annotation.Nullable;

@Table
public class Todo {

    @PrimaryKey
    public long id;

    @Column(indexed = true)
    public String title;

    @Column
    @Nullable
    public String content;

    @Column
    public long createdTimeMillis;
}
```

Second, create a database handle `OrmaDatabase`, which is generated by `orma-processor`.

```java
OrmaDatabase orma = new OrmaDatabase(context, "orma.db");
```

Then, you can create, read, update and delete models.

```java
Todo todo = ...;

// create
orma.insertIntoTodo(todo);

// read
orma.selectFromTodo()
  .where("title = ?", "foo")
  .toList();

// update
orma.updateTodo()
  .where("title = ?", "foo")
  .content("a new content")
  .execute();

// delete
orma.deleteTodo()
  .where("title = ?", "foo")
  .execute();
```

(this document is working in progress.)

# Features

* Code generation with annotation processing
* No global database context
* Model classes are POJO

# Release Engineering

```shell
./gradlew bumpMajor # or bumpMinor / bumpPatch
./gradlew check bintrayUpload -PdryRun=true
./gradlew annotations:bintrayUpload processor:bintrayUpload library:bintrayUpload
```


# Author

FUJI Goro (gfx).

# License

The MIT License.
