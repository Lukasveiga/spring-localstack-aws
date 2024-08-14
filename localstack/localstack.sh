# Parameterstore
aws --endpoint http://localhost:4566 --profile localstack ssm put-parameter --name "/config/spring-localstack_localstack/helloWorld" --value "Hello World Parameter Store" --type String
aws --endpoint http://localhost:4566 --profile localstack ssm put-parameter --name "/config/spring-localstack_localstack/sqsQueueName" --value "sqsHelloWorld" --type String
aws --endpoint http://localhost:4566 --profile localstack ssm put-parameter --name "/config/spring-localstack_localstack/snsNotificationName" --value "snsHelloWorld" --type String

# SQS
aws --endpoint http://localhost:4566 --profile localstack sqs create-queue --queue-name sqsHelloWorld
aws --endpoint http://localhost:4566 --profile localstack sqs send-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/sqsHelloWorld --message-body '{"content": "Hello World SQS!"}'

# SNS
aws --endpoint http://localhost:4566 --profile localstack sns create-topic --name snsHelloWorld
aws --endpoint http://localhost:4566 --profile localstack sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:snsHelloWorld --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:sqsHelloWorld

# S3
aws --endpoint http://localhost:4566 --profile localstack s3 mb s3://helloworld

