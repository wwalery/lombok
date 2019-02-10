## Configuration

[_(general configuration and options)_](../readme.md)

### `ARG gradle=5.1.1`

The gradle version to be used. Supported values:

- `5.1.1` (default)
- `4.10.2`
- `4.7`
- `4.2.1`

## Example build commands:

(To be executed from the `<lombokhome>/docker` directory)

```
docker build -t lombok-gradle-jdk11 -f gradle/Dockerfile .

docker build -t lombok-gradle-jdk11 --build-arg lombokjar=lombok-1.16.20.jar -f gradle/Dockerfile .
```

## Example run commands:

```
docker run -it lombok-gradle-jdk11

docker run --rm -it -v /<lombokhome>/dist/lombok.jar:/workspace/lombok.jar lombok-gradle-jdk11
```

## Example container commands:

```
gradle assemble
```
