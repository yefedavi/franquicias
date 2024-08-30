variable "instance_name" {
  description = "Value of the Name tag for the EC2 instance"
  type        = string
  default     = "FranchiseAppServerInstance"
}

variable "table_name" {
  default = "franchise"
}

variable "partition_key" {
  default = "name"
}
