```
docker run -p 8888:8888 -p 6006:6006 -it tensorflow/tensorflow:1.4.0-py3

docker cp MNIST_data/ 07c0388b2909:/MNIST_data

docker inspect 07c0388b2909 | grep IPAddress

```