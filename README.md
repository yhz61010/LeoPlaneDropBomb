# LeoPlaneDropBomb

1. For Android, you need to add the following line in your `local.properties`

```
ndk.dir=/Users/yhz61010/Library/Android/sdk/ndk/21.1.6352462
```

2. Generate assets atlas first.

- Run command on project path:

```shell
./gradlew texturePacker
```

- or double click [Gradle -> Tasks -> other -> texturePacker] on the right side menu.