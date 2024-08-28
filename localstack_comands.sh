#create table dynamodb
aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name franchise \
  --attribute-definitions \
      AttributeName=name,AttributeType=S \
  --key-schema \
      AttributeName=name,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5


aws --endpoint-url=http://localhost:4566 dynamodb scan --table-name franchise
