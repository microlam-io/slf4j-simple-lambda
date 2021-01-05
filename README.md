# slf4j-simple-lambda
Fork of slf4j-simple for logging from AWS Lambda

# The problem

It is very difficult to log from a Java Lambda and to be able to also see logs from AWS Sdk 2.
The problem is even worst when we need to compile natively using graalVM native-image.

# The solution

## Features
* fork from slf4j-simple
* Allow logging from your code via slf4j
* Allow logging from AWS Sdk via log4j-over-slf4j
* graalVM native-image friendly
* Support for AWSRequestId
* Allow env properties with underscore instead of point to be compatible with AWS lambda env property naming rules 

## Maven configuration

With property:
```
	<properties>
		<slf4j.version>2.0.0-alpha1</slf4j.version>
	</properties>

```

And dependencies:

```
<dependency>
    <groupId>io.microlam</groupId>
    <artifactId>slf4j-simple-lambda</artifactId>
    <version>${slf4j.version}</version>
</dependency>

<dependency> 
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>${slf4j.version}</version>
</dependency>
 
<dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-lambda-java-core</artifactId>
    <version>1.2.0</version>
</dependency>

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>log4j-over-slf4j</artifactId>
    <version>2.0.0-alpha1</version>
</dependency>
```

Be sure to exclude dependencies to avoid conflicts, for example:

```
<dependency>
  <groupId>software.amazon.awssdk</groupId>
  <artifactId>dynamodb</artifactId>
      <exclusions>
      <exclusion>
          <groupId>org.slf4j</groupId>
          <artifactId>slf4j-api</artifactId>
      </exclusion>
  </exclusions>  
</dependency>
```    

## Configuration: simplelogger.properties

```
# # SLF4J's SimpleLogger configuration file
# Simple implementation of Logger that sends all enabled log messages, for all defined loggers, to Lambda logger or System.err.
# Must be one of ("System.err", "System.out", "LAMBDA", or custom file path).
#org.slf4j.simpleLogger.logFile=LAMBDA

# Default logging detail level for all instances of SimpleLogger.
# Must be one of ("trace", "debug", "info", "warn", or "error").
# If not specified, defaults to "info".
#org.slf4j.simpleLogger.defaultLogLevel=info

# Logging detail level for a SimpleLogger instance named "xxxxx".
# Must be one of ("trace", "debug", "info", "warn", or "error").
# If not specified, the default logging detail level is used.
#org.slf4j.simpleLogger.log.xxxxx=
#Example for AWS Sdk 2 logging:
#org.slf4j.simpleLogger.log.software.amazon.awssdk=warn
#org.slf4j.simpleLogger.log.software.amazon.awssdk.request=debug

# Set to true if you want the current date and time to be included in output messages.
# Default is false, and will output the number of milliseconds elapsed since startup.
#org.slf4j.simpleLogger.showDateTime=false

# The date and time format to be used in the output messages.
# The pattern describing the date and time format is the same that is used in java.text.SimpleDateFormat.
# If the format is not specified or is invalid, the default format is used.
# The default format is yyyy-MM-dd HH:mm:ss:SSS Z.
#org.slf4j.simpleLogger.dateTimeFormat=yyyy-MM-dd HH:mm:ss:SSS Z

# Set to true if you want to output the current aws request id.
# Defaults to true but really applicable only when org.slf4j.simpleLogger.logFile=LAMBDA.
#org.slf4j.simpleLogger.showAWSRequestId=true

# Set to true if you want to output the current thread name.
# Defaults to true.
#org.slf4j.simpleLogger.showThreadName=true

# Set to true if you want the Logger instance name to be included in output messages.
# Defaults to true.
#org.slf4j.simpleLogger.showLogName=true

# Set to true if you want the last component of the name to be included in output messages.
# Defaults to false.
#org.slf4j.simpleLogger.showShortLogName=false
```

