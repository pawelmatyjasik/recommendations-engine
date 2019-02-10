# Goal #

We have a system for betting on chess matches. Customers bet on specific matches and we gather information about their betting history. The goal is to make the best recommendations for the customers. I will try two approaches:
* neural network,
* matrix factorization,
and compare them.

# Local setup #

On Windows machines
set HADOOP_HOME env variable to directory with winutils binary 

# Algorithms description #
1. Matrix factorization
  - after new data comes in algorithm needs to recount values
2. Nearest neighbours
3. Deep learning: https://spark.apache.org/docs/latest/ml-classification-regression.html#multilayer-perceptron-classifier
  - needs to be calculated for each user. Some generalzation required?
  
