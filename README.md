# LRU Cache Visualizer (Java)

An interactive LRU (Least Recently Used) Cache Visualizer built using Java Swing.
This project demonstrates how LRU caching works internally using HashMap and Doubly Linked List with animated UI blocks.

## Preview

![Preview](screenshot1.png)
![Preview](screenshot2.png)

## Features

* O(1) GET and PUT operations
* HashMap + Doubly Linked List implementation
* Animated colored cache blocks
* MRU → LRU visualization
* Automatic eviction of least recently used item
* Interactive GUI

## Technologies Used

* Java
* Swing (GUI)
* HashMap
* Doubly Linked List
* OOP

## How to Run

Compile:

```
javac LRUCacheVisualizer.java
```

Run:

```
java LRUCacheVisualizer
```

## Example

Capacity = 3

PUT 1
PUT 2
PUT 3

Cache:
[3] [2] [1]

GET 1

Cache:
[1] [3] [2]

## Time Complexity

GET → O(1)
PUT → O(1)

## Author

Sujal Chouksey
