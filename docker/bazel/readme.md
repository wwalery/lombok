## Configuration

[_(general configuration and options)_](../readme.md)

### `ARG bazel=0.13.0`

The bazel version to be used. Supported values:

- `0.13.0` (default)

## Example build commands:

(To be executed from the `<lombokhome>/docker` directory)

```
docker build -t lombok-bazel-jdk10 -f bazel/Dockerfile .

docker build -t lombok-bazel-jdk10 --build-arg lombokjar=lombok-1.16.20.jar -f bazel/Dockerfile .
```

## Example run commands:

```
docker run -it lombok-bazel-jdk10

docker run --rm -it -v /<lombokhome>/dist/lombok.jar:/workspace/lombok.jar lombok-bazel-jdk10
```

## Example container commands:

```
bazel build //:ProjectRunner
```
