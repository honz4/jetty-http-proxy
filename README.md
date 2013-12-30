# jetty-http-proxy

An example project showing basic usage of jetty's ProxyServlet. This project
creates an HTTP proxy servlet. The servlet listens for requests and logs the
response content to the console (System.out).

## Usage

To run the project on port 8080, just use [maven](http://maven.apache.org):

```Shell
mvn jetty:run
```

You will need to configure your client to connect to this proxy server.

### Connecting an iOS device to the proxy

For iOS devices, this can be done by going to Settings > WiFi > (select your wifi
network) and turning the HTTP Proxy option to Manual. Enter the server's IP
address and port 8080. After exiting Settings, your traffic will be directed
through the proxy and you will see all response content logged to the console.
