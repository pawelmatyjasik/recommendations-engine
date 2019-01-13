#Goal

Recommendation service for system that offers products of various categories. 
Products have defined lifecycle and expire after some point in time. Therefore 
recommendations base only on selected categories of products, not on specific products.

#Local setup

On Windows machines
set HADOOP_HOME env variable to directory with winutils binary 

#Algorithm selection
1. Matrix factorization
  - after new data comes in algorithm needs to recount values
2. Nearest neighbours
3. Deep learning: https://spark.apache.org/docs/latest/ml-classification-regression.html#multilayer-perceptron-classifier
  - needs to be calculated for each user. Some generalzation required?
  
