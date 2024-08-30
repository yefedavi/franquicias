terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }
}

provider "aws" {
  region = "us-east-1"
}

resource "aws_instance" "app_server" {
  ami           = "ami-08d70e59c07c61a3a"
  instance_type = "t2.micro"

  tags = {
    Name = var.instance_name
  }
}

resource "aws_dynamodb_table" "franchise_table" {
  name           = var.table_name
  billing_mode   = "PAY_PER_REQUEST"

  attribute {
    name = "name"
    type = "S"  # S para tipo String
  }

  hash_key = var.partition_key
}