#create table dynamodb
aws --endpoint-url=http://localhost:4566 dynamodb create-table \
  --table-name franchise \
  --attribute-definitions \
      AttributeName=name,AttributeType=S \
  --key-schema \
      AttributeName=name,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5


aws --endpoint-url=http://localhost:4566 dynamodb scan --table-name franchise


#curl

curl -X POST http://localhost:8080/api/franchise \
   -H 'Content-Type: application/json' \
   -d '{"name":"SUBWAY"}'

curl -X POST http://localhost:8080/api/branchOffice \
   -H 'Content-Type: application/json' \
   -d '{"franchiseName":"SUBWAY","branchOfficeName":"SUBWAY_MANRIQUE"}'

curl -X POST http://localhost:8080/api/product \
   -H 'Content-Type: application/json' \
   -d '{"franchiseName":"SUBWAY","branchOfficeName":"SUBWAY_MANRIQUE","name":"SUBWAY_ITALIANO","stock":17}'

curl -X POST http://localhost:8080/api/product \
    -H 'Content-Type: application/json' \
    -d '{"franchiseName":"SUBWAY","branchOfficeName":"SUBWAY_MANRIQUE","name":"SUBWAY_HAWAIANO","stock":35}'

curl -X DELETE http://localhost:8080/api/product \
    -H 'Content-Type: application/json' \
    -d '{"franchiseName":"SUBWAY","branchOfficeName":"SUBWAY_MANRIQUE","name":"SUBWAY_HAWAIANO"}'

curl -X PUT http://localhost:8080/api/product \
    -H 'Content-Type: application/json' \
    -d '{"franchiseName":"SUBWAY","branchOfficeName":"SUBWAY_MANRIQUE","name":"SUBWAY_HAWAIANO","stock":24}'


curl -X POST http://localhost:8080/api/franchise \
   -H 'Content-Type: application/json' \
   -d '{"name":"QUESUDOS"}'

curl -X POST http://localhost:8080/api/branchOffice \
   -H 'Content-Type: application/json' \
   -d '{"franchiseName":"QUESUDOS","branchOfficeName":"CASTILLA"}'

curl -X POST http://localhost:8080/api/branchOffice \
   -H 'Content-Type: application/json' \
   -d '{"franchiseName":"QUESUDOS","branchOfficeName":"BELEN"}'

curl -X POST http://localhost:8080/api/product \
       -H 'Content-Type: application/json' \
       -d '{"franchiseName":"QUESUDOS","branchOfficeName":"BELEN","name":"PAN_PERRO","stock":150}'

curl -X POST http://localhost:8080/api/product \
       -H 'Content-Type: application/json' \
       -d '{"franchiseName":"QUESUDOS","branchOfficeName":"BELEN","name":"PAN_HAMBURGUESA","stock":200}'

curl -X POST http://localhost:8080/api/product \
       -H 'Content-Type: application/json' \
       -d '{"franchiseName":"QUESUDOS","branchOfficeName":"CASTILLA","name":"PAN_HAMBURGUESA","stock":300}'

curl -X GET http://localhost:8080/api/product/maxStockBranchOffice/SUBWAY