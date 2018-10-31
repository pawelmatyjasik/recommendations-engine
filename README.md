#Goal

Recommendation service for system that offers products of various categories. 
Products have defined lifecycle and expire after some point in time. Therefore 
recommendations base only on selected categories of products, not on specific products.

#Local setup

set env variable SPARK_USER to current user

#Algorithm selection
1. Matrix factorization
  PROS:
  CONS:
  - after new data comes in algorithm needs to recount values
2. Nearest neighbours
  PROS:
  CONS:
