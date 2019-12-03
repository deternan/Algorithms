# coding=utf8

'''
version: December 03, 2019 04:23 PM
Last revision: December 03, 2019 04:23 PM
  
Author : Chao-Hsuan Ke

Reference
https://www.datacamp.com/community/tutorials/scikit-learn-python

'''

#import sklearn
 
from sklearn import datasets

iris = datasets.load_iris()

digits = datasets.load_digits()

print(digits.data)
  
# Load in the `digits` data
#digits = datasets.load_digits()
  
# Print the `digits` data 
#print(digits)


#import pandas as pd
#
#datastlocation = 'D:\\Phelps\\GitHub\\Algorithms\\dataset\\optdigits.tra'

# Load in the data with `read_csv()`
#digits = pd.read_csv("http://archive.ics.uci.edu/ml/machine-learning-databases/optdigits/optdigits.tra", header=None)

# digits = pd.read_csv(datastlocation, header=None)
# 
# # Print out `digits`
# print(digits)


# # Isolate the `digits` data
# digits_data = digits.data
# 
# # Inspect the shape
# print(digits_data.shape)
# 
# # Isolate the target values with `target`
# digits_target = digits.target
# 
# # Inspect the shape
# print(digits_target.shape)
# 
# # Print the number of unique labels
# number_digits = len(np.unique(digits.target))
# 
# # Isolate the `images`
# digits_images = digits.images
# 
# # Inspect the shape
# print(digits_images.shape)


